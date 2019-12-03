package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.configuration.Controller;
import main.midiInterface.MidiManager;
import main.persistance.ControlsConverter;
import main.visualizer.TestVisualizer;
import main.visualizer.VisualizerApplet;
import processing.core.PApplet;

public class VisualMix extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	VisualizerApplet _vApplet;

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("configuration/scene.fxml"));
		Parent root = loader.load();
		Controller c = loader.getController();

		new ControlsConverter().loadControllerValues();
		c.loadMappingsFromModel();

		TestVisualizer testVisualizer = new TestVisualizer();
		MidiManager manager = new MidiManager();
		_vApplet = new VisualizerApplet(testVisualizer, manager);
		c.injectModels(manager, _vApplet, testVisualizer);
		PApplet.runSketch(new String[] { "visualMix.visualizer.VisualizerApplet" }, _vApplet);

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("configuration/styles.css").toExternalForm());
		stage.setTitle("VisualMix");
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void stop() throws Exception {
		new ControlsConverter().saveControllerValues();
		_vApplet.exit();
		super.stop();
	}

}
