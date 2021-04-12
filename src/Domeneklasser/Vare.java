package Domeneklasser;

//Hele klassen er opprettet og generert i fellesskap
public class Vare {
	String varenr;
	String varenavn;
	float pris;
	
	public Vare(String varenr, String varenavn, float pris) {
		this.varenr = varenr;
		this.varenavn = varenavn;
		this.pris = pris;
	}

	public String getVarenr() {
		return varenr;
	}

	public void setVarenr(String varenr) {
		this.varenr = varenr;
	}

	public String getVarenavn() {
		return varenavn;
	}

	public void setVarenavn(String varenavn) {
		this.varenavn = varenavn;
	}

	public float getPris() {
		return pris;
	}

	public void setPris(float pris) {
		this.pris = pris;
	}
	
	public String toString() {
		return "Varenr: "+varenr+", Varenavn: "+varenavn+", Pris: "+pris;
	}
}
