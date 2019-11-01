package client;


/**
 * @author Tim
 *
 */
public class ClientController {
	private ClientModel clientModel;
	private ClientView clientView;
	
	private int isClicked=0;
	private int isPlayerOneCounter=0;
	private int isPlayerTwoCounter=0;

	public ClientController(ClientModel clientModel, ClientView clientView) {
		this.clientModel = clientModel;
		this.clientView = clientView;

		Player player = new Player();

			clientView.lobbyView.loginButton.setOnAction(e -> {
				this.isClicked++;
				clientView.lobbyView.playerDefaultNumber++;
				
				
				if(this.isClicked<5) {
					if(this.isPlayerOneCounter>0) {
						this.clientView.lobbyView.teamOne.setDisable(true);
					}else if(this.isPlayerTwoCounter>0) {
						this.clientView.lobbyView.teamTwo.setDisable(true);
					}
					String playerName = clientView.lobbyView.userTextField.getText();
					boolean isTeamOne;
					if (clientView.lobbyView.teamOne.isSelected()) {
						isTeamOne = true;
						this.isPlayerOneCounter++;
					} else {
						isTeamOne = false;
						this.isPlayerTwoCounter++;
					}

					int playerID = 0;
					player.increasePlayerID();
					player.setPlayerID(player.getPlayerID());

					player.setPlayerName(playerName);
					player.setIsTeamOne(isTeamOne);
					clientView.stage.setScene(clientView.gameScene);

					// TODO Delete This
					System.out.println(player);
				}else {
					clientView.lobbyView.userTextField.setDisable(true);
					clientView.lobbyView.teamOne.setDisable(true);
					clientView.lobbyView.teamOne.setDisable(true);
				}
			});
		
		clientView.gameView.button.setOnAction(e -> {
			clientView.stage.setScene(clientView.lobbyScene);
		});

	}

}
