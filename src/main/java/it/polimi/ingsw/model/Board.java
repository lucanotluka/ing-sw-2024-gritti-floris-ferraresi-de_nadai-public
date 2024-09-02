package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.exceptions.*;

import java.util.*;
/**
 * The Board class represents the game board where cards can be placed during gameplay.
 * It manages the placement of cards, tracks available resources, and maintains the state of the board.
 *
 * @author Marco Ferraresi, Fabio Marco Floris
 */
public class Board {

    private BoardAvailability[][] matrix;
    private int multiplier;
    private int dim;
    private ArrayList<Coordinate> frontier;  //coordinate list of corners where you can add a card
    private Map<Coordinate, PlayableCard> map;  //"Mapping of association between occupied coordinates and inserted cards."
    private Map<Symbol, Integer> resources; //symbols visible and available on the game board

    /**
     * Constructs a new Board object.
     * Initializes the board matrix, frontier list, and resources map.
     */

    public Board() {
        frontier = new ArrayList<>();
        dim = 4;
        multiplier = 2;
        matrix = new BoardAvailability[dim][dim];

        for(BoardAvailability[] row : matrix) {
            Arrays.fill(row, BoardAvailability.UNINITIALIZED);
        }

        map = new HashMap<>();

        resources = new HashMap<>();

        resources.put(Symbol.INSECT, 0);
        resources.put(Symbol.PLANT, 0);
        resources.put(Symbol.FUNGI, 0);
        resources.put(Symbol.ANIMAL, 0);
        resources.put(Symbol.MANUSCRIPT, 0);
        resources.put(Symbol.INKWELL, 0);
        resources.put(Symbol.QUILL, 0);

    }

    /**
     * Place a card at the specified position on the board.
     *
     * @param position The coordinate where the card is to be placed.
     * @param card     The card to be placed.
     * @throws WrongPositionException if the position is already occupied or inaccessible.
     */

    public void setCard(Coordinate position, PlayableCard card) throws WrongPositionException{

        // versione corretta del map.containsKey()
        if(existsPosition(position)){
            throw new WrongPositionException("position already occupied");
        }

        map.put(position, card);
        matrix[position.getX()][position.getY()] = BoardAvailability.BLOCKED;
        frontier.remove(position);

    }

    /**
     * Return the card at the specified position on the board, or null if the position does not exist.
     *
     * @param position The coordinate from which to get the card.
     * @return The card at the specified position, or null if no card is present.
     */

    public PlayableCard getCard(Coordinate position) {
        for (Coordinate coordinate : map.keySet()) {
            if(position.compareTo(coordinate) == 0)
                return map.get(coordinate); // 0 implica le coordinate uguali (e' true)
        }
        return null;
    }

    /**
     * Retrieves the mapping of coordinates to cards on the board.
     *
     * @return The mapping of coordinates to cards.
     */
    public Map<Coordinate, PlayableCard> getMap() {
        return map;
    }

    /**
     * Retrieves the size of the map.
     *
     * @return the size of the map
     */
    public int getMapSize(){ return map.size();}

    /**
     * Constructs a matrix where every position is a vector of 6 elements: the id of the card, the validation of the 4 corners
     * and the face of the card (0 FRONT, 1 BACK)
     *
     * @return the three-dimensional array
     */
    public int[][][] compressBoard(){
        List<Map.Entry<Coordinate, PlayableCard>> elements = new ArrayList<>(map.entrySet());
        elements.sort(Map.Entry.comparingByKey());
        int[][][] matrix = new int[dim][dim][6];

        for (Map.Entry<Coordinate, PlayableCard> item: elements){
            matrix[item.getKey().getX()][item.getKey().getY()][0] = item.getValue().getCardId();
            if (item.getValue().getTop_left() == null || item.getValue().getTop_left().isValid())
                matrix[item.getKey().getX()][item.getKey().getY()][1] = 1;
            else
                matrix[item.getKey().getX()][item.getKey().getY()][1] = 0;
            if (item.getValue().getTop_right() == null || item.getValue().getTop_right().isValid())
                matrix[item.getKey().getX()][item.getKey().getY()][2] = 1;
            else
                matrix[item.getKey().getX()][item.getKey().getY()][2] = 0;
            if (item.getValue().getBot_left() == null || item.getValue().getBot_left().isValid())
                matrix[item.getKey().getX()][item.getKey().getY()][3] = 1;
            else
                matrix[item.getKey().getX()][item.getKey().getY()][3] = 0;
            if (item.getValue().getBot_right() == null || item.getValue().getBot_right().isValid())
                matrix[item.getKey().getX()][item.getKey().getY()][4] = 1;
            else
                matrix[item.getKey().getX()][item.getKey().getY()][4] = 0;
            matrix[item.getKey().getX()][item.getKey().getY()][5] = item.getValue().getSide().ordinal();
        }

        return matrix;
    }

