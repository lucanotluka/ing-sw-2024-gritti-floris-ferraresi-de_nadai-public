package it.polimi.ingsw.model.cards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CornerTest {
    Corner corner;
    @BeforeEach
    void setUp(){
        CornerType cornerType = CornerType.TOP_LEFT;
        Symbol symbol = Symbol.PLANT;
        corner = new Corner(cornerType, symbol);
    }

    @Test
    void isValid() {
        assertTrue(corner.isValid());
    }

    @Test
    void setValid() {
        corner = new Corner(CornerType.TOP_LEFT, Symbol.FUNGI);
        corner.setValid(false);
        assertFalse(corner.isValid());
    }

    @Test
    void getType() {
        assertEquals(CornerType.TOP_LEFT, corner.getType());
    }

    @Test
    void getSymbol() {
        assertEquals(Symbol.PLANT, corner.getSymbol());
    }

    @Test
    void setSymbol() {
        Symbol s = Symbol.INSECT;
        corner.setSymbol(s);
        assertEquals(s, corner.getSymbol());
    }

    @Test
    void testEquals(){
        assertEquals(corner, new Corner(CornerType.TOP_LEFT, Symbol.PLANT));
    }
}
