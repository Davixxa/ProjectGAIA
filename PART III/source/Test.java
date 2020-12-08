import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class Test{
	public static void main(String[] args){
		Colony[] homes = new Colony[2];
		
		Graph graph = new Graph(5,5,homes,0.5,2);
		graph.adjacentTo(homes[0]);
	}
}