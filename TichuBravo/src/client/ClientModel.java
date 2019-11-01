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

import javafx.beans.property.SimpleStringProperty;

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
	protected ArrayList<String> turn = new ArrayList<>();

	public ClientModel() {
		try {
			connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * create a socket and connect to the server with ip and port number
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void connect() throws UnknownHostException, IOException {
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
	}

	/**
	 * write the content from a Json on the socket
	 * 
	 * @param json
	 */
	public void send(JSONObject json) {
		if (socket == null)System.out.println("socket = null - ClientModel");
		try (OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());) {
			out.write(json.toString());
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
		try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
			String inputString = in.readLine();
			JSONParser parser = new JSONParser();
			json = (JSONObject) parser.parse(inputString);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * depending on the type, the message will be stored at a different location
	 * all cards with the key "turn" are saved in an arraylist
	 * @param json
	 */
	public void saveInput(JSONObject json) {
		if (json.containsKey(MsgType.name.toString())) {
			sspName.set("");
			sspName.set((String) json.get(MsgType.name.toString()));
		}
		if (json.containsKey(MsgType.msg.toString())) {
			sspMsg.set("");
			sspMsg.set((String) json.get(MsgType.msg.toString()));
		}
		if (json.containsKey(MsgType.turn.toString())) {
			turn.clear();
			JSONArray list = (JSONArray) json.get(MsgType.turn.toString());
			for (int i = 0; i < list.size(); i++) {
				turn.add((String) list.get(i));
			}
		}
		if (json.containsKey(MsgType.game.toString())) {
			sspGame.set("");
			sspGame.set((String) json.get(MsgType.game.toString()));
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
	 * @param key
	 * @param strings
	 * @return JSONObject
	 */
	public JSONObject createJsonArray(String key, String...strings) {
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

}
