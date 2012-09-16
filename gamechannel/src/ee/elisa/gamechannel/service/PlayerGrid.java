package ee.elisa.gamechannel.service;

import ee.elisa.gamechannel.model.PositionedShip;

public class PlayerGrid {
	public int size;
	public int[][] grid;

	public PlayerGrid(int size) {
		this.size = size;
		grid = new int[size][size];
	}

	public void drawOnGrid(PositionedShip ship) throws PlayerGridException {

		if (grid[ship.getCornerX()][ship.getCornerY()] > 0) {
			throw new PlayerGridException("ship's place is already taken");
		}

		checkBeginning(ship.getCornerX(), ship.getCornerY(), ship.isHorizontal());
		
		int i = 0;
		try {
			for (i = 0; i < ship.getSize(); i++) {
				if (ship.isHorizontal()) {
					canDraw(ship.getCornerX() + i, ship.getCornerY(),
							ship.isHorizontal());
					grid[ship.getCornerX() + i][ship.getCornerY()] = 1;
				} else {
					canDraw(ship.getCornerX(), ship.getCornerY() + i,
							ship.isHorizontal());
					grid[ship.getCornerX()][ship.getCornerY() + i] = ship
							.getSize();
				}
			}
		} catch (PlayerGridException e) {
			// clean grid from mispositioned ship
			for (int undo = i; undo >= 0; undo--) {
				if (ship.isHorizontal()) {
					if (inGrid(ship.getCornerX()+ undo,ship.getCornerY())){
						grid[ship.getCornerX() + undo][ship.getCornerY()] = 0;
					}
				} else {
					if (inGrid(ship.getCornerX(),ship.getCornerY() + undo)){
						grid[ship.getCornerX()][ship.getCornerY() + undo] = 0;
					}
				}
			}
			throw e;
		}
	}

	private void checkBeginning(int x, int y, boolean horizontal) throws PlayerGridException {

		int sum = 0;

		if (horizontal){
			if (inGrid(x-1, y)){
				sum += grid[x-1][y]; 
			}
			if (inGrid(x-1, y-1)){
				sum += grid[x-1][y-1]; 
			}
			if (inGrid(x-1, y+1)){
				sum += grid[x-1][y+1]; 
			}
		} else {
			if (inGrid(x, y-1)){
				sum += grid[x][y-1]; 
			}
			if (inGrid(x-1, y-1)){
				sum += grid[x-1][y-1]; 
			}
			if (inGrid(x+1, y-1)){
				sum += grid[x+1][y-1]; 
			}
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

		int sum = grid[x][y];

		if (sum > 0) {
			throw new PlayerGridException("ship's place is already taken (" + x
					+ "," + y + ")");
		}

		if (horizontal) {
			// right
			if (inGrid(x + 1, y)) {
				sum += grid[x + 1][y];
			}
			// upper
			if (inGrid(x, y - 1)) {
				sum += grid[x][y - 1];
			}
			// lower
			if (inGrid(x, y + 1)) {
				sum += grid[x][y + 1];
			}
			// right lower
			if (inGrid(x + 1, y + 1)) {
				sum += grid[x + 1][y + 1];
			}
			// right upper
			if (inGrid(x + 1, y - 1)) {
				sum += grid[x + 1][y - 1];
			}
		} else {
			if (inGrid(x, y + 1)) {
				sum += grid[x][y + 1];
			}
			if (inGrid(x - 1, y)) {
				sum += grid[x - 1][y];
			}
			if (inGrid(x + 1, y)) {
				sum += grid[x + 1][y];
			}
			if (inGrid(x + 1, y + 1)) {
				sum += grid[x + 1][y + 1];
			}
			if (inGrid(x - 1, y + 1)) {
				sum += grid[x - 1][y + 1];
			}
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
		if (grid[x][y] > 0 && grid[x][y]<999){
			grid[x][y] = 999;
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

}