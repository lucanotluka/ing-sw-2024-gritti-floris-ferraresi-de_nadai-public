package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.DisconnectedException;
import it.polimi.ingsw.exceptions.EarlyTerminationException;
import it.polimi.ingsw.listener.Event;
import it.polimi.ingsw.listener.EventType;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.PlayField;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.objectives.ObjectiveCard;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * This class represents the end game state of the game, where the final rounds and scoring occur.
 *
 * @author Luca Gritti, Marco Ferraresi
 */
public class EndGameState extends State{

    /**
     * Constructor for EndGameState.
     *
     * @param model The game model.
     */
    public EndGameState(Model model) {
        super(model);
    }


    /**
     * Starts the endgame phase of the game. It performs the following actions:
     *  when a Player reaches at least 20 pts OR
     *  both decks of the playfield are empty:
     *             1. the current round is executed, no matter who
     *             the player is.
     *
     *             2. a final round, from the first
     *                  player to the last one, is played.
     *             All the points from the hidden Objective Cards
     *             are added to every Player's points.
     *
     *      The Player with maximum points wins.
     *      (Could be more than one Player).
     *
     * @throws EarlyTerminationException if the endgame phase is terminated early.
     */
    @Override
    public void startPhase() throws EarlyTerminationException {

        Player currentPlayer = model.getCurrentPlayer();
        PlayField playfield = model.getPlayfield();

        iteratePlayers(player -> {
            Event eventEndGame = new Event(EventType.TEXT_MESSAGE, player.getNickName());
            eventEndGame.setParameter("\n\n /// \\\\\\ It's ENDGAME now. /// \\\\\\ \n\n");
            try {
                eventManager.notifyEvent(eventEndGame);
            } catch (DisconnectedException ignored) {
            }
        });

        // first of all:    END the CURRENT ROUND!
        while (!currentPlayer.equals(model.getRound().getFirst())) {

            try {
                Event eventLastTurn = new Event(EventType.TEXT_MESSAGE, currentPlayer.getNickName());
                eventLastTurn.setParameter("\n\n\n"+currentPlayer.getNickName() + ", you need to do ONE ROUND before the Last Round.\n\n\n");
                eventManager.notifyEvent(eventLastTurn);


                // DONT play the actual Turn: only PlayCard().
                if (playfield.getGoldDeckSize() == 0 && playfield.getResourceDeckSize() == 0
                        && playfield.getFirstResource() == null && playfield.getSecondResource() == null
                        && playfield.getFirstGold() == null && playfield.getSecondGold() == null){

                    Event event = new Event(EventType.PRINT_BOARD, currentPlayer.getNickName());

                    StringBuilder message= new StringBuilder();
                    int[][][] matrix = visualizeBoardAndResources(message, currentPlayer, false);
                    event.setParameter(currentPlayer.getNickName() + ", it's your turn\n" +
                            "these are your resources:\n"+message
                            +  "\nYour points: "+ currentPlayer.getPlayerPoints());

                    event.setBoard(matrix);
                    fillDrawChoices(playfield, event);
                    eventManager.notifyEvent(event);

                    String print = "\n\nYou have to wait, it's "+ currentPlayer.getNickName() +" turn !";
                    waitOtherChoice(currentPlayer, print);

                    playCard(currentPlayer);


                    Event event1 = new Event(EventType.TEXT_MESSAGE, currentPlayer.getNickName());
                    event1.setParameter("You can't draw any cards, the play field is empty!");
                    eventManager.notifyEvent(event1);
                }
                else{   // PLAY the actual Turn: playCard() and drawCard()!

                    String print = "\n\nYou have to wait, it's "+ currentPlayer.getNickName() +" turn !";
                    waitOtherChoice(currentPlayer, print);

                    playTurn(currentPlayer, playfield);
                }

                notifyCurrentPlayerTurn(currentPlayer);

            } catch (DisconnectedException ignored) {
            }

            model.changeTurn(); // next player!
            currentPlayer = model.getCurrentPlayer();
        }


        ArrayList<Player> leagueTable = new ArrayList<>();
        // suddenly, one LAST ROUND
        iteratePlayers(player ->{
            try {
                Event eventOK = new Event(EventType.TEXT_MESSAGE, player.getNickName());
                eventOK.setParameter("\n\n  --------- LAST ROUND! ---------  \n\n");
                eventManager.notifyEvent(eventOK);

                if (playfield.getGoldDeckSize() == 0 && playfield.getResourceDeckSize() == 0
                        && playfield.getFirstResource() == null && playfield.getSecondResource() == null
                        && playfield.getFirstGold() == null && playfield.getSecondGold() == null){

                    Event event = new Event(EventType.PRINT_BOARD, player.getNickName());

                    StringBuilder message= new StringBuilder();
                    int[][][] matrix = visualizeBoardAndResources(message, player, false);
                    event.setParameter(player.getNickName() + ", it's your turn\n" +
                            "these are your resources:\n"+message
                            +  "\nYour points: "+ player.getPlayerPoints());

                    event.setBoard(matrix);
                    fillDrawChoices(playfield, event);
                    eventManager.notifyEvent(event);

                    String print = "\n\nYou have to wait, it's not your turn !";
                    waitOtherChoice(player, print);

                    playCard(player);

                    Event event1 = new Event(EventType.TEXT_MESSAGE, player.getNickName());
                    event1.setParameter("You can't draw any cards, the play field is empty !");
                    eventManager.notifyEvent(event1);


                }
                else{
                    String print = "\n\nYou have to wait, it's not your turn !";
                    waitOtherChoice(player, print);

                    playTurn(player, playfield);
                }
            } catch (DisconnectedException e) {
                System.out.println(e);
            }


            // Notify all the Players that Current Player has finished
            notifyCurrentPlayerTurn(player);

            // count all the Current player's hidden Objective Cards's points
            countHiddenObjectivePoints(player, playfield);

            try {
                Event eventCountPoints = new Event(EventType.TEXT_MESSAGE, player.getNickName());
                eventCountPoints.setParameter("\nYou have a total of "+player.getPlayerPoints()
                    + " points and you completed the Objective Cards "+ player.getCount()+" times in total. Good job!\n");

                eventManager.notifyEvent(eventCountPoints);
            } catch (DisconnectedException ignored) {
            }

            leagueTable.add(player);
        });

        StringBuilder leaderboardString = createLeaderboard(leagueTable);

        iteratePlayers(player -> {
            try {

                Event eventOK = new Event(EventType.TEXT_MESSAGE, player.getNickName());
                eventOK.setParameter(String.valueOf(leaderboardString));
                eventManager.notifyEvent(eventOK);

                Thread.sleep(1000);
                Event event = new Event(EventType.TEXT_MESSAGE, player.getNickName());
                event.setParameter("\nThe game is finished, now you will return on the menu.\n\n");
                eventManager.notifyEvent(event);
                Event event1 = new Event(EventType.RETURN_TO_THE_MENU, player.getNickName());
                eventManager.notifyEvent(event1);
            } catch (DisconnectedException | InterruptedException ignored) {
            }
        });


        // phase ended.
        // match finished.

    }


