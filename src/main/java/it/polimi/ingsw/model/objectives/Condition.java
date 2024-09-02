package it.polimi.ingsw.model.objectives;

import it.polimi.ingsw.model.Board;

/**
 * interface exposed by all the cards that need to check if a condition is
 * respected in the board
 *
 * @author Fabio Marco Floris
 */
public interface Condition {

    /**
     * Check how many times the condition in the board passed as a parameter is respected
     *
     * @param matrix that must comply with the condition
     * @return number of times the condition is met
     */
    int checkCondition(Board matrix);
}
