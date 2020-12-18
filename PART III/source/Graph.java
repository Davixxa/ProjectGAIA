import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

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
	* PRECONDITION: width and depth > 2
	*/
	public Graph(int width, int depth, Colony[] homes, double sugarProbability, int avgSugar){
		this.sugarProbability = sugarProbability;
		this.avgSugar = avgSugar;
		this.nodes = new Node[width*depth];
		
		//initializing colonies
		for(int i = 0; i < homes.length; i++){
			int place;
			do{
				//Finds a random place for the colony
				place = RandomUtils.randomInt(nodes.length);
			}while(nodes[place] != null);
			nodes[place] = homes[i];
		}
		
		//initializing nodes
		for(int i = 0; i < nodes.length; i++){
			//initializing the places that are not a Colony
			if(nodes[i] == null){
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
			if(i+width <= nodes.length-1)
				tempEdge.add(new Edge(nodes[i], nodes[i+width]));
				
			//To the rigth
			if(i%width < width-1)
				tempEdge.add(new Edge(nodes[i], nodes[i+1]));
		}
		
		this.edges = tempEdge.toArray(new Edge[0]);
	}
	
	/*
	* Constructor for reading from a file
	* PRECONDITION: File must be well-formed
	*/
	public Graph(String filename, Colony[] homes, double sugarProbability, int avgSugar){
		File file = new File(filename);
		try{
			Scanner scanner = new Scanner(file);
			
			this.nodes = new Node[scanner.nextInt()];
			
			//Initalizing colonies
			for(int i = 0; i < homes.length; i++){
				System.out.println(i);
				nodes[scanner.nextInt()-1] = homes[i];
			}
			
			//Initalizing nodes
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
				tempEdge.add(new Edge(nodes[scanner.nextInt()-1],nodes[scanner.nextInt()-1]));
			
			this.edges = tempEdge.toArray(new Edge[0]);
		}
		catch(FileNotFoundException e){
			System.out.println("The file does not exist");
			System.exit(0);
		}
	}
	
	//Contract methods
	
	/*
	 * Returns the pheromone level between source and target.
	 * PRECONDITION: Edge between source and target must exist
	 */
	public int pheromoneLevel(Node source, Node target){
		return edgeFinder(source, target).pheromones();
	}
	
	/* 
	 * Raises the pheromone level between source and target.
	 * PRECONDITION: Edge between source and target must exist
	 */
	
	public void raisePheromones(Node source, Node target, int amount){
		edgeFinder(source, target).raisePheromones(amount);
	}
	
	//Returns all adjacent nodes to node
	public Node[] adjacentTo(Node node){
		ArrayList<Node> tempNode = new ArrayList<>();
		
		for(int i = 0; i < edges.length; i++){
			//valid node if edge between either source or target and this node.
			if(edges[i].source() == node)
				tempNode.add(edges[i].target());
			else if(edges[i].target() == node)
				tempNode.add(edges[i].source());
		}
		
		Node[] nodes = tempNode.toArray(new Node[0]);
				
		return nodes;
	}
	
	//Decreases pheromones in all edges and maybe spawns sugar in node.
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
	}
	
	//Auxillary methods
	
	//Returns the edge between source and target.
	private Edge edgeFinder(Node source, Node target){
		Edge edge = null;
		int i = 0;
		boolean found = false;
		
		//Loops until an edge has been found between source and target, checks both ways
		while(!found && i < edges.length){
			if(edges[i].source() == source || edges[i].target() == source)
				if(edges[i].source() == target || edges[i].target() == target){
					edge = edges[i];
					found = true;
				}
			i++;
		}
				
		return edge;
	}	
}