package it.polimi.ingsw.exceptions;

/**
 * Exception thrown when an operation is attempted on an empty playfield with no cards.
 */
public class NoCardOnPlayfieldException extends Exception {

    /**
     * Constructs a new NoCardOnPlayfieldException with the specified detail message.
     *
     * @param message the detail message.
     */
    public NoCardOnPlayfieldException(String message) {
        super(message);
    }
}
