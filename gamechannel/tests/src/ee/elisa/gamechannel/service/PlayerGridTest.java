package ee.elisa.gamechannel.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ee.elisa.gamechannel.model.PositionedShip;

public class PlayerGridTest {

	PlayerGrid grid;

	@Before
	public void setUp() throws Exception {
		grid = new PlayerGrid(10);
	}

	@Test
	public final void testGridBoundaries() {
		assertFalse(grid.inGrid(grid.getSize(), grid.getSize()));
		assertFalse(grid.inGrid(-1, -1));
		assertTrue(grid.inGrid(0, 0));
		assertTrue(grid.inGrid(grid.getSize()-1, grid.getSize()-1));
	}

	@Test
	public final void testCanDrawEmptyGrid() {
		assertTrue(grid.canDrawB(0, 0, true));
		assertTrue(grid.canDrawB(grid.getSize()-1, grid.getSize()-1, true));
		assertTrue(grid.canDrawB(grid.getSize()-1, 0, true));
		assertTrue(grid.canDrawB(0, grid.getSize()-1, true));
		
		assertFalse(grid.canDrawB(grid.getSize(), grid.getSize(), true));
		assertFalse(grid.canDrawB(-1, -1, true));
	}

	@Test
	public final void testCollisions() throws PlayerGridException {
		PositionedShip ship1 = new PositionedShip(5,1,1,true);
		PositionedShip ship11 = new PositionedShip(5,4,1,true);
		PositionedShip ship2 = new PositionedShip(5,3,3,true);
		PositionedShip ship3 = new PositionedShip(1,6,6,true);
		PositionedShip ship4 = new PositionedShip(1,7,6,true);
		PositionedShip ship5 = new PositionedShip(1,7,7,true);
		PositionedShip ship6 = new PositionedShip(1,5,5,true);
		
		grid.drawOnGrid(ship1);
		
		try {
			grid.drawOnGrid(ship1);
			fail("Succeeded to draw two ships on the same place");
		} catch (PlayerGridException e){			
		}

		try {
			grid.drawOnGrid(ship11);
			fail("Succeeded to draw two ships on the same place");
		} catch (PlayerGridException e){			
		}
		
		grid.drawOnGrid(ship2);

		grid.drawOnGrid(ship3);

		try {
			grid.drawOnGrid(ship4);
			fail("Succeeded to draw two ships without separating space");
		} catch (PlayerGridException e){			
		}
		
		try {
			grid.drawOnGrid(ship5);
			fail("Succeeded to draw two ships without separating space");
		} catch (PlayerGridException e){			
		}

		try {
			grid.drawOnGrid(ship6);
			fail("Succeeded to draw two ships with corner collision");
		} catch (PlayerGridException e){			
		}
		
	}
	
}
