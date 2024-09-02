package it.polimi.ingsw.client;

import it.polimi.ingsw.listener.Event;
import it.polimi.ingsw.listener.EventType;
import it.polimi.ingsw.server.ServerRMInterface;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * The {@code ClientRMI} class implements the RMI client for the application.
 * It handles communication with the RMI server and manages events.
 *
 * <p>This class provides methods to connect to the server, post events,
 * and manage client-server communication.</p>
 *
 * @author Fabio Marco Floris, Angelo De Nadai, Marco Ferraresi, Luca Gritti
 */

public class ClientRMI extends UnicastRemoteObject implements CommunicationSetting, ClientRMInterface {

    private View view;
    private ServerRMInterface serverRMI;

    /**
     * Registry of the RMI
     */
    private Registry registry;

    /**
     * Constructs a new ClientRMI.
     *
     * @throws RemoteException if creating the new client fails
     */
    public ClientRMI() throws RemoteException {
        super();
    }

    /**
     * It modifies the event received (with view.manageEvent(event)) and send it to the serverRMI
     * with serverRMI.postEvent(event)
     *
     * @param event to be changed and sent to the serverRMI
     * @throws RemoteException if posting the event fails
     */
    @Override
    public void postEvent(Event event) throws RemoteException {

        if(event.getEvent() == EventType.EXIT_GAME) {
            System.out.println("Bye Bye!");
            try {
                UnicastRemoteObject.unexportObject(this, true);
            } catch (NoSuchObjectException e) {
                throw new RuntimeException(e);
            }
        } else if(event.getEvent() != EventType.PING){
            event = view.manageEvent(event);
            if(event.getEvent() != EventType.TIMEOUT){
                try {
                    serverRMI.postEvent(event);
                } catch (RemoteException e) {
                    System.out.println("[ClientRMI]: "+ e);
                    e.printStackTrace();
                }
            }

        }

    }

    /**
     * Connects the client to the RMI server using the specified server IP and port.
     *
     * @param serverIP   the IP address of the RMI server
     * @param serverPort the port number of the RMI server
     */
     public void connect(String serverIP, int serverPort) {

        //support variables
        boolean retry = false;
        int attempt = 1;
        int i;

        do {
            try {
                // get the registry from the server
                registry = LocateRegistry.getRegistry(serverIP, serverPort);

                // take a reference of the server from the registry
                serverRMI = (ServerRMInterface) registry.lookup("CommunicationInterface");

                // join server and client
                serverRMI.registerClient(this);

                System.out.println("Client RMI ready");
                retry = false;

            } catch (Exception e) {
                if (!retry) {
                    System.err.println("[ERROR] CONNECTING TO RMI SERVER: \n\tClient RMI exception: " + e + "\n");
                }
                System.err.println("[#" + attempt + "]Waiting to reconnect to RMI Server on port: '" + serverPort + "' with name: '" + serverIP + "'");

                //loop waiting for reconnection
                i = 0;
                int SECONDS_BETWEEN_RECONNECTION = 5;
                while (i < SECONDS_BETWEEN_RECONNECTION) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                   throw new RuntimeException(ex);
                    }
                    System.out.print(".");
                    i++;
                }
                System.out.println();

                //handle number of reconnection tests
                int NUM_OF_ATTEMPT_TO_CONNECT_TO_SERVER_BEFORE_GIVE_UP = 5;
                if (attempt >= NUM_OF_ATTEMPT_TO_CONNECT_TO_SERVER_BEFORE_GIVE_UP) {
                    System.out.println("Give up!");
                    try {
                        System.in.read();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.exit(-1);
                }
                retry = true;
                attempt++;
            }
        } while (retry); //reconnection loop

    }


    /**
     * Opens communication with the server by starting the server.
     *
     * @throws RemoteException if opening the communication fails
     */
    @Override
    public void openCommunication() throws RemoteException{
        serverRMI.startServer();
    }

    /**
     * Sets the view for the client.
     *
     * @param view the view to set
     * @throws RemoteException if setting the view fails
     */
    @Override
    public void setView(View view) throws RemoteException{
        this.view = view;
    }

}
