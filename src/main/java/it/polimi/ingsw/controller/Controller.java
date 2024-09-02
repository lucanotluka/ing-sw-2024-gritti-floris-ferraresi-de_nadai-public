package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.DisconnectedException;
import it.polimi.ingsw.exceptions.EarlyTerminationException;
import it.polimi.ingsw.exceptions.FullLobbyException;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.CommunicationInterface;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents our Controller
 *
 * @author Marco Ferraresi, Luca Gritti, Fabio Marco Floris
 */
public class Controller {
    private Model model;
    private final int idMatch;
    private final int numOfPlayers;

    private ArrayList<Player> playersList;
    private ArrayList<Player> onlinePlayers;
    private State currState;
    private final VirtualView virtualView;

    /**
     * Number of players getter
     * @return the number of players
     */
    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    /**
     * Check if the player is offline
     * @param username of the player
     * @return true if player has logged into match previously and it's not online
     */
    public boolean isPlayerOffline(String username) {
        return playersList.stream().anyMatch(player -> player.getNickName().equals(username))
                && virtualView.getConnections().get(username) == null;
    }

    /**
     * Constructs a new Controller
     * @param idMatch is the id of the controller
     * @param numOfPlayers is the number of players of this controller
     * @param nicknameFirstPlayer is the nickname of the first player that has created this controller
     * @param connection is the type of communication (Socket or RMI)
     */
    public Controller(int idMatch, int numOfPlayers, String nicknameFirstPlayer, CommunicationInterface connection) {

        this.playersList = new ArrayList<>(numOfPlayers);
        this.numOfPlayers = numOfPlayers;
        this.idMatch = idMatch;
        this.model = null; // to be constructed!
        this.currState = null; // to be initialized
        this.onlinePlayers = null; // to be initialized
        this.virtualView = new VirtualView(this);

        virtualView.connect(nicknameFirstPlayer, connection);
        playersList.addFirst(new Player(nicknameFirstPlayer));
    }

    /**
     * Wait until the lobby is full then create the model so the game can starts
     */
    public synchronized void startLobby(){

        // add all the others players until numOfPlayers
        while(playersList.size() < numOfPlayers){
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // raw copying of the array
        onlinePlayers = new ArrayList<>(playersList);

        // if everything is OK: construct the game!
        this.model = new Model(onlinePlayers);
    }

    /**
     * Connect a player to the lobby
     * @param nicknameNewPlayer is the nickname of the player that wants to connect to the match
     * @param connection is the type of communication (Socket or RMI)
     * @throws FullLobbyException if the lobby is already full
     */
    public synchronized void connect(String nicknameNewPlayer, CommunicationInterface connection) throws FullLobbyException{
        if(playersList.size() < numOfPlayers) {
            playersList.addLast(new Player(nicknameNewPlayer));
            virtualView.connect(nicknameNewPlayer, connection);
        } else {
            throw new FullLobbyException("Full lobby try a new one");
        }
        this.notifyAll();
    }

    /**
     * Reconnect a player to the match
     * @param nicknamePlayer is the nickname of the player that wants to reconnect
     * @param connection is the type of communication (Socket or RMI)
     * @throws DisconnectedException if the client disconnected
     */
    public void reconnect(String nicknamePlayer, CommunicationInterface connection) throws DisconnectedException {

        // if nicknamePlayer is present in the playersList that started the Match
        if(playersList.stream().anyMatch(player -> player.getNickName().equals(nicknamePlayer))) {

            System.out.println("[Controller]: Trying to reconnecting " + nicknamePlayer + " to the match...");

            // Current reconnecting player needs to have an invalid connection: cannot play yet.
            try {
                connection.setValidity(false);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            if (virtualView.reconnect(nicknamePlayer, connection)) {
                try {
                    currState.reconnectPlayer(nicknamePlayer);
                } catch (EarlyTerminationException e) {
                    //ignora
                }
                // Wait for notify of the state change
                while (currState instanceof SetupState) {
                    try {
                        System.out.println("[Controller]: " + nicknamePlayer + " is waiting for SetupState to finish");
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                // current player now can play: connection validity set true.
                try {
                    connection.setValidity(true);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }


                // the CommInterface has been already restored in the Caller method
                System.out.println("[Controller]: " + nicknamePlayer + " returned online!");

            } else System.out.println("[Controller]: cannot add " + nicknamePlayer + " Comm. Interface");
        }
        else System.out.println("[Controller]: " + nicknamePlayer + " never joined this lobby");
    }

    /**
     * State Design Pattern: SetupState then PlayingState and finally EndGameState
     * with the start of the HeartBeats thread.
     */
    public void startEverything() {
        try{

            // HeartBeats thread!
            new Thread(() -> {
                boolean running = true;
                System.out.println("[Controller]: HeartBeats thread started working.");
                while(running){
                    if(virtualView.countHowManyPlayersAlive() == 0) running = false;

                    virtualView.heartBeat();

                    try {
                        Thread.sleep( 3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("[Controller]: HeartBeats thread just stopped working.");
            }).start();


            // Setup: it loads the Decks, gives the PlayableCards for the Players and setups the PlayField

            currState = new SetupState(model);
            virtualView.setListenerState(currState);
            currState.setListenerVirtualView(virtualView);
            currState.startPhase();


            // Playing: the core of the match. Each player in round does:
            //      1st action. Play a card from hand
            //      2nd action. Draw a card from playfield
            //    Every action notifies the Endgame Conditions.

            currState = new PlayingState(model);
            virtualView.setListenerState(currState);
            currState.setListenerVirtualView(virtualView);
            currState.startPhase();


            // Endgame: when a Player reaches at least 20 pts OR
            //         both decks of the playfield are empty.
            //         The current round is executed, no matter who
            //         the player is. A final round, from the first
            //         player to the last one, is played.
            //     All the points from the hidden Objective Cards
            //     are added to every Player's points.
            //   The Player with maximum points wins. (Could be that more Players wins).

            currState = new EndGameState(model);
            virtualView.setListenerState(currState);
            currState.setListenerVirtualView(virtualView);
            currState.startPhase();

        } catch (EarlyTerminationException e) {
            System.out.println("[Controller]: Match " + idMatch + " just early terminated.");
        }

        virtualView.removeAllPlayers();
        System.out.println("[Controller]: removed all vView connections.");
    }

    /**
     * Retrieves the id of the match
     * @return the id of the match
     */
    public int getIdMatch() {
        return idMatch;
    }

    /**
     * Retrieves the model
     * @return the model
     */
    public Model getModel() {
        return this.model;
    }

    /**
     * Retrieves the map of players and their connection
     * @return the map of players and their connection
     */
    public HashMap<String, CommunicationInterface> getPlayersAndConnections() {
        return virtualView.getConnections();
    }
}
