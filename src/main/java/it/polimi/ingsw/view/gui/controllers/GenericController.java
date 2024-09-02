package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.view.gui.EventListenerGUI;

/**
 * Abstract class that provides a generic controller with the ability to handle event notifications
 * and display messages at the top of the GUI.
 */
public abstract class GenericController {
    protected EventListenerGUI eventListenerGUI;

    /**
     * Sets the event listener for the controller.
     *
     * @param listener the event listener to set
     */
    public void setEventListenerGUI(EventListenerGUI listener) {
        this.eventListenerGUI = listener;
    }

    /**
     * Abstract method to print a message at the top of the GUI.
     *
     * @param text the message text to print
     */
    public abstract void printTextTopMessage(String text);
}
