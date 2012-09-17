package ee.elisa.gamechannel.service;

import java.util.Comparator;

public class PlayerGameSessionComparator implements Comparator<PlayerGameSession> {

	/**
     * @return a negative integer, zero, or a positive integer as the
     * 	       first argument is less than, equal to, or greater than the
     *	       second.
	 */
	@Override
	public int compare(PlayerGameSession o1, PlayerGameSession o2) {
		// one is lost, other is not
		if (o1.hasShipsAlive() != o2.hasShipsAlive()){
			return o1.hasShipsAlive()?1:-1;
		}

		if (o1.hasShipsAlive()){
			return new Integer(o1.getEarnedHits()).compareTo(o2.getEarnedHits());
		} else {
			return o1.getTimeLost().compareTo(o2.getTimeLost());
		}
	}

}
