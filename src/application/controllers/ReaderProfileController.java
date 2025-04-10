package application.controllers;

import application.Classes.Reader;
import application.Classes.Resource;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.collections.*;

public class ReaderProfileController {

    @FXML private Label readerInfo;
    @FXML private ListView<String> borrowedList;
    @FXML private ListView<String> availableList;

    private Reader reader;
//    private ObservableList<Resource> availableResources = FXCollections.observableArrayList();

    public void setReader(Reader reader) {
        this.reader = reader;
        readerInfo.setText("Reader: " + reader.getName() + " (" + reader.getPhoneNumber() + ")");
        refreshLists();
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private void refreshLists() {
        borrowedList.getItems().clear();
        for (Resource res : reader.getBorrowedBooks()) {
            borrowedList.getItems().add(res.getTitle() + " - " + res.getPublisher());
        }

//        availableList.getItems().clear();
//        availableResources.clear();
//
//        for (Resource res : Resource.getAllResources().values()) {
//            if (res.isAvailable()) {
//                availableResources.add(res);
//                availableList.getItems().add(res.getTitle() + " - " + res.getPublisher());
//            }
//        }
    }

    @FXML
    private void handleReturn() {
        int index = borrowedList.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            Resource res = reader.getBorrowedBooks().get(index);
            reader.returnBook(res);
            refreshLists();
        }
    }

    //@FXML
    //private void handleBorrow() {
    //    int index = availableList.getSelectionModel().getSelectedIndex();
    //    if (index >= 0) {
    //        Resource res = availableResources.get(index);
    //        reader.borrow(res);
    //        refreshLists();
    //    }
    //}

    @FXML
    private void handleBack() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/application/views/ViewReaders.fxml"));
        Stage stage = (Stage) borrowedList.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
    
    @FXML
    private void handleEditReader() {
        TextInputDialog nameDialog = new TextInputDialog(reader.getName());
        nameDialog.setTitle("Edit Reader");
        nameDialog.setHeaderText("Update Reader Name");
        nameDialog.setContentText("Full Name:");

        String updatedName = nameDialog.showAndWait().orElse(null);
        if (updatedName == null || updatedName.isBlank()) return;

        TextInputDialog phoneDialog = new TextInputDialog(reader.getPhoneNumber());
        phoneDialog.setTitle("Edit Reader");
        phoneDialog.setHeaderText("Update Phone Number");
        phoneDialog.setContentText("Phone:");

        String updatedPhone = phoneDialog.showAndWait().orElse(null);
        if (updatedPhone == null || updatedPhone.isBlank()) return;

        reader.setName(updatedName);
        reader.setPhoneNumber(updatedPhone);
        readerInfo.setText("Reader: " + reader.getName() + " (" + reader.getPhoneNumber() + ")");
    }
    
    @FXML
    private void handleDeleteReader() throws Exception {
        if (!reader.getBorrowedBooks().isEmpty()) {
            new Alert(Alert.AlertType.WARNING,
                "This reader cannot be deleted while they still have borrowed resources.")
                .show();
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this reader?");
        confirm.setTitle("Delete Reader");
        confirm.setHeaderText("This action cannot be undone.");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Reader.getAllReaders().remove(reader.getReaderId());
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/application/views/ViewReaders.fxml"));
                    Stage stage = (Stage) readerInfo.getScene().getWindow();
                    stage.setScene(new Scene(root));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
