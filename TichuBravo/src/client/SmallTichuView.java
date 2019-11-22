package client;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class SmallTichuView extends BorderPane { 
	protected Button yesButton;
	protected Button noButton;
	protected Label infoLAbel;
	
	public SmallTichuView() {
		this.yesButton=new Button("Yes");
		this.noButton=new Button("No");
		this.infoLAbel=new Label("Do You Want To Say Small Tichu?");
		
		this.setTop(this.infoLAbel);
		this.setLeft(this.yesButton);
		this.setRight(this.noButton);
	}
}
