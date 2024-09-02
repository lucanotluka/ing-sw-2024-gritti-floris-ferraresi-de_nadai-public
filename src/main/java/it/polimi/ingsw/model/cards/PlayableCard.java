package it.polimi.ingsw.model.cards;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Abstract class representing a playable card in the game.
 *
 * @author Fabio Marco Floris
 */
public abstract class PlayableCard {
    private final int cardId;      // The ID of the card
    private Face side;             // The current face of the card
    private final Symbol symbol;    // The symbol of the card
    private Corner top_left = null;       // The top left corner of the card
    private Corner top_right = null;      // The top right corner of the card
    private Corner bot_left = null;       // The bottom left corner of the card
    private Corner bot_right = null;      // The bottom right corner of the card

    /**
     * Constructs a PlayableCard with all four corners.
     *
     * @param cardId      The ID of the card.
     * @param symbol      The symbol of the card.
     * @param top_left    The top left corner.
     * @param top_right   The top right corner.
     * @param bot_left    The bottom left corner.
     * @param bot_right   The bottom right corner.
     */
    public PlayableCard(int cardId,Symbol symbol, Corner top_left, Corner top_right, Corner bot_left, Corner bot_right) {
        this.cardId = cardId;
        this.side = Face.FRONT;
        this.symbol = symbol;
        for (Corner corner : new ArrayList<>(Arrays.asList(top_left, top_right, bot_left, bot_right))) {
            switch (corner.getType()) {
                case TOP_LEFT:
                    this.top_left = corner;
                    break;
                case TOP_RIGHT:
                    this.top_right = corner;
                    break;
                case BOT_LEFT:
                    this.bot_left = corner;
                    break;
                case BOT_RIGHT:
                    this.bot_right = corner;
                    break;
            }
        }
    }

    /**
     * Constructs a PlayableCard with only three corners.
     *
     * @param cardId  The ID of the card.
     * @param symbol  The symbol of the card.
     * @param first   The first corner.
     * @param second  The second corner.
     * @param third   The third corner.
     */
    public PlayableCard(int cardId,Symbol symbol, Corner first, Corner second, Corner third) {
        this.cardId = cardId;
        this.side = Face.FRONT;
        this.symbol = symbol;
        for (Corner corner : new ArrayList<>(Arrays.asList(first, second, third))) {
            switch (corner.getType()) {
                case TOP_LEFT:
                    this.top_left = corner;
                    break;
                case TOP_RIGHT:
                    this.top_right = corner;
                    break;
                case BOT_LEFT:
                    this.bot_left = corner;
                    break;
                case BOT_RIGHT:
                    this.bot_right = corner;
                    break;
            }
        }
    }

    /**
     * Constructs a PlayableCard with only two corners.
     *
     * @param cardId  The ID of the card.
     * @param symbol  The symbol of the card.
     * @param first   The first corner.
     * @param second  The second corner.
     */
    public PlayableCard(int cardId,Symbol symbol, Corner first, Corner second) {
        this.cardId = cardId;
        this.side = Face.FRONT;
        this.symbol = symbol;
        for (Corner corner : new ArrayList<>(Arrays.asList(first, second))) {
            switch (corner.getType()) {
                case TOP_LEFT:
                    this.top_left = corner;
                    break;
                case TOP_RIGHT:
                    this.top_right = corner;
                    break;
                case BOT_LEFT:
                    this.bot_left = corner;
                    break;
                case BOT_RIGHT:
                    this.bot_right = corner;
                    break;
            }
        }
    }

    /**
     * Set the card's face to BACK and changes the corners value.
     */
    public void setBack(){
        this.side = Face.BACK;
        top_right = new Corner(CornerType.TOP_RIGHT,Symbol.EMPTY);
        top_left = new Corner(CornerType.TOP_LEFT,Symbol.EMPTY);
        bot_right = new Corner(CornerType.BOT_RIGHT,Symbol.EMPTY);
        bot_left = new Corner(CornerType.BOT_LEFT,Symbol.EMPTY);

    }

    /**
     * Set the face of the card.
     *
     * @param face to be setting
     */
    public void setFace(Face face){
        this.side = face;
    }

    /**
     * Retrieves the ID of the card.
     *
     * @return The ID of the card.
     */
    public int getCardId() {
        return cardId;
    }

    /**
     * Retrieves the current face of the card.
     *
     * @return The current face of the card.
     */
    public Face getSide() {
        return side;
    }

    /**
     * Retrieves the top left corner of the card.
     *
     * @return The top left corner of the card.
     */
    public Corner getTop_left() {
        return top_left;
    }

    /**
     * Retrieves the top right corner of the card.
     *
     * @return The top right corner of the card.
     */
    public Corner getTop_right() {
        return top_right;
    }

    /**
     * Retrieves the bottom left corner of the card.
     *
     * @return The bottom left corner of the card.
     */
    public Corner getBot_left() {
        return bot_left;
    }

    /**
     * Retrieves the bottom right corner of the card.
     *
     * @return The bottom right corner of the card.
     */
    public Corner getBot_right() {
        return bot_right;
    }

    /**
     * Retrieves the symbol of the card.
     *
     * @return The symbol of the card.
     */
    public Symbol getSymbol() {
        return symbol;
    }

    /**
     * Checks if two cards are equal, to do that it checks the id, the symbol, the side of the card and the corners.
     *
     * @param o is the Object to check
     * @return true if the two cards are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if(this.symbol == ((PlayableCard) o).symbol &&
                this.cardId == ((PlayableCard) o).cardId &&
                this.side == ((PlayableCard) o).side &&
                ((this.top_left == null &&
                        ((PlayableCard) o).top_left == null)
                        ||
                        (this.top_left != null &&
                                this.top_left.equals(((PlayableCard) o).top_left))) &&
                ((this.top_right == null &&
                        ((PlayableCard) o).top_right == null)
                        ||
                        (this.top_right != null &&
                                this.top_right.equals(((PlayableCard) o).top_right))) &&
                ((this.bot_left == null &&
                        ((PlayableCard) o).bot_left == null)
                        ||
                        (this.bot_left != null &&
                                this.bot_left.equals(((PlayableCard) o).bot_left))) &&
                ((this.bot_right == null &&
                        ((PlayableCard) o).bot_right == null)
                        ||
                        (this.bot_right != null &&
                                this.bot_right.equals(((PlayableCard) o).bot_right)))){
            return true;
        }

        return false;
    }
}
