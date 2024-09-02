package it.polimi.ingsw.exceptions;

/**
 * Exception thrown when a player attempts to log in but is already logged in.
 */
public class PlayerAlreadyLoggedException extends Exception {
    private String message;

    /**
     * Constructs a new PlayerAlreadyLoggedException with the specified detail message.
     *
     * @param message the detail message.
     */
    public PlayerAlreadyLoggedException(String message) {
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