    /**
     * Retrieves the frontier of coordinates on the board.
     *
     * @return The list of free coordinates on the board.
     */
    public ArrayList<Coordinate> getFrontier() {
        return frontier;
    }

    /**
     * Counts the number of resources of the specified symbol available on the board.
     *
     * @param symbol The symbol for which to count the resources.
     * @return The number of resources of the specified symbol.
     */
    public int countResources(Symbol symbol) {
        return resources.get(symbol);
    }

    /**
     * getter of the resources map
     * @return the mapping between each resource and its quantity
     */
    public Map<Symbol, Integer> getResources() {
        return resources;
    }

    /**
     * Sets the availability of the specified position to FREE.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     */
    public void setFREE(int x, int y){
        if(matrix[x][y] == BoardAvailability.UNINITIALIZED){
            matrix[x][y] = BoardAvailability.FREE;
            frontier.add(new Coordinate(x, y));
            Collections.sort(frontier);
        }
    }

    /**
     * Sets the availability of the specified position to BLOCKED.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     */
    public void setBLOCKED(int x, int y){
        matrix[x][y] = BoardAvailability.BLOCKED;
        frontier.remove(new Coordinate(x, y));
    }

    /**
     * Adds resources to the board based on the provided card.
     *
     * @param card The card from which to add resources.
     */
    public void addResources(PlayableCard card){
        if(card.getSide() == Face.FRONT || card.getSymbol() == Symbol.EMPTY){

            // card is StarterCard!
            if(card.getSymbol() == Symbol.EMPTY && card.getSide() == Face.FRONT){
                for (Symbol symbol : ((StarterCard) card).getSymbols()) {
                    resources.put(symbol, resources.get(symbol) + 1);
                }
            }

            // adding resources for all corners, if possible
            if(card.getTop_left() != null){
                if(card.getTop_left().getSymbol() != Symbol.EMPTY){
                    resources.put(card.getTop_left().getSymbol(),resources.get(card.getTop_left().getSymbol())+1);
                }
            }
            if(card.getTop_right() != null){
                if(card.getTop_right().getSymbol() != Symbol.EMPTY){
                    resources.put(card.getTop_right().getSymbol(),resources.get(card.getTop_right().getSymbol())+1);
                }
            }
            if(card.getBot_left() != null){
                if(card.getBot_left().getSymbol() != Symbol.EMPTY){
                    resources.put(card.getBot_left().getSymbol(),resources.get(card.getBot_left().getSymbol())+1);
                }
            }
            if(card.getBot_right() != null){
                if(card.getBot_right().getSymbol() != Symbol.EMPTY){
                    resources.put(card.getBot_right().getSymbol(),resources.get(card.getBot_right().getSymbol())+1);
                }
            }
        }
        else {       // card is Resource or Gold, but back-sided
            resources.put(card.getSymbol(),resources.get(card.getSymbol())+1);
        }
    }

    /**
     * Removes a resource associated with the specified covered corner.
     *
     * @param coveredCorner The corner whose resource is to be removed.
     */
    public void removeResource(Corner coveredCorner){
        if(coveredCorner.getSymbol() == Symbol.EMPTY){
            return;
        }
        resources.put(coveredCorner.getSymbol(), resources.get(coveredCorner.getSymbol())-1);
    }

    /**
     * Checks if a position with the specified coordinates exists on the board.
     *
     * @param position the coordinate of the wanted card
     * @return true if the position exists on the board, false otherwise.
     */
    public boolean existsPosition(Coordinate position){

        // confronto questa coordinata con tutte quelle della mappa

        for (Coordinate coordinate : map.keySet()) {
            if(position.compareTo(coordinate) == 0) return true; // 0 implica le coordinate uguali (e' true)
        }
        return false;
    }

