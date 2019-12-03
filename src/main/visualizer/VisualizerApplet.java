package main.visualizer;

import main.midiInterface.MidiManager;
import processing.core.PApplet;

public class VisualizerApplet extends PApplet {

	private Visualizer _visualizer;
	private MidiManager _manager;

	public VisualizerApplet(Visualizer visualizer, MidiManager manager) {
		_visualizer = visualizer;
		_manager = manager;
		manager.addClockListener(_visualizer);
	}

	public void settings() {
		_visualizer.settings(this);

	}

	public void setup() {
		_visualizer.setup(this);

	}

	public void draw() {
		_visualizer.draw(this);
	}

	public void setVisualizer(Visualizer visualizer) {
		_manager.removeClockListener(_visualizer);
		_visualizer = visualizer;
		_manager.addClockListener(_visualizer);
	}
}
