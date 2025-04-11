package application.controllers;

import application.Classes.Book;
import application.Classes.CD;
import application.Classes.Resource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import application.Classes.Reader;
import java.io.IOException;
import java.util.List;

public class SearchController {

    @FXML private TextField searchField;

    @FXML private ComboBox<String> filterComboBox;

    @FXML private ComboBox<String> availabilityComboBox;

    @FXML private TableView<Resource> resultsTable;

    @FXML private TableColumn<Resource, String> typeColumn;
    @FXML private TableColumn<Resource, String> titleColumn;
    @FXML private TableColumn<Resource, String> authorOrPerformerColumn;
    @FXML private TableColumn<Resource, String> subjectOrCatalogColumn;
    @FXML private TableColumn<Resource, String> publisherColumn;
    @FXML private TableColumn<Resource, String> isbnColumn;
    @FXML private TableColumn<Resource, String> availabilityColumn;
    @FXML private Button CancelButton;
    @FXML private Button searchButton;
    @FXML private Button borrowOrReturnButton;

    private ObservableList<Resource> resourceList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
    	loadResourcesFromFile();
        
        // Initialize filter options
        filterComboBox.getItems().addAll("Title", "Author", "Performer", "Subject", "Catalog Number");
        filterComboBox.setValue("Title");
        availabilityComboBox.getItems().addAll("All", "Available", "Checked Out");
        availabilityComboBox.setValue("All");

        // Set up table column value factories
        typeColumn.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getClass().getSimpleName()));
        
        isbnColumn.setCellValueFactory(cellData ->
        	new javafx.beans.property.SimpleStringProperty(cellData.getValue().getISBNNum()));

        titleColumn.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitle()));

        authorOrPerformerColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Book book) {
                return new javafx.beans.property.SimpleStringProperty(book.getAuthor());
            } else if (cellData.getValue() instanceof CD cd) {
                return new javafx.beans.property.SimpleStringProperty(cd.getPerformer());
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });

        subjectOrCatalogColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Book book) {
                return new javafx.beans.property.SimpleStringProperty(book.getSubject());
            } else if (cellData.getValue() instanceof CD cd) {
                return new javafx.beans.property.SimpleStringProperty(cd.getCatalogNumber());
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });

        publisherColumn.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPublisher()));

        availabilityColumn.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().isAvailable() ? "Available" : "Checked Out"));

        loadResources();
    }

    private void loadResources() {
        for (Resource res : Resource.getAllResources().values()) {
            resourceList.add(res);
        }
        resultsTable.setItems(resourceList);
    }
    
    private void loadResourcesFromFile() {
    	// Load book and CD resources before launching GUI
        Resource.loadBooksFromTXT("src/application/books_dataset.txt");
        Resource.loadCDsFromTXT("src/application/cd_dataset.txt");
        // Print summary to console
        System.out.println("Library initialized. Resources loaded:");
        for (var resource : Resource.getAllResources().values()) {
            String type = resource.getClass().getSimpleName();
            System.out.println(type + ": " + resource.getTitle() + " - " + resource.getPublisher());
        }
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
	private void handleBorrowOrReturn() {
	    Resource selected = resultsTable.getSelectionModel().getSelectedItem();
	    if (selected == null) {
	        new Alert(Alert.AlertType.WARNING, "Please select a resource from the list.").show();
	        return;
	    }

	    if (selected.isAvailable()) {
	        // Borrow logic
	    	Reader reader = chooseReaderByNameOrPhone();
	    	if (reader == null) {
	    	    new Alert(Alert.AlertType.ERROR, "No reader selected.").show();
	    	} else {
	    	    reader.borrow(selected);
	    	    new Alert(Alert.AlertType.INFORMATION, "Resource borrowed successfully.").show();
	    	    refreshTable();
	    	}

	    } else {
	        // Return logic
	        for (Reader reader : Reader.getAllReaders().values()) {
	            if (reader.getBorrowedBooks().contains(selected)) {
	                reader.returnBook(selected);
	                new Alert(Alert.AlertType.INFORMATION, "Resource returned successfully.").show();
	                refreshTable();
	                return;
	            }
	        }
	        new Alert(Alert.AlertType.ERROR, "Borrowing record not found.").show();
	    }
	}
	
	private void refreshTable() {
	    resultsTable.refresh();
	}

	private Reader chooseReaderByNameOrPhone() {
	    Dialog<Reader> dialog = new Dialog<>();
	    dialog.setTitle("Select Reader");
	    dialog.setHeaderText("Search by name or phone number");

	    // UI components
	    TextField searchField = new TextField();
	    ListView<Reader> listView = new ListView<>();

	    ObservableList<Reader> allReaders = FXCollections.observableArrayList(Reader.getAllReaders().values());
	    listView.setItems(allReaders);

	    searchField.textProperty().addListener((obs, oldVal, newVal) -> {
	        listView.setItems(allReaders.filtered(reader -> {
	            String query = newVal.toLowerCase();
	            return reader.getName().toLowerCase().contains(query) ||
	                   reader.getPhoneNumber().toLowerCase().contains(query);
	        }));
	    });

	    listView.setCellFactory(param -> new ListCell<>() {
	        @Override
	        protected void updateItem(Reader reader, boolean empty) {
	            super.updateItem(reader, empty);
	            if (empty || reader == null) {
	                setText("");
	            } else {
	                setText(reader.getName() + " (" + reader.getPhoneNumber() + ")");
	            }
	        }
	    });

	    VBox content = new VBox(10, new Label("Enter name or phone number:"), searchField, listView);
	    dialog.getDialogPane().setContent(content);
	    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

	    dialog.setResultConverter(button -> {
	        if (button == ButtonType.OK) {
	            return listView.getSelectionModel().getSelectedItem();
	        }
	        return null;
	    });

	    return dialog.showAndWait().orElse(null);
	}
	
    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().toLowerCase();
        String filter = filterComboBox.getValue();
        String availability = availabilityComboBox.getValue();

        List<Resource> filtered = resourceList.stream()
            .filter(res -> {
                String fieldToSearch = switch (filter) {
                    case "Title" -> res.getTitle().toLowerCase();
                    case "Author" -> (res instanceof Book b) ? b.getAuthor().toLowerCase() : "";
                    case "Performer" -> (res instanceof CD cd) ? cd.getPerformer().toLowerCase() : "";
                    case "Subject" -> (res instanceof Book b) ? b.getSubject().toLowerCase() : "";
                    case "Catalog Number" -> (res instanceof CD cd) ? cd.getCatalogNumber().toLowerCase() : "";
                    default -> "";
                };

                boolean matchesFilter = fieldToSearch.contains(keyword);

                boolean matchesAvailability = switch (availability) {
                    case "Available" -> res.isAvailable();
                    case "Checked Out" -> !res.isAvailable();
                    default -> true;
                };

                return matchesFilter && matchesAvailability;
            })
            .toList();

        resultsTable.setItems(FXCollections.observableArrayList(filtered));
    }
}
