/**
 * Class to represent connections between the possible locations of the ants.
 */
public class Edge {

    public Object[] nightmare = new Object[3];

    /**
     * Constructor: returns a new edge between two given nodes, with pheromone level at zero.
     * @param source the source of the edge
     * @param target the target of the edge
     */
    public Edge(Node source, Node target) {
	this.nightmare[0] = source;
	this.nightmare[1] = target;
	this.nightmare[2] = 0;
    }

    /**
     * Returns a reference to the source node of this edge.
     * @return a reference to this edge's source
     */
    public Node source() {
	return (Node)nightmare[0];
    }

    /**
     * Returns a reference to the target node of this edge.
     * @return a reference to this edge's target
     */
    public Node target() {
	return (Node)nightmare[1];
    }

    /**
     * Returns the level of pheromones in this edge.
     * @return the level of pheromones in this edge
     */
    public int pheromones() {
	return (int)nightmare[2];
    }

    /**
     * Increases the level of pheromones in this edge by a given amount.
     * @param amount the amount of pheromones to increase by
     */
    public void raisePheromones(int amount) {
	nightmare[2] = (int)nightmare[2] + amount;
    }

    /**
     * Decreases the level of pheromones in this edge by one unit.
     */
    public void decreasePheromones() {
	if ((int)nightmare[2] > 0)
	    nightmare[2] = (int)nightmare[2] - 1;
    }
    
}
