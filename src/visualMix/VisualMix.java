package visualMix;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VisualMix extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("configuration/scene.fxml"));

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("configuration/styles.css").toExternalForm());
		stage.setTitle("VisualMix");
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void stop() throws Exception {
		System.exit(0);
		super.stop();
	}

}
