package server;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import common.Card;
import common.MsgType;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ServerGame {
	protected Deck deck = new Deck();
	protected ObservableList<Player> players = FXCollections.observableArrayList();
	protected ObservableList<Player> newSequence = FXCollections.observableArrayList();
	protected SimpleIntegerProperty currentPlayerID;
	
	public ServerGame() {
		
	}
	
	public void newSequence(int firstID) {
		
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
		newSequence.add(1, players.get(0));
		newSequence.add(players.get(1));
		players.clear();
	}
	
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
