package server;

import javafx.application.Application;
import javafx.stage.Stage;

public class TichuServer extends Application{

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		ServerModel model = new ServerModel();
		ServerView view = new ServerView(stage, model);
		ServerController controller = new ServerController(model, view);
		view.start();
		
	}

}
