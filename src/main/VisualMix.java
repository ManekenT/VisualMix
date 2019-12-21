package main;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.configuration.Controller;
import main.midiInterface.MidiManager;
import main.persistance.ControlsConverter;
import main.visualizer.CircleVisualizer;
import main.visualizer.PerlinNoiseVisualizer;
import main.visualizer.TestVisualizer;
import main.visualizer.Visualizer;
import main.visualizer.VisualizerApplet;
import processing.core.PApplet;

public class VisualMix extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	VisualizerApplet _vApplet;

	@Override
	public void start(Stage stage) throws Exception {
		// Load GUI
		FXMLLoader loader = new FXMLLoader(getClass().getResource("configuration/scene.fxml"));
		Parent root = loader.load();
		Controller c = loader.getController();

		new ControlsConverter().loadControllerValues();
		c.loadMappingsFromModel();

		MidiManager manager = new MidiManager();
		_vApplet = new VisualizerApplet(manager);

		List<Visualizer> visualizer = new ArrayList<>();
		TestVisualizer testVisualizer = new TestVisualizer(_vApplet);
		visualizer.add(testVisualizer);
		visualizer.add(new PerlinNoiseVisualizer(_vApplet));
		visualizer.add(new CircleVisualizer(_vApplet));

		_vApplet.setVisualizer(testVisualizer);

		c.injectModels(manager, _vApplet, visualizer);

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
