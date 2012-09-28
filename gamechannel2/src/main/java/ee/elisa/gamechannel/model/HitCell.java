package ee.elisa.gamechannel.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class HitCell {

	private int x;
	private int y;
	
	public HitCell(){		
	}

	public HitCell(int coordinateX, int coordinateY){
		setX(coordinateX);
		setY(coordinateY);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}
