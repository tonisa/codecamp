package ee.elisa.gamechannel.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RandomTest {

	Random rnd;
	
	@Before
	public void setUp() throws Exception {
		rnd = new Random();
	}

	@Test
	public void testMax() {
		for(int max=1;max<10;max++){
			for(int i=0;i<100;i++){
				int val = rnd.getInt(0, max); 
				assertTrue("random value is bigger than allowed - max:"+max+" rnd:"+val,val<=max);
				System.out.println("max:"+max+" rnd:"+val);
			}
		}
	}

	@Test
	public void testMin() {
		for(int min=0;min<5;min++){
			for(int i=0;i<100;i++){
				int val = rnd.getInt(min, 10); 
				assertTrue("random value is smaller than allowed - min:"+min+" rnd:"+val,val>=min);
				System.out.println("min:"+min+" rnd:"+val);
			}
		}
	}
	
}
