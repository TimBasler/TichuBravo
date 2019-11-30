package client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import common.Card;
import common.MsgType;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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
	
	ArrayList <Integer> intLIst  = new ArrayList<Integer>();

	/**
	 * @param clientModel
	 * @param clientView
	 */
	public ClientController(ClientModel clientModel, ClientView clientView) {
		this.clientModel = clientModel;
		this.clientView = clientView;
		
		
		//Update View after the Winner evaluation
				clientModel.sspWinnerLabelTeamTwo.addListener((obs,oV,nV)->{
					Platform.runLater(() -> {
						clientView.gameView.chatView.winnerLabel.setText(nV);
						});
				});
		
		//Update View after the Winner evaluation
		clientModel.sspWinnerLabelTeamOne.addListener((obs,oV,nV)->{
			Platform.runLater(() -> {
				clientView.gameView.chatView.winnerLabel.setText(nV);
				});
		});
		
		//Update pointsView Team 1
		clientModel.sipPointsTeamOne.addListener((obs,oV,nV)->{
			Platform.runLater(() -> {
				clientView.gameView.chatView.teamOneScoreLabel.setText(nV+" Points");
				if((int)nV>=1000) {
					clientModel.send(clientModel.createJson(MsgType.winnerLabelTeamOne.toString(), "Team 1 is the Winner"));
				}
				});
		});

		//Update pointsView Team 2
		clientModel.sipPointsTeamTwo.addListener((obs,oV,nV)->{
			Platform.runLater(() -> {
				clientView.gameView.chatView.teamTwoScoreLabel.setText(nV+" Points");
				if((int)nV>=1000) {
					clientModel.send(clientModel.createJson(MsgType.winnerLabelTeamTwo.toString(), "Team 2 is the Winner"));
				}
				});
		});

		// if myTurn is equal to my ID then my buttons are able to click
		clientModel.player.myTurn.addListener((o, oldValue, newValue) -> {
			if (clientModel.player != null && (int) newValue == clientModel.player.getPlayerID()) {
				clientView.gameView.controlAreaView.confirmButton.setDisable(false);
				clientView.gameView.controlAreaView.passButton.setDisable(false);
			} 
		});
		
		clientModel.player.winnerOfTheRound.addListener((o, oldValue, newValue) -> {
			if (clientModel.player != null && (int) newValue == clientModel.player.getPlayerID()) {
				//add cards to earnedCards
				for(Card c : clientModel.player.playedCardsThisRound) {
					clientModel.player.earnedCards.add(c);
				}
				
				//count points 
					if(clientModel.player.isTeamOne) {
						clientModel.countPointsFromTeamOne(clientModel.player.earnedCards);
						clientModel.send(clientModel.createJson(MsgType.pointsTeamOne.toString(), clientModel.sipPointsTeamOne.get()+""));
					}
					if(!clientModel.player.isTeamOne) {
						clientModel.countPointsFromTeamTwo(clientModel.player.earnedCards);
						clientModel.send(clientModel.createJson(MsgType.pointsTeamTwo.toString(), clientModel.sipPointsTeamTwo.get()+""));
					}
					
				
				System.out.println(clientModel.player.earnedCards +"kommt vom here");
				clientModel.send(clientModel.createJson(MsgType.game.toString(), "resetTable"));
			} 
		});

		clientModel.sspMsg.addListener((o, oldValue, newValue) -> {
			Platform.runLater(() -> {
			clientView.gameView.chatView.chatTextArea.appendText(newValue + "\n");
			});
		});

		clientModel.sspGame.addListener((o, oldValue, newValue) -> {
			if (newValue.equals("resetTable")) {
				clientModel.player.table.clear();
				clientModel.player.playedCardsThisRound.clear();
			}
		});

		clientModel.sspName.addListener((o, oldValue, newValue) -> {
		});
		
		clientModel.player.table.addListener((ListChangeListener<? super Card>) (e -> {
			Platform.runLater(() -> {
				clientView.gameView.boardView.middleBoxForCards.getChildren().clear();
				
				for (int i = 0; i < clientModel.player.table.size(); i++) {
					clientView.gameView.boardView.middleBoxForCards.getChildren()
					.add(new CardLabel(clientModel.player.table.get(i)));
					
					if (!clientModel.player.playedCardsThisRound.contains(clientModel.player.table.get(i))) {
						clientModel.player.playedCardsThisRound.add(clientModel.player.table.get(i));
					}
				}
			});
		}));

		clientModel.player.normalCardList.addListener((ListChangeListener<? super Card>) (e -> {
			if (clientModel.player.normalCardList.size() + clientModel.player.specialCardList.size() == 14) {
				clientModel.player.allCardsReceived.set(true);
			} else {
				clientModel.player.allCardsReceived.set(false);
			}
		}));

		clientModel.player.specialCardList.addListener((ListChangeListener<? super Card>) (e -> {
			if (clientModel.player.normalCardList.size() + clientModel.player.specialCardList.size() == 14) {
				clientModel.player.allCardsReceived.set(true);
			} else {
				clientModel.player.allCardsReceived.set(false);
			}
			if (HandType.hasMahJong(new ArrayList<Card>(clientModel.player.specialCardList))) {
				clientModel.send(
						clientModel.createJson(MsgType.whoHasMahJong.toString(), clientModel.player.playerID + ""));
			}
		}));

		clientView.gameView.button.setOnAction(e -> {
			clientView.stage.setScene(clientView.lobbyScene);
		});

		clientView.gameView.chatView.sendButton.setOnAction(e -> {
			
			clientModel.send(clientModel.createJson(MsgType.msg.toString(),
					clientView.gameView.chatView.chatTextField.getText()));
			clientView.gameView.chatView.chatTextField.clear();

		});

		clientModel.player.allCardsReceived.addListener((o, oldValue, newValue) -> {
			if(newValue == true) {
				// Display the player Cards
				Platform.runLater(() -> {
					Collections.sort(clientModel.player.normalCardList);
					Collections.sort(clientModel.player.specialCardList);
					for (int i = 0; i < clientModel.player.normalCardList.size(); i++) { 
																							
						clientView.gameView.boardView.bottomBox.getChildren().add(
								new CardLabel(clientModel.player.normalCardList.get(i)));
						clientView.gameView.boardView.bottomBox.getChildren().get(i).setId("cardButton");
					}

					for (int i = 0; i < clientModel.player.specialCardList.size(); i++) {
						clientView.gameView.boardView.bottomBox.getChildren().add(
								new CardLabel(clientModel.player.specialCardList.get(i)));
						clientView.gameView.boardView.bottomBox.getChildren().get(i).setId("cardButton");
					}
					//generate 6 random inex numbers from 0-13 
					Random random = new Random();
					for (int i =0;i<6;i++) {
						int randomInt=random.nextInt(14);
						while(this.intLIst.contains(randomInt)) {
							randomInt=random.nextInt(14);
						}
						this.intLIst.add(randomInt);
						clientView.gameView.boardView.bottomBox.getChildren().get(randomInt).setVisible(false);
						
					}
					
					clientView.grandTichuStage.setScene(clientView.grandTichuScene);
					clientView.grandTichuStage.show();

					// Add the selected Cards to the selectedCardList and set the Id for css styling
					updateCardEvents();

					// Delete Selected Cards from the selected CardList
					clientView.gameView.controlAreaView.resetSelectedCardsButton.setOnMouseClicked(ev -> {
						clientModel.player.selectedCardList.clear();
						for (int i = 0; i < clientView.gameView.boardView.bottomBox.getChildren().size(); i++) {
							clientView.gameView.boardView.bottomBox.getChildren().get(i).setId("cardButton");
						}
					});
				});
			}
		});

		clientView.lobbyView.loginButton.setOnAction(e -> {
			clientModel.send(
					clientModel.createJson(MsgType.name.toString(), clientView.lobbyView.userTextField.getText()));
			clientView.stage.setScene(clientView.gameScene);
			clientModel.playerName = clientView.lobbyView.userTextField.getText();
			clientModel.isTeamOne = clientView.lobbyView.teamOne.isSelected();
			clientModel.createPlayer();

			clientModel.send(clientModel.createJson(MsgType.player.toString(),
					clientModel.player.playerID + "," + clientModel.player.isTeamOne));

			
			// confirm Cards
			clientView.gameView.controlAreaView.confirmButton.setOnMouseClicked(abc -> {
				ArrayList<String> temp = new ArrayList<String>();
				//Disable Small Tichu
				clientView.gameView.controlAreaView.smallTichu.setDisable(true);
				for (int i = 0; i < clientModel.player.selectedCardList.size(); i++) {
						clientModel.player.normalCardList.remove(((CardLabel) clientModel.player.selectedCardList.get(i)).getCard());
						clientModel.player.specialCardList.remove(((CardLabel) clientModel.player.selectedCardList.get(i)).getCard());
					clientView.gameView.boardView.bottomBox.getChildren().remove((CardLabel) clientModel.player.selectedCardList.get(i));
				}	
				for (CardLabel cl : clientModel.player.selectedCardList) {
					temp.add(cl.getCard().toString());
				}
				clientModel.player.selectedCardList.clear();
				updateCardEvents();
				clientView.gameView.controlAreaView.confirmButton.setDisable(true);
				clientView.gameView.controlAreaView.passButton.setDisable(true);
				clientModel.send(clientModel.createJsonArray(MsgType.turn.toString(), temp));	
				
				
			});
			
			//SmallTichu
			clientView.gameView.controlAreaView.smallTichu.setOnMouseClicked(event->{
				clientModel.player.saidSmallTichu=true;
			});
			
			//GrandTichu
		clientView.grandTichuView.yesButton.setOnMouseClicked(event->{
			clientModel.player.saidGrandTichu=true;
			clientView.grandTichuStage.close();
			for(int i=0;i<intLIst.size();i++) {
				clientView.gameView.boardView.bottomBox.getChildren().get(this.intLIst.get(i)).setVisible(true);
			}
			
		});
		
		clientView.grandTichuView.noButton.setOnAction(event->{
			clientView.grandTichuStage.close();
			for(int i=0;i<intLIst.size();i++) {
				clientView.gameView.boardView.bottomBox.getChildren().get(this.intLIst.get(i)).setVisible(true);
				System.out.println(this.intLIst.get(i));
			}
			System.out.println(this.intLIst);
		});
			
			// pass
			clientView.gameView.controlAreaView.passButton.setOnMouseClicked(abc -> {
				clientModel.player.selectedCardList.clear();
				clientView.gameView.controlAreaView.confirmButton.setDisable(true);
				clientView.gameView.controlAreaView.passButton.setDisable(true);
				clientModel.send(clientModel.createJson(MsgType.pass.toString(), new String("x")));
			});
			
		});

		//quitGameButton
		clientView.gameView.controlAreaView.quitGameButton.setOnAction(e->{
			clientView.stage.close();
		});
		
	}

	public void updateCardEvents() {
		for (int i = 0; i < clientView.gameView.boardView.bottomBox.getChildren().size(); i++) {
			int x = i;
			int y = i;

			clientView.gameView.boardView.bottomBox.getChildren().get(i).setOnMouseClicked(event -> {

				clientModel.player.selectedCardList
						.add(((CardLabel) clientView.gameView.boardView.bottomBox.getChildren().get(x)));
				clientView.gameView.boardView.bottomBox.getChildren().get(y).setId("clickedCard");
			});
		}
	}

}
