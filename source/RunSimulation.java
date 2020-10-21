

public class RunSimulation {

	public static void main(String[] args){
		//welcomeMessage();
		//parameter
		Colony[] homes = new Colony[1];
		Graph graph = new Graph(2,2,homes,0.50,5);
		Ant[] ants = new Ant[1](homes[0]);
		Simulator simCity = new Simulator(graph,ants,1,5,);
		Node node = new Node(5);
		
		Visualizer visualizer = new Visualizer(graph,true,node,ants);
	}
}