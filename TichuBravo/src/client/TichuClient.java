package client;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Dominik
 *
 */
public class TichuClient extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		ClientModel model = new ClientModel();
		ClientView view = new ClientView(stage, model);
		ClientController controller = new ClientController(model, view);
		view.start();
		model.connect();
	}
}
