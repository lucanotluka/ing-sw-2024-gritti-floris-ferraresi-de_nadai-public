package it.polimi.ingsw.model.cards;

/**
 * Represents a Resource Card in the game.
 *
 * @author Fabio Marco Floris
 */
public class ResourceCard extends PlayableCard {
    private int points;         // The points associated with the card

    /**
     * Constructs a Resource Card with only three corners.
     *
     * @param cardId  The ID of the card.
     * @param symbol  The symbol of the card.
     * @param points  The points associated with the card.
     * @param first   The first corner.
     * @param second  The second corner.
     * @param third   The third corner.
     */
    public ResourceCard(int cardId, Symbol symbol, int points, Corner first, Corner second, Corner third) {
        super(cardId, symbol, first, second, third);
        this.points = points;
    }

    /**
     * Constructs the only Resource Card with all four corners.
     *
     * @param cardId  The ID of the card.
     * @param symbol  The symbol of the card.
     * @param points  The points associated with the card.
     * @param first   The first corner.
     * @param second  The second corner.
     * @param third   The third corner.
     */
    public ResourceCard(int cardId, Symbol symbol, int points, Corner first, Corner second, Corner third, Corner fourth) {
        super(cardId, symbol, first, second, third, fourth);
        this.points = points;
    }

    /**
     * Retrieves the points associated with the card.
     *
     * @return The points associated with the card.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Checks if two resource cards are equal, to do that it checks the conditions that are in PlayableCard.equals(Object o)
     * and the points.
     *
     * @param o is the Object to check
     * @return true if the two resource cards are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        return  super.equals(o) &&
                this.points == ((ResourceCard) o).points;
    }
}
