package it.polimi.ingsw.exceptions;

/**
 * Class UnplayableCardException: handles the case where a Player
 * try to play a GoldCard that doesn't respect its condition.
 */
public class UnplayableCardException extends Exception{
    /**
     * Constructs a new custom exception object called UnplayableCardException.
     * @param message message of the exception.
     */
    public UnplayableCardException(String message) {
        super(message);
    }
}
