import java.util.Scanner;

public class RunSimulation {
	
	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args){
		welcomeMessage();
		
		System.out.println("Please enter the probability that a node will start as a node containing sugar. (Between 0 and 1.0)");
		double sugarProb = getDouble(0,1);
		 
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
	
}