package server;

import common.MsgType;
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
		
		model.game.currentPlayerID.addListener(((o, oldValue, newValue) ->{
			model.broadcast(ServerClient.createJson(MsgType.currentPlayerID.toString(),newValue.toString()));
		}));
		
		model.game.players.addListener((ListChangeListener<? super Player>)(e ->{
			if (model.game.players.size() == 4) model.game.sendNewCards();
		}));
		
		model.sspGame.addListener((o, oldValue, newValue) -> {
			if (newValue.equals("dealCards")) {
				model.game.sendNewCards();
			}
			if (newValue.equals("resetTable")) {
				model.broadcast(ServerClient.createJson(MsgType.game.toString(), "resetTable"));
				model.game.currentPlayerID.set(0);
				model.game.currentPlayerID.set(model.game.lastMove.get());
			}
		});
		
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
		view.getStage().setOnCloseRequest( e -> model.stopServer());
	}
}
