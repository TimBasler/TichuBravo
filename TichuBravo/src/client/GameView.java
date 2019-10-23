package client;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class GameView extends BorderPane {
	private Label label;
	protected Button button;
	
	public GameView() {
		this.label=new Label("Das ist das Game");
		this.button=new Button("wechsle in die Lobby");
		this.setCenter(button);
		this.setTop(label);
	}
	

}
