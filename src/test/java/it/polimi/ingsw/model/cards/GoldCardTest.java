package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GoldCardTest {
    GoldCard goldCard;
    GoldCard goldCard1;
    GoldCard goldCard2;
    GoldCard goldCard3;
    GoldCard goldCard4;
    GoldCard goldCard5;

    @BeforeEach
    void setUp(){
        int id = 65;
        Corner corner = new Corner(CornerType.TOP_LEFT, Symbol.EMPTY);
        Corner corner1 = new Corner(CornerType.BOT_LEFT, Symbol.EMPTY);
        Corner corner2 = new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY);
        int points = 2;
        Symbol symbol = Symbol.ANIMAL;
        PointsType pointsType = PointsType.CORNERS;
        Map<Symbol, Integer> condition = new HashMap<>();
        condition.put(Symbol.ANIMAL, 3);
        condition.put(Symbol.FUNGI, 1);
        goldCard = new GoldCard(id, corner, corner1, corner2, points, symbol, pointsType, condition);

        int id1 = 67;
        Corner corner3 = new Corner(CornerType.TOP_LEFT, Symbol.EMPTY);
        Corner corner4 = new Corner(CornerType.BOT_LEFT, Symbol.MANUSCRIPT);
        int points1 = 3;
        PointsType pointsType1 = PointsType.NULL;
        Map<Symbol, Integer> condition1 = new HashMap<>();
        condition1.put(Symbol.ANIMAL, 3);
        goldCard1 = new GoldCard(id1, corner3, corner4, points1, symbol, pointsType1, condition1);

        int id2 = 70;
        Corner corner5 = new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY);
        Corner corner6 = new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY);
        int points2 = 5;
        PointsType pointsType2 = PointsType.NULL;
        Map<Symbol, Integer> condition2 = new HashMap<>();
        condition2.put(Symbol.ANIMAL, 5);
        goldCard2 = new GoldCard(id2, corner5, corner6, points2, symbol, pointsType2, condition2);

        int id3 = 63;
        Corner corner7 = new Corner(CornerType.TOP_LEFT, Symbol.EMPTY);
        Corner corner8 = new Corner(CornerType.BOT_LEFT, Symbol.QUILL);
        Corner corner9 = new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY);
        int points3 = 1;
        PointsType pointsType3 = PointsType.QUILL;
        Map<Symbol, Integer> condition3 = new HashMap<>();
        condition3.put(Symbol.ANIMAL, 2);
        condition3.put(Symbol.FUNGI, 1);
        goldCard3 = new GoldCard(id3, corner7, corner8, corner9, points3, symbol, pointsType3, condition3);

        int id4 = 62;
        Corner corner10 = new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY);
        Corner corner11 = new Corner(CornerType.BOT_LEFT, Symbol.EMPTY);
        Corner corner12 = new Corner(CornerType.BOT_RIGHT, Symbol.MANUSCRIPT);
        int points4 = 1;
        PointsType pointsType4 = PointsType.MANUSCRIPT;
        Map<Symbol, Integer> condition4 = new HashMap<>();
        condition4.put(Symbol.ANIMAL, 2);
        condition4.put(Symbol.PLANT, 1);
        goldCard4 = new GoldCard(id4, corner10, corner11, corner12, points4, symbol, pointsType4, condition4);

        int id5 = 61;
        Corner corner13 = new Corner(CornerType.TOP_LEFT, Symbol.INKWELL);
        Corner corner14 = new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY);
        Corner corner15 = new Corner(CornerType.BOT_LEFT, Symbol.EMPTY);
        int points5 = 1;
        PointsType pointsType5 = PointsType.INKWELL;
        Map<Symbol, Integer> condition5 = new HashMap<>();
        condition5.put(Symbol.ANIMAL, 2);
        condition5.put(Symbol.INSECT, 1);
        goldCard5 = new GoldCard(id5, corner13, corner14, corner15, points5, symbol, pointsType5, condition5);
    }

    @Test
    void getSymbol() {
        Symbol symbol = Symbol.ANIMAL;
        assertEquals(symbol, goldCard.getSymbol());
    }

    @Test
    void checkPointsCORNERS() {
        int points = 2;

        Board board = new Board();
        ResourceCard card1 = new ResourceCard(1, Symbol.FUNGI, 0, new Corner(CornerType.TOP_LEFT, Symbol.EMPTY), new Corner(CornerType.TOP_RIGHT, Symbol.FUNGI), new Corner(CornerType.BOT_LEFT, Symbol.FUNGI));
        board.cardPlacement(card1,new Coordinate(2, 2));
        ResourceCard card2 = new ResourceCard(2, Symbol.FUNGI, 0, new Corner(CornerType.TOP_LEFT, Symbol.ANIMAL), new Corner(CornerType.TOP_RIGHT, Symbol.ANIMAL), new Corner(CornerType.BOT_LEFT, Symbol.ANIMAL));
        board.cardPlacement(card2,new Coordinate(1, 1));
        ResourceCard card3 = new ResourceCard(3, Symbol.FUNGI, 0, new Corner(CornerType.TOP_LEFT, Symbol.INKWELL), new Corner(CornerType.TOP_RIGHT, Symbol.ANIMAL), new Corner(CornerType.BOT_LEFT, Symbol.INSECT));
        board.cardPlacement(card3,new Coordinate(1, 3));

        assertEquals(points, goldCard.checkPoints(board, new Coordinate(2,6)));
        points = 4;
        assertEquals(points, goldCard.checkPoints(board, new Coordinate(2,4)));

    }

    @Test
    void checkPointsNULL3() {
        int points = 3;
        Board board = new Board();
        assertEquals(points, goldCard1.checkPoints(board, new Coordinate(2, 2)));

    }

    @Test
    void checkPointsNULL5() {
        int points = 5;
        Board board = new Board();
        assertEquals(points, goldCard2.checkPoints(board, new Coordinate(2, 2)));

    }

    @Test
    void checkPointsQUILL() {
        int points = 2;

        Board board = new Board();
        ResourceCard card1 = new ResourceCard(1, Symbol.FUNGI, 0, new Corner(CornerType.TOP_LEFT, Symbol.QUILL), new Corner(CornerType.TOP_RIGHT, Symbol.FUNGI), new Corner(CornerType.BOT_LEFT, Symbol.FUNGI));
        board.cardPlacement(card1,new Coordinate(2, 2));
        ResourceCard card2 = new ResourceCard(2, Symbol.FUNGI, 0, new Corner(CornerType.TOP_LEFT, Symbol.ANIMAL), new Corner(CornerType.TOP_RIGHT, Symbol.ANIMAL), new Corner(CornerType.BOT_LEFT, Symbol.ANIMAL));
        board.cardPlacement(card2,new Coordinate(1, 1));
        ResourceCard card3 = new ResourceCard(3, Symbol.FUNGI, 0, new Corner(CornerType.TOP_LEFT, Symbol.QUILL), new Corner(CornerType.TOP_RIGHT, Symbol.ANIMAL), new Corner(CornerType.BOT_LEFT, Symbol.INSECT));
        board.cardPlacement(card3,new Coordinate(1, 3));
        Coordinate coordinate = new Coordinate(2,6);
        board.cardPlacement(goldCard3, coordinate);

        assertEquals(points, goldCard3.checkPoints(board, coordinate));

    }

    @Test
    void checkPointsMANUSCRIPT() {
        int points = 2;

        Board board = new Board();
        ResourceCard card1 = new ResourceCard(1, Symbol.FUNGI, 0, new Corner(CornerType.TOP_LEFT, Symbol.MANUSCRIPT), new Corner(CornerType.TOP_RIGHT, Symbol.FUNGI), new Corner(CornerType.BOT_LEFT, Symbol.PLANT));
        board.cardPlacement(card1,new Coordinate(2, 2));
        ResourceCard card2 = new ResourceCard(2, Symbol.FUNGI, 0, new Corner(CornerType.TOP_LEFT, Symbol.ANIMAL), new Corner(CornerType.TOP_RIGHT, Symbol.ANIMAL), new Corner(CornerType.BOT_LEFT, Symbol.ANIMAL));
        board.cardPlacement(card2,new Coordinate(1, 1));
        ResourceCard card3 = new ResourceCard(3, Symbol.FUNGI, 0, new Corner(CornerType.TOP_LEFT, Symbol.MANUSCRIPT), new Corner(CornerType.TOP_RIGHT, Symbol.ANIMAL), new Corner(CornerType.BOT_LEFT, Symbol.INSECT));
        board.cardPlacement(card3,new Coordinate(1, 3));
        Coordinate coordinate = new Coordinate(2,6);
        board.cardPlacement(goldCard4, coordinate);

        assertEquals(points, goldCard4.checkPoints(board, coordinate));

    }

    @Test
    void checkPointsINKWELL() {
        int points = 2;

        Board board = new Board();
        ResourceCard card1 = new ResourceCard(1, Symbol.FUNGI, 0, new Corner(CornerType.TOP_LEFT, Symbol.INKWELL), new Corner(CornerType.TOP_RIGHT, Symbol.FUNGI), new Corner(CornerType.BOT_LEFT, Symbol.FUNGI));
        board.cardPlacement(card1,new Coordinate(2, 2));
        ResourceCard card2 = new ResourceCard(2, Symbol.FUNGI, 0, new Corner(CornerType.TOP_LEFT, Symbol.ANIMAL), new Corner(CornerType.TOP_RIGHT, Symbol.ANIMAL), new Corner(CornerType.BOT_LEFT, Symbol.ANIMAL));
        board.cardPlacement(card2,new Coordinate(1, 1));
        ResourceCard card3 = new ResourceCard(3, Symbol.FUNGI, 0, new Corner(CornerType.TOP_LEFT, Symbol.INKWELL), new Corner(CornerType.TOP_RIGHT, Symbol.ANIMAL), new Corner(CornerType.BOT_LEFT, Symbol.INSECT));
        board.cardPlacement(card3,new Coordinate(1, 3));
        Coordinate coordinate = new Coordinate(2,6);
        board.cardPlacement(goldCard5, coordinate);

        assertEquals(points, goldCard5.checkPoints(board, coordinate));

    }
    @Test
    void checkConditionFalse() {
        Board board = new Board();
        ResourceCard card1 = new ResourceCard(1, Symbol.FUNGI, 0, new Corner(CornerType.TOP_LEFT, Symbol.EMPTY), new Corner(CornerType.TOP_RIGHT, Symbol.FUNGI), new Corner(CornerType.BOT_LEFT, Symbol.FUNGI));
        board.cardPlacement(card1,new Coordinate(2, 2));
        assertEquals(0,goldCard.checkCondition(board));
    }

    @Test
    void checkConditionTrue(){
        Board board = new Board();
        ResourceCard card1 = new ResourceCard(1, Symbol.FUNGI, 0, new Corner(CornerType.TOP_LEFT, Symbol.EMPTY), new Corner(CornerType.TOP_RIGHT, Symbol.FUNGI), new Corner(CornerType.BOT_LEFT, Symbol.FUNGI));
        board.cardPlacement(card1,new Coordinate(2, 2));
        ResourceCard card2 = new ResourceCard(2, Symbol.FUNGI, 0, new Corner(CornerType.TOP_LEFT, Symbol.ANIMAL), new Corner(CornerType.TOP_RIGHT, Symbol.ANIMAL), new Corner(CornerType.BOT_LEFT, Symbol.ANIMAL));
        board.cardPlacement(card2,new Coordinate(1, 1));
        assertEquals(1,goldCard.checkCondition(board));
    }

    @Test
    void getCardId(){
        int id = 65;
        assertEquals(id, goldCard.getCardId());
    }

    @Test
    void getSide(){
        Face face = Face.FRONT;
        assertEquals(face, goldCard.getSide());
        goldCard.setFace(Face.BACK);
        face = Face.BACK;
        assertEquals(face, goldCard.getSide());
    }

    @Test
    void getTop_left(){
        Corner corner1 = new Corner(CornerType.TOP_LEFT, Symbol.EMPTY);
        assertEquals(corner1, goldCard.getTop_left());
    }

    @Test
    void getBot_left(){
        Corner corner2 = new Corner(CornerType.BOT_LEFT, Symbol.EMPTY);
        assertEquals(corner2, goldCard.getBot_left());
    }

    @Test
    void getTop_right(){
        assertNull(goldCard.getTop_right());
    }

    @Test
    void getBot_right(){
        Corner corner3 = new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY);
        assertEquals(corner3, goldCard.getBot_right());
    }

    @Test
    void setFace(){
        Face face = Face.BACK;
        goldCard.setFace(face);
        assertEquals(face, goldCard.getSide());
        face = Face.FRONT;
        goldCard.setFace(face);
        assertEquals(face, goldCard.getSide());
    }

    @Test
    void setBack(){
        Corner top_right = new Corner(CornerType.TOP_RIGHT,Symbol.EMPTY);
        Corner top_left = new Corner(CornerType.TOP_LEFT,Symbol.EMPTY);
        Corner bot_right = new Corner(CornerType.BOT_RIGHT,Symbol.EMPTY);
        Corner bot_left = new Corner(CornerType.BOT_LEFT,Symbol.EMPTY);
        Face side = Face.BACK;
        goldCard.setBack();
        assertEquals(top_left, goldCard.getTop_left());
        assertEquals(top_right, goldCard.getTop_right());
        assertEquals(bot_left, goldCard.getBot_left());
        assertEquals(bot_right, goldCard.getBot_right());
        assertEquals(side, goldCard.getSide());
    }

    @Test
    void testEquals(){
        Map<Symbol, Integer> condition = new HashMap<>();
        condition.put(Symbol.ANIMAL, 3);
        condition.put(Symbol.FUNGI, 1);
        assertEquals(goldCard, new GoldCard(65, new Corner(CornerType.TOP_LEFT, Symbol.EMPTY),
                new Corner(CornerType.BOT_LEFT, Symbol.EMPTY), new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY),
                2, Symbol.ANIMAL, PointsType.CORNERS, condition));
    }
}