package it.polimi.ingsw.model.cards;

import java.util.ArrayList;

/**
 * Represents a Starter Card in the game.
 *
 * @author Fabio Marco Floris
 */
public class StarterCard extends PlayableCard {
    private final ArrayList<Symbol> symbols;   // The symbols associated with the card

    /**
     * Constructs a Starter Card with all four corners.
     *
     * @param cardId    The ID of the card.
     * @param symbols   The symbols associated with the card.
     * @param face      The face of the card.
     * @param top_left  The top left corner.
     * @param top_right The top right corner.
     * @param bot_left  The bottom left corner.
     * @param bot_right The bottom right corner.
     */
    public StarterCard(int cardId, ArrayList<Symbol> symbols, Face face, Corner top_left, Corner top_right, Corner bot_left, Corner bot_right) {
        super(cardId, Symbol.EMPTY, top_left, top_right, bot_left, bot_right);
        this.symbols = symbols;
        this.setFace(face);
    }

    /**
     * Constructs a Starter Card with only two corners.
     *
     * @param cardId  The ID of the card.
     * @param symbols The symbols associated with the card.
     * @param face    The face of the card.
     * @param first   The first corner.
     * @param second  The second corner.
     */
    public StarterCard(int cardId, ArrayList<Symbol> symbols, Face face, Corner first, Corner second) {
        super(cardId,Symbol.EMPTY, first, second);
        this.symbols = symbols;
        this.setFace(face);
    }

    /**
     * Retrieves the symbols associated with the card.
     *
     * @return The symbols associated with the card.
     */
    public ArrayList<Symbol> getSymbols() {
        return symbols;
    }

    /**
     * Checks if two starter cards are equal, to do that it checks the conditions that are in PlayableCard.equals(Object o)
     * and if the side is front it also checks the symbols
     *
     * @param o is the Object to check
     * @return true if the two starter cards are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        return  super.equals(o) &&
                (!(this.getSide() == Face.FRONT) || this.symbols.equals(((StarterCard) o).symbols));
    }
}
