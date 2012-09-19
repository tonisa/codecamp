package ee.elisa.gamechannel.service;

import java.util.ArrayList;
import java.util.List;

import ee.elisa.gamechannel.model.PositionedShip;
import ee.elisa.gamechannel.model.HitCell;

public class PlayerGrid {

	// marks hitted cell in grid
	
	public static final int HIT = 99;
	
	public int size;
	public int[][] grid;

	public PlayerGrid(int size) {
		this.size = size;
		grid = new int[size][size];
	}

	public void drawOnGrid(PositionedShip ship) throws PlayerGridException {

		if (getGrid(ship.getCornerX(),ship.getCornerX()) > 0) {
			throw new PlayerGridException("ship's place is already taken");
		}

		checkBeginning(ship.getCornerX(), ship.getCornerY(), ship.isHorizontal());
		
		int delta = 0;
		try {
			for (delta = 0; delta < ship.getSize(); delta++) {
				if (ship.isHorizontal()) {
					canDraw(ship.getCornerX() + delta, ship.getCornerY(), ship.isHorizontal());
					setGrid(ship.getCornerX() + delta,ship.getCornerY(),ship.getSize());
				} else {
					canDraw(ship.getCornerX(), ship.getCornerY() + delta,ship.isHorizontal());
					setGrid(ship.getCornerX(),ship.getCornerY() + delta, ship.getSize());
				}
			}
		} catch (PlayerGridException e) {
			// clean grid from mispositioned ship
			for (int undo = delta-1; undo >= 0; undo--) {
				if (ship.isHorizontal()) {
					if (inGrid(ship.getCornerX()+ undo,ship.getCornerY())){
						setGrid(ship.getCornerX()+undo,ship.getCornerY(),0);
					}
				} else {
					if (inGrid(ship.getCornerX(),ship.getCornerY() + undo)){
						setGrid(ship.getCornerX(),ship.getCornerY() + undo,0); 
					}
				}
			}
			throw e;
		}
	}

	private void setGrid( int x, int y, int val){
		grid[y][x] = val;		
	}

	private int getGrid( int x, int y){
		return grid[y][x];		
	}

	private int getGridCond( int x, int y){
		if (inGrid(x,y)){
			return getGrid(x,y);
		}
		return 0;		
	}
	
	private void checkBeginning(int x, int y, boolean horizontal) throws PlayerGridException {

		int sum = 0;

		if (horizontal){
			sum += getGridCond(x-1,y); 
			sum += getGridCond(x-1,y-1); 
			sum += getGridCond(x-1,y+1); 
		} else {
			sum += getGridCond(x,y-1); 
			sum += getGridCond(x-1,y-1); 
			sum += getGridCond(x+1,y-1); 
		}
		if (sum > 0 ){
			throw new PlayerGridException("ships beginning coordinate collides");
		}
	}

	protected boolean canDrawB(int x, int y, boolean horizontal) {
		try {
			canDraw(x, y, horizontal);
		} catch (PlayerGridException e) {
			return false;
		}
		return true;
	}

	protected void canDraw(int x, int y, boolean horizontal)
			throws PlayerGridException {
		if (!inGrid(x, y)) {
			throw new PlayerGridException("ship is out of playground (" + x
					+ "," + y + ")");
		}

		int sum = getGrid(x,y);

		if (sum > 0) {
			throw new PlayerGridException("ship's place is already taken (" + x
					+ "," + y + ")");
		}

		if (horizontal) {
			// right
			sum += getGridCond(x + 1, y);
			// upper
			sum += getGridCond(x, y - 1);
			// lower
			sum += getGridCond(x, y + 1);
			// right lower
			sum += getGridCond(x + 1, y + 1);			
			// right upper
			sum += getGridCond(x + 1, y - 1);
		} else {
			sum += getGridCond(x, y + 1);
			sum += getGridCond(x - 1, y);
			sum += getGridCond(x + 1, y);
			sum += getGridCond(x + 1, y + 1);
			sum += getGridCond(x - 1, y + 1);
		}
		if (sum > 0) {
			throw new PlayerGridException("ship collides with other ship");
		}
	}

	protected boolean inGrid(int x, int y) {
		if (x < size && y < size && x >= 0 && y >= 0) {
			return true;
		}
		return false;
	}

	public int getSize() {
		return size;
	}
	
	protected boolean isHit(int x, int y){
		if (getGrid(x,y) > 0 && getGrid(x,y)<HIT){
			setGrid(x,y,HIT);
			return true;
		}
		return false;
	}
	
	public boolean shootAt(int x, int y) {
		if (inGrid(x,y) && isHit(x,y)){
			return true;
		}
		return false;
	}

	public List<HitCell> getCellHits() {
		List<HitCell> out = new ArrayList<HitCell>();
		for (int x = 0; x < getSize(); x++) {
			for (int y = 0; y < getSize(); y++) {
				if (getGrid(x,y) == HIT) {
					out.add(new HitCell(x, y));
				}
			}
		}
		return out;
	}
}