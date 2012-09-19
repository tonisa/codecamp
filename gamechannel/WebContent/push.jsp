<%@page pageEncoding="utf-8" import="java.util.*,
				java.lang.*,
                java.net.*,
                java.text.*,
                java.util.zip.*,
                java.io.*,
                ee.elisa.gamechannel.service.*,
                ee.elisa.gamechannel.model.*,
                com.google.inject.*"
%><%!
public static String getTime( Date it) {
    SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");
    String strDate = sdfDate.format(it);
    return strDate;
}

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
  String topStatus;
  if (GameStatus.FINISHED.equals(game.getSettings().status)){
   	topStatus = "WINNER";
  } else if (GameStatus.RUNNING.equals(game.getSettings().status)){
   	topStatus = "Playing";
  } else {
   	topStatus = "Player";
  }
  
  out.write("id: <b>"+new Date()+"</b>\n\n");
  StringBuffer data = new StringBuffer();
    
  data.append( "<button align=left onClick=\"if(source!=null)source.close();\">Game '"+game.getSettings().name+"' (id "+game.getSettings().id+", size "+game.getSettings().gridSize+")<br>"+game.getCurrentPlayer()+" bombing <hr/><table nowrap><tr><th>#</th><th>Name</th><th>Status</th><th>Hits</th><th>Losses</th></th><th>Moves</th></tr>");
  int j = 0;
  for( int i = 0; i < games.getPlayerRanks(id).size(); i++) {
   PlayerRank rank = games.getPlayerRanks(id).get(i);
   data.append( "<tr><td>"+(i+1)+"</td><td>" + rank.getName() + "</td><td>" + (rank.getTimeLost()!=null?("Lost "+getTime(rank.getTimeLost())):topStatus) + "</td><td>" + rank.getEarnedHits() + "</td><td>" + rank.getHits() + "</td><td>" + rank.getMoves() + "</td></tr>");
  }
  data.append( "</table></button>");
  int i = 0;
  for( PlayerGameSession player : game.getPlayers()){
  	  i++;
  	  if( i > 4){
  	  	i = 0;
  	  }
	  data.append( "<div class=\"ui-block-"+(char)('a'+i)+"\">");
	  data.append( "<fieldset><legend><b>"+player.getName()+", "+(player.hasShipsAlive()?"In battle":"GAME OVER")+"</b></legend>");
	  data.append( "<table>");
	  for( int y = 0; y < game.getSettings().gridSize; y++){
	  	data.append( "<tr>");
	  	for( int x = 0; x < game.getSettings().gridSize; x++){
	   		data.append( "<td"+(player.grid.HIT == player.grid.grid[y][x]?" bgcolor=black": (player.grid.grid[y][x]>0?" bgcolor=yellow":""))+">"+(player.grid.grid[y][x]>0 ? ""+player.grid.grid[y][x] : "")+"</td>");
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