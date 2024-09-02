package it.polimi.ingsw.model;

import it.polimi.ingsw.jsonData.DataParser;
import it.polimi.ingsw.model.cards.GoldCard;
import it.polimi.ingsw.model.cards.ResourceCard;
import it.polimi.ingsw.model.objectives.ObjectiveCard;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a match of the game.
 *
 * @author Marco Ferraresi, Luca Gritti
 */

public class Model {
    private int currentPlayerIndex;  // it's the array Index!
    private ArrayList<Player> round;
    private PlayField playfield;
    private ArrayList<Token> tokens;
    private ArrayList<Integer> startDeck = DataParser.getStarterCards();


    /**
     * Constructs a new Match object with the specified ID and round of players.
     *
     * @param round the list of players participating in the match
     */

    public Model(ArrayList<Player> round){
        this.currentPlayerIndex = 0;
        this.round = round;
        this.playfield = null; // to be initialized!
        tokens = new ArrayList<>();
        Token[] values = Token.values();
        tokens.addAll(Arrays.asList(values).subList(0, values.length - 1));
    }

    /**
     * Retrieves a player by their nickname.
     *
     * @param nickname the nickname of the player
     * @return the player with the specified nickname, or null if not found
     */
    public Player getPlayerByNickName(String nickname){
        for(Player player : round){
            if(player.getNickName().equals(nickname))
                return player;
        }
        return null;
    }

    /**
     * Initializes the playfield with the given decks of resource, gold, and objective cards.
     *
     * @param resDeck the deck of resource cards
     * @param golDeck the deck of gold cards
     * @param objDeck the deck of objective cards
     */
    public void initializePlayField(ArrayList<ResourceCard> resDeck, ArrayList<GoldCard> golDeck, ArrayList<ObjectiveCard> objDeck){
        this.playfield = new PlayField(resDeck, golDeck, objDeck);
    }

    /**
     * Returns the playfield.
     *
     * @return the playfield
     */
    public PlayField getPlayfield() {
        return playfield;
    }

    /**
     * Returns the list of players in the current round.
     *
     * @return the list of players in the round
     */
    public ArrayList<Player> getRound() {
        return round;
    }

    /**
     * Sets the list of players in the current round.
     *
     * @param round the new list of players in the round
     */
    public void setRound(ArrayList<Player> round) {
        this.round = round;
    }

    /**
     * Returns the current player.
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return round.get(currentPlayerIndex);
    }

    /**
     * Changes the turn to the next player in the round.
     */
    public void changeTurn(){
        currentPlayerIndex = (currentPlayerIndex + 1) % round.size();
    }


    /**
     * Returns the list of available tokens.
     *
     * @return the list of available tokens
     */
    public ArrayList<Token> getAvailableTokens() {
        return  tokens;
    }

    /**
     * Returns the list of available starter cards.
     *
     * @return the list of available starter cards
     */
    public ArrayList<Integer> getAvailableStarters() {
        return startDeck;
    }
}
