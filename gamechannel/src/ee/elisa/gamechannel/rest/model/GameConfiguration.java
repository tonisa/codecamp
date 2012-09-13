package ee.elisa.gamechannel.rest.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="gameConfiguration")
public class GameConfiguration {
	public Integer id;
	public String name;
	public GameStatus status;
	public String initiatedBy;
	public Integer playersTotal;
	public Integer playersActive;
	public Integer gridSize;

	public Date created;
	public Date started;
	public Date finished;
	
	public GameConfiguration(){
		this.gridSize = 10;
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
	
}
