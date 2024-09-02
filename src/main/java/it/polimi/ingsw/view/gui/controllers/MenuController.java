package it.polimi.ingsw.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Controller for handling the main menu screen. This controller manages the actions for creating a game,
 * joining a game, exiting the application, and displaying the player's name.
 */
public class MenuController extends GenericController {

    @FXML
    private TextField TopMessage;

    @FXML
    private Button CreateButton;

    @FXML
    private Button ExitButton;

    @FXML
    private Button JoinButton;

    @FXML
    private Pane content;

    @FXML
    private AnchorPane scenePane;

    @FXML
    private Pane CreateGamePane;

    @FXML
    private Text PlayerName;

    /**
     * Handles the action of creating a game.
     *
     * @param event the action event
     */
    @FXML
    void actionCreateGame(ActionEvent event) {
        if (eventListenerGUI != null) {
            eventListenerGUI.onStringReceived("1");
        }
    }

    /**
     * Handles the action of joining a game.
     *
     * @param event the action event
     */
    @FXML
    void actionJoinGame(ActionEvent event) {
        // Notify the listener with the entered string
        if (eventListenerGUI != null) {
            eventListenerGUI.onStringReceived("2");
        }
    }

    private Stage stage;

    /**
     * Handles the action of exiting the application.
     *
     * @param event the action event
     */
    @FXML
    void actionExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You're about to exit!");
        alert.setContentText("Are you sure you want to exit?: ");

        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) scenePane.getScene().getWindow();
            System.out.println("You successfully exit!");
            stage.close();
            System.exit(0);
        }
    }

    /**
     * Sets the player's name in the menu.
     *
     * @param playerName the player's name
     */
    public void setPlayerName(String playerName) {
        PlayerName.setText("Player: " + playerName);
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
