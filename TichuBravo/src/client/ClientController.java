package client;

public class ClientController {
	private ClientModel clientModel;
	private ClientView clientView;
	
	public ClientController(ClientModel clientModel, ClientView clientView) {
		this.clientModel=clientModel;
		this.clientView=clientView;
		
		clientView.lobbyView.loginButton.setOnAction(e->{
			clientView.stage.setScene(clientView.gameScene);
		});
		
		clientView.gameView.button.setOnAction(e->{
			clientView.stage.setScene(clientView.lobbyScene);
		});
		
	}

}
