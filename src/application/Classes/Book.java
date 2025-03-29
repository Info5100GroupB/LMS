package application.Classes;

public class Book extends Resource {

    private String ISBNNum;
    private String author;
    private String editor;
    private String subject;

    // Full constructor with all fields
    public Book(String title, String author, String editor, String publisher, String subject, String ISBNNum) {
        this.setTitle(title);
        setAuthor(author);
        setEditor(editor);
        setSubject(subject);
        super.setPublisher(publisher);

        if (!setISBNNum(ISBNNum)) return;

        this.setStatus(true);
        GenerateResourceID(this);
    }

	// Getter and validated setter for ISBN
    public String getISBNNum() {
        return ISBNNum;
    }

    public Boolean setISBNNum(String isbnNum) {
        if (isbnNum == null || isbnNum.length() != 13) {
            this.ShowAlert("ISBN number must be exactly 13 digits long.");
            return false;
        }

        if (!isbnNum.matches("\\d{13}")) {
            this.ShowAlert("ISBN number must contain only digits.");
            return false;
        }

        this.ISBNNum = isbnNum;
        return true;
    }

    // Other field getters and setters
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
