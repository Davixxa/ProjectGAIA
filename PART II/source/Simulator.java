public class Simulator{
	
	//Instance variables
	private Graph graph;
	private Ant[] ants;
	private int sugarCarried;
	private int droppedPheromones;
	
	public Simulator(Graph graph,Ant[] ants,int sugarCarried,int droppedPheromones){
		this.graph = graph;
		this.ants = ants;
		this.sugarCarried = sugarCarried;
		this.droppedPheromones = droppedPheromones;
	}
	
	/*
	*
	*/
	public void tick(){
		graph.tick();
		
		for(int i = 0;i < ants.length;i++){
			//Making sure the ant is alive
			if(ants[i] != null){
				//This part is for the ants that arrived home in the previous tick
				if(ants[i].isAtHome() && !ants[i].wasAtHome()){
					//if the ant can eat
					if(ants[i].home().hasStock()){
						ants[i].home().consume();
						//The ant moves from home to home to represent it spending a tick in this node
						ants[i].move(ants[i].current());
					}
					//If there is no food in the colony
					else{
						ants[i] = null;
					}
				}
				
				//This part is for the ants that did not arrive home in the previous tick
				else{
					//If there is sugar in the current node the ant will pick it up and it will move to its previous location
					boolean pickedUpSugar = false;
					if(!ants[i].carrying() && (ants[i].current().sugar() > 0)){
						ants[i].current().decreaseSugar();
						ants[i].pickUpSugar();
						pickedUpSugar = true;
					}
					//If the ant picked up sugar on this tick it will move to its previous location
					if(pickedUpSugar)
						ants[i].move(ants[i].previous());
					//If there is only one neighboring node the ant will move there
					else if(graph.adjacentTo(ants[i].current()).length == 1){
						ants[i].move(graph.adjacentTo(ants[i].current())[0]);
						graph.raisePheromones(ants[i].current(),ants[i].previous(),this.droppedPheromones);
					}
					//Else move is decided by movePicker
					else{
						ants[i].move(movePicker(ants[i]));
						graph.raisePheromones(ants[i].current(),ants[i].previous(),this.droppedPheromones);
					}
					//If it arrived home the ant will drop its sugar
					if(ants[i].isAtHome() && ants[i].carrying()){
						ants[i].dropSugar();
						ants[i].home().topUp(sugarCarried);
					}
				}
			}
		}
	}
	
	/*
	*This method handles the process of picking the node the given ant will move to
	*PRECONDITION: There must be more than one neighboring node
	*/
	private Node movePicker(Ant ant){
		
		Node currentNode = ant.current();
		int sumOfPheromones = 0;
		Node[] neighbours = graph.adjacentTo(currentNode);
		
		//Counts the total pheromone level in the edges to the availible 
		for(int i = 0; i < neighbours.length; i++){
			if(!neighbours[i].equals(ant.previous()))
				sumOfPheromones = sumOfPheromones + graph.pheromoneLevel(currentNode, neighbours[i]);
		}
		

		Node move = currentNode;
		boolean coinFlip = false;
		int i = 0;
		
		//makes a coinflip with given probability for each node, stops when a satisfied flip has been thrown.
		while(i < neighbours.length && !coinFlip){
			//to ensure that the ant doesn't go its previous node.
			if(!neighbours[i].equals(ants[i].previous())){
				//Calculates the probalitity with given formula
				double probability = (graph.pheromoneLevel(currentNode, neighbours[i])) / (sumOfPheromones + (neighbours.length - 1.0));
				coinFlip = RandomUtils.coinFlip(probability);
				if (coinFlip){
					move = neighbours[i];
				}
			}
			
			i = i + 1;
		}
		
		return move;
	}
}