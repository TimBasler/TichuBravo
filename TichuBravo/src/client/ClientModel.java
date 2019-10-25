package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Dominik
 * 
 */
public class ClientModel {
	protected Socket socket;
	protected int portNr;
	protected String ip = null;
	protected SimpleStringProperty sspName = new SimpleStringProperty();
	protected SimpleStringProperty sspMsg = new SimpleStringProperty();
	protected SimpleStringProperty sspTurn = new SimpleStringProperty();
	protected SimpleStringProperty sspGame = new SimpleStringProperty();

	public ClientModel() {

	}
	
	//create a socket and connect to the server with ip and port number
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

	// write the content from a Json on the socket
	public void send(JSONObject json) {
		try (OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());) {
			out.write(json.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// read from the socket and parse the content to a json
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

	// depending on the type, the message will be stored at a different location
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
			sspTurn.set("");
			sspTurn.set((String) json.get(MsgType.turn.toString()));
		}
		if (json.containsKey(MsgType.game.toString())) {
			sspGame.set("");
			sspGame.set((String) json.get(MsgType.game.toString()));
		}
	}

	// create a Json object with a key and a value
	public JSONObject createJson(String key, String value) {
		JSONObject json = new JSONObject();
		json.put(key, value);
		return json;
	}

	// close the socket
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
