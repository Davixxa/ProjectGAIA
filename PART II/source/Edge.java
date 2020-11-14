public class Edge{
	//Instance variables
	private Node source;
	private Node target;
	private double pheromones;
	
	/*
	*Constructor for the edge:
	*Creates a new edge between n1 and n2 with a pheromone level of 0
	*PRECONDITIONS: n1 != n2
	*/
	public Edge(Node n1, Node n2){
		source = n1;
		target = n2;
		pheromones = 0;
	}
	
	//Contract methods
	
	/*
	*Getter for the source node
	*/
	public Node source(){
		return source;
	}
	
	/*
	*Getter for the target node
	*/
	public Node target(){
		return target;
	}
	
	/*
	*Getter for the current level of pheromones
	*/
	public int pheromones(){
		return pheromones;
	}
	
	/*
	* Decreases the pheromone level by one
	*/
	public void decreasePheromones(){
		pheromones = pheromones - 1;
	}
	
	/*
	*Increases the pheromone level by the given amount
	*PRECONDITION: amount >= 0
	*/
	public void raisePheromones(int amount){
		pheromones = pheromones + amount;
	}
	
}