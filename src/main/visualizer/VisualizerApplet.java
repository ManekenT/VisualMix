package main.visualizer;

import main.midiInterface.MidiManager;
import processing.core.PApplet;

public class VisualizerApplet extends PApplet {

	private Visualizer _visualizer;
	private MidiManager _manager;

	public VisualizerApplet(MidiManager manager) {
		_manager = manager;
		manager.addClockListener(_visualizer);
	}

	public void draw() {
		if (_visualizer != null) {
			_visualizer.draw();
		}
	}

	@Override
	public void settings() {
		fullScreen(2);
	}

	@Override
	public void setup() {
		if (_visualizer != null) {
			_visualizer.setup();
		}
	}

	public void setVisualizer(Visualizer visualizer) {
		_manager.removeClockListener(_visualizer);
		_visualizer = visualizer;
		_manager.addClockListener(_visualizer);
		visualizer.setup();
	}
}
