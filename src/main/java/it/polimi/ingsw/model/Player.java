package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.UnplayableCardException;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.objectives.ObjectiveCard;

import java.util.ArrayList;
import java.util.Objects;


/**
 * Class Player: represents a player in the game.
 * <p>
 * This class contains information about a player, such as their unique ID, nickname, token color, score, objectives, cards in hand, and board state.
 * Players can choose cards to play, draw cards, update their score, and play cards on the game board.
 *
 * @author Marco Ferraresi, Luca Gritti, Angelo De Nadai
 */

public class Player {
    private final String nickName; //player nickname
    private Token token; //player token color
    private Token firstPlayerToken;
    private int points; //player score
    private ObjectiveCard objectiveCard;
    private ArrayList<PlayableCard> cardsInHand;
    private Board board;
    private int count; // final count of objective card patterns
    private int index;

    /**
     * Constructs a new Player object.
     *
     * @param nickName Player's nickname.
     */
    public Player(String nickName) {
        this.nickName = nickName;
        this.objectiveCard = null;
        this.token = null;
        this.points = 0;
        this.board = new Board();
        this.cardsInHand = new ArrayList<>();
        this.count = 0;
        this.firstPlayerToken = null;
    }

    /**
     * Retrieves the personal Objective card.
     *
     * @return the player's Objective card
     */
    public ObjectiveCard getObjectiveCard() {
        return objectiveCard;
    }

    /**
     * Set the personal Objective card.
     *
     * @param objectiveCard to be set
     */
    public void setObjectiveCard(ObjectiveCard objectiveCard) {
        this.objectiveCard = objectiveCard;
    }

    /**
     * Set the player's token.
     *
     * @param token to be set
     */
    public void setToken(Token token) {
        this.token = token;
    }

    /**
     * Retrieves the player's token.
     *
     * @return the player's token
     */
    public Token getToken() {
        return token;
    }

    /**
     * Set the black token if this player is the first one.
     *
     * @param firstPlayerToken is the black token to set
     */
    public void setFirstPlayerToken(Token firstPlayerToken) {
        this.firstPlayerToken = firstPlayerToken;
    }

    /**
     * Retrieves the black token if this player is the first one.
     *
     * @return the black token if this player is the first one, null otherwise
     */
    public Token getFirstPlayerToken() {
        return firstPlayerToken;
    }

    /**
     * Place the Starter card on the board.
     *
     * @param card is the Starter card to set
     */
    public void placeStarterOnBoard(StarterCard card){
        board.cardPlacement(card, new Coordinate(board.getDim() / 2, board.getDim() / 2));   // Starter placed in the middle
    }

    /**
     * Retrieves the player's Starter card.
     *
     * @return the player's Starter card
     */
    public StarterCard getStarterCard(){
        PlayableCard starter = board.getCard(new Coordinate(board.getDim() / 2, board.getDim() / 2));
        if(starter instanceof StarterCard)
            return (StarterCard) starter;
        return null;
    }

    /**
     * Retrieves the player's board.
     *
     * @return the player's board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Retrieves the list of cards currently in the player's hand.
     *
     * @return An ArrayList containing the cards in the player's hand.
     */
    public ArrayList<PlayableCard> getCardsInHand() {
        return cardsInHand;
    }

    /**
     * Adds a card to the player's hand.
     *
     * @param cardToDraw The card to add to the player's hand.
     */
    public void draw(PlayableCard cardToDraw) {
        cardsInHand.add(index, cardToDraw);
    }

    /**
     * Retrieves the player's current score.
     *
     * @return The player's current score.
     */
    public int getPlayerPoints() {
        return points;
    }

    /**
     * Set player's points.
     *
     * @param points to set
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Updates the player's score by adding the specified points.
     *
     * @param points Points to add to the player's score.
     */
    public void updatePlayerPoints(int points) {
        this.points += points;
    }

    /**
     * Retrieves the player's nickname.
     *
     * @return The player's nickname.
     */
    public String getNickName() {
        return nickName;
    }


    /**
     * Add the card to the board, given the coordinate, and update the cells near
     * the placed card, it also updates the resources.
     *
     * @param coord Coordinate for adding the card to the board
     * @param card  PlayableCard to play on the board
     * @throws UnplayableCardException if the gold card can't be played
     */
    public void playCard(PlayableCard card, Coordinate coord) throws UnplayableCardException {
        if(card instanceof GoldCard && card.getSide() == Face.FRONT) {
            if(((GoldCard) card).checkCondition(board) == 0){
                throw new UnplayableCardException("You can't play this card");
            }
        }

        board.cardPlacement(card, coord);
        index = cardsInHand.indexOf(card);
        cardsInHand.remove(card);

        if (card instanceof GoldCard && card.getSide() == Face.FRONT) {
            points += ((GoldCard) card).checkPoints(board, coord);
        } else if (card instanceof ResourceCard && card.getSide() == Face.FRONT) {
            points += ((ResourceCard) card).getPoints();
        }
    }

    /**
     * Checks if two players are equal, to do that it checks the nickname and the points.
     *
     * @param obj is the Object to check
     * @return true if the two players are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        Player o = (Player) obj;
        return (Objects.equals(this.nickName, o.nickName) && this.points == o.points);
    }

    /**
     * Set the amount of objectives cards completed.
     *
     * @param count is the number of objectives cards completed
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Retrieves the amount of objectives cards completed.
     *
     * @return the number of objectives cards completed
     */
    public int getCount() {
        return this.count;
    }

}