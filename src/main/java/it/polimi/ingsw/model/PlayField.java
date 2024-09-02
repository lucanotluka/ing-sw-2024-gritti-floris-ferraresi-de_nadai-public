package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NoMoreCardToReplaceException;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.objectives.ObjectiveCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;


/** Class PlayField: represents the field of the match, common to all players
 *
 * @author Luca Gritti, Marco Ferraresi
 */

public class PlayField {

    private List<ResourceCard> resourceDeck;
    private List<GoldCard> goldDeck;
    private List<ObjectiveCard> objectiveDeck;

    private ResourceCard firstResource;
    private ResourceCard secondResource;

    private GoldCard firstGold;
    private GoldCard secondGold;

    private final ObjectiveCard firstObjective;
    private final ObjectiveCard secondObjective;

    /**
     * Constructor method: initialise all decks and pick the first cards.
     */
    public PlayField(ArrayList<ResourceCard> resDeck, ArrayList<GoldCard> golDeck, ArrayList<ObjectiveCard> objDeck) {

        this.resourceDeck = resDeck;
        this.goldDeck = golDeck;
        this.objectiveDeck = objDeck;


        // Shuffle the deck (again)
        Collections.shuffle(resourceDeck);
        Collections.shuffle(goldDeck);
        Collections.shuffle(objectiveDeck);


        // Pick and initialize the first six cards on the playfield
        firstResource = resourceDeck.removeLast();
        secondResource = resourceDeck.removeLast();
        firstGold = goldDeck.removeLast();
        secondGold = goldDeck.removeLast();
        firstObjective = objectiveDeck.removeLast();
        secondObjective = objectiveDeck.removeLast();
    }

    /**
     * Retrieves the id of the last card in the Resource deck.
     *
     * @return the id of Resource deck's last card
     */
    public int lastCardResourceDeckId(){
        if(resourceDeck ==null || resourceDeck.isEmpty())
            return 0;
        return resourceDeck.getLast().getCardId();
    }

    /**
     * Retrieves the id of the last card in the Gold deck.
     *
     * @return the id of Gold deck's last card
     */
    public int lastCardGoldDeckId(){
        if(goldDeck ==null || goldDeck.isEmpty())
            return 0;
        return goldDeck.getLast().getCardId();
    }

    /**
     * Retrieves the id of the first Resource card on the play field.
     *
     * @return the id of the first Resource card on the play field
     */
    public ResourceCard getFirstResource() {
        return firstResource;
    }

    /**
     * Retrieves the id of the second Resource card on the play field.
     *
     * @return the id of the second Resource card on the play field
     */
    public ResourceCard getSecondResource() {
        return secondResource;
    }

    /**
     * Retrieves the id of the first Gold card on the play field.
     *
     * @return the id of the first Gold card on the play field
     */
    public GoldCard getFirstGold() {
        return firstGold;
    }

    /**
     * Retrieves the id of the second Gold card on the play field.
     *
     * @return the id of the second Gold card on the play field
     */
    public GoldCard getSecondGold() {
        return secondGold;
    }

    /**
     * Resource deck size getter.
     *
     * @return resourceDeck.size() the number of resource cards remaining
     */
    public int getResourceDeckSize() {
        return resourceDeck.size();
    }

    /**
     * Gold deck size getter.
     *
     * @return goldDeck.size() the number of gold cards remaining
     */
    public int getGoldDeckSize() {
        return goldDeck.size();
    }

    /**
     * First objective card getter.
     *
     * @return firstObjective the first objective card in the field
     */
    public ObjectiveCard getFirstObjective() {
        return firstObjective;
    }

    /**
     * Second objective card getter.
     *
     * @return secondObjective the second objective card in the field
     */
    public ObjectiveCard getSecondObjective() {
        return secondObjective;
    }

    /**
     * Retrieves an Objective card from the Objective deck.
     *
     * @return the last Objective card in the Objective deck
     */
    public ObjectiveCard getObjectiveFromDeck(){
        return objectiveDeck.removeLast();
    }

    /**
     * Method for choosing the correct PlayableCard on the field, given its ID.
     *
     * @param choice for the chosen card
     * @return chosenCard the PlayableCard chosen
     * @throws NoMoreCardToReplaceException if the player is trying to draw a card that doesn't exist
     */
    public PlayableCard chooseCardToDraw(DrawCardChoice choice) throws NoMoreCardToReplaceException {

        PlayableCard chosenCard = null;

        if (firstResource != null && choice == DrawCardChoice.FIRSTRESOURCE) {
            chosenCard = firstResource;
            try {
                firstResource = resourceDeck.removeLast();
            } catch (NoSuchElementException e) {
                firstResource = null;
            }
        } else if (secondResource != null && choice == DrawCardChoice.SECONDRESOURCE) {
            chosenCard = secondResource;
            try {
                secondResource = resourceDeck.removeLast();
            } catch (NoSuchElementException e) {
                secondResource = null;
            }
        } else if (firstGold != null && choice == DrawCardChoice.FIRSTGOLD) {
            chosenCard = firstGold;
            try {
                firstGold = goldDeck.removeLast();
            } catch (NoSuchElementException e) {
                firstGold = null;
            }
        } else if (secondGold != null && choice == DrawCardChoice.SECONDGOLD) {
            chosenCard = secondGold;
            try {
                secondGold = goldDeck.removeLast();
            } catch (NoSuchElementException e) {
                secondGold = null;
            }
        } else if (goldDeck != null && !goldDeck.isEmpty() && choice == DrawCardChoice.GOLDDECK) {
            chosenCard = goldDeck.removeLast();
        }
        else if (resourceDeck != null && !resourceDeck.isEmpty() && choice == DrawCardChoice.RESOURCEDECK) {
            chosenCard = resourceDeck.removeLast();
        }
        else throw new NoMoreCardToReplaceException("The card you chose isn't available");

        return chosenCard;
    }
}