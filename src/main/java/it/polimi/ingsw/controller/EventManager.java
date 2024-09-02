package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.DisconnectedException;
import it.polimi.ingsw.exceptions.EarlyTerminationException;
import it.polimi.ingsw.listener.Event;
import it.polimi.ingsw.listener.EventListener;

/**
 * The {@code EventManager} class is responsible for managing events within the application.
 * It allows setting an event listener and notifying events to the listener.
 *
 * <p>This class provides methods to set an {@link EventListener} and to notify events,
 * which can throw exceptions if the listener encounters issues.</p>
 *
 * @author Fabio Marco Floris
 */
public class EventManager {
    private EventListener listener;

    /**
     * Sets the event listener.
     *
     * @param listener the event listener to set
     */
    public void setListeners(EventListener listener) {
        this.listener = listener;
    }

    /**
     * Notifies an event to the listener.
     *
     * @param event the event to notify
     * @throws DisconnectedException if the listener is disconnected
     * @throws EarlyTerminationException if the listener encounters early termination
     */
    public void notifyEvent(Event event) throws EarlyTerminationException, DisconnectedException {
        listener.receivedEvent(event);
    }

}
