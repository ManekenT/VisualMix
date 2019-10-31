package visualMix.visualizer;

import processing.core.PApplet;
import visualMix.midiInterface.MidiController;

public abstract class Visualizer {

	protected final MidiController _values = MidiController.getInstance();

	public abstract void draw(PApplet processing);

	public abstract void settings(PApplet processing);

	public abstract void setup(PApplet processing);

	public abstract String getName();

	@Override
	public String toString() {
		return getName();
	}
}
