package server;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Dominik
 *
 */
public class ServerView {
	private Stage stage;
	private ServerModel model;
	protected TextArea textArea;
	protected Button startBtn;
	protected Label portLabel;
	protected TextField tfPort;

	public ServerView(Stage stage, ServerModel model) {
		this.stage = stage;
		this.model = model;

		VBox root = new VBox();
		startBtn = new Button("Start");
		textArea = new TextArea();
		textArea.setEditable(false);
		portLabel = new Label("Server - PortNr: ");
		tfPort = new TextField("4444"); 
		HBox hbox = new HBox(portLabel, tfPort, startBtn);

		root.getChildren().addAll(hbox, textArea);

		Scene scene = new Scene(root, 300, 200);
		stage.setTitle("Server");
		stage.setScene(scene);
	}

	public void start() {
		stage.show();
	}

	public void stop() {
		stage.hide();
	}

	public Stage getStage() {
		return stage;
	}

}
