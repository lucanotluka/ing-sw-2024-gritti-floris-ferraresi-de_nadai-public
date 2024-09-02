package it.polimi.ingsw.listener;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Event class represents the type of event that a player can create by doing some choices in the view,
 * the controller that is listening takes an action based on the event received.
 *
 * @author Marco Ferraresi, Fabio Marco Floris
 */
public class Event implements Serializable {
    private String nickName;
    private EventType event;
    private String parameter;    // parameter
    private int n1, n2, n3, n4, n5, n6;
    private int[][][] board;
    private ArrayList<String> tokens;
    private ArrayList<String> players;

    /**
     * Constructs a new event.
     *
     * @param event is the type of the event
     * @param nickName is the nickname of the player
     */
    public Event(EventType event, String nickName) {
        this.event = event;
        this.nickName = nickName;
    }

    /**
     * Return the nicknames of the players.
     *
     * @return the nicknames
     */
    public ArrayList<String> getPlayers() {
        return players;
    }

    /**
     * Set the nicknames of the players.
     *
     * @param players to be setting
     */
    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }

    /**
     * Return the board.
     *
     * @return the board
     */
    public int[][][] getBoard() {
        return board;
    }

    /**
     * Set a board.
     *
     * @param board to be setting
     */
    public void setBoard(int[][][] board) {
        this.board = board;
    }

    /**
     * Return a number that represents a card id.
     *
     * @return the card id
     */
    public int getN5() {
        return n5;
    }

    /**
     * Set a number that represents a card id.
     *
     * @param n5 is the card id
     */
    public void setN5(int n5) {
        this.n5 = n5;
    }

    /**
     * Return a number that represents a card id.
     *
     * @return the card id
     */
    public int getN6() {
        return n6;
    }

    /**
     * Set a number that represents a card id.
     *
     * @param n6 is the card id
     */
    public void setN6(int n6) {
        this.n6 = n6;
    }

    /**
     * Return the list of tokens.
     *
     * @return the list of tokens
     */
    public ArrayList<String> getTokens() {
        return tokens;
    }

    /**
     * Set the list of tokens.
     *
     * @param tokens to be setting
     */
    public void setTokens(ArrayList<String> tokens) {
        this.tokens = tokens;
    }

    /**
     * Return a number that represents a card id.
     *
     * @return the card id
     */
    public int getN3() {
        return n3;
    }

    /**
     * Set a number that represents a card id.
     *
     * @param n3 is the card id
     */
    public void setN3(int n3) {
        this.n3 = n3;
    }

    /**
     * Return a number that represents a card id.
     *
     * @return the card id
     */
    public int getN4() {
        return n4;
    }

    /**
     * Set a number that represents a card id.
     *
     * @param n4 is the card id
     */
    public void setN4(int n4) {
        this.n4 = n4;
    }

    /**
     * Return the nickname of the player.
     *
     * @return the nickname of the player
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * Retrieves the string parameter of the event.
     *
     * @return the parameter
     */
    public String getParameter() {
        return parameter;
    }

    /**
     * Sets a string that has to be print.
     *
     * @param parameter is the string to print
     */
    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    /**
     * Retrieves a number that can represents different things:
     * number of players
     * id of the match
     * id of the token
     * id of the card the player wants to play
     * id of the card the player wants to draw
     * the position where to play a card
     *
     * @return a number that can represents different things based on the player's choice
     */
    public int getN1() {
        return n1;
    }

    /**
     * Sets the first number that represents different things.
     *
     * @param n1 the number that represents the player's choice
     */
    public void setN1(int n1) {
        this.n1 = n1;
    }

    /**
     * Retrieves a number that represents a card id.
     *
     * @return the card id
     */
    public int getN2() {
        return n2;
    }

    /**
     * Sets a card id.
     *
     * @param n2 is the card id
     */
    public void setN2(int n2) {
        this.n2 = n2;
    }

    /**
     * Return the type of the event.
     *
     * @return the type of the event
     */
    public EventType getEvent() {
        return event;
    }

}