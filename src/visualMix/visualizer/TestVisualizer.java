package visualMix.visualizer;

import processing.core.PApplet;

public class TestVisualizer extends Visualizer {

	@Override
	public void draw(PApplet processing) {
		processing.ellipse(_values.getCrossfader(), processing.height / 2, PApplet.second(), PApplet.second());
	}

	@Override
	public String getName() {
		return "Test Visualizer";
	}

	@Override
	public void settings(PApplet processing) {
		processing.size(1000, 700);
	}

	@Override
	public void setup(PApplet processing) {
		processing.fill(120, 50, 240);
	}

}
