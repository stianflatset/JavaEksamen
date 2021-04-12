package Domeneklasser;

public abstract class Kunde{
	int kundenummer;
	String kundenavn;
	
	// Konstruktør
	public Kunde(int kundenummer, String kundenavn) {
		this.kundenummer = kundenummer;
		this.kundenavn = kundenavn;
	}
	
	
	// Gettere og settere
	// Set kundenummer
	public void setKundenr(int kundenummer) {
		this.kundenummer = kundenummer;
	}
	
	// get kundenummer
	public String getKundenr() {
		return ""+kundenummer;
	}
	
	// set kundenavn
	public void setKundenavn(String kundenavn) {
		this.kundenavn = kundenavn;
	}
	
	// get kundenavn
	public String getKundenavn() {
		return kundenavn;
	}
	
	// to string
	public String toString() {
		return "Kundenummer: " +kundenummer +", Kundenavn: "+ kundenavn;
	}
}
