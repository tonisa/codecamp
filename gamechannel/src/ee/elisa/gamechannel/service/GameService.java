package ee.elisa.gamechannel.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.sun.jersey.api.NotFoundException;

import ee.elisa.gamechannel.model.GameConfiguration;
import ee.elisa.gamechannel.model.GameStatus;
import ee.elisa.gamechannel.model.Player;
import ee.elisa.gamechannel.model.ShipsSettings;

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

	public Game getGameById(Integer id) throws NotFoundException {
		// TODO Auto-generated method stub
		logger.info("getById "+id+" "+this);
		Game game = games.get(id);
		if (game == null){
			throw new NotFoundException("No such game");
		}		
		
		// return new GameConfiguration(id, "good one", 10, "system");
		return game;
	}

	public List<GameConfiguration> getListing(GameStatus running) {
		// TODO Auto-generated method stub
		return null;
	}

	public void joinGame(Integer id, String player, ShipsSettings ships) throws ServiceException, PlayerGridException {
		Game game = games.get(id);
		if (game == null){
			throw new NotFoundException("No such game");
		}		
		game.addPlayer(player, ships);
		return;
	}

	public PlayerGameSession getGamePlayer(Integer id, String player) {
		Game game = games.get(id);
		if (game == null){
			throw new NotFoundException("No such game");
		}
		PlayerGameSession playerSession = game.getPlayer(player);
		if (playerSession == null){
			throw new NotFoundException("No such player");
		}
		return playerSession;
	}

	public int shoot(Integer id, String player, Integer x, Integer y) throws GameLogicException {
		Game game = games.get(id);
		if (game == null){
			throw new NotFoundException("No such game");
		}
		PlayerGameSession playerSession = game.getPlayer(player);
		if (playerSession == null){
			throw new NotFoundException("No such player");
		}
		if (!GameStatus.RUNNING.equals(game.getStatus())){
			throw new GameLogicException("Game not running, cannot bomb");			
		}
		
		if (!playerSession.getName().equalsIgnoreCase(game.getCurrentPlayer())){
			throw new GameLogicException("Not your turn, let "+game.getCurrentPlayer()+" to play");
		}
		if (game.getSettings().getGridSize()< x.intValue() || game.getSettings().getGridSize()< y.intValue()){
			throw new GameLogicException("bomb coordinates out of grid");
		}
		
		int hitCount = game.shoot(x,y, player);
		return hitCount;
	}

}
