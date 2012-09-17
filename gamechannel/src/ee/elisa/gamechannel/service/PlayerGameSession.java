package ee.elisa.gamechannel.service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ee.elisa.gamechannel.Settings;
import ee.elisa.gamechannel.model.PositionedShip;
import ee.elisa.gamechannel.util.Random;

@XmlRootElement()
@XmlAccessorType(XmlAccessType.NONE)
public class PlayerGameSession {

	public PlayerGrid grid;
	protected List<PositionedShip> ships;
	@XmlElement
	protected boolean shipsPlaced;
	protected boolean hasError;
	@XmlElement
	protected String playerName;
	@XmlElement
	protected int earnedHits;
	@XmlElement
	protected int gotHits;
	@XmlElement
	protected int hitsPossible;
	@XmlElement
	protected boolean hasShipsAlive;

	public PlayerGameSession(){		
	}
	
	public PlayerGameSession(String name, int size) {
		shipsPlaced = false;
		grid = new PlayerGrid(size);
		ships = new ArrayList<PositionedShip>();
		this.playerName = name;
		setEarnedHits(0);
		hasShipsAlive = true;
		hitsPossible = 0;
	}

	protected void addShip(PositionedShip ship) throws PlayerGridException {
		try {
			verifyShipType(ship);
			grid.drawOnGrid(ship);
			ships.add(ship);
			System.out.println("Added: " + ship);
		} catch (PlayerGridException e) {
			setError(true);
			throw e;
		}
	}

	private void verifyShipType(PositionedShip ship) throws PlayerGridException {
		if (ship.getSize() < 1
				|| ship.getSize() > Settings.getMaxShipSize(grid.getSize())) {
			throw new PlayerGridException("Invalid ship size");
		}
	}

	public boolean areShipsPlaced() {
		return shipsPlaced;
	}

	protected void setShipsPlaced() {
		this.shipsPlaced = true;
	}

	public boolean hasError() {
		return hasError;
	}

	protected void setError(boolean hasError) {
		this.hasError = hasError;
	}

	public void createRandomShips() throws PlayerGridException {
		Random rnd = new Random();

		for (int shipSize = Settings.getMaxShipSize(grid.getSize()); shipSize > 0; shipSize--) {

			for (int count = Settings.getShipCount(grid.getSize(), shipSize); count > 0; count--) {

				for (boolean done = false; done == false;) {
					try {
						addShip(new PositionedShip(shipSize, rnd.getInt(0,
								grid.size - 1), rnd.getInt(0, grid.size - 1),
								rnd.getInt(0, 1) > 0 ? true : false));
						done = true;
					} catch (PlayerGridException e) {
						// try another point
						System.out.println(e.getMessage());
					}
				}
			}
		}
		verifyShipsCollection();
	}

	public void addShips(List<PositionedShip> ships) throws PlayerGridException {
		for (PositionedShip ship : ships) {
			addShip(ship);
		}
		verifyShipsCollection();
	}

	private void verifyShipsCollection() throws PlayerGridException {
		int maxShip = Settings.getMaxShipSize(grid.getSize());
		int[] sizes = new int[maxShip];

		hitsPossible = 0;
		
		for (PositionedShip ship : ships) {
			sizes[ship.getSize() - 1]++;
		}

		try {
			for (int i = 0; i < maxShip; i++) {
				int targetCount = Settings.getShipCount(grid.getSize(), i + 1);
				if (sizes[i] != targetCount) {
					throw new PlayerGridException(
							"Invalid count of ships with size of " + (i + 1)
									+ ", must be " + targetCount);
				}
				hitsPossible += ((i+1)*targetCount);
			}
		} catch (PlayerGridException e) {
			this.setError(true);
			throw e;
		}

		System.out.println("Ships collection verified!");
		setShipsPlaced();
		setError(false);
	}

	public List<PositionedShip> getShips() {
		return ships;
	}

	public String getName() {
		return playerName;
	}

	public boolean hasShipsAlive() {
		return hasShipsAlive;
	}

	public boolean shootAtPlayer(int x, int y) {
		boolean hit =  grid.shootAt(x,y);
		if (hit){
			increaseGotHitsBy(1);
		}
		return hit;
	}

	public void addEarnedHits(int hitCount) {
		setEarnedHits(getEarnedHits() + hitCount);		
	}

	public int getEarnedHits() {
		return earnedHits;
	}

	public void setEarnedHits(int earnedHits) {
		this.earnedHits = earnedHits;
	}

	public int getHits() {
		return gotHits;
	}

	private void increaseGotHitsBy(int count) {
		this.gotHits += count;
		
		if (gotHits>=hitsPossible){
			hasShipsAlive = false;
		}
	}
}
