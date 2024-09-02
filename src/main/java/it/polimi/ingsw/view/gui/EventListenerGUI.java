package it.polimi.ingsw.view.gui;

/**
 * Ensures that when an event is triggered in a controller, the corresponding method in the GUI class is notified.
 * This allows the modification of the event parameter contained in it and enables communication with the server.
 */
public interface EventListenerGUI {
    /**
     * Method called when a string value is received from the GUI.
     *
     * @param value the received string value
     */
    void onStringReceived(String value);
}