    /**
     * Place a card on the board.
     *
     * @param card to be placed
     * @param coord is the coordinate where to place the card
     */
    public void cardPlacement(PlayableCard card, Coordinate coord){
        try {
            setCard(coord, card);
            addResources(card);
        } catch (WrongPositionException e) {
            System.out.println(e);
        }

        if (coord.getX() == 0 || coord.getX() == dim - 1 || coord.getY() == 0 || coord.getY() == dim - 1){
            adjustMatrix();
        }

        for (Map.Entry<Coordinate, PlayableCard> entry : map.entrySet()) {
            if (entry.getValue().equals(card)) {
                coord = entry.getKey();
                break;
            }
        }

        updateCornersAvailabilities(card, coord);
    }

    /**
     * Update matrix availability and validation of covered corners
     *
     * @param card is the card we place on the board
     * @param coord is the coordinate where we placed the card
     */
    private void updateCornersAvailabilities(PlayableCard card, Coordinate coord) {

        PlayableCard linkedCard;

        //                  Top_Left Corner
        // Set Availability when uninitialized matrix cell. Skip if top-left card already placed
        if (card.getTop_left() != null) {
            setFREE(coord.getX() - 1, coord.getY() - 1);
        } else {
            setBLOCKED(coord.getX() - 1, coord.getY() - 1);
        }
        // Update CornerValidity of covered cards
        linkedCard = getCard(new Coordinate(coord.getX() - 1, coord.getY() - 1));
        if(linkedCard != null) {
            linkedCard.getBot_right().setValid(false);
            removeResource(linkedCard.getBot_right());
        }

        //Top_Right
        linkedCard = getCard(new Coordinate(coord.getX()-1, coord.getY()+1));
        if(card.getTop_right() != null){
            setFREE(coord.getX()-1, coord.getY()+1);
        } else{
            setBLOCKED(coord.getX() -1, coord.getY()+1);
        }
        if(linkedCard != null){
            linkedCard.getBot_left().setValid(false);
            removeResource(linkedCard.getBot_left());
        }
        //Bot_Left
        linkedCard = getCard(new Coordinate(coord.getX()+1, coord.getY()-1));
        if(card.getBot_left() != null){
            setFREE(coord.getX()+1, coord.getY()-1);
        } else{
            setBLOCKED(coord.getX() +1, coord.getY()-1);
        }
        if(linkedCard != null){
            linkedCard.getTop_right().setValid(false);
            removeResource(linkedCard.getTop_right());
        }
        //Bot_Right
        linkedCard = getCard(new Coordinate(coord.getX()+1, coord.getY()+1));
        if(card.getBot_right() != null){
            setFREE(coord.getX()+1, coord.getY()+1);
        } else{
            setBLOCKED(coord.getX() +1, coord.getY()+1);
        }
        if(linkedCard != null){
            linkedCard.getTop_left().setValid(false);
            removeResource(linkedCard.getTop_left());
        }
    }

    /**
     * Dynamic matrix: if we place a card on the sides we duplicate the dimension of the matrix and update frontier and map.
     */
    private void adjustMatrix(){
        int dim1 = dim * 2;
        BoardAvailability[][] matrix1 = new BoardAvailability[dim1][dim1];
        Map<Coordinate, PlayableCard> map1 = new HashMap<>();

        for (BoardAvailability[] row : matrix1) {
            Arrays.fill(row, BoardAvailability.UNINITIALIZED);
        }

        for (int i = 0; i < dim; i++){
            for (int j = 0; j < dim; j++){
                matrix1[i + multiplier][j + multiplier] = matrix[i][j];
            }
        }

        for (int i = 0; i < frontier.size(); i++) {
            int x = frontier.get(i).getX() + multiplier;
            int y = frontier.get(i).getY() + multiplier;
            Coordinate coordinate = new Coordinate(x, y);
            frontier.set(i, coordinate);
        }

        for (Map.Entry<Coordinate, PlayableCard> entry : map.entrySet()) {
            int x = entry.getKey().getX() + multiplier;
            int y = entry.getKey().getY() + multiplier;
            Coordinate coordinate = new Coordinate(x, y);
            map1.put(coordinate, entry.getValue());
        }

        map = map1;
        dim = dim1;
        multiplier = multiplier * 2;
        matrix = matrix1;
    }

    /**
     * Retrieves the dimension of the matrix.
     *
     * @return the dimension of the matrix
     */
    public int getDim() {
        return dim;
    }
}
