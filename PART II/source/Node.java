public class Node{
	
	//Instance variables
    private int sugar;

	/*
    *Constructor taking one argument, initialising the node with the sugar level provided in the variable sugar.
	*PRECONDITION:sugar>=0
    */
    public Node(int sugar) {
        this.sugar = sugar;
    }
	
	/*
    *Second constructor taking no arguments, initialising the node with a the default sugar level of 0.
	*/
    public Node() {
        this(0);
    }
	
	/*
	*Returns the amount of sugar in the current node.
	*/
    public int sugar() {
        return sugar;
    }

    /*
    *Decreases the amount of sugar in the current node by one unit unless there's no sugar in the current node
	*PRECONDITION:sugar>0
    */
    public void decreaseSugar() {
        this.sugar = this.sugar - 1;
    }


    /*
    *Sets the value of sugar in the node to a given value sugar.
	*PRECONDITION:sugar>=0
    */
    public void setSugar(int sugar) {
        this.sugar = sugar;
    }


}