module visualMix {
	requires themidibus;
	requires core;
	requires transitive javafx.graphics;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	requires java.desktop;
	requires java.sql;
	requires java.xml;

	opens main.configuration to javafx.fxml;
	opens main to javafx.graphics;
}