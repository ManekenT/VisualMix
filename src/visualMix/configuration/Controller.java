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
import visualMix.midiInterface.ControllerValues;
import visualMix.midiInterface.MidiClockListener;
import visualMix.midiInterface.MidiListener;
import visualMix.midiInterface.MidiValueListener;
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

	@FXML
	private Label clockDebug;

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
		initializeChannelComboBox(faderLevelAChannel);
		initializeCCComboBox(faderLevelACC);
		initializeChannelComboBox(eqHighBChannel);
		initializeCCComboBox(eqHighBCC);
		initializeChannelComboBox(eqMidBChannel);
		initializeCCComboBox(eqMidBCC);
		initializeChannelComboBox(eqLowBChannel);
		initializeCCComboBox(eqLowBCC);
		initializeChannelComboBox(faderLevelBChannel);
		initializeCCComboBox(faderLevelBCC);
		initializeChannelComboBox(crossfaderChannel);
		initializeCCComboBox(crossfaderCC);

		crossfaderChannel.setOnAction(
				e -> ControllerValues.CROSSFADER.setChannel(crossfaderChannel.getSelectionModel().getSelectedItem()));
		crossfaderCC.setOnAction(
				e -> ControllerValues.CROSSFADER.setCC(crossfaderCC.getSelectionModel().getSelectedItem()));
		eqHighAChannel.setOnAction(
				e -> ControllerValues.EQ_HIGH_A.setChannel(eqHighAChannel.getSelectionModel().getSelectedItem()));
		eqHighACC.setOnAction(e -> ControllerValues.EQ_HIGH_A.setCC(eqHighACC.getSelectionModel().getSelectedItem()));
		eqHighBChannel.setOnAction(
				e -> ControllerValues.EQ_HIGH_B.setChannel(eqHighBChannel.getSelectionModel().getSelectedItem()));
		eqHighBCC.setOnAction(e -> ControllerValues.EQ_HIGH_B.setCC(eqHighBCC.getSelectionModel().getSelectedItem()));
		eqMidAChannel.setOnAction(
				e -> ControllerValues.EQ_MID_A.setChannel(eqMidAChannel.getSelectionModel().getSelectedItem()));
		eqMidACC.setOnAction(e -> ControllerValues.EQ_MID_A.setCC(eqMidACC.getSelectionModel().getSelectedItem()));
		eqMidBChannel.setOnAction(
				e -> ControllerValues.EQ_MID_B.setChannel(eqMidBChannel.getSelectionModel().getSelectedItem()));
		eqMidBCC.setOnAction(e -> ControllerValues.EQ_MID_B.setCC(eqMidBCC.getSelectionModel().getSelectedItem()));
		eqLowAChannel.setOnAction(
				e -> ControllerValues.EQ_LOW_A.setChannel(eqLowAChannel.getSelectionModel().getSelectedItem()));
		eqLowACC.setOnAction(e -> ControllerValues.EQ_LOW_A.setCC(eqLowACC.getSelectionModel().getSelectedItem()));
		eqLowBChannel.setOnAction(
				e -> ControllerValues.EQ_LOW_B.setChannel(eqLowBChannel.getSelectionModel().getSelectedItem()));
		eqLowBCC.setOnAction(e -> ControllerValues.EQ_LOW_B.setCC(eqLowBCC.getSelectionModel().getSelectedItem()));
		faderLevelAChannel.setOnAction(e -> ControllerValues.FADER_LEVEL_A
				.setChannel(faderLevelAChannel.getSelectionModel().getSelectedItem()));
		faderLevelACC.setOnAction(
				e -> ControllerValues.FADER_LEVEL_A.setCC(faderLevelACC.getSelectionModel().getSelectedItem()));
		faderLevelBChannel.setOnAction(e -> ControllerValues.FADER_LEVEL_B
				.setChannel(faderLevelBChannel.getSelectionModel().getSelectedItem()));
		faderLevelBCC.setOnAction(
				e -> ControllerValues.FADER_LEVEL_B.setCC(faderLevelBCC.getSelectionModel().getSelectedItem()));
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
		MidiListener.getInstance().addValueListener(new MidiValueListener() {

			@Override
			public void onValueChange(int channel, int number, int value) {
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

		MidiListener.getInstance().addClockListener(new MidiClockListener() {
			private int _totalTicks;

			@Override
			public void onClockTick() {
				_totalTicks++;
				if (_totalTicks % 48 == 0) {
					Platform.runLater(() -> clockDebug.setText("BEAT"));
				} else {
					Platform.runLater(() -> clockDebug.setText("----"));
				}
			}

			@Override
			public void onClockRestart() {
				_totalTicks = 0;
			}
		});
	}

	private void onMidiDeviceChanged() {
		MidiListener.getInstance().setMidiDevice(midiDevicesComboBox.getSelectionModel().getSelectedItem());
	}

}
