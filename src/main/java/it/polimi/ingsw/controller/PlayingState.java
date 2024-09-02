package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.DisconnectedException;
import it.polimi.ingsw.exceptions.EarlyTerminationException;
import it.polimi.ingsw.listener.Event;
import it.polimi.ingsw.listener.EventType;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.PlayField;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Token;

import java.util.ArrayList;
import java.util.Collections;


/**
 * This class represents the playing state of the game, where the core gameplay occurs,
 * including player actions and turn management.
 *
 * @author Luca Gritti, Marco Ferraresi
 */
public class PlayingState extends State{

    /**
     * Constructor for PlayingState.
     *
     * @param model The game model.
     */
    public PlayingState(Model model) {
        super(model);
    }

    /**
     * Starts the playing phase of the game. It performs the following steps:
     *  Each player in round does:
     *     1st action. Play a card from hand
     *       2nd action. Draw a card from playfield
     *  Every action check the Endgame Conditions.
     *
     * @throws EarlyTerminationException if the playing phase is terminated early.
     */
    @Override
    public void startPhase() throws EarlyTerminationException {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // RANDOMLY CHOOSE THE 1ST PLAYER!
        sortPlayerRound();

        Player currentPlayer = model.getCurrentPlayer();
        PlayField playfield = model.getPlayfield();

        // loop until Endgame conditions are true.
        while (!(currentPlayer.getPlayerPoints() >= 20 || (playfield.getGoldDeckSize() == 0 && playfield.getResourceDeckSize() == 0))) {

            String print = "\n\nYou have to wait, it's "+currentPlayer.getNickName()+"'s turn!";
            waitOtherChoice(currentPlayer, print);

            playTurn(currentPlayer, playfield);

            notifyCurrentPlayerTurn(currentPlayer);

            model.changeTurn();

            if (currentPlayer.getPlayerPoints() >= 20 || (playfield.getGoldDeckSize() == 0 && playfield.getResourceDeckSize() == 0)) {
                // current player: the very one that just made true the Endgame Conditions.
                break;
            }

            currentPlayer = model.getCurrentPlayer();
        }

        // phase ended.
    }

    /**
     * Randomly sorts the player round and assigns the first player token.
     *
     * @throws EarlyTerminationException if the player round sorting is terminated early.
     */
    private void sortPlayerRound() throws EarlyTerminationException {

        ArrayList<Player> round = model.getRound();
        Collections.shuffle(round);
        model.setRound(round);

        round.getFirst().setFirstPlayerToken(Token.BLACK);
        try {
            Event event = new Event(EventType.TEXT_MESSAGE, round.getFirst().getNickName());
            event.setParameter("\n\n\n" + round.getFirst().getNickName() + ", you are the FIRST player!\n You have now received the BLACK token\n");
            eventManager.notifyEvent(event);
        } catch (DisconnectedException ignored) {
        }

        for (int i = 1; i < round.size(); i++){
            Event event1 = new Event(EventType.TEXT_MESSAGE, round.get(i).getNickName());
            event1.setParameter("\n\n\n" + round.get(i).getNickName() + ", you are the " + ((int) i + 1) + " player!\n");
            try {
                eventManager.notifyEvent(event1);
            } catch (DisconnectedException ignored) {
            }
        }
    }


    /**
     * Handles received events during the playing phase.
     *
     * @param event The event to handle.
     */
    @Override
    public void receivedEvent(Event event) {
        switch (event.getEvent()){
            case TEXT_MESSAGE:
                break;
            case CHOOSE_PLAY_CARD, CHOOSE_POSITION:
                index = event.getN1() - 1;
                break;
            case CHOOSE_FRONT_OR_BACK:
                face = event.getParameter();
                break;
            case CHOOSE_DRAW_CARD:
                index = event.getN1();
                break;
            case PRINT_HAND:
                break;
            case PRINT_BOARD:
                break;
            case PING:
                break;
            case RECONNECT_PLAYER:
                System.out.println("[PlayingState]: Player reconnected!");
                break;
            default:
                System.out.println("Big problems");
                break;
        }
    }
}
