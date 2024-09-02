package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.UnplayableCardException;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.objectives.ObjectiveCard;
import it.polimi.ingsw.model.objectives.PatternObjectiveCard;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player player;
    @BeforeEach
    void setUp(){
        String nickName = "Mario";
        player = new Player(nickName);
    }

    @Test
    void getObjectiveCard(){
        int id5 = 87;
        int points5 = 2;
        HashMap<Coordinate, Symbol> pattern = new HashMap<>();
        pattern.put(new Coordinate(0, 0), Symbol.FUNGI);
        pattern.put(new Coordinate(-1, 1), Symbol.FUNGI);
        pattern.put(new Coordinate(-2, 2), Symbol.FUNGI);
        ObjectiveCard patternObjectiveCard = new PatternObjectiveCard(id5, points5, pattern);
        player.setObjectiveCard(patternObjectiveCard);
        assertEquals(patternObjectiveCard, player.getObjectiveCard());
    }

    @Test
    void setObjectiveCard(){
        int id5 = 87;
        int points5 = 2;
        HashMap<Coordinate, Symbol> pattern = new HashMap<>();
        pattern.put(new Coordinate(0, 0), Symbol.FUNGI);
        pattern.put(new Coordinate(-1, 1), Symbol.FUNGI);
        pattern.put(new Coordinate(-2, 2), Symbol.FUNGI);
        ObjectiveCard patternObjectiveCard = new PatternObjectiveCard(id5, points5, pattern);
        player.setObjectiveCard(patternObjectiveCard);
        assertEquals(patternObjectiveCard, player.getObjectiveCard());
    }

    @Test
    void setToken(){
        Token token = Token.RED;
        player.setToken(token);
        assertEquals(token, player.getToken());
    }

    @Test
    void getToken(){
        Token token = Token.RED;
        player.setToken(token);
        assertEquals(token, player.getToken());
    }

    @Test
    void setFirstPlayerToken(){
        assertNull(player.getFirstPlayerToken());
        player.setFirstPlayerToken(Token.BLACK);
        assertEquals(Token.BLACK, player.getFirstPlayerToken());
    }

    @Test
    void getFirstPlayerToken(){
        assertNull(player.getFirstPlayerToken());
        player.setFirstPlayerToken(Token.BLACK);
        assertEquals(Token.BLACK, player.getFirstPlayerToken());
    }

    @Test
    void placeStarterOnBoard(){
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
        player.placeStarterOnBoard(starterCard);

        Board board = new Board();
        board.cardPlacement(starterCard, new Coordinate(2, 2));
        assertEquals(board.getFrontier(), player.getBoard().getFrontier());
        assertEquals(board.getMap(), player.getBoard().getMap());
        assertEquals(board.getResources(), player.getBoard().getResources());
        assertEquals(board.getCard(new Coordinate(2, 2)), player.getBoard().getCard(new Coordinate(2, 2)));
    }

    @Test
    void getStarterCard(){
        assertNull(player.getStarterCard());
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
        player.placeStarterOnBoard(starterCard);
        assertEquals(starterCard, player.getStarterCard());
    }

    @Test
    void getBoard(){
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
        player.placeStarterOnBoard(starterCard);

        Board board = new Board();
        board.cardPlacement(starterCard, new Coordinate(2, 2));
        Board board1 = player.getBoard();
        assertEquals(board.getFrontier(), board1.getFrontier());
        assertEquals(board.getMap(), board1.getMap());
        assertEquals(board.getResources(), board1.getResources());
        assertEquals(board.getCard(new Coordinate(2, 2)), board1.getCard(new Coordinate(2, 2)));

    }

    @Test
    void getCardsInHand() {
        assertTrue(player.getCardsInHand().isEmpty());
        ResourceCard card1 = new ResourceCard(1, Symbol.FUNGI, 0, new Corner(CornerType.TOP_LEFT, Symbol.EMPTY), new Corner(CornerType.TOP_RIGHT, Symbol.FUNGI), new Corner(CornerType.BOT_LEFT, Symbol.FUNGI));
        player.draw(card1);
        assertFalse(player.getCardsInHand().isEmpty());
        assertEquals(card1,player.getCardsInHand().getFirst());
    }

    @Test
    void drawResourceCard() {
        ResourceCard card1 = new ResourceCard(1, Symbol.FUNGI, 0, new Corner(CornerType.TOP_LEFT, Symbol.EMPTY), new Corner(CornerType.TOP_RIGHT, Symbol.FUNGI), new Corner(CornerType.BOT_LEFT, Symbol.FUNGI));
        player.draw(card1);
        assertTrue(player.getCardsInHand().contains(card1));
    }

    @Test
    void drawGoldCard() {
        Map<Symbol, Integer> condition = new HashMap<>();
        condition.put(Symbol.INSECT, 3);
        GoldCard card1 = new GoldCard(78, new Corner(CornerType.TOP_LEFT, Symbol.EMPTY), new Corner(CornerType.TOP_RIGHT, Symbol.MANUSCRIPT),3, Symbol.INSECT, PointsType.NULL, condition);
        player.draw(card1);
        assertTrue(player.getCardsInHand().contains(card1));
    }

    @Test
    void getPlayerPoints() {
        assertEquals(0,player.getPlayerPoints());
        int id = 28;
        Corner corner = new Corner(CornerType.TOP_RIGHT, Symbol.EMPTY);
        Corner corner1 = new Corner(CornerType.BOT_LEFT, Symbol.ANIMAL);
        Corner corner2 = new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY);
        Symbol symbol = Symbol.ANIMAL;
        int points  = 1;
        ResourceCard resourceCard = new ResourceCard(id, symbol, points, corner, corner1, corner2);
        try {
            player.playCard(resourceCard, new Coordinate(2,2));
        } catch (UnplayableCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(1, player.getPlayerPoints());
        points = 10;
        player.setPoints(points);
        assertEquals(10, player.getPlayerPoints());
    }

    @Test
    void setPoints(){
        int points = 5;
        player.setPoints(5);
        assertEquals(points, player.getPlayerPoints());
    }

    @Test
    void updatePlayerPoints() {
        int points = 5;
        player.updatePlayerPoints(points);
        assertEquals(points, player.getPlayerPoints());
        points = 10;
        player.updatePlayerPoints(points);
        assertEquals(15, player.getPlayerPoints());
    }

    @Test
    void getNickName() {
        String name = "Mario";
        assertEquals(name, player.getNickName());
    }

    @Test
    void playStarterCard(){
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
        try {
            player.playCard(starterCard, new Coordinate(2,2));
        } catch (UnplayableCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0, player.getPlayerPoints());
        assertEquals(starterCard, player.getBoard().getCard(new Coordinate(2,2)));
    }
    @Test
    void playResourceCard() {
        int id = 26;
        Symbol symbol = Symbol.ANIMAL;
        int points = 0;
        Corner top_left = new Corner(CornerType.TOP_LEFT, Symbol.PLANT);
        Corner top_right = new Corner(CornerType.TOP_RIGHT, Symbol.ANIMAL);
        Corner bot_right = new Corner(CornerType.BOT_RIGHT, Symbol.MANUSCRIPT);
        ResourceCard resourceCard = new ResourceCard(id, symbol, points, top_left, top_right, bot_right);
        try{
            player.playCard(resourceCard,new Coordinate(2,2));
        } catch (UnplayableCardException e){
            System.err.println(e);
        }
        assertEquals(0, player.getPlayerPoints());
        assertEquals(resourceCard, player.getBoard().getCard(new Coordinate(2,2)));
    }

    @Test
    void playGoldCardWithoutCondition(){
        Map<Symbol, Integer> condition = new HashMap<>();
        condition.put(Symbol.INSECT, 3);
        GoldCard card1 = new GoldCard(78, new Corner(CornerType.TOP_LEFT, Symbol.EMPTY), new Corner(CornerType.TOP_RIGHT, Symbol.MANUSCRIPT),3, Symbol.INSECT, PointsType.NULL, condition);
        try{
            player.playCard(card1,new Coordinate(2,2));
        } catch (UnplayableCardException e){
            System.err.println(e);
        }
        assertEquals(0, player.getPlayerPoints());
        assertNull(player.getBoard().getCard(new Coordinate(2, 2)));
    }

    @Test
    void playGoldCardWithCondition(){
        Map<Symbol, Integer> condition = new HashMap<>();
        condition.put(Symbol.INSECT, 3);
        GoldCard card1 = new GoldCard(78, new Corner(CornerType.TOP_LEFT, Symbol.EMPTY), new Corner(CornerType.TOP_RIGHT, Symbol.MANUSCRIPT),3, Symbol.INSECT, PointsType.NULL, condition);

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
        try {
            player.playCard(starterCard, new Coordinate(2,2));
        } catch (UnplayableCardException e) {
            throw new RuntimeException(e);
        }

        int id1 = 31;
        Symbol symbol = Symbol.INSECT;
        int points = 0;
        Corner top_left1 = new Corner(CornerType.TOP_LEFT, Symbol.INSECT);
        Corner top_right1 = new Corner(CornerType.TOP_RIGHT, Symbol.INSECT);
        Corner bot_left1 = new Corner(CornerType.BOT_LEFT, Symbol.EMPTY);
        ResourceCard resourceCard = new ResourceCard(id1, symbol, points, top_left1, top_right1, bot_left1);
        try{
            player.playCard(resourceCard,new Coordinate(3,1));
        } catch (UnplayableCardException e){
            System.err.println(e);
        }

        try {
            player.playCard(card1, new Coordinate(3,3));
        } catch (UnplayableCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3, player.getPlayerPoints());
        assertEquals(card1, player.getBoard().getCard(new Coordinate(3,3)));
    }

    @Test
    void testEquals(){
        Player player1 = new Player("Mario");
        player.setPoints(7);
        player1.setPoints(7);
        assertEquals(player1, player);
    }

    @Test
    void setCount(){
        int count = 3;
        player.setCount(3);
        assertEquals(count, player.getCount());
    }

    @Test
    void getCount(){
        int count = 3;
        player.setCount(3);
        assertEquals(count, player.getCount());
    }
}