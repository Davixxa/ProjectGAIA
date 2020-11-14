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
		graph.tick;
		
		for(int i = 0;i < ants.length;i++){
			Ant currentAnt = ants[i];
			
			if(currentAnt.isAtHome()){
				if(currentAnt.home().hasStock())
					currentAnt.home().consume();
				else
					/*ant=dø*/
			}
			
			else if((!currentAnt.carrying()) && (currentAnt.current().sugar()>0)){
				currentAnt.current().decreaseSugar();
				currentAnt.pickUpSugar();
				currentAnt.move(currentAnt.previous());
			}
			
			else if(graph.adjacentTo(currentAnt.current()).length == 1){
				currentAnt.move(graph.adjacentTo(currentAnt.current())[0]);
			}
			
			else{
				currentAnt.move(movePicker(currentAnt.current()));
			}
		}
	}
}