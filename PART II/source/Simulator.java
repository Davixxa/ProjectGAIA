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
	
	public void tick(){
		graph.tick();
		
		for(int i = 0;i < ants.length;i++){
			Ant currentAnt = ants[i];
			if(currentAnt != null){
				if(currentAnt.isAtHome()){
					if(currentAnt.home().hasStock())
						currentAnt.home().consume();
					
					else
						ants[i] = null;
				}
				
				else if((!currentAnt.carrying()) && (currentAnt.current().sugar()>0)){
					currentAnt.current().decreaseSugar();
					currentAnt.pickUpSugar();
					currentAnt.move(currentAnt.previous());
				}
				
				if(graph.adjacentTo(currentAnt.current()).length == 1){
					currentAnt.move(graph.adjacentTo(currentAnt.current())[0]);
				}
				
				else{
					currentAnt.move(movePicker(currentAnt));
				}
				
				if(currentAnt.current() == currentAnt.home() && currentAnt.carrying()){
					currentAnt.home().topUp(sugarCarried);
					currentAnt.dropSugar();
				}
			}
		}
	}
	
	/*
	*This method handles the process of picking the node the given ant will move to
	*/
	private Node movePicker(Ant currentAnt){
		Node currentNode = currentAnt.current();
		int sumOfPheromones = 0;
		Node[] neighbours = graph.adjacentTo(currentNode);
		
		for(int i = 0; i < neighbours.length; i++){
			if(!neighbours[i].equals(currentAnt.previous()))
				sumOfPheromones = sumOfPheromones + graph.pheromoneLevel(currentNode, neighbours[i]);
		}
		
		Node move = currentNode;
		boolean coinFlip = false;
		int i = 0;
		
		while(i < neighbours.length && !coinFlip){
			if(!neighbours[i].equals(currentAnt.previous())){
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