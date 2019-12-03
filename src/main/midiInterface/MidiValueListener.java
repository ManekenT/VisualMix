package main.midiInterface;

public interface MidiValueListener {
	public void onValueChange(int channel, int number, int value);
}
