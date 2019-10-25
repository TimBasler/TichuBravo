package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Dominik
 *
 */
public class ServerClient {

	private String name;
	private Socket socket;
	private ServerModel model;

	/**
	 * constructor
	 * starts a threat for this object
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
					model.broadcast(read());
				}
			}
		};
		Thread t = new Thread(r);
		t.start();
	}

	/**
	 * write the content from a Json on a ServerClient socket
	 * @param json
	 */
	public void send(JSONObject json) {
		try (OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());){
			out.write(json.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * read from a ServerClient socket and parse the content to a json
	 * @return
	 */
	public JSONObject read() {
		JSONObject json = null;
		try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));){
			String inputString = in.readLine();
			JSONParser parser = new JSONParser();
			json = (JSONObject) parser.parse(inputString);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return json;
	}

	//getter
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
