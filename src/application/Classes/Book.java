package application.Classes;

public class Book extends Resource{
	private String ISBNNum;
	private String Author;
	private String Editor;
	private String Subject;
	
	public String getISBNNum() {
		return ISBNNum;
	}
	
	public void setISBNNum(String iSBNNum) {
		// if ISBN Num contains chat, we will not assign it to ISBNNum
		ISBNNum = iSBNNum;
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
