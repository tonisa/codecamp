package ee.elisa.gamechannel.model;

import javax.xml.bind.annotation.XmlRootElement;

import ee.elisa.gamechannel.service.Game;
import ee.elisa.gamechannel.service.PlayerGameSession;

@XmlRootElement
public class BombingResult {

	private GameConfiguration gameStatus;
	private String currentPlayer;
	private int hitsCount;
	private Integer coordinateX;
	private Integer coordinateY;

	public BombingResult(){		
	}
	
	public BombingResult(Game game, PlayerGameSession playerSession,
			int hitsCount, Integer x, Integer y) {
		setGameStatus(game.getSettings());
		setCurrentPlayer(game.getCurrentPlayer());
		this.setHitsCount(hitsCount);
		this.setCoordinateX(x);
		this.setCoordinateY(y);
	}

	public GameConfiguration getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(GameConfiguration gameStatus) {
		this.gameStatus = gameStatus;
	}

	public String getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(String currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public int getHitsCount() {
		return hitsCount;
	}

	public void setHitsCount(int hitsCount) {
		this.hitsCount = hitsCount;
	}

	public Integer getCoordinateX() {
		return coordinateX;
	}

	public void setCoordinateX(Integer coordinateX) {
		this.coordinateX = coordinateX;
	}

	public Integer getCoordinateY() {
		return coordinateY;
	}

	public void setCoordinateY(Integer coordinateY) {
		this.coordinateY = coordinateY;
	}
}
