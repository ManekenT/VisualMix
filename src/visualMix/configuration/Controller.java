package visualMix.configuration;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import processing.core.PApplet;
import themidibus.MidiBus;
import visualMix.midiInterface.MidiCCListener;
import visualMix.midiInterface.MidiController;
import visualMix.visualizer.TestVisualizer;
import visualMix.visualizer.Visualizer;
import visualMix.visualizer.VisualizerApplet;

public class Controller {

	@FXML
	private ComboBox<Integer> eqHighAChannel;

	@FXML
	private ComboBox<Integer> eqHighACC;

	@FXML
	private ComboBox<Integer> eqMidAChannel;

	@FXML
	private ComboBox<Integer> eqMidACC;

	@FXML
	private ComboBox<Integer> eqLowAChannel;

	@FXML
	private ComboBox<Integer> eqLowACC;

	@FXML
	private ComboBox<Integer> beatPhaseAChannel;

	@FXML
	private ComboBox<Integer> beatPhaseACC;

	@FXML
	private ComboBox<Integer> faderLevelAChannel;

	@FXML
	private ComboBox<Integer> faderLevelACC;

	@FXML
	private ComboBox<Integer> eqHighBChannel;

	@FXML
	private ComboBox<Integer> eqHighBCC;

	@FXML
	private ComboBox<Integer> eqMidBChannel;

	@FXML
	private ComboBox<Integer> eqMidBCC;

	@FXML
	private ComboBox<Integer> eqLowBChannel;

	@FXML
	private ComboBox<Integer> eqLowBCC;

	@FXML
	private ComboBox<Integer> beatPhaseBChannel;

	@FXML
	private ComboBox<Integer> beatPhaseBCC;

	@FXML
	private ComboBox<Integer> faderLevelBChannel;

	@FXML
	private ComboBox<Integer> faderLevelBCC;

	@FXML
	private ComboBox<Integer> crossfaderChannel;

	@FXML
	private ComboBox<Integer> crossfaderCC;

	@FXML
	private ComboBox<Visualizer> visualizerComboBox;

	@FXML
	private ComboBox<String> midiDevicesComboBox;

	@FXML
	private Button startButton;

	@FXML
	private Label ccDebug;

	private MidiConfig _midiconfig = MidiConfig.getInstance();

	public void initialize() {
		initMidiDebugListener();

		visualizerComboBox.setItems(getAvailableVisualizer());
		visualizerComboBox.getSelectionModel().select(0);

		midiDevicesComboBox.setItems(getAvailableMidiDevices());

		midiDevicesComboBox.setOnAction(a -> onMidiDeviceChanged());

		initializeChannelComboBox(eqHighAChannel);
		initializeCCComboBox(eqHighACC);
		initializeChannelComboBox(eqMidAChannel);
		initializeCCComboBox(eqMidACC);
		initializeChannelComboBox(eqLowAChannel);
		initializeCCComboBox(eqLowACC);
		initializeChannelComboBox(beatPhaseAChannel);
		initializeCCComboBox(beatPhaseACC);
		initializeChannelComboBox(faderLevelAChannel);
		initializeCCComboBox(faderLevelACC);
		initializeChannelComboBox(eqHighBChannel);
		initializeCCComboBox(eqHighBCC);
		initializeChannelComboBox(eqMidBChannel);
		initializeCCComboBox(eqMidBCC);
		initializeChannelComboBox(eqLowBChannel);
		initializeCCComboBox(eqLowBCC);
		initializeChannelComboBox(beatPhaseBChannel);
		initializeCCComboBox(beatPhaseBCC);
		initializeChannelComboBox(faderLevelBChannel);
		initializeCCComboBox(faderLevelBCC);
		initializeChannelComboBox(crossfaderChannel);
		initializeCCComboBox(crossfaderCC);

		crossfaderChannel.setOnAction(
				e -> _midiconfig.setCrossfaderChannel(crossfaderChannel.getSelectionModel().getSelectedItem()));
		crossfaderCC.setOnAction(e -> _midiconfig.setCrossfaderCC(crossfaderCC.getSelectionModel().getSelectedItem()));
	}

	private void initializeChannelComboBox(ComboBox<Integer> comboBox) {
		for (int i = 0; i < 16; i++) {
			comboBox.getItems().add(Integer.valueOf(i));
		}
	}

	private void initializeCCComboBox(ComboBox<Integer> comboBox) {
		for (int i = 0; i < 128; i++) {
			comboBox.getItems().add(Integer.valueOf(i));
		}
	}

	public void onStart() {
		startVisualization();
	}

	private void startVisualization() {
		Visualizer visualizer = getSelectedVisualizer();
		VisualizerApplet applet = new VisualizerApplet(visualizer);
		PApplet.runSketch(new String[] { "visualMix.visualizer.VisualizerApplet" }, applet);
	}

	private Visualizer getSelectedVisualizer() {
		return visualizerComboBox.getSelectionModel().getSelectedItem();
	}

	private ObservableList<Visualizer> getAvailableVisualizer() {
		// TODO
		return FXCollections.observableArrayList(new TestVisualizer());
	}

	private ObservableList<String> getAvailableMidiDevices() {
		return FXCollections.observableArrayList(MidiBus.availableInputs());
	}

	private void initMidiDebugListener() {
		MidiController.getInstance().addCCListener(new MidiCCListener() {

			@Override
			public void onCC(int channel, int number, int value) {
				StringBuilder builder = new StringBuilder();
				builder.append("Channel: ");
				builder.append(channel);
				builder.append(" | ");
				builder.append("Number: ");
				builder.append(number);
				builder.append(" | ");
				builder.append("Value: ");
				builder.append(value);
				Platform.runLater(() -> ccDebug.setText(builder.toString()));
			}
		});
	}

	private void onMidiDeviceChanged() {
		_midiconfig.setMidiDevice(midiDevicesComboBox.getSelectionModel().getSelectedItem());
	}

}
