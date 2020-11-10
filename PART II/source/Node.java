public class Node{
  
    private int sugarLevel;

    /**
      * Constructor taking no arguments, initialising the node with a the default sugar level of 0.
      */
    public Node() {
        this.sugarLevel = 0;
    }

    /**
      * Second Constructor taking one argument, initialising the node with the sugar level provided in the variable sugar.
      */
    public Node(int sugar) {
        this.sugarLevel = sugar;
    }

    /**
      * Returns the amount of sugar in the current node.
      */
    public int sugar() {

        return sugarLevel;

    }


    /**
      * Decreases the amount of sugar in the current node by one unit unless there's no sugar in the current node
      */
    public void decreaseSugar() {

        this.sugarLevel = this.sugarLevel - 1;

    }


    /**
      * Sets the value of sugar in the node to a given value sugar.
      */
    public void setSugar(int sugar) {
        this.sugarLevel = sugar;
    }


}