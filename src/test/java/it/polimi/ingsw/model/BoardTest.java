package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.WrongPositionException;
import it.polimi.ingsw.model.cards.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Board board;

    @BeforeEach
    void setUp(){
        board = new Board();
    }
    @Test
    void setCard() {
        int id = 84;
        ArrayList<Symbol> symbols = new ArrayList<>();
        symbols.add(Symbol.ANIMAL);
        symbols.add(Symbol.INSECT);
        Corner top_left = new Corner(CornerType.TOP_LEFT, Symbol.EMPTY);
        Corner top_right = new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY);
        Corner bot_left = new Corner(CornerType.BOT_LEFT, Symbol.EMPTY);
        Corner bot_right = new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY);
        Face face = Face.FRONT;
        StarterCard starterCard = new StarterCard(id, symbols, face, top_left, top_right, bot_left, bot_right);
        int x = 2;
        int y = 2;
        Coordinate coordinate = new Coordinate(x, y);
        try{
            board.setCard(coordinate, starterCard);
        } catch(WrongPositionException e){
            System.out.println(e);
        }
        assertEquals(starterCard, board.getCard(coordinate));

        int id1 = 84;
        ArrayList<Symbol> symbols1 = new ArrayList<>();
        symbols1.add(Symbol.ANIMAL);
        symbols1.add(Symbol.INSECT);
        Corner top_left1 = new Corner(CornerType.TOP_LEFT, Symbol.EMPTY);
        Corner top_right1 = new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY);
        Corner bot_left1 = new Corner(CornerType.BOT_LEFT, Symbol.EMPTY);
        Corner bot_right1 = new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY);
        Face face1 = Face.FRONT;
        StarterCard starterCard1 = new StarterCard(id1, symbols1, face1, top_left1, top_right1, bot_left1, bot_right1);
        int x1 = 2;
        int y1 = 2;
        Coordinate coordinate1 = new Coordinate(x1, y1);
        try{
            board.setCard(coordinate1, starterCard1);
        } catch(WrongPositionException e){
            System.err.println(e);
        }
    }

    @Test
    void getCard() {
        int id = 84;
        ArrayList<Symbol> symbols = new ArrayList<>();
        symbols.add(Symbol.ANIMAL);
        symbols.add(Symbol.INSECT);
        Corner top_left = new Corner(CornerType.TOP_LEFT, Symbol.EMPTY);
        Corner top_right = new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY);
        Corner bot_left = new Corner(CornerType.BOT_LEFT, Symbol.EMPTY);
        Corner bot_right = new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY);
        Face face2 = Face.FRONT;
        StarterCard starterCard = new StarterCard(id, symbols, face2, top_left, top_right, bot_left, bot_right);
        int x = 2;
        int y = 2;
        Coordinate coordinate = new Coordinate(x, y);
        try{
            board.setCard(coordinate, starterCard);
        } catch(WrongPositionException e){
            System.out.println(e);
        }
        assertEquals(starterCard, board.getCard(coordinate));
    }

    @Test
    void getMapSize(){
        try {
            board.setCard(new Coordinate(2,2), new ResourceCard(
                    1, Symbol.FUNGI, 0, new Corner(CornerType.TOP_LEFT, Symbol.FUNGI),
                    new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY), new Corner(CornerType.BOT_LEFT, Symbol.FUNGI)
            ));
        } catch (WrongPositionException e) {
            throw new RuntimeException(e);
        }
        assertEquals(1, board.getMapSize());
    }

    @Test
    void compressBoard(){
        int id = 81;
        ArrayList<Symbol> symbols = new ArrayList<>();
        ArrayList<Coordinate> frontier = new ArrayList<>();
        Map<Coordinate, PlayableCard> map = new HashMap<>();
        Map<Symbol, Integer> resources = new HashMap<>();

        symbols.add(Symbol.INSECT);
        Corner top_left = new Corner(CornerType.TOP_LEFT, Symbol.EMPTY);
        Corner top_right = new Corner(CornerType.TOP_RIGHT, Symbol.PLANT);
        Corner bot_left = new Corner(CornerType.BOT_LEFT, Symbol.INSECT);
        Corner bot_right = new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY);
        Face face = Face.FRONT;
        StarterCard starterCard = new StarterCard(id, symbols, face, top_left, top_right, bot_left, bot_right);
        int x = 2;
        int y = 2;
        Coordinate coordinate = new Coordinate(x, y);
        board.cardPlacement(starterCard, coordinate);

        int id1 = 26;
        Symbol symbol1 = Symbol.ANIMAL;
        int points = 0;
        Corner top_left1 = new Corner(CornerType.TOP_LEFT, Symbol.PLANT);
        Corner top_right1 = new Corner(CornerType.TOP_RIGHT, Symbol.ANIMAL);
        Corner bot_right1 = new Corner(CornerType.BOT_RIGHT, Symbol.MANUSCRIPT);
        ResourceCard resourceCard = new ResourceCard(id1, symbol1, points, top_left1, top_right1, bot_right1);
        int x1 = 3;
        int y1 = 1;
        Coordinate coordinate1 = new Coordinate(x1, y1);
        resourceCard.setBack();
        board.cardPlacement(resourceCard, coordinate1);

        int[][][] matrix = new int[8][8][6];
        matrix[4][4][0] = starterCard.getCardId();
        matrix[4][4][1] = 1;
        matrix[4][4][2] = 1;
        matrix[4][4][3] = 0;
        matrix[4][4][4] = 1;
        matrix[4][4][5] = 0;
        matrix[5][3][0] = resourceCard.getCardId();
        matrix[5][3][1] = 1;
        matrix[5][3][2] = 1;
        matrix[5][3][3] = 1;
        matrix[5][3][4] = 1;
        matrix[5][3][5] = 1;

        assertArrayEquals(matrix, board.compressBoard());
    }
    @Test
    void getMap() {
        int id = 84;
        ArrayList<Symbol> symbols = new ArrayList<>();
        symbols.add(Symbol.ANIMAL);
        symbols.add(Symbol.INSECT);
        Corner top_left = new Corner(CornerType.TOP_LEFT, Symbol.EMPTY);
        Corner top_right = new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY);
        Corner bot_left = new Corner(CornerType.BOT_LEFT, Symbol.EMPTY);
        Corner bot_right = new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY);
        Face face = Face.FRONT;
        StarterCard starterCard = new StarterCard(id, symbols, face, top_left, top_right, bot_left, bot_right);
        int x = 2;
        int y = 2;
        Coordinate coordinate = new Coordinate(x, y);
        try{
            board.setCard(coordinate, starterCard);
        } catch(WrongPositionException e){
            System.out.println(e);
        }
        Map<Coordinate, PlayableCard> map = new HashMap<>();
        map.put(coordinate, starterCard);
        assertEquals(map, board.getMap()); 
    }

    @Test
    void getFrontier() {
        int id = 84;
        ArrayList<Symbol> symbols = new ArrayList<>();
        symbols.add(Symbol.ANIMAL);
        symbols.add(Symbol.INSECT);
        Corner top_left = new Corner(CornerType.TOP_LEFT, Symbol.EMPTY);
        Corner top_right = new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY);
        Corner bot_left = new Corner(CornerType.BOT_LEFT, Symbol.EMPTY);
        Corner bot_right = new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY);
        Face face = Face.FRONT;
        StarterCard starterCard = new StarterCard(id, symbols, face, top_left, top_right, bot_left, bot_right);
        int x = 2;
        int y = 2;
        Coordinate coordinate = new Coordinate(x, y);
        board.cardPlacement(starterCard, coordinate);

        List<Coordinate> frontier = new ArrayList<>();
        Coordinate coordinate1 = new Coordinate(x+1, y+1);
        Coordinate coordinate2 = new Coordinate(x+1, y-1);
        Coordinate coordinate3 = new Coordinate(x-1, y-1);
        Coordinate coordinate4 = new Coordinate(x-1, y+1);

        frontier.add(coordinate3);
        frontier.add(coordinate4);
        frontier.add(coordinate2);
        frontier.add(coordinate1);

        assertEquals(frontier, board.getFrontier());
    }

    @Test
    void countResources() {
        int num_symbol = 1;

        int id = 84;
        ArrayList<Symbol> symbols = new ArrayList<>();
        symbols.add(Symbol.ANIMAL);
        symbols.add(Symbol.INSECT);
        Corner top_left = new Corner(CornerType.TOP_LEFT, Symbol.EMPTY);
        Corner top_right = new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY);
        Corner bot_left = new Corner(CornerType.BOT_LEFT, Symbol.EMPTY);
        Corner bot_right = new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY);
        Face face = Face.FRONT;
        StarterCard starterCard = new StarterCard(id, symbols, face, top_left, top_right, bot_left, bot_right);
        int x = 2;
        int y = 2;
        Coordinate coordinate = new Coordinate(x, y);
        board.cardPlacement(starterCard, coordinate);

        assertEquals(num_symbol, board.countResources(Symbol.ANIMAL));
        assertEquals(num_symbol, board.countResources(Symbol.INSECT));
        assertEquals(0, board.countResources(Symbol.INKWELL));
    }

    @Test
    void getResources(){
        int id = 81;
        ArrayList<Symbol> symbols = new ArrayList<>();
        ArrayList<Coordinate> frontier = new ArrayList<>();
        Map<Coordinate, PlayableCard> map = new HashMap<>();
        Map<Symbol, Integer> resources = new HashMap<>();

        symbols.add(Symbol.INSECT);
        Corner top_left = new Corner(CornerType.TOP_LEFT, Symbol.EMPTY);
        Corner top_right = new Corner(CornerType.TOP_RIGHT, Symbol.PLANT);
        Corner bot_left = new Corner(CornerType.BOT_LEFT, Symbol.INSECT);
        Corner bot_right = new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY);
        Face face = Face.FRONT;
        StarterCard starterCard = new StarterCard(id, symbols, face, top_left, top_right, bot_left, bot_right);
        int x = 2;
        int y = 2;
        Coordinate coordinate = new Coordinate(x, y);
        board.cardPlacement(starterCard, coordinate);

        frontier.add(new Coordinate(1, 1));
        frontier.add(new Coordinate(1, 3));
        frontier.add(new Coordinate(3, 1));
        frontier.add(new Coordinate(3, 3));
        map.put(coordinate, starterCard);
        resources.put(Symbol.QUILL, 0);
        resources.put(Symbol.FUNGI, 0);
        resources.put(Symbol.INKWELL, 0);
        resources.put(Symbol.PLANT, 1);
        resources.put(Symbol.MANUSCRIPT, 0);
        resources.put(Symbol.ANIMAL, 0);
        resources.put(Symbol.INSECT, 2);

        assertEquals(resources, board.getResources());
    }
    @Test
    void setFREE() {
        int x = 2;
        int y = 2;
        Coordinate coordinate = new Coordinate(x, y);
        List<Coordinate> frontier = new ArrayList<>();

        frontier.add(coordinate);
        board.setFREE(x, y);
        assertEquals(frontier, board.getFrontier());
    }

    @Test
    void setBLOCKED() {
        int x = 2;
        int y = 2;
        Coordinate coordinate = new Coordinate(x, y);
        List<Coordinate> frontier = new ArrayList<>();

        int x1 = 1;
        int y1 = 1;
        Coordinate coordinate1 = new Coordinate(x1, y1);

        frontier.add(coordinate);
        frontier.add(coordinate1);
        board.setFREE(x1, y1);
        frontier.remove(coordinate);
        board.setBLOCKED(x, y);

        assertEquals(frontier, board.getFrontier());
    }

    @Test
    void addResources() {
        int id = 84;
        ArrayList<Symbol> symbols = new ArrayList<>();
        Map<Symbol, Integer> resources = new HashMap<>();
        symbols.add(Symbol.ANIMAL);
        symbols.add(Symbol.INSECT);
        Corner top_left = new Corner(CornerType.TOP_LEFT, Symbol.EMPTY);
        Corner top_right = new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY);
        Corner bot_left = new Corner(CornerType.BOT_LEFT, Symbol.EMPTY);
        Corner bot_right = new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY);
        Face face = Face.FRONT;
        StarterCard starterCard = new StarterCard(id, symbols, face, top_left, top_right, bot_left, bot_right);

        resources.put(Symbol.ANIMAL, 1);
        resources.put(Symbol.INSECT, 1);
        board.addResources(starterCard);

        assertEquals(resources.get(Symbol.ANIMAL), board.countResources(Symbol.ANIMAL));
        assertEquals(resources.get(Symbol.INSECT), board.countResources(Symbol.INSECT));
    }

    @Test
    void removeResource() {
        int id = 81;
        ArrayList<Symbol> symbols = new ArrayList<>();
        Map<Symbol, Integer> resources = new HashMap<>();
        symbols.add(Symbol.INSECT);
        Corner top_left = new Corner(CornerType.TOP_LEFT, Symbol.EMPTY);
        Corner top_right = new Corner(CornerType.TOP_RIGHT, Symbol.PLANT);
        Corner bot_left = new Corner(CornerType.BOT_LEFT, Symbol.INSECT);
        Corner bot_right = new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY);
        Face face = Face.FRONT;
        StarterCard starterCard = new StarterCard(id, symbols, face, top_left, top_right, bot_left, bot_right);
        int x = 2;
        int y = 2;
        Coordinate coordinate = new Coordinate(x, y);
        try{
            board.setCard(coordinate, starterCard);
        } catch(WrongPositionException e){
            System.out.println(e);
        }
        board.addResources(starterCard);
        resources.put(Symbol.INSECT, 2);
        resources.put(Symbol.PLANT, 1);

        int id1 = 26;
        Symbol symbol1 = Symbol.ANIMAL;
        int points = 0;
        Corner top_left1 = new Corner(CornerType.TOP_LEFT, Symbol.PLANT);
        Corner top_right1 = new Corner(CornerType.TOP_RIGHT, Symbol.ANIMAL);
        Corner bot_right1 = new Corner(CornerType.BOT_RIGHT, Symbol.MANUSCRIPT);
        ResourceCard resourceCard = new ResourceCard(id1, symbol1, points, top_left1, top_right1, bot_right1);
        int x1 = 3;
        int y1 = 1;
        Coordinate coordinate1 = new Coordinate(x1, y1);
        try{
            board.setCard(coordinate1, resourceCard);
        } catch(WrongPositionException e){
            System.out.println(e);
        }
        board.addResources(resourceCard);
        resources.put(Symbol.PLANT, resources.get(Symbol.PLANT)+1);
        resources.put(Symbol.ANIMAL, 1);
        resources.put(Symbol.MANUSCRIPT, 1);

        board.removeResource(bot_left);
        resources.put(Symbol.INSECT, resources.get(Symbol.INSECT)-1);
        assertEquals(resources.get(Symbol.PLANT), board.countResources(Symbol.PLANT));
        assertEquals(resources.get(Symbol.ANIMAL), board.countResources(Symbol.ANIMAL));
        assertEquals(resources.get(Symbol.MANUSCRIPT), board.countResources(Symbol.MANUSCRIPT));
        assertEquals(resources.get(Symbol.INSECT), board.countResources(Symbol.INSECT));
    }

    @Test
    void existsPosition() {
        int id = 84;
        ArrayList<Symbol> symbols = new ArrayList<>();
        symbols.add(Symbol.ANIMAL);
        symbols.add(Symbol.INSECT);
        Corner top_left = new Corner(CornerType.TOP_LEFT, Symbol.EMPTY);
        Corner top_right = new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY);
        Corner bot_left = new Corner(CornerType.BOT_LEFT, Symbol.EMPTY);
        Corner bot_right = new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY);
        Face face = Face.FRONT;
        StarterCard starterCard = new StarterCard(id, symbols, face, top_left, top_right, bot_left, bot_right);
        int x = 2;
        int y = 2;
        Coordinate coordinate = new Coordinate(x, y);
        board.cardPlacement(starterCard, coordinate);
        boolean check = true;

        assertEquals(check, board.existsPosition(coordinate));
    }

    @Test
    void cardPlacement() {
        int id = 81;
        ArrayList<Symbol> symbols = new ArrayList<>();
        ArrayList<Coordinate> frontier = new ArrayList<>();
        Map<Coordinate, PlayableCard> map = new HashMap<>();
        Map<Symbol, Integer> resources = new HashMap<>();

        symbols.add(Symbol.INSECT);
        Corner top_left = new Corner(CornerType.TOP_LEFT, Symbol.EMPTY);
        Corner top_right = new Corner(CornerType.TOP_RIGHT, Symbol.PLANT);
        Corner bot_left = new Corner(CornerType.BOT_LEFT, Symbol.INSECT);
        Corner bot_right = new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY);
        Face face = Face.FRONT;
        StarterCard starterCard = new StarterCard(id, symbols, face, top_left, top_right, bot_left, bot_right);
        int x = 2;
        int y = 2;
        Coordinate coordinate = new Coordinate(x, y);
        board.cardPlacement(starterCard, coordinate);

        int id1 = 26;
        Symbol symbol1 = Symbol.ANIMAL;
        int points = 0;
        Corner top_left1 = new Corner(CornerType.TOP_LEFT, Symbol.PLANT);
        Corner top_right1 = new Corner(CornerType.TOP_RIGHT, Symbol.ANIMAL);
        Corner bot_right1 = new Corner(CornerType.BOT_RIGHT, Symbol.MANUSCRIPT);
        ResourceCard resourceCard = new ResourceCard(id1, symbol1, points, top_left1, top_right1, bot_right1);
        int x1 = 3;
        int y1 = 1;
        Coordinate coordinate1 = new Coordinate(x1, y1);
        board.cardPlacement(resourceCard, coordinate1);

        map.put(new Coordinate(x + 2, y + 2), starterCard);
        map.put(new Coordinate(x1 + 2, y1 + 2), resourceCard);
        resources.put(Symbol.PLANT, 2);
        resources.put(Symbol.ANIMAL, 1);
        resources.put(Symbol.MANUSCRIPT, 1);
        resources.put(Symbol.INSECT, 1);
        Coordinate coordinate2 = new Coordinate(3, 3);
        Coordinate coordinate3 = new Coordinate(3, 5);
        Coordinate coordinate4 = new Coordinate(4, 2);
        Coordinate coordinate5 = new Coordinate(5, 5);
        Coordinate coordinate6 = new Coordinate(6, 4);
        frontier.add(coordinate2);
        frontier.add(coordinate3);
        frontier.add(coordinate4);
        frontier.add(coordinate5);
        frontier.add(coordinate6);

        assertEquals(frontier, board.getFrontier());
        assertEquals(map, board.getMap());
        assertEquals(resources.get(Symbol.PLANT), board.countResources(Symbol.PLANT));
        assertEquals(resources.get(Symbol.INSECT), board.countResources(Symbol.INSECT));
        assertEquals(resources.get(Symbol.MANUSCRIPT), board.countResources(Symbol.MANUSCRIPT));
        assertEquals(resources.get(Symbol.ANIMAL), board.countResources(Symbol.ANIMAL));
    }

    @Test
    void getDim(){
        assertEquals(4, board.getDim());
    }
}