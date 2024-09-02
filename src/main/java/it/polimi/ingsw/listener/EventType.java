package it.polimi.ingsw.listener;

/**
 * Enumeration representing all the different event types handled by the program
 *
 * @author Fabio Marco Floris, Marco Ferraresi
 */
public enum EventType {
    CHOOSE_CONNECT_OR_CREATE_LOBBY,
    SELECT_NUMBERS_PLAYERS,
    ID_MATCH,
    JOIN_LOBBY,
    PLAYER_JOINED,
    CHOOSE_NICKNAME,
    CHOOSE_TOKEN,
    CHOOSE_OBJECTIVES,
    CHOOSE_FACE_STARTER,
    CHOOSE_FRONT_OR_BACK,
    CHOOSE_PLAY_CARD,
    CHOOSE_POSITION,
    CHOOSE_DRAW_CARD,
    SEND_OBJECTIVES,
    TEXT_MESSAGE,
    PRINT_HAND,
    PRINT_BOARD,
    EXIT_GAME,
    RECONNECT_PLAYER,
    PRINT_TITLE_AND_CREDITS,
    RETURN_TO_THE_MENU,
    ERROR,
    TIMEOUT,
    PING
}