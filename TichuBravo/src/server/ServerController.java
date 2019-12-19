package server;

import common.MsgType;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;

/**
 * @author Dominik
 *
 */
public class ServerController {
	private ServerModel model;
	private ServerView view;

	/**
	 * 
	 * @param model
	 * @param view
	 */
	public ServerController(ServerModel model, ServerView view) {
		this.view = view;
		this.model = model;
		
		// if there is a new player ID, then send it to all clients
		model.game.currentPlayerID.addListener(((o, oldValue, newValue) ->{
			model.broadcast(ServerClient.createJson(MsgType.currentPlayerID.toString(),newValue.toString()));
		}));
		
		// if we have four players, then deal cards
		model.game.players.addListener((ListChangeListener<? super Player>)(e ->{
			if (model.game.players.size() == 4) model.game.sendNewCards();
		}));
		
		// if three clients are finish, then we know the round is finish
		model.game.finisherList.addListener((ListChangeListener<? super Player>)(e ->{
			if (model.game.finisherList.size() == 1) {
				model.broadcast(ServerClient.createJson(MsgType.announcementEvaluation.toString(), model.game.finisherList.get(0).getID()+""));
			}
			if (model.game.finisherList.size() == 3) {
				model.broadcast(ServerClient.createJson(MsgType.game.toString(), "stopRound"));
			}
		}));
		
		model.sspGame.addListener((o, oldValue, newValue) -> {
			// send new cards to all clients
			if (newValue.equals("dealCards")) {
				model.game.sendNewCards();
			}
			// clear the table and the player who made the last move can continue
			if (newValue.equals("resetTable")) {
				model.broadcast(ServerClient.createJson(MsgType.game.toString(), "resetTable"));
				model.game.currentPlayerID.set(0);
				model.game.currentPlayerID.set(model.game.lastMove.get());
			}
		});
		
		// if all clients pass, send the winner of the round
		model.game.passCounter.addListener(((o, oldValue, newValue) ->{
			if ((Integer)newValue == 4) {
				model.broadcast(ServerClient.createJson(MsgType.winnerOfTheRound.toString(), model.game.lastMove.get()+""));
			}
			
		}));
		
		ServerModel.clients.addListener((ListChangeListener<? super ServerClient>) (e -> view.textArea.appendText("new client")));
		
		
		//read port number and start server
		view.startBtn.setOnAction(e -> {
			view.startBtn.setDisable(true);
			model.serverStart(Integer.parseInt(view.tfPort.getText()));
		} );
		view.getStage().setOnCloseRequest( e -> {
			model.stopServer();
			Platform.exit();
		});
	}
}
