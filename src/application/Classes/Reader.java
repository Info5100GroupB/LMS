package application.Classes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        this.name = name;
        this.phoneNumber = phoneNumber;
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
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
}