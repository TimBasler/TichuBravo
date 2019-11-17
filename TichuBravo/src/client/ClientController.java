package client;

import java.io.IOException;
import java.util.ArrayList;

import common.Card;
import common.Card.Rank;
import common.Card.Suit;
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
		
		clientModel.player.hasMahJong.addListener((o, oldValue, newValue) -> {
			clientModel.send(clientModel.createJson(MsgType.whoHasMahJong.toString(), clientModel.player.playerID+""));
		});

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
			 	for(int i=0;i<=13;i++) {
			 		
			 		int x=i;
			 		int y=i;
			 		clientView.gameView.boardView.bottomBox.getChildren().get(i).setOnMouseClicked(event->{
			 			if(clientView.gameView.boardView.bottomBox.getChildren().size()==14) {
			 				clientModel.selectedCardList.add(clientView.gameView.boardView.bottomBox.getChildren().get(x));
				 			clientView.gameView.boardView.bottomBox.getChildren().get(y).setId("clickedCard");
			 			}
			 			if(clientView.gameView.boardView.bottomBox.getChildren().size()==13) {
			 				clientModel.selectedCardList.add(clientView.gameView.boardView.bottomBox.getChildren().get(x-1));
				 			clientView.gameView.boardView.bottomBox.getChildren().get(y-1).setId("clickedCard");
			 			}
			 			if(clientView.gameView.boardView.bottomBox.getChildren().size()==12) {
			 				clientModel.selectedCardList.add(clientView.gameView.boardView.bottomBox.getChildren().get(x-2));
				 			clientView.gameView.boardView.bottomBox.getChildren().get(y-2).setId("clickedCard");
			 			}
			 			if(clientView.gameView.boardView.bottomBox.getChildren().size()==11) {
			 				clientModel.selectedCardList.add(clientView.gameView.boardView.bottomBox.getChildren().get(x-3));
				 			clientView.gameView.boardView.bottomBox.getChildren().get(y-3).setId("clickedCard");
			 			}
			 			if(clientView.gameView.boardView.bottomBox.getChildren().size()==10) {
			 				clientModel.selectedCardList.add(clientView.gameView.boardView.bottomBox.getChildren().get(x-4));
				 			clientView.gameView.boardView.bottomBox.getChildren().get(y-4).setId("clickedCard");
			 			}
			 			if(clientView.gameView.boardView.bottomBox.getChildren().size()==9) {
			 				clientModel.selectedCardList.add(clientView.gameView.boardView.bottomBox.getChildren().get(x-5));
				 			clientView.gameView.boardView.bottomBox.getChildren().get(y-5).setId("clickedCard");
			 			}
			 			if(clientView.gameView.boardView.bottomBox.getChildren().size()==8) {
			 				clientModel.selectedCardList.add(clientView.gameView.boardView.bottomBox.getChildren().get(x-6));
				 			clientView.gameView.boardView.bottomBox.getChildren().get(y-6).setId("clickedCard");
			 			}
			 			if(clientView.gameView.boardView.bottomBox.getChildren().size()==7) {
			 				clientModel.selectedCardList.add(clientView.gameView.boardView.bottomBox.getChildren().get(x-7));
				 			clientView.gameView.boardView.bottomBox.getChildren().get(y-7).setId("clickedCard");
			 			}
			 			if(clientView.gameView.boardView.bottomBox.getChildren().size()==6) {
			 				clientModel.selectedCardList.add(clientView.gameView.boardView.bottomBox.getChildren().get(x-8));
				 			clientView.gameView.boardView.bottomBox.getChildren().get(y-8).setId("clickedCard");
			 			}
			 			if(clientView.gameView.boardView.bottomBox.getChildren().size()==5) {
			 				clientModel.selectedCardList.add(clientView.gameView.boardView.bottomBox.getChildren().get(x-9));
				 			clientView.gameView.boardView.bottomBox.getChildren().get(y-9).setId("clickedCard");
			 			}
			 			if(clientView.gameView.boardView.bottomBox.getChildren().size()==4) {
			 				clientModel.selectedCardList.add(clientView.gameView.boardView.bottomBox.getChildren().get(x-10));
				 			clientView.gameView.boardView.bottomBox.getChildren().get(y-10).setId("clickedCard");
			 			}
			 			if(clientView.gameView.boardView.bottomBox.getChildren().size()==3) {
			 				clientModel.selectedCardList.add(clientView.gameView.boardView.bottomBox.getChildren().get(x-11));
				 			clientView.gameView.boardView.bottomBox.getChildren().get(y-11).setId("clickedCard");
			 			}
			 			if(clientView.gameView.boardView.bottomBox.getChildren().size()==2) {
			 				clientModel.selectedCardList.add(clientView.gameView.boardView.bottomBox.getChildren().get(x-12));
				 			clientView.gameView.boardView.bottomBox.getChildren().get(y-12).setId("clickedCard");
			 			}
			 			if(clientView.gameView.boardView.bottomBox.getChildren().size()==1) {
			 				clientModel.selectedCardList.add(clientView.gameView.boardView.bottomBox.getChildren().get(x-13));
				 			clientView.gameView.boardView.bottomBox.getChildren().get(y-13).setId("clickedCard");
			 			}
			 			
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



		// Card	
		
		// 1Card
		clientView.gameView.boardView.bottomBox.getChildren().get(0).setOnMouseClicked(e -> {
			clientModel.selectedCardList.add((CardLabel) clientView.gameView.boardView.bottomBox.getChildren().get(0));
			clientView.gameView.boardView.bottomBox.getChildren().get(0).setId("clickedCard");
		});

		// 2Card
		clientView.gameView.boardView.bottomBox.getChildren().get(1).setOnMouseClicked(e -> {
			clientModel.selectedCardList.add((CardLabel) clientView.gameView.boardView.bottomBox.getChildren().get(1));
			clientView.gameView.boardView.bottomBox.getChildren().get(1).setId("clickedCard");
		});

		// 3Card
		clientView.gameView.boardView.bottomBox.getChildren().get(2).setOnMouseClicked(e -> {
			clientModel.selectedCardList.add((CardLabel) clientView.gameView.boardView.bottomBox.getChildren().get(2));
			clientView.gameView.boardView.bottomBox.getChildren().get(2).setId("clickedCard");
		});

		// 4Card
		clientView.gameView.boardView.bottomBox.getChildren().get(3).setOnMouseClicked(e -> {
			clientModel.selectedCardList.add((CardLabel) clientView.gameView.boardView.bottomBox.getChildren().get(3));
			clientView.gameView.boardView.bottomBox.getChildren().get(3).setId("clickedCard");
		});

		// 5Card
		clientView.gameView.boardView.bottomBox.getChildren().get(4).setOnMouseClicked(e -> {
			clientModel.selectedCardList.add((CardLabel) clientView.gameView.boardView.bottomBox.getChildren().get(4));
			clientView.gameView.boardView.bottomBox.getChildren().get(4).setId("clickedCard");
		});

		// 6Card
		clientView.gameView.boardView.bottomBox.getChildren().get(5).setOnMouseClicked(e -> {
			clientModel.selectedCardList.add((CardLabel) clientView.gameView.boardView.bottomBox.getChildren().get(5));
			clientView.gameView.boardView.bottomBox.getChildren().get(5).setId("clickedCard");
		});

		// 7Card
		clientView.gameView.boardView.bottomBox.getChildren().get(6).setOnMouseClicked(e -> {
			clientModel.selectedCardList.add((CardLabel) clientView.gameView.boardView.bottomBox.getChildren().get(6));
			clientView.gameView.boardView.bottomBox.getChildren().get(6).setId("clickedCard");
		});

		// 8Card
		clientView.gameView.boardView.bottomBox.getChildren().get(7).setOnMouseClicked(e -> {
			clientModel.selectedCardList.add((CardLabel) clientView.gameView.boardView.bottomBox.getChildren().get(7));
			clientView.gameView.boardView.bottomBox.getChildren().get(7).setId("clickedCard");
		});

		// 9Card
		clientView.gameView.boardView.bottomBox.getChildren().get(8).setOnMouseClicked(e -> {
			clientModel.selectedCardList.add((CardLabel) clientView.gameView.boardView.bottomBox.getChildren().get(8));
			clientView.gameView.boardView.bottomBox.getChildren().get(8).setId("clickedCard");
		});

		// 10Card
		clientView.gameView.boardView.bottomBox.getChildren().get(9).setOnMouseClicked(e -> {
			clientModel.selectedCardList.add((CardLabel) clientView.gameView.boardView.bottomBox.getChildren().get(9));
			clientView.gameView.boardView.bottomBox.getChildren().get(9).setId("clickedCard");
		});

		// 11Card
		clientView.gameView.boardView.bottomBox.getChildren().get(10).setOnMouseClicked(e -> {
			clientModel.selectedCardList.add((CardLabel) clientView.gameView.boardView.bottomBox.getChildren().get(10));
			clientView.gameView.boardView.bottomBox.getChildren().get(10).setId("clickedCard");
		});

		// 12Card
		clientView.gameView.boardView.bottomBox.getChildren().get(11).setOnMouseClicked(e -> {
			clientModel.selectedCardList.add((CardLabel) clientView.gameView.boardView.bottomBox.getChildren().get(11));
			clientView.gameView.boardView.bottomBox.getChildren().get(11).setId("clickedCard");
		});

		// 13Card
		clientView.gameView.boardView.bottomBox.getChildren().get(12).setOnMouseClicked(e -> {
			clientModel.selectedCardList.add((CardLabel) clientView.gameView.boardView.bottomBox.getChildren().get(12));
			clientView.gameView.boardView.bottomBox.getChildren().get(12).setId("clickedCard");
		});

		// 14Card
		clientView.gameView.boardView.bottomBox.getChildren().get(13).setOnMouseClicked(e -> {
			clientModel.selectedCardList.add((CardLabel) clientView.gameView.boardView.bottomBox.getChildren().get(13));
			clientView.gameView.boardView.bottomBox.getChildren().get(13).setId("clickedCard");
		});

		// Delete Selected Cards
		clientView.gameView.controlAreaView.resetSelectedCardsButton.setOnMouseClicked(e -> {
			clientModel.selectedCardList.clear();
			clientView.gameView.boardView.bottomBox.getChildren().get(0).setId("cardButton");
			clientView.gameView.boardView.bottomBox.getChildren().get(1).setId("cardButton");
			clientView.gameView.boardView.bottomBox.getChildren().get(2).setId("cardButton");
			clientView.gameView.boardView.bottomBox.getChildren().get(3).setId("cardButton");
			clientView.gameView.boardView.bottomBox.getChildren().get(4).setId("cardButton");
			clientView.gameView.boardView.bottomBox.getChildren().get(5).setId("cardButton");
			clientView.gameView.boardView.bottomBox.getChildren().get(6).setId("cardButton");
			clientView.gameView.boardView.bottomBox.getChildren().get(7).setId("cardButton");
			clientView.gameView.boardView.bottomBox.getChildren().get(8).setId("cardButton");
			clientView.gameView.boardView.bottomBox.getChildren().get(9).setId("cardButton");
			clientView.gameView.boardView.bottomBox.getChildren().get(10).setId("cardButton");
			clientView.gameView.boardView.bottomBox.getChildren().get(11).setId("cardButton");
			clientView.gameView.boardView.bottomBox.getChildren().get(12).setId("cardButton");
			clientView.gameView.boardView.bottomBox.getChildren().get(13).setId("cardButton");
		});

		// confirm Cards
		clientView.gameView.controlAreaView.confirmButton.setOnMouseClicked(e -> {
			clientView.gameView.boardView.middleBoxForCards.getChildren().add((Node) clientModel.selectedCardList.get(0));
			clientView.gameView.boardView.middleBoxForCards.getChildren().add((Node) clientModel.selectedCardList.get(1));
			clientView.gameView.boardView.middleBoxForCards.getChildren().add((Node) clientModel.selectedCardList.get(2));
			clientView.gameView.boardView.middleBoxForCards.getChildren().add((Node) clientModel.selectedCardList.get(3));
			clientView.gameView.boardView.middleBoxForCards.getChildren().add((Node) clientModel.selectedCardList.get(4));
			clientView.gameView.boardView.middleBoxForCards.getChildren().add((Node) clientModel.selectedCardList.get(5));
			clientView.gameView.boardView.middleBoxForCards.getChildren().add((Node) clientModel.selectedCardList.get(6));
			clientView.gameView.boardView.middleBoxForCards.getChildren().add((Node) clientModel.selectedCardList.get(7));
			clientView.gameView.boardView.middleBoxForCards.getChildren().add((Node) clientModel.selectedCardList.get(8));
			clientView.gameView.boardView.middleBoxForCards.getChildren().add((Node) clientModel.selectedCardList.get(9));
			clientView.gameView.boardView.middleBoxForCards.getChildren().add((Node) clientModel.selectedCardList.get(10));
			clientView.gameView.boardView.middleBoxForCards.getChildren().add((Node) clientModel.selectedCardList.get(11));
			clientView.gameView.boardView.middleBoxForCards.getChildren().add((Node) clientModel.selectedCardList.get(12));
			clientView.gameView.boardView.middleBoxForCards.getChildren().add((Node) clientModel.selectedCardList.get(13));
			clientModel.selectedCardList.clear();
		});

		 

		
	}

}
