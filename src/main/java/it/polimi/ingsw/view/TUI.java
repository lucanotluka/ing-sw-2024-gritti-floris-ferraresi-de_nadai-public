package it.polimi.ingsw.view;


import it.polimi.ingsw.client.PlayerData;
import it.polimi.ingsw.jsonData.DataParser;
import it.polimi.ingsw.listener.Event;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * The TUI class implements the UserInterface interface and provides methods
 * for displaying various elements of a game via a text-based user interface.
 * It uses ANSI escape codes to add colors and formatting to the console output.
 *
 * @author Marco Ferraresi, Fabio Marco Floris, Luca Gritti
 */
public class TUI implements UserInterface{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLACK = "\u001B[90m";
    public static final String ANSI_WHITE = "\u001B[97m";

    public static final String ANSI_BACK_RED = "\u001B[101m";
    public static final String ANSI_BACK_CYAN = "\u001B[46m";//w
    public static final String ANSI_BACK_PURPLE = "\u001B[45m";//w
    public static final String ANSI_BACK_GREEN = "\u001B[102m";
    public static final String ANSI_BACK_YELLOW = "\u001B[103m";
    public static final String ANSI_BACK_WHITE = "\u001B[107m";


    public static Scanner in = new Scanner(System.in);

    /**
     * Parses a symbol string into a list of strings representing its ASCII art.
     *
     * @param symbol the symbol to parse
     * @return a list of strings representing the ASCII art of the symbol
     */
    public static List<String> parseSymbol(String symbol) {
        List<String> list = new ArrayList<>();
        switch (symbol) {
            case "FUNGI":
                list.add("  /¯¯¯\\  ");
                list.add(" /__ __\\ ");
                list.add("   └─┘   ");
                break;
            case "PLANT":
                list.add(" |\\ Λ /| ");
                list.add(" │ \\V/ │ ");
                list.add("  \\_|_/  ");
                break;
            case "INSECT":
                list.add(" │\\   /│ ");
                list.add(" \\_\\█/_/ ");
                list.add("  │/▀\\│  ");
                break;
            case "ANIMAL":
                list.add("  /\\_/\\  ");
                list.add(" (     ) ");
                list.add("  \\_Ŷ_/  ");
                break;
            case "MANUSCRIPT":
                list.add(" ()¯¯¯)  ");
                list.add("  │___│  ");
                list.add("  ()___) ");
                break;
            case "INKWELL":
                list.add("   ▄▄▄   ");
                list.add(" ┌─┴─┴─┐ ");
                list.add(" |_____| ");
                break;
                case "QUILL":
                list.add("   /U\\   ");
                list.add("   \\|/   ");
                list.add("    V    ");
                break;
            case "EMPTY":
                list.add("         ");
                list.add("         ");
                list.add("         ");
                break;
        }
        return list;
    }
    /**
     * Parses a JSON array representing conditions to play a card into a formatted string.
     *
     * @param arr the JSON array representing conditions
     * @return a formatted string representing the conditions
     */
    public static String parseCondition(JSONArray arr){
        StringBuilder condition= new StringBuilder();
        int q=0;
        for(Object obj : arr){
            for(int i=0; i<((Long)((JSONObject)obj).get("quantity")).intValue();i++, q++){
                switch (((JSONObject) obj).get("symbol").toString()){
                    case "ANIMAL":
                        condition.append(ANSI_CYAN + "A");
                        break;
                    case "FUNGI":
                        condition.append(ANSI_RED + "F");
                        break;
                    case "PLANT":
                        condition.append(ANSI_GREEN + "P");
                        break;
                    case "INSECT":
                        condition.append(ANSI_PURPLE + "I");
                        break;
                }
            }
        }
        if(q==3)
            condition = new StringBuilder(" " + condition + " ");
        else if (q==4)

            condition = new StringBuilder(condition.substring(0, 18) + " " + condition.substring(18));
        return condition.toString();
    }
    /**
     * Retrieves a list of strings representing the ASCII art of a card based on its ID and face.
     *
     * @param id the ID of the card
     * @param face the face of the card ("FRONT" or "BACK")
     * @return a list of strings representing the ASCII art of the card
     */
    public List<String> getCard(int id, String face) {
        List<String> card = new ArrayList<>();
        String cardColor;
        String line = "";
        List<String> top_l = new ArrayList<String>();
        List<String> top_r = new ArrayList<String>();
        List<String> bot_l = new ArrayList<String>();
        List<String> bot_r = new ArrayList<String>();
        JSONParser parser = new JSONParser();
        try {

            //resource card ----------------------------------------------------------

            if (id <= 40 && id >= 1) {
                JSONObject obj = (JSONObject) DataParser.getJSONFromPath("FrontResourceDeck.json").get(id - 1);
                cardColor = getColor(obj.get("symbol").toString());
                if (face.equals("FRONT")) {

                    if (obj.containsKey("top_left")) {
                        top_l = parseSymbol(((JSONObject) obj.get("top_left")).get("symbol").toString());
                        card.add(0, cardColor + "┌─────────┬");
                        card.add(1, cardColor + "│" + getColor(((JSONObject) obj.get("top_left")).get("symbol").toString()) + top_l.get(0) + cardColor + "│");
                        card.add(2, cardColor + "│" + getColor(((JSONObject) obj.get("top_left")).get("symbol").toString()) + top_l.get(1) + cardColor + "│");
                        card.add(3, cardColor + "│" + getColor(((JSONObject) obj.get("top_left")).get("symbol").toString()) + top_l.get(2) + cardColor + "│");
                        card.add(4, cardColor + "├─────────┘");
                    } else {
                        card.add(0, cardColor + "┌──────────");
                        card.add(1, cardColor + "│          ");
                        card.add(2, cardColor + "│          ");
                        card.add(3, cardColor + "│          ");
                        card.add(4, cardColor + "│          ");
                    }

                    if (((Long) obj.get("points")).intValue() == 1) {
                        card.set(0, card.get(0) + cardColor + "───┬───┬───");
                        card.set(1, card.get(1) + cardColor + "   │ 1 │   ");
                        card.set(2, card.get(2) + cardColor + "   └───┘   ");
                        card.set(3, card.get(3) + cardColor + "           ");
                        card.set(4, card.get(4) + cardColor + "           ");

                    } else {
                        card.set(0, card.get(0) + cardColor + "───────────");
                        card.set(1, card.get(1) + "           ");
                        card.set(2, card.get(2) + "           ");
                        card.set(3, card.get(3) + "           ");
                        card.set(4, card.get(4) + "           ");
                    }

                    if (obj.containsKey("top_right")) {
                        top_r = parseSymbol(((JSONObject) obj.get("top_right")).get("symbol").toString());
                        card.set(0, card.get(0) + cardColor + "┬─────────┐");
                        card.set(1, card.get(1) + cardColor + "│" + getColor(((JSONObject) obj.get("top_right")).get("symbol").toString()) + top_r.get(0) + cardColor + "│");
                        card.set(2, card.get(2) + cardColor + "│" + getColor(((JSONObject) obj.get("top_right")).get("symbol").toString()) + top_r.get(1) + cardColor + "│");
                        card.set(3, card.get(3) + cardColor + "│" + getColor(((JSONObject) obj.get("top_right")).get("symbol").toString()) + top_r.get(2) + cardColor + "│");
                        card.set(4, card.get(4) + cardColor + "└─────────┤");
                    } else {
                        card.set(0, card.get(0) + cardColor + "──────────┐");
                        card.set(1, card.get(1) + cardColor + "          │");
                        card.set(2, card.get(2) + cardColor + "          │");
                        card.set(3, card.get(3) + cardColor + "          │");
                        card.set(4, card.get(4) + cardColor + "          │");
                    }

                    //center
                    card.add(5,cardColor +"│                               │");
                    card.add(6,cardColor +"│                               │");
                    card.add(7,cardColor +"│                               │");

                    if (obj.containsKey("bot_left")) {
                        bot_l = parseSymbol(((JSONObject) obj.get("bot_left")).get("symbol").toString());
                        card.add(8, cardColor + "├─────────┐");
                        card.add(9, cardColor + "│" + getColor(((JSONObject) obj.get("bot_left")).get("symbol").toString()) + bot_l.get(0) + cardColor + "│");
                        card.add(10, cardColor + "│" + getColor(((JSONObject) obj.get("bot_left")).get("symbol").toString()) + bot_l.get(1) + cardColor + "│");
                        card.add(11, cardColor + "│" + getColor(((JSONObject) obj.get("bot_left")).get("symbol").toString()) + bot_l.get(2) + cardColor + "│");
                        card.add(12, cardColor + "└─────────┴");
                    } else {
                        card.add(8, cardColor + "│          ");
                        card.add(9, cardColor + "│          ");
                        card.add(10, cardColor + "│          ");
                        card.add(11, cardColor + "│          ");
                        card.add(12, cardColor + "└──────────");
                    }
                    //no conditions
                    card.set(8, card.get(8) + "           ");
                    card.set(9, card.get(9) + "           ");
                    card.set(10, card.get(10) + "           ");
                    card.set(11, card.get(11) + "           ");
                    card.set(12, card.get(12) + "───────────");


                    if (obj.containsKey("bot_right")) {
                        bot_r = parseSymbol(((JSONObject) obj.get("bot_right")).get("symbol").toString());
                        card.set(8, card.get(8) + cardColor + "┌─────────┤");
                        card.set(9, card.get(9) + cardColor + "│" + getColor(((JSONObject) obj.get("bot_right")).get("symbol").toString()) + bot_r.get(0) + cardColor + "│");
                        card.set(10, card.get(10) + cardColor + "│" + getColor(((JSONObject) obj.get("bot_right")).get("symbol").toString()) + bot_r.get(1) + cardColor + "│");
                        card.set(11, card.get(11) + cardColor + "│" + getColor(((JSONObject) obj.get("bot_right")).get("symbol").toString()) + bot_r.get(2) + cardColor + "│");
                        card.set(12, card.get(12) + cardColor + "┴─────────┘");
                    } else {
                        card.set(8, card.get(8) + cardColor + "          │");
                        card.set(9, card.get(9) + cardColor + "          │");
                        card.set(10, card.get(10) + cardColor + "          │");
                        card.set(11, card.get(11) + cardColor + "          │");
                        card.set(12, card.get(12) + cardColor + "──────────┘");
                    }
                }
                else {

                    card.add(0,cardColor+"┌─────────┬───────────┬─────────┐");
                    card.add(1,cardColor +"│         │           │         │");
                    card.add(2,cardColor +"│         │           │         │");
                    card.add(3,cardColor +"│         │           │         │");
                    card.add(4,cardColor +"├─────────┘           └─────────┤");
                    card.add(5,cardColor +"│           ");
                    card.set(5, card.get(5) + parseSymbol(obj.get("symbol").toString()).get(0));
                    card.set(5,card.get(5)+"           │");
                    card.add(6,cardColor +"│           ");
                    card.set(6, card.get(6) + parseSymbol(obj.get("symbol").toString()).get(1));
                    card.set(6,card.get(6)+"           │");
                    card.add(7,cardColor +"│           ");
                    card.set(7, card.get(7) + parseSymbol(obj.get("symbol").toString()).get(2));
                    card.set(7,card.get(7)+"           │");
                    card.add(8,cardColor +"├─────────┐           ┌─────────┤");
                    card.add(9,cardColor +"│         │           │         │");
                    card.add(10,cardColor +"│         │           │         │");
                    card.add(11,cardColor +"│         │           │         │");
                    card.add(12,cardColor +"└─────────┴───────────┴─────────┘");

                }
            }

            // gold card --------------------------------------------------

            else if (id <= 80 && id > 40) {
                JSONObject obj = (JSONObject) DataParser.getJSONFromPath("FrontGoldDeck.json").get(id - 41);
                cardColor = getColor(obj.get("symbol").toString());
                if (face.equals("FRONT")) {
                    if (obj.containsKey("top_left")) {
                        top_l = parseSymbol(((JSONObject) obj.get("top_left")).get("symbol").toString());
                        card.add(0, cardColor + "┌─────────┬");
                        card.add(1, cardColor + "│" + getColor(((JSONObject) obj.get("top_left")).get("symbol").toString()) + top_l.get(0) + ANSI_YELLOW + "│");
                        card.add(2, cardColor + "│" + getColor(((JSONObject) obj.get("top_left")).get("symbol").toString()) + top_l.get(1) + ANSI_YELLOW + "│");
                        card.add(3, cardColor + "│" + getColor(((JSONObject) obj.get("top_left")).get("symbol").toString()) + top_l.get(2) + ANSI_YELLOW + "│");
                        card.add(4, cardColor + "├"+ANSI_YELLOW+"─────────┘");
                    } else {
                        card.add(0, cardColor + "┌──────────");
                        card.add(1, cardColor + "│          ");
                        card.add(2, cardColor + "│          ");
                        card.add(3, cardColor + "│          ");
                        card.add(4, cardColor + "│          ");
                    }

                    //points and pointtype
                    card.set(0, card.get(0) + cardColor + "───┬───┬───");
                    if(obj.get("pointsType").toString().equals("NULL")){
                        card.set(1, card.get(1) + ANSI_YELLOW + "   │ "+((Long) obj.get("points")).intValue()+" │   ");
                    } else {
                        switch (obj.get("pointsType").toString()) {
                            case "QUILL":
                                card.set(1, card.get(1) + ANSI_YELLOW + "   │"+((Long) obj.get("points")).intValue()+"│Q│   ");
                                break;
                            case "MANUSCRIPT":
                                card.set(1, card.get(1) + ANSI_YELLOW + "   │"+((Long) obj.get("points")).intValue()+"│M│   ");
                                break;
                            case "CORNERS":
                                card.set(1, card.get(1) + ANSI_YELLOW + "   │"+((Long) obj.get("points")).intValue()+"│C│   ");
                                break;
                            case "INKWELL":
                                card.set(1, card.get(1) + ANSI_YELLOW + "   │"+((Long) obj.get("points")).intValue()+"│I│   ");
                                break;
                        }
                    }
                        card.set(2, card.get(2) + ANSI_YELLOW + "   └───┘   ");
                        card.set(3, card.get(3) + "           ");
                        card.set(4, card.get(4) + "           ");

                    if (obj.containsKey("top_right")) {
                        top_r = parseSymbol(((JSONObject) obj.get("top_right")).get("symbol").toString());
                        card.set(0, card.get(0) + cardColor + "┬─────────┐");
                        card.set(1, card.get(1) + ANSI_YELLOW + "│" + getColor(((JSONObject) obj.get("top_right")).get("symbol").toString()) + top_r.get(0) + cardColor + "│");
                        card.set(2, card.get(2) + ANSI_YELLOW + "│" + getColor(((JSONObject) obj.get("top_right")).get("symbol").toString()) + top_r.get(1) + cardColor + "│");
                        card.set(3, card.get(3) + ANSI_YELLOW + "│" + getColor(((JSONObject) obj.get("top_right")).get("symbol").toString()) + top_r.get(2) + cardColor + "│");
                        card.set(4, card.get(4) + ANSI_YELLOW + "└─────────"+cardColor+"┤");
                    } else {
                        card.set(0, card.get(0) + cardColor + "──────────┐");
                        card.set(1, card.get(1) + cardColor + "          │");
                        card.set(2, card.get(2) + cardColor + "          │");
                        card.set(3, card.get(3) + cardColor + "          │");
                        card.set(4, card.get(4) + cardColor + "          │");
                    }

                    //center
                    card.add(5,cardColor +"│                               │");
                    card.add(6,cardColor +"│                               │");
                    card.add(7,cardColor +"│                               │");

                    if (obj.containsKey("bot_left")) {
                        bot_l = parseSymbol(((JSONObject) obj.get("bot_left")).get("symbol").toString());
                        card.add(8, cardColor + "├"+ANSI_YELLOW+"─────────┐");
                        card.add(9, cardColor + "│" + getColor(((JSONObject) obj.get("bot_left")).get("symbol").toString()) + bot_l.get(0) + ANSI_YELLOW + "│");
                        card.add(10, cardColor + "│" + getColor(((JSONObject) obj.get("bot_left")).get("symbol").toString()) + bot_l.get(1) + ANSI_YELLOW + "│");
                        card.add(11, cardColor + "│" + getColor(((JSONObject) obj.get("bot_left")).get("symbol").toString()) + bot_l.get(2) + ANSI_YELLOW + "│");
                        card.add(12, cardColor + "└─────────┴");
                    } else {
                        card.add(8, cardColor + "│          ");
                        card.add(9, cardColor + "│          ");
                        card.add(10, cardColor + "│          ");
                        card.add(11, cardColor + "│          ");
                        card.add(12, cardColor + "└──────────");
                    }
                    //conditions

                    card.set(8, card.get(8) + cardColor + "           ");
                    card.set(9, card.get(9) + cardColor + "           ");
                    card.set(10, card.get(10)+ANSI_YELLOW + "  ┌─────┐  ");
                    card.set(11, card.get(11) + ANSI_YELLOW + "  │"+parseCondition((JSONArray) obj.get("condition"))+ ANSI_YELLOW +"│  ");
                    card.set(12, card.get(12) + cardColor + "──┴─────┴──");


                    if (obj.containsKey("bot_right")) {
                        bot_r = parseSymbol(((JSONObject) obj.get("bot_right")).get("symbol").toString());
                        card.set(8, card.get(8) + ANSI_YELLOW + "┌─────────"+cardColor+"┤");
                        card.set(9, card.get(9) + ANSI_YELLOW + "│" + getColor(((JSONObject) obj.get("bot_right")).get("symbol").toString()) + bot_r.get(0) + cardColor + "│");
                        card.set(10, card.get(10) + ANSI_YELLOW + "│" + getColor(((JSONObject) obj.get("bot_right")).get("symbol").toString()) + bot_r.get(1) + cardColor + "│");
                        card.set(11, card.get(11) + ANSI_YELLOW + "│" + getColor(((JSONObject) obj.get("bot_right")).get("symbol").toString()) + bot_r.get(2) + cardColor + "│");
                        card.set(12, card.get(12) + cardColor + "┴─────────┘");
                    } else {
                        card.set(8, card.get(8) + cardColor + "          │");
                        card.set(9, card.get(9) + cardColor + "          │");
                        card.set(10, card.get(10) + cardColor + "          │");
                        card.set(11, card.get(11) + cardColor + "          │");
                        card.set(12, card.get(12) + cardColor + "──────────┘");
                    }

                } else {

                    card.add(0,cardColor+"┌─────────┬───────────┬─────────┐");
                    card.add(1,cardColor +"│         "+ANSI_YELLOW+"│           │"+cardColor+"         │");
                    card.add(2,cardColor +"│         "+ANSI_YELLOW+"│           │"+cardColor+"         │");
                    card.add(3,cardColor +"│         "+ANSI_YELLOW+"│           │"+cardColor+"         │");
                    card.add(4,cardColor +"├"+ANSI_YELLOW+"─────────┘           └─────────"+cardColor+"┤");
                    card.add(5,cardColor +"│           ");
                    card.set(5, card.get(5) + parseSymbol(obj.get("symbol").toString()).get(0));
                    card.set(5,card.get(5)+"           │");
                    card.add(6,cardColor +"│           ");
                    card.set(6, card.get(6) + parseSymbol(obj.get("symbol").toString()).get(1));
                    card.set(6,card.get(6)+"           │");
                    card.add(7,cardColor +"│           ");
                    card.set(7, card.get(7) + parseSymbol(obj.get("symbol").toString()).get(2));
                    card.set(7,card.get(7)+"           │");
                    card.add(8,cardColor +"├"+ANSI_YELLOW+"─────────┐           ┌─────────"+cardColor+"┤");
                    card.add(9,cardColor +"│         "+ANSI_YELLOW+"│           │"+cardColor+"         │");
                    card.add(10,cardColor +"│         "+ANSI_YELLOW+"│           │"+cardColor+"         │");
                    card.add(11,cardColor +"│         "+ANSI_YELLOW+"│           │"+cardColor+"         │");
                    card.add(12,cardColor +"└─────────┴───────────┴─────────┘");

                }

            }

            // starter card --------------------------------------------------

            else if (id >= 81 && id < 87) {
                cardColor = ANSI_YELLOW;
                if (face.equals("FRONT")) {
                    JSONObject obj = (JSONObject) DataParser.getJSONFromPath("FrontStarterDeck.json").get(id - 81);


                    if (obj.containsKey("top_left")) {
                        top_l = parseSymbol(((JSONObject) obj.get("top_left")).get("symbol").toString());
                        card.add(0, cardColor + "┌─────────┬");
                        card.add(1, cardColor + "│" + getColor(((JSONObject) obj.get("top_left")).get("symbol").toString()) + top_l.get(0) + cardColor + "│");
                        card.add(2, cardColor + "│" + getColor(((JSONObject) obj.get("top_left")).get("symbol").toString()) + top_l.get(1) + cardColor + "│");
                        card.add(3, cardColor + "│" + getColor(((JSONObject) obj.get("top_left")).get("symbol").toString()) + top_l.get(2) + cardColor + "│");
                        card.add(4, cardColor + "├─────────┘");
                    } else {
                        card.add(0, cardColor + "┌──────────");
                        card.add(1, cardColor + "│          ");
                        card.add(2, cardColor + "│          ");
                        card.add(3, cardColor + "│          ");
                        card.add(4, cardColor + "│          ");
                    }

                    card.set(0, card.get(0) + cardColor + "───────────");
                    card.set(1, card.get(1) + "           ");
                    card.set(2, card.get(2) + "           ");
                    card.set(3, card.get(3) + "           ");
                    card.set(4, card.get(4) + "           ");

                    if (obj.containsKey("top_right")) {
                        top_r = parseSymbol(((JSONObject) obj.get("top_right")).get("symbol").toString());
                        card.set(0, card.get(0) + cardColor + "┬─────────┐");
                        card.set(1, card.get(1) + cardColor + "│" + getColor(((JSONObject) obj.get("top_right")).get("symbol").toString()) + top_r.get(0) + cardColor + "│");
                        card.set(2, card.get(2) + cardColor + "│" + getColor(((JSONObject) obj.get("top_right")).get("symbol").toString()) + top_r.get(1) + cardColor + "│");
                        card.set(3, card.get(3) + cardColor + "│" + getColor(((JSONObject) obj.get("top_right")).get("symbol").toString()) + top_r.get(2) + cardColor + "│");
                        card.set(4, card.get(4) + cardColor + "└─────────┤");
                    } else {
                        card.set(0, card.get(0) + cardColor + "──────────┐");
                        card.set(1, card.get(1) + cardColor + "          │");
                        card.set(2, card.get(2) + cardColor + "          │");
                        card.set(3, card.get(3) + cardColor + "          │");
                        card.set(4, card.get(4) + cardColor + "          │");
                    }
                    //center
                    JSONArray symbArray = (JSONArray) obj.get("symbols");
                    String s1;
                    String s2;
                    String s3;
                    switch (symbArray.size()){
                        case 1:
                            s1=symbArray.get(0).toString();
                            card.add(5,cardColor +"│           "+getColor(s1)+parseSymbol(s1).get(0)+cardColor+"           │");
                            card.add(6,cardColor +"│           "+getColor(s1)+parseSymbol(s1).get(1)+cardColor+"           │");
                            card.add(7,cardColor +"│           "+getColor(s1)+parseSymbol(s1).get(2)+cardColor+"           │");
                            break;
                        case 2:
                            s1=symbArray.get(0).toString();
                            s2=symbArray.get(1).toString();
                            card.add(5,cardColor +"│      "+getColor(s1)+parseSymbol(s1).get(0)+" "+getColor(s2)+parseSymbol(s2).get(0)+cardColor+"      │");
                            card.add(6,cardColor +"│      "+getColor(s1)+parseSymbol(s1).get(1)+" "+getColor(s2)+parseSymbol(s2).get(1)+cardColor+"      │");
                            card.add(7,cardColor +"│      "+getColor(s1)+parseSymbol(s1).get(2)+" "+getColor(s2)+parseSymbol(s2).get(2)+cardColor+"      │");
                            break;
                        case 3:
                            s1=symbArray.get(0).toString();
                            s2=symbArray.get(1).toString();
                            s3=symbArray.get(2).toString();
                            card.add(5,cardColor +"│  "+getColor(s1)+parseSymbol(s1).get(0)+getColor(s2)+parseSymbol(s2).get(0)+getColor(s3)+parseSymbol(s3).get(0)+cardColor+"  │");
                            card.add(6,cardColor +"│  "+getColor(s1)+parseSymbol(s1).get(1)+getColor(s2)+parseSymbol(s2).get(1)+getColor(s3)+parseSymbol(s3).get(1)+cardColor+"  │");
                            card.add(7,cardColor +"│  "+getColor(s1)+parseSymbol(s1).get(2)+getColor(s2)+parseSymbol(s2).get(2)+getColor(s3)+parseSymbol(s3).get(2)+cardColor+"  │");
                            break;
                    }

                    if (obj.containsKey("bot_left")) {
                        bot_l = parseSymbol(((JSONObject) obj.get("bot_left")).get("symbol").toString());
                        card.add(8, cardColor + "├─────────┐");
                        card.add(9, cardColor + "│" + getColor(((JSONObject) obj.get("bot_left")).get("symbol").toString()) + bot_l.get(0) + cardColor + "│");
                        card.add(10, cardColor + "│" + getColor(((JSONObject) obj.get("bot_left")).get("symbol").toString()) + bot_l.get(1) + cardColor + "│");
                        card.add(11, cardColor + "│" + getColor(((JSONObject) obj.get("bot_left")).get("symbol").toString()) + bot_l.get(2) + cardColor + "│");
                        card.add(12, cardColor + "└─────────┴");
                    } else {
                        card.add(8, cardColor + "│          ");
                        card.add(9, cardColor + "│          ");
                        card.add(10, cardColor + "│          ");
                        card.add(11, cardColor + "│          ");
                        card.add(12, cardColor + "└──────────");
                    }
                    //no conditions
                    card.set(8, card.get(8) + "           ");
                    card.set(9, card.get(9) + "           ");
                    card.set(10, card.get(10) + "           ");
                    card.set(11, card.get(11) + "           ");
                    card.set(12, card.get(12) + "───────────");


                    if (obj.containsKey("bot_right")) {
                        bot_r = parseSymbol(((JSONObject) obj.get("bot_right")).get("symbol").toString());
                        card.set(8, card.get(8) + cardColor + "┌─────────┤");
                        card.set(9, card.get(9) + cardColor + "│" + getColor(((JSONObject) obj.get("bot_right")).get("symbol").toString()) + bot_r.get(0) + cardColor + "│");
                        card.set(10, card.get(10) + cardColor + "│" + getColor(((JSONObject) obj.get("bot_right")).get("symbol").toString()) + bot_r.get(1) + cardColor + "│");
                        card.set(11, card.get(11) + cardColor + "│" + getColor(((JSONObject) obj.get("bot_right")).get("symbol").toString()) + bot_r.get(2) + cardColor + "│");
                        card.set(12, card.get(12) + cardColor + "┴─────────┘");
                    } else {
                        card.set(8, card.get(8) + cardColor + "          │");
                        card.set(9, card.get(9) + cardColor + "          │");
                        card.set(10, card.get(10) + cardColor + "          │");
                        card.set(11, card.get(11) + cardColor + "          │");
                        card.set(12, card.get(12) + cardColor + "──────────┘");
                    }

                }
                else {
                    JSONObject obj = (JSONObject) DataParser.getJSONFromPath("BackStarterDeck.json").get(id - 81);

                    top_l = parseSymbol(((JSONObject) obj.get("top_left")).get("symbol").toString());
                    card.add(0, cardColor + "┌─────────┬");
                    card.add(1, cardColor + "│" + getColor(((JSONObject) obj.get("top_left")).get("symbol").toString()) + top_l.get(0) + cardColor + "│");
                    card.add(2, cardColor + "│" + getColor(((JSONObject) obj.get("top_left")).get("symbol").toString()) + top_l.get(1) + cardColor + "│");
                    card.add(3, cardColor + "│" + getColor(((JSONObject) obj.get("top_left")).get("symbol").toString()) + top_l.get(2) + cardColor + "│");
                    card.add(4, cardColor + "├─────────┘");


                    card.set(0, card.get(0) + cardColor + "───────────");
                    card.set(1, card.get(1) + "           ");
                    card.set(2, card.get(2) + "           ");
                    card.set(3, card.get(3) + "           ");
                    card.set(4, card.get(4) + "           ");

                    top_r = parseSymbol(((JSONObject) obj.get("top_right")).get("symbol").toString());
                    card.set(0, card.get(0) + cardColor + "┬─────────┐");
                    card.set(1, card.get(1) + cardColor + "│" + getColor(((JSONObject) obj.get("top_right")).get("symbol").toString()) + top_r.get(0) + cardColor + "│");
                    card.set(2, card.get(2) + cardColor + "│" + getColor(((JSONObject) obj.get("top_right")).get("symbol").toString()) + top_r.get(1) + cardColor + "│");
                    card.set(3, card.get(3) + cardColor + "│" + getColor(((JSONObject) obj.get("top_right")).get("symbol").toString()) + top_r.get(2) + cardColor + "│");
                    card.set(4, card.get(4) + cardColor + "└─────────┤");

                    //center

                    card.add(5,cardColor +"│              (¤)              │");
                    card.add(6,cardColor +"│           (¤)   (¤)           │");
                    card.add(7,cardColor +"│              (¤)              │");

                    bot_l = parseSymbol(((JSONObject) obj.get("bot_left")).get("symbol").toString());
                    card.add(8, cardColor + "├─────────┐");
                    card.add(9, cardColor + "│" + getColor(((JSONObject) obj.get("bot_left")).get("symbol").toString()) + bot_l.get(0) + cardColor + "│");
                    card.add(10, cardColor + "│" + getColor(((JSONObject) obj.get("bot_left")).get("symbol").toString()) + bot_l.get(1) + cardColor + "│");
                    card.add(11, cardColor + "│" + getColor(((JSONObject) obj.get("bot_left")).get("symbol").toString()) + bot_l.get(2) + cardColor + "│");
                    card.add(12, cardColor + "└─────────┴");

                    //no conditions
                    card.set(8, card.get(8) + "           ");
                    card.set(9, card.get(9) + "           ");
                    card.set(10, card.get(10) + "           ");
                    card.set(11, card.get(11) + "           ");
                    card.set(12, card.get(12) + "───────────");

                    bot_r = parseSymbol(((JSONObject) obj.get("bot_right")).get("symbol").toString());
                    card.set(8, card.get(8) + cardColor + "┌─────────┤");
                    card.set(9, card.get(9) + cardColor + "│" + getColor(((JSONObject) obj.get("bot_right")).get("symbol").toString()) + bot_r.get(0) + cardColor + "│");
                    card.set(10, card.get(10) + cardColor + "│" + getColor(((JSONObject) obj.get("bot_right")).get("symbol").toString()) + bot_r.get(1) + cardColor + "│");
                    card.set(11, card.get(11) + cardColor + "│" + getColor(((JSONObject) obj.get("bot_right")).get("symbol").toString()) + bot_r.get(2) + cardColor + "│");
                    card.set(12, card.get(12) + cardColor + "┴─────────┘");


                }
            }

            //objective card  --------------------------------------------------

            else {
                JSONObject obj = (JSONObject) DataParser.getJSONFromPath("FrontObjectiveDeck.json").get(id - 87);
                cardColor = ANSI_YELLOW;
                //points
                card.add(0,cardColor +"┌─────────────┬───┬─────────────┐");
                card.add(1,cardColor +"│             │ "+((Long) obj.get("points")).intValue()+" │             │");
                card.add(2,cardColor +"│             └───┘             │");

                if(obj.get("type").toString().equals("SYMBOL_OBJECTIVE")){
                    card.add(3,cardColor+"│                               │");
                    if(obj.containsKey("symbol3")){
                        String s3=obj.get("symbol3").toString();
                        card.add(4,cardColor +"│           "+getColor(s3)+parseSymbol(s3).get(0)+cardColor +"           │");
                        card.add(5,cardColor +"│           "+getColor(s3)+parseSymbol(s3).get(1)+cardColor +"           │");
                        card.add(6,cardColor +"│           "+getColor(s3)+parseSymbol(s3).get(2)+cardColor +"           │");
                    }
                    else {
                        card.add(4,cardColor +"│                               │");
                        card.add(5,cardColor +"│                               │");
                        card.add(6,cardColor +"│                               │");
                    }
                    card.add(7,cardColor +"│                               │");
                    String s1 =obj.get("symbol1").toString();
                    String s2 =obj.get("symbol2").toString();
                    card.add(8,cardColor +"│      "+getColor(s1)+parseSymbol(s1).get(0)+" "+getColor(s2)+parseSymbol(s2).get(0)+cardColor+"      │");
                    card.add(9,cardColor +"│      "+getColor(s1)+parseSymbol(s1).get(1)+" "+getColor(s2)+parseSymbol(s2).get(1)+cardColor+"      │");
                    card.add(10,cardColor +"│      "+getColor(s1)+parseSymbol(s1).get(2)+" "+getColor(s2)+parseSymbol(s2).get(2)+cardColor+"      │");
                    card.add(11,cardColor+"│                               │");
                }
                else {
                    JSONObject row1= (JSONObject) ((JSONArray) obj.get("pattern")).get(0);//x=0
                    JSONObject row2= (JSONObject) ((JSONArray) obj.get("pattern")).get(1);//x=-1
                    JSONObject row3= (JSONObject) ((JSONArray) obj.get("pattern")).get(2);//x=-2
                    card.add(3,cardColor+"│           ");
                    card.add(4,cardColor+"│           ");
                    card.add(5,cardColor+"│           ");
                    card.add(6,cardColor+"│           ");
                    card.add(7,cardColor+"│           ");
                    card.add(8,cardColor+"│           ");
                    card.add(9,cardColor+"│           ");
                    card.add(10,cardColor+"│           ");
                    card.add(11,cardColor+"│           ");

                    if(((Long) row3.get("Y")).intValue()-((Long) row2.get("Y")).intValue()==-1){
                        card.set(4,cardColor+"│"+getColor(row3.get("symbol").toString())+"  ┌───────┐");
                        card.set(5,cardColor+"│"+getColor(row3.get("symbol").toString())+"  │       │");
                        card.set(6,cardColor+"│"+getColor(row3.get("symbol").toString())+"  └───────┘");
                    }
                    if(((Long) row1.get("Y")).intValue()-((Long) row2.get("Y")).intValue()==-1){
                        card.set(8,cardColor+"│"+getColor(row1.get("symbol").toString())+"  ┌───────┐");
                        card.set(9,cardColor+"│"+getColor(row1.get("symbol").toString())+"  │       │");
                        card.set(10,cardColor+"│"+getColor(row1.get("symbol").toString())+"  └───────┘");
                    }

                    card.set(6,card.get(6)+getColor(row2.get("symbol").toString())+"┌───────┐");
                    card.set(7,card.get(7)+getColor(row2.get("symbol").toString())+"│       │");
                    card.set(8,card.get(8)+getColor(row2.get("symbol").toString())+"└───────┘");

                    if(((Long) row1.get("Y")).intValue()-((Long) row2.get("Y")).intValue()==0){
                        card.set(9,card.get(9)+getColor(row1.get("symbol").toString())+"┌───────┐");
                        card.set(10,card.get(10)+getColor(row1.get("symbol").toString())+"│       │");
                        card.set(11,card.get(11)+getColor(row1.get("symbol").toString())+"└───────┘");
                    } else{
                        card.set(9,card.get(9)+"         ");
                        card.set(10,card.get(10)+"         ");
                        card.set(11,card.get(11)+"         ");
                    }
                    if(((Long) row3.get("Y")).intValue()-((Long) row2.get("Y")).intValue()==0){
                        card.set(3,card.get(3)+getColor(row3.get("symbol").toString())+"┌───────┐");
                        card.set(4,card.get(4)+getColor(row3.get("symbol").toString())+"│       │");
                        card.set(5,card.get(5)+getColor(row3.get("symbol").toString())+"└───────┘");
                    } else{
                        card.set(3,card.get(3)+"         ");
                        card.set(4,card.get(4)+"         ");
                        card.set(5,card.get(5)+"         ");
                    }

                    card.set(3, card.get(3)+cardColor+"           │");
                    card.set(7, card.get(7)+cardColor+"           │");
                    card.set(11, card.get(11)+cardColor+"           │");

                    if(((Long) row3.get("Y")).intValue()-((Long) row2.get("Y")).intValue()==1){
                        card.set(4,card.get(4)+getColor(row3.get("symbol").toString())+"┌───────┐  "+cardColor+"│");
                        card.set(5,card.get(5)+getColor(row3.get("symbol").toString())+"│       │  "+cardColor+"│");
                        card.set(6,card.get(6)+getColor(row3.get("symbol").toString())+"└───────┘  "+cardColor+"│");
                    } else {
                        card.set(4, card.get(4)+cardColor+"           │");
                        card.set(5, card.get(5)+cardColor+"           │");
                        card.set(6, card.get(6)+cardColor+"           │");
                    }

                    if(((Long) row1.get("Y")).intValue()-((Long) row2.get("Y")).intValue()==1){
                        card.set(8,card.get(8)+getColor(row1.get("symbol").toString())+"┌───────┐  "+cardColor+"│");
                        card.set(9,card.get(9)+getColor(row1.get("symbol").toString())+"│       │  "+cardColor+"│");
                        card.set(10,card.get(10)+getColor(row1.get("symbol").toString())+"└───────┘  "+cardColor+"│");
                    }
                    else {
                        card.set(8, card.get(8)+cardColor+"           │");
                        card.set(9, card.get(9)+cardColor+"           │");
                        card.set(10, card.get(10)+cardColor+"           │");
                    }

                }
                card.add(12,cardColor +"└───────────────────────────────┘");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } //catch (ParseException e) {
        catch (ParseException e) {
            throw new RuntimeException(e);
        }


        return card;
    }
    /**
     * Returns the ANSI color code for a given symbol.
     *
     * @param symbol the symbol to get the color for
     * @return the ANSI color code for the symbol
     */
    public static String getColor(String symbol){
        switch (symbol){
            case "FUNGI":
                return ANSI_RED;
            case "ANIMAL":
                return ANSI_CYAN;
            case "INSECT":
                return ANSI_PURPLE;
            case "PLANT":
                return ANSI_GREEN;
            case "INKWELL", "QUILL", "EMPTY", "MANUSCRIPT":
                return ANSI_YELLOW;

            default:
                return ANSI_RESET;
        }
    }
    /**
     * Returns the ANSI background color code based on the card ID.
     *
     * @param id the ID of the card
     * @return the ANSI background color code for the card ID
     */
    public static String getBackgroundById(int id){
        if(1<=id && id<=10){
            return ANSI_BLACK+ANSI_BACK_RED;
        } else if (11<=id && id<=20) {
            return ANSI_BLACK+ANSI_BACK_GREEN;
        } else if (21<=id && id<=30) {
            return ANSI_WHITE+ANSI_BACK_CYAN;
        } else if (31<=id && id<=40) {
            return ANSI_WHITE+ANSI_BACK_PURPLE;
        } else if (41<=id && id<=50) {
            return ANSI_BLACK+ANSI_BACK_RED;
        } else if (51<=id && id<=60) {
            return ANSI_BLACK+ANSI_BACK_GREEN;
        } else if (61<=id && id<=70) {
            return ANSI_WHITE+ANSI_BACK_CYAN;
        } else if (71<=id && id<=80) {
            return ANSI_WHITE+ANSI_BACK_PURPLE;
        } else {
            return ANSI_BLACK+ANSI_BACK_YELLOW;
        }
    }
    /**
     * Prints the ASCII art of a card based on its ID and face.
     *
     * @param id the ID of the card
     * @param face the face of the card ("FRONT" or "BACK")
     */
    public void printCard(int id, String face){
        for (String riga : getCard(id, face)) {
            System.out.println(riga);
            try {
                Thread.sleep(45);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println(ANSI_RESET);
    }
    /**
     * Prints the title and credits of the game.
     */
    @Override
    public void printTitleCredits() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        try {

            Thread.sleep(500);
            System.out.println("\n" +
                               "▄█▄    ████▄ ██▄   ▄███▄       ▄         ▄   ██      ▄▄▄▄▀   ▄   █▄▄▄▄ ██   █     ▄█    ▄▄▄▄▄   ");
            Thread.sleep(100);
            System.out.println("█▀ ▀▄  █   █ █  █  █▀   ▀  ▀▄   █         █  █ █  ▀▀▀ █       █  █  ▄▀ █ █  █     ██   █     ▀▄ ");
            Thread.sleep(100);
            System.out.println("█   ▀  █   █ █   █ ██▄▄      █ ▀      ██   █ █▄▄█     █    █   █ █▀▀▌  █▄▄█ █     ██ ▄  ▀▀▀▀▄   ");
            Thread.sleep(100);
            System.out.println("█▄  ▄▀ ▀████ █  █  █▄   ▄▀  ▄ █       █ █  █ █  █    █     █   █ █  █  █  █ ███▄  ▐█  ▀▄▄▄▄▀    ");
            Thread.sleep(100);
            System.out.println("▀███▀        ███▀  ▀███▀   █   ▀▄     █  █ █    █   ▀      █▄ ▄█   █      █     ▀  ▐            ");
            Thread.sleep(100);
            System.out.println("                            ▀         █   ██   █            ▀▀▀   ▀      █                      ");
            Thread.sleep(100);
            System.out.println("                                              ▀                         ▀                       ");
            Thread.sleep(500);

            System.out.println("A Game of Cranio Creations, developed by");
            Thread.sleep(300);
            System.out.println(ANSI_PURPLE+" Luca Gritti");
            Thread.sleep(150);
            System.out.println(ANSI_YELLOW+" Fabio Marco Floris");
            Thread.sleep(150);
            System.out.println(ANSI_RED+" Marco Ferraresi");
            Thread.sleep(150);
            System.out.println(ANSI_CYAN+ " Angelo De Nadai\n\n"+ANSI_RESET);
            Thread.sleep(600);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }



    }
    /**
     * Prints the ASCII art of three cards. Prints the FRONT side.
     *
     * @param id1 the ID of the first card
     * @param id2 the ID of the second card
     * @param id3 the ID of the third card
     */
    public void printCards(int id1, int id2, int id3){
        List<String> card1 = getCard(id1, "FRONT");
        List<String> card2 = getCard(id2, "FRONT");
        List<String> card3 = getCard(id3, "FRONT");
        for (int i = 0; i < card1.size(); i++) {
            System.out.println(card1.get(i) +"          " + card2.get(i) + "          " + card3.get(i));
            try {
                Thread.sleep(45);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(ANSI_RESET);
    }
    /**
     * Prints the ASCII art of two cards. Prints the FRONT side.
     *
     * @param id1 the ID of the first card
     * @param id2 the ID of the second card
     */
    public void printCards(int id1, int id2){
        List<String> card1 = getCard(id1, "FRONT");
        List<String> card2 = getCard(id2, "FRONT");
        for (int i = 0; i < card1.size(); i++) {
            System.out.println(card1.get(i) +"              " + card2.get(i));
            try {
                Thread.sleep(45);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(ANSI_RESET);
    }
    /**
     * Prints the ASCII art of a card's front and back side.
     *
     * @param id the ID of the card
     */
    public void printCard(int id){
        List<String> front = getCard(id, "FRONT");
        List<String> back = getCard(id, "BACK");
        System.out.println("\n FRONT:                                     BACK:\n");
        for(int i = 0; i<front.size(); i++){
            System.out.println(front.get(i)  + "          " + back.get(i));
        }
        System.out.println(ANSI_RESET);
    }
    /**
     * Prints the game board.
     *
     * @param event the event containing the game board data
     */
    @Override
    public void printBoard(Event event){
        int[][][] board = event.getBoard();
        int count;
        int j;

        ArrayList<Integer> notPrintRow = new ArrayList<>();
        ArrayList<Integer> notPrintColumns = new ArrayList<>();

        printText(event.getParameter());

        for (int i = 0; i < board.length; i++) {
            count = 0;
            for (j = 0; j < board[i].length; j++) {
                if (board[i][j][0] == 0)
                    count++;
            }
            if (count == j) {
                notPrintRow.add(i);
            }
        }

        int numRow;
        for (j = 0; j < board[0].length; j++) {
            count = 0;
            numRow = 0;
            for (int i = 0; i < board.length; i++) {
                if (notPrintRow.contains(i))
                    continue;
                numRow++;
                if (board[i][j][0] == 0)
                    count++;
            }
            if (count == numRow)
                notPrintColumns.add(j);
        }

        System.out.println();
        for (int i = 0; i < board.length; i++){
            if (notPrintRow.contains(i))
                continue;

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            for (int k = 0; k < board[i].length; k++){
                if (notPrintColumns.contains(k))
                    continue;
                if(board[i][k][0] >= 1 && board[i][k][0] <= 86){
                    if(board[i][k][0] < 10)
                        System.out.print(getBackgroundById(board[i][k][0])+"  "+board[i][k][0]+" "+ANSI_RESET);
                    else
                        System.out.print(getBackgroundById(board[i][k][0])+" "+board[i][k][0]+" "+ANSI_RESET);
                } else if (board[i][k][0] < 0) {
                    if(-board[i][k][0] < 10)
                        System.out.print(ANSI_BLACK+ANSI_BACK_WHITE+"  "+(-board[i][k][0])+" "+ANSI_RESET);
                    else
                        System.out.print(ANSI_BLACK+ANSI_BACK_WHITE+" "+(-board[i][k][0])+" "+ANSI_RESET);
                } else
                    System.out.print(ANSI_RESET+"    ");
            }
            System.out.println();
        }
        System.out.println();
    }
    /**
     * Prints a text string to the console.
     *
     * @param text the text to print
     */
    @Override
    public void printText(String text){
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(text);
    }
    /**
     * Reads a line of text from the console.
     *
     * @return the text read from the console
     */
    public String readText(){
        in.reset();
        return in.nextLine();
    }
    /**
     * Prints the player's hand and objectives.
     *
     * @param event the event containing the player's hand and objectives
     */
    @Override
    public void printHand(Event event){

        // Thread Sleep for Relaxing the View
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(PlayerData.getPersonalObj() != -1){
            printText("Your objective and the Playfield objective card\n");
            printCards(PlayerData.getPersonalObj(), PlayerData.getPlayfieldObj1(), PlayerData.getPlayfieldObj2());
        }
        // print hand
        printText(event.getParameter());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        printCards(event.getN1(), event.getN2(), event.getN3());
    }
    /**
     * Allows the player to choose a nickname.
     *
     * @param event the event containing the prompt for choosing a nickname
     */
    @Override
    public void chooseNickname(Event event){
        String regex = ".*[a-zA-Z0-9]+.*";
        String input1;
        printText(event.getParameter());
        do{
            input1 = readText();
            if(!Pattern.matches(regex, input1))
                printText("Insert a valid username!");
        } while(!Pattern.matches(regex, input1));

        event.setParameter(input1);
    }
    /**
     * Allows the player to choose to connect to an existing lobby or create a new one.
     *
     * @param event the event containing the prompt for choosing an option
     */
    @Override
    public void chooseConnectOrCreateLobby(Event event){
        int choice = -1;
        printText(event.getParameter());

        do {
            try {

                String input = readText();  // read input as a string
                choice = Integer.parseInt(input);  // try to convert the string in int
                if (choice != 1 && choice != 2 && choice != 0)
                    printText("Reply 1 or 2, or 0 to Exit");

            } catch(NumberFormatException e){
                printText("Reply 1 or 2, or 0 to Exit");
            }
        } while(choice != 1 && choice != 2 && choice != 0);

        event.setN1(choice);
    }
    /**
     * Allows the player to select the number of players in the game.
     *
     * @param event the event containing the prompt for selecting the number of players
     */
    @Override
    public void selectNumbersPlayers(Event event){
        int num_players = 0;
        printText(event.getParameter());

        do {
            try {

                String input = readText();
                num_players = Integer.parseInt(input);
                if (num_players < 2 || num_players > 4)
                    printText("Reply with a number between 2 and 4");

            } catch(NumberFormatException e){
                printText("Reply with a number between 2 and 4");
            }
        } while(num_players < 2 || num_players > 4);

        event.setN1(num_players);
    }
    /**
     * Allows the player to enter the ID of a match to join.
     *
     * @param event the event containing the prompt for entering the match ID
     */
    @Override
    public void idMatch(Event event){
        int id = -1;
        boolean id_is_string;
        printText(event.getParameter());

        do{
            try {
                String input = readText();
                id = Integer.parseInt(input);
                id_is_string = false;
            } catch(NumberFormatException e){
                printText("Reply with a valid number");
                id_is_string = true;
            }
        } while(id_is_string);

        event.setN1(id);
    }
    /**
     * Informs the player that they have joined a lobby, and print the name of the members.
     *
     * @param event the event containing the lobby information
     */
    @Override
    public void joinLobby(Event event){
        printText(event.getParameter());
        if (event.getPlayers() != null) {
            printText("You join a lobby with");
            event.getPlayers().forEach(this::printText);
        }
        else
            printText("Now you gotta wait a little bit... until other players connect!");
    }
    /**
     * Informs the player that another player has joined the lobby.
     *
     * @param event the event containing the information of the player who joined
     */
    @Override
    public void playerJoined(Event event){
        printText(event.getParameter() + " joined the lobby");
    }
    /**
     * Allows the player to choose a token.
     *
     * @param event the event containing the prompt for choosing a token
     */
    @Override
    public void chooseToken(Event event){
        printText(event.getParameter());
        String token = readText();
        token = token.toUpperCase();
        while (!token.equals("RED") && !token.equals("YELLOW") && !token.equals("BLUE") && !token.equals("GREEN")){
            printText("Reply with a valid token:");
            token = readText();
            token = token.toUpperCase();
        }
        event.setParameter(token);
    }
    /**
     * Allows the player to choose the face of a starter card.
     *
     * @param event the event containing the prompt for choosing the face of a starter card
     */
    @Override
    public void chooseFaceStarter(Event event){
        printText(event.getParameter());
        String face;
        printCard(event.getN1());

        printText(event.getParameter());

        face = readText();
        face = face.toUpperCase();

        while (!face.equals("FRONT") && !face.equals("BACK")){
            printText("Reply with [ FRONT ] or [ BACK ]: ");
            face = readText();
            face = face.toUpperCase();
        }

        PlayerData.addCard(event.getN1());
        PlayerData.setFace(event.getN1(),face);
        event.setParameter(face);
    }
    /**
     * Allows the player to choose between two objective cards.
     *
     * @param event the event containing the prompt for choosing an objective card
     */
    @Override
    public void chooseObjectives(Event event){
        int id1 = 0;
        boolean not_a_number;
        printText(event.getParameter());
        printCards(event.getN1(), event.getN2());
        printText("Choose between: \n[1] Card "+ event.getN1()+"\n[2] Card "+event.getN2());
        do{
            try {
                String input = readText();
                id1 = Integer.parseInt(input);
                not_a_number = false;
                if (id1 != 1 && id1 != 2){
                    printText("Reply with 1 to choose the first card or 2 to choose the second one");
                    printCards(event.getN1(), event.getN2());
                }
            } catch(NumberFormatException e){
                printText("Reply with a valid number");
                not_a_number = true;
            }
        } while(not_a_number || (id1 != 1 && id1 != 2));

        event.setN1(id1);
    }
    /**
     * Sends the objective cards to the player.
     *
     * @param event the event containing the objectives to send
     */
    @Override
    public void sendObjectives(Event event){
        PlayerData.setPersonalObj(event.getN1());
        PlayerData.setPlayfieldObj1(event.getN2());
        PlayerData.setPlayfieldObj2(event.getN3());
        printText(event.getParameter());
        printCards(PlayerData.getPersonalObj(), PlayerData.getPlayfieldObj1(), PlayerData.getPlayfieldObj2());
    }
    /**
     * Allows the player to choose a card to play.
     *
     * @param event the event containing information about the cards.
     */
    @Override
    public void choosePlayCard(Event event){
        int id2 = 0;
        boolean not_a_number1;

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Every turn prints the Objectives
        printText("Your objective and the Playfield objective card\n");
        printCards(PlayerData.getPersonalObj(), PlayerData.getPlayfieldObj1(), PlayerData.getPlayfieldObj2());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        printText(event.getParameter());
        printCards(event.getN1(), event.getN2(), event.getN3());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        printText("Choose between\n[1] Card "+ event.getN1()+"\n[2] Card "+event.getN2()
                +"\n[3] Card "+event.getN3() +"\n");
        do{
            try {
                String input = readText();
                id2 = Integer.parseInt(input);
                not_a_number1 = false;
                if (id2 != 1 && id2 != 2 && id2 != 3){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    printText("Reply: [ 1 ] First card \n[ 2 ] Second card \n" +
                            "[ 3 ] Third card");

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    printCards(event.getN1(), event.getN2(), event.getN3());
                }
            } catch(NumberFormatException e){
                printText("Reply with a valid number");
                not_a_number1 = true;
            }
        } while(not_a_number1 || (id2 != 1 && id2 != 2 && id2 != 3));
        switch(id2){
            case 1 -> PlayerData.addCard(event.getN1());
            case 2 -> PlayerData.addCard(event.getN2());
            case 3 -> PlayerData.addCard(event.getN3());
        }
        event.setN1(id2);
    }
    /**
     * Allows the player to choose whether to use the front or back face of a card.
     *
     * @param event the event containing the prompt for choosing the face of a card
     */
    @Override
    public void chooseFrontOrBack(Event event){

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        printText(event.getParameter());

        String face1;
        printCard(event.getN1());

        face1 = readText();
        face1 = face1.toUpperCase();

        while (!face1.equals("FRONT") && !face1.equals("BACK") && !face1.equals("RETURN")){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            printText("Reply with FRONT or BACK or RETURN: ");
            face1 = readText();
            face1 = face1.toUpperCase();
        }

        if (!face1.equals("RETURN"))
            PlayerData.setFace(event.getN1(), face1);

        event.setParameter(face1);
    }
    /**
     * Allows the player to choose the position for a card.
     *
     * @param event the event containing the prompt for choosing the position
     */
    @Override
    public void choosePosition(Event event){
        int id3;
        int id4 = 0;
        boolean not_a_number2;
        boolean done = false;
        boolean invalid;
        printText(event.getParameter());

        do{
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            printText("\nSelect the option you prefer:"+
                    "\n[1]. Insert the position where you want to put the card"+
                    "\n[2]. Insert the number on the board of the card you want to view");
            do {
                try {
                    String input = readText();
                    id3 = Integer.parseInt(input);
                    do {
                        try {
                            if (id3 == 1) {
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                printText("Insert the position:");

                                while (true) {
                                    id4 = Integer.parseInt(readText());
                                    if ((id4 < 1 || id4 > event.getN1())) {
                                        try {
                                            Thread.sleep(100);
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }
                                        printText("Reply https://discord.com/channels/@mewith a number between 1 and " + event.getN1());
                                    } else {
                                        done = true;
                                        not_a_number2 = false;
                                        invalid = false;
                                        break;
                                    }
                                }

                            } else if (id3 == 2) {
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                printText("Insert the number of the card");

                                while (true) {
                                    id4 = Integer.parseInt(readText());
                                    if (!PlayerData.isValidCard(id4)) {
                                        try {
                                            Thread.sleep(100);
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }
                                        printText("Reply with a valid card number");
                                    } else {
                                        printCard(id4, PlayerData.getFace(id4));
                                        not_a_number2 = false;
                                        invalid = false;
                                        break;
                                    }
                                }

                            } else {
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                printText("Reply with 1 to choose a position or 2 to view a card");
                                invalid = true;
                                not_a_number2 = false;
                            }
                        } catch (NumberFormatException e) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException exx) {
                                throw new RuntimeException(exx);
                            }
                            printText("Reply with a valid number");
                            not_a_number2 = true;
                            invalid = false;
                        }
                    } while (not_a_number2);

                } catch (NumberFormatException e) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    printText("Reply with a valid number");
                    invalid = true;
                }

            } while (invalid);

        } while (!done);

        event.setN1(id4);
    }
    /**
     * Allows the player to choose a card to draw.
     *
     * @param event the event containing information for choosing a card to draw
     */
    public void chooseDrawCard(Event event){
        int id5 = 0;
        boolean not_a_number3;
        ArrayList<Integer> validOptions = new ArrayList<>();

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        printText("Where do you want to draw a card?\n");
        if(event.getN1() != 0){
            printText("[1]. Resource deck\n");
            printCard(event.getN1(), "BACK");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            validOptions.add(1);
        }
        if(event.getN2() != 0){
            printText("[2]. Gold deck");
            printCard(event.getN2(), "BACK");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            validOptions.add(2);
        }
        if(event.getN3() != 0){
            printText("[3]. Resource card");
            printCard(event.getN3(), "FRONT");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            validOptions.add(3);
        }
        if(event.getN4() != 0){
            printText("[4]. Resource card");
            printCard(event.getN4(), "FRONT");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            validOptions.add(4);
        }
        if(event.getN5() != 0){
            printText("[5]. Gold card");
            printCard(event.getN5(), "FRONT");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            validOptions.add(5);
        }
        if(event.getN6() != 0){
            printText("[6]. Gold card");
            printCard(event.getN6(), "FRONT");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            validOptions.add(6);
        }

        do{
            try {
                String input = readText();
                id5 = Integer.parseInt(input);
                not_a_number3 = false;
                StringBuilder message= new StringBuilder();
                for (Integer x : validOptions) {
                    message.append("[").append(x).append("] ");
                }
                if (!validOptions.contains(id5)){
                    printText("choose a number from the following\n"+message);
                }
            } catch(NumberFormatException e){
                printText("Reply with a valid number");
                not_a_number3 = true;
            }
        } while(not_a_number3 || id5 < 1 || id5 > 6);

        event.setN1(id5);
    }
    /**
     * Reconnects the player's data and print all personal information.
     *
     * @param event the event containing the player's data to reconnect
     */
    @Override
    public void reconnectPlayerData(Event event) {

        printText(event.getParameter());

        printText("You got the token: "+PlayerData.getToken()+"\nThese are your selected Starter card and Objective card");
        printCard(event.getN1(), PlayerData.getFace(event.getN1()));
        printCard(PlayerData.getPersonalObj(), "FRONT");
        printText("You have the following cards in your hand");
        printCards(event.getN2(), event.getN3(), event.getN4());
    }
}