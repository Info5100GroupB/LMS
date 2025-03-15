package application.controllers;

import application.Classes.Book;
import application.Classes.Resource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.stream.Collectors;

public class SearchController {

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private ComboBox<String> availabilityComboBox;

    @FXML
    private TableView<Book> resultsTable;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, String> subjectColumn;

    @FXML
    private TableColumn<Book, String> availabilityColumn;

    private ObservableList<Book> bookList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize filters
        filterComboBox.getItems().addAll("Title", "Author", "Subject");
        availabilityComboBox.getItems().addAll("All", "Available", "Checked Out");
        availabilityComboBox.setValue("All");

        // Initialize table columns
        titleColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitle()));
        authorColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAuthor()));
        subjectColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getSubject()));
        availabilityColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().isAvailable() ? "Available" : "Checked Out"));

        loadBooks();
    }

    private void loadBooks() {
        // Load books from the static resources map
        for (Resource res : Resource.getAllResources().values()) {
            if (res instanceof Book) {
                bookList.add((Book) res);
            }
        }
        resultsTable.setItems(bookList);
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().toLowerCase();
        String filter = filterComboBox.getValue();
        String availability = availabilityComboBox.getValue();

        List<Book> filteredBooks = bookList.stream()
            .filter(book -> {
                boolean matchesFilter = switch (filter) {
                    case "Title" -> book.getTitle().toLowerCase().contains(keyword);
                    case "Author" -> book.getAuthor().toLowerCase().contains(keyword);
                    case "Subject" -> book.getSubject().toLowerCase().contains(keyword);
                    default -> true;
                };

                boolean matchesAvailability = switch (availability) {
                    case "Available" -> book.isAvailable();
                    case "Checked Out" -> !book.isAvailable();
                    default -> true;
                };

                return matchesFilter && matchesAvailability;
            })
            .collect(Collectors.toList());

        resultsTable.setItems(FXCollections.observableArrayList(filteredBooks));
    }
}
