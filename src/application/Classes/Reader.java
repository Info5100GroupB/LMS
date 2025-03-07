package application.Classes;

import java.util.HashMap;
import java.util.Map;

public class Reader {
	
	/* *Variable initialization */
    private String readerId;
    private String name;

    /* *Static map to store registered readers */
    private static Map<String, Reader> readers = new HashMap<>();

    /* *to create new Reader record and store it in the map. */
    public Reader(String readerId, String name, String email) {
        this.readerId = readerId;
        this.name = name;
        readers.put(readerId, this); // Store the reader in the static map
    }

    /* *getter methods */
    public String getReaderId() {
        return readerId;
    }

    public String getName() {
        return name;
    }

    /* * Retrieves a reader from the map using their reader ID. */
    public static Reader getReaderById(String readerId) {
        return readers.get(readerId);
    }
}