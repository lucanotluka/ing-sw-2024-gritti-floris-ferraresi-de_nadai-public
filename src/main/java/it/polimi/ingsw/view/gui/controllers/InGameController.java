package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.client.PlayerData;
import it.polimi.ingsw.listener.EventType;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.scene.control.DialogPane;

import java.lang.reflect.Field;

import java.util.*;

/**
 * Controller for managing the in-game actions and display.
 */
public class InGameController extends GenericController {

    private Pane supportPaneFrontier;

    @FXML
    private Pane content;

    @FXML
    public DialogPane ChooseCardDialog;

    @FXML
    public DialogPane ChooseTokenDialog;

    @FXML
    private Button DialogCard1;

    @FXML
    private Button DialogCard2;

    @FXML
    private ScrollPane ScrollPaneField;

    @FXML
    private Pane GoldDeck;

    @FXML
    private Pane GoldTable1;

    @FXML
    private Pane GoldTable2;

    @FXML
    private Pane Hand1;

    @FXML
    private Pane Hand2;

    @FXML
    private Pane Hand3;

    @FXML
    private Pane Objective1;

    @FXML
    private Pane Objective2;

    @FXML
    private Pane Objective3;

    @FXML
    private AnchorPane PlayField0;

    @FXML
    private Pane ResourceDeck;

    @FXML
    private Pane ResourceTable1;

    @FXML
    private Pane ResourceTable2;

    @FXML
    public TextField TopMessage;

    @FXML
    private Pane point00;

    @FXML
    private Pane point01;

    @FXML
    private Pane point02;

    @FXML
    private Pane point03;

    @FXML
    private Pane point04;

    @FXML
    private Pane point05;

    @FXML
    private Pane point06;

    @FXML
    private Pane point07;

    @FXML
    private Pane point08;

    @FXML
    private Pane point09;

    @FXML
    private Pane point10;

    @FXML
    private Pane point11;

    @FXML
    private Pane point12;

    @FXML
    private Pane point13;

    @FXML
    private Pane point14;

    @FXML
    private Pane point15;

    @FXML
    private Pane point16;

    @FXML
    private Pane point17;

    @FXML
    private Pane point18;

    @FXML
    private Pane point19;

    @FXML
    private Pane point20;

    @FXML
    private Pane point21;

    @FXML
    private Pane point22;

    @FXML
    private Pane point23;

    @FXML
    private Pane point24;

    @FXML
    private Pane point25;

    @FXML
    private Pane point26;

    @FXML
    private Pane point27;

    @FXML
    private Pane point28;

    @FXML
    private Pane point29;

    @FXML
    private Pane token1;

    @FXML
    private Pane token2;

    @FXML
    private Pane token3;

    @FXML
    private Pane token4;

    @FXML
    private Button flipIcon1;

    @FXML
    private Button flipIcon2;

    @FXML
    private Button flipIcon3;

    @FXML
    private DialogPane PlayerDialog1;

    @FXML
    private AnchorPane PlayerField1;

    @FXML
    private DialogPane PlayerDialog2;

    @FXML
    private AnchorPane PlayerField2;

    @FXML
    private DialogPane PlayerDialog3;

    @FXML
    private AnchorPane PlayerField3;

    @FXML
    private Button buttonPlayer1;

    @FXML
    private Button buttonPlayer2;

    @FXML
    private Button buttonPlayer3;

    private Pane supportPane;

    private Map<Pane, Integer> dropZones;

    /**
     * Sets the drop zones for the game board.
     *
     * @param dropZones a map of drop zones with their corresponding integer values
     */
    public void setDropZones(Map<Pane, Integer> dropZones) {
        this.dropZones = dropZones;
    }

    /**
     * Handles the event when a dialog card is chosen.
     *
     * @param event the action event
     */
    @FXML
    void ChosedDialogCard(ActionEvent event) {
        if (PlayerData.getGameStatus() != EventType.CHOOSE_OBJECTIVES && PlayerData.getGameStatus() != EventType.CHOOSE_FACE_STARTER)
            return;
        String choosed = "";
        if (event.getSource().equals(DialogCard1))
            choosed = "1";
        if (event.getSource().equals(DialogCard2))
            choosed = "2";
        ChooseCardDialog.setVisible(false);
        // Notify the listener with the chosen string
        if (eventListenerGUI != null) {
            eventListenerGUI.onStringReceived(choosed);
        }
    }

