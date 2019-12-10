package client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import common.Card;
import common.Card.SpecialCard;
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

	ArrayList<Integer> intLIst = new ArrayList<Integer>();

	/**
	 * @param clientModel
	 * @param clientView
	 */
	public ClientController(ClientModel clientModel, ClientView clientView) {
		this.clientModel = clientModel;
		this.clientView = clientView;

		// if myTurn is equal to my ID then my buttons are able to click
		clientModel.player.myTurn.addListener((o, oldValue, newValue) -> {
			if (clientModel.player != null && (int) newValue == clientModel.player.getPlayerID()) {
				clientView.gameView.controlAreaView.confirmButton.setDisable(false);
				clientView.gameView.controlAreaView.passButton.setDisable(false);
			}
			if (clientModel.player.normalCardList.size() + clientModel.player.specialCardList.size() == 0) {
				clientModel.send(clientModel.createJson(MsgType.noCards.toString(), clientModel.player.getPlayerID()+""));
			}
		});

		clientModel.player.teamChange.addListener((o, oldValue, newValue) -> {
			if (clientModel.player != null && (int) newValue == clientModel.player.getPlayerID()) {
				if (clientModel.player.isTeamOne())
					clientModel.player.setIsTeamOne(false);
				else
					clientModel.player.setIsTeamOne(true);
				Platform.runLater(() -> {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText("You were changed to the other team!");
					alert.showAndWait();
				});
			}
		});

		clientModel.player.winnerOfTheRound.addListener((o, oldValue, newValue) -> {
			if (clientModel.player != null && (int) newValue == clientModel.player.getPlayerID()) {
				// add cards to earnedCards
				for (Card c : clientModel.player.playedCardsThisRound) {
					clientModel.player.earnedCards.add(c);
				}
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
			if (newValue.equals("stopRound")) {
				clientView.gameView.controlAreaView.confirmButton.setDisable(true);
				clientView.gameView.controlAreaView.passButton.setDisable(true);
				clientModel.player.table.clear();
				if (clientModel.player.normalCardList.size() + clientModel.player.specialCardList.size() > 0) {
					ArrayList<String> temp = new ArrayList<String>();
					for (Card c : clientModel.player.normalCardList) {
						temp.add(c.toString());
					}
					clientModel.player.normalCardList.clear();
					for (Card c : clientModel.player.specialCardList) {
						temp.add(c.toString());
					}
					clientModel.player.specialCardList.clear();
					
					//send json array with the cards in my hand to the other team
					if(clientModel.player.isTeamOne == true) {
						clientModel.send(clientModel.createJsonArray(MsgType.fromTeamOne.toString(), temp));
					} else {
						clientModel.send(clientModel.createJsonArray(MsgType.fromTeamTwo.toString(), temp));
					}
					
					//send all my earned cards to the fastest finisher
					ArrayList<String> temp2 = new ArrayList<String>();
					for (Card c : clientModel.player.earnedCards) {
						temp2.add(c.toString());
					}
					clientModel.player.earnedCards.clear();
					clientModel.send(clientModel.createJsonArray(MsgType.toFastestFinisher.toString(), temp2));
				}
				
				
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

				if (clientModel.player.table.size() > 1
						&& HandType.isNormalCards(new ArrayList<Card>(clientModel.player.table))) {
					ArrayList<ArrayList<Card>> list = HandType.compareHandTypes(
							new ArrayList<Card>(clientModel.player.table),
							new ArrayList<Card>(clientModel.player.normalCardList));

					for (int i = 0; i < clientView.gameView.boardView.bottomBox.getChildren().size(); i++) {
						clientView.gameView.boardView.bottomBox.getChildren().get(i).setDisable(true);
					}
					for (int i = 0; i < list.size(); i++) {
						for (int j = 0; j < list.get(i).size(); j++) {
							for (int k = 0; k < clientView.gameView.boardView.bottomBox.getChildren().size(); k++) {
								if (list.get(i).get(j).getRank() == ((CardLabel) clientView.gameView.boardView.bottomBox
										.getChildren().get(k)).getCard().getRank()) {
									clientView.gameView.boardView.bottomBox.getChildren().get(k).setDisable(false);
								}
							}
						}
					}
				} else if (clientModel.player.table.size() == 1
						&& HandType.isNormalCards(new ArrayList<Card>(clientModel.player.table))) {
					for (int k = 0; k < clientView.gameView.boardView.bottomBox.getChildren().size(); k++) {
						if (clientModel.player.table.get(0)
								.getRankOrdinal() < ((CardLabel) clientView.gameView.boardView.bottomBox.getChildren()
										.get(k)).getCard().getRankOrdinal()) {
							clientView.gameView.boardView.bottomBox.getChildren().get(k).setDisable(false);
						}
					}
				} else {
					for (int i = 0; i < clientView.gameView.boardView.bottomBox.getChildren().size(); i++) {
						clientView.gameView.boardView.bottomBox.getChildren().get(i).setDisable(false);
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

		});

		clientModel.player.allCardsReceived.addListener((o, oldValue, newValue) -> {
			if (newValue == true) {
				// Display the player Cards
				Platform.runLater(() -> {
					Collections.sort(clientModel.player.normalCardList);
					Collections.sort(clientModel.player.specialCardList);

					for (int i = 0; i < clientModel.player.normalCardList.size(); i++) {
						clientView.gameView.boardView.bottomBox.getChildren()
								.add(new CardLabel(clientModel.player.normalCardList.get(i)));
						clientView.gameView.boardView.bottomBox.getChildren().get(i).setId("cardButton");
					}

					for (int i = 0; i < clientModel.player.specialCardList.size(); i++) {
						clientView.gameView.boardView.bottomBox.getChildren()
								.add(new CardLabel(clientModel.player.specialCardList.get(i)));
						clientView.gameView.boardView.bottomBox.getChildren().get(i).setId("cardButton");
					}

					/**
					 * //here //generate 6 random inex numbers from 0-13 Random random = new
					 * Random(); for (int i =0;i<6;i++) { int randomInt=random.nextInt(14);
					 * while(this.intLIst.contains(randomInt)) { randomInt=random.nextInt(14); }
					 * this.intLIst.add(randomInt);
					 * clientView.gameView.boardView.bottomBox.getChildren().get(randomInt).setVisible(false);
					 * 
					 * }
					 * 
					 * clientView.grandTichuStage.setScene(clientView.grandTichuScene);
					 * clientView.grandTichuStage.show();
					 */

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
				ArrayList<Card> cards = new ArrayList<Card>();
				ArrayList<Card> table = new ArrayList<Card>(clientModel.player.table);
				for (CardLabel cl : clientModel.player.selectedCardList) {
					cards.add(cl.getCard());
				}
				Collections.sort(cards);
				if (table.isEmpty() && HandType.legalMoveOnEmptyTable(cards)
						|| table.size() > 0 && HandType.compareHandTypesBoolean(table, cards)) {
					for (int i = 0; i < clientModel.player.selectedCardList.size(); i++) {
						clientModel.player.normalCardList
								.remove(((CardLabel) clientModel.player.selectedCardList.get(i)).getCard());
						clientModel.player.specialCardList
								.remove(((CardLabel) clientModel.player.selectedCardList.get(i)).getCard());
						clientView.gameView.boardView.bottomBox.getChildren()
								.remove((CardLabel) clientModel.player.selectedCardList.get(i));
					}
					for (CardLabel cl : clientModel.player.selectedCardList) {
						temp.add(cl.getCard().toString());
					}
					clientModel.player.selectedCardList.clear();
					updateCardEvents();
					// Disable Small Tichu
					clientView.gameView.controlAreaView.smallTichu.setDisable(true);
					clientView.gameView.controlAreaView.confirmButton.setDisable(true);
					clientView.gameView.controlAreaView.passButton.setDisable(true);
					if (cards.size() == 1 && cards.get(0).isSpecial()
							&& cards.get(0).getSpecialCard() == SpecialCard.Dog) {
						clientModel.send(clientModel.createJsonArray(MsgType.dog.toString(), temp));
					} else {
						clientModel.send(clientModel.createJsonArray(MsgType.turn.toString(), temp));
					}
				} else {
					Platform.runLater(() -> {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information");
						alert.setHeaderText(null);
						alert.setContentText("Invalid move!");
						alert.showAndWait();
					});
				}

			});

			// SmallTichu
			clientView.gameView.controlAreaView.smallTichu.setOnMouseClicked(event -> {
				clientModel.player.saidSmallTichu = true;
			});

			/**
			 * //GrandTichu clientView.grandTichuView.yesButton.setOnMouseClicked(event->{
			 * clientModel.player.saidGrandTichu=true; clientView.grandTichuStage.close();
			 * for(int i=0;i<intLIst.size();i++) {
			 * clientView.gameView.boardView.bottomBox.getChildren().get(this.intLIst.get(i)).setVisible(true);
			 * }
			 * 
			 * });
			 * 
			 * clientView.grandTichuView.noButton.setOnAction(event->{
			 * clientView.grandTichuStage.close(); for(int i=0;i<intLIst.size();i++) {
			 * clientView.gameView.boardView.bottomBox.getChildren().get(this.intLIst.get(i)).setVisible(true);
			 * System.out.println(this.intLIst.get(i)); } System.out.println(this.intLIst);
			 * });
			 */

			// pass
			clientView.gameView.controlAreaView.passButton.setOnMouseClicked(abc -> {
				clientModel.player.selectedCardList.clear();
				clientView.gameView.controlAreaView.confirmButton.setDisable(true);
				clientView.gameView.controlAreaView.passButton.setDisable(true);
				clientModel.send(clientModel.createJson(MsgType.pass.toString(), new String("x")));
			});

			System.out.println(clientModel.player); // TODO löschen

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
