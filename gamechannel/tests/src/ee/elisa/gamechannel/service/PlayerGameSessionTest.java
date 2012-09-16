package ee.elisa.gamechannel.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PlayerGameSessionTest {

	PlayerGameSession session = null;

	@Before
	public void setUp() throws Exception {
		session = new PlayerGameSession("name", 10);
	}

	@Test
	public final void testcreateRandomShips() throws PlayerGridException {
		session.createRandomShips();
	}

}
