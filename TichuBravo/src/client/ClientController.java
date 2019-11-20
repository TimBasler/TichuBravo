package client;

import java.io.IOException;
import java.util.ArrayList;

import common.Card;
import common.Card.Rank;
import common.Card.Suit;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;

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
		
		/*
		 * clientModel.player.hasMahJong.addListener((o, oldValue, newValue) -> {
			clientModel.send(clientModel.createJson(MsgType.whoHasMahJong.toString(), clientModel.player.playerID+""));
		});
		 */
		

		clientModel.sspMsg.addListener((o, oldValue, newValue) -> {
			clientView.gameView.chatView.chatTextArea.appendText(newValue + "\n");
		});
		
		clientModel.sspGame.addListener((o, oldValue, newValue) -> {
			
		});

		clientModel.sspName.addListener((o, oldValue, newValue) -> {
		});

		clientModel.cardList.addListener((ListChangeListener<? super Card>) (e -> {
			System.out.println(clientModel.cardList);
		}));

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
			clientModel.send(clientModel.createJson(MsgType.game.toString(), "dealCards")); // nur zum testen
			System.out.println(clientModel.cardList.toString());
		});

		clientView.lobbyView.loginButton.setOnAction(e -> {
			clientModel.send(
					clientModel.createJson(MsgType.name.toString(), clientView.lobbyView.userTextField.getText()));
			clientView.stage.setScene(clientView.gameScene);

			clientModel.playerName = clientView.lobbyView.userTextField.getText();
			clientModel.isTeamOne = clientView.lobbyView.teamOne.isSelected();
			clientModel.createPlayer();
			
			//Display the player Cards
			 	for(int i =0;i<=13;i++) {
				clientView.gameView.boardView.bottomBox.getChildren().add(clientView.gameView.boardView.getPlayerCardLabel().makeCardLabel(clientModel.cardList.get(i)));
				clientView.gameView.boardView.bottomBox.getChildren().get(i).setId("cardButton");
			}
			 
			 	//Add the selected Cards to the selectedCardList and set the Id for css styling
			 	for(int i=0;i<clientModel.cardList.size();i++) {
			 		int x=i;
			 		int y=i;
			 		clientView.gameView.boardView.bottomBox.getChildren().get(i).setOnMouseClicked(event->{
			 			clientModel.selectedCardList.add(clientView.gameView.boardView.bottomBox.getChildren().get(x));
			 			clientView.gameView.boardView.bottomBox.getChildren().get(y).setId("clickedCard");
			 		});
			 	}
			 	
			 	//Delete Selected Cards from the selected CardList
			 	clientView.gameView.controlAreaView.resetSelectedCardsButton.setOnMouseClicked(ev->{
			 		clientModel.selectedCardList.clear();
			 		for (int i=0;i<=13;i++) {
				 		clientView.gameView.boardView.bottomBox.getChildren().get(i).setId("cardButton");
				 	}
			 	});
			 
			 	//confirm Cards
			 	clientView.gameView.controlAreaView.confirmButton.setOnMouseClicked(abc->{
			 		for(int i=0;i<clientModel.selectedCardList.size();i++) {
			 				clientView.gameView.boardView.middleBoxForCards.getChildren().add((Node) clientModel.selectedCardList.get(i));
			 		}
			 		clientModel.selectedCardList.clear();
			 	
			 	});
 					
			System.out.println(clientModel.player);
		});

		
	}

}
