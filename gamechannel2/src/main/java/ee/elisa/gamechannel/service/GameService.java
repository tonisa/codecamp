package ee.elisa.gamechannel.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ws.rs.WebApplicationException;

import com.google.inject.Inject;

import ee.elisa.gamechannel.model.GameConfiguration;
import ee.elisa.gamechannel.model.GameStatus;
import ee.elisa.gamechannel.model.PlayerRank;
import ee.elisa.gamechannel.model.ShipsSettings;
import ee.elisa.gamechannel.rest.RESTException;

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

	public GameConfiguration startGame(Integer id) throws WebApplicationException {
		Game game = games.get(id);
		if (game == null){
			throw new RESTException.NotFoundException("Game id "+id+" not valid");
		}		
		try {
			game.startGame();
		} catch (ServiceException e) {
			throw new RESTException.ForbiddenException("Game cannot be started",e);
		}		
		return game.settings;
	}

	public Game getGameById(Integer id) throws RESTException.NotFoundException {
		Game game = games.get(id);
		if (game == null){
			throw new RESTException.NotFoundException("Game id "+id+" not valid");
		}		
		
		return game;
	}

	public List<GameConfiguration> getListing(GameStatus expectedStatus) {
		List<GameConfiguration> theGames = new ArrayList<GameConfiguration>();
		if (games != null) {
			for (Map.Entry<Integer, Game> it : games.entrySet()) {
				Game game = it.getValue();
				if (game.getStatus().equals(expectedStatus)) {
					theGames.add(game.getSettings());
				}
			}
		}
		return theGames;
	}

	public void joinGame(Integer gameId, String player, ShipsSettings ships) throws WebApplicationException {
		Game game = games.get(gameId);
		if (game == null){
			throw new RESTException.NotFoundException("Game id "+gameId+" not valid");
		}		
		try {
			game.addPlayer(player, ships);
		} catch (ServiceException e) {
			throw new RESTException.ForbiddenException("Player already in game", e);
		} catch (PlayerGridException e) {
			throw new RESTException.ForbiddenException(e);
		}
	}

	public PlayerGameSession getGamePlayer(Integer id, String player) throws RESTException.NotFoundException {
		Game game = games.get(id);
		if (game == null){
			throw new RESTException.NotFoundException("No such game");
		}
		PlayerGameSession playerSession = game.getPlayer(player);
		if (playerSession == null){
			throw new RESTException.NotFoundException("No such player");
		}
		return playerSession;
	}

	public int shoot(Integer id, String player, Integer x, Integer y) throws WebApplicationException {
		Game game = games.get(id);
		if (game == null){
			throw new RESTException.NotFoundException("No such game");
		}
		PlayerGameSession playerSession = game.getPlayer(player);
		if (playerSession == null){
			throw new RESTException.NotFoundException("No such player");
		}
		if (!GameStatus.RUNNING.equals(game.getStatus())){
			throw new RESTException.ForbiddenException("Game not running, cannot bomb");			
		}
		
		if (!playerSession.getName().equalsIgnoreCase(game.getCurrentPlayer())){
			throw new RESTException.NotFoundException("Not your turn, let "+game.getCurrentPlayer()+" to play");
		}
		if (game.getSettings().getGridSize()< x.intValue() || game.getSettings().getGridSize()< y.intValue()){
			throw new RESTException.BadRequestException("bomb coordinates out of grid");
		}
		
		int hitCount;
		try {
			hitCount = game.shoot(x,y, player);
		} catch (GameLogicException e) {
			throw new RESTException.ForbiddenException("shooting failed", e);
		}
		return hitCount;
	}

	public List<PlayerRank> getPlayerRanks(Integer id) throws RESTException.NotFoundException{
		Game game = games.get(id);
		if (game == null) {
			throw new RESTException.NotFoundException("No such game");
		}
		List<PlayerGameSession> players = game.getPlayers();

		Collections.sort(players, new PlayerGameSessionComparator());

		LinkedList<PlayerRank> ranks = new LinkedList<PlayerRank>();
		int rank = players.size();
		for (PlayerGameSession player : players) {
			ranks.addFirst(new PlayerRank(player, rank--));
		}
		return ranks;
	}

	public GameConfiguration initAutomaticGame(Integer id, Integer delay) throws WebApplicationException {
		Game game = games.get(id);
		if (game == null) {
			throw new RESTException.NotFoundException("No such game");
		}
		try {
			game.startAutomaticGame(delay);
		} catch (ServiceException e) {
			throw new RESTException.ForbiddenException(e);
		}
		
		return game.getSettings();
	}

}
