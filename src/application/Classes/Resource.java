package application.Classes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Resource {

    private String resourceId;
    private String title;
    private String publisher;
    protected boolean isAvailable;

    //to store all available Resource objects in the system.
    private static Map<String, Resource> resources = new HashMap<>();
    
    public Resource() {
    }
    
    public static void GenerateResourceID(Resource resource) {
        UUID id = UUID.randomUUID();
        resource.setResourceId(id.toString());
    }

    // Getters and setters for the fields if needed
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

    // isAvailabile = true means the resource can borrow.
    public void borrow() {
        if (isAvailable) {
            isAvailable = false;
        } else {
            System.out.println("Item is already borrowed.");
        }
    }

    public void returnItem() {
        isAvailable = true;
    }
    
    public void ShowAlert(String Message) {
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Error");
    	alert.setHeaderText("Error Existed!");
    	alert.setContentText(Message);

    	alert.showAndWait();
    }
    
    //lookup method that retrieves a Resource object from the resources map based on its resourceId.
    public static Resource getResourceById(String resourceId) {
        return resources.get(resourceId);
    }
}
