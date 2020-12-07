//import java.io.IOException;
import java.util.Scanner;

public class RunSimulation {

    // for interactivity
    private static Scanner reader;

    // simulation parameters
    private static int maxTicks, mode;
    private static Simulator simulator;
    private static Visualizer visualizer;

    private static final int SUMMARY = 1, VISUAL = 2;

    /**
     * Initialization routine.
     */
    private static void init() {
        // Simulation parameters
        double sugarProbability;
        do {
            System.out.print("What is the probability that a node will have sugar? ");
            sugarProbability = reader.nextDouble();
            if (sugarProbability < 0 || sugarProbability > 1)
                System.out.println("Please insert a value between 0 and 1.");
        } while (sugarProbability < 0 || sugarProbability > 1);
        int avgSugar;
        do {
            System.out.print("What is the average amount of sugar in a node with sugar? ");
            avgSugar = reader.nextInt();
            if (avgSugar <= 0)
                System.out.println("Please insert a positive value.");
        } while (avgSugar <= 0);
        int carriedSugar;
        do {
            System.out.print("How many units of sugar can an ant carry? ");
            carriedSugar = reader.nextInt();
            if (carriedSugar <= 0)
                System.out.println("Please input a positive value.");
        } while (carriedSugar <= 0);
        int pheromones;
        do {
            System.out.print("How many units of pheromones are dropped by ants as they move? ");
            pheromones = reader.nextInt();
            if (pheromones <= 0)
                System.out.println("Please input a positive value.");
        } while (pheromones <= 0);

        do {
            System.out.print("For how long should the simulation run? ");
            maxTicks = reader.nextInt();
            if (maxTicks <= 0)
                System.out.println("Please input a positive value.");
        } while (maxTicks <= 0);

        Colony[] homes = initHomes();
        Ant[] ants = initAnts(homes);

        // Initialize the grid
        int option;
        do {
            System.out.print("What kind of graph do you want (1:grid / 2:read from a file)? ");
            option = reader.nextInt();
            if (option != 1 && option != 2)
                System.out.println("Please input a valid option.");
        } while (option != 1 && option != 2);
        Graph graph;
        if (option == 1)
            graph = initGrid(homes, sugarProbability, avgSugar);
        else // option == 2
            graph = initFromFile(homes, sugarProbability, avgSugar);

        visualizer = new Visualizer(graph, (option == 1), homes[0], ants);
        simulator = new Simulator(graph, ants, carriedSugar, pheromones);
    }

    /**
     * Initializes a grid
     *
     * @param homes            the list of home nodes
     * @param sugarProbability the probability that there is sugar in a node
     * @param avgSugar         the average amount of sugar in a node
     */
    private static Graph initGrid(Colony[] homes, double sugarProbability, int avgSugar) {
        // Get the dimensions
        int width, height;
        do {
            System.out.print("Please insert the width of the grid (at least 3): ");
            width = reader.nextInt();
            if (width < 3)
                System.out.println("Please insert a correct value.");
        } while (width < 3);
        do {
            System.out.print("Please insert the height of the grid (at least 3): ");
            height = reader.nextInt();
            if (height < 3)
                System.out.println("Please insert a correct value.");
        } while (height < 3);

        return new Graph(width, height, homes, sugarProbability, avgSugar);
    }

    /**
     * Initializes the graph from a file.
     *
     * @param homes            the list of home nodes
     * @param sugarProbability the probability that there is sugar in a node
     * @param avgSugar         the average amount of sugar in a node
     */
    private static Graph initFromFile(Colony[] homes, double sugarProbability, int avgSugar) {
	reader.nextLine(); // flush
        System.out.print("Please enter the name of the file containing the graph: ");
        String filename = reader.nextLine().trim();
        return new Graph(filename, homes, sugarProbability, avgSugar);
    }

    /**
     * Initializes the ant colonies.
     */
    private static Colony[] initHomes() {
        int totalHomes;
        do {
            System.out.print("Please insert the number of ant colonies: ");
            totalHomes = reader.nextInt();
            if (totalHomes <= 0)
                System.out.println("Please insert a positive number.");
        } while (totalHomes <= 0);
        int i = 0;
        Colony[] homes = new Colony[totalHomes];
        while (i < totalHomes) {
            homes[i] = new Colony();
            i = i + 1;
        }
        return homes;
    }

    /**
     * Initializes the ants.
     *
     * @param homes the list of colonies
     */
    private static Ant[] initAnts(Colony[] homes) {
        int totalHomes = homes.length;
        int[] antValues = new int[totalHomes];
        int totalAnts = 0;
        int i = 0;
        while (i < totalHomes) {
            int antsHere;
            do {
                System.out.print("How many ants live at colony " + (i + 1) + "? ");
                antsHere = reader.nextInt();
                if (antsHere < 0)
                    System.out.println("Please insert a non-negative number.");
            } while (antsHere < 0);
            antValues[i] = antsHere;
            totalAnts = totalAnts + antsHere;
            i = i + 1;
        }

        // create the ants
        Ant[] theAnts = new Ant[totalAnts];
        int currentHome = 0;
        i = 0;
        while (i < totalAnts) {
            int j = 0;
            while (j < antValues[currentHome]) {
                theAnts[i] = new Ant(homes[currentHome]);
                i = i + 1;
                j = j + 1;
            }
            currentHome = currentHome + 1;
        }
        return theAnts;
    }

    public static void main(String[] args) {
        reader = new Scanner(System.in);
        init();

        do {
            System.out.print("What simulation mode do you want (1:resumed / 2:visual)? ");
            mode = reader.nextInt();
            if (mode != 1 && mode != 2)
                System.out.println("Please input a valid option.");
        } while (mode != 1 && mode != 2);

        int reportFrequency = 0;
        if (mode == SUMMARY) {
            System.out.println("Simulation started.");
            do {
                System.out.println("How often do you want a report?");
                reportFrequency = reader.nextInt();
                if (reportFrequency <= 0)
                    System.out.println("Please insert a positive value.");
            } while (reportFrequency <= 0);
        }
        int tick = 0;
        if (mode == VISUAL)
            visualizer.display();
        while (tick < maxTicks) {
            simulator.tick();
            tick = tick + 1;
            if (mode == SUMMARY && tick % reportFrequency == 0) {
                System.out.println();
                System.out.println("Status at tick " + tick);
                System.out.println("------");
                visualizer.printStatus();
                System.out.println("------");
                System.out.println();
            }
            if (mode == VISUAL) {
                visualizer.update();
                /*if ( reportFrequency > 0 && tick % reportFrequency == 0) {
                    try {
                        visualizer.exportGridVisual("status at tick " +  tick);
                    } catch (IOException e ) {

                    }
                }*/
            }
        }
    }
}
