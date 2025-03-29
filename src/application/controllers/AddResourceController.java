package application.controllers;

import java.io.IOException;

import application.Classes.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class AddResourceController {

    @FXML
    private Button SaveButton;
    
    @FXML
    private Button CancelButton;

    @FXML
    private ComboBox<String> ResourceComboBox;
    
    @FXML
    private TextField TitleField;
    
    @FXML
    private TextField PublisherField;
    
    @FXML
    private TextField AuthorField;
    
    @FXML
    private TextField SubjectField;
    
    @FXML
    private TextField ISBNField;
    
    @FXML
    private TextField EditorField;

    @FXML
    public void initialize() {
        ResourceComboBox.getItems().addAll("Book", "CD");
    }

    @FXML
    private void handleCancelButtonClick() {
        try {
            goBackToMainScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	private void goBackToMainScreen() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/Home.fxml"));
		Parent searchRoot = loader.load();
		Stage stage = (Stage) CancelButton.getScene().getWindow();
		stage.setScene(new Scene(searchRoot));
		stage.setTitle("Search Books - Library Management System");
	}
    
    @FXML
    private void handleAddResourceButtonClick() {
        try {
        	if (areFieldsFilled()) {
                if (ResourceComboBox.getValue().equals("Book")) {
					Book book = new Book(TitleField.getText(), AuthorField.getText(), EditorField.getText(), PublisherField.getText(), SubjectField.getText(), ISBNField.getText());
					if (book.getISBNNum() == null) {
		                return;
		            }
					Resource.addBookToTXT(book, "src/application/books_dataset.txt");
				} else if (ResourceComboBox.getValue().equals("CD")) {
					CD cd = new CD(TitleField.getText(), AuthorField.getText(), ISBNField.getText(), PublisherField.getText());
					Resource.addCDToTXT(cd, "src/application/cd_dataset.txt");
				}
                
				// Show success message
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Resource Added");
				alert.setHeaderText(null);
				alert.setContentText("Resource has been added successfully.");
				alert.showAndWait();
				
				goBackToMainScreen();
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Incomplete Form");
                alert.setHeaderText(null);
                if (ResourceComboBox.getValue() == null) {
					alert.setContentText("Please select a resource type.");
				} else if (ResourceComboBox.getValue().equals("Book")) {
					alert.setContentText("Please fill in all fields related to Book.");
				} else if (ResourceComboBox.getValue().equals("CD")) {
					alert.setContentText("Please fill in all fields related to CD.");
				}
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private boolean areFieldsFilled() {
        if (ResourceComboBox.getValue() == null) {
            return false;
        }

        if (ResourceComboBox.getValue().equals("Book")) {
            return !TitleField.getText().isEmpty() &&
                   !PublisherField.getText().isEmpty() &&
                   !ISBNField.getText().isEmpty() &&
                   !AuthorField.getText().isEmpty() &&
                   !SubjectField.getText().isEmpty() &&
                   !EditorField.getText().isEmpty();
        } else if (ResourceComboBox.getValue().equals("CD")) {
            return !TitleField.getText().isEmpty() &&
                   !PublisherField.getText().isEmpty() &&
                   !AuthorField.getText().isEmpty() &&
                   !ISBNField.getText().isEmpty();
        }

        return false;
    }
}
