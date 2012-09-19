package ee.elisa.gamechannel.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import ee.elisa.gamechannel.model.GameConfiguration;
import ee.elisa.gamechannel.model.GameStatus;
import ee.elisa.gamechannel.model.PlayerRank;
import ee.elisa.gamechannel.model.ShipsSettings;
import ee.elisa.gamechannel.util.Random;
 
public class Game extends TimerTask {

	protected GameConfiguration settings;
	protected Map<String, PlayerGameSession> players;
	protected Deque<String> playerOrder;
	protected boolean automaticPlay;
	private Timer timer;
	private int x;
	private Random rnd;
	private int delay;

	public Game(GameConfiguration settings) {
		automaticPlay = false;
		timer = null;
		this.settings = settings;
		this.settings.playersTotal = 0;
		this.settings.playersActive = 0;
		players = new HashMap<String, PlayerGameSession>();
		playerOrder = new LinkedList<String>();
	}

	public synchronized void addPlayer(String name, ShipsSettings ships)
			throws ServiceException, PlayerGridException {

		if (!GameStatus.STARTING.equals(settings.status)) {
			throw new ServiceException(
					"Cannot join game, not in STARTING state");
		}

		if (players.get(name) != null) {
			throw new ServiceException("Player " + name + " already in game "
					+ settings.id);
		}

		PlayerGameSession session = new PlayerGameSession(name,
				settings.gridSize);

		if (ships == null) {
			if (settings.gridSize>15){
				throw new ServiceException("Cannot do automatic ships placement, grid size bigger than 15");
			}
			session.createRandomShips();
		} else {
			session.addShips(ships.getShips());
		}

		settings.playersTotal++;
		players.put(name, session);
	}

	public synchronized void startGame() throws ServiceException {
		if (!GameStatus.STARTING.equals(settings.status)) {
			throw new ServiceException(
					"Cannot start game, not in STARTING state");
		}
		if (players.size() < 2) {
			throw new ServiceException("Cannot start game, less than 2 players");
		}
		
		createPlayerOrder();
		
		settings.playersActive = settings.playersTotal;
		settings.status = GameStatus.RUNNING;
		settings.started = new Date();
	}


	private void createPlayerOrder() {
		for (Entry<String, PlayerGameSession> entry : players.entrySet()) {
			playerOrder.add(entry.getKey());
		}
		Collections.shuffle((List<?>) playerOrder);
	}

	public String getCurrentPlayer() {
		if (!playerOrder.isEmpty()) {
			return playerOrder.getFirst();
		}
		return null;
	}

	private String nextPlayer() {
		PlayerGameSession sess = null;
		String current = null;

		if (!playerOrder.isEmpty()) {
			current = playerOrder.getFirst();

			do {
				playerOrder.addLast(playerOrder.removeFirst());
				sess = players.get(playerOrder.getFirst());
				if (current.equalsIgnoreCase(sess.getName())) {
					// fail on infinitive loop
					return null;
				}
			} while (!sess.hasShipsAlive());

			return sess.getName();
		}
		return null;
	}

	public PlayerGameSession getPlayer(String player) {
		return players.get(player);
	}

	public GameConfiguration getSettings() {
		return settings;
	}

	public int shoot(int x, int y, String player) throws GameLogicException {
		int hitCount = 0;

		System.out.println(player+" shoots at "+x+","+y);
		
		if (!GameStatus.RUNNING.equals(settings.status)) {
			throw new GameLogicException(
					"Cannot shoot, game not in RUNNING state");
		}
		
		PlayerGameSession session = getPlayer(player);		
		for (Entry<String, PlayerGameSession> entry : players.entrySet()) {
			PlayerGameSession gamer = entry.getValue();			
			if (!player.equalsIgnoreCase(gamer.getName()) && gamer.shootAtPlayer(x,y)){
				hitCount++;
			}
		}
		
		if (hitCount==0){
			String next = nextPlayer();
			if (next==null){
				settings.status = GameStatus.FINISHED;
				settings.finished = new Date();
			}
		} else {
			System.out.println(player+" hits to "+hitCount+" ships!");			
		}
		
		session.addEarnedHits(hitCount);
		
		return hitCount;
	}

	public GameStatus getStatus() {
		return settings.status;
	}

	public List<PlayerGameSession> getPlayers() {
		List<PlayerGameSession> playerList = new ArrayList<PlayerGameSession>();
		for (Entry<String, PlayerGameSession> entry : players.entrySet()) {
			playerList.add(entry.getValue());			
		}
		return playerList;
	}

	@Deprecated
	public List<PlayerRank> getPlayerRanks() {
		List<PlayerGameSession> players = getPlayers();
		Collections.sort(players, new PlayerGameSessionComparator());
		List<PlayerRank> ranks = new ArrayList<PlayerRank>();
		int rank = 1;
		for(PlayerGameSession player:players){
			ranks.add(new PlayerRank(player,rank++));
		}
		return ranks;
	}

	protected void setDelay( Integer delay ){
		if (delay==null){
			delay = 100;
		}
		
		if (delay.intValue() < 100){
			this.delay = 100;
		} else if (delay.intValue() > 1000){
			this.delay = 1000;
		} else {
			this.delay = delay.intValue();
		}
	}
	
	public void startAutomaticGame(Integer delay) throws ServiceException {
		if (automaticPlay){
			setDelay(delay);
			return;
		}
		
		startGame();
		
		automaticPlay = true;
		rnd = new Random();
		timer = new Timer();
		setDelay(delay);
		timer.scheduleAtFixedRate(this, 2000, this.delay);
		System.out.println("Starting autoplay with delay of "+this.delay+" ms");
	}

	// body for timer task
	@Override
	public void run() {
		
		if (GameStatus.FINISHED.equals(settings.status) && timer != null) {
			System.out.println("Closing autoplay");
			this.cancel();
			automaticPlay = false;
			return;
		}

		String player = getCurrentPlayer();
		
		int y = rnd.getInt(0, getSettings().getGridSize()-1), x = rnd.getInt(0, settings.getGridSize()-1);
		
		try {
			shoot(x,y,player);
		} catch (GameLogicException e) {
			e.printStackTrace();
		}		
	}
}
