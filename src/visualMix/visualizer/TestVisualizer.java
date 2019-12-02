package visualMix.visualizer;

import processing.core.PApplet;
import visualMix.midiInterface.ControllerValues;

public class TestVisualizer extends Visualizer {

	private float _clockCircleSizeQuarter = 0;
	private float _clockCircleSizeHalf = 0;
	private float _clockCircleSizeEighth = 0;

	@Override
	public void draw(PApplet processing) {
		processing.clear();
		processing.rect(processing.width / 2.0F, processing.height / 2.0F,
				((float) (100 * ControllerValues.getEQHigh())), 200F);
		processing.ellipse(processing.width * 0.5F, processing.height / 4F, _clockCircleSizeQuarter,
				_clockCircleSizeQuarter);
		_clockCircleSizeQuarter = _clockCircleSizeQuarter * 0.9F;
		processing.ellipse(processing.width * 0.25F, processing.height / 4F, _clockCircleSizeHalf,
				_clockCircleSizeHalf);
		_clockCircleSizeHalf = _clockCircleSizeHalf * 0.9F;
		processing.ellipse(processing.width * 0.75F, processing.height / 4F, _clockCircleSizeEighth,
				_clockCircleSizeEighth);
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
}
