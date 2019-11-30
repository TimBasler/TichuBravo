package client;

import common.Card;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Tim
 *
 */
public class GameView extends BorderPane {
	protected BoardView boardView;
	protected ChatView chatView;
	protected ControlAreaView controlAreaView;	

	//TODO delete this button
	Button button =new Button("wechsle in die Lobby");
	
	public GameView() {
		this.setId("gameView");
		this.getStylesheets().add(getClass().getResource("gameStyle.css").toExternalForm());
		
		this.boardView=new BoardView();
		this.setCenter(boardView);
		this.boardView.setAlignment(Pos.CENTER);
		//this.boardView.setStyle("-fx-background-color:green");
		
		this.chatView=new ChatView();
		this.setRight(chatView);
		//this.chatView.setStyle("-fx-background-color:yellow");
		
		this.controlAreaView=new ControlAreaView();
		this.setBottom(controlAreaView);
		//this.controlAreaView.setStyle("-fx-background-color:blue");
	}
	
}
