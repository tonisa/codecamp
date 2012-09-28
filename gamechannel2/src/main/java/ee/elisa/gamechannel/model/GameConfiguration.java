package ee.elisa.gamechannel.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import ee.elisa.gamechannel.Settings;


@XmlRootElement(name="gameConfiguration")
public class GameConfiguration {
	public Integer id;
	public String name;
	public GameStatus status;
	public String initiatedBy;
	public int playersTotal;
	public Integer playersActive;
	public int gridSize;

	public Date created;
	public Date started;
	public Date finished;
	
	public GameConfiguration(){
		this.gridSize = Settings.getDefaultPlaygroundSize();
		this.status = GameStatus.STARTING;
		this.initiatedBy = "n/a";
		this.created = new Date();
	}
	
	public GameConfiguration(Integer id, String name, Integer gridSize, String initiatedBy){
		this.id = id;
		this.name = name;
		this.gridSize = gridSize;
		this.status = GameStatus.STARTING;
		this.initiatedBy = initiatedBy;
		this.created = new Date();
	}

	public int getGridSize() {
		return gridSize;
	}
	
}
