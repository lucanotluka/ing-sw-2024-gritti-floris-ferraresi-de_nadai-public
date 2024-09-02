package it.polimi.ingsw.view.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * Controller for handling the lobby screen. This controller manages the display of player names,
 * game ID, and the visibility of panes in the lobby.
 */
public class LobbyController extends GenericController {

    @FXML
    private Pane content;

    @FXML
    private Text gameidLabel;

    @FXML
    private Text nicknameLabel;

    @FXML
    private Pane pane0;

    @FXML
    private Pane pane1;

    @FXML
    private Pane pane2;

    @FXML
    private Pane pane3;

    @FXML
    private TextField TopMessage;

    /**
     * Prints a message at the top of the screen.
     *
     * @param text the message text to print
     */
    @Override
    public void printTextTopMessage(String text) {
        TopMessage.setText(text);
    }

    /**
     * Sets the player's nickname in the lobby.
     *
     * @param text the player's nickname
     */
    public void setPlayerName(String text) {
        nicknameLabel.setText(text);
    }

    /**
     * Sets the game ID in the lobby.
     *
     * @param text the game ID
     */
    public void setGameID(String text) {
        gameidLabel.setText("GameID: " + text);
    }

    /**
     * Hides all the player panes in the lobby.
     */
    public void hidePanes() {
        pane0.setVisible(false);
        pane1.setVisible(false);
        pane2.setVisible(false);
        pane3.setVisible(false);
    }

}
