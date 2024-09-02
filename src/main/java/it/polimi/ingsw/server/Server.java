package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exceptions.IncorrectIdException;
import it.polimi.ingsw.exceptions.NicknameAlreadyTakenException;
import it.polimi.ingsw.exceptions.PlayerAlreadyLoggedException;
import it.polimi.ingsw.exceptions.PlayerWasOfflineException;

import java.io.IOException;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents our Server, it starts the chosen communication and handles the controllers
 *
 * @author Marco Ferraresi, Fabio Marco Floris
 */
public class Server {
    private ArrayList <Controller> controllers;
    private HashMap<String, Controller> usersControllers;
    private Registry registry;

    /**
     * It constructs the Server
     */
    public Server() {
        controllers = new ArrayList<>();
        usersControllers = new HashMap<>();

        try (Socket socket = new Socket("localhost", 50865)) {

            // Port is already in use
            socket.close();

        } catch (IOException e) {
            // Port is available
         }

        try {
            registry = LocateRegistry.createRegistry(50865);
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Socket communication begins
     */
    public void startSocket() {
        try {

            java.net.ServerSocket serverSocket = null;

            try (Socket socket = new Socket("localhost", 50866)) {
                // Port is already in use
                socket.close();
                serverSocket = new java.net.ServerSocket(50866);
                System.out.println("[ServerSocket]: the server ready to accept new clients at " + serverSocket.getInetAddress().getHostAddress() + ' ' + serverSocket.getLocalPort());

            } catch (IOException e) {
                // Port is available
                serverSocket = new java.net.ServerSocket(50866);
                System.out.println("[ServerSocket]: the server ready to accept new clients at " + serverSocket.getInetAddress().getHostAddress() + ' ' + serverSocket.getLocalPort());
            }


            while (true) { //keep accepting new clients
                try //accept incoming connections from clients
                {
                    Socket socket = serverSocket.accept();
                    System.out.println("[ServerSocket]: client connected: " + socket.getInetAddress().getHostAddress() +' ' + socket.getLocalPort());


                    // Handle client connection in a separate thread to allow the server to continue accepting new clients
                    new ServerThread(new ServerSocket(socket), this).start();

                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * RMI communication begins
     * @param i is the id of the ServerRMI
     * @throws RemoteException if starting RMI fails
     */
    public void startRMI(int i) throws RemoteException {
        ServerRMI server = new ServerRMI(i, this);
        registry.rebind("CommunicationInterface", server);

    }

    /**
     * Get a controller by its id
     * @param idMatch is the id of the controller
     * @return the controller
     * @throws IncorrectIdException if the id of the match doesn't exist
     */
    public Controller getControllerById (int idMatch) throws IncorrectIdException {
        for (Controller controller : controllers){
            if(controller.getIdMatch() == idMatch){
                return controller;
            }
        }
        throw new IncorrectIdException("ID doesn't exists");
    }

    /**
     * Add a controller to the list
     * @param controller to be added
     */
    public void addController (Controller controller){
        controllers.add(controller);
    }

    /**
     * Remove a controller from the list
     * @param controller to be removed
     */
    public void removeController (Controller controller){
        removeUserController(controller);
        controllers.remove(controller);
    }

    /**
     * Check if the nickname is valid, or it is already taken
     * @param nickname to be checked
     * @throws NicknameAlreadyTakenException if the nickname is already taken
     * @throws PlayerWasOfflineException if the player was offline
     */
    public void loginUser(String nickname) throws NicknameAlreadyTakenException, PlayerWasOfflineException {
        synchronized (usersControllers){
            if ( ! usersControllers.containsKey(nickname))
            {
                usersControllers.put(nickname, null);      // client controller is initialized @ null
            }
            else if (usersControllers.get(nickname) != null){

                if(usersControllers.get(nickname).isPlayerOffline(nickname)) {
                    throw new PlayerWasOfflineException(nickname + " was offline, redirect...");
                }
                else
                    throw new NicknameAlreadyTakenException("Nickname is already taken!");
            }
        }
    }

    /**
     * Remove the players from the controller when the game is finished
     * @param finishedMatch is the controller where the players have to be removed
     */
    private void removeUserController(Controller finishedMatch){
        for(Map.Entry<String, Controller> entry: usersControllers.entrySet()){
            if( entry != null && entry.getValue() != null &&
            entry.getValue().getIdMatch() == finishedMatch.getIdMatch()) {
                entry.setValue(null);
            }
        }
    }

    /**
     * Register a player to the match
     * @param nick is the nickname of the player
     * @param idMatch is the id of the controller
     * @throws PlayerAlreadyLoggedException if the player has already logged
     */
    public void addUserController(String nick, int idMatch) throws PlayerAlreadyLoggedException {
        synchronized (usersControllers) {
            if ( usersControllers.containsKey(nick) && usersControllers.get(nick) == null) {
                try {
                    usersControllers.put(nick, getControllerById(idMatch));
                } catch (IncorrectIdException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new PlayerAlreadyLoggedException("[DEBUG]: this player has already logged into a Match.");
            }
        }
    }

    /**
     * Reconnect a player to the match
     * @param nick is the nickname of the player
     * @return the controller associated to the player or null
     */
    public Controller reconnectUser(String nick) {
        synchronized (usersControllers){
            return usersControllers.getOrDefault(nick, null);
        }
    }

    /**
     * Logout of a player
     * @param nickname of the player
     */
    public void logoutUser(String nickname){
        synchronized (usersControllers) {
            usersControllers.remove(nickname);
        }
    }
}