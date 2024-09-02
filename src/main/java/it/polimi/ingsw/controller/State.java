package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.DisconnectedException;
import it.polimi.ingsw.exceptions.EarlyTerminationException;
import it.polimi.ingsw.exceptions.NoMoreCardToReplaceException;
import it.polimi.ingsw.exceptions.UnplayableCardException;
import it.polimi.ingsw.jsonData.DataParser;
import it.polimi.ingsw.listener.Event;
import it.polimi.ingsw.listener.EventListener;
import it.polimi.ingsw.listener.EventType;
import it.polimi.ingsw.model.DrawCardChoice;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.PlayField;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.Face;
import it.polimi.ingsw.model.cards.PlayableCard;
import it.polimi.ingsw.model.cards.StarterCard;
import it.polimi.ingsw.model.cards.Symbol;
import it.polimi.ingsw.server.CommunicationInterface;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the abstract state in the controller of the game.
 * This class provides common functionality for various states of the game.
 * @author Luca Gritti, Fabio Marco Floris, Marco Ferraresi
 */
public abstract class State implements EventListener {

    protected final Model model;
    protected EventManager eventManager;
    protected VirtualView vView;
    protected int index;
    protected String face;

    /**
     * Constructs a new State with the specified model.
     *
     * @param model the model of the game.
     */
    public State(Model model)
    {
        this.model = model;
        this.eventManager = new EventManager();

    }

    /**
     * Starts the phase of the game associated with this state.
     *
     * @throws EarlyTerminationException if the phase is terminated early.
     */
    public abstract void startPhase() throws EarlyTerminationException;

    /**
     * Handles the received event.
     *
     * @param event the event received.
     */
    @Override
    public abstract void receivedEvent(Event event);

