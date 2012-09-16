package ee.elisa.gamechannel.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ee.elisa.gamechannel.service.Game;
import ee.elisa.gamechannel.service.PlayerGameSession;

@XmlRootElement()
@XmlAccessorType(XmlAccessType.NONE)
public class PlayersStatus {

	@XmlElement
	private List<PlayerGameSession> players;
	@XmlElement
	private String currentPlayer;

	public PlayersStatus(){		
	}
	
	public PlayersStatus(Game game) {
		setPlayers(game.getPlayers());
		currentPlayer = game.getCurrentPlayer();
	}

	public List<PlayerGameSession> getPlayers() {
		return players;
	}

	public void setPlayers(List<PlayerGameSession> players) {
		this.players = players;
	}
	
}
