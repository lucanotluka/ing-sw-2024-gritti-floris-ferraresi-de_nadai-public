package it.polimi.ingsw.model.cards;

/**
 * Enumeration representing different types of points for cards.
 *
 * @author Angelo De Nadai
 */
public enum PointsType {
    NULL,           // No point calculation
    CORNERS,        // Points calculated based on the number of corners covered
    INKWELL,        // Points calculated based on the presence of inkwells
    QUILL,          // Points calculated based on the presence of quills
    MANUSCRIPT      // Points calculated based on the presence of manuscripts
}
