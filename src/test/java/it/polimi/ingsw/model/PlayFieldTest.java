package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NoMoreCardToReplaceException;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.objectives.ObjectiveCard;
import it.polimi.ingsw.model.objectives.PatternObjectiveCard;
import it.polimi.ingsw.model.objectives.SymbolObjectiveCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayFieldTest {
    PlayField playField;

    @BeforeEach
    void setUp(){
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
        int id3 = 7;
        Symbol symbol3 = Symbol.FUNGI;
        int points3 = 0;
        Corner top_left3 = new Corner(CornerType.TOP_LEFT, Symbol.FUNGI);
        Corner top_right3 = new Corner(CornerType.TOP_RIGHT, Symbol.INSECT);
        Corner bot_left3 = new Corner(CornerType.BOT_LEFT, Symbol.MANUSCRIPT);
        Corner bot_right3 = new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY);
        ResourceCard resourceCard3 = new ResourceCard(id3, symbol3, points3, top_left3, top_right3, bot_left3, bot_right3);
        resDeck.add(resourceCard3);

        ArrayList<GoldCard> goldDeck = new ArrayList<>();
        int id4 = 65;
        Corner corner = new Corner(CornerType.TOP_LEFT, Symbol.EMPTY);
        Corner corner1 = new Corner(CornerType.BOT_LEFT, Symbol.EMPTY);
        Corner corner2 = new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY);
        int points4 = 2;
        Symbol symbol = Symbol.ANIMAL;
        PointsType pointsType = PointsType.CORNERS;
        Map<Symbol, Integer> condition = new HashMap<>();
        condition.put(Symbol.ANIMAL, 3);
        condition.put(Symbol.FUNGI, 1);
        GoldCard goldCard = new GoldCard(id4, corner, corner1, corner2, points4, symbol, pointsType, condition);
        goldDeck.add(goldCard);
        int id5 = 66;
        Corner corner3 = new Corner(CornerType.TOP_LEFT, Symbol.EMPTY);
        Corner corner4 = new Corner(CornerType.BOT_LEFT, Symbol.EMPTY);
        Corner corner5 = new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY);
        int points5 = 2;
        Symbol symbol4 = Symbol.ANIMAL;
        PointsType pointsType2 = PointsType.CORNERS;
        Map<Symbol, Integer> condition2 = new HashMap<>();
        condition2.put(Symbol.ANIMAL, 3);
        condition2.put(Symbol.FUNGI, 1);
        GoldCard goldCard2 = new GoldCard(id5, corner3, corner4, corner5, points5, symbol4, pointsType2, condition2);
        goldDeck.add(goldCard2);
        int id6 = 67;
        Corner corner6 = new Corner(CornerType.TOP_LEFT, Symbol.EMPTY);
        Corner corner7 = new Corner(CornerType.BOT_LEFT, Symbol.MANUSCRIPT);
        int points6 = 3;
        Symbol symbol5 = Symbol.ANIMAL;
        PointsType pointsType3 = PointsType.NULL;
        Map<Symbol, Integer> condition3 = new HashMap<>();
        condition3.put(Symbol.ANIMAL, 3);
        GoldCard goldCard3 = new GoldCard(id6, corner6, corner7, points6, symbol5, pointsType3, condition3);
        goldDeck.add(goldCard3);


        ArrayList<ObjectiveCard> objDeck = new ArrayList<>();
        int id7 = 87;
        int points7 = 2;
        HashMap<Coordinate, Symbol> pattern = new HashMap<>();
        pattern.put(new Coordinate(0, 0), Symbol.FUNGI);
        pattern.put(new Coordinate(-1, 1), Symbol.FUNGI);
        pattern.put(new Coordinate(-2, 2), Symbol.FUNGI);
        ObjectiveCard patternObjectiveCard = new PatternObjectiveCard(id7, points7, pattern);
        objDeck.add(patternObjectiveCard);
        int id8 = 88;
        int points8 = 2;
        HashMap<Coordinate, Symbol> pattern2 = new HashMap<>();
        pattern2.put(new Coordinate(0, 0), Symbol.FUNGI);
        pattern2.put(new Coordinate(-1, 1), Symbol.FUNGI);
        pattern2.put(new Coordinate(-2, 2), Symbol.FUNGI);
        ObjectiveCard patternObjectiveCard2 = new PatternObjectiveCard(id8, points8, pattern2);
        objDeck.add(patternObjectiveCard2);
        int id9 = 97;
        int points9 = 2;
        Symbol symbol6 = Symbol.ANIMAL;
        Symbol symbol7 = Symbol.ANIMAL;
        Symbol symbol8 = Symbol.ANIMAL;
        SymbolObjectiveCard symbolObjectiveCard = new SymbolObjectiveCard(id9, points9, symbol6, symbol7, symbol8);
        objDeck.add(symbolObjectiveCard);

        playField = new PlayField(resDeck, goldDeck, objDeck);
    }
    @Test
    void lastCardResourceDeckId() {
        List<Integer> resourceCardId = new ArrayList<>();
        resourceCardId.add(26);
        resourceCardId.add(27);
        resourceCardId.add(7);
        resourceCardId.remove((Integer) playField.getFirstResource().getCardId());
        resourceCardId.remove((Integer) playField.getSecondResource().getCardId());
        assertEquals(resourceCardId.getLast(), playField.lastCardResourceDeckId());
    }

    @Test
    void lastCardGoldDeckId() {
        List<Integer> goldCardId = new ArrayList<>();
        goldCardId.add(65);
        goldCardId.add(66);
        goldCardId.add(67);
        goldCardId.remove((Integer) playField.getFirstGold().getCardId());
        goldCardId.remove((Integer) playField.getSecondGold().getCardId());
        assertEquals(goldCardId.getLast(), playField.lastCardGoldDeckId());
    }

    @Test
    void getFirstResource() {
        List<Integer> resourceCardId = new ArrayList<>();
        int id = 0;
        resourceCardId.add(26);
        resourceCardId.add(27);
        resourceCardId.add(7);
        for (Integer integer : resourceCardId)
            if (integer == playField.getFirstResource().getCardId())
                id = integer;
        assertEquals(id, playField.getFirstResource().getCardId());
    }

    @Test
    void getSecondResource() {
        List<Integer> resourceCardId = new ArrayList<>();
        int id = 0;
        resourceCardId.add(26);
        resourceCardId.add(27);
        resourceCardId.add(7);
        for (Integer integer : resourceCardId)
            if (integer == playField.getSecondResource().getCardId())
                id = integer;
        assertEquals(id, playField.getSecondResource().getCardId());
    }

    @Test
    void getFirstGold() {
        List<Integer> goldCardId = new ArrayList<>();
        int id = 0;
        goldCardId.add(65);
        goldCardId.add(66);
        goldCardId.add(67);
        for (Integer integer : goldCardId)
            if (integer == playField.getFirstGold().getCardId())
                id = integer;
        assertEquals(id, playField.getFirstGold().getCardId());
    }

    @Test
    void getSecondGold() {
        List<Integer> goldCardId = new ArrayList<>();
        int id = 0;
        goldCardId.add(65);
        goldCardId.add(66);
        goldCardId.add(67);
        for (Integer integer : goldCardId)
            if (integer == playField.getSecondGold().getCardId())
                id = integer;
        assertEquals(id, playField.getSecondGold().getCardId());
    }

    @Test
    void getResourceDeckSize() {
        assertEquals(1, playField.getResourceDeckSize());
    }

    @Test
    void getGoldDeckSize() {
        assertEquals(1, playField.getGoldDeckSize());
    }

    @Test
    void getFirstObjective() {
        List<Integer> objCardId = new ArrayList<>();
        int id = 0;
        objCardId.add(87);
        objCardId.add(88);
        objCardId.add(97);
        for (Integer integer : objCardId)
            if (integer == playField.getFirstObjective().getId())
                id = integer;
        assertEquals(id, playField.getFirstObjective().getId());
    }

    @Test
    void getSecondObjective() {
        List<Integer> objCardId = new ArrayList<>();
        int id = 0;
        objCardId.add(87);
        objCardId.add(88);
        objCardId.add(97);
        for (Integer integer : objCardId)
            if (integer == playField.getSecondObjective().getId())
                id = integer;
        assertEquals(id, playField.getSecondObjective().getId());
    }

    @Test
    void getObjectiveFromDeck() {
        List<Integer> objCardId = new ArrayList<>();
        objCardId.add(87);
        objCardId.add(88);
        objCardId.add(97);
        objCardId.remove((Integer) playField.getFirstObjective().getId());
        objCardId.remove((Integer) playField.getSecondObjective().getId());
        assertEquals(objCardId.getLast(), playField.getObjectiveFromDeck().getId());
    }

    @Test
    void chooseCardToDrawResourceDeck() {
        List<Integer> resourceCardId = new ArrayList<>();
        int id = 0;
        int id1;
        resourceCardId.add(26);
        resourceCardId.add(27);
        resourceCardId.add(7);
        for (Integer integer : resourceCardId)
            if (integer == playField.lastCardResourceDeckId())
                id = integer;
        try {
            id1 = playField.chooseCardToDraw(DrawCardChoice.RESOURCEDECK).getCardId();
        } catch (NoMoreCardToReplaceException e) {
            throw new RuntimeException(e);
        }
        assertEquals(id, id1);
    }

    @Test
    void chooseCardToDrawResourceDeckException() {
        try {
            playField.chooseCardToDraw(DrawCardChoice.RESOURCEDECK);
            playField.chooseCardToDraw(DrawCardChoice.RESOURCEDECK);
        } catch (NoMoreCardToReplaceException e) {
            System.err.println(e);
        }
    }

    @Test
    void chooseCardToDrawGoldDeck() {
        List<Integer> goldCardId = new ArrayList<>();
        int id = 0;
        int id1;
        goldCardId.add(65);
        goldCardId.add(66);
        goldCardId.add(67);
        for (Integer integer : goldCardId)
            if (integer == playField.lastCardGoldDeckId())
                id = integer;
        try {
            id1 = playField.chooseCardToDraw(DrawCardChoice.GOLDDECK).getCardId();
        } catch (NoMoreCardToReplaceException e) {
            throw new RuntimeException(e);
        }
        assertEquals(id, id1);
    }

    @Test
    void chooseCardToDrawGoldDeckException() {
        try {
            playField.chooseCardToDraw(DrawCardChoice.GOLDDECK);
            playField.chooseCardToDraw(DrawCardChoice.GOLDDECK);
        } catch (NoMoreCardToReplaceException e) {
            System.err.println(e);
        }
    }

    @Test
    void chooseCardToDrawFirstResource(){
        List<Integer> resourceCardId = new ArrayList<>();
        int id = 0;
        int id1 = 0;
        int id2;
        resourceCardId.add(26);
        resourceCardId.add(27);
        resourceCardId.add(7);
        for (Integer integer : resourceCardId) {
            if (integer == playField.getFirstResource().getCardId())
                id = integer;
            else if (integer == playField.lastCardResourceDeckId())
                id1 = integer;
        }
        try {
            id2 = playField.chooseCardToDraw(DrawCardChoice.FIRSTRESOURCE).getCardId();
        } catch (NoMoreCardToReplaceException e) {
            throw new RuntimeException(e);
        }
        assertEquals(id, id2);
        try {
            id2 = playField.chooseCardToDraw(DrawCardChoice.FIRSTRESOURCE).getCardId();
        } catch (NoMoreCardToReplaceException e) {
            throw new RuntimeException(e);
        }
        assertEquals(id1, id2);
    }

    @Test
    void chooseCardToDrawFirstResourceException() {
        try {
            playField.chooseCardToDraw(DrawCardChoice.FIRSTRESOURCE);
            playField.chooseCardToDraw(DrawCardChoice.FIRSTRESOURCE);
            playField.chooseCardToDraw(DrawCardChoice.FIRSTRESOURCE);
        } catch (NoMoreCardToReplaceException e) {
            System.err.println(e);
        }
    }

    @Test
    void chooseCardToDrawSecondResource(){
        List<Integer> resourceCardId = new ArrayList<>();
        int id = 0;
        int id1 = 0;
        int id2;
        resourceCardId.add(26);
        resourceCardId.add(27);
        resourceCardId.add(7);
        for (Integer integer : resourceCardId) {
            if (integer == playField.getSecondResource().getCardId())
                id = integer;
            else if (integer == playField.lastCardResourceDeckId())
                id1 = integer;
        }
        try {
            id2 = playField.chooseCardToDraw(DrawCardChoice.SECONDRESOURCE).getCardId();
        } catch (NoMoreCardToReplaceException e) {
            throw new RuntimeException(e);
        }
        assertEquals(id, id2);
        try {
            id2 = playField.chooseCardToDraw(DrawCardChoice.SECONDRESOURCE).getCardId();
        } catch (NoMoreCardToReplaceException e) {
            throw new RuntimeException(e);
        }
        assertEquals(id1, id2);
    }

    @Test
    void chooseCardToDrawSecondResourceException() {
        try {
            playField.chooseCardToDraw(DrawCardChoice.SECONDRESOURCE);
            playField.chooseCardToDraw(DrawCardChoice.SECONDRESOURCE);
            playField.chooseCardToDraw(DrawCardChoice.SECONDRESOURCE);
        } catch (NoMoreCardToReplaceException e) {
            System.err.println(e);
        }
    }

    @Test
    void chooseCardToDrawFirstGold(){
        List<Integer> goldCardId = new ArrayList<>();
        int id = 0;
        int id1 = 0;
        int id2;
        goldCardId.add(65);
        goldCardId.add(66);
        goldCardId.add(67);
        for (Integer integer : goldCardId) {
            if (integer == playField.getFirstGold().getCardId())
                id = integer;
            else if (integer == playField.lastCardGoldDeckId())
                id1 = integer;
        }
        try {
            id2 = playField.chooseCardToDraw(DrawCardChoice.FIRSTGOLD).getCardId();
        } catch (NoMoreCardToReplaceException e) {
            throw new RuntimeException(e);
        }
        assertEquals(id, id2);
        try {
            id2 = playField.chooseCardToDraw(DrawCardChoice.FIRSTGOLD).getCardId();
        } catch (NoMoreCardToReplaceException e) {
            throw new RuntimeException(e);
        }
        assertEquals(id1, id2);
    }

    @Test
    void chooseCardToDrawFirstGoldException() {
        try {
            playField.chooseCardToDraw(DrawCardChoice.FIRSTGOLD);
            playField.chooseCardToDraw(DrawCardChoice.FIRSTGOLD);
            playField.chooseCardToDraw(DrawCardChoice.FIRSTGOLD);
        } catch (NoMoreCardToReplaceException e) {
            System.err.println(e);
        }
    }

    @Test
    void chooseCardToDrawSecondGold(){
        List<Integer> goldCardId = new ArrayList<>();
        int id = 0;
        int id1 = 0;
        int id2;
        goldCardId.add(65);
        goldCardId.add(66);
        goldCardId.add(67);
        for (Integer integer : goldCardId) {
            if (integer == playField.getSecondGold().getCardId())
                id = integer;
            else if (integer == playField.lastCardGoldDeckId())
                id1 = integer;
        }
        try {
            id2 = playField.chooseCardToDraw(DrawCardChoice.SECONDGOLD).getCardId();
        } catch (NoMoreCardToReplaceException e) {
            throw new RuntimeException(e);
        }
        assertEquals(id, id2);
        try {
            id2 = playField.chooseCardToDraw(DrawCardChoice.SECONDGOLD).getCardId();
        } catch (NoMoreCardToReplaceException e) {
            throw new RuntimeException(e);
        }
        assertEquals(id1, id2);
    }

    @Test
    void chooseCardToDrawSecondGoldException() {
        try {
            playField.chooseCardToDraw(DrawCardChoice.SECONDGOLD);
            playField.chooseCardToDraw(DrawCardChoice.SECONDGOLD);
            playField.chooseCardToDraw(DrawCardChoice.SECONDGOLD);
        } catch (NoMoreCardToReplaceException e) {
            System.err.println(e);
        }
    }
}