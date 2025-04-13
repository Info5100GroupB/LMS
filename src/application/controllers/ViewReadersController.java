package application.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import application.Classes.Reader;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.collections.transformation.FilteredList;

public class ViewReadersController {
    @FXML private TableView<Reader> readerTable;
    @FXML private TableColumn<Reader, String> idColumn;
    @FXML private TableColumn<Reader, String> nameColumn;
    @FXML private TableColumn<Reader, String> phoneColumn;
    @FXML private TextField readerSearchField;

    @FXML
    public void initialize() {
    	if (Reader.getAllReaders().isEmpty()) {
			Reader.loadReadersFromCSV("src/application/reader_dataset.txt");
		}
    	
        idColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getReaderId()));
        nameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        phoneColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPhoneNumber()));

        ObservableList<Reader> allReaders = FXCollections.observableArrayList(Reader.getAllReaders().values());
        FilteredList<Reader> filtered = new FilteredList<>(allReaders, p -> true);
        readerTable.setItems(filtered);

        // Filter on search
        readerSearchField.textProperty().addListener((obs, oldVal, newVal) -> {
            String query = newVal.toLowerCase();
            filtered.setPredicate(reader ->
                reader.getName().toLowerCase().contains(query) ||
                reader.getPhoneNumber().toLowerCase().contains(query)
            );
        });
    }

    @FXML
    private void handleViewProfile() throws Exception {
        Reader selected = readerTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/ReaderProfile.fxml"));
        Parent root = loader.load();
        ReaderProfileController controller = loader.getController();
        controller.setReader(selected);

        Stage stage = (Stage) readerTable.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void handleBack() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/application/views/Home.fxml"));
        Stage stage = (Stage) readerTable.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}