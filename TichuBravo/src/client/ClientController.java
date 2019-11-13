package client;

import java.io.IOException;

import common.Card;
import javafx.collections.ListChangeListener;


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

		clientModel.sspMsg.addListener((o, oldValue, newValue) -> {
			clientView.gameView.chatView.chatTextArea.appendText(newValue+"\n");
		});

		clientModel.sspName.addListener((o, oldValue, newValue) -> {
		});
		
		clientModel.cardList.addListener((ListChangeListener<? super Card>) (e -> {
			System.out.println(clientModel.cardList);
		}));
		

	clientView.gameView.button.setOnAction(e-> {
		clientView.stage.setScene(clientView.lobbyScene);
	});
	
	clientView.lobbyView.btnConnect.setOnAction( event -> {
			clientModel.connect();
	});
	
	clientView.gameView.chatView.sendButton.setOnAction(e -> {
		
		clientModel.send(clientModel.createJson(MsgType.msg.toString(), clientView.gameView.chatView.chatTextField.getText()));
		
	});
	
	clientView.lobbyView.sendBtn.setOnAction(e -> {
		clientModel.send(clientModel.createJson(MsgType.game.toString(), "dealCards"));	//nur zum testen
	});
	
	
	clientView.lobbyView.loginButton.setOnAction(e -> {
		clientView.stage.setScene(clientView.gameScene);
	});

}

}
