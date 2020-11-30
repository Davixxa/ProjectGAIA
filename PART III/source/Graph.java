public class Graph{

	//Instance variables.
	private Colony[] colonies;
	private double probability;
	private int averageSugar;
	
	//First constructor : new description later.
	public Graph(int width, int depth, Colony[] colonies, double probability, int averageSugar){
		
	}
	
	//Second constructor : new description later.
	public Graph(String filename, Colony[] colonies, double probability, int averageSugar){
		
	}
	
	//Getters and Setters
	
	//Contract methods
	
	//Returns the pheromone level between Node source and Node target.
	public int pheromoneLevel(Node source, Node target){
		
	}
	
	//Raises the amount of pheromones in the edge between source and target by given amount.
	public void raisePheromones(Node source, Node target, int amount){
		
	}
	
	//Returns all the Nodes adjacent to given node.
	public Node[] adjacentTo(Node node){
		
	}
	
	//Decreases the amount of pheromones in this by 1 unit and possibly spawns sugar in a random node.
	public void tick(){
		
	}
}