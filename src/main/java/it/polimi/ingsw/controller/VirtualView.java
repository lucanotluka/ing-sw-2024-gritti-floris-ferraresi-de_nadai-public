package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.DisconnectedException;
import it.polimi.ingsw.exceptions.EarlyTerminationException;
import it.polimi.ingsw.listener.Event;
import it.polimi.ingsw.listener.EventListener;
import it.polimi.ingsw.listener.EventType;
import it.polimi.ingsw.server.CommunicationInterface;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class represents our VirtualView that is,
 * the class through which we communicate with the server view,
 * using the objects of the Event class as messages
 *
 * @author Marco Ferraresi, Luca Gritti, Fabio Marco Floris
 */
public class VirtualView implements EventListener{
    private final Controller controller;
    private HashMap<String, CommunicationInterface> connections;
    private final EventManager eventManager;
    private final Object reconnectLock = new Object();
    private final Object disconnectLock = new Object();
    private final Object timeoutLock = new Object();

    /**
     * Constructs a new VirtualView
     * @param controller is the controller to connect to this object
     */
    public VirtualView(Controller controller) {
        this.controller = controller;
        this.connections = new HashMap<>();
        eventManager = new EventManager();
    }

    /**
     * set the event listener of the eventManager with which we communicate
     * @param state is the EventListener
     */
    public void setListenerState(EventListener state){
        eventManager.setListeners(state);
    }

    /**
     * Sends repetitively ping messages to all players to check for disconnection
    */
    public void heartBeat() {

        // In case there's only 1 player left, ping isn't sent. Timeout is already pinging.
        if(countHowManyPlayersAlive() == 1){
            return;
        }

        for (Map.Entry<String, CommunicationInterface> entry : connections.entrySet()){
            if(entry.getValue()!=null){
                try{
                    entry.getValue().communicate(new Event(EventType.PING, entry.getKey()));
                } catch (DisconnectedException | RemoteException e) {
                    try {
                        System.out.println("[vView]: " + entry.getKey() + " disconnected via HeartBeat.");
                        disconnect(entry.getKey());
                    } catch (EarlyTerminationException ignored) {}
                }
            }
        }
    }

    /**
     * Return the Connections HashMap
     *
     * @return actual connections
     */
    public HashMap<String, CommunicationInterface> getConnections() {
        return connections;
    }

    /**
     * link a given host to a given connection and put the couple in the connections map
     * @param host nickname of the client
     * @param connection communication interface with which the server communicate with the client
     */
    public void connect(String host, CommunicationInterface connection) {
        connections.put(host, connection);
    }

    /**
     * remove the given host and its respective connection from the connections map
     * @param host nickname of the client
     * @throws EarlyTerminationException if after removing the host, the map is empty
     */
    private void disconnect(String host) throws EarlyTerminationException {
        boolean timeoutCondition;
        synchronized (disconnectLock) {
            connections.put(host, null);
            timeoutCondition = countHowManyPlayersAlive() <= 1;
        }
        if (timeoutCondition) {
            System.out.println("[vView]: Timeout started!");
            synchronized (timeoutLock){
                startTimeout();
            }

        }
    }

    /**
     * called when there is only one remaining player, find the name of that player
     * @return the name of the last remaining player
     */
    private String findOnlinePlayerNickname() {
        for (Map.Entry<String, CommunicationInterface> entry : connections.entrySet()) {
            if (entry.getValue() != null) {
                return entry.getKey();
            }
        }
        return "";
    }

    /**
     * called if there is only one remaining player, starts the timeout
     * @throws EarlyTerminationException if the last player disconnects
     */
    private void startTimeout() throws EarlyTerminationException {

        // ===============      START the TIMEOUT   ==============
        // & make it notifiable by reconnect!

        long timeout = 20;  // seconds

        String host = findOnlinePlayerNickname();
        CommunicationInterface channel = connections.get(host);
        while(timeout > 0 && countHowManyPlayersAlive() <= 1){

            if(countHowManyPlayersAlive() == 0) throw new EarlyTerminationException(host);

            System.out.println("[TimeOut]: " + timeout + " sec.");

            Event event = new Event(EventType.TIMEOUT, host);
            event.setParameter("[TimeOut]: " + timeout + " sec.");


            try {
                channel.communicate(event);
                //channel.listen();
                Thread.sleep(1000);
            } catch (DisconnectedException | RemoteException e) {
                System.out.println("[vView / DebugOnly]: socio disconnesso ingiustamente");
                throw new EarlyTerminationException(host);
            } catch (InterruptedException ignored) { }

            timeout = timeout - 1;
        }

        if(timeout <= 0){
            try{
                Event event = new Event(EventType.TIMEOUT, host);
                event.setParameter("\nThe game is finished\nCongratulation "+host+", you are the last remaining player alive\ntherefore     YOU WIN!      \nnow you will return to the menu\n\n");
                channel.communicate(event);
                //channel.listen();

                connections.put(host, null);

            } catch (DisconnectedException | RemoteException e) {
                System.out.println("[vView / DebugOnly]: " + e);
            }

            throw new EarlyTerminationException(host);
        }

    }

    /**
     * Counts how many players are currently alive (connected).
     *
     * @return the number of players currently alive.
     */
    public long countHowManyPlayersAlive() {
        return connections.values().stream().filter(Objects::nonNull).count();
    }


    /**
     * reconnect a player to this controller, link the given connection to the
     * given player in the connections map
     * @param host nickname of the player to reconnect
     * @param connection communication interface to link to the player
     * @return true if the player's nickname is a key of the map and the corrective value is null
     */
    public boolean reconnect(String host, CommunicationInterface connection){

        synchronized (reconnectLock){
            if (connections.containsKey(host) && connections.get(host) == null) {

                connect(host, connection);
                System.out.println("[vView]: " + host + "'s new connection established");

                return true;
            }
            return false;
        }
    }

    /**
     * this method sends the given event to the player whose name is listed
     * in the event, it then notifies the controller of the response
     * @param event is the Event to send
     * @throws DisconnectedException if the player is unreachable
     * @throws EarlyTerminationException if there are no player connected
     */
    @Override
    public void receivedEvent(Event event) throws EarlyTerminationException, DisconnectedException {

        // disconnected exception is thrown by both listen() and this very method, so this is the first way to handle disconnections:
        // checking if the current player is disconnected.

        synchronized (timeoutLock){
            if(countHowManyPlayersAlive() == 0)
                throw new EarlyTerminationException(event.getNickName());
            CommunicationInterface channel = connections.get(event.getNickName());

            if(channel == null) { // if it's trying to communicate to offline player.
                System.out.println("[vView]:" + event.getNickName() + " is still offline.");
                event = new Event(EventType.PING, event.getNickName());
                throw new DisconnectedException(event.getNickName());
            }
            else {
                try {
                    channel.communicate(event);
                    event = connections.get(event.getNickName()).listen();
                } catch (DisconnectedException | RemoteException e) {

                    disconnect(event.getNickName());               // disconnect the current player (the player whom the event was for)

                    throw new DisconnectedException("[vView]: " + event.getNickName() + " disconnected.");
                }
            }
            eventManager.notifyEvent(event);
        }

    }
    /**
     * removes all connections and player from the connections map
     */
    public void removeAllPlayers() {
        connections.clear();
    }
}