    /**
     * Creates the leaderboard string showing the final scores.
     *
     * @param leagueTable The list of players sorted by their scores.
     * @return The leaderboard string.
     */
    private StringBuilder createLeaderboard(ArrayList<Player> leagueTable) {
        // sorting players...
        // If points are equal, sort by count in descending order
        leagueTable.sort(Comparator.comparingInt(Player::getPlayerPoints).reversed().thenComparingInt(Player::getCount));

        // printing the league table: who won?
        StringBuilder leaderboardString = new StringBuilder("\n%%%%%%%%%%%% SCOREBOARD %%%%%%%%%%%%\n");
        int i = 1;
        while(!leagueTable.isEmpty()){
            Player player = leagueTable.getFirst();
            String playerName = player.getNickName();
            int playerPoints = player.getPlayerPoints();

            // Append the player's name and points to the leaderboard string
            leaderboardString.append(String.format("%d. (%d points) %s", i, playerPoints, playerName));
            leagueTable.removeFirst();
            while ( !leagueTable.isEmpty() && leagueTable.getFirst().getPlayerPoints() == playerPoints) {
                leaderboardString.append(String.format(" and %s", leagueTable.getFirst().getNickName()));
                leagueTable.removeFirst();
            }

            leaderboardString.append("\n");
            i++;
        }
        return leaderboardString;
    }

    /**
     * Handles received events during the endgame phase.
     *
     * @param event The event received.
     */
    @Override
    public void receivedEvent (Event event){
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
            case RETURN_TO_THE_MENU:
                break;
            case PRINT_BOARD:
                break;
            case PING:
                break;
            case RECONNECT_PLAYER:
                System.out.println("[EndGameState]: Player reconnected!");
                break;
            default:
                System.out.println("Big problems");
                break;

        }

    }

    /**
     * Counts the hidden objective points for the current player and updates their score.
     *
     * @param currentPlayer The current player.
     * @param playfield     The playfield.
     */
    private void countHiddenObjectivePoints(Player currentPlayer, PlayField playfield) {

        // player's objective
        ObjectiveCard card = currentPlayer.getObjectiveCard();

        // playfield's objectives
        ObjectiveCard card1 = playfield.getFirstObjective();
        ObjectiveCard card2 = playfield.getSecondObjective();

        // player's objective points calculation
        int count = card.checkCondition(currentPlayer.getBoard());
        int points = count * card.getPoints();
        System.out.println("Card " + card.getId() + " resolved " + count + " times, for a total of " + points + " points.");

        // playfield's objectives points calculation
        int count1 = card1.checkCondition(currentPlayer.getBoard());
        count += count1;
        points += card1.getPoints() * count1;
        System.out.println("Card " + card1.getId() + " resolved " + count1 + " times, for a total of " + card1.getPoints() * count1 + " points.");


        int count2 = card2.checkCondition(currentPlayer.getBoard());
        count += count2;
        points += card2.getPoints() * count2;
        System.out.println("Card " + card2.getId() + " resolved " + count2 + " times, for a total of " + card2.getPoints() * count2 + " points.");


        // setting the total Count of objectives card completed.
        currentPlayer.setCount(count);

        if (currentPlayer.getPlayerPoints() + points >= 29)
            currentPlayer.setPoints(29);
        else
            currentPlayer.updatePlayerPoints(points);

        System.out.println("Player resolved Objective Cards " + count + " times in total, for " + points + " points to be added.");
    }
}
