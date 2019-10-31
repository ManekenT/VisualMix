package visualMix.midiInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import themidibus.MidiBus;
import themidibus.SimpleMidiListener;
import visualMix.configuration.MidiConfig;
import visualMix.configuration.MidiDeviceChangeListener;

/**
 * Stores the MIDI values received by the connected controller, to be read by
 * the visualisation
 * 
 * @author ManekenT
 *
 */
public class MidiController implements SimpleMidiListener, MidiDeviceChangeListener {

	private static MidiController INSTANCE;

	private MidiController() {
		init();
	}

	public static MidiController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MidiController();
		}
		return INSTANCE;
	}

	private MidiBus _midibus = new MidiBus();
	private MidiConfig _midiconfig = MidiConfig.getInstance();

	private List<MidiCCListener> _changeListener = new ArrayList<>();

	private int _eqHighA;
	private int _eqMidA;
	private int _eqLowA;
	private int _beatPhaseA;
	private int _faderLevelA;
	private int _eqHighB;
	private int _eqMidB;
	private int _eqLowB;
	private int _beatPhaseB;
	private int _faderLevelB;
	private int _crossfader;

	private void init() {
		_midibus.addMidiListener(this);
		_midiconfig.addChangeListener(this);
	}

	public void addCCListener(MidiCCListener listener) {
		_changeListener.add(listener);
	}

	private void fireValueChangedEvent(int channel, int number, int value) {
		for (MidiCCListener listener : _changeListener) {
			listener.onCC(channel, number, value);
		}
	}

	public int getEqHighA() {
		return _eqHighA;
	}

	public int getEqMidA() {
		return _eqMidA;
	}

	public int getEqLowA() {
		return _eqLowA;
	}

	public int getBeatPhaseA() {
		return _beatPhaseA;
	}

	public int getFaderLevelA() {
		return _faderLevelA;
	}

	public int getEqHighB() {
		return _eqHighB;
	}

	public int getEqMidB() {
		return _eqMidB;
	}

	public int getEqLowB() {
		return _eqLowB;
	}

	public int getBeatPhaseB() {
		return _beatPhaseB;
	}

	public int getFaderLevelB() {
		return _faderLevelB;
	}

	public int getCrossfader() {
		return _crossfader;
	}

	@Override
	public void controllerChange(int channel, int number, int value) {
		// TODO

		if (channel == _midiconfig.getCrossfaderChannel() && number == _midiconfig.getCrossfaderCC()) {
			_crossfader = value;
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

	@Override
	public void onMidiDeviceChange() {
		List<String> inputs = Arrays.asList(_midibus.attachedInputs());
		if (!inputs.contains(_midiconfig.getMidiDevice())) {
			clearMidiInouts();
			_midibus.addInput(_midiconfig.getMidiDevice());
		}
		// TODO
	}

	private void clearMidiInouts() {
		for (String s : _midibus.attachedInputs()) {
			_midibus.removeInput(s);
		}
	}

}
