package it.polimi.ingsw.exceptions;
/**
 * Class FullLobbyException: handles the issue of a player trying to connect to a full lobby
 */
public class FullLobbyException extends Exception{
    /**
     * Constructs a new custom exception object of class FullLobbyException.
     * @param message message of the exception.
     */
    public FullLobbyException(String message) {
        super(message);
    }
}
