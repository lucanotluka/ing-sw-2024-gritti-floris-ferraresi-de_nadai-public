package it.polimi.ingsw.server;

import it.polimi.ingsw.client.ClientRMInterface;
import it.polimi.ingsw.listener.Event;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface used for the RMI connection
 *
 * @author Fabio Marco Floris, Luca Gritti
 */
public interface ServerRMInterface extends Remote {
    /**
     * Register a new client
     * @param client to be registered
     * @throws RemoteException if register a new client fails
     */
   void registerClient(ClientRMInterface client) throws RemoteException;

    /**
     * Save an event that will be return
     * @param event to be returned
     * @throws RemoteException if posting the event fails
     */
   void postEvent(Event event) throws RemoteException;

    /**
     * Create and start a ServerThread
     * @throws RemoteException if starting the server fails
     */
    void startServer() throws RemoteException;
}
