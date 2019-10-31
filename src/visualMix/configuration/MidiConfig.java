package visualMix.configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the MIDI Channel and CC values that are configured.
 * 
 * @author ManekenT
 *
 */
public class MidiConfig {

	private static MidiConfig INSTANCE;

	private MidiConfig() {
		// empty
	}

	public static MidiConfig getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MidiConfig();
		}
		return INSTANCE;
	}

	private List<MidiDeviceChangeListener> _changeListener = new ArrayList<>();

	private int _eqHighAChannel;
	private int _eqHighACC;
	private int _eqMidAChannel;
	private int _eqMidACC;
	private int _eqLowAChannel;
	private int _eqLowACC;
	private int _beatPhaseAChannel;
	private int _beatPhaseACC;
	private int _faderLevelAChannel;
	private int _faderLevelACC;
	private int _eqHighBChannel;
	private int _eqHighBCC;
	private int _eqMidBChannel;
	private int _eqMidBCC;
	private int _eqLowBChannel;
	private int _eqLowBCC;
	private int _beatPhaseBChannel;
	private int _beatPhaseBCC;
	private int _faderLevelBChannel;
	private int _faderLevelBCC;
	private int _crossfaderChannel;
	private int _crossfaderCC;

	private String _midiDevice;

	private void fireDeviceChangedEvent() {
		for (MidiDeviceChangeListener listener : _changeListener) {
			listener.onMidiDeviceChange();
		}
	}

	public void addChangeListener(MidiDeviceChangeListener listener) {
		_changeListener.add(listener);
	}

	public String getMidiDevice() {
		return _midiDevice;
	}

	public void setMidiDevice(String midiDevice) {
		_midiDevice = midiDevice;
		fireDeviceChangedEvent();
	}

	public int getEqHighAChannel() {
		return _eqHighAChannel;
	}

	public void setEqHighAChannel(int eqHighAChannel) {
		_eqHighAChannel = eqHighAChannel;
	}

	public int getEqHighACC() {
		return _eqHighACC;
	}

	public void setEqHighACC(int eqHighACC) {
		_eqHighACC = eqHighACC;
	}

	public int getEqMidAChannel() {
		return _eqMidAChannel;
	}

	public void setEqMidAChannel(int eqMidAChannel) {
		_eqMidAChannel = eqMidAChannel;
	}

	public int getEqMidACC() {
		return _eqMidACC;
	}

	public void setEqMidACC(int eqMidACC) {
		_eqMidACC = eqMidACC;
	}

	public int getEqLowAChannel() {
		return _eqLowAChannel;
	}

	public void setEqLowAChannel(int eqLowAChannel) {
		_eqLowAChannel = eqLowAChannel;
	}

	public int getEqLowACC() {
		return _eqLowACC;
	}

	public void setEqLowACC(int eqLowACC) {
		_eqLowACC = eqLowACC;
	}

	public int getBeatPhaseAChannel() {
		return _beatPhaseAChannel;
	}

	public void setBeatPhaseAChannel(int beatPhaseAChannel) {
		_beatPhaseAChannel = beatPhaseAChannel;
	}

	public int getBeatPhaseACC() {
		return _beatPhaseACC;
	}

	public void setBeatPhaseACC(int beatPhaseACC) {
		_beatPhaseACC = beatPhaseACC;
	}

	public int getFaderLevelAChannel() {
		return _faderLevelAChannel;
	}

	public void setFaderLevelAChannel(int faderLevelAChannel) {
		_faderLevelAChannel = faderLevelAChannel;
	}

	public int getFaderLevelACC() {
		return _faderLevelACC;
	}

	public void setFaderLevelACC(int faderLevelACC) {
		_faderLevelACC = faderLevelACC;
	}

	public int getEqHighBChannel() {
		return _eqHighBChannel;
	}

	public void setEqHighBChannel(int eqHighBChannel) {
		_eqHighBChannel = eqHighBChannel;
	}

	public int getEqHighBCC() {
		return _eqHighBCC;
	}

	public void setEqHighBCC(int eqHighBCC) {
		_eqHighBCC = eqHighBCC;
	}

	public int getEqMidBChannel() {
		return _eqMidBChannel;
	}

	public void setEqMidBChannel(int eqMidBChannel) {
		_eqMidBChannel = eqMidBChannel;
	}

	public int getEqMidBCC() {
		return _eqMidBCC;
	}

	public void setEqMidBCC(int eqMidBCC) {
		_eqMidBCC = eqMidBCC;
	}

	public int getEqLowBChannel() {
		return _eqLowBChannel;
	}

	public void setEqLowBChannel(int eqLowBChannel) {
		_eqLowBChannel = eqLowBChannel;
	}

	public int getEqLowBCC() {
		return _eqLowBCC;
	}

	public void setEqLowBCC(int eqLowBCC) {
		_eqLowBCC = eqLowBCC;
	}

	public int getBeatPhaseBChannel() {
		return _beatPhaseBChannel;
	}

	public void setBeatPhaseBChannel(int beatPhaseBChannel) {
		_beatPhaseBChannel = beatPhaseBChannel;
	}

	public int getBeatPhaseBCC() {
		return _beatPhaseBCC;
	}

	public void setBeatPhaseBCC(int beatPhaseBCC) {
		_beatPhaseBCC = beatPhaseBCC;
	}

	public int getFaderLevelBChannel() {
		return _faderLevelBChannel;
	}

	public void setFaderLevelBChannel(int faderLevelBChannel) {
		_faderLevelBChannel = faderLevelBChannel;
	}

	public int getFaderLevelBCC() {
		return _faderLevelBCC;
	}

	public void setFaderLevelBCC(int faderLevelBCC) {
		_faderLevelBCC = faderLevelBCC;
	}

	public int getCrossfaderChannel() {
		return _crossfaderChannel;
	}

	public void setCrossfaderChannel(int crossfaderChannel) {
		_crossfaderChannel = crossfaderChannel;
	}

	public int getCrossfaderCC() {
		return _crossfaderCC;
	}

	public void setCrossfaderCC(int crossfaderCC) {
		_crossfaderCC = crossfaderCC;
	}
}
