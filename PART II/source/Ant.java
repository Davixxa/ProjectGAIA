public class Ant {
	//Instance variables
	private Colony home;
	private Node currentPosition;
	private Node previousPosition;
	private boolean isCarryingSugar;
	
	//Class variables
	
	
	//Constructor which takes a home Colony 
	public Ant(Colony home){
		this.home = home;
		currentPosition = home;
		previousPosition = home;
		isCarryingSugar = false;
	}
	
	//Getters and Setters is already described in contract.
	
	//Contract methods
	
	//Moves the Ant to the given location
	public void move(Node location){
		previousPosition = currentPosition;
		currentPosition = location;
	}
	
	//Returns the current position of this Ant
	public Node current(){
		return currentPosition;
	}
	
	//Returns the previous position of this Ant
	public Node previous(){
		return previousPosition;
	}
	
	//Returns the home Colony of this Ant
	public Colony home(){
		return home;
	}
	
	//Returns whether this Ant's current position is home
	public boolean isAtHome(){
		return currentPosition.equals(home);
	}
	
	//Returns whether this Ant's previous position is home
	public boolean wasAtHome(){
		return previousPosition.equals(home);
	}
	
	//Returns whether this Ant is carrying sugar
	public boolean carrying(){
		return isCarryingSugar;
	}
	
	public void pickUpSugar(){
		isCarryingSugar = true;
	}
	
	public void dropSugar(){
		isCarryingSugar = false;
	}
	
	
	//Default methods
}