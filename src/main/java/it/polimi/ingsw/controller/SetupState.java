package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.DisconnectedException;
import it.polimi.ingsw.exceptions.EarlyTerminationException;
import it.polimi.ingsw.exceptions.NoMoreCardToReplaceException;
import it.polimi.ingsw.jsonData.DataParser;
import it.polimi.ingsw.listener.Event;
import it.polimi.ingsw.listener.EventType;
import it.polimi.ingsw.model.DrawCardChoice;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Token;
import it.polimi.ingsw.model.cards.Face;
import it.polimi.ingsw.model.cards.GoldCard;
import it.polimi.ingsw.model.cards.ResourceCard;
import it.polimi.ingsw.model.cards.StarterCard;
import it.polimi.ingsw.model.objectives.ObjectiveCard;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * This class represents the setup state of the game, where initial configurations
 * and player choices are made before the main gameplay begins.
 * @author Luca Gritti, Marco Ferraresi, Fabio Marco Floris
 */
public class SetupState extends State{

    private String token;
    private String face;
    private int index;

    /**
     * Constructor for SetupState.
     *
     * @param model The game model.
     */
    public SetupState(Model model) {
        super(model);
    }

    /**
     * Starts the setup phase of the game. It performs these actions:
     *  1. Loads resource, gold, and objective cards from a JSON file.
     *  2. Initializes the playfield with these decks.
     *  3. Notifies all players that the game has started and enters the setup phase.
     *  4. Prompts each player to choose a token.
     *  5. Assigns a starter card to each player and allows them to choose the face of the card.
     *  6. Distributes resource and gold cards to each player.
     *  7. Allows each player to choose an objective card from two options provided.
     *
     * @throws EarlyTerminationException if the setup phase is terminated early.
     */
    @Override
    public void startPhase() throws EarlyTerminationException {

        // LOAD FROM JSON!
        ArrayList<ResourceCard> resDeck = DataParser.getResourceCards();
        ArrayList<GoldCard> golDeck = DataParser.getGoldCards();
        ArrayList<ObjectiveCard> objDeck = DataParser.getObjectiveCards();


        // GIVE THE PLAYFIELD THE DECKS!
        model.initializePlayField(resDeck, golDeck, objDeck);

        // NOTIFY forall players START OF THE GAME: SETUP STATE
        iteratePlayers(
                player -> {
                    try {
                        Event eventOK = new Event(EventType.TEXT_MESSAGE, player.getNickName());
                        eventOK.setParameter("\n\nGAME STARTED!\n\nNow it's Setup Phase: each player gotta make few initial choices\n\n");
                        eventManager.notifyEvent(eventOK);

                    } catch (EarlyTerminationException | DisconnectedException e) {
                        System.out.println("[SetupState]: " + e.getMessage());
                    }
                }
        );

        // LET PLAYERS CHOOSE THEIR TOKEN
        iteratePlayers(
                player -> {
                    try {
                        String print = "\n\nYou have to wait, " + player.getNickName() + " is choosing their token";
                        waitOtherChoice(player, print);

                        Event event = new Event(EventType.CHOOSE_TOKEN, player.getNickName());
                        event.setParameter(player.getNickName() + ", choose a token from the remaining list!\n" + model.getAvailableTokens());
                        event.setTokens(new ArrayList<>(model.getAvailableTokens().stream().map(Enum::name).collect(Collectors.toList())));
                        eventManager.notifyEvent(event);


                        while (!model.getAvailableTokens().contains(Token.valueOf(token))) {
                            event.setParameter("Cool color but reply with a token from the list...\n" + model.getAvailableTokens());
                            eventManager.notifyEvent(event);
                        }

                        player.setToken(Token.valueOf(token));
                        model.getAvailableTokens().remove(Token.valueOf(token));
                        System.out.println(player.getNickName() + " chose token " + token);

                    } catch (EarlyTerminationException e) {
                        throw new EarlyTerminationException(e.getMessage());
                    } catch (DisconnectedException e) {
                        System.out.println("[SetupState]: " + e.getMessage());
                    }
                }
        );


        // GIVE THE PLAYERS A STARTER CARD!
        //      (and suddenly create the board
        //              with the starter cards)
        iteratePlayers(
                currentPlayer -> {
                    try {
                        String print = "\n\nYou have to wait, " + currentPlayer.getNickName() + " is choosing the face of the starter card";
                        waitOtherChoice(currentPlayer, print);

                        Integer idCard = model.getAvailableStarters().getLast(); // retrieves the ID of a random starter card
                        System.out.println(currentPlayer.getNickName() + " got Starter Card " + idCard);
                        Event event = new Event(EventType.CHOOSE_FACE_STARTER, currentPlayer.getNickName());

                        event.setN1(idCard);
                        event.setParameter(currentPlayer.getNickName() + ", choose the card's face you want to place:\n[ FRONT ] / [ BACK ]");
                        eventManager.notifyEvent(event);

                        StarterCard card = DataParser.getStarterCard(idCard, Face.valueOf(face));
                        System.out.println(currentPlayer.getNickName() + " chose face " + face);

                        currentPlayer.placeStarterOnBoard(card);
                        model.getAvailableStarters().remove(idCard);
                    } catch (EarlyTerminationException e) {
                        throw new EarlyTerminationException(e.getMessage());
                    } catch (DisconnectedException e) {
                        System.out.println("[SetupState]: " + e.getMessage());
                    }
                }
        );


        // GIVE THE PLAYERS 2 RESOURCE & 1 GOLD!
        iteratePlayers(
                currentPlayer -> {
                    try {
                        try {
                            currentPlayer.draw(model.getPlayfield().chooseCardToDraw(DrawCardChoice.RESOURCEDECK));
                            currentPlayer.draw(model.getPlayfield().chooseCardToDraw(DrawCardChoice.RESOURCEDECK));
                            currentPlayer.draw(model.getPlayfield().chooseCardToDraw(DrawCardChoice.GOLDDECK));
                        } catch (NoMoreCardToReplaceException e) {
                            throw new RuntimeException(e);
                        }

                        Event event1 = new Event(EventType.PRINT_HAND, currentPlayer.getNickName());
                        event1.setN1(currentPlayer.getCardsInHand().get(0).getCardId());
                        event1.setN2(currentPlayer.getCardsInHand().get(1).getCardId());
                        event1.setN3(currentPlayer.getCardsInHand().get(2).getCardId());
                        event1.setParameter("\n\n" + currentPlayer.getNickName() + ", this is your current hand: \n");
                        eventManager.notifyEvent(event1);
                    } catch (EarlyTerminationException e) {
                        throw new EarlyTerminationException(e.getMessage());
                    } catch (DisconnectedException e) {
                        System.out.println("[SetupState]: " + e.getMessage());
                    }
                }
        );


        // GIVE THE PLAYERS 2 OBJECTIVE AND LET THEM
        //          CHOOSE ONLY ONE
        iteratePlayers(
                currentPlayer -> {
                    try {
                        ArrayList<ObjectiveCard> objCardsToChoose = new ArrayList<>();
                        objCardsToChoose.addLast(objDeck.removeLast());
                        objCardsToChoose.addLast(objDeck.removeLast());

                        String print = "\n\nYou have to wait,  " + currentPlayer.getNickName() + " is choosing their objective card";
                        waitOtherChoice(currentPlayer, print);

                        // send to the client the two Objective Cards
                        // let the client choose one
                        Event event = new Event(EventType.CHOOSE_OBJECTIVES, currentPlayer.getNickName());
                        event.setParameter("\n\n" + currentPlayer.getNickName() + ", choose your objective card: ");
                        event.setN1(objCardsToChoose.getFirst().getId());
                        event.setN2(objCardsToChoose.get(1).getId());
                        eventManager.notifyEvent(event);

                        // set the selected objective
                        ObjectiveCard chosenObjective = objCardsToChoose.get(index);
                        currentPlayer.setObjectiveCard(chosenObjective);

                        // send the Client all three objective cards, to be stored in the client.
                        Event eventSendObj = new Event(EventType.SEND_OBJECTIVES, currentPlayer.getNickName());
                        eventSendObj.setParameter("\n\n" + currentPlayer.getNickName() + ", these are your's and playfield's Objective Cards ");
                        eventSendObj.setN1(chosenObjective.getId());
                        eventSendObj.setN2(model.getPlayfield().getFirstObjective().getId());
                        eventSendObj.setN3(model.getPlayfield().getSecondObjective().getId());
                        eventManager.notifyEvent(eventSendObj);

                    } catch (EarlyTerminationException e) {
                        throw new EarlyTerminationException(e.getMessage());
                    } catch (DisconnectedException e) {
                        System.out.println("[SetupState]: " + e.getMessage());
                    }
                }
        );


        // finished phase.

    }


    /**
     * Handles received events during the setup phase.
     *
     * @param event The event to handle.
     */
    @Override
    public void receivedEvent (Event event){
        switch (event.getEvent()){
            case CHOOSE_TOKEN:
                token = event.getParameter().toUpperCase();
                break;
            case TEXT_MESSAGE:
                break;
            case SEND_OBJECTIVES:
                break;
            case CHOOSE_FACE_STARTER:
                face = event.getParameter();
                break;
            case CHOOSE_OBJECTIVES:
                index = event.getN1() - 1;
                break;
            case PRINT_HAND:
                break;
            case PING:
                break;
            case RECONNECT_PLAYER:
                System.out.println("[SetupState]: Player reconnected!");
                break;
            default:
                System.out.println("[SetupState]: Big problems");
                break;

        }

    }

}
