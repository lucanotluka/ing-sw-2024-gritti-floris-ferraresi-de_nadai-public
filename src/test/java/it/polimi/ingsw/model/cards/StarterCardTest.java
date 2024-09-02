package it.polimi.ingsw.model.cards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class StarterCardTest {

    StarterCard starterCard;
    StarterCard starterCard1;

    @BeforeEach
    void setUp() {
        int id = 84;
        ArrayList<Symbol> symbols = new ArrayList<>();
        symbols.add(Symbol.ANIMAL);
        symbols.add(Symbol.INSECT);
        Corner top_left = new Corner(CornerType.TOP_LEFT, Symbol.EMPTY);
        Corner top_right = new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY);
        Corner bot_left = new Corner(CornerType.BOT_LEFT, Symbol.EMPTY);
        Corner bot_right = new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY);
        Face face = Face.FRONT;
        starterCard = new StarterCard(id, symbols, face, top_left, top_right, bot_left, bot_right);

        int id1 = 85;
        ArrayList<Symbol> symbols1 = new ArrayList<>();
        symbols1.add(Symbol.ANIMAL);
        symbols1.add(Symbol.INSECT);
        symbols1.add(Symbol.PLANT);
        Corner top_left1 = new Corner(CornerType.TOP_LEFT, Symbol.EMPTY);
        Corner top_right1 = new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY);
        Face face1 = Face.FRONT;
        starterCard1 = new StarterCard(id1, symbols1, face1, top_left1, top_right1);
    }

    @Test
    void getSymbols() {
        ArrayList<Symbol> symbols = new ArrayList<>();
        symbols.add(Symbol.ANIMAL);
        symbols.add(Symbol.INSECT);
        assertEquals(symbols, starterCard.getSymbols());
        symbols.add(Symbol.PLANT);
        assertEquals(symbols, starterCard1.getSymbols());
    }

    @Test
    void getSymbol() {
        Symbol symbolEmpty = Symbol.EMPTY;
        assertEquals(symbolEmpty, starterCard.getSymbol());
        assertEquals(symbolEmpty, starterCard1.getSymbol());
    }

    @Test
    void getCardId(){
        int id = 84;
        assertEquals(id, starterCard.getCardId());
        id = 85;
        assertEquals(id, starterCard1.getCardId());
    }

    @Test
    void getSide(){
        Face face = Face.FRONT;
        assertEquals(face, starterCard.getSide());
        assertEquals(face, starterCard1.getSide());
        starterCard.setBack();
        starterCard1.setBack();
        face = Face.BACK;
        assertEquals(face, starterCard.getSide());
        assertEquals(face, starterCard1.getSide());
    }

    @Test
    void getTop_left(){
        Corner corner1 = new Corner(CornerType.TOP_LEFT, Symbol.EMPTY);
        assertEquals(corner1, starterCard.getTop_left());
        assertEquals(corner1, starterCard1.getTop_left());
    }

    @Test
    void getTop_right(){
        Corner corner2 = new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY);
        assertEquals(corner2, starterCard.getTop_right());
        assertEquals(corner2, starterCard1.getTop_right());
    }

    @Test
    void getBot_left(){
        Corner corner3 = new Corner(CornerType.BOT_LEFT, Symbol.EMPTY);
        assertEquals(corner3, starterCard.getBot_left());
        assertNull(starterCard1.getBot_left());
    }

    @Test
    void getBot_right(){
        Corner corner4 = new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY);
        assertEquals(corner4, starterCard.getBot_right());
        assertNull(starterCard1.getBot_right());
    }

    @Test
    void setFace(){
        Face face = Face.BACK;
        starterCard.setFace(face);
        starterCard1.setFace(face);
        assertEquals(face, starterCard.getSide());
        assertEquals(face, starterCard1.getSide());
        face = Face.FRONT;
        starterCard.setFace(face);
        starterCard1.setFace(face);
        assertEquals(face, starterCard.getSide());
        assertEquals(face, starterCard1.getSide());
    }

    @Test
    void testEquals(){
        ArrayList<Symbol> symbols = new ArrayList<>();
        symbols.add(Symbol.ANIMAL);
        symbols.add(Symbol.INSECT);
        Corner top_left = new Corner(CornerType.TOP_LEFT, Symbol.EMPTY);
        Corner top_right = new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY);
        Corner bot_left = new Corner(CornerType.BOT_LEFT, Symbol.EMPTY);
        Corner bot_right = new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY);
        Face face = Face.FRONT;
        assertEquals(starterCard, new StarterCard(84, symbols, face, top_left, top_right, bot_left, bot_right));
        symbols.add(Symbol.PLANT);
        assertEquals(starterCard1, new StarterCard(85, symbols, face, top_left, top_right));
    }
}