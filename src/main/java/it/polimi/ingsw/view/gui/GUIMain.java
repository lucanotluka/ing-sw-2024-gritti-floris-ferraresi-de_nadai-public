package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.client.PlayerData;
import it.polimi.ingsw.listener.Event;
import it.polimi.ingsw.listener.EventType;
import it.polimi.ingsw.view.gui.controllers.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

/**
 * GUIMain is the main class for the graphical user interface (GUI) of the game.
 * It manages the application window, scenes, and controllers, and handles the interaction between the user and the game logic.
 */
public class GUIMain extends Application {

    private Stage primaryStage;
    private GenericController controller;
    private Scene currentScene;
    private Map<String, Scene> scenes = new HashMap<>();
    private Map<String, GenericController> controllers = new HashMap<>();
    private EventListenerGUI eventListenerGUI;
    private static GUIMain instance;
    private static final CountDownLatch latch = new CountDownLatch(1);
    private double widthOld = 1200, heightOld = 750;

    /**
     * Starts the application by initializing the primary stage and loading the initial scene.
     *
     * @param primaryStage the primary stage of the application
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        instance = this;

        loadScene("Welcome", "/it/polimi/ingsw/Welcome.fxml");
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Codex Naturalis");
        ((WelcomeController) controllers.get("Welcome")).textNickName.setVisible(false); // Hide the TextField
        setScene("Welcome");

        // Listener for resizing
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            currentScene.getRoot().requestLayout();
        });
        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            currentScene.getRoot().requestLayout();
        });
        primaryStage.setOnCloseRequest(e -> {  // When X is pressed on the window
            e.consume();
            exit(primaryStage);
        });
        widthOld = primaryStage.getScene().getWidth();
        heightOld = primaryStage.getScene().getHeight();
        this.primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            rescale((double) newVal, heightOld);
        });

        this.primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            rescale(widthOld, (double) newVal-30);
        });
        latch.countDown();
    }

    /**
     * Rescales the scene based on the new width and height of the window.
     *
     * @param width  the new width of the window
     * @param height the new height of the window
     */
    public void rescale(double width, double height) {
        double w = width / widthOld;  // your window width
        double h = height / heightOld;  // your window height

        widthOld = width;
        heightOld = height;
        Scale scale = new Scale(w, h, 0, 0);
        primaryStage.getScene().lookup("#content").getTransforms().add(scale);
    }

    /**
     * Returns the singleton instance of GUIMain.
     *
     * @return the singleton instance of GUIMain
     * @throws InterruptedException if the current thread is interrupted while waiting
     */
    public static GUIMain getInstance() throws InterruptedException {
        latch.await();
        return instance;
    }

    /**
     * Sets the event listener for GUI events.
     *
     * @param listener the event listener to set
     */
    public void setEventListenerGUI(EventListenerGUI listener) {
        this.eventListenerGUI = listener;
    }

    /**
     * Loads all the scenes needed for the application.
     *
     * @throws IOException if an I/O error occurs
     */
    private void loadScenes() throws IOException {
        loadScene("Menu", "/it/polimi/ingsw/Menu.fxml");
        loadScene("Lobby", "/it/polimi/ingsw/Lobby.fxml");
        loadScene("InGame", "/it/polimi/ingsw/InGame.fxml");
        loadScene("EndGame", "/it/polimi/ingsw/EndGame.fxml");
        loadScene("PlayerLobby1", "/it/polimi/ingsw/PlayerLobby1.fxml");
        loadScene("PlayerLobby2", "/it/polimi/ingsw/PlayerLobby2.fxml");
        loadScene("PlayerLobby3", "/it/polimi/ingsw/PlayerLobby3.fxml");
        loadScene("PlayerLobby4", "/it/polimi/ingsw/PlayerLobby4.fxml");
    }

