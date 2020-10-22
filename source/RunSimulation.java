import java.util.Scanner;

public class RunSimulation {
	
	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args){
		welcomeMessage();
		
		System.out.println("Please enter the probability that a node will start as a node containing sugar. (Between 0 and 1.0)");
		
		Colony[] colony = 
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
	
	
	private static int getNormalNumber(){
		int number = 0;
		do {
			Scanner sc = new Scanner(System.in);
			number = sc.nextInt();
		
		if(number > 4000 || number < 1){
			System.out.println("Skriv venligst et input mellem 1 og 4000");
		}
		} while(number > 4000 || number < 1);
		
		return number;
	}
	
}