    /**
     * Iterates through the ordered Round of all players and performs the specified action on each.
     *
     * @param playerAction the action to perform on each player.
     * @throws EarlyTerminationException if the action is terminated early.
     */
    protected void iteratePlayers(ThrowingConsumer<Player, EarlyTerminationException> playerAction) throws EarlyTerminationException{

        for (Player player : model.getRound()){
            String currentPlayer = player.getNickName();
            CommunicationInterface conn = vView.getConnections().get(currentPlayer);
            try {
                if (conn != null && conn.getValid() != false) {
                    try {
                        playerAction.accept(model.getPlayerByNickName(currentPlayer));
                    } catch (EarlyTerminationException e) {
                        throw new EarlyTerminationException(e.getMessage());
                    }
                } else if (conn != null && conn.getValid() == false) {
                    System.out.println("[State]: "  + currentPlayer + " hasn't reconnected yet.");
                }
                else System.out.println("[State]: " + currentPlayer + " is still offline.");
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Executes the turn for the specified player.
     *
     * @param currentPlayer the player whose turn it is.
     * @param playfield the playfield of the game.
     * @throws EarlyTerminationException if the turn is terminated early.
     */
    public void playTurn(Player currentPlayer, PlayField playfield) throws EarlyTerminationException {

        try {
            Event event = new Event(EventType.PRINT_BOARD, currentPlayer.getNickName());

            StringBuilder message= new StringBuilder();
            int[][][] matrix = visualizeBoardAndResources(message, currentPlayer, false);
            event.setParameter(currentPlayer.getNickName() + ", it's your turn\n" +
                    "these are your resources:\n"+message
                    +  "\nYour points: "+ currentPlayer.getPlayerPoints());

            event.setBoard(matrix);
            fillDrawChoices(playfield, event);
            eventManager.notifyEvent(event);

            playCard(currentPlayer);

            drawCard(currentPlayer, playfield);

        } catch (DisconnectedException e) {

            // Player could disconnect after playing a card, without drawing.
            //      -> AutoDraw.
            if(currentPlayer.getCardsInHand().size() < 3){
                try {
                    currentPlayer.draw(playfield.chooseCardToDraw(DrawCardChoice.RESOURCEDECK));
                } catch (NoMoreCardToReplaceException ex) {
                    try {
                        currentPlayer.draw(playfield.chooseCardToDraw(DrawCardChoice.GOLDDECK));
                    } catch (NoMoreCardToReplaceException exc) {
                        System.out.println(exc);
                    }
                }

            }

            System.out.println(e);
        }

    }

    /**
     * Handles the action of drawing a card for the specified player.
     *
     * @param currentPlayer the player drawing the card.
     * @param playfield the playfield of the game.
     * @throws DisconnectedException if the player gets disconnected.
     * @throws EarlyTerminationException if the action is terminated early.
     */
    protected void drawCard(Player currentPlayer, PlayField playfield) throws DisconnectedException, EarlyTerminationException {
        try {
            Thread.sleep(500);
            Event event5 = new Event(EventType.CHOOSE_DRAW_CARD, currentPlayer.getNickName());
            fillDrawChoices(playfield,event5);

            eventManager.notifyEvent(event5);
            System.out.println(currentPlayer.getNickName()+" chose to draw card "+ index);
            while (true){
                try {
                    switch (index){
                        case 1:
                            currentPlayer.draw(playfield.chooseCardToDraw(DrawCardChoice.RESOURCEDECK));
                            break;
                        case 2:
                            currentPlayer.draw(playfield.chooseCardToDraw(DrawCardChoice.GOLDDECK));
                            break;
                        case 3:
                            currentPlayer.draw(playfield.chooseCardToDraw(DrawCardChoice.FIRSTRESOURCE));
                            break;
                        case 4:
                            currentPlayer.draw(playfield.chooseCardToDraw(DrawCardChoice.SECONDRESOURCE));
                            break;
                        case 5:
                            currentPlayer.draw(playfield.chooseCardToDraw(DrawCardChoice.FIRSTGOLD));
                            break;
                        case 6:
                            currentPlayer.draw(playfield.chooseCardToDraw(DrawCardChoice.SECONDGOLD));
                            break;
                        default:
                            System.out.println("Big problems");
                            break;
                    }
                    break;
                } catch (NoMoreCardToReplaceException e){
                    Thread.sleep(300);
                    Event event6 = new Event(EventType.TEXT_MESSAGE, currentPlayer.getNickName());
                    event6.setParameter("The card you are trying to draw doesn't exist");
                    eventManager.notifyEvent(event6);

                    Thread.sleep(300);
                    Event event7 = new Event(EventType.CHOOSE_DRAW_CARD, currentPlayer.getNickName());
                    fillDrawChoices(playfield, event7);
                    eventManager.notifyEvent(event7);
                }
            }
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }

    }

    /**
     * Handles the action of playing a card for the specified player.
     *
     * @param currentPlayer the player playing the card.
     * @throws DisconnectedException if the player gets disconnected.
     * @throws EarlyTerminationException if the action is terminated early.
     */
    protected void playCard(Player currentPlayer) throws DisconnectedException, EarlyTerminationException {
        try {
            while( true ) {

                PlayableCard card;

                face = "TOSET";
                while(true) {

                    Event event1 = new Event(EventType.CHOOSE_PLAY_CARD, currentPlayer.getNickName());
                    event1.setN1(currentPlayer.getCardsInHand().get(0).getCardId());
                    event1.setN2(currentPlayer.getCardsInHand().get(1).getCardId());
                    event1.setN3(currentPlayer.getCardsInHand().get(2).getCardId());
                    event1.setParameter(currentPlayer.getNickName() + ", choose a card to play from your hand\n [ 1 ] / [ 2 ] / [ 3 ]\n");
                    eventManager.notifyEvent(event1);
                    System.out.println(currentPlayer.getNickName()+" choose card n° "+ index);

                    Event event2 = new Event(EventType.CHOOSE_FRONT_OR_BACK, currentPlayer.getNickName());
                    card = currentPlayer.getCardsInHand().get(index);
                    event2.setN1(card.getCardId());
                    event2.setParameter("Choose the face [ FRONT / BACK ] you want to place\n or reply " +
                            "[ RETURN ] if you want to choose another card");
                    eventManager.notifyEvent(event2);
                    System.out.println(currentPlayer.getNickName()+" choose side "+ face);

                    // Exit loop only when a face is chosen
                    if(!face.equals("RETURN")) break;
                }


                if(face.equals("BACK"))
                    card.setBack();


                Event event5 = new Event(EventType.PRINT_BOARD, currentPlayer.getNickName());
                StringBuilder message= new StringBuilder();
                int[][][] matrix = visualizeBoardAndResources(message, currentPlayer, true);
                event5.setParameter(currentPlayer.getNickName() + ", these are your resources:\n"+message);
                event5.setBoard(matrix);
                fillDrawChoices(model.getPlayfield(), event5);
                eventManager.notifyEvent(event5);


                Event event3 = new Event(EventType.CHOOSE_POSITION, currentPlayer.getNickName());
                message = new StringBuilder();
                message.append("You can play this card in the white positions\n > ");
                int i;
                for(i = 0; i < currentPlayer.getBoard().getFrontier().size() - 1; i++){
                    message.append(i + 1).append(", ");
                }
                message.append(i + 1);
                event3.setParameter(message.toString());
                event3.setN1(currentPlayer.getBoard().getFrontier().size());
                eventManager.notifyEvent(event3);
                System.out.println(currentPlayer.getNickName()+" choose position "+ index);

                try {
                    currentPlayer.playCard(card, currentPlayer.getBoard().getFrontier().get(index));

                    break;
                } catch (UnplayableCardException e) {
                    Thread.sleep(300);
                    Event event4 = new Event(EventType.TEXT_MESSAGE, currentPlayer.getNickName());
                    event4.setParameter("You tried to put a gold card that doesn't respect its condition");
                    eventManager.notifyEvent(event4);
                }

            }
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }

    }

    /**
     * Sets the listener for the current state. Also, sets the virtualView.
     *
     * @param virtualView the virtual view to set as the listener.
     */
    public void setListenerVirtualView(EventListener virtualView){
        eventManager.setListeners(virtualView);
        this.vView = (VirtualView) virtualView;
    }

    protected void fillDrawChoices(PlayField playfield, Event event) {
        event.setN1(playfield.lastCardResourceDeckId());
        event.setN2(playfield.lastCardGoldDeckId());
        if(playfield.getFirstResource() == null)
            event.setN3(0);
        else
            event.setN3(playfield.getFirstResource().getCardId());
        if(playfield.getSecondResource() == null)
            event.setN4(0);
        else
            event.setN4(playfield.getSecondResource().getCardId());
        if(playfield.getFirstGold() == null)
            event.setN5(0);
        else
            event.setN5(playfield.getFirstGold().getCardId());
        if(playfield.getSecondGold() == null)
            event.setN6(0);
        else
            event.setN6(playfield.getSecondGold().getCardId());
    }
    /**
     * Completes the setup choices for an offline player.
     *
     * @param offlinePlayer the offline player.
     */
    private void completeSetupChoices(Player offlinePlayer){

        if(offlinePlayer.getToken()==null){
            offlinePlayer.setToken(model.getAvailableTokens().removeFirst());
        }
        if(offlinePlayer.getObjectiveCard()==null){
            offlinePlayer.setObjectiveCard(model.getPlayfield().getObjectiveFromDeck());
        }
        if(offlinePlayer.getCardsInHand().size()<3){
            try {
                if(offlinePlayer.getCardsInHand().isEmpty()){
                    offlinePlayer.draw(model.getPlayfield().chooseCardToDraw(DrawCardChoice.RESOURCEDECK));
                    offlinePlayer.draw(model.getPlayfield().chooseCardToDraw(DrawCardChoice.GOLDDECK));
                }
                offlinePlayer.draw(model.getPlayfield().chooseCardToDraw(DrawCardChoice.RESOURCEDECK));

            } catch (NoMoreCardToReplaceException e) {
                throw new RuntimeException(e);
            }
        }
        if (offlinePlayer.getBoard().getMapSize() < 1){
            Integer idCard = model.getAvailableStarters().removeFirst();
            StarterCard card = DataParser.getStarterCard(idCard, Face.BACK);
            offlinePlayer.placeStarterOnBoard(card);
        }
    }

    /**
     * Notifies all the Other Players that it is the Current Player's turn.
     *
     * @param currentPlayer the current player.
     * @throws EarlyTerminationException if the notification is terminated early.
     */
    public void notifyCurrentPlayerTurn(Player currentPlayer) throws EarlyTerminationException {
        HashMap<String, CommunicationInterface> connections = vView.getConnections();

        for (Map.Entry<String, CommunicationInterface> entry : connections.entrySet()) {
            String player = entry.getKey();
            CommunicationInterface conn = entry.getValue();
            if (conn != null) {
                try {

                    Event event2 = new Event(EventType.PRINT_BOARD, player);

                    StringBuilder message= new StringBuilder();
                    int[][][] matrix = visualizeBoardAndResources(message, currentPlayer, false);
                    if(currentPlayer.getNickName().equals(player)){
                        event2.setParameter("you have finished your turn, Your points: " + currentPlayer.getPlayerPoints() + "\n" + "your resources and board:\n" + message);
                    }else{
                        event2.setParameter(currentPlayer.getNickName() + " ("+currentPlayer.getToken().name()+") finished their turn, with a total of " + currentPlayer.getPlayerPoints() + " points\n" + currentPlayer.getNickName() + "'s resources and board:\n" + message);
                    }
                    event2.setBoard(matrix);
                    fillDrawChoices(model.getPlayfield(), event2);


                    try {
                        eventManager.notifyEvent(event2);
                    } catch (DisconnectedException ignored) {
                    }

                } catch (EarlyTerminationException e) {
                    throw new EarlyTerminationException(e.getMessage());
                }
            } else System.out.println("[State]: " + currentPlayer + " is still offline.");
        }
    }

    /**
     * Reconnects a player to the game.
     *
     * @param nicknamePlayer the nickname of the player to reconnect.
     * @throws DisconnectedException if the player gets disconnected.
     * @throws EarlyTerminationException if the reconnection is terminated early.
     */
    public void reconnectPlayer(String nicknamePlayer) throws DisconnectedException, EarlyTerminationException {

        Player offlinePlayer = null;

        for (Player player : model.getRound()){
            if(player.getNickName().equals(nicknamePlayer))
                offlinePlayer = player;
        }

        if(offlinePlayer == null){
            System.out.println("player not found");
            return;
        } else
            completeSetupChoices(offlinePlayer);

        Event eventReconnect = new Event(EventType.RECONNECT_PLAYER, nicknamePlayer);
        eventReconnect.setParameter(nicknamePlayer + ", we are sending you all your data. Keep patience!");
        if (offlinePlayer.getStarterCard() != null)
            eventReconnect.setN1(offlinePlayer.getStarterCard().getCardId());

        eventReconnect.setN2(offlinePlayer.getCardsInHand().get(0).getCardId());
        eventReconnect.setN3(offlinePlayer.getCardsInHand().get(1).getCardId());
        eventReconnect.setN4(offlinePlayer.getCardsInHand().get(2).getCardId());

        //----------------JSON-----------------------------------------

        JSONObject data = new JSONObject();
        data.put("token", offlinePlayer.getToken().toString());
        data.put("personalObj", offlinePlayer.getObjectiveCard().getId());
        data.put("playfieldObj1", model.getPlayfield().getFirstObjective().getId());
        data.put("playfieldObj2", model.getPlayfield().getSecondObjective().getId());

        ArrayList<PlayableCard> cards = new ArrayList<>(offlinePlayer.getBoard().getMap().values());
        JSONObject jsonMap = new JSONObject();
        for (PlayableCard card : cards) {
            jsonMap.put(card.getCardId(), card.getSide().toString());
        }
        data.put("faces", jsonMap);

        //-------------------------------------------------------------

        ArrayList<String> strings = new ArrayList<>();
        strings.addFirst(data.toJSONString());

        eventReconnect.setTokens(strings);

        eventManager.notifyEvent(eventReconnect);

        System.out.println("[State]: "+ nicknamePlayer +"'s Data sent!");
    }
    /**
     * Sends to Other Players the message of what Current Player is doing.
     *
     * @param currentPlayer the current player.
     * @param print the message to display while waiting.
     * @throws EarlyTerminationException if the wait is terminated early.
     */
    public void waitOtherChoice(Player currentPlayer, String print) throws EarlyTerminationException {
        HashMap<String, CommunicationInterface> connections = vView.getConnections();

        for (Map.Entry<String, CommunicationInterface> entry : connections.entrySet()) {
            String player = entry.getKey();
            CommunicationInterface conn = entry.getValue();
            if (conn != null) {
                if (!currentPlayer.getNickName().equals(player)) {
                    Event event1 = new Event(EventType.TEXT_MESSAGE, player);
                    event1.setParameter(print);
                    try {
                        eventManager.notifyEvent(event1);
                    } catch (DisconnectedException ignored) {
                    }
                }
            }
        }
    }

    /**
     * Visualizes the board and resources of the specified player.
     *
     * @param message the message to append the resource information to.
     * @param currentPlayer the player whose board and resources are to be visualized.
     * @param visualizeFrontier whether to visualize the frontier of the player's board.
     * @return a 3D array representing the player's board.
     */
    public int[][][] visualizeBoardAndResources(StringBuilder message, Player currentPlayer, boolean visualizeFrontier){
        boolean found= false;
        for (Map.Entry<Symbol,Integer> resource : currentPlayer.getBoard().getResources().entrySet()){
            if (resource.getValue() != 0) {
                switch (resource.getKey()) {
                    case FUNGI -> message.append("🍄".repeat(resource.getValue()));
                    case ANIMAL -> message.append("🐺".repeat(resource.getValue()));
                    case INSECT -> message.append("🦋".repeat(resource.getValue()));
                    case PLANT -> message.append("🌿".repeat(resource.getValue()));
                    case INKWELL -> message.append("🫙".repeat(resource.getValue()));
                    case QUILL -> message.append("🪶".repeat(resource.getValue()));
                    case MANUSCRIPT -> message.append("📜".repeat(resource.getValue()));
                }
                found = true;
                message.append("\n");
            }
        }
        if(!found)
            message.append("no resources");

        int[][][] matrix = currentPlayer.getBoard().compressBoard();

        if (visualizeFrontier)
            for(int j = 0; j < currentPlayer.getBoard().getFrontier().size(); j++)
                matrix[currentPlayer.getBoard().getFrontier().get(j).getX()][currentPlayer.getBoard().getFrontier().get(j).getY()][0] = (j +1)*(-1);

        return matrix;
    }


}