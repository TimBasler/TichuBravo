package client;

import java.io.IOException;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

/**
 * @author Tim
 *
 */
public class ClientController {
	private ClientModel clientModel;
	private ClientView clientView;
	int id = 1;

	public ClientController(ClientModel clientModel, ClientView clientView) {
		this.clientModel = clientModel;
		this.clientView = clientView;
		
		clientModel.sspMsg.addListener((o, oldValue, newValue) -> {
			System.out.println(newValue);
			clientView.gameView.chatView.chatTextArea.appendText(newValue + "\n");
		});

		clientModel.sspName.addListener((o, oldValue, newValue) -> {
			clientView.gameView.chatView.chatTextArea.appendText(newValue + "\n");
		});

		clientView.gameView.button.setOnAction(e -> {
			clientView.stage.setScene(clientView.lobbyScene);
		});

		clientView.lobbyView.btnConnect.setOnAction(event -> {
				clientModel.connect();
		});

		clientView.gameView.chatView.sendButton.setOnAction(e -> {
			clientModel.send(clientModel.createJson(MsgType.msg.toString(),
					clientView.gameView.chatView.chatTextField.getText()));
		});

		clientView.lobbyView.sendBtn.setOnAction(e -> {
			clientModel.send(clientModel.createJson(MsgType.msg.toString(), "Hallo aldaksjföl"));
		});

		clientView.lobbyView.loginButton.setOnAction(e -> {
			clientModel.send(
					clientModel.createJson(MsgType.name.toString(), clientView.lobbyView.userTextField.getText()));
			clientView.stage.setScene(clientView.gameScene);

			clientModel.playerName = clientView.lobbyView.userTextField.getText();
			clientModel.isTeamOne = clientView.lobbyView.teamOne.isSelected();
			clientModel.createPlayer();

			System.out.println(clientModel.player);
		});

	}

}
