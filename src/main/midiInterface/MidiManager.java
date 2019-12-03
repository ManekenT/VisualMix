package main.midiInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sound.midi.MidiMessage;

import main.visualizer.Visualizer;
import themidibus.MidiBus;
import themidibus.SimpleMidiListener;
import themidibus.StandardMidiListener;

/**
 * Stores the MIDI values received by the connected controller, to be read by
 * the visualisation
 * 
 * @author ManekenT
 *
 */
public class MidiManager implements SimpleMidiListener, StandardMidiListener {

	private static int CLOCK_CC = 248;
	private static int CLOCK_START = 250;

	public MidiManager() {
		init();
	}

	private MidiBus _midibus = new MidiBus();
	private String _midiDevice;

	private List<MidiValueListener> _valueListener = new ArrayList<>();
	private List<MidiClockListener> _clockListener = new ArrayList<>();

	private void init() {
		_midibus.addMidiListener(this);
	}

	public void addValueListener(MidiValueListener listener) {
		_valueListener.add(listener);
	}

	public void addClockListener(MidiClockListener listener) {
		_clockListener.add(listener);
	}

	private void fireValueChangedEvent(int channel, int number, int value) {
		for (MidiValueListener listener : _valueListener) {
			listener.onValueChange(channel, number, value);
		}
	}

	private void fireClockTickEvent() {
		for (MidiClockListener listener : _clockListener) {
			listener.onClockTick();
		}
	}

	private void fireClockRestartEvent() {
		for (MidiClockListener listener : _clockListener) {
			listener.onClockRestart();
		}
	}

	@Override
	public void controllerChange(int channel, int number, int value) {
		for (Controls controllerValue : Controls.values()) {
			if (channel == controllerValue.getChannel() && number == controllerValue.getCC()) {
				controllerValue.setValue(value);
			}
		}
		fireValueChangedEvent(channel, number, value);
	}

	@Override
	public void noteOff(int arg0, int arg1, int arg2) {
		// empty
	}

	@Override
	public void noteOn(int arg0, int arg1, int arg2) {
		// empty
	}

	private void clearMidiInouts() {
		for (String s : _midibus.attachedInputs()) {
			_midibus.removeInput(s);
		}
	}

	@Override
	public void midiMessage(MidiMessage message, long timestamp) {
		if (message.getStatus() == CLOCK_CC) {
			fireClockTickEvent();
		}
		if (message.getStatus() == CLOCK_START) {
			fireClockRestartEvent();
		}
	}

	public void setMidiDevice(String midiDevice) {
		_midiDevice = midiDevice;
		List<String> inputs = Arrays.asList(_midibus.attachedInputs());
		if (!inputs.contains(_midiDevice)) {
			clearMidiInouts();
			_midibus.addInput(_midiDevice);
		}
	}

	public void removeClockListener(Visualizer _visualizer) {
		_clockListener.remove(_visualizer);
	}

}
