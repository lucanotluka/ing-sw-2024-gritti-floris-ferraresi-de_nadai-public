package it.polimi.ingsw.model.objectives;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.cards.Symbol;
/**
 * The SymbolObjectiveCard class represents an objective card based on symbols.
 * It defines conditions based on symbols that players must fulfill to earn points.
 *
 * @author Angelo De Nadai, Marco Ferraresi
 */

public class SymbolObjectiveCard extends ObjectiveCard {

    //symbols required to obtain the points
    private final Symbol symbol1;
    private final Symbol symbol2;
    private Symbol symbol3 = null;
    private final int howManySymbols;

    /**
     * Constructs a new SymbolObjectiveCard object with three symbols and points.
     *
     * @param symbol1 The first symbol required for the objective.
     * @param symbol2 The second symbol required for the objective.
     * @param symbol3 The third symbol required for the objective.
     * @param points  The points awarded for completing the objective.
     */

    public SymbolObjectiveCard(int id, int points, Symbol symbol1, Symbol symbol2, Symbol symbol3) {
        super(id, points);
        this.symbol1 = symbol1;
        this.symbol2 = symbol2;
        this.symbol3 = symbol3;
        this.howManySymbols = 3;
    }

    /**
     * Constructs a new SymbolObjectiveCard object with two symbols and points.
     *
     * @param symbol1 The first symbol required for the objective.
     * @param symbol2 The second symbol required for the objective.
     * @param points  The points awarded for completing the objective.
     */
    public SymbolObjectiveCard(int id, int points, Symbol symbol1, Symbol symbol2) {
        super(id, points);
        this.symbol1 = symbol1;
        this.symbol2 = symbol2;
        this.howManySymbols = 2;
    }

    /**
     * Checks if two Symbol Objective cards are equal, to do that it checks the conditions that are in ObjectiveCard.equals(Object o)
     * and the symbols
     *
     * @param o is the Object to check
     * @return true if the two Symbol Objective cards are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        return super.equals(o) && (this.symbol1 == ((SymbolObjectiveCard) o).symbol1 && this.symbol2 == ((SymbolObjectiveCard) o).symbol2 && this.symbol3 == ((SymbolObjectiveCard) o).symbol3 && this.howManySymbols == ((SymbolObjectiveCard) o).howManySymbols);
    }

    /**
     * Check how many times the condition in the board passed as a parameter is respected
     *
     * @param board that must comply with the condition
     * @return number of times the condition is met
     */
    @Override
    public int checkCondition(Board board) {

        int count = 0;  //counter how many times the condition is met


        int c1 = board.countResources(symbol1);     // how many for the 1st symbol on the board

        if(howManySymbols == 2) {              // two equal symbols card
            count = c1 / 2;
        } else {                                    // three symbols card
            if(symbol1.equals(symbol2) && symbol3.equals(symbol2)) {               // equals
                count = c1 / 3;
            }
            else {                                      // different
                int c2 = board.countResources(symbol2);
                int c3 = board.countResources(symbol3);
                count = Math.min(Math.min(c1, c2), c3);
            }
        }

        return count;
    }
}
