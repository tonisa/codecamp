package ee.elisa.gamechannel.service;

import java.util.ArrayList;
import java.util.List;

public class PlayerGrid {

	int size;
	int[][] grid;

	List<PositionedShip> ships;

	boolean shipsPlaced;
	boolean hasError;

	public PlayerGrid(int size) {
		shipsPlaced = false;
		this.size = size;
		grid = new int[size][size];
		ships = new ArrayList<PositionedShip>();
	}

	public void addShip(PositionedShip ship) throws PlayerGridException {
		try {
			verifyShipTypes(ship);
			drawOnGrid(ship);
			ships.add(ship);
			if (ships.size() == 8) {
				this.setShipsPlaced(true);
			}
		} catch (PlayerGridException e) {
			setError(true);
			throw e;
		}
	}

	private void verifyShipTypes(PositionedShip ship)
			throws PlayerGridException {
		if (ships.size() > 8) {
			throw new PlayerGridException("Too many ships added to playground");
		}
	}

	private void drawOnGrid(PositionedShip ship) throws PlayerGridException {

		for (int i = 0; i < ship.size; i++) {
			if (ship.horizontal) {
				canDraw(ship.cornerX + i, ship.cornerY, ship.horizontal);
				grid[ship.cornerX + i][ship.cornerY] = 1;
			} else {
				canDraw(ship.cornerX, ship.cornerY + i, ship.horizontal);
				grid[ship.cornerX][ship.cornerY + i] = ship.size;
			}
		}
	}

	private void canDraw(int x, int y, boolean horizontal)
			throws PlayerGridException {
		if (!inGrid(x, y)) {
			throw new PlayerGridException("ship is out of playground");
		}

		int sum = grid[x][y];

		if (sum > 0) {
			throw new PlayerGridException("ship's place is already taken");
		}

		if (horizontal) {
			if (inGrid(x + 1, y)) {
				sum += grid[x + 1][y];
			}
			if (inGrid(x, y - 1)) {
				sum += grid[x][y - 1];
			}
			if (inGrid(x, y + 1)) {
				sum += grid[x][y + 1];
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
		}
		if (sum > 0) {
			throw new PlayerGridException("ship collides with other ship");
		}
	}

	private boolean inGrid(int x, int y) {
		if (x < size && y < size && x >= 0 && y >= 0) {
			return true;
		}
		return false;
	}

	public boolean isShipsPlaced() {
		return shipsPlaced;
	}

	protected void setShipsPlaced(boolean shipsPlaced) {
		this.shipsPlaced = shipsPlaced;
	}

	public boolean hasError() {
		return hasError;
	}

	protected void setError(boolean hasError) {
		this.hasError = hasError;
	}

}
