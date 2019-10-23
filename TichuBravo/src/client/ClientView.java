package client;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientView {
	protected Stage stage;
	private ClientModel clientModel;
	
	protected Scene lobbyScene,gameScene;
	
	protected LobbyView lobbyView;
	protected GameView gameView; //GameView = ContorlArea

	public ClientView(Stage stage,ClientModel clientModel) {
		this.stage=stage;
		this.clientModel=clientModel;
		
		this.lobbyView=new LobbyView();
		this.gameView=new GameView();
		
		lobbyScene=new Scene(lobbyView,1000,800);
		lobbyScene.getStylesheets().add(getClass().getResource("lobbyStyle.css").toExternalForm());
		this.lobbyView.setId("lobbyView");
		
		gameScene=new Scene(gameView);
		gameScene.getStylesheets().add(getClass().getResource("gameStyle.css").toExternalForm());
		
		stage.setScene(lobbyScene);
		stage.setTitle("Tichu");
		stage.show();
	}
	
	public void start() {
		this.stage.show();
	}

}
