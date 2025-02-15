package application.Classes;

public class CD extends Resource{
	private static int quantitiesOfCD;
	private String catalogNumber;
	private String performer;
	
	public CD(String title, String performer, String catalogNumber){
		setPerformer(performer);
		setCatalogNumber(catalogNumber);
		setQuantitiesOfCD(getQuantitiesOfCD() + 1);
		this.setTitle(title);
		this.setStatus(true);
		GenerateResourceID(this);
	}
	
	public CD(String title, String catalogNumber){
		setCatalogNumber(catalogNumber);
		setQuantitiesOfCD(getQuantitiesOfCD() + 1);
		this.setTitle(title);
		this.setStatus(true);
		GenerateResourceID(this);
	}
	
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
	
	public String ToString() {
		return this.catalogNumber + this.performer;
	}

	public static int getQuantitiesOfCD() {
		return quantitiesOfCD;
	}

	public static void setQuantitiesOfCD(int quantitiesOfCD) {
		CD.quantitiesOfCD = quantitiesOfCD;
	}
}
