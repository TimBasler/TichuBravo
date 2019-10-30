package client;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameView extends BorderPane {
	protected VBox rightBox;
	
	protected HBox teamOneBox;
	protected Label teamOneLabel;
	protected Label pointsOfTeamOneLabel;
	
	protected HBox teamTwoBox;
	protected Label teamTwoLabel;
	protected Label pointsOfTeamTwoLabel;
	
	protected Label chatLabel;
	
	protected TextArea chatTextArea;
	
	protected HBox writeAndSendBox;
	protected TextField chatTextField;
	protected Button sendButton;
	
	protected Button button;//TODO delete this Button
	
	public GameView() {
		this.rightBox=new VBox();
		
		this.teamOneBox=new HBox();
		this.teamOneLabel=new Label("Team One");
		this.teamOneLabel.setId("teamOneLabel");
		this.pointsOfTeamOneLabel=new Label("points");
		this.pointsOfTeamOneLabel.setId("pointsOfTeamOneLabel");
		this.teamOneBox.getChildren().addAll(this.teamOneLabel,this.pointsOfTeamOneLabel);
		
		this.teamTwoBox=new HBox();
		this.teamTwoLabel=new Label("Team Two");
		this.teamTwoLabel.setId("teamTwoLabel");
		this.pointsOfTeamTwoLabel=new Label("points");
		this.pointsOfTeamTwoLabel.setId("pointsOfTeamTwoLabel");
		this.teamTwoBox.getChildren().addAll(this.teamTwoLabel,this.pointsOfTeamTwoLabel);
		
		this.chatLabel=new Label("Chat");
		this.chatLabel.setId("chatLabel");
		
		this.chatTextArea=new TextArea();
		this.chatTextArea.setId("chatTextArea");
		
		this.writeAndSendBox=new HBox();
		this.chatTextField=new TextField();
		this.chatTextField.setId("chatTextField");
		this.sendButton=new Button("Send");
		this.sendButton.setId("sendButton");
		this.writeAndSendBox.getChildren().addAll(this.chatTextField,this.sendButton);
		
		this.rightBox.getChildren().addAll(this.teamOneBox,this.teamTwoBox,this.chatLabel,this.chatTextArea,this.writeAndSendBox);
		
		this.setRight(this.rightBox);

		//TODO delete this button
		this.button=new Button("wechsle in die Lobby");
		this.setLeft(button);
		
		
		
		
	
	}
	

}
