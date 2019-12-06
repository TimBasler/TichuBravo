package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import common.MsgType;

/**
 * @author Dominik
 *
 */
public class ServerClient {

	private String name;
	private Socket socket;
	private ServerModel model;

	/**
	 * constructor starts a threat for this object
	 * 
	 * @param socket
	 * @param name
	 * @param model
	 */
	public ServerClient(Socket socket, String name, ServerModel model) {
		this.name = name;
		this.socket = socket;
		this.model = model;

		// read messages from clients and send to all clients
		Runnable r = new Runnable() {
			@Override
			public void run() {

				while (true) {
					decide(read());
				}

			}
		};
		Thread t = new Thread(r);
		t.start();
	}

	public void decide(JSONObject json) {
		if (json.containsKey(MsgType.name.toString()) || json.containsKey(MsgType.msg.toString())||
				json.containsKey(MsgType.pointsTeamOne.toString())||json.containsKey(MsgType.pointsTeamTwo.toString())||
				json.containsKey(MsgType.winnerLabelTeamOne.toString())||json.containsKey(MsgType.winnerLabelTeamTwo.toString())||json.containsKey(MsgType.currentPlayerName.toString())) {
			model.broadcast(json);
		} else if (json.containsKey(MsgType.game.toString())) {
			model.sspGame.set("");
			model.sspGame.set((String) json.get(MsgType.game.toString()));
			
		} else if (json.containsKey(MsgType.turn.toString())) {
			model.game.passCounter.set(0);
			model.game.lastMove.set(model.game.currentPlayerID.get());
			model.game.nextPlayer();
			model.broadcast(json);
			
		}else if (json.containsKey(MsgType.whoHasMahJong.toString())) {
			model.game.newSequence(Integer.parseInt((String) json.get(MsgType.whoHasMahJong.toString())));
			
		} else if (json.containsKey(MsgType.player.toString())) {
			String[] strings = ((String) json.get(MsgType.player.toString())).split(",");
			int ID = Integer.parseInt(strings[0]);
			boolean isTeamOne = Boolean.parseBoolean(strings[1]);
			Player player = model.game.createPlayer(ID, isTeamOne);
			model.game.players.add(player);
			if (player.isTeamOne() != isTeamOne) {
				model.broadcast(createJson(MsgType.teamChange.toString(), ID+""));
			}
			
		} else if (json.containsKey(MsgType.pass.toString())) {
			model.game.passCounter.set(model.game.passCounter.get()+1);
			if(model.game.passCounter.get() != 4) {
				model.game.nextPlayer();
			}

		} else {
			// wrong key
		}
	}

	/**
	 * write the content from a Json on a ServerClient socket
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
	 * read from a ServerClient socket and parse the content to a json
	 * 
	 * @return
	 */
	public JSONObject read() {
		JSONObject json = null;
		String inputString = null;

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			inputString = in.readLine();

		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONParser parser = new JSONParser();
		try {
			json = (JSONObject) parser.parse(inputString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	public String readString() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String inputString = in.readLine();
			return inputString;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * create a Json object with a key and a value
	 * 
	 * @param key
	 * @param value
	 * @return json
	 */
	public static JSONObject createJson(String key, String value) {
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

	// getter
	public Socket getSocket() {
		return this.socket;
	}

	/**
	 * stop the socket
	 */
	public void stop() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
