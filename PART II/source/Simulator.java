public class Simulator{
	
	//Instance variables
	Graph graph;
	Ant[] ants;
	int carriedSugar;
	int droppedPheromones;
	
	//Class Atributes
	
	//Constructor
	public Simulator(Graph graph, Ant[] ants, int carriedSugar, int droppedPheromones){
		this.graph = graph;
		this.ants = ants;
		this.carriedSugar = carriedSugar;
		this.droppedPheromones = droppedPheromones;
	}
	
	public void tick() {
		//First decrease all pheromone leves
		graph.tick();
		
		//Decisions for all Ants
		for(int i = 0; i < ants.length; i++){
			//Ants that arrived in previous tick must eat sugar
			if(ants[i].wasAtHome()){
				//er ikke helt sikker på denne implementering...
				Colony home = (Colony) ants[i].previous();
				home.hasStock() ? home.consume() : //ellers dø????
			} 
			//Make a move
			else { 
				//first description
				if(!ants[i].carrying() && ants[i].current().sugar() > 0){
					ants[i].current().decreaseSugar();
					ants[i].pickUpSugar();
				}
				
				//second description
				else if(graph.adjacentTo(ants[i].current()).length <= 1){
					ants[i].move(graph.adjacentTo(ants[i].current())[0]);
				}
				
				//third description
				else {
					Node[] posibleNodes = graph.adjacentTo(ants[i].current);
					double highestProbability = 0.0;
					int indexOfHighestProbability = 0;
					
					//Computing all the probalities and saving the best.
					for(int j = 0; j < posibleNodes.length; j++){
						//should not return to previous node
						if(!posibleNodes[j].equals(ants[i].previous())){
							int sumOfPheromones = 0;
		
							for(int z = 0; z > posibleNodes.length; z++){
								sumOfPheromones += graph.pheromoneLevel(ants[i].current(), posibleNodes[z]);
							}
							
							double probability = (graph.pheromoneLevel(ants[i], posibleNodes[j]) + 1.0) / ( sumOfPheromones + posibleNodes.length - 1.0 )
							
							if(probability >= highestProbability){
								highestProbability = probability;
								indexOfHighestProbability = j;
							}
						}
					}
					
					//Move the ant to the correct node
					ants[i].move(posibleNodes[indexOfHighestProbability]);
				}
				
				//fourth description
				if(ants[i].isAtHome() && ants[i].carrying){
					ants[i].dopSugar();
					ants[i].home().topUp(carriedSugar);
				}
			}
		}
	}	
}