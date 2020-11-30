import java.util.Random;

/**
 * Utilities for the simulation.
 */
public class RandomUtils {

    private static Random generator = new Random();

    /**
     * Returns a random natural number uniformly distributed between 0 and n-1.
     * @param n the upper limit of the result
     * @return an integer between 0 (inclusive) and n (exclusive)
     */
    public static int randomInt(int n) {
	return generator.nextInt(n);
    }
    
    /**
     * Returns a random natural number with Poisson distribution and expected value n.
     * @param n the expected value of the distribution
     * @return an integer with Poisson distribution and expected value n
     */
    public static int randomPoisson(int n) {
	// Knuth's algorithm for Poisson distribution
	double l = Math.exp(-n);
	int k = 0;
	double p = 1;
	while (p > l) {
	    k = k+1;
	    double u = generator.nextDouble();
	    p = p*u;
	}
	return k-1;
    }

    /**
     * Flips a coin with a given probability.
     * @param probability the probability of success
     * @return {@code true} with probability {@code probability}
     */
    public static boolean coinFlip(double probability) {
	return (generator.nextDouble() < probability);
    }

    /*
    public static void main(String args[]) {
	int k = 0;
	for (int i=0; i<500; i++)
	    k = k + getRandomValue(25);
	System.out.println(k/500.0);
    }
    */
}
