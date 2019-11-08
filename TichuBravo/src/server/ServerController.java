package server;

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
		
		model.clients.addListener((ListChangeListener<? super ServerClient>) (e -> view.textArea.appendText("new client")));
		
		
		
		//read port number and start server
		view.startBtn.setOnAction(e -> {
			view.startBtn.setDisable(true);
			model.serverStart(Integer.parseInt(view.tfPort.getText()));
		} );
		view.getStage().setOnCloseRequest( e -> model.stopServer());
	}
}
