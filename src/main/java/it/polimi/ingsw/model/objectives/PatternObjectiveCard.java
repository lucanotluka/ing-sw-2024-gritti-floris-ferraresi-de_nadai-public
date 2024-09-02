package it.polimi.ingsw.model.objectives;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Coordinate;
import it.polimi.ingsw.model.cards.PlayableCard;
import it.polimi.ingsw.model.cards.Symbol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * The PatternObjectiveCard class represents an objective card based on specific
 * combination of cards.
 * It defines conditions based on patterns that players must fulfill to earn points.
 *
 * @author Angelo De Nadai, Marco Ferraresi
 */

public class PatternObjectiveCard extends ObjectiveCard {
    private Map<Coordinate, Symbol> pattern;    //Arrangement of cards required to obtain the points.

    /**
     * Constructs a Pattern Objective card.
     *
     * @param id is the id of the card
     * @param points are the points of the card
     * @param pattern is the pattern of the card that players must fulfill to earn points
     */
    public PatternObjectiveCard(int id, int points, Map<Coordinate, Symbol> pattern) {
        super(id, points);
        this.pattern = pattern;
    }

    /**
     * Checks if two Pattern Objective cards are equal, to do that it checks the conditions that are in ObjectiveCard.equals(Object o)
     * and the pattern
     *
     * @param o is the Object to check
     * @return true if the two Pattern Objective cards are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        return super.equals(o) && (this.pattern.equals(((PatternObjectiveCard) o).pattern));
    }

    /**
     * Check how many times the condition in the board passed as a parameter is respected
     *
     * @param board that must comply with the condition
     * @return number of times the condition is met
     */
    @Override
    public int checkCondition(Board board) {

        int count = 0;
        ArrayList<Coordinate> considerated = new ArrayList<>();   // keeps track of the Map coordinates that have already earned points

        Coordinate cord = new Coordinate(0, 0);
        Symbol s1 = pattern.get(cord);
        List<Map.Entry<Coordinate, PlayableCard>> list = new ArrayList<>(board.getMap().entrySet());
        list.sort(Map.Entry.comparingByKey());
        Collections.reverse(list);
        for (Map.Entry<Coordinate, PlayableCard> entry : list) {   // scroll through the elements of the ordered map

            if (entry.getValue().getSymbol().equals(s1)) {    // if I found the first symbol in the map

                int supp = 0;   // number of pattern's cards checked
                ArrayList<Coordinate> arrSupp = new ArrayList<>(); // keeps track of the coordinates in the map already checked

                for (Map.Entry<Coordinate, Symbol> entry2 : pattern.entrySet()) {       // scroll through the elements of the pattern
                    Coordinate c2 = new Coordinate(entry.getKey().getX() + entry2.getKey().getX(), entry.getKey().getY() + entry2.getKey().getY());

                    if (board.getMap().get(c2) == null || considerated.contains(c2) || !board.getMap().get(c2).getSymbol().equals(entry2.getValue())){
                        break;
                    }

                    arrSupp.add(c2); // add the calculated coordinate each time
                    if (++supp == 3) {      // if the last card occurs
                        count++;
                        considerated.addAll(arrSupp);
                    }
                }
            }
        }
        return count;
    }

}
