package ee.elisa.gamechannel;

public class Settings {

	public static int getMaxShipSize(int playgroundSize) {
		return (playgroundSize / 2) - 1;
	}

	public static int getShipCount(int playgroundSize, int shipSize) {
		int maxShip = getMaxShipSize(playgroundSize);
		if (shipSize > maxShip) {
			return 0;
		}
		return (maxShip - shipSize) + 1;
	}

	public static int getDefaultPlaygroundSize() {
		return 10;
	}

}
