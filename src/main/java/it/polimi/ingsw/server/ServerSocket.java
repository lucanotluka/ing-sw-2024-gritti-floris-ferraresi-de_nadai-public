package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.DisconnectedException;
import it.polimi.ingsw.listener.Event;
import it.polimi.ingsw.listener.EventType;

import java.io.*;
import java.net.Socket;

/**
 * This class handles Socket communication with the client
 *
 * @author Marco Ferraresi, Luca Gritti
 */
public class ServerSocket implements CommunicationInterface {
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private boolean validity;

    /**
     * It constructs the ServerSocket
     * @param socket has to communicate with the client Socket
     */
    public ServerSocket(Socket socket){
        this.socket = socket;
        this.validity = true;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Listen an event from the client
     * @return the event received from the client
     * @throws DisconnectedException if the client disconnected
     */
    @Override
    public Event listen() throws DisconnectedException {
        try {
            Event receivedEvent = (Event) (new ObjectInputStream(inputStream)).readObject();
            if(receivedEvent.getEvent() == EventType.EXIT_GAME)
                System.out.println("[ServerSocket]: "+receivedEvent.getNickName() + " exited the Game.");
            return receivedEvent;
        } catch (IOException | ClassNotFoundException e) {
            throw new DisconnectedException(null);
        }
    }

    /**
     * Communicate an event to the client
     * @param event it is the event we want to communicate
     * @throws DisconnectedException if the client disconnected
     */
    @Override
    public void communicate(Event event) throws DisconnectedException {
        try {
            ObjectOutputStream objOutStream = new ObjectOutputStream(outputStream);
            objOutStream.writeObject(event);
            objOutStream.flush();
        } catch (IOException e) {
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
        return validity;
    }

    /**
     * Set false for reconnecting players.
     */
    @Override
    public void setValidity(boolean val) {
        this.validity = val;
    }


    /**
     * close the socket
     * @throws IOException if an I/ O error occurs when closing this socket
     */
    public void close() throws IOException {
        this.socket.close();
    }
}
