package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load FXML and start the GUI
    	Parent root = FXMLLoader.load(getClass().getResource("views/Home.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Library Management System - Home");
        primaryStage.setScene(scene);
        primaryStage.setWidth(950);
        primaryStage.setHeight(600);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Launch JavaFX application
        launch(args);
    }
}
