package it.polimi.ingsw.model.objectives;

/**
 * Abstract class representing a generic objective card in the game.
 */

public abstract class ObjectiveCard implements Condition {
    private final int id;
    protected final int points; //points that the card gives if the conditions are respected

    /**
     * Constructs an objective card.
     *
     * @param id is the id of the card
     * @param points are the points of the card
     */
    public ObjectiveCard(int id, int points) {
        this.id = id;
        this.points = points;
    }

    /**
     * Retrieves the id of the card.
     *
     * @return the id of the card
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the points of the card.
     *
     * @return the points of the card
     */
    public int getPoints() {
        return points;
    }

    /**
     * Checks if two objective cards are equal, to do that it checks the id and the points.
     *
     * @param o is the Object to check
     * @return true if the two objective cards are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {

        if(this.id == ((ObjectiveCard) o).id && this.points == ((ObjectiveCard) o).points)
            return true;
        return false;
    }
}