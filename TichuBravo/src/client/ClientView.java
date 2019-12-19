package client;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Loris
 *
 */
public class ClientView {
	protected Stage stage ,grandTichuStage;
	private ClientModel clientModel;
	
	protected Scene lobbyScene,gameScene,grandTichuScene;
	
	protected LobbyView lobbyView;
	protected GameView gameView; //GameView = ContorlArea
	protected GrandTichuView grandTichuView;

	public ClientView(Stage stage,ClientModel clientModel) {
		
		this.stage=stage;
		this.clientModel=clientModel;
		
		this.lobbyView=new LobbyView();
		this.gameView=new GameView();
		
		lobbyScene=new Scene(lobbyView,1550,830);
		lobbyScene.getStylesheets().add(getClass().getResource("lobbyStyle.css").toExternalForm());
		this.lobbyView.setId("lobbyView");
		
		gameScene=new Scene(gameView,1550,830);
		gameScene.getStylesheets().add(getClass().getResource("gameStyle.css").toExternalForm());
		
		this.grandTichuView=new GrandTichuView();
		this.grandTichuStage=new Stage();
		this.grandTichuScene=new Scene(grandTichuView,310,200);
		this.grandTichuView.setId("grandTichuView");
		grandTichuScene.getStylesheets().add(getClass().getResource("grandTichuViewStyle.css").toExternalForm());
		
		
		stage.setScene(lobbyScene);
		stage.setTitle("Tichu");
		stage.show();
	}
	
	public void start() {
		this.stage.show();
	}

}