    /**
     * Handles the event when a token is chosen.
     *
     * @param event the mouse event
     */
    @FXML
    void ChosedToken(MouseEvent event) {
        if (PlayerData.getGameStatus() != EventType.CHOOSE_TOKEN)
            return;
        String choosed = "";
        if (event.getSource().equals(token1))
            choosed = "BLUE";
        if (event.getSource().equals(token2))
            choosed = "RED";
        if (event.getSource().equals(token3))
            choosed = "YELLOW";
        if (event.getSource().equals(token4))
            choosed = "GREEN";
        ChooseTokenDialog.setVisible(false);
        // Notify the listener with the chosen string
        if (eventListenerGUI != null) {
            eventListenerGUI.onStringReceived(choosed);
        }
    }

    /**
     * Toggles the visibility of the field for Player 1.
     *
     * @param event the action event
     */
    @FXML
    void ShowFieldPlayer1(ActionEvent event) {
        if (PlayerDialog1.isVisible())
            PlayerDialog1.setVisible(false);
        else {
            PlayerDialog1.setVisible(true);
            PlayerDialog2.setVisible(false);
            PlayerDialog3.setVisible(false);
        }
    }

    /**
     * Toggles the visibility of the field for Player 2.
     *
     * @param event the action event
     */
    @FXML
    void ShowFieldPlayer2(ActionEvent event) {
        if (PlayerDialog2.isVisible())
            PlayerDialog2.setVisible(false);
        else {
            PlayerDialog2.setVisible(true);
            PlayerDialog1.setVisible(false);
            PlayerDialog3.setVisible(false);
        }
    }

    /**
     * Toggles the visibility of the field for Player 3.
     *
     * @param event the action event
     */
    @FXML
    void ShowFieldPlayer3(ActionEvent event) {
        if (PlayerDialog3.isVisible())
            PlayerDialog3.setVisible(false);
        else {
            PlayerDialog3.setVisible(true);
            PlayerDialog1.setVisible(false);
            PlayerDialog2.setVisible(false);
        }
    }

    /**
     * Sets the top message text.
     *
     * @param text the message to display
     */
    @Override
    public void printTextTopMessage(String text) {
        TopMessage.setText(text);
    }

    /**
     * Sets the available tokens for selection.
     *
     * @param tokens an array of available tokens
     */
    public void setTokens(String[] tokens) {
        ChooseTokenDialog.setVisible(true);
        token1.setVisible(true);
        token2.setVisible(true);
        token3.setVisible(true);
        token4.setVisible(true);
        if (!Arrays.asList(tokens).contains("BLUE"))
            token1.setVisible(false);
        if (!Arrays.asList(tokens).contains("RED"))
            token2.setVisible(false);
        if (!Arrays.asList(tokens).contains("YELLOW"))
            token3.setVisible(false);
        if (!Arrays.asList(tokens).contains("GREEN"))
            token4.setVisible(false);
    }