    /**
     * Loads a specific scene from the specified resource file.
     *
     * @param name     the name of the scene
     * @param resource the resource file of the scene
     * @throws IOException if an I/O error occurs
     */
    private void loadScene(String name, String resource) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        GenericController controller = fxmlLoader.getController();
        if (controller != null)
            controller.setEventListenerGUI(eventListenerGUI);
        scenes.put(name, scene);
        controllers.put(name, controller);
    }

    /**
     * Sets the current scene by name.
     *
     * @param name the name of the scene to set
     */
    public void setScene(String name) {
        Scene scene = scenes.get(name);
        if (scene != null) {
            setScene(scene);
            controller = controllers.get(name);
        }
    }

    /**
     * Sets the current scene.
     *
     * @param scene the scene to set
     */
    public void setScene(Scene scene) {
        double width = widthOld, height = heightOld;
        if (primaryStage != null) {
            if (currentScene != null) {
                primaryStage.setWidth(1200);
                primaryStage.setHeight(780);
            }
            currentScene = scene;
            primaryStage.setScene(scene);
            primaryStage.setWidth(width);
            primaryStage.setHeight(height+29);
            primaryStage.show();
        }
    }

    /**
     * Prints the title credits on the GUI.
     */
    public void printTitleCredits() {
        controllers.get("Welcome").setEventListenerGUI(eventListenerGUI); // Set EventListenerGUI in Welcome's Controller because it has to be done after the launch() method
        try {
            loadScenes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Prompts the user to choose a nickname.
     *
     * @param event the event triggering the nickname choice
     */
    public void chooseNickname(Event event) {
        if (controller instanceof WelcomeController) {
            ((WelcomeController) controller).textNickName.setVisible(true); // Show TextField
            if (!((WelcomeController) controller).textNickName.getText().isEmpty()) {    // In case the server calls the method after the client has already written the nickname
                ((WelcomeController) controller).textNickName.setStyle("-fx-border-color: red; -fx-text-fill: red;");// Color the border and the text of the textfield
            }
        }
    }

    /**
     * Sets the scene to the menu for choosing to connect or create a lobby.
     *
     * @param event the event triggering the choice
     */
    public void chooseConnectOrCreateLobby(Event event) {
        setScene("Menu");
    }

    /**
     * Prompts the user to select the number of players.
     */
    public void selectNumbersPlayers() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Number of player Insertion");
        dialog.setHeaderText("Enter Number of Player");
        dialog.setContentText("Number: ");

        Optional<String> result;
        boolean validNumber = false;

        do {
            result = dialog.showAndWait(); // Capture the response value
            if (result.isPresent()) {
                String input = result.get();
                if (input.matches("[2-4]")) {
                    validNumber = true;
                    PlayerData.setOption(Integer.parseInt(input));
                    eventListenerGUI.onStringReceived("1");
                } else {
                    // If the number is not valid, show the dialog again
                    dialog.setHeaderText("Invalid number! Enter Number of Player (2-4)");
                }
            } else {
                PlayerData.setGameStatus(EventType.CHOOSE_CONNECT_OR_CREATE_LOBBY);
                eventListenerGUI.onStringReceived("0");
                return;
            }
        } while (!validNumber);
    }

    /**
     * Prompts the user to input a game ID.
     */
    public void idMatch() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("GameID Insertion");
        dialog.setHeaderText("Enter GameID");
        dialog.setContentText("GameID: ");

        Optional<String> result;
        boolean validGameID = false;

        do {
            result = dialog.showAndWait(); // Capture the response value
            if (result.isPresent()) {
                String input = result.get();
                if (input.matches("\\d+")) {
                    validGameID = true;
                    PlayerData.setOption(Integer.parseInt(input));
                    eventListenerGUI.onStringReceived("1");
                } else {
                    // If the GameID is not valid, show the dialog again with an error message
                    dialog.setHeaderText("Invalid GameID! Enter a numeric GameID");
                }
            } else {
                PlayerData.setGameStatus(EventType.CHOOSE_CONNECT_OR_CREATE_LOBBY);
                eventListenerGUI.onStringReceived("0");
                return;
            }
        } while (!validGameID);
    }

    /**
     * Prompts the user to choose a token.
     *
     * @param event the event triggering the token choice
     */
    public void chooseToken(Event event) {
        setScene("InGame");
        ((InGameController) controller).ChooseTokenDialog.setVisible(true);

        String input = event.getParameter();
        String[] colors = {"RED", "YELLOW", "GREEN", "BLUE"};

        int start = input.indexOf('[') + 1;
        int end = input.indexOf(']');

        String substring = input.substring(start, end);
        String[] tokens = substring.split(", ");

        ((InGameController) controller).setTokens(tokens);
    }

    /**
     * Prompts the user to choose the face of the starter card.
     *
     * @param event the event triggering the face starter choice
     */
    public void chooseFaceStarter(Event event) {
        controller.printTextTopMessage("Choose the face of the starter card");
        ((InGameController) controller).setStarterCardDialog(event.getN1());
    }

    /**
     * Sets the hand of cards for the player.
     *
     * @param event the event containing the hand data
     */
    public void printHand(Event event) {
        ((InGameController) controller).setHandCard(event.getN1(), event.getN2(), event.getN3());
    }

    /**
     * Prompts the user to choose objectives.
     *
     * @param event the event triggering the objectives choice
     */
    public void chooseObjectives(Event event) {
        ((InGameController) controller).setObjectiveCardDialog(event.getN1(), event.getN2());
    }

    /**
     * Sets the chosen objectives in the game.
     *
     * @param event the event containing the objectives data
     */
    public void sendObjectives(Event event) {
        ((InGameController) controller).setObjectivesCard(event.getN1(), event.getN2(), event.getN3());
    }

    /**
     * Prompts the user to choose a play card.
     *
     * @param event the event triggering the play card choice
     */
    public void choosePlayCard(Event event) {
        ((InGameController) controller).setHandCard(event.getN1(), event.getN2(), event.getN3());
    }

    /**
     * Prompts the user to choose a draw card.
     *
     * @param event the event triggering the draw card choice
     */
    public void chooseDrawCard(Event event) {
        ((InGameController) controller).setDeck(event.getN1(), event.getN2(), event.getN3(), event.getN4(), event.getN5(), event.getN6());
    }

    /**
     * Prints a message at the top of the GUI.
     *
     * @param text the text to print
     */
    public void printText(String text) {
        controller.printTextTopMessage(text);
    }

    /**
     * Prints the game board.
     *
     * @param sortedCoordinates the sorted coordinates of the cards
     * @param board             the game board
     */
    public void printBoard(List<int[]> sortedCoordinates, int[][][] board) {
        ((InGameController) controller).printBoard(sortedCoordinates, board);
    }

    /**
     * Rolls back the last play card choice if it was invalid.
     */
    public void rollBackChoosePlayCard() {
        ((InGameController) controller).rollBackChoosePlayCard();
        printText("You tried to put a gold card that doesn't respect its condition");
    }

    /**
     * Updates the points of a player.
     *
     * @param token  the player's token
     * @param points the points to update
     */
    public void updatePlayerPoints(String token, int points) {
        ((InGameController) controller).updatePoints(token, points);
    }

    /**
     * Prints the board of another player.
     *
     * @param playerName       the name of the player
     * @param token            the player's token
     * @param sortedCoordinates the sorted coordinates of the cards
     * @param board            the game board
     */
    public void printPlayerBoard(String playerName, String token, List<int[]> sortedCoordinates, int[][][] board) {
        ((InGameController) controller).printPlayerBoard(playerName, token, sortedCoordinates, board);
    }

    /**
     * Exits the application with a confirmation dialog.
     *
     * @param stage the primary stage of the application
     */
    void exit(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You're about to exit!");
        alert.setContentText("Are you sure you want to exit?: ");

        if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("You successfully exit!");
            stage.close();
            System.exit(0);
        }
    }

    /**
     * Returns the map of controllers.
     *
     * @return the map of controllers
     */
    public Map<String, GenericController> getControllers() {
        return controllers;
    }

    /**
     * Adds a player to the lobby pane.
     *
     * @param nick the nickname of the player
     */
    public void addLobbyPanePlayer(String nick) {
        int index = 0;
        for (index = 0; index < 4; index++)
            if (!primaryStage.getScene().getRoot().lookup("#pane" + index).isVisible())
                break;

        Scene sceneToLoad = null;
        GenericController controllerToLoad = null;

        switch (index) {
            case 0:
                sceneToLoad = scenes.get("PlayerLobby1");
                controllerToLoad = controllers.get("PlayerLobby1");
                break;
            case 1:
                sceneToLoad = scenes.get("PlayerLobby2");
                controllerToLoad = controllers.get("PlayerLobby2");
                break;
            case 2:
                sceneToLoad = scenes.get("PlayerLobby3");
                controllerToLoad = controllers.get("PlayerLobby3");
                break;
            case 3:
                sceneToLoad = scenes.get("PlayerLobby4");
                controllerToLoad = controllers.get("PlayerLobby4");
                break;
        }

        Pane paneToLoad;
        paneToLoad = (Pane) sceneToLoad.getRoot();

        ((PlayerLobbyController) controllerToLoad).setNickname(nick);

        Pane panePlayerLobby = (Pane) this.primaryStage.getScene().getRoot().lookup("#pane" + index);
        panePlayerLobby.setVisible(true);

        panePlayerLobby.getChildren().clear();
        paneToLoad.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(paneToLoad);
        StackPane.setAlignment(paneToLoad, Pos.CENTER);

        paneToLoad.prefWidthProperty().bind(panePlayerLobby.widthProperty());
        ((PlayerLobbyController) controllerToLoad).getNickPanel().prefWidthProperty().bind(panePlayerLobby.widthProperty());
        paneToLoad.prefHeightProperty().bind(panePlayerLobby.heightProperty());

        panePlayerLobby.getChildren().add(stackPane);
    }

    /**
     * Sets the scoreboard with players' nicknames and points.
     *
     * @param nicknames the list of players' nicknames
     * @param points    the list of players' points
     */
    public void setScoreBoard(List<String> nicknames, List<Integer> points) {
        ((GameEndController) controller).setScoreBoard(nicknames, points);
    }

    /**
     * Reconnects player data to the game.
     *
     * @param event the event containing the player data
     */
    public void reconnectPlayerData(Event event) {
        setScene("InGame");
        printText("You have been reconnected, you'll see your cards on your turn");
        ((InGameController) controller).setObjectivesCard(PlayerData.getPersonalObj(), PlayerData.getPlayfieldObj1(), PlayerData.getPlayfieldObj2());
    }

    /**
     * Cleans up the game state in the GUI.
     */
    public void cleanGame() {
        ((LobbyController) controllers.get("Lobby")).hidePanes();
        ((InGameController) controllers.get("InGame")).cleanInGame();
    }

    /**
     * The main entry point for the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
        System.out.println("GUIMain is Done");
    }
}
