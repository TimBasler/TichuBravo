package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import common.Card;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Dominik
 *
 */
public class ServerModel {
	private ServerSocket serverSocket;
	private boolean stop = false;
	protected final ObservableList<ServerClient> clients = FXCollections.observableArrayList();
	protected SimpleStringProperty sspGame = new SimpleStringProperty();
	protected Deck deck = new Deck();
	
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
		for (int i = 0; i < clients.size(); i++) {
			JSONObject json = clients.get(i).createJsonArray("Cards", lists.get(i));
			clients.get(i).send(json);
		}
	}
	
	

	
//TODO client name einlesen
	
	/**
	 * for each request create a ServerClient and add it to the list
	 * @param port
	 */
	public void serverStart(int port) {
		try {
			serverSocket = new ServerSocket(port);
			Runnable r = new Runnable() {
				@Override
				public void run() {
					while (!stop) {
						try {
							Socket socket = serverSocket.accept();
							ServerClient c = new ServerClient(socket, "client ", ServerModel.this);
							clients.add(c);
						} catch (IOException e) {
							e.printStackTrace();
							//break;
						}
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
		 * stop each client and close the serverSocket
		 */
		public void stopServer() {
			for (ServerClient sc : clients)
				sc.stop();
			stop = true;
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	/**
	 * send the received message to all clients
	 * @param json
	 */
	public void broadcast(JSONObject json) {
		for (ServerClient sc : clients) {
			sc.send(json);
		}
	}
	
	public void broadcastString(String s) {
		for (ServerClient sc : clients) {
			sc.sendString(s);
		}
	}
	
	
}
