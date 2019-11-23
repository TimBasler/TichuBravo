package server;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import common.Card;
import common.MsgType;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Dominik
 *
 */
public class ServerGame {
	protected Deck deck = new Deck();
	protected ObservableList<Player> players = FXCollections.observableArrayList();
	protected ObservableList<Player> newSequence = FXCollections.observableArrayList();
	protected SimpleIntegerProperty currentPlayerID = new SimpleIntegerProperty();
	
	
	public ServerGame() {
		
	}
	
	/**
	 * check the currentPlayerID and set the next ID on the SimpleIntegerProperty
	 */
	public void nextPlayer() {
		int i = currentPlayerID.get();
		for (int j = 0; j < newSequence.size(); j++) {
			if (newSequence.get(j).getID() == i) {
				if(j == newSequence.size()-1) {
					currentPlayerID.set(newSequence.get(0).getID());
				} else {
					currentPlayerID.set(newSequence.get(j + 1).getID());
				}
			}
		}
	}
	
	
	/**
	 * add the player with MahJong on the first place and the team colleague on the third place to the list, 
	 * then add the others
	 * @param firstID
	 */
	public void newSequence(int firstID) {
		if (newSequence.isEmpty()) {
			newSequence.clear();
			for (Player p : players) {
				if (p.getID() == firstID) {
					newSequence.add(p);
					players.remove(p);
					break;
				} 
			}
			for (Player p : players) {
				if (p.isTeamOne() == newSequence.get(0).isTeamOne()) {
					newSequence.add(p);
					players.remove(p);
					break;
				}
			}
			newSequence.add(1, players.get(0));	//Fehler
			newSequence.add(players.get(1));
			players.clear();
			currentPlayerID.set(newSequence.get(0).getID());
		}
	}
	
	/**
	 * deal all cards into four lists and send each list to a client
	 */
	public void sendNewCards() {
		ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
		lists.add(new ArrayList<String>());
		lists.add(new ArrayList<String>());
		lists.add(new ArrayList<String>());
		lists.add(new ArrayList<String>());
		deck.createDeck();
		Card c = null;
		do {
			for(ArrayList<String> list : lists) {
				c = deck.deal();
				if (c != null)
				list.add(c.toString());
			}
		} while (c != null);
		for (int i = 0; i < ServerModel.clients.size(); i++) {
			JSONObject json = ServerModel.clients.get(i).createJsonArray(MsgType.cards.toString(), lists.get(i));
			ServerModel.clients.get(i).send(json);
		}
	}

}
