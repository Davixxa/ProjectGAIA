/**
 * Class to represent the ants.
 * Each ant knows its its current position and its previous position, as well as the location of its
 * home.
 * Furthermore, ants can be carrying a fixed amount of sugar.
 */
public class Ant {

    private Object[] stuff = new Object[4];

    /**
     * Constructor: returns a new ant, at home.
     * @param home reference to the ant's home node.
     */
    public Ant(Colony home) {
	this.stuff[0] = home;
	this.stuff[1] = home;
	this.stuff[2] = home;
	this.stuff[3] = false;
    }

    /**
     * Returns a reference to the node where this ant currently is.
     * @return a reference to this ant's current position
     */
    public Node current() {
	return (Node)stuff[1];
    }

    /**
     * Returns a reference to the node where this ant was before moving to the current node.
     * @return a reference to this ant's previous position
     */
    public Node previous() {
	return (Node)stuff[2];
    }

    /**
     * Returns a reference to this ant's home node.
     * @return a reference to this ant's home node
     */
    public Colony home() {
	return (Colony)stuff[0];
    }

    /**
     * Informs whether this ant is currently carrying sugar.
     * @return {@code true} if this ant is carrying sugar
     */
    public boolean carrying() {
	return (boolean)stuff[3];
    }

    /**
     * Informs whether this ant is currently at home.
     * @return {@code true} if this ant is at home
     */
    public boolean isAtHome() {
	return (stuff[0] == stuff[1]);
    }

    /**
     * Informs whether this ant's previous location was home.
     * @return {@code true} if this ant's previous location was home
     */
    public boolean wasAtHome() {
	return (stuff[0] == stuff[2]);
    }

    /**
     * Moves this ant to a new location.<br>
     * <b>Precondition:</b> there should be an edge between this ant's current location and its new
     * location.
     * @param location the ant's new location
     */
    public void move(Node location) {
	stuff[2] = stuff[1];
	stuff[1] = location;
    }

    /**
     * Models picking sugar from a location.<br>
     * <b>Precondition:</b> there is sugar at the current node AND this ant is not carrying sugar.
     */
    public void pickUpSugar() {
	stuff[3] = true;
    }

    /**
     * Models dropping sugar.<br>
     * <b>Precondition:</b> this ant is at home AND this ant is carrying sugar.
     */
    public void dropSugar() {
	stuff[3] = false;
    }
}
