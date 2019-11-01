package client;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * @author Tim
 *
 */
public class ControlAreaView extends HBox {
	protected Button passButton, quitGameButton, confirmButton,smallTichu,grandTichu,showRulesButton;
	
	public ControlAreaView() {
		this.passButton=new Button("Pass");
		this.passButton.setId("passButton");
		
		this.quitGameButton=new Button("Quit Game");
		this.quitGameButton.setId("quitGameButton");
		
		this.confirmButton=new Button("Confirm");
		this.confirmButton.setId("confirmButton");
		
		this.smallTichu=new Button("Small Tichu");
		this.smallTichu.setId("smallTichu");
		
		this.grandTichu=new Button("Grand Tichu");
		this.grandTichu.setId("grandTichu");
		
		this.showRulesButton=new Button("Show Rules");
		this.showRulesButton.setId("showRulesButton");
		
		this.getChildren().addAll(this.passButton,this.quitGameButton,this.confirmButton,this.smallTichu,this.grandTichu,this.showRulesButton);
		this.setAlignment(Pos.CENTER_LEFT);
		this.setSpacing(100);
		
	}

}
