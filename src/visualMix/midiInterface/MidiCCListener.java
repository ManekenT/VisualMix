package visualMix.midiInterface;

public interface MidiCCListener {
	public void onCC(int channel, int number, int value);
}
