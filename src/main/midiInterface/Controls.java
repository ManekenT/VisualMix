package main.midiInterface;

public enum Controls {
	EQ_HIGH_A, EQ_MID_A, EQ_LOW_A, FADER_LEVEL_A, EQ_HIGH_B, EQ_MID_B, EQ_LOW_B, FADER_LEVEL_B, CROSSFADER;

	private int _channel;
	private int _cc;
	private int _value;

	public int getValue() {
		return _value;
	}

	public void setValue(int value) {
		_value = value;
	}

	public int getChannel() {
		return _channel;
	}

	public void setChannel(int channel) {
		_channel = channel;
	}

	public int getCC() {
		return _cc;
	}

	public void setCC(int cc) {
		_cc = cc;
	}

	/**
	 * Use this method to normalize the value of a knob style control (usually 0 -
	 * 127, with 63 being neutral)
	 * 
	 * @return a float from -1 to 1, with 0 being a neutral knob position
	 */
	public double normalizeKnob() {
		return (_value - 63.5) / 63.5;
	}

	/**
	 * Use this method to normalize the value of a fader style control (usually 0 -
	 * 127)
	 * 
	 * @return a float from 0 to 1 representing the fader position
	 */
	public double normalizeFader() {
		return _value / 127;
	}

	/**
	 * Normalized the EQ values of two of the same knob from different decks (i.e.
	 * EQ High A and EQ High B) depending on the fader values of the respective
	 * decks.
	 * 
	 * @return a float from -1 to 1, with 0 being a neutral knob position (between
	 *         both decks)
	 */
	public static double getNormalizedKnobs(Controls knobA, Controls knobB) {
		float faderSum = FADER_LEVEL_A.getValue() + FADER_LEVEL_B.getValue();
		if (faderSum == 0) {
			return 0;
		}
		double portionFaderA = FADER_LEVEL_A.getValue() / faderSum;
		double portionFaderB = FADER_LEVEL_B.getValue() / faderSum;
		return portionFaderA * knobA.normalizeKnob() + portionFaderB * knobB.normalizeKnob();
	}

}
