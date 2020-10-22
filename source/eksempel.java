import java.util.Scanner;

public class eksempel{
	public static void main(String[] args){
		Colony[] homes = new Colony[5];
		for(int i=0; i<homes.length;i++){
			homes[i] = new Colony();
		}
		Graph graph = new Graph(4,4,homes,0.50,5);
		Ant[] ants = new Ant[20];
		for(int i = 0; i < ants.length; i++){
			ants[i] = new Ant(homes[i % 5]);
		}
		Simulator simCity = new Simulator(graph,ants,20,1);
		
		Visualizer visualizer = new Visualizer(graph,true,(Node) homes[0],ants);
		visualizer.display();
		
		
		//do while loop så simuleringen kan køres i steps
		int metode = 1;
		do {
		simCity.tick();
		visualizer.update();
			
			
		Scanner sc = new Scanner(System.in);
		metode = sc.nextInt();
		
		if(metode > 0){
			System.out.println("will you continue press 1 if true and 0 if false");
		}
		} while(metode > 0);

		
	}
}