package ee.elisa.gamechannel.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

// http://www.cigital.com/justice-league-blog/2009/08/14/proper-use-of-javas-securerandom/

public class Random {

	private SecureRandom random;
	
	public Random(){
		byte[] bytes = new byte[(int) (System.currentTimeMillis()%20)];
		random = new SecureRandom();
		random.nextBytes(bytes);
		// random = SecureRandom.getInstance("SHA1PRNG");		
	}
	
	public int getIntss(int min, int max){
		int val = random.nextInt();
		return min+(val%max);
	}
	
	 /**
     * Generate a random int value uniformly distributed between
     * <code>lower and upper, inclusive.
     *
     * @param lower
     *            the lower bound.
     * @param upper
     *            the upper bound.
     * @return the random integer.
     */
    public int getInt(int lower, int upper) {
        double r =random.nextDouble();
        return (int) ((r * upper) + ((1.0 - r) * lower) + r);
    }	
}
