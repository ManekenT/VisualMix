module visualMix {
	requires themidibus;
	requires core;
	requires transitive javafx.graphics;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	requires java.desktop;

	opens visualMix.configuration to javafx.fxml;
	opens visualMix to javafx.graphics;

}