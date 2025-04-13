package application.Classes;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class Reader {
	
	/* *Variable initialization */
    private String readerId;
    private String name;
    private String phoneNumber;
    private List<Resource> borrowedBooks = new ArrayList<>();

    /* *Static map to store registered readers */
    private static Map<String, Reader> readers = new HashMap<>();

    /* *to create new Reader record and store it in the map. */
    public Reader(String readerId, String name, String phoneNumber) {
        this.readerId = readerId;
        this.setName(name);
        if (!this.setPhoneNumber(phoneNumber)) return;
        readers.put(readerId, this);
    }

    /* *getter methods */
    public String getReaderId() {
        return readerId;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    /* * Retrieves a reader from the map using their reader ID. */
    public static Reader getReaderById(String readerId) {
        return readers.get(readerId);
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public Boolean setPhoneNumber(String phoneNumber) {        
        if (!phoneNumber.matches("\\d+")) {
        	Platform.runLater(() -> {
                new Alert(Alert.AlertType.WARNING, "Phone number must contain only digits.").show();
            });
            return false;
        }
        
        this.phoneNumber = phoneNumber;
        return true;
    }

    public List<Resource> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrow(Resource resource) {
        borrowedBooks.add(resource);
        resource.borrow();
    }

    public void returnBook(Resource resource) {
        borrowedBooks.remove(resource);
        resource.returnItem();
    }
    
    public static Map<String, Reader> getAllReaders() {
        return readers;
    }
    
    public static void saveReadersToCSV(String filePath) {
    	if (filePath == null || filePath.trim().isEmpty()) {
    	     throw new IllegalArgumentException("File path cannot be null or empty");
    	}
    	 
    	try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
            // Write header
            writer.write("ReaderId,Name,PhoneNumber\n");
            
            // Write all readers from getAllReaders() map
            for (Reader reader : Reader.getAllReaders().values()) {
                writer.write(String.format("%s,%s,%s%n",
                    reader.getReaderId(),
                    reader.getName(),
                    reader.getPhoneNumber()));
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to save reader data: " + e.getMessage());
            alert.getDialogPane().setPrefWidth(400);
            alert.showAndWait();
        }
    }
    
    public static void saveReadersToCSV(Reader reader, String filePath) {
    	 if (filePath == null || filePath.trim().isEmpty()) {
    	        throw new IllegalArgumentException("File path cannot be null or empty");
    	 }
    	 
    	 try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
    	        // Write header
    	        writer.write("ReaderId,Name,PhoneNumber\n");

    	        // Write remaining readers from getAllReaders() map
    	        if (getAllReaders().isEmpty()) {
	    	        for (Reader reader1 : getAllReaders().values()) {
	    	            if (reader1 != null) {
	    	                writer.write(String.format("%s,%s,%s%n",
	    	                    reader1.getReaderId(),
	    	                    reader1.getName(),
	    	                    reader1.getPhoneNumber()));
	    	            }
	    	        }
    	        }
        } catch (IOException e) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                String message = "Failed to save readers data: " + e.getMessage();
                alert.setContentText(message);
                alert.getDialogPane().setPrefWidth(message.length() * 7);
                alert.show();
            });
        }
    }

    public static void loadReadersFromCSV(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            readers.clear(); // Clear existing data
            
            // Skip header line and process each reader
            lines.stream()
            	.skip(1)
                .forEach(line -> {
                    String[] data = line.split(",");
                    if (data.length == 3) {
                    	readers.put(data[0], new Reader(data[0], data[1], data[2]));
                    }
                });
        } catch (IOException e) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                String message = "Failed to load readers data: " + e.getMessage();
                alert.setContentText(message);
                alert.getDialogPane().setPrefWidth(message.length() * 7);
                alert.show();
            });
        }
    }
}