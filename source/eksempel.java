public class eksempel{
	public static void main(String[] args){
		Colony[] homes = new Colony[1];
		homes[0] = new Colony();
		Graph graph = new Graph("graph1",homes,0.50,5);
		Ant[] ants = new Ant[1];
		ants[0] = new Ant(homes[0]);
		Simulator simCity = new Simulator(graph,ants,1,5);
		Node node = new Node(5);
		
		Visualizer visualizer = new Visualizer(graph,false,node,ants);
		visualizer.printStatus();
	}
}