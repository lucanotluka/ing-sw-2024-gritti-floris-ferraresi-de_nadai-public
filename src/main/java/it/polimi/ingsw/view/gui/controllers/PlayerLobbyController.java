package it.polimi.ingsw.view.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * Controller for handling the player lobby screen. This controller manages the display of player nicknames
 * and the corresponding panel in the lobby.
 */
public class PlayerLobbyController extends GenericController {

    @FXML
    private Text nicknameLabel;

    @FXML
    private Pane NickPanel;

    /**
     * Sets the player's nickname in the lobby.
     *
     * @param nickname the player's nickname
     */
    public void setNickname(String nickname) {
        nicknameLabel.setText(nickname);
    }

    /**
     * Prints a message at the top of the screen. This method is not used in this controller.
     *
     * @param text the message text to print
     */
    @Override
    public void printTextTopMessage(String text) {
        // No implementation needed for this controller
    }

    /**
     * Gets the panel containing the player's nickname.
     *
     * @return the nickname panel
     */
    public Pane getNickPanel() {
        return NickPanel;
    }
}
