package client;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * @author Tim
 *
 */
public class ControlAreaView extends HBox {
	protected Button passButton, quitGameButton, confirmButton,smallTichu,resetSelectedCardsButton;
	
	public ControlAreaView() {
		this.passButton=new Button("Pass");
		this.passButton.setId("passButton");
		this.passButton.setDisable(true);
		
		this.quitGameButton=new Button("Quit Game");
		this.quitGameButton.setId("quitGameButton");
		
		this.confirmButton=new Button("Confirm");
		this.confirmButton.setId("confirmButton");
		this.confirmButton.setDisable(true);
		
		this.smallTichu=new Button("Small Tichu");
		this.smallTichu.setId("smallTichu");
		
		
		this.resetSelectedCardsButton=new Button("Delete Selected Cards");
		this.resetSelectedCardsButton.setId("resetSelectedCardsButton");
		
		this.getChildren().addAll(this.passButton,this.quitGameButton,this.confirmButton,this.smallTichu,this.resetSelectedCardsButton);
		this.setAlignment(Pos.CENTER_LEFT);
		this.setSpacing(90);
		
	}

}
