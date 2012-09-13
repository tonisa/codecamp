package ee.elisa.gamechannel.service;

public class PositionedShip {
	protected int size;
	protected int cornerX; // left upper corner X
	protected int cornerY; // left upper corner X
	protected boolean horizontal; // horizontal (true) or vertical ship

	public PositionedShip(int size, int cornerX, int cornerY, boolean horizontal) {
		super();
		this.size = size;
		this.cornerX = cornerX;
		this.cornerY = cornerY;
		this.horizontal = horizontal;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getCornerX() {
		return cornerX;
	}
	public void setCornerX(int cornerX) {
		this.cornerX = cornerX;
	}
	public int getCornerY() {
		return cornerY;
	}
	public void setCornerY(int cornerY) {
		this.cornerY = cornerY;
	}
	public boolean isHorizontal() {
		return horizontal;
	}
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}
}
