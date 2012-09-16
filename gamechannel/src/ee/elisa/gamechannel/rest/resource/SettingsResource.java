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

import ee.elisa.gamechannel.Settings;
import ee.elisa.gamechannel.model.GameConfiguration;
import ee.elisa.gamechannel.model.GameStatus;
import ee.elisa.gamechannel.model.Player;
import ee.elisa.gamechannel.model.PlayerStatus;
import ee.elisa.gamechannel.model.PositionedShip;
import ee.elisa.gamechannel.model.ShipsSettings;
import ee.elisa.gamechannel.service.Game;
import ee.elisa.gamechannel.service.GameLogicException;
import ee.elisa.gamechannel.service.GameService;
import ee.elisa.gamechannel.service.PlayerGameSession;
import ee.elisa.gamechannel.service.PlayerGridException;
import ee.elisa.gamechannel.service.ServiceException;

@Path("/settings")
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class SettingsResource {

	@Inject
	private GameService games;

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public String defaultQuery(){
		return "hint: /playGroundSize , /playGroundSize/{size}/maxShipSize , /playGroundSize/{psize}/shipSize/{shipSize}/shipCount";
	}
	
	@GET
	@Path("/playGroundSize")
	@Produces(MediaType.TEXT_PLAIN)
	public String defaultPlaygroundSize(){
		return Integer.toString(Settings.getDefaultPlaygroundSize());
	}

	@GET
	@Path("/playGroundSize/{size}/maxShipSize")
	@Produces(MediaType.TEXT_PLAIN)
	public String maxShipSize(@PathParam("size") Integer size) {
		return Integer.toString(Settings.getMaxShipSize(size));
	}

	@GET
	@Path("/playGroundSize/{psize}/shipSize/{shipSize}/shipCount")
	@Produces(MediaType.TEXT_PLAIN)
	public String shipCount(@PathParam("psize") Integer playgroundSize, @PathParam("shipSize") Integer shipSize) {
		return Integer.toString(Settings.getShipCount(playgroundSize, shipSize));
	}
}
