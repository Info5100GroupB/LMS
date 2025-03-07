package application.Classes;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Borrowing {
	
	/* *variable initialization */
	private String borrowerId;
	private String readerId;
	private String resourceId;
	private Date dateBorrowed;
	private Date dueDate;	
	
	/* *to track borrowed resources in the library system */
	private static Map<String, Borrowing> borrowedResources = new HashMap<>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/* *to check if the resource is already borrowed.
	 * if not borrow yet, create the record and sets the borrowing date and due date (7-day loan period).
	 * and print success message */
	public static void borrowResource(String readerId, String resourceId) {
        if (borrowedResources.containsKey(resourceId)) {
            System.out.println("Resource is already borrowed.");
            return;
        }
        
        /* *Fetch resource title from the Resource class */
        Resource resource = Resource.getResourceById(resourceId);
        if (resource == null) {
            System.out.println("Resource not found.");
            return;
        }
        
        Borrowing borrowing = new Borrowing();
        borrowing.readerId = readerId;
        borrowing.resourceId = resourceId;
        borrowing.dateBorrowed = new Date();
        borrowing.dueDate = new Date(borrowing.dateBorrowed.getTime() + (7L * 24 * 60 * 60 * 1000)); // set due date to 7 days loan period
        borrowedResources.put(resourceId, borrowing);
        
        System.out.println("Resource " + resource.getTitle() + " successfully borrowed by reader " + readerId + ". Due date: " + borrowing.dueDate);
    }
    
	/* *to remove the record from borrowed list if the record already return */
	public static void returnResource(String readerId, String resourceId) {
        if (!borrowedResources.containsKey(resourceId)) {
            System.out.println("Resource is not found.");
            return;
        }
        
        borrowedResources.remove(resourceId);
        System.out.println("Resource successfully returned by reader " + readerId + ".");
    }
	
	/* *getter methods */
	public String getBorrowerId() {
		return borrowerId;
    }
	
	public String getReaderId() {
	    return readerId;
	}
	
	public String getResourceId() {
	    return resourceId;
	}
	
	public Date getDateBorrowed() {
	    return dateBorrowed;
	}
	
	public Date getDueDate() {
	    return dueDate;
	}

}
