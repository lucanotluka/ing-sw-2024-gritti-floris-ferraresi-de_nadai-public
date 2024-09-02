package it.polimi.ingsw.model.cards;

/**
 * Represents a corner of a card.
 *
 * @author Fabio Marco Floris, Marco Ferraresi, Luca Gritti
 */
public class Corner {
    private boolean valid = true;
    private CornerType type;
    private Symbol symbol;

    /**
     * Constructs a new Corner object with the specified type and symbol.
     *
     * @param type   the type of the corner
     * @param symbol the symbol associated with the corner
     */
    public Corner(CornerType type, Symbol symbol) {
        this.type = type;
        this.symbol = symbol;
    }

    /**
     * Checks if the corner is valid.
     *
     * @return true if the corner is valid, false otherwise
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Sets the validity of the corner.
     *
     * @param valid the validity status to set
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * Retrieves the type of the corner.
     *
     * @return the type of the corner
     */
    public CornerType getType() {
        return type;
    }

    /**
     * Retrieves the symbol associated with the corner.
     *
     * @return the symbol associated with the corner
     */
    public Symbol getSymbol() {
        return symbol;
    }

    /**
     * Sets the symbol associated with the corner.
     *
     * @param symbol the symbol to set
     */
    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    /**
     * Checks if two corners are equal, to do that it checks the validation, the symbol and the type.
     *
     * @param o is the object to check
     * @return true if the two corners are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if(this.valid == ((Corner) o).valid &&
            this.symbol == ((Corner) o).symbol &&
            this.type == ((Corner) o).type
        ){
            return true;
        }
        return false;
    }
}