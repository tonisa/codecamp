package ee.elisa.gamechannel.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import ee.elisa.gamechannel.service.PlayerGameSession;

@XmlRootElement()
public class PlayerRank {

	private String name;
	private int rank;
	private boolean hasShipsAlive;
	private Date timeLost;
	private int earnedHits;
	private int hits;
	private int moves;

	public PlayerRank() {
	}

	public PlayerRank(PlayerGameSession player, int rank) {
		name = player.getName();
		this.setRank(rank);
		setHasShipsAlive(player.hasShipsAlive());
		setTimeLost(player.getTimeLost());
		setEarnedHits(player.getEarnedHits());
		setHits(player.getHits());
		setMoves(player.getPlayerMoves());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public boolean isHasShipsAlive() {
		return hasShipsAlive;
	}

	public void setHasShipsAlive(boolean hasShipsAlive) {
		this.hasShipsAlive = hasShipsAlive;
	}

	public Date getTimeLost() {
		return timeLost;
	}

	public void setTimeLost(Date timeLost) {
		this.timeLost = timeLost;
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

	public int getMoves() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}

}
