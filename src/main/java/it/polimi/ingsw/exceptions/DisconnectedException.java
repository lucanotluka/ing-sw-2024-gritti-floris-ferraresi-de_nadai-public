package it.polimi.ingsw.exceptions;
/**
 * Class DisconnectedException: handles the issues of player disconnections
 */
public class DisconnectedException extends Exception{
    private String message;
    /**
     * Constructs a new custom exception object of class DisconnectedException.
     * @param message message of the exception.
     */
    public DisconnectedException(String message) {
        this.message = message;
    }

    /**
     * getter of the Exception's message
     * @return the Exception's message
     */
    @Override
    public String getMessage() {
        return message;
    }
}
