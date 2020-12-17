public class Colony extends Node{
	
	//Instance variable
	private int sugar;

	//Constructor
	public Colony(){
		this.sugar = 0;
	}
	
	//No getters and setters implemented.
	
	//Increases this.sugar with given sugar.
	public void topUp(int sugar){
		this.sugar = this.sugar + sugar;
	}
	
	//Consumes 1 unit of sugar
	public void consume(){
		this.sugar = this.sugar - 1;
	}
	
	//Checks if the Colony has any sugar
	public boolean hasStock(){
		return this.sugar > 0;
	}
	
	//No standard methods implemented.
}