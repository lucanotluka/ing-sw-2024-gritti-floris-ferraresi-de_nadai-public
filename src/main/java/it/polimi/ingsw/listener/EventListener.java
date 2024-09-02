package it.polimi.ingsw.listener;

import it.polimi.ingsw.exceptions.DisconnectedException;
import it.polimi.ingsw.exceptions.EarlyTerminationException;

/**
 * The EventListener interface is implemented by the controller states and the virtual-view because
 * they need to communicate changes to each other.
 *
 * @author Marco Ferraresi
 */
public interface EventListener {

    /**
     * The controller states and virtual-view take an action based on the Event received.
     *
     * @param event is the type of Event
     */
    void receivedEvent(Event event) throws EarlyTerminationException, DisconnectedException;
}
