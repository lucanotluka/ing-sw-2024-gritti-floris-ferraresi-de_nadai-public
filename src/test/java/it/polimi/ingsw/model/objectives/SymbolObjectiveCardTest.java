package it.polimi.ingsw.model.objectives;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Coordinate;
import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.CornerType;
import it.polimi.ingsw.model.cards.ResourceCard;
import it.polimi.ingsw.model.cards.Symbol;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SymbolObjectiveCardTest {
    SymbolObjectiveCard symbolObjectiveCard;
    SymbolObjectiveCard symbolObjectiveCard1;

    @BeforeEach
    void setUp() {
        int id = 96;
        int points = 2;
        Symbol s1 = Symbol.PLANT;
        Symbol s2 = Symbol.PLANT;
        Symbol s3 = Symbol.PLANT;
        symbolObjectiveCard = new SymbolObjectiveCard(id, points, s1, s2, s3);

        int id1 = 100;
        int points1 = 2;
        Symbol symbol = Symbol.MANUSCRIPT;
        Symbol symbol1 = Symbol.MANUSCRIPT;
        symbolObjectiveCard1 = new SymbolObjectiveCard(id1, points1, symbol, symbol1);
    }

    @Test
    void getId(){
        Assertions.assertEquals(96, symbolObjectiveCard.getId());
        Assertions.assertEquals(100, symbolObjectiveCard1.getId());
    }

    @Test
    void getPoints(){
        Assertions.assertEquals(2, symbolObjectiveCard.getPoints());
        Assertions.assertEquals(2, symbolObjectiveCard1.getPoints());
    }

    @Test
    void checkConditionThreeSymbols(){
        int points = 2;

        Board board = new Board();
        ResourceCard card1 = new ResourceCard(1, Symbol.PLANT, 0, new Corner(CornerType.TOP_LEFT, Symbol.PLANT), new Corner(CornerType.TOP_RIGHT, Symbol.PLANT), new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY));
        ResourceCard card2 = new ResourceCard(2, Symbol.PLANT, 0, new Corner(CornerType.TOP_LEFT, Symbol.PLANT), new Corner(CornerType.TOP_RIGHT, Symbol.PLANT), new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY));
        board.cardPlacement(card1, new Coordinate(2,2));
        board.cardPlacement(card2, new Coordinate(3,3));
        Assertions.assertEquals(points, symbolObjectiveCard.checkCondition(board)*symbolObjectiveCard.getPoints());

        ResourceCard card3 = new ResourceCard(3, Symbol.PLANT, 0, new Corner(CornerType.TOP_LEFT, Symbol.PLANT), new Corner(CornerType.TOP_RIGHT, Symbol.PLANT), new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY));
        board.cardPlacement(card3, new Coordinate(6,6));
        points = points*2;
        Assertions.assertEquals(points, symbolObjectiveCard.checkCondition(board)*symbolObjectiveCard.getPoints());
    }

    @Test
    void checkConditionTwoSymbols(){
        int points = 2;

        Board board = new Board();
        int id = 7;
        Symbol symbol = Symbol.FUNGI;
        int points1 = 0;
        Corner top_left = new Corner(CornerType.TOP_LEFT, Symbol.FUNGI);
        Corner top_right = new Corner(CornerType.TOP_RIGHT, Symbol.INSECT);
        Corner bot_left = new Corner(CornerType.BOT_LEFT, Symbol.MANUSCRIPT);
        Corner bot_right = new Corner(CornerType.BOT_RIGHT, Symbol.EMPTY);
        ResourceCard resourceCard = new ResourceCard(id, symbol, points1, top_left, top_right, bot_left, bot_right);
        board.cardPlacement(resourceCard, new Coordinate(2, 2));
        Assertions.assertEquals(0, symbolObjectiveCard1.checkCondition(board)*symbolObjectiveCard1.getPoints());

        int id1 = 17;
        Symbol symbol1 = Symbol.PLANT;
        Corner top_left1 = new Corner(CornerType.TOP_LEFT, Symbol.MANUSCRIPT);
        Corner bot_left1 = new Corner(CornerType.BOT_LEFT, Symbol.PLANT);
        Corner bot_right1 = new Corner(CornerType.BOT_RIGHT, Symbol.ANIMAL);
        ResourceCard resourceCard1 = new ResourceCard(id1, symbol1, points1, top_left1, bot_left1, bot_right1);
        board.cardPlacement(resourceCard1, new Coordinate(1, 1));
        Assertions.assertEquals(points, symbolObjectiveCard1.checkCondition(board)*symbolObjectiveCard1.getPoints());
    }

    @Test
    void testEquals(){
        int id = 96;
        int points = 2;
        Symbol s1 = Symbol.PLANT;
        Symbol s2 = Symbol.PLANT;
        Symbol s3 = Symbol.PLANT;
        Assertions.assertEquals(symbolObjectiveCard, new SymbolObjectiveCard(id, points, s1, s2, s3));
        id = 100;
        s1 = Symbol.MANUSCRIPT;
        s2 = Symbol.MANUSCRIPT;
        Assertions.assertEquals(symbolObjectiveCard1, new SymbolObjectiveCard(id, points, s1, s2));
        Assertions.assertNotEquals(symbolObjectiveCard, symbolObjectiveCard1);
        Assertions.assertNotEquals(symbolObjectiveCard1, symbolObjectiveCard);
    }

}