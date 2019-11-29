package client;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Tim
 *
 */
public class ChatView  extends VBox{	
	protected HBox topBox,middleBox,textFieldAndButtonHBox;
	protected VBox bottomBox;
	protected Label teamOneLabel, teamOneScoreLabel, teamTwoLabel, teamTwoScoreLabel, ChatLabel,winnerLabel;
	protected TextArea chatTextArea;
	protected TextField chatTextField;
	protected Button sendButton;
	
	public ChatView() {
		//topBox
		this.teamOneLabel=new Label("Team One");
		this.teamOneLabel.setId("teamOneLabel");
		this.teamOneScoreLabel=new Label("Score");
		this.teamOneScoreLabel.setId("teamOneScoreLabel");
		this.topBox=new HBox(this.teamOneLabel,this.teamOneScoreLabel);
		
		//middleBox
		this.teamTwoLabel=new Label("Team Two");
		this.teamTwoLabel.setId("teamTwoLabel");
		this.teamTwoScoreLabel= new Label("Score");
		this.teamTwoScoreLabel.setId("teamTwoScoreLabel");
		this.middleBox=new HBox(this.teamTwoLabel,this.teamTwoScoreLabel);
		
		//WinnerLabel
		this.winnerLabel=new Label();
		this.winnerLabel.setId("winnerLabel");
		
		//bottomBox
		this.ChatLabel=new Label("Chat");
		this.ChatLabel.setId("chatLabel");
		this.chatTextArea=new TextArea("");
		this.chatTextArea.setId("chatTextArea");
		this.chatTextField=new TextField();
		this.chatTextField.setId("chatTextField");
		this.sendButton=new Button("Send");
		this.sendButton.setId("sendButton");
		this.textFieldAndButtonHBox=new HBox(this.chatTextField,this.sendButton);
		this.textFieldAndButtonHBox.setAlignment(Pos.CENTER);
		this.bottomBox=new VBox(this.ChatLabel,this.chatTextArea,this.textFieldAndButtonHBox);
		
		//container
		this.getChildren().addAll(this.topBox,this.middleBox,this.winnerLabel,this.bottomBox);
		this.setAlignment(Pos.CENTER);
	}

}