    /**
     * Sets the background image of a specified component.
     *
     * @param componentName the name of the component
     * @param imagePath     the path to the image
     */
    public void setBackgroundImage(String componentName, String imagePath) {
        try {
            // Retrieve the field of the object using the component name
            Field field = this.getClass().getDeclaredField(componentName);
            field.setAccessible(true);
            Object component = field.get(this);

            // Create a new image
            Image image = new Image(getClass().getResourceAsStream(imagePath));

            if (component instanceof Pane) {
                Pane pane = (Pane) component;
                pane.setStyle("-fx-background-image: url('" + getClass().getResource(imagePath).toExternalForm() + "'); " +
                        "-fx-background-size: cover;");
            } else if (component instanceof Button) {
                Button button = (Button) component;
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(button.getWidth());
                imageView.setFitHeight(button.getHeight());
                imageView.setPreserveRatio(true);
                button.setGraphic(imageView);
            } else {
                throw new IllegalArgumentException("Unsupported component type");
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Component not found or accessible: " + componentName, e);
        }
    }

    /**
     * Sets the starter card dialog with the front and back images.
     *
     * @param cardNumber the card number
     */
    public void setStarterCardDialog(int cardNumber) {
        setBackgroundImage("DialogCard1", getAddressFrontCard(cardNumber));
        setBackgroundImage("DialogCard2", getAddressBackCard(cardNumber));
        ChooseCardDialog.setVisible(true);
    }

    /**
     * Sets the objective card dialog with the given card numbers.
     *
     * @param cardNumber1 the first card number
     * @param cardNumber2 the second card number
     */
    public void setObjectiveCardDialog(int cardNumber1, int cardNumber2) {
        setBackgroundImage("DialogCard1", getAddressFrontCard(cardNumber1));
        setBackgroundImage("DialogCard2", getAddressFrontCard(cardNumber2));
        ChooseCardDialog.setVisible(true);
    }

    /**
     * Gets the address of the back image of a card.
     *
     * @param cardNumber the card number
     * @return the address of the back image
     */
    private String getAddressBackCard(int cardNumber) {
        String nameCard = "";
        if (cardNumber < 10)
            nameCard = "00" + cardNumber;
        else if (cardNumber < 100)
            nameCard = "0" + cardNumber;

        String address = "";
        if (cardNumber < 11)
            address = "/cardBack/resource/fungi.png";
        else if (cardNumber < 21)
            address = "/cardBack/resource/plant.png";
        else if (cardNumber < 31)
            address = "/cardBack/resource/animal.png";
        else if (cardNumber < 41)
            address = "/cardBack/resource/insect.png";
        else if (cardNumber < 51)
            address = "/cardBack/gold/fungi.png";
        else if (cardNumber < 61)
            address = "/cardBack/gold/plant.png";
        else if (cardNumber < 71)
            address = "/cardBack/gold/animal.png";
        else if (cardNumber < 81)
            address = "/cardBack/gold/insect.png";
        else if (cardNumber < 87)
            address = "/cardBack/starter/" + nameCard + ".png";
        else
            address = "/cardBack/objective.png";

        return address;
    }

    /**
     * Gets the address of the front image of a card.
     *
     * @param cardNumber the card number
     * @return the address of the front image
     */
    private String getAddressFrontCard(int cardNumber) {
        String nameCard = "";
        if (cardNumber < 10)
            nameCard = "00" + cardNumber;
        else if (cardNumber < 100)
            nameCard = "0" + cardNumber;
        else
            nameCard = "" + cardNumber;
        return "/cardFront/" + nameCard + ".png";
    }

    /**
     * Sets the objectives card with the given card numbers.
     *
     * @param n1 the first card number
     * @param n2 the second card number
     * @param n3 the third card number
     */
    public void setObjectivesCard(int n1, int n2, int n3) {
        setBackgroundImage("Objective1", getAddressFrontCard(n1));
        setBackgroundImage("Objective2", getAddressFrontCard(n2));
        setBackgroundImage("Objective3", getAddressFrontCard(n3));
    }

    /**
     * Sets the hand cards with the given card numbers.
     *
     * @param n1 the first card number
     * @param n2 the second card number
     * @param n3 the third card number
     */
    public void setHandCard(int n1, int n2, int n3) {
        String face = PlayerData.getFace(n1);
        if (face != null && face.equals("BACK")) {
            setBackgroundImage("Hand1", getAddressBackCard(n1));
            PlayerData.setFace(n1, "BACK");
        } else {
            setBackgroundImage("Hand1", getAddressFrontCard(n1));
            PlayerData.setFace(n1, "FRONT");
        }
        Hand1.setUserData(n1);
        Hand1.setVisible(true);
        flipIcon1.setVisible(true);

        face = PlayerData.getFace(n2);
        if (face != null && face.equals("BACK")) {
            setBackgroundImage("Hand2", getAddressBackCard(n2));
            PlayerData.setFace(n2, "BACK");
        } else {
            setBackgroundImage("Hand2", getAddressFrontCard(n2));
            PlayerData.setFace(n2, "FRONT");
        }

        Hand2.setUserData(n2);
        Hand2.setVisible(true);
        flipIcon2.setVisible(true);

        face = PlayerData.getFace(n3);
        if (face != null && face.equals("BACK")) {
            setBackgroundImage("Hand3", getAddressBackCard(n3));
            PlayerData.setFace(n3, "BACK");
        } else {
            setBackgroundImage("Hand3", getAddressFrontCard(n3));
            PlayerData.setFace(n3, "FRONT");
        }
        Hand3.setUserData(n3);
        Hand3.setVisible(true);
        flipIcon3.setVisible(true);
    }

    /**
     * Prints the game board with the given coordinates and board data.
     *
     * @param sortedCoordinates the sorted coordinates of the cards
     * @param board             the game board data
     */
    public void printBoard(List<int[]> sortedCoordinates, int[][][] board) {
        int topBorderSize = 90;
        int sideBorderSize = 125;

        int width, height;

        int possibleElements = (board[0].length + 1) / 2;
        int toRemove = 0;

        if (board[0].length % 2 != 0)//in case the board has an odd number of columns
            toRemove = 59;
        if (ScrollPaneField.getWidth() > ((possibleElements * 164) - toRemove + sideBorderSize + sideBorderSize)) {
            width = (int) ScrollPaneField.getWidth();
            sideBorderSize = (int) ((ScrollPaneField.getWidth() - ((possibleElements * 164) - toRemove))) / 2; // 164= 105 + 59
        } else {
            width = (possibleElements * 164) - toRemove + sideBorderSize + sideBorderSize;
        }

        possibleElements = (board.length + 1) / 2;
        toRemove = 0;
        if (possibleElements == 0)//in case the result is 0
            possibleElements = 1;
        if (board.length % 2 != 0)//in case the board has an odd number of columns
            toRemove = 14;

        if (ScrollPaneField.getHeight() > ((possibleElements * 84) - toRemove + topBorderSize + topBorderSize)) {
            height = (int) ScrollPaneField.getHeight();
            topBorderSize = (int) ((ScrollPaneField.getHeight() - ((possibleElements * 84) - toRemove))) / 2; // 84= 70 + 14
        } else {
            height = (possibleElements * 84) - toRemove + sideBorderSize + sideBorderSize;
        }

        PlayField0.getChildren().clear(); // clear all the board
        PlayField0.setPrefWidth(width);
        PlayField0.setPrefHeight(height);

        Map<Pane, Integer> frontier = new HashMap<>();

        int pairOrOdd = 0;
        for (int i = 0; i < board[0].length; i++) {
            if (board[0][i][0] != 0) {
                pairOrOdd = i % 2;
                break;
            }
        }
        for (int[] coord : sortedCoordinates) {
            int i = coord[0];
            int j = coord[1];
            if (board[i][j][0] != 0) {
                Pane pane = new Pane();
                pane.setPrefSize(105, 70);
                if (i % 2 == pairOrOdd)
                    pane.setLayoutX((((j + 1) / 2) * 164) + sideBorderSize);
                else
                    pane.setLayoutX(((j / 2) * 164) + sideBorderSize + 82);
                if (j % 2 == pairOrOdd)
                    pane.setLayoutY(((i / 2) * 84) + topBorderSize);
                else
                    pane.setLayoutY((((i - 1) / 2) * 84) + topBorderSize + 42);
                supportPane = pane;
                if (board[i][j][0] > 0) // if it is a card
                    if (board[i][j][5] == 0)
                        setBackgroundImage("supportPane", getAddressFrontCard(board[i][j][0]));
                    else {
                        setBackgroundImage("supportPane", getAddressBackCard(board[i][j][0]));
                    }
                else { // else it is a border
                    supportPane.setStyle("-fx-background-color: rgba(255, 255, 0, 0.5); -fx-background-radius: 15;");
                    frontier.put(pane, board[i][j][0]);
                }
                PlayField0.getChildren().add(supportPane);
            }
        }
        this.dropZones = frontier;
        setUpDragAndDrop();
    }

    /**
     * Handles the event to flip a card in the hand.
     *
     * @param event the action event
     */
    @FXML
    void flipCard(ActionEvent event) {
        String componentName;
        int cardNumber;
        String face;
        if (event.getSource().equals(flipIcon1)) {
            componentName = "Hand1";
            cardNumber = (int) Hand1.getUserData();
            face = PlayerData.getFace(cardNumber);

        } else if (event.getSource().equals(flipIcon2)) {
            componentName = "Hand2";
            cardNumber = (int) Hand2.getUserData();
            face = PlayerData.getFace(cardNumber);
        } else {
            componentName = "Hand3";
            cardNumber = (int) Hand3.getUserData();
            face = PlayerData.getFace(cardNumber);
        }
        if (face.equals("FRONT")) {
            setBackgroundImage(componentName, getAddressBackCard(cardNumber));
            PlayerData.setFace(cardNumber, "BACK");
        } else {
            setBackgroundImage(componentName, getAddressFrontCard(cardNumber));
            PlayerData.setFace(cardNumber, "FRONT");
        }
    }

    /**
     * Sets up the drag detection for a card in the hand.
     *
     * @param event the mouse event
     */
    @FXML
    void setOnDragDetected(MouseEvent event) {
        if (PlayerData.getGameStatus() != EventType.CHOOSE_PLAY_CARD && PlayerData.getGameStatus() != EventType.CHOOSE_POSITION)
            return;
        Pane pane;
        int cardOption;
        if (event.getSource().equals(Hand1)) {
            pane = Hand1;
            flipIcon1.setVisible(false); // Hide the flip icon when the card is dragged
            cardOption = 1;
        } else if (event.getSource().equals(Hand2)) {
            pane = Hand2;
            flipIcon2.setVisible(false);
            cardOption = 2;
        } else {
            pane = Hand3;
            flipIcon3.setVisible(false);
            cardOption = 3;
        }
        Dragboard db = pane.startDragAndDrop(TransferMode.ANY);
        // Create an ImageView with the background image
        ImageView background;
        int cardNumber = (int) pane.getUserData();
        if (PlayerData.getFace(cardNumber).equals("BACK"))
            background = new ImageView(new Image(getClass().getResourceAsStream(getAddressBackCard(cardNumber))));
        else
            background = new ImageView(new Image(getClass().getResourceAsStream(getAddressFrontCard(cardNumber))));

        // Set the ImageView size to fit the Pane
        background.setFitWidth(pane.getWidth());
        background.setFitHeight(pane.getHeight());

        // Add the ImageView to the Pane
        pane.getChildren().add(background);

        // Now, when you take a snapshot, the background image will be included
        WritableImage snapshot = pane.snapshot(new SnapshotParameters(), null);
        pane.getChildren().clear();
        // Set the snapshot image as the drag view
        db.setDragView(snapshot);

        ClipboardContent content = new ClipboardContent();
        content.putString("Pane Data");
        db.setContent(content);
        pane.setVisible(false);
        if (PlayerData.getGameStatus() == EventType.CHOOSE_PLAY_CARD)
            eventListenerGUI.onStringReceived("" + cardOption);
        event.consume();
    }

    /**
     * Handles the drag done event.
     *
     * @param event the drag event
     */
    @FXML
    void setOnDragDone(DragEvent event) {
        if (event.getTransferMode() == null) {
            ((Pane) event.getGestureSource()).setVisible(true);
        }
    }

    /**
     * Sets up the drag and drop functionality for the game.
     */
    public void setUpDragAndDrop() {
        for (Pane dropZone : dropZones.keySet()) {
            dropZone.setOnDragOver(event -> {
                if ((event.getGestureSource() != Hand1 || event.getGestureSource() != Hand2 || event.getGestureSource() != Hand3)) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            });

            dropZone.setOnDragDropped(event -> {
                if (PlayerData.getGameStatus() != EventType.CHOOSE_POSITION)
                    return;
                supportPaneFrontier = dropZone; // Save the frontier where the card is dropped
                Dragboard db = event.getDragboard();
                boolean success = false;
                if ((event.getGestureSource() != Hand1 || event.getGestureSource() != Hand2 || event.getGestureSource() != Hand3)) {
                    Pane source = (Pane) event.getGestureSource();
                    source.setVisible(false);
                    if (source.equals(Hand1)) {
                        flipIcon1.setVisible(false);
                        if (PlayerData.getFace((int) Hand1.getUserData()).equals("BACK"))
                            setBackgroundImage("supportPaneFrontier", getAddressBackCard((int) Hand1.getUserData()));
                        else
                            setBackgroundImage("supportPaneFrontier", getAddressFrontCard((int) Hand1.getUserData()));
                    }
                    if (source.equals(Hand2)) {
                        flipIcon2.setVisible(false);
                        if (PlayerData.getFace((int) Hand2.getUserData()).equals("BACK"))
                            setBackgroundImage("supportPaneFrontier", getAddressBackCard((int) Hand2.getUserData()));
                        else
                            setBackgroundImage("supportPaneFrontier", getAddressFrontCard((int) Hand2.getUserData()));
                    }
                    if (source.equals(Hand3)) {
                        flipIcon3.setVisible(false);
                        if (PlayerData.getFace((int) Hand3.getUserData()).equals("BACK"))
                            setBackgroundImage("supportPaneFrontier", getAddressBackCard((int) Hand3.getUserData()));
                        else
                            setBackgroundImage("supportPaneFrontier", getAddressFrontCard((int) Hand3.getUserData()));
                    }
                    eventListenerGUI.onStringReceived("" + dropZones.get(dropZone) * (-1));
                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();
                eventListenerGUI.onStringReceived("" + dropZones.get(dropZone));
                dropZones.remove(dropZone);
                for (Pane dropZone2 : dropZones.keySet()) { // Hide frontier
                    dropZone2.setVisible(false);
                }
            });
        }
    }

    /**
     * Handles the draw card event.
     *
     * @param event the mouse event
     */
    @FXML
    void drawCardEvent(MouseEvent event) {
        if (PlayerData.getGameStatus() != EventType.CHOOSE_DRAW_CARD)
            return;
        int cardOption;
        int cardNumber;
        if (event.getSource().equals(ResourceDeck)) {
            cardOption = 1;
            cardNumber = (int) ResourceDeck.getUserData();
            ResourceDeck.setVisible(false);
        } else if (event.getSource().equals(GoldDeck)) {
            cardOption = 2;
            cardNumber = (int) GoldDeck.getUserData();
            GoldDeck.setVisible(false);
        } else if (event.getSource().equals(ResourceTable1)) {
            cardOption = 3;
            cardNumber = (int) ResourceTable1.getUserData();
            ResourceTable1.setVisible(false);
        } else if (event.getSource().equals(ResourceTable2)) {
            cardOption = 4;
            cardNumber = (int) ResourceTable2.getUserData();
            ResourceTable2.setVisible(false);
        } else if (event.getSource().equals(GoldTable1)) {
            cardOption = 5;
            cardNumber = (int) GoldTable1.getUserData();
            GoldTable1.setVisible(false);
        } else {
            cardOption = 6;
            cardNumber = (int) GoldTable2.getUserData();
            GoldTable2.setVisible(false);
        }
        if (!Hand1.isVisible()) {
            setBackgroundImage("Hand1", getAddressFrontCard(cardNumber));
            Hand1.setUserData(cardNumber);
            PlayerData.setFace(cardNumber, "FRONT");
            Hand1.setVisible(true);
            flipIcon1.setVisible(true);
        } else if (!Hand2.isVisible()) {
            setBackgroundImage("Hand2", getAddressFrontCard(cardNumber));
            Hand2.setUserData(cardNumber);
            PlayerData.setFace(cardNumber, "FRONT");
            Hand2.setVisible(true);
            flipIcon2.setVisible(true);
        } else if (!Hand3.isVisible()) {
            setBackgroundImage("Hand3", getAddressFrontCard(cardNumber));
            Hand3.setUserData(cardNumber);
            PlayerData.setFace(cardNumber, "FRONT");
            Hand3.setVisible(true);
            flipIcon3.setVisible(true);
        }
        eventListenerGUI.onStringReceived("" + cardOption);
        PlayerData.setGameStatus(EventType.ERROR); // Change the status to prevent the player from choosing another card
    }

    /**
     * Sets the deck with the given card numbers.
     *
     * @param n1 the first card number
     * @param n2 the second card number
     * @param n3 the third card number
     * @param n4 the fourth card number
     * @param n5 the fifth card number
     * @param n6 the sixth card number
     */
    public void setDeck(int n1, int n2, int n3, int n4, int n5, int n6) {
        // Set the background of the Panes
        if (n1 != 0) {
            setBackgroundImage("ResourceDeck", getAddressBackCard(n1));
            ResourceDeck.setUserData(n1);
            ResourceDeck.setVisible(true);
        } else {
            ResourceDeck.setVisible(false);
        }
        if (n3 != 0) {
            setBackgroundImage("ResourceTable1", getAddressFrontCard(n3));
            ResourceTable1.setUserData(n3);
            ResourceTable1.setVisible(true);
        } else {
            ResourceTable1.setVisible(false);
        }
        if (n4 != 0) {
            setBackgroundImage("ResourceTable2", getAddressFrontCard(n4));
            ResourceTable2.setUserData(n4);
            ResourceTable2.setVisible(true);
        } else {
            ResourceTable2.setVisible(false);
        }
        if (n2 != 0) {
            setBackgroundImage("GoldDeck", getAddressBackCard(n2));
            GoldDeck.setUserData(n2);
            GoldDeck.setVisible(true);
        } else {
            GoldDeck.setVisible(false);
        }
        if (n5 != 0) {
            setBackgroundImage("GoldTable1", getAddressFrontCard(n5));
            GoldTable1.setUserData(n5);
            GoldTable1.setVisible(true);
        } else {
            GoldTable1.setVisible(false);
        }
        if (n6 != 0) {
            setBackgroundImage("GoldTable2", getAddressFrontCard(n6));
            GoldTable2.setUserData(n6);
            GoldTable2.setVisible(true);
        } else {
            GoldTable2.setVisible(false);
        }
    }

    /**
     * Rolls back the choose play card action.
     */
    public void rollBackChoosePlayCard() {
        supportPaneFrontier.setVisible(false);

        Hand1.setVisible(true);
        Hand2.setVisible(true);
        Hand3.setVisible(true);
        flipIcon1.setVisible(true);
        flipIcon2.setVisible(true);
        flipIcon3.setVisible(true);
    }

    /**
     * Updates the points for a given token.
     *
     * @param token  the token identifier
     * @param points the points to update
     */
    public void updatePoints(String token, int points) {
        PlayerData.addTokenPoints(token, points);

        for (int i = 0; i <= 29; i++) {
            String paneName = String.format("point%02d", i);
            try {
                Field field = this.getClass().getDeclaredField(paneName);
                field.setAccessible(true);
                Pane pane = (Pane) field.get(this);
                if (pane != null) {
                    pane.setVisible(false);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException("Component not found or accessible: " + paneName, e);
            }
        }

        HashMap<String, Integer> tokenPoints = PlayerData.getTokenPoints();
        int i = 0;

        for (String supportToken : tokenPoints.keySet()) {
            i = tokenPoints.get(supportToken);
            if (i > 29) break; // Limit the panes to the first 30
            String paneName = String.format("point%02d", i);
            try {
                Field field = this.getClass().getDeclaredField(paneName);
                field.setAccessible(true);
                Pane pane = (Pane) field.get(this);
                if (pane != null) {
                    pane.setVisible(true);
                    pane.getChildren().clear(); // Remove any previous content
                    if (supportToken.equals("BLUE"))
                        setBackgroundImage(paneName, "/tokens/blue.png");
                    else if (supportToken.equals("GREEN"))
                        setBackgroundImage(paneName, "/tokens/green.png");
                    else if (supportToken.equals("RED"))
                        setBackgroundImage(paneName, "/tokens/red.png");
                    else if (supportToken.equals("YELLOW"))
                        setBackgroundImage(paneName, "/tokens/yellow.png");
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException("Component not found or accessible: " + paneName, e);
            }
        }
    }

    /**
     * Cleans the in-game state, resetting all fields and UI elements.
     */
    public void cleanInGame() {
        PlayerField1.getChildren().clear();
        PlayerField2.getChildren().clear();
        PlayerField3.getChildren().clear();
        buttonPlayer1.setVisible(false);
        buttonPlayer2.setVisible(false);
        buttonPlayer3.setVisible(false);
        PlayField0.getChildren().clear();
        Hand1.setStyle("");
        Hand2.setStyle("");
        Hand3.setStyle("");
        flipIcon1.setVisible(false);
        flipIcon2.setVisible(false);
        flipIcon3.setVisible(false);
        ResourceDeck.setStyle("");
        GoldDeck.setStyle("");
        ResourceTable1.setStyle("");
        ResourceTable2.setStyle("");
        GoldTable1.setStyle("");
        GoldTable2.setStyle("");
        for (int i = 0; i <= 29; i++) {
            String paneName = String.format("point%02d", i);
            try {
                Field field = this.getClass().getDeclaredField(paneName);
                field.setAccessible(true);
                Pane pane = (Pane) field.get(this);
                if (pane != null) {
                    pane.setVisible(false);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException("Component not found or accessible: " + paneName, e);
            }
        }
    }

    /**
     * Prints the player's board with the given coordinates and board data.
     *
     * @param playerName        the name of the player
     * @param token             the player's token
     * @param sortedCoordinates the sorted coordinates of the cards
     * @param board             the game board data
     */
    public void printPlayerBoard(String playerName, String token, List<int[]> sortedCoordinates, int[][][] board) {
        AnchorPane currentField;
        switch (getAndSetFieldButton(playerName, token)) {
            case 1:
                currentField = PlayerField1;
                break;
            case 2:
                currentField = PlayerField2;
                break;
            case 3:
                currentField = PlayerField3;
                break;
            default:
                return;
        }

        int topBorderSize = 90;
        int sideBorderSize = 125;

        int width, height;

        int possibleElements = (board[0].length + 1) / 2;
        int toRemove = 0;

        if (board[0].length % 2 != 0)//in case the board has an odd number of columns
            toRemove = 59;
        if (currentField.getWidth() > ((possibleElements * 164) - toRemove + sideBorderSize + sideBorderSize)) {
            width = (int) currentField.getWidth();
            sideBorderSize = (int) ((currentField.getWidth() - ((possibleElements * 164) - toRemove))) / 2; // 164= 105 + 59
        } else {
            width = (possibleElements * 164) - toRemove + sideBorderSize + sideBorderSize;
        }

        possibleElements = (board.length + 1) / 2;
        toRemove = 0;
        if (possibleElements == 0)//in case the result is 0
            possibleElements = 1;
        if (board.length % 2 != 0)//in case the board has an odd number of columns
            toRemove = 14;

        if (currentField.getHeight() > ((possibleElements * 84) - toRemove + topBorderSize + topBorderSize)) {
            height = (int) currentField.getHeight();
            topBorderSize = (int) ((currentField.getHeight() - ((possibleElements * 84) - toRemove))) / 2; // 84= 70 + 14
        } else {
            height = (possibleElements * 84) - toRemove + sideBorderSize + sideBorderSize;
        }

        currentField.getChildren().clear(); // clear all the board
        currentField.setPrefWidth(width);
        currentField.setPrefHeight(height);

        int pairOrOdd = 0;
        for (int i = 0; i < board[0].length; i++) {
            if (board[0][i][0] != 0) {
                pairOrOdd = i % 2;
                break;
            }
        }
        for (int[] coord : sortedCoordinates) {
            int i = coord[0];
            int j = coord[1];
            if (board[i][j][0] != 0) {
                Pane pane = new Pane();
                pane.setPrefSize(105, 70);
                if (i % 2 == pairOrOdd)
                    pane.setLayoutX((((j + 1) / 2) * 164) + sideBorderSize);
                else
                    pane.setLayoutX(((j / 2) * 164) + sideBorderSize + 82);
                if (j % 2 == pairOrOdd)
                    pane.setLayoutY(((i / 2) * 84) + topBorderSize);
                else
                    pane.setLayoutY((((i - 1) / 2) * 84) + topBorderSize + 42);
                supportPane = pane;
                if (board[i][j][0] > 0) // if it is a card
                    if (board[i][j][5] == 0)
                        setBackgroundImage("supportPane", getAddressFrontCard(board[i][j][0]));
                    else {
                        setBackgroundImage("supportPane", getAddressBackCard(board[i][j][0]));
                    }
            }
            if (!currentField.getChildren().contains(supportPane)) {
                currentField.getChildren().add(supportPane);
            }
        }
    }

    /**
     * Gets and sets the field button for the given player and token.
     *
     * @param playerName the name of the player
     * @param token      the player's token
     * @return the field button number
     */
    public int getAndSetFieldButton(String playerName, String token) {
        String style;
        switch (token) {
            case "BLUE":
                style = "-fx-background-color: #10628a; -fx-font-weight: bold;";
                break;
            case "GREEN":
                style = "-fx-background-color: #217402; -fx-font-weight: bold;";
                break;
            case "RED":
                style = "-fx-background-color: #d61412; -fx-font-weight: bold;";
                break;
            case "YELLOW":
                style = "-fx-background-color: #af9515; -fx-font-weight: bold;";
                break;
            default:
                style = "-fx-background-color: #ADD8E6; -fx-font-weight: bold;";
                break;
        }
        if (buttonPlayer1.isVisible()) {
            if (buttonPlayer1.getUserData().equals(token))
                return 1;
        } else {
            buttonPlayer1.setText(playerName);
            buttonPlayer1.setStyle(style);
            buttonPlayer1.setVisible(true);
            buttonPlayer1.setUserData(token);
            return 1;
        }
        if (buttonPlayer2.isVisible()) {
            if (buttonPlayer2.getUserData().equals(token))
                return 2;
        } else {
            buttonPlayer2.setText(playerName);
            buttonPlayer2.setStyle(style);
            buttonPlayer2.setVisible(true);
            buttonPlayer2.setUserData(token);
            return 2;
        }
        if (!buttonPlayer3.isVisible()) {
            buttonPlayer3.setText(playerName);
            buttonPlayer3.setStyle(style);
            buttonPlayer3.setVisible(true);
            buttonPlayer3.setUserData(token);
        }
        return 3;
    }
}
