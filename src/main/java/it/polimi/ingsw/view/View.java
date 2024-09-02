package it.polimi.ingsw.view;

import it.polimi.ingsw.client.PlayerData;
import it.polimi.ingsw.listener.Event;
import it.polimi.ingsw.view.gui.GUIMain;
import javafx.application.Application;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * The View class represents the view component in a model-view-controller (MVC) architecture.
 * It is responsible for the user interface (UI) interactions and displaying information to the user.
 * It communicates with the virtualView on the server
 * @author Marco Ferraresi, Angelo De Nadai, Fabio Marco Floris, Luca Gritti
 */
public class View {

    UserInterface UI;

    /**
     * Constructs a new {@code View} instance with the specified UI choice.
     * If the choice is 1, it initializes a graphical user interface (GUI);
     * otherwise, a text-based user interface (TUI).
     *
     * @param choice the integer representing the user's choice of interface
     */
    public View(int choice) {
        if (choice == 1) {
            new Thread(() -> Application.launch(GUIMain.class)).start();
            UI = new GUI();
        } else {
            UI = new TUI();
        }
    }

    /**
     * Manages an event by invoking the appropriate UI method based on the event type.
     *
     * @param event the event to be managed
     * @return the same event that was passed in, after being processed
     */
    public Event manageEvent(Event event) {

        switch (event.getEvent()) {

            case TEXT_MESSAGE, TIMEOUT:
                UI.printText(event.getParameter());
                break;

            case PRINT_TITLE_AND_CREDITS:
                UI.printTitleCredits();
                break;

            case PRINT_HAND:
                UI.printHand(event);
                break;

            case PRINT_BOARD:
                UI.printBoard(event);
                break;

            case CHOOSE_NICKNAME:
                UI.chooseNickname(event);
                break;

            case CHOOSE_CONNECT_OR_CREATE_LOBBY:
                UI.chooseConnectOrCreateLobby(event);
                break;

            case SELECT_NUMBERS_PLAYERS:
                UI.selectNumbersPlayers(event);
                break;

            case ID_MATCH:
                UI.idMatch(event);
                break;

            case JOIN_LOBBY:
                UI.joinLobby(event);
                break;

            case PLAYER_JOINED:
                UI.playerJoined(event);
                break;

            case CHOOSE_TOKEN:
                UI.chooseToken(event);
                break;

            case CHOOSE_FACE_STARTER:
                UI.chooseFaceStarter(event);
                break;

            case CHOOSE_OBJECTIVES:
                UI.chooseObjectives(event);
                break;

            case SEND_OBJECTIVES:
                UI.sendObjectives(event);
                break;

            case CHOOSE_PLAY_CARD:
                UI.choosePlayCard(event);
                break;

            case CHOOSE_FRONT_OR_BACK:
                UI.chooseFrontOrBack(event);
                break;

            case CHOOSE_POSITION:
                UI.choosePosition(event);
                break;

            case CHOOSE_DRAW_CARD:
                UI.chooseDrawCard(event);
                break;

            case RECONNECT_PLAYER:
                // ----------- JSON DEPARSING ----------
                String jsonString = event.getTokens().getFirst() ;
                JSONParser parser = new JSONParser();

                try {
                    JSONObject jsonObject = (JSONObject) parser.parse(jsonString);

                    // Create a HashMap to store the parsed data
                    Map<Integer, String> map = new HashMap<>();

                    for (Object idObj : ((JSONObject)jsonObject.get("faces")).keySet()) {
                        Integer idCard = Integer.parseInt((String) idObj);
                        String face = (String) ((JSONObject)jsonObject.get("faces")).get(idObj);
                        map.put(idCard, face);
                    }


                    // ----------- RESETTING PLAYER DATA ----------
                    PlayerData.setCards(new ArrayList<>(map.keySet()));
                    PlayerData.setFaces(map);
                    PlayerData.setPersonalObj(((Long) jsonObject.get("personalObj")).intValue());
                    PlayerData.setPlayfieldObj1(((Long) jsonObject.get("playfieldObj1")).intValue());
                    PlayerData.setPlayfieldObj2(((Long) jsonObject.get("playfieldObj2")).intValue());
                    PlayerData.setToken((String) jsonObject.get("token"));

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                UI.reconnectPlayerData(event);
                break;

            case RETURN_TO_THE_MENU:
                break;
        }

        return event;
    }
}
