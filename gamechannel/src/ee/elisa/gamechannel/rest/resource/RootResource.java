package ee.elisa.gamechannel.rest.resource;

import java.io.IOException;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context; 
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.core.ResourceContext;

@Path("/")
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class RootResource {
    private static final Logger LOG = LoggerFactory.getLogger(RootResource.class);

    @Context
    ResourceContext rc;
    
    private String moduleInfo;

//    @Path(OffersResource.OFFERS_PATH)
//    public OffersResource getCustomersResource() {
//        return rc.getResource(OffersResource.class);
//    }

    @GET
    @Path("status")
    @Produces(MediaType.TEXT_PLAIN)
    public String getModuleInfo(@Context ServletContext ctx) throws IOException {
        return "sadsjlk jkljdskljsl akljaskaj kl";
    }
}
