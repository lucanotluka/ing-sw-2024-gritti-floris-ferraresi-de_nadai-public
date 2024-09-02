package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Coordinate;
import it.polimi.ingsw.model.objectives.Condition;

import java.util.Map;

/**
 * Instance of Playable Card, represents a gold card in the game.
 *
 * @author Fabio Marco Floris
 */
public class GoldCard extends PlayableCard implements Condition {
    private int points;                             // Points to be multiplied
    private final PointsType pointsType;                  // How points are multiplied
    private final Map<Symbol, Integer> condition;         // Symbols in the placement condition

    /**
     * Constructs a Gold Card with only three corners.
     *
     * @param cardId      The ID of the card.
     * @param first       The first corner.
     * @param second      The second corner.
     * @param third       The third corner.
     * @param points      The points to be multiplied.
     * @param symbol      The symbol of the card.
     * @param pointsType  The method of multiplying points.
     * @param condition   The placement condition.
     */
    public GoldCard(int cardId, Corner first, Corner second, Corner third, int points, Symbol symbol, PointsType pointsType, Map<Symbol, Integer> condition) {
        super(cardId, symbol, first, second, third);
        this.points = points;
        this.pointsType = pointsType;
        this.condition = condition;
    }

    /**
     * Constructs a Gold Card with only two corners.
     *
     * @param cardId      The ID of the card.
     * @param first       The first corner.
     * @param second      The second corner.
     * @param points      The points to be multiplied.
     * @param symbol      The symbol of the card.
     * @param pointsType  The method of multiplying points.
     * @param condition   The placement condition.
     */
    public GoldCard(int cardId, Corner first, Corner second, int points, Symbol symbol, PointsType pointsType, Map<Symbol, Integer> condition) {
        super(cardId, symbol, first, second);
        this.points = points;
        this.pointsType = pointsType;
        this.condition = condition;
    }


    /**
     * Checks and calculates the points based on the board, card's points type, and coordinate.
     *
     * @param board   The game board.
     * @param coord   The coordinate to check points for.
     * @return The calculated points.
     */
    public int checkPoints(Board board, Coordinate coord) {
        switch (pointsType) {
            case NULL:
                return points;
            case CORNERS:
                // Count corners
                int sum = 0;

                if (board.existsPosition(new Coordinate(coord.getX() - 1, coord.getY() - 1))) {
                    sum++;
                }
                if (board.existsPosition(new Coordinate(coord.getX() - 1, coord.getY() + 1))) {
                    sum++;
                }
                if (board.existsPosition(new Coordinate(coord.getX() + 1, coord.getY() - 1))) {
                    sum++;
                }
                if (board.existsPosition(new Coordinate(coord.getX() + 1, coord.getY() + 1))) {
                    sum++;
                }
                return points * sum;
            case INKWELL:
                return (points * board.countResources(Symbol.INKWELL));
            case QUILL:
                return (points * board.countResources(Symbol.QUILL));
            case MANUSCRIPT:
                return (points * board.countResources(Symbol.MANUSCRIPT));
        }
        return 0;
    }

    /**
     * Check if the board has the required symbols to place the card
     *
     * @param board that must comply with the condition
     * @return 1:the board comply with the condition, 0: the board does not comply with the condition
     */

    @Override
    public int checkCondition(Board board) {
        for (Symbol symbol : condition.keySet()) {
            if (board.countResources(symbol) < condition.get(symbol))
                return 0;
        }
        return 1;
    }

    /**
     * Checks if two gold cards are equal, to do that it checks the conditions that are in PlayableCard.equals(Object o),
     * the points, the points type and the condition.
     *
     * @param o is the Object to check
     * @return true if the two gold cards are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        return  super.equals(o) &&
                this.points== ((GoldCard) o).points &&
                this.pointsType == ((GoldCard) o).pointsType &&
                this.condition.equals(((GoldCard) o).condition);
    }

}
