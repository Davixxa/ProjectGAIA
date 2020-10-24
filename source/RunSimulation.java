import java.util.Scanner;

public class RunSimulation {
	
	private static Scanner scanner = new Scanner(System.in);		
	private static Visualizer visualizer;
	private static Simulator simulator;
	private static Graph graph;
	
	public static void main(String[] args){
		welcomeMessage();
		
		System.out.println("Please enter the probability that a node will start as a node containing sugar. (Between 0 and 1.0)");
<<<<<<< Updated upstream
		double sugarProb = getDouble(0,1);
		
		System.out.println("Please enter the average amount of sugar in a node. (Greater than 0)");
		int averageSugar = getInt(0);
		
		System.out.println("Please enter the number of units of sugar that an ant can carry. (Greater than 0)");
		int carriedSugar = getInt(0);
		
		System.out.println("Please enter the number of units of pheromones dropped by an ant when passing through a node. (Greater than 0)");
		int droppedPheromones = getInt(0);
		
		System.out.println("Please enter the total number of ant colonies. (Stricly greater than 0)");
		int totalColonies = getInt(1);
		Colony[] homes = new Colony[totalColonies];
		for(int i = 0; i < homes.length; i++){
			homes[i] = new Colony();
		}
		
		System.out.println("Please enter the number of ants initially at each colony. (Greater than 0)");
		int initialAnts = getInt(0);
		int totalAnts = initialAnts * totalColonies;
		Ant[] ants = new Ant[totalAnts];
		for(int i = 0; i < ants.length; i++){
			ants[i] = new Ant(homes[i % totalColonies]);
		}
		
		System.out.println("Please enter the type of graph to use: \n 1. grid \n 2. file");
		boolean isGrid = (1 == getIntBetween(1,2));
		
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
		
		System.out.println("Please enter the total simulation time. (Greater than 0)");
		int totalSimulationTime = getInt(0);
		
		System.out.println("Please enter the desired viewing mode:  \n 1. textual summary \n 2. graphical representation");
		boolean isGraph = (2 == getIntBetween(1,2));
		
		//Checks if additional information is needed.
		if(!isGraph){
			System.out.println("Please enter the update frequency. (Stricly greater than 0)");
			int updateFrequency = getInt(1);
		}
		
		simulator = new Simulator(graph, ants, carriedSugar, droppedPheromones);
		visualizer = new Visualizer(graph, isGraph, homes[0], ants);
		
		visualizer.display();
		
		boolean stopProgram = false;
		
		//Loop to run the simulation
		while(totalSimulationTime > 0 && !stopProgram){
			System.out.println("Ticks left in simulation: " + totalSimulationTime);
			System.out.println("Would you like to continue the simulation? (true/false)");
			stopProgram = !scanner.nextBoolean();
			totalSimulationTime = totalSimulationTime - 1;
			simulator.tick();
			visualizer.update();
		}
		
=======
		double sugarProb = getDouble(0,1.0);
		 
>>>>>>> Stashed changes
		//File or grid
		/*Colony
			Total amount of colonies
		*/
		
		
		/*Ants
			Colony(Home)
		*/
		
		/*Graph(Grid)
			Width
			Depth
			Colonies[]
			Sugar probability
			Average sugar
		*/
			
		/*Graph(File)
			Filename
			Colonies[]
			Sugar probability
			Average sugar
		*/
		
		/*Simulator
			graph
			ants[]
			carried
			dropped pheromones
		*/
		
		/*Visualiser
			Graph
			isGrid
			Node(Start)
			Ants[]
		*/
		//Running the simulation
		
		
	}
	
	/*
	* This method prints the welcome screen when the program is opened
	*/
	private static void welcomeMessage(){
		
	}
	
	
	private static double getDouble(double min,double max){
		double number;
		
		do {
			Scanner sc = new Scanner(System.in);
			number = sc.nextDouble();
		
			if(number > max || number < min){
				System.out.println("Please write a number between " + min + " and " + max + ".");
			}
		} while(number > max || number < min);
		
		return number;
	}
	
	private static int getInt(int min){
		int number;
		
		do {
			Scanner sc = new Scanner(System.in);
			number = sc.nextInt();
		
			if(number < min){
				System.out.println("Please write a number greater than " + min + ".");
			}
		} while(number < min);
		
		return number;
	}
	
	private static int getIntBetween(int min, int max){
		int number;
		
		do {
			Scanner sc = new Scanner(System.in);
			number = sc.nextInt();
		
			if(number < min || number > max){
				System.out.println("Please write a number greater than " + min + ".");
			}
		} while(number < min || number > max);
		
		return number;
	}
	
}