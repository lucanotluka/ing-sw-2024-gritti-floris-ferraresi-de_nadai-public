package it.polimi.ingsw.exceptions;


/**
 * Exception thrown when an operation is attempted by a player who has never logged in.
 */
public class PlayerNeverLoggedException extends Exception {
    private String message;

    /**
     * Constructs a new PlayerNeverLoggedException with the specified detail message.
     *
     * @param message the detail message.
     */
    public PlayerNeverLoggedException(String message) {
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