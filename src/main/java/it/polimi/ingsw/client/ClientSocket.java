package it.polimi.ingsw.client;

import it.polimi.ingsw.listener.Event;
import it.polimi.ingsw.listener.EventType;
import it.polimi.ingsw.view.View;

import java.io.*;
import java.net.Socket;

/**
 * The ClientSocket class represents the client if the communication's choice was Socket.
 * It receives the events from the server, send them to the view for viewing and editing,
 * finally it sends the modified event to the server.
 *
 * @author Fabio Marco Floris, Luca Gritti
 */
public class ClientSocket implements CommunicationSetting{
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private View view;

    /**
     * Constructs a new ClientSocket
     *
     * @param socket The socket that was created in the Client class
     */
    public ClientSocket(Socket socket){
        this.socket = socket;

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set the user interface
     *
     * @param view The user interface created in Client
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * Open the communication with the server.
     * Events are handled in this class. They are received from the server,
     * sent to the view and then the modified event is sent to the server.
     */
    @Override
    public void openCommunication() {

        Event event;
        event = receveEvent();
        do {

            // Client Exit Game
            if(event.getEvent() == EventType.EXIT_GAME) {
                System.out.println("Bye Bye!");
                sendEvent(event);
                break;
            }
            else  if(event.getEvent() != EventType.PING){

                // clean inputStream

                event = view.manageEvent(event);
                if(event.getEvent() != EventType.TIMEOUT)
                    sendEvent(event);

            }
            event = receveEvent();
        } while (true);

        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Receive the event from the server
     *
     * @return the event received
     */
    public Event receveEvent() {
        try {
            return (Event) (new ObjectInputStream(inputStream)).readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Send the event to the server
     *
     * @param event The event that we want to send to the server
     */
    public void sendEvent(Event event) {
        try {
            ObjectOutputStream objOutStream = new ObjectOutputStream(outputStream);
            //objOutStream.reset();
            objOutStream.writeObject(event);
            objOutStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}