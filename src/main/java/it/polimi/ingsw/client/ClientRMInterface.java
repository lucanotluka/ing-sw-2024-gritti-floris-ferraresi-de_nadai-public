package it.polimi.ingsw.client;

import it.polimi.ingsw.listener.Event;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface used for the RMI connection
 *
 * @author Marco Ferraresi
 */
public interface ClientRMInterface extends Remote {
    /**
     * It modifies the event received and send it to the serverRMI
     * @param event to be handled
     * @throws RemoteException if posting the event fails
     */
    void postEvent(Event event) throws RemoteException;
}
