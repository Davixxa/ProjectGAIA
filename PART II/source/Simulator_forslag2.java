public class Simulator{
	//Instance variables
	private Graph graph;
	private Ant[] ants;
	private int droppedPheromones;
	private int carriedSugar;
	
	public Simulator(Graph graph,Ant[] ants,int droppedPheromones,int carriedSugar){
		this.graph = graph;
		this.ants = ants;
		this.droppedPheromones = droppedPheromones;
		this.carriedSugar = carriedSugar;
	}
	
	public void tick(){
		graph.tick();
		for(int i = 0;i < ants.length;i++){
			if(ants[i].isAthome()){
				
			}
		}
	}
}