package it.polimi.ingsw.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Controller for handling the welcome screen. This controller manages the nickname input and validation,
 * as well as transitioning to the next screen upon valid input.
 */
public class WelcomeController extends GenericController {

    @FXML
    private TextField TopMessage;

    @FXML
    public TextField textNickName;

    /**
     * Handles the action when the Enter key is pressed. Validates the nickname and notifies the listener if valid.
     *
     * @param event the action event
     * @throws IOException if an I/O error occurs
     */
    @FXML
    void handleEnterPressed(ActionEvent event) throws IOException {
        String regex = ".*[a-zA-Z0-9]+.*";
        if (!Pattern.matches(regex, textNickName.getText())) { // If the text doesn't meet the requirements
            textNickName.setStyle("-fx-border-color: red; -fx-text-fill: red;"); // Color the border and text of the textfield
            return;
        }

        if (textNickName.getText().isEmpty()) {
            return;
        }
        // Notify the listener with the entered string
        if (eventListenerGUI != null) {
            eventListenerGUI.onStringReceived(textNickName.getText());
        }
    }

    /**
     * Handles the key pressed event. Resets the text field style when a key is pressed.
     *
     * @param keyEvent the key event
     */
    @FXML
    public void keyPressed(javafx.scene.input.KeyEvent keyEvent) {
        if (!(keyEvent.getCode() == KeyCode.ENTER)) {
            textNickName.setStyle("-fx-border-color: black; -fx-text-fill: black;"); // Color the border and text of the textfield
        }
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
