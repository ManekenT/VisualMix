package main.midiInterface;

public interface MidiClockListener {
	public void onClockTick();

	public void onClockRestart();
}
