<%@page pageEncoding="utf-8" import="java.util.*,
				java.lang.*,
                java.net.*,
                java.text.*,
                java.util.zip.*,
                java.io.*,
                ee.elisa.gamechannel.service.*,
                ee.elisa.gamechannel.model.*,
                com.google.inject.*"
%><%
Injector inj = (Injector) pageContext.getServletContext().getAttribute(Injector.class.getName());
GameService games = inj.getInstance(GameService.class);

if( request.getParameter( "id") != null){
 response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
 response.setHeader("Pragma","no-cache"); //HTTP 1.0
 response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
 response.setContentType("text/event-stream");
 response.addHeader("Connection","keep-alive");
  Integer id = Integer.parseInt( request.getParameter( "id"));
  Game game = games.getGameById(id);
  out.write("id: <b>"+new Date()+"</b>\n\n");
  StringBuffer data = new StringBuffer();
  data.append( "<h1>"+game.getSettings().id+" - "+game.getSettings().name+" ("+game.getSettings().gridSize+")</h1>");
  int i = -1;
  for( PlayerGameSession player : game.getPlayers()){
  	  i++;
  	  if( i > 4){
  	  	i = -1;
  	  }
	  data.append( "<div class=\"ui-block-"+(char)('a'+i)+"\">");
	  data.append( "<fieldset><legend><b>"+player.getName()+", "+(player.hasShipsAlive()?"In battle":"GAME OVER")+". Hits: "+player.getEarnedHits()+"</b></legend>");
	  data.append( "<table>");
	  for( int y = 0; y < game.getSettings().gridSize; y++){
	  	data.append( "<tr>");
	  	for( int x = 0; x < game.getSettings().gridSize; x++){
	   		data.append( "<td>"+(player.grid.grid[y][x]>0 ? ""+player.grid.grid[y][x] : "")+"</td>");
	  	}
	  	data.append( "</tr>");
	  }
	  data.append( "</table></fieldset>");
	  data.append( "</div>");
	  out.write("data: "+data+"\n\n");
	  out.flush();
  }
} else {
 out.write("Game id parameter is missing...");
}
%>
