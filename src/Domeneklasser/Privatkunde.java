package Domeneklasser;

//Hele klassen er opprettet og generert i fellesskap
public class Privatkunde extends Kunde{
	String butikk;
	
	// Konstruktør
	public Privatkunde(int kundenummer, String kundenavn, String butikk) {
		super(kundenummer, kundenavn);
		this.butikk = butikk;
	}
	
	//gettere og settere
	public String getButikk() {
		return butikk;
	}

	public void setButikk(String butikk) {
		this.butikk = butikk;
	}
	
	// toString
	public String toString() {
		return super.toString() +", Butikk: "+butikk;
	}
	
	
	

}
