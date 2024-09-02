package it.polimi.ingsw.client;

import it.polimi.ingsw.view.View;

import java.rmi.RemoteException;

/**
 * This interface is implemented by the client to open a communication with the server
 * and to set the view
 *
 * @author Fabio Marco Floris
 */
public interface CommunicationSetting {

    /**
     * Open the communication with the server
     * @throws RemoteException if opening the communication fails
     */
    void openCommunication() throws RemoteException;

    /**
     * Set the view
     *
     * @param view to be set
     * @throws RemoteException if setting the view fails
     */
    void setView(View view) throws RemoteException;

}
