import java.util.Scanner;

public class eksempel{
	
	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args){
		Colony[] homes = new Colony[5];
		for(int i=0; i<homes.length;i++){
			homes[i] = new Colony();
		}
		Graph graph = new Graph(16,16,homes,0.50,5);
		//Graph graph = new Graph("graph1",homes,0.50,5);
		Ant[] ants = new Ant[20];
		for(int i = 0; i < ants.length; i++){
			ants[i] = new Ant(homes[i % 5]);
		}
		Simulator simCity = new Simulator(graph,ants,20,1);
		
		Visualizer visualizer = new Visualizer(graph,true,homes[0],ants);
		visualizer.display();
		
		//do while loop så simuleringen kan køres i steps
		int metode = 10;
		boolean cont;
		
		do {	
			System.out.println("will you continue? true/false");
			cont = sc.nextBoolean();
			metode = metode - 1;
			
			simCity.tick();
			visualizer.update();
		} while(metode > 0 && cont);

		
	}
}