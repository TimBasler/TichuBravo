package client;

//This Code is written by Tim

import java.util.ArrayList;

import common.Card;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Player {
	
	protected int playerID=0;
	protected String playerName;
	protected boolean isTeamOne;
	protected int scorePlayer;
	protected ArrayList<Card>cardList=new ArrayList<>();
	
	
	public Player(String playerName, boolean isTeamOne,int playerID) {
	this.playerName=playerName;
	this.isTeamOne=isTeamOne;
	this.playerID=playerID;
	}
	
	public void setPlayerID(int playerID) {
		this.playerID=playerID;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName=playerName;
	}
	
	public void setIsTeamOne(boolean isTeamOne) {
		this.isTeamOne=isTeamOne;
	}
	
	public void setScorePlayer(int scorePlayer) {
		this.scorePlayer=scorePlayer;
	}
	
	public int getPlayerID() {
		return this.playerID;
	}
	
	public void increasePlayerID() {
		if(this.playerID<4) {
			this.playerID++;
		}else {
			Alert alert = new Alert(AlertType.ERROR, "MAX 4 Players");
			alert.showAndWait();
		}
	}
	
	public  Player getPlayer() {
		return new Player(this.playerName,this.isTeamOne,this.playerID);
	}
	
	public String toString() {
		return this.playerName +" player is in Team One " + this.isTeamOne +" he has the Player ID: "+this.playerID;
	}
	
	

}
