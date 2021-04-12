package Domeneklasser;

//Hele klassen er opprettet og generert i fellesskap
public class Firmakunde extends Kunde{
	int kredittgrense;
	int telefonnummer;
	
	// konstruktør
	public Firmakunde(int kundenummer, String kundenavn, int kredittgrense, int telefonnummer) {
		super(kundenummer, kundenavn);
		this.kredittgrense = kredittgrense;
		this.telefonnummer = telefonnummer;
	}
	
	// Gettere og settere
	public int getKredittgrense() {
		return kredittgrense;
	}

	public void setKredittgrense(int kredittgrense) {
		this.kredittgrense = kredittgrense;
	}

	public int getTelefonnummer() {
		return telefonnummer;
	}

	public void setTelefonnummer(int telefonnummer) {
		this.telefonnummer = telefonnummer;
	}
	
	// toString
	public String toString() {
		return super.toString() + ", Kredittgrense: "+ kredittgrense + ", Telefonnummer: "+telefonnummer;
	}
	
	
}
