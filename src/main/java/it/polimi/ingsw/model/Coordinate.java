package it.polimi.ingsw.model;

import java.util.Objects;

/**
 * Represents a coordinate in a two-dimensional space.
 *
 * @author Angelo De Nadai
 */

public class Coordinate implements Comparable<Coordinate>{
    private final int x;
    private final int y;

    /**
     * Constructs a new Coordinate object with the specified x and y values.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Retrieves the x-coordinate.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Retrieves the y-coordinate.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }


    /**
     * Compares this Coordinate to another Object for equality.
     *
     * @param o the Object to compare to
     * @return true if the Object is a Coordinate with the same x and y values, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    /**
     * Calculates the hash code using the x and y coordinates.
     *
     * @return a hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Compare two coordinates.
     *
     * @param o the object to be compared.
     * @return 0 if x and y are identical, a value less than 0 if this.x < o.x or this.x = o.x and this.y < o.y,
     * a value greater than 0 if this.x > o.x or this.x = o.x and this.y > o.y
     */
    @Override
    public int compareTo(Coordinate o) {
        if (this.x == o.x) {
            return Integer.compare(this.y, o.y);
        } else {
            return Integer.compare(this.x, o.x);
        }
    }

    /**
     * Constructs the string that represents the coordinate.
     *
     * @return the string to print
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
