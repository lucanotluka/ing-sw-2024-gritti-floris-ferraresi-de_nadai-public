package it.polimi.ingsw.exceptions;

/**
 * Class NoMoreCardToReplaceException: handles the case where a Player try to draw a PlayableCard
 * on the PlayField that doesn't exist anymore.
 */
public class NoMoreCardToReplaceException extends Exception{
    /**
     * Constructs a new custom exception object called NoMoreCardToReplaceException.
     * @param message message of the exception.
     */
    public NoMoreCardToReplaceException(String message) {
        super(message);
    }
}
