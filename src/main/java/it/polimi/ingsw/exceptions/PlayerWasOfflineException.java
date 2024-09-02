package it.polimi.ingsw.exceptions;

/**
 * Exception thrown when an operation is attempted involving a player who was offline.
 */
public class PlayerWasOfflineException extends Exception {
    private String message;

    /**
     * Constructs a new PlayerWasOfflineException with the specified detail message.
     *
     * @param message the detail message.
     */
    public PlayerWasOfflineException(String message) {
        this.message = message;
    }

    /**
     * Returns the detail message string of this exception.
     *
     * @return the detail message string.
     */
    @Override
    public String getMessage() {
        return message;
    }
}