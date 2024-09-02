package it.polimi.ingsw.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.List;

/**
 * Controller for handling the game end screen. This controller manages displaying the scoreboard
 * and returning to the menu.
 */
public class GameEndController extends GenericController {

    @FXML
    private TextField TopMessage;

    @FXML
    private Button btnMenu;

    @FXML
    private Pane content;

    @FXML
    private Label player0;

    @FXML
    private Label player1;

    @FXML
    private Label player2;

    @FXML
    private Label player3;

    /**
     * Handles the action of returning to the menu.
     *
     * @param event the action event
     */
    @FXML
    void actionReturnMenu(ActionEvent event) {
        eventListenerGUI.onStringReceived("");
    }

    /**
     * Sets the scoreboard with the players' nicknames and their corresponding points.
     *
     * @param nicknames a list of player nicknames
     * @param points    a list of player points
     */
    public void setScoreBoard(List<String> nicknames, List<Integer> points) {
        player0.setText(nicknames.get(0) + " - " + points.get(0) + " points");
        player0.setVisible(true);
        player1.setText(nicknames.get(1) + " - " + points.get(1) + " points");
        player1.setVisible(true);
        if (nicknames.size() == 2)
            return;
        player2.setText(nicknames.get(2) + " - " + points.get(2) + " points");
        player2.setVisible(true);
        if (nicknames.size() == 3)
            return;
        player3.setText(nicknames.get(3) + " - " + points.get(3) + " points");
        player3.setVisible(true);
    }

    /**
     * Prints a message at the top of the screen.
     *
     * @param text the message text to print
     */
    @Override
    public void printTextTopMessage(String text) {
        TopMessage.setText(text);
    }

}
