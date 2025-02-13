package application.Classes;

import java.util.UUID;

public class Resource {

    private String resourceId;
    private String title;
    private String publisher;

    public Resource() {
        // Constructor can be empty or initialize fields if needed
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
}
