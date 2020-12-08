import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.NumberFormatException;

public class Graph{
	//Instance variables
	private Node[] nodes;
	private Edge[] edges;
	private double sugarProbability;
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
				if(RandomUtils.coinFlip(sugarProbability))
					nodes[i] = new Node(RandomUtils.randomPoisson(avgSugar));
				else
					nodes[i] = new Node();
			}
		}
		//Creating the edges
		ArrayList<Edge> tempEdge = new ArrayList<>();
		
		//The last node does not connect to anything as source.
		for(int i = 0; i < nodes.length - 1; i++){
			//Below
			if(!(i+width > nodes.length-1))
				tempEdge.add(new Edge(nodes[i], nodes[i+width]));
				
			//To the rigth
			if(i%width != 0)
				tempEdge.add(new Edge(nodes[i], nodes[i+1]));
		}
		this.edges = tempEdge.toArray(new Edge[0]);
		
		System.out.println("TEST GRID");
	}
	
	/*
	* Constructor for reading from a file
	* PRECONDITION: File must be well-formed
	*/
	public Graph(String filename, Colony[] homes, double sugarProbability, int avgSugar){
		try{
			File file = new File(filename);
			Scanner scanner = new Scanner(file);
			
			//Initializing nodes
			this.nodes = new Node[scanner.nextInt()];
			for(int i = 0; i < homes.length; i++){
				nodes[scanner.nextInt()] = homes[i];
			}
			
			for(int i = 0; i < nodes.length; i++){
				if(nodes[i] == null){
					//Initalizes the rest of the nodes and decides if there is sugar
					if(RandomUtils.coinFlip(sugarProbability))
						nodes[i] = new Node(RandomUtils.randomPoisson(avgSugar));
					else
						nodes[i] = new Node();
				}
			}
			
			//Creating the edges
			ArrayList<Edge> tempEdge = new ArrayList<>();
			
			while(scanner.hasNextInt())
				tempEdge.add(new Edge(nodes[scanner.nextInt()],nodes[scanner.nextInt()]));
			
			this.edges = (Edge[]) tempEdge.toArray();
			}
		catch(FileNotFoundException e){
			System.out.println("The file does not exist");
			System.exit(0);
		}
	}
	
	//Contract methods
	
	public int pheromoneLevel(Node source, Node target){
		return edgeFinder(source, target).pheromones();
	}
	
	public void raisePheromones(Node source, Node target, int amount){
		edgeFinder(source, target).raisePheromones(amount);
	}
	
	public Node[] adjacentTo(Node node){
		ArrayList<Node> tempNode = new ArrayList<>();
		
		for(int i = 0; i < edges.length; i++){
			if(edges[i].source() == node)
				tempNode.add(edges[i].target());
			else if(edges[i].target() == node)
				tempNode.add(edges[i].source());
		}
		
		System.out.println("TEST ADJACENTTO");
		Node[] nodes = tempNode.toArray(new Node[0]);
		
		for(int i = 0; i < nodes.length; i++){
			System.out.println(nodes[i]);
		}
				
		return nodes;
	}
	
	public void tick(){
		//Decreases pheromones in all edges that have some
		for(int i = 0; i < edges.length; i++){
			edges[i].decreasePheromones();
		}
		//Finds out if sugar will spawn in a node this tick
		if(RandomUtils.coinFlip(sugarProbability)){
			//Finds the node where the sugar will spawn
			int chosenNode;
			do{
				chosenNode = RandomUtils.randomInt(nodes.length);
			}while(nodes[chosenNode] instanceof Colony);
			//Increases the amount of sugar in the chosen Node
			nodes[chosenNode].setSugar(nodes[chosenNode].sugar() + RandomUtils.randomPoisson(avgSugar));
		}
		
		System.out.println("TEST TICK");
	}
	
	//Auxillary methods
	private Edge edgeFinder(Node source, Node target){
		Edge edge = null;
		int i = 0;
		boolean found = false;
		while(!found && i < edges.length){
			if(edges[i].source() == source || edges[i].target() == source)
				if(edges[i].source() == target || edges[i].target() == target){
					edge = edges[i];
					found = true;
				}
			i++;
		}
		
		System.out.println("TEST EDGEFINDER");
		System.out.println(edge != null);
				
		return edge;
	}	
}