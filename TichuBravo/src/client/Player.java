package client;

import java.util.ArrayList;

import common.Card;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author Tim
 *
 */
public class Player {
	
	protected int playerID=0;
	protected String playerName;
	protected boolean isTeamOne;
	protected int scorePlayer;
	protected ObservableList<Card> earnedCards = FXCollections.observableArrayList(); //cards that this player earned
	protected ObservableList<Card> playedCardsThisRound = FXCollections.observableArrayList(); //all cards on the table
	protected ObservableList<Card> table = FXCollections.observableArrayList();	//showed cards on the table
	protected ObservableList<Card> normalCardList = FXCollections.observableArrayList();
	protected ObservableList<Card> specialCardList = FXCollections.observableArrayList();
	protected ArrayList <CardLabel> selectedCardList=new ArrayList<>();
	protected SimpleBooleanProperty allCardsReceived = new SimpleBooleanProperty(false);
	protected SimpleIntegerProperty myTurn = new SimpleIntegerProperty();
	protected SimpleIntegerProperty teamChange = new SimpleIntegerProperty();
	protected SimpleIntegerProperty winnerOfTheRound = new SimpleIntegerProperty();
	protected boolean saidSmallTichu=false;
	protected boolean saidGrandTichu=false;
	
	public Player() {
		this.playerName=null;
		this.isTeamOne=true;
	}
	
	public Player(String playerName, boolean isTeamOne, int playerID) {
	this.playerName=playerName;
	this.isTeamOne=isTeamOne;
	this.playerID=playerID;
	}
	
	public boolean isTeamOne() {
		return isTeamOne;
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
	
	public String toString() {
		return this.playerName +" player is in Team One " + this.isTeamOne +" he has the Player ID: "+this.playerID;
	}
	
	

}
