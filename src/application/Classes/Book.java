package application.Classes;

public class Book extends Resource{
	private String ISBNNum;
	private String Author;
	private String Editor;
	private String Subject;
	
	public Book(String title, String author, String editor, String publisher, String subject, String ISBNNum){
		this.setTitle(title);
		setEditor(editor);
		setAuthor(author);
		setSubject(subject);
		setPublisher(publisher);
		if (!setISBNNum(ISBNNum))
			return;
		this.setStatus(true);
		GenerateResourceID(this);
	}
	
	public Book(String title, String author, String editor, String subject, String ISBNNum){
		this.setTitle(title);
		setEditor(editor);
		setAuthor(author);
		setSubject(subject);
		if (!setISBNNum(ISBNNum))
			return;
		this.setStatus(true);
		GenerateResourceID(this);
	}
	
	public Book(String title, String author, String subject, String ISBNNum){
		this.setTitle(title);
		setAuthor(author);
		setSubject(subject);
		if (!setISBNNum(ISBNNum))
			return;
		this.setStatus(true);
		GenerateResourceID(this);
	}
	
	public Book(String title, String author, String ISBNNum){
		this.setTitle(title);
		setAuthor(author);
		if (!setISBNNum(ISBNNum))
			return;
		this.setStatus(true);
		GenerateResourceID(this);
	}
	
	public String getISBNNum() {
		return ISBNNum;
	}
	
	public Boolean setISBNNum(String isbnNum) {
	    // Check if the ISBN number is exactly 13 characters long
	    if (isbnNum == null || isbnNum.length() != 13) {
	        this.ShowAlert("ISBN number must be exactly 13 digits long.");
	        return false;
	    }

	    // Check if the ISBN number contains only digits
	    if (!isbnNum.matches("\\d{13}")) {
	    	this.ShowAlert("ISBN number must contain only digits.");
	        return false;
	    }

	    // Assign the valid ISBN number
	    ISBNNum = isbnNum;
	    return true;
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
	}

	public String getEditor() {
		return Editor;
	}

	public void setEditor(String editor) {
		Editor = editor;
	}

	public String getSubject() {
		return Subject;
	}

	public void setSubject(String subject) {
		Subject = subject;
	}	
}
