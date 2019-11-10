package server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.simple.JSONObject;

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
	public int clientId = 0;

	// TODO client name einlesen

	/**
	 * for each request create a ServerClient and add it to the list
	 * 
	 * @param port
	 */

	public void serverStart(int port) {
		try {
			serverSocket = new ServerSocket(port);
			Runnable r = new Runnable() {
				@Override
				public void run() {
					while (!stop) {
						if (clientId <= 4) {
							try {

								Socket socket = serverSocket.accept();
								ServerClient c = new ServerClient(socket, "client ", ServerModel.this);

								// Set the id's
								clientId++;
								OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
								JSONObject json = new JSONObject();
								json.put("clientId", clientId + "");
								out.write(json.toString() + "\n");
								out.flush();
								clients.add(c);

								if (clients.size() > 0) {
									JSONObject json2 = new JSONObject();
									OutputStreamWriter out2 = new OutputStreamWriter(socket.getOutputStream());
									json2.put("disableConnect", "true");
									out2.write(json2.toString() + "\n");
									out2.flush();
								}

							} catch (IOException e) {
								e.printStackTrace();
								// break;
							}
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
	 * 
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
