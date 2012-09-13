package ee.elisa.gamechannel.service;

import java.util.HashMap;
import java.util.Map;

import ee.elisa.gamechannel.rest.model.GameConfiguration;
import ee.elisa.gamechannel.rest.model.GameStatus;

public class Game  {

	protected GameConfiguration settings;
	protected Map<String,PlayerGrid> players;
	
	public Game(GameConfiguration settings){
		this.settings = settings;
		this.settings.playersTotal = 0;
		this.settings.playersActive = 0;
		players = new HashMap<String,PlayerGrid>();
	}
	
	public synchronized void addPlayer( String name, PlayerGrid grid) throws ServiceException {
		if (players.get(name)!= null){
			throw new ServiceException("Player "+name+" already in game "+settings.id);
		}
		
		if (settings.gridSize != grid.getGridSize()){
			throw new ServiceException("Grid size mismatch, should be "+settings.gridSize);
		}
		
		settings.playersTotal++;		
		players.put(name, grid);
	}
	
	public synchronized void startGame() throws ServiceException {
		if (!GameStatus.STARTING.equals(settings.status)){
			throw new ServiceException("Cannot start game, not in STARTING state");
		}		
		if (players.size()<2){
			throw new ServiceException("Cannot start game, less than 2 players");
		}		
		settings.playersActive = settings.playersTotal;
		settings.status = GameStatus.RUNNING;
	}
}
