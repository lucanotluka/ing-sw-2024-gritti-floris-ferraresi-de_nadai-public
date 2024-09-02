package it.polimi.ingsw.model.objectives;

import it.polimi.ingsw.exceptions.UnplayableCardException;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Coordinate;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PatternObjectiveCardTest {
    PatternObjectiveCard patternObjectiveCard;
    PatternObjectiveCard patternObjectiveCard1;
    @BeforeEach
    void setUp() {
        int id = 87;
        int points = 2;
        HashMap<Coordinate, Symbol> pattern = new HashMap<>();
        pattern.put(new Coordinate(0, 0), Symbol.FUNGI);
        pattern.put(new Coordinate(-1, 1), Symbol.FUNGI);
        pattern.put(new Coordinate(-2, 2), Symbol.FUNGI);
        patternObjectiveCard = new PatternObjectiveCard(id, points, pattern);

        int id1 = 93;
        int points1 = 3;
        HashMap<Coordinate, Symbol> pattern1 = new HashMap<>();
        pattern1.put(new Coordinate(0, 0), Symbol.ANIMAL);
        pattern1.put(new Coordinate(-2, 0), Symbol.ANIMAL);
        pattern1.put(new Coordinate(-3, 1), Symbol.FUNGI);
        patternObjectiveCard1 = new PatternObjectiveCard(id1, points1, pattern1);
    }

    @Test
    void getId(){
        assertEquals(87, patternObjectiveCard.getId());
        assertEquals(93, patternObjectiveCard1.getId());
    }

    @Test
    void getPoints(){
        assertEquals(2, patternObjectiveCard.getPoints());
        assertEquals(3, patternObjectiveCard1.getPoints());
    }

    @Test
    void checkConditionDiagonal() {
        int points = 2;

        ArrayList<Symbol> symbols = new ArrayList<>();
        symbols.add(Symbol.INSECT);
        StarterCard card1 = new StarterCard(81, symbols, Face.FRONT, new Corner(CornerType.TOP_LEFT, Symbol.EMPTY), new Corner(CornerType.TOP_RIGHT, Symbol.PLANT), new Corner(CornerType.BOT_LEFT, Symbol.INSECT), new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY));
        Player player = new Player("Marco");
        player.placeStarterOnBoard(card1);

        assertEquals(0, patternObjectiveCard.checkCondition(player.getBoard())*patternObjectiveCard.getPoints());
        ResourceCard card2 = new ResourceCard(2, Symbol.FUNGI, 0, new Corner(CornerType.TOP_LEFT, Symbol.EMPTY), new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY), new Corner(CornerType.BOT_LEFT, Symbol.FUNGI));
        try {
            player.playCard(card2,new Coordinate(1, 3));
        } catch (UnplayableCardException e) {
            throw new RuntimeException(e);
        }
        ResourceCard card3 = new ResourceCard(3, Symbol.FUNGI, 0, new Corner(CornerType.TOP_LEFT, Symbol.EMPTY), new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY), new Corner(CornerType.BOT_LEFT, Symbol.FUNGI));
        try {
            player.playCard(card3,new Coordinate(2, 6));
        } catch (UnplayableCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0, patternObjectiveCard.checkCondition(player.getBoard())*patternObjectiveCard.getPoints());
        ResourceCard card4 = new ResourceCard(4, Symbol.FUNGI, 0, new Corner(CornerType.TOP_LEFT, Symbol.EMPTY), new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY), new Corner(CornerType.BOT_LEFT, Symbol.FUNGI));
        try {
            player.playCard(card4,new Coordinate(1, 7));
        } catch (UnplayableCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(points, patternObjectiveCard.checkCondition(player.getBoard())*patternObjectiveCard.getPoints());
        ResourceCard card5 = new ResourceCard(5, Symbol.FUNGI, 0, new Corner(CornerType.TOP_LEFT, Symbol.EMPTY), new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY), new Corner(CornerType.BOT_LEFT, Symbol.FUNGI));
        try {
            player.playCard(card5,new Coordinate(4, 12));
        } catch (UnplayableCardException e) {
            throw new RuntimeException(e);
        }

        ResourceCard card6 = new ResourceCard(1, Symbol.FUNGI, 0, new Corner(CornerType.TOP_LEFT, Symbol.EMPTY), new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY), new Corner(CornerType.BOT_LEFT, Symbol.FUNGI));
        try {
            player.playCard(card6,new Coordinate(3, 13));
        } catch (UnplayableCardException e) {
            throw new RuntimeException(e);
        }

        assertEquals(points, patternObjectiveCard.checkCondition(player.getBoard())*patternObjectiveCard.getPoints());

        ResourceCard card7 = new ResourceCard(2, Symbol.FUNGI, 0, new Corner(CornerType.TOP_LEFT, Symbol.EMPTY), new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY), new Corner(CornerType.BOT_LEFT, Symbol.FUNGI));
        try {
            player.playCard(card7,new Coordinate(2, 14));
        } catch (UnplayableCardException e) {
            throw new RuntimeException(e);
        }
        points = 2 * points;

        assertEquals(points, patternObjectiveCard.checkCondition(player.getBoard())*patternObjectiveCard.getPoints());
    }

    @Test
    void checkConditionLSHAPE() {
        int points = 3;

        ArrayList<Symbol> symbols = new ArrayList<>();
        symbols.add(Symbol.INSECT);
        StarterCard card1 = new StarterCard(81, symbols, Face.FRONT, new Corner(CornerType.TOP_LEFT, Symbol.EMPTY), new Corner(CornerType.TOP_RIGHT, Symbol.PLANT), new Corner(CornerType.BOT_LEFT, Symbol.INSECT), new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY));
        Player player = new Player("Marco");
        player.placeStarterOnBoard(card1);

        assertEquals(0, patternObjectiveCard1.checkCondition(player.getBoard())*patternObjectiveCard1.getPoints());
        ResourceCard card2 = new ResourceCard(2, Symbol.ANIMAL, 0, new Corner(CornerType.TOP_LEFT, Symbol.EMPTY), new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY), new Corner(CornerType.BOT_LEFT, Symbol.FUNGI));
        try {
            player.playCard(card2,new Coordinate(1, 3));
        } catch (UnplayableCardException e) {
            throw new RuntimeException(e);
        }
        ResourceCard card3 = new ResourceCard(3, Symbol.ANIMAL, 0, new Corner(CornerType.TOP_LEFT, Symbol.EMPTY), new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY), new Corner(CornerType.BOT_LEFT, Symbol.FUNGI));
        try {
            player.playCard(card3,new Coordinate(1, 5));
        } catch (UnplayableCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0, patternObjectiveCard1.checkCondition(player.getBoard())*patternObjectiveCard1.getPoints());
        ResourceCard card4 = new ResourceCard(4, Symbol.FUNGI, 0, new Corner(CornerType.TOP_LEFT, Symbol.EMPTY), new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY), new Corner(CornerType.BOT_LEFT, Symbol.FUNGI));
        try {
            player.playCard(card4,new Coordinate(0, 6));
        } catch (UnplayableCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(points, patternObjectiveCard1.checkCondition(player.getBoard())*patternObjectiveCard1.getPoints());
        ResourceCard card5 = new ResourceCard(5, Symbol.ANIMAL, 0, new Corner(CornerType.TOP_LEFT, Symbol.EMPTY), new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY), new Corner(CornerType.BOT_LEFT, Symbol.FUNGI));
        try {
            player.playCard(card5,new Coordinate(3, 11));
        } catch (UnplayableCardException e) {
            throw new RuntimeException(e);
        }

        ResourceCard card6 = new ResourceCard(1, Symbol.ANIMAL, 0, new Corner(CornerType.TOP_LEFT, Symbol.EMPTY), new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY), new Corner(CornerType.BOT_LEFT, Symbol.FUNGI));
        try {
            player.playCard(card6,new Coordinate(1, 11));
        } catch (UnplayableCardException e) {
            throw new RuntimeException(e);
        }

        assertEquals(points, patternObjectiveCard1.checkCondition(player.getBoard())*patternObjectiveCard1.getPoints());

        ResourceCard card7 = new ResourceCard(2, Symbol.FUNGI, 0, new Corner(CornerType.TOP_LEFT, Symbol.EMPTY), new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY), new Corner(CornerType.BOT_LEFT, Symbol.FUNGI));
        try {
            player.playCard(card7,new Coordinate(0, 12));
        } catch (UnplayableCardException e) {
            throw new RuntimeException(e);
        }
        points = 2 * points;

        assertEquals(points, patternObjectiveCard1.checkCondition(player.getBoard())*patternObjectiveCard1.getPoints());
    }

    @Test
    void testEquals(){
        int id = 87;
        int points = 2;
        HashMap<Coordinate, Symbol> pattern = new HashMap<>();
        pattern.put(new Coordinate(0, 0), Symbol.FUNGI);
        pattern.put(new Coordinate(-1, 1), Symbol.FUNGI);
        pattern.put(new Coordinate(-2, 2), Symbol.FUNGI);
        assertEquals(patternObjectiveCard, new PatternObjectiveCard(id, points, pattern));
    }
}