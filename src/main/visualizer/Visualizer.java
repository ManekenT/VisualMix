package main.visualizer;

import main.midiInterface.MidiClockListener;
import processing.core.PApplet;

public abstract class Visualizer implements MidiClockListener {

	private int _clockTicks;
	private static int MIDI_CLOCK_DEVISION = 24;

	public abstract void draw(PApplet processing);

	public abstract void settings(PApplet processing);

	public abstract void setup(PApplet processing);

	public abstract String getName();

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public void onClockRestart() {
		_clockTicks = 0;
	}

	@Override
	public void onClockTick() {
		_clockTicks++;
		if (_clockTicks % MIDI_CLOCK_DEVISION == 0) {
			quarterNote();
		}
		if (_clockTicks % (MIDI_CLOCK_DEVISION * 2) == 0) {
			halfNote();
		}
		if (_clockTicks % (MIDI_CLOCK_DEVISION / 2) == 0) {
			eighthNote();
		}
	}

	public abstract void quarterNote();

	public abstract void halfNote();

	public abstract void eighthNote();

}
