package application.Classes;

public class CD extends Resource{
	private String catalogNumber;
	private String performer;
	
	public String getPerformer() {
		return performer;
	}
	
	public void setPerformer(String performer) {
		this.performer = performer;
	}
	
	public String getCatalogNumber() {
		return catalogNumber;
	}
	
	public void setCatalogNumber(String catalogNumber) {
		this.catalogNumber = catalogNumber;
	}	
}
