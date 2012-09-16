package ee.elisa.gamechannel.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class ShipsSettings {

	private List<PositionedShip> ships = new ArrayList<PositionedShip>();
	
	public ShipsSettings(){		
	}

	public ShipsSettings(List<PositionedShip> ships) {
		setShips(ships);
	}
	
	public List<PositionedShip> getShips() {
		return ships;
	}

	public void setShips(List<PositionedShip> ships) {
		this.ships = ships;
	}

	public  void addShip(PositionedShip ship) {
		this.ships.add(ship);
	}
	
}
