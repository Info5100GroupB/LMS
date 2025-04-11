package application.controllers;

import application.Classes.Reader;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class RegisterReaderController {
    @FXML private TextField nameField;
    @FXML private TextField phoneField;

    @FXML
    private void handleRegister() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill in all fields.").show();
            return;
        }
        
        if (!phone.matches("\\d+")) {
            new Alert(Alert.AlertType.WARNING, "Phone number must contain only digits.").show();
            return;
        }

        String readerId = "R" + System.currentTimeMillis();
        new Reader(readerId, name, phone);
        new Alert(Alert.AlertType.INFORMATION, "Reader registered successfully.").show();
        nameField.clear();
        phoneField.clear();
    }

    @FXML
    private void handleBack() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/application/views/Home.fxml"));
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}