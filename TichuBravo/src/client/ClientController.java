package client;

/**
 * @author Tim
 *
 */
public class ClientController {
	private ClientModel clientModel;
	private ClientView clientView;

	private int isClicked = 0;
	private int isPlayerOneCounter = 0;
	private int isPlayerTwoCounter = 0;

	Player player;

	public ClientController(ClientModel clientModel, ClientView clientView) {
		this.clientModel = clientModel;
		this.clientView = clientView;

		

		clientModel.sspName.addListener((o, oldValue, newValue) -> {
			System.out.println(newValue);
		});
		
		clientView.lobbyView.loginButton.setOnAction(e -> {
			clientModel.send(clientModel.createJson(MsgType.name.toString(), "blablabla"));
		});

	clientView.gameView.button.setOnAction(e-> {
		clientView.stage.setScene(clientView.lobbyScene);
	});

}

}
