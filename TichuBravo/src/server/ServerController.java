package server;

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
		
		//read port number and start server
		view.startBtn.setOnAction(e -> {
			view.startBtn.setDisable(true);
			model.serverStart(Integer.parseInt(view.tfPort.getText()));
		} );
		view.getStage().setOnCloseRequest( e -> model.stopServer());
	}
}
