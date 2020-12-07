import java.util.Scanner;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;

public class Graph{
	//Instance variables
	private Node[] nodes;
	private Edge[] edges;
	private double sugarProbability,
	private int avgSugar;
	//Class attributes
	
	//Constructors
	
	/*
	* Constructor for a grid graph
	*/
	public Graph(int width, int depth, Colony[] homes, double sugarProbability, int avgSugar){
		this.sugarProbability = sugarProbability;
		this.avgSugar = avgSugar;
		this.nodes = new Node[width*depth];
		
		//initializing nodes
		int placedHomes = 0;
		for(int i = 0; i < nodes.length; i++){
			if(RandomUtils.coinFlip((homes.length - placedHomes)/(nodes.length-i)))
				nodes[i] = homes[placedHomes];
			else{
				//Initalizes the nodes and decides if there is sugar
				if(coinflip(sugarProbalility))
					nodes[i] = new Node(RandomUtils.randomPoisson(avgSugar));
				else
					nodes[i] = new Node();
			}
		}
		//Creating the edges
		ArrayList<Edge> tempEdge = new ArrayList<>();
		
		for(int i = 0; i < nodes.length; i++){
			//Below
			if(!(i+width > nodes.length-1)
				tempEdge.add(new Edge(nodes[i],nodes[i+width]));
				
			//To the rigth
			if(i%width != 0)
				tempEdge.add(new Edge(nodes[i],nodes[i+1]));
		}
		this.edges = tempEdge.toArray();
	}
	
	/*
	* Constructor for reading from a file
	* PRECONDITION: File must exist
	*/
	public Graph(String filename, Colony[] homes, double sugarProbability, int avgSugar){
		Scanner scanner = new Scanner(new File(filename));
		//Initializing nodes
		this.nodes = new Node[scanner.nextInt()];
		for(int i = 0; i < homes.length; i++){
			nodes[scanner.nextInt()] = homes[i];
		}
		
		for(int i = 0; i < nodes.length; i++){
			if(nodes[i] == null){
				//Initalizes the rest of the nodes and decides if there is sugar
				if(coinflip(sugarProbalility))
					nodes[i] = new Node(RandomUtils.randomPoisson(avgSugar));
				else
					nodes[i] = new Node();
			}
		}
		
		//Creating the edges
		ArrayList<Edge> tempEdge = new ArrayList<>();
		
		while(scanner.hasNextInt())
			tempEdge.add(new Edge(nodes[scanner.nextInt()],nodes[scanner.nextInt()]);
		
		this.edges = tempEdge.toArray();
	}
	
	//Contract methods
	
	public int pheromoneLevel(Node source, Node target){
		
	}
	
	public void raisePheromones(Node source, Node target, int amount){
		
	}
	
	public Node[] adjacentTo(Node node){
		
	}
	
	public void tick(){
		
	}
	
}