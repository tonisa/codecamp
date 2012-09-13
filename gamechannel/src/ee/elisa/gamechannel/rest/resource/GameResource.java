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

import ee.elisa.gamechannel.rest.model.*;
import ee.elisa.gamechannel.service.GameService;
import ee.elisa.gamechannel.service.ServiceException;

@Path("/games")
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })

public class GameResource {

	@Inject 
	private GameService games;
	
    @POST
    public GameConfiguration createGame( GameConfiguration gameConfig)  {
        return games.create(gameConfig);
    }

    @GET
    @Path("/create")
    public GameConfiguration createGameSimple(@QueryParam("name") String name){
    	GameConfiguration settings = new GameConfiguration();
    	settings.name = name;
		return games.create(settings);
    }
    
    @GET
    @Path("{game_id}/start")
    public GameConfiguration startGame(@PathParam("game_id") Integer id) throws NotFoundException, ServiceException {
		return games.startGame(id);
    }
    
    @GET
    @Path("{game_id}")
    public GameConfiguration getById(@PathParam("game_id") Integer id) {
        return games.getGameById(id);
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
    
}
