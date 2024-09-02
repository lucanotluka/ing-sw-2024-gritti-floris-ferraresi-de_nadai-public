package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.DisconnectedException;
import it.polimi.ingsw.listener.Event;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The CommunicationInterface is implemented by ServerRMI, ServerSocket
 *
 * @author Marco Ferraresi
 */
public interface CommunicationInterface extends Remote{
    /**
     * It is used to wait until an answer is given
     *
     * @return an Event
     * @throws DisconnectedException if the client disconnected
     * @throws RemoteException if listening an event fails
     */
    Event listen() throws DisconnectedException, RemoteException;

    /**
     * It is used to communicate an Event
     *
     * @param event it is the event we want to communicate
     * @throws DisconnectedException if the client disconnected
     * @throws RemoteException if communicating an event fails
     */
    void communicate(Event event) throws DisconnectedException, RemoteException;

    /**
     * Check for reconnecting or online players.
     *
     * @return true if CommunicationInterface is Valid
     */
    boolean getValid() throws RemoteException;

    /**
     * Set false for reconnecting players, true for online playing players.
     */
    void setValidity(boolean validity) throws RemoteException;

}