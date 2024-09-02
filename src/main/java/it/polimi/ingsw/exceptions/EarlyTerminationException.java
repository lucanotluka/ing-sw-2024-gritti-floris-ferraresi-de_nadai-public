package it.polimi.ingsw.exceptions;
/**
 * Class EarlyTerminationException: handles the case in which there is no longer any player connected
 */
public class EarlyTerminationException extends Exception{
    /**
     * Constructs a new custom exception object of class EarlyTerminationException.
     * @param message message of the exception.
     */
    public EarlyTerminationException(String message) {
        super(message);
    }
}
