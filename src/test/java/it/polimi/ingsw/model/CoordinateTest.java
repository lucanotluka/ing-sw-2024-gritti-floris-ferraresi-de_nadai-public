package it.polimi.ingsw.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

class CoordinateTest {
    Coordinate coord;

    @BeforeEach
    void setUp() {
        coord = new Coordinate(0, 0);
    }

    @Test
    void getX() {
        Assertions.assertEquals(0, coord.getX());
    }

    @Test
    void getY() {
        Assertions.assertEquals(0, coord.getY());
    }

    @Test
    void testEquals() {
        Assertions.assertEquals(new Coordinate(0, 0), coord);
    }

    @Test
    void compareTo() {
        Assertions.assertEquals(0, coord.compareTo(new Coordinate(0, 0)));
    }

    @Test
    void testToString() {
        Assertions.assertEquals("(0, 0)", coord.toString());
    }

    @Test
    void testHashCode(){
        Assertions.assertEquals(coord.hashCode(), Objects.hash(coord.getX(), coord.getY()));
    }
}