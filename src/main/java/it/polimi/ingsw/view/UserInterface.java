package it.polimi.ingsw.view;

import it.polimi.ingsw.listener.Event;

/**
 * The UserInterface interface defines the methods that any user interface must implement
 * to interact with the user in the game application.
 *
 * @author Marco Ferraresi, Fabio Marco Floris
 */
public interface UserInterface {

    /**
     * Displays a text message to the user.
     *
     * @param text the text message to be displayed
     */
    void printText(String text);

    /**
     * Displays the title and credits of the game.
     */
    void printTitleCredits();
    /**
     * Displays the game board to the user.
     *
     * @param event the event containing the state of the game board
     */
    void printBoard(Event event);

    /**
     * Displays the player's hand.
     *
     * @param event the event containing the player's hand information
     */
    void printHand(Event event);
    /**
     * Prompts the user to choose a nickname.
     * The chose nickname will be added to the event
     *
     * @param event the event in which to insert the chose nickname
     */
    void chooseNickname(Event event);
    /**
     * Prompts the user to connect to an existing lobby or create a new one.
     *
     * @param event the event in which to insert the response about lobby connection or creation
     */
    void chooseConnectOrCreateLobby(Event event);
    /**
     * Prompts the user to select the number of players for the game.
     *
     * @param event the event in which to insert the information about the number of players
     */
    void selectNumbersPlayers(Event event);
    /**
     * Displays the match ID to the user.
     *
     * @param event the event in which to insert the match ID
     */
    void idMatch(Event event);
    /**
     * Prompts the user to join a game lobby.
     *
     * @param event the event in which to insert information for joining a lobby
     */
    void joinLobby(Event event);
    /**
     * Notifies the user when a player joins the game.
     *
     * @param event the event containing information about the player who joined
     */
    void playerJoined(Event event);
    /**
     * Prompts the user to choose a game token.
     *
     * @param event the event in which to insert the chose token
     */
    void chooseToken(Event event);
    /**
     * Prompts the user to choose the starter face for the game.
     *
     * @param event the event containing information for choosing the starter face
     */
    void chooseFaceStarter(Event event);
    /**
     * Prompts the user to choose their objectives for the game.
     *
     * @param event the event containing information for choosing objectives
     */
    void chooseObjectives(Event event);
    /**
     * Visualize the objective cards set by the game server.
     *
     * @param event the event containing the objective cards
     */
    void sendObjectives(Event event);
    /**
     * Prompts the user to choose a card to play.
     *
     * @param event the event containing information for choosing a play card
     */
    void choosePlayCard(Event event);
    /**
     * Prompts the user to choose between the front or back of a card.
     *
     * @param event the event containing information for making the choice
     */
    void chooseFrontOrBack(Event event);
    /**
     * Prompts the user to choose a position on the board.
     *
     * @param event the event containing information for choosing a position
     */
    void choosePosition(Event event);
    /**
     * Prompts the user to choose a card to draw.
     *
     * @param event the event containing information for choosing a card to draw
     */
    void chooseDrawCard(Event event);
    /**
     * Provides the user with data to reconnect to the game.
     *
     * @param event the event containing the data for the player to reconnect
     */
    void reconnectPlayerData(Event event);
}
