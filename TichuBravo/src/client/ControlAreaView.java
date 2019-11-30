package client;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * @author Tim
 *
 */
public class ControlAreaView extends VBox {
	protected Button passButton, quitGameButton, confirmButton,smallTichu,resetSelectedCardsButton;
	protected Region spacer;
	protected HBox buttonBox,spacerBox;
	
	public ControlAreaView() {
		this.buttonBox=new HBox();
		this.spacerBox=new HBox();
		this.spacerBox.setStyle("-fx-pref-height:0.25cm");
		
		this.spacer=new Region();
		this.spacer.setId("spacer");
		
		this.passButton=new Button("Pass");
		this.passButton.setId("passButton");
		this.passButton.setDisable(true);
		
		this.quitGameButton=new Button("Quit");
		this.quitGameButton.setId("quitGameButton");
		
		this.confirmButton=new Button("Confirm");
		this.confirmButton.setId("confirmButton");
		this.confirmButton.setDisable(true);
		
		this.smallTichu=new Button("Small Tichu");
		this.smallTichu.setId("smallTichu");
		
		this.resetSelectedCardsButton=new Button("Delete Selected Cards");
		this.resetSelectedCardsButton.setId("resetSelectedCardsButton");
		
		this.buttonBox.getChildren().addAll(this.spacer,this.passButton,this.confirmButton,this.smallTichu,this.resetSelectedCardsButton,this.quitGameButton);
		this.buttonBox.setAlignment(Pos.CENTER_LEFT);
		this.buttonBox.setSpacing(90);
		
		
		this.getChildren().addAll(this.buttonBox,this.spacerBox);
		
	}

}
