package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import common.Card;
import common.MsgType;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

/**
 * @author Dominik
 * 
 */
public class ClientModel {
	private Socket socket;
	protected int portNr = 4444;
	protected String ip = "localhost";
	protected SimpleStringProperty sspName = new SimpleStringProperty();
	protected SimpleStringProperty sspMsg = new SimpleStringProperty();
	protected SimpleStringProperty sspGame = new SimpleStringProperty();
	protected SimpleIntegerProperty sipPointsTeamOne=new SimpleIntegerProperty();
	protected SimpleIntegerProperty sipPointsTeamTwo= new SimpleIntegerProperty();
	protected SimpleStringProperty sspWinnerLabelTeamOne=new SimpleStringProperty();
	protected SimpleStringProperty sspWinnerLabelTeamTwo=new SimpleStringProperty();
	protected String playerName;
	protected boolean isTeamOne;
	protected int clientId;
	protected Player player = new Player();
	protected int scoreTeamOne = 0;
	protected int ScoreTeamTwo = 0;

	public void createPlayer() {
		player.setPlayerName(playerName);
		player.setIsTeamOne(isTeamOne);
		player.setPlayerID(clientId);
	}

	public ClientModel() {

	}

	/**
	 * create a socket and connect to the server with ip and port number
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void connect() {
		try {
			// connection
			socket = new Socket(ip, portNr);
			Runnable r = new Runnable() {
				@Override
				public void run() {

					// read and save the message
					while (true) {
						saveInput(read());
					}

				}
			};
			Thread t = new Thread(r);
			t.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * write the content from a Json on the socket
	 * 
	 * @param json
	 */
	public void send(JSONObject json) {
		try {
			OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
			out.write(json.toString() + "\n");
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendString(String s) {
		try {
			OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
			out.write(s + "\n");
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * read from the socket and parse the content to a json
	 * 
	 * @return
	 */
	public JSONObject read() {
		JSONObject json = null;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String inputString = in.readLine();

			JSONParser parser = new JSONParser();
			json = (JSONObject) parser.parse(inputString);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return json;
	}

	public String readString() {
		String inputString = null;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			inputString = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputString;
	}

	/**
	 * depending on the type, the message will be stored at a different location all
	 * cards with the key "turn" are saved in an arraylist
	 * 
	 * @param json
	 */
	public void saveInput(JSONObject json) {
		if (json.containsKey(MsgType.clientId.toString())) {
			this.clientId = Integer.parseInt((String) json.get(MsgType.clientId.toString()));
		}

		if (json.containsKey(MsgType.name.toString())) {
			sspName.set("");
			sspName.set((String) json.get(MsgType.name.toString()));
		}
		if (json.containsKey(MsgType.msg.toString())) {
			sspMsg.set("");
			sspMsg.set((String) json.get(MsgType.msg.toString()));
		}
		if (json.containsKey(MsgType.turn.toString())) {
			player.table.clear();
			JSONArray list = (JSONArray) json.get(MsgType.turn.toString());
			for (int i = 0; i < list.size(); i++) {
				Card c = Card.makeCard((String) list.get(i));
				player.table.add(c);
			}
		}
		if (json.containsKey(MsgType.game.toString())) {
			sspGame.set("");
			sspGame.set((String) json.get(MsgType.game.toString()));
		}
		if (json.containsKey(MsgType.cards.toString())) {
			player.allCardsReceived.set(false);
			
			
			JSONArray list = (JSONArray) json.get(MsgType.cards.toString());
			for (int i = 0; i < list.size(); i++) {
				Card c = Card.makeCard((String) list.get(i));
				if (c.isSpecial())
					player.specialCardList.add(c);
				else
					player.normalCardList.add(c);
			}
		}
		if (json.containsKey(MsgType.currentPlayerID.toString())) {
			player.myTurn.set(Integer.parseInt((String) json.get(MsgType.currentPlayerID.toString())));
		}
		if (json.containsKey(MsgType.winnerOfTheRound.toString())) {
			player.winnerOfTheRound.set(Integer.parseInt((String) json.get(MsgType.winnerOfTheRound.toString())));
		}
		
		if(json.containsKey(MsgType.pointsTeamOne.toString())) {
			sipPointsTeamOne.set(Integer.parseInt((String) json.get(MsgType.pointsTeamOne.toString())));
		}
		
		if(json.containsKey(MsgType.pointsTeamTwo.toString())) {
			sipPointsTeamTwo.set(Integer.parseInt((String) json.get(MsgType.pointsTeamTwo.toString())));
		}
		
		if(json.containsKey(MsgType.winnerLabelTeamOne.toString())) {
			sspWinnerLabelTeamOne.set((String) json.get(MsgType.winnerLabelTeamOne.toString()));
		}
		
		if(json.containsKey(MsgType.winnerLabelTeamTwo.toString())) {
			sspWinnerLabelTeamTwo.set((String) json.get(MsgType.winnerLabelTeamTwo.toString()));
		}
		

		if (json.containsKey(MsgType.teamChange.toString())) {
			player.teamChange.set(Integer.parseInt((String) json.get(MsgType.teamChange.toString())));
		}

		if (json.containsKey(MsgType.playerIDOne.toString()) && clientId == 1) {
			JSONArray list = (JSONArray) json.get(MsgType.playerIDOne.toString());
			for (int i = 0; i < list.size(); i++) {
				Card c = Card.makeCard((String) list.get(i));
				player.earnedCards.add(c);
			}
		}
		if (json.containsKey(MsgType.playerIDTwo.toString()) && clientId == 2) {
			JSONArray list = (JSONArray) json.get(MsgType.playerIDTwo.toString());
			for (int i = 0; i < list.size(); i++) {
				Card c = Card.makeCard((String) list.get(i));
				player.earnedCards.add(c);
			}
		}
		if (json.containsKey(MsgType.playerIDThree.toString()) && clientId == 3) {
			JSONArray list = (JSONArray) json.get(MsgType.playerIDThree.toString());
			for (int i = 0; i < list.size(); i++) {
				Card c = Card.makeCard((String) list.get(i));
				player.earnedCards.add(c);
			}
		}
		if (json.containsKey(MsgType.playerIDFour.toString()) && clientId == 4) {
			JSONArray list = (JSONArray) json.get(MsgType.playerIDFour.toString());
			for (int i = 0; i < list.size(); i++) {
				Card c = Card.makeCard((String) list.get(i));
				player.earnedCards.add(c);
			}
		}

	}

	/**
	 * create a Json object with a key and a value
	 * 
	 * @param key
	 * @param value
	 * @return json
	 */
	public JSONObject createJson(String key, String value) {
		JSONObject json = new JSONObject();
		json.put(key, value);
		return json;
	}

	/**
	 * create Json with an array
	 * 
	 * @param key
	 * @param strings
	 * @return JSONObject
	 */
	public JSONObject createJsonArray(String key, ArrayList<String> strings) {
		JSONObject json = new JSONObject();
		JSONArray list = new JSONArray();
		for (String s : strings) {
			list.add(s);
		}
		json.put(key, list);
		return json;
	}

	/**
	 * close the socket
	 */
	public void disconnect() {
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// count Points Team one
	public void countPointsFromTeamOne(ObservableList<Card> earnedCards) {
		int points = 0;
		for (Card c : earnedCards) {
			if (c.isSpecial() == false) {// Normal Cards
				switch (c.getRank().ordinal()) {
				case 3:
					points += 5;
					break;
				case 8:
					points += 10;
					break;
				case 11:
					points += 10;
					break;
				default: // no such cards found
					break;
				}
			}

			if (c.isSpecial()) {
				switch (c.getSpecialCard().ordinal()) {// Spezial Cards
				case 2:
					points -= 25;
					break;
				case 3:
					points += 25;
					break;
				default: // no such cards found
					break;
				}
			}

		}

		this.sipPointsTeamOne.set(points); 
	}

	// count Points Team two
	public void countPointsFromTeamTwo(ObservableList<Card> earnedCards) {
		int points = 0;
		for (Card c : earnedCards) {
			if (c.isSpecial() == false) {// Normal Cards
				switch (c.getRank().ordinal()) {
				case 3:
					points += 5;
					break;
				case 8:
					points += 10;
					break;
				case 11:
					points += 10;
					break;
				default: // no such cards found
					break;
				}
			}

			if (c.isSpecial()) {
				switch (c.getSpecialCard().ordinal()) {// Spezial Cards
				case 2:
					points -= 25;
					break;
				case 3:
					points += 25;
					break;
				default: // no such cards found
					break;
				}
			}

		}
		this.sipPointsTeamTwo.set(points);
	}

}
