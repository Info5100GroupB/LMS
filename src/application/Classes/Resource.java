package application.Classes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Resource {

    private String resourceId;
    private String title;
    private String publisher;
    protected boolean isAvailable;

    // To store all available Resource objects in the system.
    private static Map<String, Resource> resources = new HashMap<>();

    public Resource() {
    }

    public Resource(String title, String publisher) {
        this.resourceId = UUID.randomUUID().toString();
        this.title = title;
        this.publisher = publisher;
        this.isAvailable = true;
    }

    public static void GenerateResourceID(Resource resource) {
        UUID id = UUID.randomUUID();
        resource.setResourceId(id.toString());
    }

    // Getters and setters
    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setStatus(boolean status) {
        this.isAvailable = status;
    }

    // Marks the resource as borrowed
    public void borrow() {
        if (isAvailable) {
            isAvailable = false;
        } else {
            System.out.println("Item is already borrowed.");
        }
    }

    // Marks the resource as returned
    public void returnItem() {
        isAvailable = true;
    }

    // Shows a JavaFX alert
    public void ShowAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error Existed!");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Retrieves a Resource object by ID
    public static Resource getResourceById(String resourceId) {
        return resources.get(resourceId);
    }

    // Returns all resources
    public static Map<String, Resource> getAllResources() {
        return resources;
    }

    /**
     * Loads Book-type resources from a txt file and adds them to the resource map.
     * Expects each line to be: title,publisher,author,isbn
     */
    public static void loadBooksFromTXT(String filePath) {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] values = line.split(",");
                if (values.length == 4) {
                    String title = values[0].trim();
                    String author = values[1].trim();
                    String editor = values[2].trim();
                    String publisher = values[3].trim();
                    String subject = values[4].trim();
                    String isbn = values[5].trim();

                    // Use a matching constructor (adjust as needed)
                    Book book = new Book(title, author, editor, publisher, subject, isbn);
                    resources.put(book.getResourceId(), book);
                    count++;
                }
            }
            System.out.println("Books loaded: " + count);
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
    }

    /**
     * Loads CD-type resources from a txt file and adds them to the resource map.
     * Expects each line to be: title,performer,catalogNumber,publisher
     */
    public static void loadCDsFromTXT(String filePath) {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Optional: skip header
            br.readLine();

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] values = line.split(",");
                if (values.length == 4) {
                    String title = values[0].trim();
                    String performer = values[1].trim();
                    String catalogNumber = values[2].trim();
                    String publisher = values[3].trim();

                    CD cd = new CD(title, performer, catalogNumber, publisher);
                    resources.put(cd.getResourceId(), cd);
                    count++;
                }
            }
            System.out.println("CDs loaded: " + count);
        } catch (IOException e) {
            System.out.println("Error loading CDs: " + e.getMessage());
        }
    }

    public static void addBookToTXT(Book book, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            String bookDetails = String.format("%s,%s,%s,%s,%s,%s",
                    book.getTitle(),
                    book.getAuthor(),
                    book.getEditor(),
                    book.getPublisher(),
                    book.getSubject(),
                    book.getISBNNum());
            writer.write(bookDetails);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing book to file: " + e.getMessage());
        }
    }

	public static void addCDToTXT(CD cd, String filePath) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            String bookDetails = String.format("%s,%s,%s,%s",
                    cd.getTitle(),
                    cd.getPerformer(),
                    cd.getCatalogNumber(),
                    cd.getPublisher());
            writer.write(bookDetails);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing book to file: " + e.getMessage());
        }
	}
}