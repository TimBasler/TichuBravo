package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.json.simple.JSONObject;

public class ServerModel {
	private ServerSocket serverSocket;
	private boolean stop = false;
	public static ArrayList<ServerClient> clients = new ArrayList<>();
//TODO client name einlesen
	// for each request create a ServerClient and add it to the list
	public void serverStart(int port) {
		try {
			serverSocket = new ServerSocket(port);
			Runnable r = new Runnable() {
				public void run() {
					while (!stop) {
						try {
							Socket socket = serverSocket.accept();
							ServerClient c = new ServerClient(socket, "client ", ServerModel.this);
							clients.add(c);
						} catch (IOException e) {
							e.printStackTrace();
							break;
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
	
	// stop each client and close the serverSocket
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

	// send the received message to all clients
	public void broadcast(JSONObject json) {
		for (ServerClient sc : clients) {
			sc.send(json);
		}
	}
}
