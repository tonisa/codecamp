package ee.elisa.gamechannel.rest.resource;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;

import com.google.inject.Singleton;
import com.sun.jersey.api.client.ClientResponse.Status;

@Provider
@Singleton
public class ExceptionExceptionMapper implements ExceptionMapper<Exception> {

	@Context Providers providers;
    // private final Providers providers;

	   @Context
	    private HttpServletRequest req;
	   
	    @Context
	    private HttpHeaders headers;

    public ExceptionExceptionMapper() {
    }
	
    @Override  
    public Response toResponse(Exception e) {
    	System.out.println(headers.getAcceptableMediaTypes());
    	
    	System.out.println(req.getContextPath()+req.getPathInfo());
    	System.out.println(req.getQueryString());    	
    	System.out.println(req.getSession().getId());
    	
    	if (e instanceof WebApplicationException){
    		return ((WebApplicationException)e).getResponse();
    	}
    	// e.printStackTrace();
    	for(String row:filterStack(e,"com.")){
    		System.out.println(row);
    	}
        return Response.status(Status.BAD_REQUEST)  
            .type(MediaType.TEXT_PLAIN)  
            .entity("mymessage")  
            .build();  
    }

	private ArrayList<String> filterStack(Throwable e, String packagePattern) {
		StackTraceElement[] stack = e.getStackTrace();
		ArrayList<String> out = new ArrayList<String>();
		out.add(e.getClass().getName()+": "+e.getMessage());
		StackTraceElement topRow = null;
		StackTraceElement lastRow = null;
		for(StackTraceElement row:stack){
			lastRow = row;
			if (row.getClassName().startsWith(packagePattern)){
				topRow = row;
			}
		}
		for(StackTraceElement row:stack){
			out.add(formatStackRow(row));
			if (topRow == null || row == topRow){
				if (row != lastRow){
					out.add("More stack rows to follow..");
				}
				break;
			}
		}
		return out;
	}

	private String formatStackRow(StackTraceElement row) {
		StringBuilder builder = new StringBuilder();
		builder.append('\t');
		builder.append(row.getClassName()).append('.').append(row.getMethodName());
		builder.append("(");
		if (row.isNativeMethod()){
			builder.append("Native Method");
		} else {			
			builder.append(row.getFileName()).append(':').append(row.getLineNumber());
		}		
		builder.append(')');
		return builder.toString();
	} 
}
