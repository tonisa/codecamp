package ee.elisa.gamechannel.rest.resource;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.sun.jersey.api.NotFoundException;
import com.sun.jersey.api.core.ResourceContext;

import ee.elisa.gamechannel.model.BombingResult;
import ee.elisa.gamechannel.model.GameConfiguration;
import ee.elisa.gamechannel.model.GameStatus;
import ee.elisa.gamechannel.model.Player;
import ee.elisa.gamechannel.model.PlayerRank;
import ee.elisa.gamechannel.model.PlayerStatus;
import ee.elisa.gamechannel.model.PlayersStatus;
import ee.elisa.gamechannel.model.PositionedShip;
import ee.elisa.gamechannel.model.ShipsSettings;
import ee.elisa.gamechannel.service.Game;
import ee.elisa.gamechannel.service.GameLogicException;
import ee.elisa.gamechannel.service.GameService;
import ee.elisa.gamechannel.service.PlayerGameSession;
import ee.elisa.gamechannel.service.PlayerGridException;
import ee.elisa.gamechannel.service.ServiceException;
import ee.elisa.gamechannel.util.Random;

@Path("/games")
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class GameResource {

	@Inject
	private GameService games;

	@Inject 
	private Random random;
	
	@POST
	public GameConfiguration createGame(GameConfiguration gameConfig) {
		return games.create(gameConfig);
	}

	@GET
	@Path("/create")
	public GameConfiguration createGameSimple(@QueryParam("name") String name, @QueryParam("size") Integer size) {
		GameConfiguration settings = new GameConfiguration();
		if (size != null){
			settings.gridSize = size;
		}
		settings.name = name;
		return games.create(settings);
	}

	@GET
	@Path("{game_id}/start")
	public GameConfiguration startGame(@PathParam("game_id") Integer id){
		return games.startGame(id);
	}

	@GET
	@Path("{game_id}/auto")
	public GameConfiguration initAutomaticGame(@PathParam("game_id") Integer id, @QueryParam("delay") Integer delay){
		return games.initAutomaticGame(id, delay);
	}
	
	@GET
	@Path("{game_id}")
	public GameConfiguration getById(@PathParam("game_id") Integer id) {
		return games.getGameById(id).getSettings();
	}

	@GET
	@Path("{game_id}/players")
	public PlayersStatus getPlayersStatus(@PathParam("game_id") Integer id) {
		Game game = games.getGameById(id);
		return new PlayersStatus(game);
	}

	@GET
	@Path("{game_id}/ranks")
	public List<PlayerRank> getPlayerRanks(@PathParam("game_id") Integer id) {
		return games.getPlayerRanks(id);
	}
	
	@GET
	@Path("/starting")
	public List<GameConfiguration> getStartingGames() {
		return games.getListing(GameStatus.STARTING);
	}

	@GET
	@Path("/running")
	public List<GameConfiguration> getRunningGames() {
		return games.getListing(GameStatus.RUNNING);
	}

	@GET
	@Path("/finished")
	public List<GameConfiguration> getFinishedGames() {
		return games.getListing(GameStatus.FINISHED);
	}

	@GET
	@Path("{game_id}/join/{player}")
	public ShipsSettings joinGame(@PathParam("game_id") Integer id,
			@PathParam("player") String player) throws ServiceException,
			PlayerGridException {
		games.joinGame(id, player, null);
		PlayerGameSession playerSession = games.getGamePlayer(id, player);

		return new ShipsSettings(playerSession.getShips());
	}

	@POST
	@Path("{game_id}/join/{player}")
	public void joinGame(@PathParam("game_id") Integer id,
			@PathParam("player") String player, ShipsSettings ships)
			throws ServiceException, PlayerGridException {
		games.joinGame(id, player, ships);
	}

	@GET
	@Path("{game_id}/players/{player}")
	public PlayerStatus playerStatus(@PathParam("game_id") Integer id,
			@PathParam("player") String player) throws ServiceException,
			PlayerGridException {
		Game game = games.getGameById(id);
		PlayerGameSession playerSession = games.getGamePlayer(id, player);
		return new PlayerStatus(game, playerSession);
	}

	@GET
	@Path("{game_id}/player/{player}/bomb")
	public BombingResult bombSimple(@PathParam("game_id") Integer id,
			@PathParam("player") String player, @QueryParam("x") Integer x,
			@QueryParam("y") Integer y) throws ServiceException,
			PlayerGridException, GameLogicException {		

		Game game = games.getGameById(id);

		if (x == null){
			x = random.getInt(0, game.getSettings().getGridSize());
		}

		if (y == null){
			y = random.getInt(0, game.getSettings().getGridSize());
		}
		
		int hitsCount = games.shoot(id, player, x, y);
		PlayerGameSession playerSession = games.getGamePlayer(id, player);
		return new BombingResult(game,playerSession,hitsCount,x, y);
	}
}
