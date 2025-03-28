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
        Parent root = FXMLLoader.load(getClass().getResource("views/Home.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Library Management System - Home");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Load book and CD resources before launching GUI
        Resource.loadBooksFromTXT("src/application/books_dataset.txt");
        Resource.loadCDsFromTXT("src/application/cd_dataset.csv");

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
