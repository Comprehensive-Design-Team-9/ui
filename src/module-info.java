module javafx {
	requires javafx.controls;
	requires transitive javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	requires opencsv;
	requires jdk.compiler;
	requires jdk.jartool;
	requires commons.exec;
	requires java.sql;
	
	
	opens application to javafx.graphics, javafx.fxml;
	exports application;
}
