<%@page import="java.util.*,
                java.net.*,
                java.text.*,
                java.util.zip.*,
                java.io.*"
%><%! int i =1; %>
<%

if( request.getParameter( "i") != null){
 i = i + Integer.parseInt( request.getParameter( "i"));
} else {
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
response.setContentType("text/event-stream");
response.addHeader("Connection","keep-alive");
 out.write("id: " + (new java.util.Date()) + "\n\n");
 out.write("data: " + i + (new java.util.Date()) + "\n\n");
 out.flush();
}
%>
