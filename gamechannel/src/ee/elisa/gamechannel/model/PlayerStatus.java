package ee.elisa.gamechannel.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import ee.elisa.gamechannel.service.Game;
import ee.elisa.gamechannel.service.PlayerGameSession;

@XmlRootElement()
public class PlayerStatus {

	private GameConfiguration gameStatus;
	private boolean shipsPlaced;
	private boolean shipsInError;
	private String playerName;
	private String currentPlayer;
	private int earnedHits;
	private int hits;
	private boolean shipsAlive;
	private List<HitCell> hitCells;

	public PlayerStatus(){		
	}
	
	public PlayerStatus(Game game,
			PlayerGameSession playerSession) {
		this.setGameStatus(game.getSettings());
		setShipsPlaced(playerSession.areShipsPlaced());
		setShipsInError(playerSession.hasError());
		setPlayerName(playerSession.getName());
		setCurrentPlayer(game.getCurrentPlayer());
		setEarnedHits(playerSession.getEarnedHits());
		setHits(playerSession.getHits());
		setShipsAlive(playerSession.hasShipsAlive());
		setHitCells(playerSession.getCellHits());
	}

	public GameConfiguration getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(GameConfiguration gameStatus) {
		this.gameStatus = gameStatus;
	}

	public boolean isShipsPlaced() {
		return shipsPlaced;
	}

	public void setShipsPlaced(boolean shipsPlaced) {
		this.shipsPlaced = shipsPlaced;
	}

	public boolean isShipsInError() {
		return shipsInError;
	}

	public void setShipsInError(boolean shipsInError) {
		this.shipsInError = shipsInError;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(String currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public int getEarnedHits() {
		return earnedHits;
	}

	public void setEarnedHits(int earnedHits) {
		this.earnedHits = earnedHits;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public void setShipsAlive(boolean hasShipsAlive) {
		this.shipsAlive = hasShipsAlive;
	}

	public boolean isShipsAlive() {
		return shipsAlive;
	}

	public List<HitCell> getHitCells() {
		return hitCells;
	}

	public void setHitCells(List<HitCell> hitCells) {
		this.hitCells = hitCells;
	}

}
