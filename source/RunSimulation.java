import java.util.Scanner;

public class RunSimulation {
	
	private static Scanner scanner = new Scanner(System.in);
	private static Visualizer visualizer;
	private static Simulator simulator;
	
	public static void main(String[] args){
		welcomeMessage();
		boolean endProgram;
		
		do{
			init();
			
			System.out.println("Please enter the total simulation time. (Greater than 0)");
			int totalSimulationTime = getInt(0);
			
			System.out.println("Please enter the desired viewing mode:  \n 1. Textual summary \n 2. Graphical representation");
			boolean isGraph = (2 == getIntBetween(1,2));
			
			//Checks if additional information is needed.
			if(!isGraph){
				System.out.println("Please enter the update frequency. (Stricly greater than 0)");
				int updateFrequency = getInt(1);
				
				//Loop to run the simulation in textual summary
				for(int simulationTime = 0; simulationTime < totalSimulationTime; simulationTime++){
					simulator.tick();
					if(simulationTime % updateFrequency == 0 || (simulationTime + 1) == totalSimulationTime)
						visualizer.printStatus();
				}
			}
			else {
				visualizer.display();
			
				//Loop to run the simulation with grafical interface
				for(int simulationTime = 0; simulationTime < totalSimulationTime; simulationTime++){
					simulator.tick();
					visualizer.update();
				}
			}

			System.out.println("What would you like to do now? \n 1. Make another simulation \n 2. Stop the program");
			endProgram = (2 == getIntBetween(1,2));
			
		}while(!endProgram);	
		
		System.exit(0);
	}
	
	/*
	* This method prints the welcome screen when the program is opened
	*/
	private static void welcomeMessage(){
		
	}
	
	/*
	* Initializes the simulator and the visualizer
	*/
	private static void init(){
		//First we start with inputs that are simple datatypes
		System.out.println("Please enter the probability that a node will start as a node containing sugar. (Between 0 and 1.0)");
		double sugarProb = getDoubleBetween(0,1.0);
		
		System.out.println("Please enter the average amount of sugar in a node. (Greater than 0)");
		int averageSugar = getInt(0);
		
		System.out.println("Please enter the number of units of sugar that an ant can carry. (Greater than 0)");
		int carriedSugar = getInt(0);
		
		System.out.println("Please enter the number of units of pheromones dropped by an ant when passing through a node. (Greater than 0)");
		int droppedPheromones = getInt(0);
		
		
		System.out.println("Please enter the total number of ant colonies. (Stricly greater than 0)");
		int totalColonies = getInt(1);
		Colony[] homes = new Colony[totalColonies];
		
		for(int i = 0; i < homes.length; i++){ //Initializes the Colonies in homes[]
			homes[i] = new Colony();
		}
		
		System.out.println("Please enter the number of ants initially at each colony. (Greater than 0)");
		int initialAnts = getInt(0);
		int totalAnts = initialAnts * totalColonies;
		Ant[] ants = new Ant[totalAnts];
		
		for(int i = 0; i < ants.length; i++){ //Initializes the ants in ants[]
			ants[i] = new Ant(homes[i % totalColonies]);
		}
		
		System.out.println("Please enter the type of graph to use: \n 1. grid \n 2. file");
		boolean isGrid = (1 == getIntBetween(1,2));
		Graph graph;
		
		//Checks which additional information is needed.
		if(isGrid){
			System.out.println("Please enter the width of the graph. (Greater than 3)");
			int width = getInt(3);
			
			System.out.println("Please enter the depth of the graph. (Greater than 3)");
			int depth = getInt(3);
			
			graph = new Graph(width, depth, homes, sugarProb, averageSugar);
		} 	
		else {
			System.out.println("Please enter the name of the file storing the graph.");
			String filename = scanner.next();
			
			graph = new Graph(filename, homes, sugarProb, averageSugar);
		}
		
		simulator = new Simulator(graph, ants, carriedSugar, droppedPheromones);
		visualizer = new Visualizer(graph, isGrid, homes[0], ants);
	}
	
	/*
	* Returns an double from the user between min and max.
	* Precondition: min < max.
	*/
	private static double getDoubleBetween(double min,double max){
		double number;
		
		do {
			number = scanner.nextDouble();
		
			if(number > max || number < min){
				System.out.println("Please write a number between " + min + " and " + max + ".");
			}
		} while(number > max || number < min);
		
		return number;
	}
	
	/*
	* Returns an int from the user greater than or equal to min.
	*/
	private static int getInt(int min){
		int number;
		
		do {
			number = scanner.nextInt();
		
			if(number < min){
				System.out.println("Please write a number greater than " + min + ".");
			}
		} while(number < min);
		
		return number;
	}
	
	/*
	* Returns an int from the user between min and max.
	* Precondition: min < max.
	*/
	private static int getIntBetween(int min, int max){
		int number;
		
		do {
			number = scanner.nextInt();
		
			if(number < min || number > max){
				System.out.println("Please write a number between " + min + " and " + max + ".");
			}
		} while(number < min || number > max);
		
		return number;
	}
	
}