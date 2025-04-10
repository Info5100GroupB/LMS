package application.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class HomeController {

    @FXML private Button searchButton;
    @FXML private Button addResourceButton;
    @FXML private Button registerReaderButton;
    @FXML private Button viewReadersButton;

    @FXML
    private void handleSearchButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/Search.fxml"));
            Parent searchRoot = loader.load();
            Stage stage = (Stage) searchButton.getScene().getWindow();
            stage.setScene(new Scene(searchRoot));
            stage.setTitle("Search Books - Library Management System");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleAddResourceButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/AddResource.fxml"));
            Parent searchRoot = loader.load();
            Stage stage = (Stage) addResourceButton.getScene().getWindow();
            stage.setScene(new Scene(searchRoot));
            stage.setTitle("Add Resource - Library Management System");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleRegisterReader() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/RegisterReader.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) registerReaderButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Register Reader - Library Management System");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewReaders() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/ViewReaders.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) viewReadersButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("View Readers - Library Management System");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
