package it.polimi.ingsw.jsonData;

import it.polimi.ingsw.model.Coordinate;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.objectives.ObjectiveCard;
import it.polimi.ingsw.model.objectives.PatternObjectiveCard;
import it.polimi.ingsw.model.objectives.SymbolObjectiveCard;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * The {@code DataParser} class provides methods for parsing and creating different types of cards
 * from JSON files. It includes methods to get starter cards, resource cards, gold cards, and objective cards.
 *
 * <p>This class uses the JSON.simple library to parse JSON files and convert them into card objects.</p>
 *
 * @author Luca Gritti, Fabio Marco Floris
 */
public class DataParser {

    /**
     * Returns a shuffled list of starter card IDs.
     *
     * @return a shuffled {@link ArrayList} of starter card IDs
     */
    public static ArrayList<Integer> getStarterCards(){
        ArrayList<Integer> startDeck = new ArrayList<>(6);
        for(int i = 81; i < 87; i++) startDeck.add(i);
        Collections.shuffle(startDeck);
        return startDeck;
    }

    /**
     * Returns a shuffled list of resource cards by reading from the JSON file.
     *
     * @return a shuffled {@link ArrayList} of {@link ResourceCard} objects
     */
    public static ArrayList<ResourceCard> getResourceCards() {
        ArrayList<ResourceCard> resDeck;
        InputStream inputStream = DataParser.class.getResourceAsStream("FrontResourceDeck.json");
        try {
//            FileReader fr = new FileReader("FrontResourceDeck.json");
            JSONParser parser = new JSONParser();
            JSONArray arrayResource = (JSONArray) parser.parse(new InputStreamReader(inputStream));
            resDeck = new ArrayList<>();

            // popolare l'array deck di carte
            for (Object objCarta: arrayResource) {
                ResourceCard carta = getResourceCard((JSONObject) objCarta);
                resDeck.add(carta);
            }

            Collections.shuffle(resDeck);
            inputStream.close();

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return resDeck;
    }

    /**
     * Returns a shuffled list of gold cards by reading from the JSON file.
     *
     * @return a shuffled {@link ArrayList} of {@link GoldCard} objects
     */
    public static ArrayList<GoldCard> getGoldCards() {

        ArrayList<GoldCard> golDeck;

        try {
            InputStream inputStream = DataParser.class.getResourceAsStream("FrontGoldDeck.json");
            JSONParser parser = new JSONParser();
            JSONArray arrayResource = (JSONArray) parser.parse(new InputStreamReader(inputStream));
            golDeck = new ArrayList<>();

            for (Object objCarta: arrayResource) {
                GoldCard carta = getGoldCard((JSONObject) objCarta);
                golDeck.add(carta);
            }

            Collections.shuffle(golDeck);
            inputStream.close();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return golDeck;
    }

    /**
     * Returns a shuffled list of objective cards by reading from the JSON file.
     *
     * @return a shuffled {@link ArrayList} of {@link ObjectiveCard} objects
     */
    public static ArrayList<ObjectiveCard> getObjectiveCards() {
        ArrayList<ObjectiveCard> objDeck;

        try {
            InputStream inputStream = DataParser.class.getResourceAsStream("FrontObjectiveDeck.json");
            JSONParser parser = new JSONParser();
            JSONArray arrayResource = (JSONArray) parser.parse(new InputStreamReader(inputStream));
            objDeck = new ArrayList<>();

            for (Object objCarta: arrayResource) {
                ObjectiveCard carta = getObjectiveCard((JSONObject) objCarta);
                objDeck.add(carta);
            }

            Collections.shuffle(objDeck);
            inputStream.close();

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return objDeck;
    }


    /**
     * Creates and returns a {@link ResourceCard} object from a JSON object.
     *
     * @param objCarta the JSON object representing the resource card
     * @return a {@link ResourceCard} object
     */
    public static ResourceCard getResourceCard(JSONObject objCarta)  {
        Long idLong = (Long)  objCarta.get("cardId");
        int id = (int) idLong.intValue();

        String symString = (String) objCarta.get("symbol");
        Symbol sym = Symbol.valueOf(symString);

        Long pointsLong = (Long)  objCarta.get("points");
        int points = (int) pointsLong.intValue();

        // list to be fulfilled with the actual Corners in JSON file
        ArrayList<Corner> cornersToAdd = new ArrayList<>();
        addCorners(cornersToAdd, objCarta);

        if (cornersToAdd.size() == 4)
            return new ResourceCard(id, sym, points, cornersToAdd.get(0), cornersToAdd.get(1), cornersToAdd.get(2), cornersToAdd.get(3));
        return new ResourceCard(id, sym, points, cornersToAdd.get(0), cornersToAdd.get(1), cornersToAdd.get(2));
    }

    /**
     * Creates and returns a {@link GoldCard} object from a JSON object.
     *
     * @param objCarta the JSON object representing the gold card
     * @return a {@link GoldCard} object
     */
    public static GoldCard getGoldCard(JSONObject objCarta) {
        Long idLong = (Long) objCarta.get("cardId");
        int id = (int) idLong.intValue();

        String symString = (String) objCarta.get("symbol");
        Symbol sym = Symbol.valueOf(symString);

        Long pointsLong = (Long)  objCarta.get("points");
        int points = (int) pointsLong.intValue();

        String ptString = (String) objCarta.get("pointsType");
        PointsType pointsType = PointsType.valueOf(ptString);

        JSONArray conditionArray = (JSONArray) objCarta.get("condition");
        Map<Symbol, Integer> condition = new HashMap<>();
        for(Object obj: conditionArray){
            JSONObject conditionObj = (JSONObject) obj;

            String sString = (String) conditionObj.get("symbol");
            Symbol s = Symbol.valueOf(sString);

            Long qLong = (Long)  conditionObj.get("quantity");
            int q = (int) qLong.intValue();

            condition.put(s, q);
        }


        // list to be fulfilled with the actual Corners in JSON file
        ArrayList<Corner> cornersToAdd = new ArrayList<>();
        addCorners(cornersToAdd, objCarta);

        if(cornersToAdd.size() == 2)
            return new GoldCard(id, cornersToAdd.get(0), cornersToAdd.get(1), points, sym, pointsType, condition);
        else if(cornersToAdd.size() == 3)
            return new GoldCard(id, cornersToAdd.get(0), cornersToAdd.get(1), cornersToAdd.get(2), points, sym, pointsType, condition);
        return null;
    }

    /**
     * Creates and returns an {@link ObjectiveCard} object from a JSON object.
     *
     * @param objCarta the JSON object representing the objective card
     * @return an {@link ObjectiveCard} object
     */
    public static ObjectiveCard getObjectiveCard(JSONObject objCarta) {

        Long idLong = (Long) objCarta.get("cardId");
        int id = (int) idLong.intValue();

        Long pointsLong = (Long)  objCarta.get("points");
        int points = (int) pointsLong.intValue();

        String type = (String) objCarta.get("type");

        if(type.equals("PATTERN_OBJECTIVE")){

            JSONArray patternArray = (JSONArray) objCarta.get("pattern");

            Map<Coordinate, Symbol> pattern = new HashMap<>();

            for(Object obj: patternArray){
                JSONObject patternObj = (JSONObject) obj;

                String sString = (String) patternObj.get("symbol");
                Symbol s = Symbol.valueOf(sString);

                Long xLong = (Long)  patternObj.get("X");
                int x = (int) xLong.intValue();
                Long yLong = (Long)  patternObj.get("Y");
                int y = (int) yLong.intValue();

                Coordinate c = new Coordinate(x, y);

                pattern.put(c, s);
            }
            return new PatternObjectiveCard(id, points, pattern);

        } else if (type.equals("SYMBOL_OBJECTIVE")) {

            String symb1String = (String) objCarta.get("symbol1");
            Symbol symb1 = Symbol.valueOf(symb1String);
            String symb2String = (String) objCarta.get("symbol2");
            Symbol symb2 = Symbol.valueOf(symb2String);

            // carta con tre symboli: la lettura e' non-null
            String symb3String = (String) objCarta.get("symbol3");
            if(symb3String != null){
                Symbol symb3 = Symbol.valueOf(symb3String);
                return new SymbolObjectiveCard(id, points, symb1, symb2, symb3);
            } else {
                return new SymbolObjectiveCard(id, points, symb1, symb2);
            }

        }

        else return null;
    }


    /**
     * Creates and returns a {@link StarterCard} object based on the card ID and face.
     *
     * @param id the card ID
     *
     * @param face the face of the card (FRONT or BACK)
     * @return a {@link StarterCard} object, or null if the card is not found
     */
    public static StarterCard getStarterCard(int id, Face face) {

        try {
            InputStream inputStream;
            if(face == Face.FRONT){
                inputStream = DataParser.class.getResourceAsStream("FrontStarterDeck.json");
            } else{
                inputStream = DataParser.class.getResourceAsStream("BackStarterDeck.json");
            }
            JSONParser parser = new JSONParser();
            JSONArray arrayResource = (JSONArray) parser.parse(new InputStreamReader(inputStream));
            JSONObject objCarta = null;

            for (Object carta: arrayResource) {
                JSONObject objCartaTemp = (JSONObject) carta;
                if(((Long) objCartaTemp.get("cardId")).intValue() == id){
                    objCarta = objCartaTemp;
                    break;
                }
            }

            inputStream.close();

            if( objCarta != null ){

                Symbol sym = Symbol.EMPTY;

                // list to be fulfilled with the actual Corners in JSON file
                ArrayList<Corner> cornersToAdd = new ArrayList<>();
                addCorners(cornersToAdd, objCarta);

                ArrayList<Symbol> symbols = new ArrayList<>();

                if(face == Face.FRONT) {
                    JSONArray symbArray = (JSONArray) objCarta.get("symbols");
                    for (Object obj : symbArray) {
                        Symbol symb = Symbol.valueOf((String) obj);
                        symbols.add(symb);
                    }
                } else { symbols = null; }

                if(cornersToAdd.size() == 2)
                    return new StarterCard(id, symbols, face,cornersToAdd.get(0), cornersToAdd.get(1));
                else if(cornersToAdd.size() == 4)
                    return new StarterCard(id, symbols, face,cornersToAdd.get(0), cornersToAdd.get(1), cornersToAdd.get(2), cornersToAdd.get(3));
                return null;


            }
            else return null;
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Helper method to add corners from a JSON object to a list.
     *
     * @param cornersToAdd the list of corners to add to
     * @param objCarta the JSON object containing the corners data
     */
    private static void addCorners(ArrayList<Corner> cornersToAdd, JSONObject objCarta) {
        JSONObject JSONCorner0 = (JSONObject) objCarta.get("top_left");
        if (JSONCorner0 != null) {
            addCorner(cornersToAdd, JSONCorner0);
        }
        JSONObject JSONCorner1 = (JSONObject) objCarta.get("top_right");
        if (JSONCorner1 != null) {
            addCorner(cornersToAdd, JSONCorner1);
        }
        JSONObject JSONCorner2 = (JSONObject) objCarta.get("bot_left");
        if (JSONCorner2 != null) {
            addCorner(cornersToAdd, JSONCorner2);
        }
        JSONObject JSONCorner3 = (JSONObject) objCarta.get("bot_right");
        if (JSONCorner3 != null) {
            addCorner(cornersToAdd, JSONCorner3);
        }
    }

    /**
     * Helper method to create and add a corner from a JSON object to a list.
     *
     * @param cornersToAdd the list of corners to add to
     * @param JSONCorner the JSON object representing a corner
     */
    private static void addCorner(ArrayList<Corner> cornersToAdd, JSONObject JSONCorner) {
        String symbString = (String) JSONCorner.get("symbol");
        Symbol symb = Symbol.valueOf(symbString);

        String typeString = (String) JSONCorner.get("type");
        CornerType type = CornerType.valueOf(typeString);

        Corner corner = new Corner(type, symb);
        cornersToAdd.add(corner);
    }

    public static JSONArray getJSONFromPath(String path) throws IOException, ParseException {
        InputStream inputStream = DataParser.class.getResourceAsStream(path);
        JSONArray array = (JSONArray) (new JSONParser()).parse(new InputStreamReader(inputStream));
        inputStream.close();
        return array;
    }
}