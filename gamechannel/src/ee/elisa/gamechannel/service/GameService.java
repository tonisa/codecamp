package ee.elisa.gamechannel.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.sun.jersey.api.NotFoundException;

import ee.elisa.gamechannel.rest.model.GameConfiguration;
import ee.elisa.gamechannel.rest.model.GameStatus;

public class GameService {

	@Inject
	Logger logger; 
		
	Map<Integer, Game> games = new HashMap<Integer,Game>();
	Integer idCounter = new Integer(1);
	
	public GameConfiguration create(GameConfiguration gameConfig) {		
		
		synchronized(idCounter){
			Integer id = idCounter++;
			gameConfig.id = id;
			games.put(id, new Game(gameConfig));
			return gameConfig;
		}
	}

	public GameConfiguration startGame(Integer id) throws NotFoundException, ServiceException {
		Game game = games.get(id);
		if (game == null){
			throw new NotFoundException("No such game");
		}		
		game.startGame();		
		return game.settings;
	}

	public GameConfiguration getGameById(Integer id) throws NotFoundException {
		// TODO Auto-generated method stub
		logger.info("getById "+id+" "+this);
		Game game = games.get(id);
		if (game == null){
			throw new NotFoundException("No such game");
		}		
		
		// return new GameConfiguration(id, "good one", 10, "system");
		return game.settings;
	}

	public List<GameConfiguration> getListing(GameStatus running) {
		// TODO Auto-generated method stub
		return null;
	}

}
