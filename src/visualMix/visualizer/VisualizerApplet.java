package visualMix.visualizer;

import processing.core.PApplet;

public class VisualizerApplet extends PApplet {

	private final Visualizer _visualizer;

	public VisualizerApplet(Visualizer visualizer) {
		_visualizer = visualizer;
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

	@Override
	public void exitActual() {
		// empty
	}

}
