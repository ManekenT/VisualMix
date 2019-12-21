package main.visualizer;

import main.midiInterface.Controls;
import processing.core.PApplet;

public class TestVisualizer extends Visualizer {

	private float _clockCircleSizeQuarter = 0;
	private float _clockCircleSizeHalf = 0;
	private float _clockCircleSizeEighth = 0;

	public TestVisualizer(PApplet applet) {
		super(applet);
	}

	@Override
	public void draw() {
		_applet.background(0);
		_applet.noStroke();
		_applet.fill(Controls.FADER_LEVEL_A.getValue() * 2F, 125, Controls.FADER_LEVEL_B.getValue() * 2F);
		_applet.rect(_applet.width / 2.0F, _applet.height / 2.0F,
				((float) (100 * Controls.getNormalizedKnobs(Controls.EQ_HIGH_A, Controls.EQ_HIGH_B))), 200F);
		_applet.ellipse(_applet.width * 0.5F, _applet.height / 4F, _clockCircleSizeQuarter, _clockCircleSizeQuarter);
		_clockCircleSizeQuarter = _clockCircleSizeQuarter * 0.9F;
		_applet.ellipse(_applet.width * 0.25F, _applet.height / 4F, _clockCircleSizeHalf, _clockCircleSizeHalf);
		_clockCircleSizeHalf = _clockCircleSizeHalf * 0.9F;
		_applet.ellipse(_applet.width * 0.75F, _applet.height / 4F, _clockCircleSizeEighth, _clockCircleSizeEighth);
		_clockCircleSizeEighth = _clockCircleSizeEighth * 0.9F;

	}

	@Override
	public String getName() {
		return "Test Visualizer";
	}

	@Override
	public void quarterNote() {
		_clockCircleSizeQuarter = 400;
	}

	@Override
	public void halfNote() {
		_clockCircleSizeHalf = 400;
	}

	@Override
	public void eighthNote() {
		_clockCircleSizeEighth = 400;
	}

	@Override
	protected void setup() {

	}
}
