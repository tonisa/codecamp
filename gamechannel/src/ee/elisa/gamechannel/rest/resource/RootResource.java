package ee.elisa.gamechannel.rest.resource;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
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

	@GET
	@Path("mirror")
	@Produces(MediaType.TEXT_PLAIN)
	public void getMirrorDisplay(@Context HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
		response.setHeader("Pragma", "no-cache"); //HTTP 1.0
		response.setDateHeader("Expires", 0); //prevents caching at the proxy server
		response.setContentType("text/event-stream");
		response.addHeader("Connection", "keep-alive");
		PrintWriter out = response.getWriter();
		out.write("id: " + (new java.util.Date()) + "\n\n");
		out.write("data: " + (new java.util.Date()) + "\n\n");
		out.flush();
	}

	@GET
	@Path("layout")
	@Produces(MediaType.TEXT_HTML)
	public void getLayout(@Context HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
		response.setHeader("Pragma", "no-cache"); //HTTP 1.0
		response.setDateHeader("Expires", 0); //prevents caching at the proxy server
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write("<html>\n");
		out.write("<head><title>BATTLESHIP 1942. &copy; Elisa CodeCamp 2012</title></head>\n");
		out.write("<body>\n");
		out.write("<h1>" + new Date() + "</h1>\n");
		out.write("<script language=javascript>\n");
		out.write("var source = new EventSource('/gamechannel/mirror');\n");
		out.write("    source.onmessage = function(e) {\n");
		out.write("    document.body.innerHTML = document.body.innerHTML + e.data + '<br>';\n");
		out.write("};\n");
		out.write("</script>\n");

		out.write("</body>\n");
		out.write("</html>\n");
		out.flush();
	}
}
