package it.polimi.ingsw.server;

import it.polimi.ingsw.client.ClientRMInterface;
import it.polimi.ingsw.exceptions.DisconnectedException;
import it.polimi.ingsw.listener.Event;
import it.polimi.ingsw.listener.EventType;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class handles RMI communication with the client
 *
 * @author Luca Gritti, Angelo De Nadai, Fabio Marco Floris, Marco Ferraresi
 */
public class ServerRMI extends UnicastRemoteObject implements CommunicationInterface, ServerRMInterface {
    private Event toReturn;
    private ClientRMInterface clientRMI;
    private final Server master;
    private boolean validity;
    int id;

    /**
     * It constructs the ServerRMI
     * @param id is the id pf the ServerRMI
     * @param server handles the users and controllers
     * @throws RemoteException if creating a new ServerRMI fails
     */
    public ServerRMI(int id, Server server) throws RemoteException {
        super();
        this.id = id;
        this.validity = true;
        System.out.println("[ServerRMI]: ServerRMI "+id+" created!");

        master = server;
    }

    /**
     * Register a new client and create another ServerRMI
     * @param client to be registered
     * @throws RemoteException if register a new client fails
     */
    @Override
    public void registerClient(ClientRMInterface client) throws RemoteException{
        System.out.println("[ServerRMI]: client connected");
        clientRMI = client;

        master.startRMI(id+1);
    }

    /**
     * Create and start a ServerThread
     * @throws RemoteException if starting the server fails
     */
    @Override
    public void startServer() throws RemoteException {
        new ServerThread(this, master).start();
    }

    /**
     * Save an event that will be return in listen()
     * @param event to be returned
     * @throws RemoteException if posting the event fails
     */
    @Override
    public void postEvent(Event event) throws RemoteException{
        toReturn = event;
    }

    /**
     * Communicate an event to the client
     * @param event it is the event we want to communicate
     * @throws DisconnectedException if the client disconnected
     */
    @Override
    public void communicate(Event event) throws DisconnectedException {
        try {
            clientRMI.postEvent(event);
        } catch (RemoteException e) {
            throw new DisconnectedException(event.getNickName());
        }
    }

    /**
     * Check for reconnecting or online players.
     *
     * @return true if CommunicationInterface is Valid
     */
    @Override
    public boolean getValid() {
        return this.validity;
    }

    /**
     * Set false for reconnecting players.
     */
    @Override
    public void setValidity(boolean val) {
        validity = val;
    }

    /**
     * Listen an event from the client
     * @return the event received from the client
     */
    @Override
    public Event listen() {
        if(toReturn.getEvent() == EventType.EXIT_GAME)
            System.out.println("[ServerRMI]: "+toReturn.getNickName() + " exited the Game.");

        return toReturn;
    }

}