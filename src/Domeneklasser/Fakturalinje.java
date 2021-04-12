package Domeneklasser;

//Hele klassen er opprettet og generert i fellesskap
public class Fakturalinje {
	int fakturanr;
	int varenr;
	String varenavn;
	int antall;
	int rabatt;
	float totalPris;
	
	public Fakturalinje(int fakturanr, int varenr, String varenavn, int antall, int rabatt, float totalPris) {
		this.fakturanr = fakturanr;
		this.varenr = varenr;
		this.varenavn = varenavn;
		this.antall = antall;
		this.rabatt = rabatt;
		this.totalPris = totalPris;
	}
	
	// For handlevogn
	public Fakturalinje(int varenr, String varenavn, int antall, int rabatt, float totalPris) {
		this.varenr = varenr;
		this.varenavn = varenavn;
		this.antall = antall;
		this.rabatt = rabatt;
		this.totalPris = totalPris;
	}


	public Fakturalinje(int fakturanr2, String string, int i, int antall2, int rabatt2, int totalPris2) {

	}

	// Gettere og settere
	public String getFakturanr() {
		return ""+fakturanr;
	}
	
	public void setFakturanr(int fakturanr) {
		this.fakturanr = fakturanr;
	}
	
	public String getVarenr() {
		return ""+varenr;
	}

	public void setVarenr(int varenr) {
		this.varenr = varenr;
	}

	public String getVarenavn() {
		return varenavn;
	}

	public void setVarenavn(String varenavn) {
		this.varenavn = varenavn;
	}

	public String getAntall() {
		return ""+antall;
	}

	public void setAntall(int antall) {
		this.antall = antall;
	}

	public String getRabatt() {
		return ""+rabatt;
	}

	public void setRabatt(int rabatt) {
		this.rabatt = rabatt;
	}

	public String getTotalPris() {
		return ""+totalPris;
	}

	public void setTotalPris(int totalPris) {
		this.totalPris = totalPris;
	}
	
	// toString
	public String toString() {
		return "Fakturanr: "+fakturanr+", Varenr: "+varenr+", Varenavn: "+varenavn+", Antall: "+antall+", Rabatt: "+rabatt+", Total pris: "+totalPris;
	}
	
}
