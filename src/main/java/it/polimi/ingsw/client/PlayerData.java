package it.polimi.ingsw.client;

import it.polimi.ingsw.listener.EventType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages data related to a player within the game.
 * This class holds information about objectives, cards, token points, and various other player-specific data points.
 * Being a static class, it allows global access within the application to manipulate and retrieve player data.
 *
 * @author Fabio Marco Floris, Luca Gritti, Angelo De Nadai
 */
public class PlayerData {
    private static EventType gameStatus;
    private static int personalObj = -1;
    private static int playfieldObj1;
    private static int playfieldObj2;
    private static List<Integer> cards = new ArrayList<>();
    private static Map<Integer, String> faces = new HashMap<>();
    private static String token;
    private static HashMap<String, Integer> tokenPoints = new HashMap<>();
    private static int option = -1;

    /**
     * Sets the list of cards held by the player.
     *
     * @param cards the list of card IDs
     */
    public static void setCards(List<Integer> cards) {
        PlayerData.cards = cards;
    }

    /**
     * Sets the map of faces associated with card IDs.
     *
     * @param faces a map where keys are card IDs and values are descriptions of faces
     */
    public static void setFaces(Map<Integer, String> faces) {
        PlayerData.faces = faces;
    }

    /**
     * Retrieves the personal objective ID of the player.
     *
     * @return the personal objective ID
     */
    public static int getPersonalObj() {
        return personalObj;
    }

    /**
     * Sets the personal objective ID for the player.
     *
     * @param personalObj the new personal objective ID
     */
    public static void setPersonalObj(int personalObj) {
        PlayerData.personalObj = personalObj;
    }

    /**
     * Retrieves the first playfield objective ID.
     *
     * @return the first playfield objective ID
     */
    public static int getPlayfieldObj1() {
        return playfieldObj1;
    }

    /**
     * Sets the first playfield objective ID.
     *
     * @param playfieldObj1 the first playfield objective ID
     */
    public static void setPlayfieldObj1(int playfieldObj1) {
        PlayerData.playfieldObj1 = playfieldObj1;
    }

    /**
     * Retrieves the second playfield objective ID.
     *
     * @return the second playfield objective ID
     */
    public static int getPlayfieldObj2() {
        return playfieldObj2;
    }

    /**
     * Sets the second playfield objective ID.
     *
     * @param playfieldObj2 the second playfield objective ID
     */
    public static void setPlayfieldObj2(int playfieldObj2) {
        PlayerData.playfieldObj2 = playfieldObj2;
    }

    /**
     * Retrieves the current token of the player.
     *
     * @return the current token string
     */
    public static String getToken() {
        return token;
    }

    /**
     * Sets the player's token.
     *
     * @param token the new token string
     */
    public static void setToken(String token) {
        PlayerData.token = token;
    }

    /**
     * Adds a card to the player's list if it's not already included.
     *
     * @param id the card ID to add
     */
    public static void addCard(int id) {
        if (!cards.contains(id))
            cards.add(id);
    }

    /**
     * Checks if a specified card ID is valid (i.e., is present in the player's card list).
     *
     * @param id the card ID to check
     * @return true if the card is present, false otherwise
     */
    public static boolean isValidCard(int id) {
        return cards.contains(id);
    }

    /**
     * Sets a face description for a specified card.
     *
     * @param id the card ID
     * @param face the face description to set
     */
    public static void setFace(int id, String face) {
        faces.put(id, face);
    }

    /**
     * Retrieves the face description associated with a specified card ID.
     *
     * @param id the card ID
     * @return the face description, or null if not found
     */
    public static String getFace(int id) {
        return faces.get(id);
    }

    /**
     * Returns the current game status as an {@code EventType}.
     *
     * @return the game status
     */
    public static EventType getGameStatus() {
        return gameStatus;
    }

    /**
     * Sets the game status to a specified event type.
     *
     * @param eventType the event type representing the new game status
     */
    public static void setGameStatus(EventType eventType) {
        PlayerData.gameStatus = eventType;
    }

    /**
     * Adds points to a token within the token points map.
     *
     * @param token the token string
     * @param points the points to add
     */
    public static void addTokenPoints(String token, int points) {
        tokenPoints.put(token, points);
    }

    /**
     * Retrieves the current option setting.
     *
     * @return the current option value
     */
    public static int getOption() {
        return option;
    }

    /**
     * Sets the option value.
     *
     * @param option the new option value
     */
    public static void setOption(int option) {
        PlayerData.option = option;
    }

    /**
     * Retrieves the token points map.
     *
     * @return the token points map
     */
    public static HashMap<String, Integer> getTokenPoints() {
        return tokenPoints;
    }

    /**
     * Clears all player data, resetting to initial state.
     */
    public static void clean() {
        gameStatus = null;
        personalObj = -1;
        playfieldObj1 = -1;
        playfieldObj2 = -1;
        cards.clear();
        faces.clear();
        token = null;
        tokenPoints.clear();
        option = -1;
    }
}
