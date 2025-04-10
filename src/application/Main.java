package application;

import application.Classes.Resource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load FXML and start the GUI
    	Parent root = FXMLLoader.load(getClass().getResource("/application/views/Login.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Library Management System - Login");
        primaryStage.setWidth(950);
        primaryStage.setHeight(600);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Load book and CD resources before launching GUI
        Resource.loadBooksFromTXT("src/application/books_dataset.txt");
        Resource.loadCDsFromTXT("src/application/cd_dataset.txt");

        // Optional: Print summary to console
        System.out.println("Library initialized. Resources loaded:");
        for (var resource : Resource.getAllResources().values()) {
            String type = resource.getClass().getSimpleName();
            System.out.println(type + ": " + resource.getTitle() + " - " + resource.getPublisher());
        }

        // Launch JavaFX application
        launch(args);
    }
}
