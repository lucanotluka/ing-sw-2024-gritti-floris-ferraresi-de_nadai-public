package it.polimi.ingsw.model.cards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceCardTest {
    ResourceCard resourceCard;
    @BeforeEach
    void setUp(){
        int id = 26;
        Symbol symbol = Symbol.ANIMAL;
        int points = 0;
        Corner top_left = new Corner(CornerType.TOP_LEFT, Symbol.PLANT);
        Corner top_right = new Corner(CornerType.TOP_RIGHT, Symbol.ANIMAL);
        Corner bot_right = new Corner(CornerType.BOT_RIGHT, Symbol.MANUSCRIPT);
        resourceCard = new ResourceCard(id, symbol, points, top_left, top_right, bot_right);
    }
    @Test
    void getSymbol() {
        Symbol symbol = Symbol.ANIMAL;
        assertEquals(symbol, resourceCard.getSymbol());
    }

    @Test
    void getPoints() {
        int points = 0;
        assertEquals(points, resourceCard.getPoints());
    }

    @Test
    void getCardId(){
        int id = 26;
        assertEquals(id, resourceCard.getCardId());
    }

    @Test
    void getSide(){
        Face face = Face.FRONT;
        assertEquals(face, resourceCard.getSide());
        resourceCard.setFace(Face.BACK);
        face = Face.BACK;
        assertEquals(face, resourceCard.getSide());
    }

    @Test
    void getTop_left(){
        Corner corner1 = new Corner(CornerType.TOP_LEFT, Symbol.PLANT);
        assertEquals(corner1, resourceCard.getTop_left());
    }

    @Test
    void getTop_right(){
        Corner corner2 = new Corner(CornerType.TOP_RIGHT, Symbol.ANIMAL);
        assertEquals(corner2, resourceCard.getTop_right());
    }

    @Test
    void getBot_left(){
        assertNull(resourceCard.getBot_left());
    }

    @Test
    void getBot_right(){
        Corner corner3 = new Corner(CornerType.BOT_RIGHT, Symbol.MANUSCRIPT);
        assertEquals(corner3, resourceCard.getBot_right());
    }

    @Test
    void setBack(){
        Corner top_right = new Corner(CornerType.TOP_RIGHT,Symbol.EMPTY);
        Corner top_left = new Corner(CornerType.TOP_LEFT,Symbol.EMPTY);
        Corner bot_right = new Corner(CornerType.BOT_RIGHT,Symbol.EMPTY);
        Corner bot_left = new Corner(CornerType.BOT_LEFT,Symbol.EMPTY);
        Face side = Face.BACK;
        resourceCard.setBack();
        assertEquals(top_left, resourceCard.getTop_left());
        assertEquals(top_right, resourceCard.getTop_right());
        assertEquals(bot_left, resourceCard.getBot_left());
        assertEquals(bot_right, resourceCard.getBot_right());
        assertEquals(side, resourceCard.getSide());
    }

    @Test
    void setFace(){
        Face side = Face.BACK;
        resourceCard.setFace(side);
        assertEquals(side, resourceCard.getSide());
        side = Face.FRONT;
        resourceCard.setFace(side);
        assertEquals(side, resourceCard.getSide());
    }

    @Test
    void testEquals(){
        assertEquals(resourceCard, new ResourceCard(26, Symbol.ANIMAL, 0, new Corner(CornerType.TOP_LEFT, Symbol.PLANT),
                new Corner(CornerType.TOP_RIGHT, Symbol.ANIMAL), new Corner(CornerType.BOT_RIGHT, Symbol.MANUSCRIPT)));
    }
}