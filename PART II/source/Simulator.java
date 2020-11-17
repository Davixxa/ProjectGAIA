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
			if(ants[i] != null){
				System.out.println("Ant " + i " alive");
				if(ants[i].isAtHome()){
					System.out.println("Ant " + i + " home");
					if(ants[i].home().hasStock())
						ants[i].home().consume();
						System.out.println("Ant " + i + " has eaten at home");
					
					else
						ants[i] = null;
						System.out.println("Ant " + i + " dead");
				}
				
				else if((!ants[i].carrying()) && (ants[i].current().sugar()>0)){
					ants[i].current().decreaseSugar();
					ants[i].pickUpSugar();
					ants[i].move(ants[i].previous());
					System.out.println("Ant " + i + " picked up sugar");
				}
				
				if(graph.adjacentTo(ants[i].current()).length == 1){
					ants[i].move(graph.adjacentTo(ants[i].current())[0]);
					System.out.println("Ant " + i + " has one neighbour");
				}
				
				else{
					ants[i].move(movePicker(ants[i]));
				}
				
				if(ants[i].current() == ants[i].home() && ants[i].carrying()){
					ants[i].home().topUp(sugarCarried);
					ants[i].dropSugar();
					System.out.println("Ant " + i + " dropped sugar at home");
				}
			}
		}
	}
	
	/*
	*This method handles the process of picking the node the given ant will move to
	*/
	private Node movePicker(Ant ant){
		
		System.out.println("current ant is deciding move");
		Node currentNode = ant.current();
		int sumOfPheromones = 0;
		Node[] neighbours = graph.adjacentTo(currentNode);
		
		for(int i = 0; i < neighbours.length; i++){
			if(!neighbours[i].equals(ant.previous()))
				sumOfPheromones = sumOfPheromones + graph.pheromoneLevel(currentNode, neighbours[i]);
		}
		
		Node move = currentNode;
		boolean coinFlip = false;
		int i = 0;
		
		while(i < neighbours.length && !coinFlip){
			if(!neighbours[i].equals(ants[i].previous())){
				double probability = (graph.pheromoneLevel(currentNode, neighbours[i])) / (sumOfPheromones + (neighbours.length - 1.0));
				coinFlip = RandomUtils.coinFlip(probability);
				System.out.println("Coin flipped at probability: " + probability + " Result: " + coinFlip);
				if (coinFlip){
					move = neighbours[i];
				}
			}
			
			i = i + 1;
		}
		
		return move;
	}
}