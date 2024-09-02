package it.polimi.ingsw.listener;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    Event event;

    @BeforeEach
    void setUp(){
        EventType eventType = EventType.CHOOSE_NICKNAME;
        String nickname = "Marco";
        event = new Event(eventType, nickname);
    }
    @Test
    void getPlayers() {
        ArrayList<String> players = new ArrayList<>();
        players.add("Luca");
        players.add("Fabio");
        event.setPlayers(players);
        assertEquals(players, event.getPlayers());
    }

    @Test
    void setPlayers() {
        ArrayList<String> players = new ArrayList<>();
        players.add("Marco");
        event.setPlayers(players);
        assertEquals(players, event.getPlayers());
    }

    @Test
    void getBoard() {
        int[][][] board = new int[4][4][6];
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                for (int k = 0; k < 6; k++){
                    board[i][j][k] = k;
                }
            }
        }
        event.setBoard(board);
        assertEquals(board, event.getBoard());
    }

    @Test
    void setBoard() {
        int[][][] board = new int[4][4][6];
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                for (int k = 0; k < 6; k++){
                    board[i][j][k] = k;
                }
            }
        }
        event.setBoard(board);
        assertEquals(board, event.getBoard());
    }

    @Test
    void getN5() {
        event.setN5(5);
        assertEquals(5, event.getN5());
    }

    @Test
    void setN5() {
        event.setN5(6);
        assertEquals(6, event.getN5());
    }

    @Test
    void getN6() {
        event.setN6(5);
        assertEquals(5, event.getN6());
    }

    @Test
    void setN6() {
        event.setN6(6);
        assertEquals(6, event.getN6());
    }

    @Test
    void getTokens() {
        ArrayList<String> tokens = new ArrayList<>();
        tokens.add("RED");
        tokens.add("BLUE");
        event.setTokens(tokens);
        assertEquals(tokens, event.getTokens());
    }

    @Test
    void setTokens() {
        ArrayList<String> tokens = new ArrayList<>();
        tokens.add("RED");
        tokens.add("GREEN");
        event.setTokens(tokens);
        assertEquals(tokens, event.getTokens());
    }

    @Test
    void getN3() {
        event.setN3(5);
        assertEquals(5, event.getN3());
    }

    @Test
    void setN3() {
        event.setN3(6);
        assertEquals(6, event.getN3());
    }

    @Test
    void getN4() {
        event.setN4(5);
        assertEquals(5, event.getN4());
    }

    @Test
    void setN4() {
        event.setN4(6);
        assertEquals(6, event.getN4());
    }

    @Test
    void getNickName() {
        assertEquals("Marco", event.getNickName());
    }

    @Test
    void getParameter() {
        event.setParameter("card");
        assertEquals("card", event.getParameter());
    }

    @Test
    void setParameter() {
        event.setParameter("card");
        assertEquals("card", event.getParameter());
    }

    @Test
    void getN1() {
        event.setN1(5);
        assertEquals(5, event.getN1());
    }

    @Test
    void setN1() {
        event.setN1(6);
        assertEquals(6, event.getN1());
    }

    @Test
    void getN2() {
        event.setN2(5);
        assertEquals(5, event.getN2());
    }

    @Test
    void setN2() {
        event.setN2(6);
        assertEquals(6, event.getN2());
    }

    @Test
    void getEvent() {
        assertEquals(EventType.CHOOSE_NICKNAME, event.getEvent());
    }
}