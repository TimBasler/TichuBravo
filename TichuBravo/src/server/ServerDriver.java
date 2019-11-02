package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerDriver {
	
	
public static void main(String[] args) {
	ServerSocket listener;
	try {
		listener = new ServerSocket(8000, 10, null);
		Runnable r = new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Socket socket = listener.accept();
						
						BufferedReader in;
						
						try {
							in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
							String msgText = in.readLine(); // Will wait here for complete line
							System.out.println(msgText);
						} catch (IOException e) {
							
						}
						
					} catch (Exception e) {
						
					}
				}
			}
		};
		Thread t = new Thread(r, "ServerSocket");
		t.start();
	} catch (IOException e) {
		
	}
		
	}

}
