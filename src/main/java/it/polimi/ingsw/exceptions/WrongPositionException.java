package it.polimi.ingsw.exceptions;

/**
 * Class WrongPositionException: handles all issues with placing cards
 */
public class WrongPositionException  extends Exception{
    /**
     * Constructs a new custom exception object called WrongPositionException.
     * @param message message of the exception.
     */
    public WrongPositionException(String message) {
        super(message);
    }
}
