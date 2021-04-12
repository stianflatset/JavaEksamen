package Domeneklasser;

//Hele klassen er opprettet og generert i fellesskap
import java.time.LocalTime;

public class Kundekontakt {
	int kundenr;
	String dato;
	String tekst;
	
	// konstruktør	
	public Kundekontakt(int kundenr, String dato, String tekst) {
		this.kundenr = kundenr;
		this.dato = dato;
		this.tekst = tekst;
	}
	
	// gettere og settere
	public int getKundenr() {
		return kundenr;
	}

	public void setKundenr(int kundenr) {
		this.kundenr = kundenr;
	}

	public String getDato() {
		return dato;
	}

	public void setDato(String dato) {
		this.dato = dato;
	}

	public String getTekst() {
		return tekst;
	}

	public void setTekst(String tekst) {
		this.tekst = tekst;
	}
	
	// toString
	public String toString() {
		return "Kundenr: "+kundenr+", Dato: "+dato+", Tekst: "+tekst;
	}
	
	
}
