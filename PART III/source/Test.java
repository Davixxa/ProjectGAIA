import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class Test{
	public static void main(String[] args) throws FileNotFoundException{
		Scanner scanner = new Scanner(new File("graph1"));
		System.out.println(scanner.nextLine());
		String[] colonies = scanner.nextLine().split(" ");
		for(int i = 0; i < colonies.length; i++){
			System.out.println(colonies[i]);
		}
	}
}