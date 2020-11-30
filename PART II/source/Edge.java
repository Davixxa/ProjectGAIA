public class Edge{
	//Instance variables
	private Node source;
	private Node target;
	private int pheromones;
	
	/*
	*Constructor for the edge:
	*Creates a new edge between source and target with a pheromone level of 0
	*/
	public Edge(Node source, Node target){
		this.source = source;
		this.target = target;
		this.pheromones = 0;
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
	*Decreases the pheromone level by one
	*/
	public void decreasePheromones(){
		if(this.pheromones > 0)
			this.pheromones = this.pheromones - 1;
	}
	
	/*
	*Increases the pheromone level by the given amount
	*PRECONDITION:amount>0
	*/
	public void raisePheromones(int amount){
		this.pheromones = this.pheromones + amount;
	}
}