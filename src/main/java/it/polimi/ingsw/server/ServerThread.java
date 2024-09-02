package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.listener.Event;
import it.polimi.ingsw.listener.EventType;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

/**
 * This class handles the communication with the Client until the match doesn't start
 *
 * @author Marco Ferraresi, Luca Gritti
 */
public class ServerThread extends Thread{

    private Server server;
    private CommunicationInterface channel;
    private static int idController = 1;

    /**
     * It constructs the ServerThread
     * @param channel for the communication with the client
     * @param server handles the users and controllers
     */
    public ServerThread(CommunicationInterface channel, Server server){
        this.channel=channel;
        this.server = server;

    }

    /**
     * It handles the Client, in particular it handles the login and the menu
     *      * @throws IOException
     *      * @throws DisconnectedException if the client disconnected
     */
    public void run() {
        try {
            try {

                // client log in or reconnect logic
                String nickname = loginClient();

                // LOOP for THE MENU and the MATCHES
                menuAndMatchHandler(nickname);

            } catch (DisconnectedException e ){
                if(channel instanceof ServerSocket){
                    ((ServerSocket)channel).close();
                }
                this.interrupt();
            }
        } catch (IOException ignored) {}
    }

    /**
     * It handles the menu
     * @param nickname of the player
     * @throws DisconnectedException if Socket disconnection occurs
     * @throws RemoteException if RMI disconnection occurs
     */
    private void menuAndMatchHandler(String nickname) throws DisconnectedException, RemoteException {

        while (true) {

            // ---------------------    MENU'    -------------------

            Event event1 = new Event(EventType.CHOOSE_CONNECT_OR_CREATE_LOBBY, nickname);
            int choice;
            event1.setParameter("Do you want to create a new lobby or join an existing one?\n" +
                    "[1] Create a new lobby\n[2] Join an existing lobby\n\n[0] Exit");

            channel.communicate(event1);
            event1 = channel.listen();
            choice = event1.getN1();


            //          [0]  Client wants to disconnect
            if(choice == 0){
                Event eventExit = new Event(EventType.EXIT_GAME, nickname);
                channel.communicate(eventExit);
                channel.listen();

                server.logoutUser(nickname);


                try {
                    if(channel instanceof ServerSocket){
                        ((ServerSocket)channel).close();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;

            }
            //          [1]  Client is creating a lobby
            else if (choice == 1){
                try {
                    Controller match = createLobby(nickname);

                    Thread.sleep(300);

                    match.startLobby();

                    Thread.sleep(2000);
                    match.startEverything();

                    server.removeController(match);
                    System.out.println("[ServerThread]: Controller "+match.getIdMatch()+" removed");
                } catch (InterruptedException ignored) {}
            }
            //          [2]  Client want to join a Lobby
            else {

                try {
                    Thread.sleep(300);

                    joinLobby(nickname);

                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}
            }


            // -------------- end of while iteration ------------------
            // Player exited [0], or its Match ended [1, 2]
        }
    }

    /**
     * It handles the choice of a player to join a game
     * @param nickname of the player
     * @throws DisconnectedException if Socket disconnection occurs
     * @throws RemoteException if RMI disconnection occurs
     */
    private void joinLobby(String nickname) throws DisconnectedException, RemoteException {

        Event event3 = new Event(EventType.ID_MATCH, nickname);
        event3.setParameter("Insert the ID of the match you want to join or type [0] for going back to the Menu: ");
        channel.communicate(event3);
        event3 = channel.listen();

        int idMatch = event3.getN1();

        Controller controller;

        // idMatch == 0 if Client chosed to go back to MENU

        // Loop until Client finds a correct Match given ID
        while (idMatch != 0) {

            try {
                controller = server.getControllerById(idMatch);

                // register the Client into the hashmap of Server: if not present, register;
                // if present, Client was previously disconnected & needs to be reconnected
                // this last case should be handled previously
                try {
                    server.addUserController(nickname, idMatch);
                } catch (PlayerAlreadyLoggedException e) {
                    System.out.println(e.getMessage());
                }

                controller.connect(nickname, channel);
                System.out.println(nickname+" has been registered to Match "+idMatch);

                try {
                    Thread.sleep(300);
                } catch (InterruptedException ignored) {
                }

                Event eventOK = new Event(EventType.JOIN_LOBBY, nickname);
                eventOK.setParameter("Match " + controller.getIdMatch() + " has now a lobby, for a maximum of " + controller.getNumOfPlayers() + " players.");
                ArrayList<String> nickNames = new ArrayList<>(controller.getPlayersAndConnections().keySet());
                nickNames.remove(nickname);
                eventOK.setPlayers(nickNames);
                channel.communicate(eventOK);
                channel.listen();

                for (Map.Entry<String, CommunicationInterface> entry : controller.getPlayersAndConnections().entrySet()){
                    if (!(entry.getKey().equals(nickname))){
                        Event event = new Event(EventType.PLAYER_JOINED, entry.getKey());
                        event.setParameter(nickname);
                        entry.getValue().communicate(event);
                        entry.getValue().listen();
                    }
                }

                break;

            } catch (IncorrectIdException e) {

                try {
                    Thread.sleep(200);
                } catch (InterruptedException ignored) {}

                event3.setParameter("\nThe ID doesn't exist, please insert a correct one");
                channel.communicate(event3);
                event3 = channel.listen();
                idMatch = event3.getN1();

            } catch (FullLobbyException e) {

                try {
                    Thread.sleep(200);
                } catch (InterruptedException ignored) {}
                event3.setParameter("\nThe lobby you are trying to connect is full, please try a new one");
                channel.communicate(event3);
                event3 = channel.listen();
                idMatch = event3.getN1();

            }




        }

        // Let connected Players to loop
        waitUntilMatchIsFinished(idMatch);

    }

    /**
     * The players that join a game will loop until Match is finished
     * @param idMatch to check if the game is finished
     */
    private void waitUntilMatchIsFinished(int idMatch) {
        while (true){
            try {
                server.getControllerById(idMatch);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {}

            } catch (IncorrectIdException e) {
                break;
            }

        }
    }

    /**
     * Create a new match with a number of players ranging from 2 to 4
     * @param nickname of the player that wants to create the match
     * @return the new match created
     * @throws DisconnectedException if Socket disconnection occurs
     * @throws RemoteException if RMI disconnection occurs
     */
    private Controller createLobby(String nickname) throws DisconnectedException, RemoteException {

        Event event2 = new Event(EventType.SELECT_NUMBERS_PLAYERS, nickname);
        event2.setParameter("Your Match ID is: " + idController +
                "\nInsert number of players (a number between 2 and 4): ");
        channel.communicate(event2);
        event2 = channel.listen();
        int numberOfPlayers = event2.getN1();

        Controller match = new Controller(idController, numberOfPlayers, nickname, channel);
        server.addController(match);

        idController++;

        // register the Client into the hashmap of Server: if not present, register
        try {
            server.addUserController(nickname, match.getIdMatch());
        } catch (PlayerAlreadyLoggedException e) {
            System.out.println("This player already logged in: debug..");
        }


        try {
            Thread.sleep(300);
        } catch (InterruptedException ignored) {}

        Event eventOK = new Event(EventType.JOIN_LOBBY, nickname);
        eventOK.setParameter("Match " + match.getIdMatch() + " has a lobby for a maximum of " + numberOfPlayers + " players.");
        channel.communicate(eventOK);
        channel.listen();

        return match;
    }

    /**
     * It handles the login
     * @return the chosen nickname
     * @throws DisconnectedException if Socket disconnection occurs
     * @throws RemoteException if RMI disconnection occurs
     */
    private String loginClient() throws DisconnectedException, RemoteException {
        String nickname;
        Event event = new Event(EventType.CHOOSE_NICKNAME, "server");
        event.setParameter("Please, insert username! ");

        while(true){

            channel.communicate(event);
            event = channel.listen();
            nickname = event.getParameter();

            try { // check for the unique username
                server.loginUser(nickname);
                System.out.println("[ServerThread]: "+nickname + " logged in.");
                break;
            } catch (NicknameAlreadyTakenException e) {
                event.setParameter("[ServerThread]: Name already taken, try a new one:");
            }
            catch (PlayerWasOfflineException e) { // reconnect user if previously crashed

                Controller controller = server.reconnectUser(nickname);

                if(controller != null) {
                    Event eventRedirect = new Event(EventType.TEXT_MESSAGE, nickname);
                    eventRedirect.setParameter(nickname+", we are going to reconnect you to Match "+controller.getIdMatch());
                    channel.communicate(eventRedirect);
                    channel.listen();

                    controller.reconnect(nickname, channel);

                    // non-lobby-creators clients going to loop until Match is finished (IncorrectIdException is thrown)
                    while (true){
                        try {
                            server.getControllerById(controller.getIdMatch());
                            Thread.sleep(1000);
                        } catch (IncorrectIdException | InterruptedException e1) {
                            break;
                        }
                    }

                    // return this Method to the Caller
                    break;
                }
                System.out.println("[ServerThread]: problems in reconnecting...");

            }
        }
        return nickname;
    }
}
