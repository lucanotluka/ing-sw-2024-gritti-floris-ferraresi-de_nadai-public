package it.polimi.ingsw.server;

import java.rmi.RemoteException;

/**
 * This class start a new Thread that handles the Socket communication
 * and start RMI to handle RMI communication
 *
 * @author Angelo De Nadai, Fabio Marco Floris
 */
public class ServerMain {
    /**
     * This is the main where the Thread is created and startRMI() is called
     * @param args
     */
    public static void main(String[] args){
        Server server = new Server();

        //Starting Server Socket thread
        new Thread(server::startSocket).start();

        try {
            server.startRMI(1);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}