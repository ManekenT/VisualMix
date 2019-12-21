package main.visualizer;

import main.midiInterface.MidiClockListener;
import processing.core.PApplet;

public abstract class Visualizer implements MidiClockListener {

	private int _clockTicks;
	private static int MIDI_CLOCK_DEVISION = 24;

	protected abstract void draw();

	protected abstract void setup();

	protected abstract String getName();

	protected final PApplet _applet;

	public Visualizer(PApplet applet) {
		_applet = applet;
	}

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
		if (_clockTicks % (MIDI_CLOCK_DEVISION * 4) == 0) {
			wholeNote();
		}
		if (_clockTicks % (MIDI_CLOCK_DEVISION / 2) == 0) {
			eighthNote();
		}
	}

	protected void wholeNote() {
		// empty
	}

	protected void quarterNote() {
		// empty
	}

	protected void halfNote() {
		// empty
	}

	protected void eighthNote() {
		// empty
	}

}
