package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import common.Card;
import common.MsgType;
import javafx.application.Platform;
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

		clientModel.player.myTurn.addListener((o, oldValue, newValue) -> {
			if (clientModel.player != null && (int) newValue == clientModel.player.getPlayerID()) {
				clientView.gameView.controlAreaView.confirmButton.setDisable(false);
				clientView.gameView.controlAreaView.passButton.setDisable(false);
			} else {
				// Buttons deaktivieren
			}

			// Spielzug start, Buttens aktivieren
			// Speilzug machen, danach Buttons deaktivieren

		});

		clientModel.sspMsg.addListener((o, oldValue, newValue) -> {
			clientView.gameView.chatView.chatTextArea.appendText(newValue + "\n");
		});

		clientModel.sspGame.addListener((o, oldValue, newValue) -> {

		});

		clientModel.sspName.addListener((o, oldValue, newValue) -> {
		});

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
			// Display the player Cards
			Platform.runLater(() -> {
				Collections.sort(clientModel.player.normalCardList);
				Collections.sort(clientModel.player.specialCardList);
				for (int i = 0; i < clientModel.player.normalCardList.size(); i++) { // nur zum testen, es müssen beide
																						// listen (special und normal)
																						// setonAction gesetzt werden
					clientView.gameView.boardView.bottomBox.getChildren().add(clientView.gameView.boardView
							.getPlayerCardLabel().makeCardLabel(clientModel.player.normalCardList.get(i)));
					clientView.gameView.boardView.bottomBox.getChildren().get(i).setId("cardButton");
				}

				for (int i = 0; i < clientModel.player.specialCardList.size(); i++) {
					clientView.gameView.boardView.bottomBox.getChildren().add(clientView.gameView.boardView
							.getPlayerCardLabel().makeCardLabel(clientModel.player.specialCardList.get(i)));
					clientView.gameView.boardView.bottomBox.getChildren().get(i).setId("cardButton");
				}

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
				for (int i = 0; i < clientModel.player.selectedCardList.size(); i++) {
					clientView.gameView.boardView.middleBoxForCards.getChildren()
							.add((CardLabel) clientModel.player.selectedCardList.get(i));
				}
//				ArrayList<String> temp = new ArrayList<String>();
//				for (CardLabel cl : clientModel.player.selectedCardList) {
//					temp.add(cl.getCard().toString());
//				}
				clientModel.player.selectedCardList.clear();
				updateCardEvents();
//				clientView.gameView.controlAreaView.confirmButton.setDisable(true);
//				clientView.gameView.controlAreaView.passButton.setDisable(true);
//				clientModel.send(clientModel.createJsonArray(MsgType.turn.toString(), temp));		
			});
			
			// pass
			clientView.gameView.controlAreaView.passButton.setOnMouseClicked(abc -> {
				clientModel.player.selectedCardList.clear();
				clientView.gameView.controlAreaView.confirmButton.setDisable(true);
				clientView.gameView.controlAreaView.passButton.setDisable(true);
				clientModel.send(clientModel.createJson(MsgType.pass.toString(), "x"));
			});
			

			System.out.println(clientModel.player);						//löschen
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
				// clientModel.player.updateCardLabels.set(true);
			});
		}
	}

}
