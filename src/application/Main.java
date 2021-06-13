package application;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class Main extends Application{
 
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage primaryStage) throws Exception {
       Parent root = FXMLLoader.load(getClass().getResource("root.fxml"));
       Scene scene = new Scene(root);
       
       primaryStage.setTitle("hi");
       primaryStage.setResizable(false);
       primaryStage.setScene(scene);
       scene.getStylesheets().add(getClass().getResource("application.css").toString());

       primaryStage.show();
    }
    
}
