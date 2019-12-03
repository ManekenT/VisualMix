package main.configuration;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import main.midiInterface.Controls;
import main.midiInterface.MidiClockListener;
import main.midiInterface.MidiManager;
import main.midiInterface.MidiValueListener;
import main.visualizer.Visualizer;
import main.visualizer.VisualizerApplet;
import themidibus.MidiBus;

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
	private Label ccDebug;

	@FXML
	private Label clockDebug;

	private VisualizerApplet _applet;
	private MidiManager _midiManager;
	private Visualizer _defaultVisualizer;

	public void initialize() {
		visualizerComboBox.setItems(getAvailableVisualizer());
		visualizerComboBox.setOnAction(a -> onVisualizerChanged());

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
				e -> Controls.CROSSFADER.setChannel(crossfaderChannel.getSelectionModel().getSelectedItem()));
		crossfaderCC.setOnAction(e -> Controls.CROSSFADER.setCC(crossfaderCC.getSelectionModel().getSelectedItem()));
		eqHighAChannel
				.setOnAction(e -> Controls.EQ_HIGH_A.setChannel(eqHighAChannel.getSelectionModel().getSelectedItem()));
		eqHighACC.setOnAction(e -> Controls.EQ_HIGH_A.setCC(eqHighACC.getSelectionModel().getSelectedItem()));
		eqHighBChannel
				.setOnAction(e -> Controls.EQ_HIGH_B.setChannel(eqHighBChannel.getSelectionModel().getSelectedItem()));
		eqHighBCC.setOnAction(e -> Controls.EQ_HIGH_B.setCC(eqHighBCC.getSelectionModel().getSelectedItem()));
		eqMidAChannel
				.setOnAction(e -> Controls.EQ_MID_A.setChannel(eqMidAChannel.getSelectionModel().getSelectedItem()));
		eqMidACC.setOnAction(e -> Controls.EQ_MID_A.setCC(eqMidACC.getSelectionModel().getSelectedItem()));
		eqMidBChannel
				.setOnAction(e -> Controls.EQ_MID_B.setChannel(eqMidBChannel.getSelectionModel().getSelectedItem()));
		eqMidBCC.setOnAction(e -> Controls.EQ_MID_B.setCC(eqMidBCC.getSelectionModel().getSelectedItem()));
		eqLowAChannel
				.setOnAction(e -> Controls.EQ_LOW_A.setChannel(eqLowAChannel.getSelectionModel().getSelectedItem()));
		eqLowACC.setOnAction(e -> Controls.EQ_LOW_A.setCC(eqLowACC.getSelectionModel().getSelectedItem()));
		eqLowBChannel
				.setOnAction(e -> Controls.EQ_LOW_B.setChannel(eqLowBChannel.getSelectionModel().getSelectedItem()));
		eqLowBCC.setOnAction(e -> Controls.EQ_LOW_B.setCC(eqLowBCC.getSelectionModel().getSelectedItem()));
		faderLevelAChannel.setOnAction(
				e -> Controls.FADER_LEVEL_A.setChannel(faderLevelAChannel.getSelectionModel().getSelectedItem()));
		faderLevelACC
				.setOnAction(e -> Controls.FADER_LEVEL_A.setCC(faderLevelACC.getSelectionModel().getSelectedItem()));
		faderLevelBChannel.setOnAction(
				e -> Controls.FADER_LEVEL_B.setChannel(faderLevelBChannel.getSelectionModel().getSelectedItem()));
		faderLevelBCC
				.setOnAction(e -> Controls.FADER_LEVEL_B.setCC(faderLevelBCC.getSelectionModel().getSelectedItem()));
	}

	public void injectModels(MidiManager manager, VisualizerApplet applet, Visualizer defaultVisualizer) {
		_applet = applet;
		_midiManager = manager;
		_defaultVisualizer = defaultVisualizer;
		initMidiDebugListener();
		visualizerComboBox.getItems().add(0, _defaultVisualizer);
		visualizerComboBox.getSelectionModel().select(_defaultVisualizer);
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

	private Visualizer getSelectedVisualizer() {
		return visualizerComboBox.getSelectionModel().getSelectedItem();
	}

	private ObservableList<Visualizer> getAvailableVisualizer() {
		ObservableList<Visualizer> vis = FXCollections.observableArrayList();
		return vis;
	}

	private ObservableList<String> getAvailableMidiDevices() {
		return FXCollections.observableArrayList(MidiBus.availableInputs());
	}

	private void initMidiDebugListener() {
		_midiManager.addValueListener(new MidiValueListener() {

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

		_midiManager.addClockListener(new MidiClockListener() {
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
		_midiManager.setMidiDevice(midiDevicesComboBox.getSelectionModel().getSelectedItem());
	}

	private void onVisualizerChanged() {
		_applet.setVisualizer(getSelectedVisualizer());
	}

	public void loadMappingsFromModel() {
		eqHighAChannel.setValue(Controls.EQ_HIGH_A.getChannel());
		eqHighACC.setValue(Controls.EQ_HIGH_A.getCC());
		eqMidAChannel.setValue(Controls.EQ_MID_A.getChannel());
		eqMidACC.setValue(Controls.EQ_MID_A.getCC());
		eqLowAChannel.setValue(Controls.EQ_LOW_A.getChannel());
		eqLowACC.setValue(Controls.EQ_LOW_A.getCC());
		faderLevelAChannel.setValue(Controls.FADER_LEVEL_A.getChannel());
		faderLevelACC.setValue(Controls.FADER_LEVEL_A.getCC());
		eqHighBChannel.setValue(Controls.EQ_HIGH_B.getChannel());
		eqHighBCC.setValue(Controls.EQ_HIGH_B.getCC());
		eqMidBChannel.setValue(Controls.EQ_MID_B.getChannel());
		eqMidBCC.setValue(Controls.EQ_MID_B.getCC());
		eqLowBChannel.setValue(Controls.EQ_LOW_B.getChannel());
		eqLowBCC.setValue(Controls.EQ_LOW_B.getCC());
		faderLevelBChannel.setValue(Controls.FADER_LEVEL_B.getChannel());
		faderLevelBCC.setValue(Controls.FADER_LEVEL_B.getCC());
		crossfaderChannel.setValue(Controls.CROSSFADER.getChannel());
		crossfaderCC.setValue(Controls.CROSSFADER.getCC());
	}

}
