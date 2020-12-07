/**
 * Class for setting up and running a simulation, one step at a time.
 */
public class Simulator {

    private Object[] ints = new Object[3];
    { ints[2] = new int[3]; }

    /**
     * Constructor: sets up a simulation with a given graph, a given set of ants, and the simulation
     * parameters.<p>
     * <b>Precondition:</b> every ant's home node is a node in the graph.
     * @param graph the graph where the ants move
     * @param ants an array containing all ants
     * @param carriedSugar the units of sugar an ant can carry
     * @param droppedPheromones the units of pheromones dropped by an ant when travelling through an
     * edge
     */
    public Simulator(Graph graph, Ant[] ants, int carriedSugar, int droppedPheromones) {
	ints[0] = graph;
	ints[1] = ants;
	((int[])ints[2])[0] = ants.length;
	((int[])ints[2])[1] = carriedSugar;
	((int[])ints[2])[2] = droppedPheromones;
    }

    /**
     * Runs the simulation for one unit of time.
     * In particular, this method goes through all ants and updates their position.
     * Ants that pass through nodes containing sugar pick up some sugar (unless they are already
     * carrying sugar), ants that get home drop the sugar they are carrying, and ants that arrived
     * home in the previous tick eat some food from the colony's stock or die.
     */
    public void tick() {
	((Graph)ints[0]).tick();
	// update all ants
	int i = 0;
	while (i < ((int[])ints[2])[0]) {
	    if (((Ant[])ints[1])[i].isAtHome() && !((Ant[])ints[1])[i].wasAtHome()) {
		if (((Ant[])ints[1])[i].home().hasStock()) {
		    ((Ant[])ints[1])[i].home().consume();
		    ((Ant[])ints[1])[i].move(((Ant[])ints[1])[i].home());
		}
		else { // the ant dies
		    ((int[])ints[2])[0] = ((int[])ints[2])[0] - 1;
		    ((Ant[])ints[1])[i] = ((Ant[])ints[1])[((int[])ints[2])[0]];
		    ((Ant[])ints[1])[((int[])ints[2])[0]] = null; // for the visualizer
		}
	    }
	    else {
		move(((Ant[])ints[1])[i]);
		i = i+1;
	    }
	}
    }

    /**
     * Moves one ant.<p>
     * <b>Precondition:</b> the ant has not just arrived home
     * @param ant the ant to move
     */
    private void move(Ant ant){
	// find the node
	Node target;
	if (!ant.carrying() && ant.current().sugar() > 0) {
	    ant.pickUpSugar();
	    ant.current().decreaseSugar();
	    target = ant.previous();
	}
	else {
	    Node[] candidates = ((Graph)ints[0]).adjacentTo(ant.current());
	    int totalPheromones = 0;
	    int[] levels = new int[candidates.length];
	    for (int i=0; i<candidates.length; i++)
		if (candidates[i] != ant.previous() && candidates.length > 1) {
		    totalPheromones = totalPheromones + ((Graph)ints[0]).pheromoneLevel(ant.current(),candidates[i]) + 1;
		    levels[i] = totalPheromones;
		}
		else {
		    totalPheromones = totalPheromones + 1;
		    levels[i] = totalPheromones;
		}
	    int choice = RandomUtils.randomInt(totalPheromones);
	    int i=0;
	    while (levels[i] < choice) i++;
	    target = candidates[i];
	}

	// move the ant
	ant.move(target);
	if (ant.isAtHome() && ant.carrying()) {
	    ant.dropSugar();
	    ant.home().topUp(((int[])ints[2])[1]);
	}
	((Graph)ints[0]).raisePheromones(ant.previous(),target,((int[])ints[2])[2]);
    }
}
