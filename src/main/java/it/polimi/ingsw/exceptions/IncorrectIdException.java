package it.polimi.ingsw.exceptions;
/**
 * Class IncorrectIdException: handles the issue of a player trying to connect to a non-existing lobby
 */
public class IncorrectIdException extends Exception{
    /**
     * Constructs a new custom exception object of class IncorrectIdException.
     * @param message message of the exception.
     */
    public IncorrectIdException(String message) {
        super(message);
    }
}
