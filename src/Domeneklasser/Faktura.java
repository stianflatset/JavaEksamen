package Domeneklasser;

//Hele klassen er opprettet og generert i fellesskap
import java.util.ArrayList;

public class Faktura {
	int fakturanr;
	int kundenummer;
	String fakturadato;
	String forfallsdato;
	ArrayList<Fakturalinje> fakturalinje = new ArrayList<Fakturalinje>();
	
	// konstruktør
	public Faktura(int fakturanr, int kundenummer, String fakturadato, String forfallsdato) {
		this.fakturanr = fakturanr;
		this.kundenummer = kundenummer;
		this.fakturadato = fakturadato;
		this.forfallsdato = forfallsdato;
	}
	
	// gettere og settere
	public int getFakturanr() {
		return fakturanr;
	}

	public void setFakturanr(int fakturanr) {
		this.fakturanr = fakturanr;
	}

	public int getKundenummer() {
		return kundenummer;
	}

	public void setKundenummer(int kundenummer) {
		this.kundenummer = kundenummer;
	}

	public String getFakturadato() {
		return fakturadato;
	}

	public void setFakturadato(String fakturadato) {
		this.fakturadato = fakturadato;
	}

	public String getForfallsdato() {
		return forfallsdato;
	}

	public void setForfallsdato(String forfallsdato) {
		this.forfallsdato = forfallsdato;
	}
	
	public void regFakturalinjeliste(int fakutranret,int varenr, String varenavn, int antall, int rabatt, float totalPris) {
		fakturalinje.add (new Fakturalinje(fakutranret,varenr,varenavn,antall,rabatt,totalPris));
	}
	
	public ArrayList getFakturalinjeliste() {
		return fakturalinje;
	}
	
	// to string
	public String toString() {
		return "Fakturanr: "+fakturanr +", Kundenummer: " +kundenummer+", Faktura dato: "+fakturadato+", Forfallsdato: "+forfallsdato;
	}
	
	
}
