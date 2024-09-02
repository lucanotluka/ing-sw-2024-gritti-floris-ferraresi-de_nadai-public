package it.polimi.ingsw.exceptions;
/**
 * Class IncorrectIdException: handles the issue of a player choosing a name already taken
 * the name must be distinguishable since a player is identified by it
 */
public class NicknameAlreadyTakenException extends Exception{
    private String message;
    /**
     * Constructs a new custom exception object of class NicknameAlreadyTakenException.
     * @param message message of the exception.
     */
    public NicknameAlreadyTakenException(String message) {
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

