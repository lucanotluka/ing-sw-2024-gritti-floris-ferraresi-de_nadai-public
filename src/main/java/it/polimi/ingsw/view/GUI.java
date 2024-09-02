package it.polimi.ingsw.view;

import it.polimi.ingsw.client.PlayerData;
import it.polimi.ingsw.listener.Event;
import it.polimi.ingsw.listener.EventType;
import it.polimi.ingsw.view.gui.EventListenerGUI;
import it.polimi.ingsw.view.gui.GUIMain;
import it.polimi.ingsw.view.gui.controllers.LobbyController;
import it.polimi.ingsw.view.gui.controllers.MenuController;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The GUI class implements the UserInterface and EventListenerGUI interfaces to handle the graphical user interface for the game.
 * It manages user interactions, event handling, and communicates with the GUI components.
 */
public class GUI implements UserInterface, EventListenerGUI {

    private GUIMain guiMain;
    private CompletableFuture<String> stringFuture;
    private static final CountDownLatch latch = new CountDownLatch(1);

    /**
     * Constructs a new GUI instance and initializes the GUIMain component.
     * Sets the event listener for GUI events.
     */
    public GUI() {
        try {
            guiMain = GUIMain.getInstance();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        guiMain.setEventListenerGUI(this);  // Sets the listener
    }

    /**
     * Callback method to handle the event when a string is received.
     * Completes the future with the received string.
     *
     * @param value the received string
     */
    @Override
    public void onStringReceived(String value) {
        if (stringFuture != null) {
            stringFuture.complete(value);  // Completes the future with the received string
        }
    }

    /**
     * Handles the event for choosing a nickname.
     * Prompts the user to input a nickname and updates the event with the chosen nickname.
     *
     * @param event the event triggering the nickname choice
     */
    @Override
    public void chooseNickname(Event event) {
        PlayerData.setGameStatus(EventType.CHOOSE_NICKNAME);
        stringFuture = new CompletableFuture<>();
        Platform.runLater(() -> {
            guiMain.chooseNickname(event);
        });
        try {
            String value = stringFuture.get();
            event.setParameter(value);

            Platform.runLater(() -> {
                ((MenuController) guiMain.getControllers().get("Menu")).setPlayerName(value);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the event for choosing to connect or create a lobby.
     * Manages different game statuses and user choices for lobby connection.
     *
     * @param event the event triggering the choice
     */
    @Override
    public void chooseConnectOrCreateLobby(Event event) {
        if (PlayerData.getGameStatus() == EventType.RETURN_TO_THE_MENU) {
            stringFuture = new CompletableFuture<>();
            try {
                stringFuture.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            PlayerData.clean();
        }
        String value = "";
        boolean skip = false;
        if(PlayerData.getGameStatus() == EventType.SELECT_NUMBERS_PLAYERS){
            value = "1";
            skip = true;
        } else if(PlayerData.getGameStatus() == EventType.ID_MATCH){
            value = "2";
            skip = true;
        }

        PlayerData.setGameStatus(EventType.CHOOSE_CONNECT_OR_CREATE_LOBBY);
        try {
            do {
                if(!skip) {
                    stringFuture = new CompletableFuture<>();
                    Platform.runLater(() -> {
                        guiMain.chooseConnectOrCreateLobby(event);
                    });
                    value = stringFuture.get();
                }
                skip = false;

                stringFuture = new CompletableFuture<>();
                if (value.equals("1")) {
                    Platform.runLater(() -> {
                        guiMain.selectNumbersPlayers();
                    });
                } else {
                    Platform.runLater(() -> {
                        guiMain.idMatch();
                    });
                }
            } while (stringFuture.get().equals("0"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        event.setN1(Integer.parseInt(value));
    }

    /**
     * Handles the event for selecting the number of players.
     *
     * @param event the event triggering the selection
     */
    @Override
    public void selectNumbersPlayers(Event event) {
        if(PlayerData.getGameStatus() == EventType.SELECT_NUMBERS_PLAYERS)
            chooseConnectOrCreateLobby(event);
        event.setN1(PlayerData.getOption());
        PlayerData.setGameStatus(EventType.SELECT_NUMBERS_PLAYERS);
    }

    /**
     * Handles the event for matching an ID.
     *
     * @param event the event triggering the ID match
     */
    @Override
    public void idMatch(Event event) {
        if(PlayerData.getGameStatus() == EventType.ID_MATCH)
            chooseConnectOrCreateLobby(event);
        event.setN1(PlayerData.getOption());
        PlayerData.setGameStatus(EventType.ID_MATCH);
    }

    /**
     * Handles the event for joining a lobby.
     * Updates the lobby view with player information.
     *
     * @param event the event triggering the join lobby action
     */
    @Override
    public void joinLobby(Event event) {
        int gameID;
        String regex = "Match (\\d+) has a lobby for a maximum of (\\d+) players";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(event.getParameter());

        if (matcher.find()) {
            gameID = Integer.parseInt(matcher.group(1));
        } else {
            regex = "Match (\\d+) has now a lobby, for a maximum of \\d+ players";
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(event.getParameter());
            if (matcher.find()) {
                gameID = Integer.parseInt(matcher.group(1));
            } else {
                gameID = 0;
            }
        }

        Platform.runLater(() -> {
            guiMain.setScene("Lobby");
            if (event.getPlayers() != null)
                for (String player : event.getPlayers()) {
                    guiMain.addLobbyPanePlayer(player);
                }
            ((LobbyController) guiMain.getControllers().get("Lobby")).setPlayerName(event.getNickName());
            ((LobbyController) guiMain.getControllers().get("Lobby")).setGameID("" + gameID);
            guiMain.addLobbyPanePlayer(event.getNickName());
        });
    }

    /**
     * Handles the event when a player joins.
     * Updates the lobby view with the new player.
     *
     * @param event the event triggering the player join action
     */
    @Override
    public void playerJoined(Event event) {
        Platform.runLater(() -> {
            guiMain.addLobbyPanePlayer(event.getParameter());
        });
    }

    /**
     * Handles the event for choosing a token.
     * Prompts the user to choose a token and updates the event with the chosen token.
     *
     * @param event the event triggering the token choice
     */
    @Override
    public void chooseToken(Event event) {
        PlayerData.setGameStatus(EventType.CHOOSE_TOKEN);
        stringFuture = new CompletableFuture<>();
        Platform.runLater(() -> {
            guiMain.chooseToken(event);
        });
        try {
            String value = stringFuture.get();
            PlayerData.setToken(value);
            event.setParameter(value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        cleanTopMessage();
    }

    /**
     * Handles the event for choosing the face starter.
     * Prompts the user to choose the face starter and updates the event with the chosen face.
     *
     * @param event the event triggering the face starter choice
     */
    @Override
    public void chooseFaceStarter(Event event) {
        cleanTopMessage();

        PlayerData.setGameStatus(EventType.CHOOSE_FACE_STARTER);
        stringFuture = new CompletableFuture<>();
        Platform.runLater(() -> {
            guiMain.chooseFaceStarter(event);
        });
        try {
            String value = stringFuture.get();
            if (value.equals("1")) {
                value = "FRONT";
            } else {
                value = "BACK";
            }
            PlayerData.setFace(event.getN1(), value);
            event.setParameter(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the event for choosing objectives.
     * Prompts the user to choose objectives and updates the event with the chosen objectives.
     *
     * @param event the event triggering the objectives choice
     */
    @Override
    public void chooseObjectives(Event event) {
        cleanTopMessage();

        PlayerData.setGameStatus(EventType.CHOOSE_OBJECTIVES);
        stringFuture = new CompletableFuture<>();
        Platform.runLater(() -> {
            guiMain.chooseObjectives(event);
        });
        try {
            String value = stringFuture.get();
            event.setN1(Integer.parseInt(value));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends the chosen objectives to the GUI.
     *
     * @param event the event containing the chosen objectives
     */
    @Override
    public void sendObjectives(Event event) {
        PlayerData.setPersonalObj(event.getN1());
        PlayerData.setPlayfieldObj1(event.getN2());
        PlayerData.setPlayfieldObj2(event.getN3());
        Platform.runLater(() -> {
            guiMain.sendObjectives(event);
        });
    }

    /**
     * Prints the hand of the current player.
     *
     * @param event the event containing the player's hand
     */
    @Override
    public void printHand(Event event) {
        String face = PlayerData.getFace(event.getN1());
        if (face == null)
            face = "FRONT";
        PlayerData.setFace(event.getN1(), face);
        face = PlayerData.getFace(event.getN2());
        if (face == null)
            face = "FRONT";
        PlayerData.setFace(event.getN2(), face);
        face = PlayerData.getFace(event.getN3());
        if (face == null)
            face = "FRONT";
        PlayerData.setFace(event.getN3(), face);
        Platform.runLater(() -> {
            guiMain.printHand(event);
        });
    }

    /**
     * Handles the event for choosing a play card.
     * Prompts the user to choose a play card and updates the event with the chosen card.
     *
     * @param event the event triggering the play card choice
     */
    @Override
    public void choosePlayCard(Event event) {
        cleanTopMessage();
        Platform.runLater(() -> {
            guiMain.printText(event.getNickName() + ", it's your turn");
        });
        if (PlayerData.getGameStatus() == EventType.CHOOSE_POSITION) {
            Platform.runLater(() -> {
                guiMain.rollBackChoosePlayCard();
            });
        }

        PlayerData.setGameStatus(EventType.CHOOSE_PLAY_CARD);
        stringFuture = new CompletableFuture<>();
        Platform.runLater(() -> {
            guiMain.choosePlayCard(event);
        });
        try {
            String value = stringFuture.get();
            event.setN1(Integer.parseInt(value));
        } catch (Exception e) {
            e.printStackTrace();
        }

        cleanTopMessage();
    }

    /**
     * Handles the event for choosing the front or back side of a card.
     *
     * @param event the event triggering the choice
     */
    @Override
    public void chooseFrontOrBack(Event event) {
        event.setParameter(PlayerData.getFace(event.getN1()));
    }

    /**
     * Handles the event for choosing a position.
     * Prompts the user to choose a position and updates the event with the chosen position.
     *
     * @param event the event triggering the position choice
     */
    @Override
    public void choosePosition(Event event) {
        PlayerData.setGameStatus(EventType.CHOOSE_POSITION);

        stringFuture = new CompletableFuture<>();
        try {
            String value = stringFuture.get();
            event.setN1(Integer.parseInt(value));
        } catch (Exception e) {
            e.printStackTrace();
        }

        cleanTopMessage();
    }

    /**
     * Called when a card is dropped to unlock the thread in choosePosition.
     */
    public static void onCardDropped() {
        latch.countDown();
    }

    /**
     * Handles the event for choosing a draw card.
     * Prompts the user to choose a draw card and updates the event with the chosen card.
     *
     * @param event the event triggering the draw card choice
     */
    @Override
    public void chooseDrawCard(Event event) {
        cleanTopMessage();

        PlayerData.setGameStatus(EventType.CHOOSE_DRAW_CARD);
        stringFuture = new CompletableFuture<>();
        Platform.runLater(() -> {
            guiMain.chooseDrawCard(event);
        });
        int value = 0;
        try {
            value = Integer.parseInt(stringFuture.get());
            event.setN1(value);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        cleanTopMessage();
    }

    /**
     * Prints the provided text to the GUI.
     *
     * @param text the text to print
     */
    @Override
    public void printText(String text) {
        if (text.equals("\n\nGAME STARTED!\n\nNow it's Setup Phase: each player gotta make few initial choices\n\n")) {
            Platform.runLater(() -> {
                guiMain.setScene("InGame");
            });
        }

        Pattern pattern = Pattern.compile("(\\d+)\\. \\((\\d+) points\\) ([\\w ]+)( and [\\w ]+)*");
        Matcher matcher = pattern.matcher(text);
        boolean found = false;

        List<String> names = new ArrayList<>();
        List<Integer> points = new ArrayList<>();
        while (matcher.find()) {
            String[] split = matcher.group(3).split(" and ");
            Integer playerPoint = Integer.parseInt(matcher.group(2));
            for (String name : split) {
                names.add(name);
                points.add(playerPoint);
            }
            found = true;
        }
        if (found) {
            PlayerData.setGameStatus(EventType.RETURN_TO_THE_MENU);
            Platform.runLater(() -> {
                guiMain.setScene("EndGame");
                guiMain.setScoreBoard(names, points);
                guiMain.cleanGame();
            });
        }

        Platform.runLater(() -> {
            guiMain.printText(text);
        });
    }

    /**
     * Prints the title credits to the GUI.
     */
    @Override
    public void printTitleCredits() {
        Platform.runLater(() -> {
            guiMain.printTitleCredits();
        });
    }

    /**
     * Prints the board to the GUI and updates player points.
     *
     * @param event the event containing the board data
     */
    @Override
    public void printBoard(Event event) {
        //update drawables cards
        Platform.runLater(() -> {
            guiMain.chooseDrawCard(event);
        });
        //print board and updates points
        int[][][] cards = calculateSubMatrix(event.getBoard());
        List<int[]> sortedCoordinates = getSortedCoordinates(cards);

        //in case it is a PrintBoard of another player
        String regex = "(\\w+) \\((\\w+)\\) finished their turn, with a total of (\\d+) points";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(event.getParameter());

        if (matcher.find()) {
            String playerName = matcher.group(1);
            String tokenName = matcher.group(2);
            int endPoints = Integer.parseInt(matcher.group(3));
            if (!tokenName.equals(PlayerData.getToken())) {
                Platform.runLater(() -> {
                    guiMain.updatePlayerPoints(tokenName, endPoints);
                    guiMain.printPlayerBoard(playerName, tokenName, sortedCoordinates, cards);
                });
            }
            return;
        }

        //in case it is a PrintBoard of the current player
        regex = "Your points: (\\d+)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(event.getParameter());

        if (matcher.find()) {
            int points = Integer.parseInt(matcher.group(1));
            Platform.runLater(() -> {
                guiMain.updatePlayerPoints(PlayerData.getToken(), points);
            });
        }

        Platform.runLater(() -> {
            guiMain.printBoard(sortedCoordinates, cards);
        });
    }

    /**
     * Clears the top message in the GUI.
     */
    private void cleanTopMessage() {
        Platform.runLater(() -> {
            guiMain.printText("");
        });
    }

    /**
     * Handles the event to reconnect player data.
     *
     * @param event the event containing player data to reconnect
     */
    @Override
    public void reconnectPlayerData(Event event) {
        Platform.runLater(() -> {
            guiMain.reconnectPlayerData(event);
        });
    }

    /**
     * Gets the sorted coordinates for the cards on the board.
     *
     * @param cards the 3D array of cards
     * @return a list of sorted coordinates
     */
    public List<int[]> getSortedCoordinates(int[][][] cards) {
        List<int[]> coordinates = new ArrayList<>();
        List<int[]> negativeCoordinates = new ArrayList<>();
        List<int[]> sortedCoordinates = new ArrayList<>();

        for (int i = 0; i < cards.length; i++) {
            for (int j = 0; j < cards[i].length; j++) {
                if (cards[i][j][0] != 0) {
                    if (cards[i][j][0] < 0) {
                        negativeCoordinates.add(new int[]{i, j});
                    } else {
                        coordinates.add(new int[]{i, j});
                    }
                }
            }
        }

        for (int[] coord : coordinates) {
            insertInOrder(sortedCoordinates, coord, cards);
        }

        sortedCoordinates.addAll(negativeCoordinates);

        return sortedCoordinates;
    }

    /**
     * Inserts a coordinate in order into the sorted list.
     *
     * @param sortedCoordinates the list of sorted coordinates
     * @param coord the coordinate to insert
     * @param cards the 3D array of cards
     */
    private void insertInOrder(List<int[]> sortedCoordinates, int[] coord, int[][][] cards) {
        for (int i = 0; i < sortedCoordinates.size(); i++) {
            if (compareCards(coord, sortedCoordinates.get(i), cards)) {
                sortedCoordinates.add(i, coord);
                return;
            }
        }
        sortedCoordinates.add(coord);
    }

    /**
     * Compares two cards to determine their order based on a common corner.
     *
     * @param card1 the first card coordinate
     * @param card2 the second card coordinate
     * @param cards the 3D array of cards
     * @return true if card1 should come before card2, false otherwise
     */
    private boolean compareCards(int[] card1, int[] card2, int[][][] cards) {
        int commonCorner = getCommonCorner(card1, card2);
        if (commonCorner == -1)
            return false;
        int[] c1 = cards[card1[0]][card1[1]];
        return c1[commonCorner] == 0;
    }

    /**
     * Gets the common corner between two cards.
     *
     * @param card1 the first card coordinate
     * @param card2 the second card coordinate
     * @return the common corner index, or -1 if no common corner exists
     */
    private int getCommonCorner(int[] card1, int[] card2) {
        int rowDiff = card1[0] - card2[0];
        int colDiff = card1[1] - card2[1];
        if (rowDiff == 1 && colDiff == 1) {
            return 1;
        } else if (rowDiff == 1 && colDiff == -1) {
            return 2;
        } else if (rowDiff == -1 && colDiff == 1) {
            return 3;
        } else if (rowDiff == -1 && colDiff == -1) {
            return 4;
        }
        return -1;
    }

    /**
     * Calculates a sub-matrix from the provided matrix.
     *
     * @param matrix the original matrix
     * @return the sub-matrix
     */
    private int[][][] calculateSubMatrix(int[][][] matrix) {
        int[] indexes = {-1, matrix[0].length, 0, 0};//first row, first column, last row, last column
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j][0] != 0) {
                    if (indexes[0] == -1) {
                        indexes[0] = i;
                    }
                    if (indexes[1] > j) {
                        indexes[1] = j;
                    }
                    if (indexes[2] < i) {
                        indexes[2] = i;
                    }
                    if (indexes[3] < j) {
                        indexes[3] = j;
                    }
                }
            }
        }
        int numRows = indexes[2] - indexes[0] + 1;
        int numCols = indexes[3] - indexes[1] + 1;
        int[][][] subMatrix = new int[numRows][numCols][6];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                subMatrix[i][j] = matrix[indexes[0] + i][indexes[1] + j];
            }
        }
        return subMatrix;
    }
}
