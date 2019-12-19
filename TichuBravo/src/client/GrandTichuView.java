package client;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * @author Loris
 *
 */
public class GrandTichuView extends BorderPane { 
	protected Button yesButton;
	protected Button noButton;
	protected Label infoLAbel;
	protected VBox middleBox;
	
	
	public GrandTichuView() {
		this.yesButton=new Button("Yes");
		this.yesButton.setId("yesButton");
		this.noButton=new Button("No");
		this.noButton.setId("noButton");
		this.infoLAbel=new Label("Do You Want To Say Grand Tichu?");
		this.infoLAbel.setId("infoLabel");
		this.middleBox=new VBox(this.infoLAbel,this.yesButton,this.noButton);
		this.middleBox.setAlignment(Pos.CENTER);
		this.middleBox.setSpacing(20);
		this.setCenter(this.middleBox);
		
		
	}
}
