package server;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import common.Card;

public class ServerGame {
	protected Deck deck = new Deck();
	protected ArrayList<Integer> playSequenceID = new ArrayList<Integer>();
	
	public ServerGame() {
		
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
			JSONObject json = ServerModel.clients.get(i).createJsonArray("Cards", lists.get(i));
			ServerModel.clients.get(i).send(json);
		}
	}

}
