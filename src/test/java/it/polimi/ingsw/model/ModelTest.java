package it.polimi.ingsw.model;

import it.polimi.ingsw.jsonData.DataParser;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.objectives.ObjectiveCard;
import it.polimi.ingsw.model.objectives.PatternObjectiveCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {
    Model model;
    @BeforeEach
    void setUp(){
        ArrayList<Player> players = new ArrayList<>();
        Player player  = new Player("Marco");
        Player player1 = new Player("Luca");
        players.add(player);
        players.add(player1);
        model = new Model(players);
    }
    @Test
    void getPlayerByNickName() {
        Player player = new Player("Luca");
        assertEquals(player, model.getPlayerByNickName("Luca"));
    }

    @Test
    void initializePlayField() {
        ArrayList<ResourceCard> resDeck = createResourceDeck();
        ArrayList<GoldCard> goldDeck = createGoldDeck();
        ArrayList<ObjectiveCard> objDeck = createObjDeck();
        int firstResourceCardId;
        int secondResourceCardId;
        int firstGoldCardId;
        int secondGoldCardId;
        int firstObjectiveCardId;
        int secondObjectiveCardId;

        PlayField playField = new PlayField(resDeck, goldDeck, objDeck);

        resDeck = createResourceDeck();
        goldDeck = createGoldDeck();
        objDeck = createObjDeck();
        model.initializePlayField(resDeck, goldDeck, objDeck);
        if(playField.getFirstResource().getCardId() == model.getPlayfield().getFirstResource().getCardId())
            firstResourceCardId = playField.getFirstResource().getCardId();
        else
            firstResourceCardId = playField.getSecondResource().getCardId();

        if(playField.getSecondResource().getCardId() == model.getPlayfield().getSecondResource().getCardId())
            secondResourceCardId = playField.getSecondResource().getCardId();
        else
            secondResourceCardId = playField.getFirstResource().getCardId();

        if(playField.getFirstGold().getCardId() == model.getPlayfield().getFirstGold().getCardId())
            firstGoldCardId = playField.getFirstGold().getCardId();
        else
            firstGoldCardId = playField.getSecondGold().getCardId();

        if(playField.getSecondGold().getCardId() == model.getPlayfield().getSecondGold().getCardId())
            secondGoldCardId = playField.getSecondGold().getCardId();
        else
            secondGoldCardId = playField.getFirstGold().getCardId();

        if(playField.getFirstObjective().getId() == model.getPlayfield().getFirstObjective().getId())
            firstObjectiveCardId = playField.getFirstObjective().getId();
        else
            firstObjectiveCardId = playField.getSecondObjective().getId();

        if(playField.getSecondObjective().getId() == model.getPlayfield().getSecondObjective().getId())
            secondObjectiveCardId = playField.getSecondObjective().getId();
        else
            secondObjectiveCardId = playField.getFirstObjective().getId();

        assertEquals(firstResourceCardId, model.getPlayfield().getFirstResource().getCardId());
        assertEquals(secondResourceCardId, model.getPlayfield().getSecondResource().getCardId());
        assertEquals(firstGoldCardId, model.getPlayfield().getFirstGold().getCardId());
        assertEquals(secondGoldCardId, model.getPlayfield().getSecondGold().getCardId());
        assertEquals(firstObjectiveCardId, model.getPlayfield().getFirstObjective().getId());
        assertEquals(secondObjectiveCardId, model.getPlayfield().getSecondObjective().getId());
    }

    @Test
    void getPlayfield() {
        ArrayList<ResourceCard> resDeck = createResourceDeck();
        ArrayList<GoldCard> goldDeck = createGoldDeck();
        ArrayList<ObjectiveCard> objDeck = createObjDeck();
        int firstResourceCardId;
        int secondResourceCardId;
        int firstGoldCardId;
        int secondGoldCardId;
        int firstObjectiveCardId;
        int secondObjectiveCardId;

        PlayField playField = new PlayField(resDeck, goldDeck, objDeck);

        resDeck = createResourceDeck();
        goldDeck = createGoldDeck();
        objDeck = createObjDeck();
        model.initializePlayField(resDeck, goldDeck, objDeck);
        if(playField.getFirstResource().getCardId() == model.getPlayfield().getFirstResource().getCardId())
            firstResourceCardId = playField.getFirstResource().getCardId();
        else
            firstResourceCardId = playField.getSecondResource().getCardId();

        if(playField.getSecondResource().getCardId() == model.getPlayfield().getSecondResource().getCardId())
            secondResourceCardId = playField.getSecondResource().getCardId();
        else
            secondResourceCardId = playField.getFirstResource().getCardId();

        if(playField.getFirstGold().getCardId() == model.getPlayfield().getFirstGold().getCardId())
            firstGoldCardId = playField.getFirstGold().getCardId();
        else
            firstGoldCardId = playField.getSecondGold().getCardId();

        if(playField.getSecondGold().getCardId() == model.getPlayfield().getSecondGold().getCardId())
            secondGoldCardId = playField.getSecondGold().getCardId();
        else
            secondGoldCardId = playField.getFirstGold().getCardId();

        if(playField.getFirstObjective().getId() == model.getPlayfield().getFirstObjective().getId())
            firstObjectiveCardId = playField.getFirstObjective().getId();
        else
            firstObjectiveCardId = playField.getSecondObjective().getId();

        if(playField.getSecondObjective().getId() == model.getPlayfield().getSecondObjective().getId())
            secondObjectiveCardId = playField.getSecondObjective().getId();
        else
            secondObjectiveCardId = playField.getFirstObjective().getId();

        assertEquals(firstResourceCardId, model.getPlayfield().getFirstResource().getCardId());
        assertEquals(secondResourceCardId, model.getPlayfield().getSecondResource().getCardId());
        assertEquals(firstGoldCardId, model.getPlayfield().getFirstGold().getCardId());
        assertEquals(secondGoldCardId, model.getPlayfield().getSecondGold().getCardId());
        assertEquals(firstObjectiveCardId, model.getPlayfield().getFirstObjective().getId());
        assertEquals(secondObjectiveCardId, model.getPlayfield().getSecondObjective().getId());
    }

    @Test
    void getRound() {
        ArrayList<Player> round = new ArrayList<>();
        Player player = new Player("Marco");
        Player player1 = new Player("Luca");
        Player player2 = new Player("Fabio");
        Player player3 = new Player("Angelo");
        round.add(player);
        round.add(player1);
        round.add(player2);
        round.add(player3);
        model.setRound(round);
        assertEquals(round, model.getRound());
    }

    @Test
    void setRound() {
        ArrayList<Player> round = new ArrayList<>();
        Player player = new Player("Marco");
        Player player1 = new Player("Luca");
        round.add(player);
        round.add(player1);
        model.setRound(round);
        assertEquals(round, model.getRound());
    }

    @Test
    void getCurrentPlayer() {
        ArrayList<Player> round = new ArrayList<>();
        Player player = new Player("Marco");
        Player player1 = new Player("Luca");
        Player player2 = new Player("Fabio");
        Player player3 = new Player("Angelo");
        round.add(player);
        round.add(player1);
        round.add(player2);
        round.add(player3);
        model.setRound(round);
        assertEquals(player, model.getCurrentPlayer());
    }

    @Test
    void changeTurn() {
        ArrayList<Player> round = new ArrayList<>();
        Player player = new Player("Marco");
        Player player1 = new Player("Luca");
        Player player2 = new Player("Fabio");
        Player player3 = new Player("Angelo");
        round.add(player);
        round.add(player1);
        round.add(player2);
        round.add(player3);
        model.setRound(round);
        assertEquals(player, model.getCurrentPlayer());
        model.changeTurn();
        assertEquals(player1, model.getCurrentPlayer());
        model.changeTurn();
        model.changeTurn();
        assertEquals(player3, model.getCurrentPlayer());
        model.changeTurn();
        assertEquals(player, model.getCurrentPlayer());
        model.changeTurn();
        assertEquals(player1, model.getCurrentPlayer());
    }

    @Test
    void getAvailableTokens() {
        ArrayList<Token> tokens = new ArrayList<>();
        Token[] values = Token.values();
        tokens.addAll(Arrays.asList(values).subList(0, values.length - 1));
        assertEquals(tokens, model.getAvailableTokens());
    }

    @Test
    void getAvailableStarters() {
        ArrayList<Integer> startDeck = DataParser.getStarterCards();
        Collections.sort(startDeck);
        Collections.sort(model.getAvailableStarters());
        assertEquals(startDeck, model.getAvailableStarters());
    }

    private ArrayList<ResourceCard> createResourceDeck(){
        ArrayList<ResourceCard> resDeck = new ArrayList<>();

        int id1 = 26;
        Symbol symbol1 = Symbol.ANIMAL;
        int points = 0;
        Corner top_left1 = new Corner(CornerType.TOP_LEFT, Symbol.PLANT);
        Corner top_right1 = new Corner(CornerType.TOP_RIGHT, Symbol.ANIMAL);
        Corner bot_right1 = new Corner(CornerType.BOT_RIGHT, Symbol.MANUSCRIPT);
        ResourceCard resourceCard = new ResourceCard(id1, symbol1, points, top_left1, top_right1, bot_right1);
        resDeck.add(resourceCard);
        int id2 = 27;
        Symbol symbol2 = Symbol.ANIMAL;
        int points2 = 0;
        Corner top_left2 = new Corner(CornerType.TOP_LEFT, Symbol.PLANT);
        Corner top_right2 = new Corner(CornerType.TOP_RIGHT, Symbol.ANIMAL);
        Corner bot_right2 = new Corner(CornerType.BOT_RIGHT, Symbol.MANUSCRIPT);
        ResourceCard resourceCard2 = new ResourceCard(id2, symbol2, points2, top_left2, top_right2, bot_right2);
        resDeck.add(resourceCard2);
        return resDeck;
    }

    private ArrayList<GoldCard> createGoldDeck(){
        ArrayList<GoldCard> goldDeck = new ArrayList<>();

        int id3 = 65;
        Corner corner = new Corner(CornerType.TOP_LEFT, Symbol.EMPTY);
        Corner corner1 = new Corner(CornerType.BOT_LEFT, Symbol.EMPTY);
        Corner corner2 = new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY);
        int points3 = 2;
        Symbol symbol = Symbol.ANIMAL;
        PointsType pointsType = PointsType.CORNERS;
        Map<Symbol, Integer> condition = new HashMap<>();
        condition.put(Symbol.ANIMAL, 3);
        condition.put(Symbol.FUNGI, 1);
        GoldCard goldCard = new GoldCard(id3, corner, corner1, corner2, points3, symbol, pointsType, condition);
        goldDeck.add(goldCard);
        int id4 = 66;
        Corner corner3 = new Corner(CornerType.TOP_LEFT, Symbol.EMPTY);
        Corner corner4 = new Corner(CornerType.BOT_LEFT, Symbol.EMPTY);
        Corner corner5 = new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY);
        int points4 = 2;
        Symbol symbol3 = Symbol.ANIMAL;
        PointsType pointsType2 = PointsType.CORNERS;
        Map<Symbol, Integer> condition2 = new HashMap<>();
        condition2.put(Symbol.ANIMAL, 3);
        condition2.put(Symbol.FUNGI, 1);
        GoldCard goldCard2 = new GoldCard(id4, corner3, corner4, corner5, points4, symbol3, pointsType2, condition2);
        goldDeck.add(goldCard2);
        return goldDeck;
    }

    private ArrayList<ObjectiveCard> createObjDeck(){
        ArrayList<ObjectiveCard> objDeck = new ArrayList<>();
        int id5 = 87;
        int points5 = 2;
        HashMap<Coordinate, Symbol> pattern = new HashMap<>();
        pattern.put(new Coordinate(0, 0), Symbol.FUNGI);
        pattern.put(new Coordinate(-1, 1), Symbol.FUNGI);
        pattern.put(new Coordinate(-2, 2), Symbol.FUNGI);
        ObjectiveCard patternObjectiveCard = new PatternObjectiveCard(id5, points5, pattern);
        objDeck.add(patternObjectiveCard);
        int id6 = 88;
        int points6 = 2;
        HashMap<Coordinate, Symbol> pattern2 = new HashMap<>();
        pattern2.put(new Coordinate(0, 0), Symbol.FUNGI);
        pattern2.put(new Coordinate(-1, 1), Symbol.FUNGI);
        pattern2.put(new Coordinate(-2, 2), Symbol.FUNGI);
        ObjectiveCard patternObjectiveCard2 = new PatternObjectiveCard(id6, points6, pattern2);
        objDeck.add(patternObjectiveCard2);
        return objDeck;
    }
}