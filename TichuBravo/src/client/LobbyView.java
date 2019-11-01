package client;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * @author Tim
 *
 */
public class LobbyView  extends BorderPane {
	protected Label userLabel,chooseLabel;
	protected TextField userTextField;
	protected RadioButton teamOne,teamTwo;
	protected ToggleGroup toggleGroup;
	protected Button loginButton;
	protected VBox vBox;
	
	protected int playerDefaultNumber=1;
	
	public LobbyView() {
		this.userLabel=new Label("Username");
		this.userLabel.setId("userLabel");
		this.userTextField=new TextField();
		this.userTextField.setText("Player "+this.playerDefaultNumber);
		this.userTextField.setId("textField");
		this.chooseLabel=new Label("Choose your Team");
		this.chooseLabel.setId("chooseLabel");
		this.toggleGroup=new ToggleGroup();
		this.teamOne=new RadioButton("Team one");
		this.teamOne.setId("teamOne");
		this.teamOne.setToggleGroup(toggleGroup);
		this.teamTwo= new RadioButton("Team two");
		this.teamTwo.setId("teamTwo");
		this.teamTwo.setToggleGroup(toggleGroup);
		this.loginButton=new Button("Login");
		this.loginButton.setId("loginButton");
		
		this.vBox=new VBox();
		this.vBox.getChildren().addAll(this.userLabel,this.userTextField,this.chooseLabel,this.teamOne,this.teamTwo,this.loginButton);
		this.vBox.setAlignment(Pos.CENTER);
		this.setCenter(this.vBox);
	}
	

}
