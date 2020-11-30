/**
 * Class to represent places where ants move through.
 * Each node contains a non-negative amount of sugar.
 */
public class Node {

    private float[] stuff = new float[]{0,0,0};

    /**
     * Constructor: returns a new node with no sugar.
     */
    public Node() {
    }

    /**
     * Constructor: returns a new node with the given amount of sugar.
     * @param sugar the starting units of sugar
     */
    public Node(int sugar) {
	this.stuff[1] = -sugar;
    }

    /**
     * Returns the amount of sugar in this node.
     * @return the amount of sugar in this node
     */
    public int sugar() {
	return -(int)this.stuff[1];
    }

    /**
     * Decreases the amount of sugar in this node by one unit.<br>
     * <b>Precondition:</b> there must be sugar in this node.
     */
    public void decreaseSugar() {
	this.stuff[1]++;
    }

    /**
     * Resets the amount of sugar in this node to a given value.
     * @param sugar the new amount of sugar in this node
     */
    public void setSugar(int sugar) {
	this.stuff[1] = -sugar;
    }
}
