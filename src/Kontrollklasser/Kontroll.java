package Kontrollklasser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeMap;
import Domeneklasser.Faktura;
import Domeneklasser.Fakturalinje;
import Domeneklasser.Firmakunde;
import Domeneklasser.Kunde;
import Domeneklasser.Kundekontakt;
import Domeneklasser.Privatkunde;
import Domeneklasser.Vare;
import Hjelpeklasser.Filbehandling;

public class Kontroll {
	Filbehandling filbehandling = new Filbehandling();
	// arraylister for oppbevaring
	TreeMap<String, Vare> vareMap = new TreeMap<String, Vare>();
	HashMap<Integer, Kunde> kundeMap = new HashMap<Integer, Kunde>();
	ArrayList<Fakturalinje> handleliste = new ArrayList<Fakturalinje>();
	ArrayList<Faktura> faktura = new ArrayList<Faktura>();
	ArrayList<Vare> vare = new ArrayList<Vare>();
	ArrayList<Kundekontakt> kontaktliste = new ArrayList<Kundekontakt>();
	
	
	// metode for å lese inn fra fil
	// tar inn filnavnet og arraylisten det skal lastes inn i
	//Skrevet av kandidat 128
	public void lesFilFaktura(String filnavn){
		faktura.clear();
		try {
			BufferedReader filen = filbehandling.lagForbindelseLesing(filnavn);
			String linjen = filen.readLine();
			// Kjører så lenge det er flere linjer i filen
			while(linjen != null) {
				faktura.add(new Faktura(Integer.parseInt(linjen),Integer.parseInt(filen.readLine()), filen.readLine(), filen.readLine()));
				
				linjen = filen.readLine();
			}
			// lukker filen
			filen.close();
			
		} catch(Exception e) {
			
		}
	}
	
	// metode for å les fra fil fakturalinje
	//Skrevet av kandidat 118
	public void lesFilFakturalinje(String filnavn) {
		
		try {
			BufferedReader filen = filbehandling.lagForbindelseLesing(filnavn);
			String linjen = filen.readLine();
			// Kjører så lenge det er flere linjer i filen
			while(linjen != null) {
				// Må først finne rett faktura
				Faktura fakturaen = finnFaktura(Integer.parseInt(linjen));
				
				int fakturanr = Integer.parseInt(linjen);
				linjen = filen.readLine();
				int varenr = Integer.parseInt(linjen);
				linjen = filen.readLine();
				String varenavn = linjen;
				linjen = filen.readLine();
				int antall = Integer.parseInt(linjen);
				linjen = filen.readLine();
				int rabatt = Integer.parseInt(linjen);
				linjen = filen.readLine();
				float pris = Float.parseFloat(linjen);
				fakturaen.regFakturalinjeliste(fakturanr,varenr,varenavn,antall,rabatt,pris);
				linjen = filen.readLine();
			}
			
			// lukker filen
			filen.close();			
		} catch(Exception e) {	
		}
	}
	
	//Metode for å lese fra Firmakunde tekstfilen
	//Skrevet av kandidat 123
	public void lesfilFkunde() {
		String filnavn = "Fkunde.txt";
		try {
			BufferedReader filen = filbehandling.lagForbindelseLesing(filnavn);
			String linjen = filen.readLine();
			
			while(linjen != null) {
				int kundenr = Integer.parseInt(linjen);
				linjen = filen.readLine();
				String navn = linjen;
				linjen = filen.readLine();
				int kredittgrense = Integer.parseInt(linjen);
				linjen = filen.readLine();
				int telefonnummer = Integer.parseInt(linjen);
				linjen = filen.readLine();
							
				kundeMap.put(kundenr, new Firmakunde(kundenr, navn, kredittgrense, telefonnummer));
			}
			
		} catch(Exception e) {}
		
	}
	
	//Metode for å lese fra Privatkunde tekstfilen
	//
	public void lesfilPkunde() {
		String filnavn = "Pkunde.txt";
		try {
			BufferedReader filen = filbehandling.lagForbindelseLesing(filnavn);
			String linjen = filen.readLine();
			
			while(linjen != null) {
				int kundenr = Integer.parseInt(linjen);
				linjen = filen.readLine();
				String navn = linjen;
				linjen = filen.readLine();
				String butikk = linjen;
				linjen = filen.readLine();
							
				kundeMap.put(kundenr, new Privatkunde(kundenr, navn, butikk));
			}			
		} catch(Exception e) {}	
	}
	
	
	// metode for å lese fra fil Vare
	//Skrevet av kandidat 111
	public void lesFilVare(String filnavn) {
		vareMap.clear();
		try {
			BufferedReader filen = filbehandling.lagForbindelseLesing(filnavn);
			String linjen = filen.readLine();
			
			// Kjører så lenge det er flere linjer i filen
			while(linjen != null) {
				String varenr = linjen;
				linjen = filen.readLine();
				String navn = linjen;
				linjen = filen.readLine();
				float pris = Float.parseFloat(linjen);
				vareMap.put(new String (navn), new Vare(varenr, navn, pris));
				linjen = filen.readLine();
			}
			// lukker filen
			filen.close();
			
		} catch(Exception e) {
			
		}
	}
	
	// Metode les fil kundekontakt
	//Skrevet av kandidat 111
	public void lesFilKundekontakt() {
		String filnavn = "kontakt.txt";
		try {
		BufferedReader filen = filbehandling.lagForbindelseLesing(filnavn);
		
		String linjen = filen.readLine();
		
		while(linjen != null) {
			int kundenr = Integer.parseInt(linjen);
			linjen = filen.readLine();
			String dato = linjen;
			linjen = filen.readLine();
			String tekst = linjen;
			
			kontaktliste.add(new Kundekontakt(kundenr, dato,tekst));
			linjen = filen.readLine();
		}
		filen.close();
		}catch(Exception e) {
			
		}
	}
	
	//Skrevet i fellesskap av 111 og 128
	public void skrivFkunde() {
		String filnavn ="Fkunde.txt";
		PrintWriter filen = filbehandling.lagForbindelseSkriving(filnavn);
		ArrayList<Firmakunde> midler =finnFirmaKunder();
		
		for(Firmakunde kunden: midler) {
			filen.println(kunden.getKundenr());
			filen.println(kunden.getKundenavn());
			filen.println(kunden.getKredittgrense());
			filen.println(kunden.getTelefonnummer());
		}
		//Lukker filen
		filen.close();
	}
	
	//Skrevet i fellesskap av kandidat 111 og 128
	public void skrivPkundeFil() {
		String filnavn = "Pkunde.txt";
	    PrintWriter filen = filbehandling.lagForbindelseSkriving(filnavn);
	    ArrayList<Privatkunde> midler =finnPrivatKunder();
		for(Privatkunde kunden: midler) {
			
			filen.println(kunden.getKundenr());
			filen.println(kunden.getKundenavn());
			filen.println(kunden.getButikk());
			
		}
		//Lukker filen
		filen.close();    
	}
	
	// metode for å skrive til fil
	// tar inn filnavnet og arraylisten det skal skrives ut til
	//Skrevet i fellesskap av kandidat 118 og 123
	public void skrivFil(String filnavn, ArrayList type) {
		try {
			PrintWriter filen = filbehandling.lagForbindelseSkriving(filnavn);
			
			// leser inn hver linje til valgt liste
			for(int i=0; i< type.size();i++) {
				if(filnavn.equals("Faktura.txt")) {
					Faktura faken = (Faktura) type.get(i);
					filen.println(faken.getFakturanr());
					filen.println(faken.getKundenummer());
					filen.println(faken.getFakturadato());
					filen.println(faken.getForfallsdato());
					
				} else if(filnavn.equals("Fakturalinjeliste.txt")) {
					Fakturalinje faken = (Fakturalinje) type.get(i);
					
					filen.println(faken.getFakturanr());
					filen.println(faken.getVarenr());
					filen.println(faken.getVarenavn());
					filen.println(faken.getAntall());
					filen.println(faken.getRabatt());
					filen.println(faken.getTotalPris());
				}		
			}
			// Lukker filen
			filen.close();
			
		}catch(Exception e) {

		}
	}
	
	// metode for å skrive til fil fra treemap
	//Skrevet av kandidat 118
	public void skrivFilVare(String filnavn) {
		try {
			PrintWriter filen = filbehandling.lagForbindelseSkriving(filnavn);
			
			// leser inn hver linje til valgt liste
			for(String key : vareMap.keySet()) {
				Vare varen = vareMap.get(key);
				
				filen.println(varen.getVarenr());
				filen.println(varen.getVarenavn());
				filen.println(varen.getPris());
			}
			// Lukker filen
			filen.close();
			
		}catch(Exception e) {}
	}	

	//Metode for å registrere en ny firmakunde
	//Skrevet av kandidat 128
	public void nyFkunde(String kundenavn,int kredittgrense, int telefonnr) {
		String filnavn="Fkunde.txt";
		try {
		    int kundenr=1;
		    /*Tar størrelse på map og øker teller med 1 som blir
		      neste kundenummer*/ 
		    for (int i = 0; i < kundeMap.size();i++) { kundenr++; }  
			kundeMap.put(kundenr, new Firmakunde(kundenr, kundenavn, kredittgrense, telefonnr));

		}catch(Exception e) {}			
	}			
	
	//Skrevet av kandidat 128
	public void nyPkunde(String navn, String butikk) {
		String filnavn = "Pkunde.txt";
		try {
		    int kundenr=1;
		    /*Tar størrelse på map og øker teller med 1 som blir
		      neste kundenummer*/ 
		    for (int i = 0; i < kundeMap.size();i++) { kundenr++; }	
			    kundeMap.put(kundenr, new Privatkunde(kundenr, navn, butikk));	    
		}catch(Exception e) {}			
		
		}	
	
	// Skriver ut til kundekontakt fil
	//Skrevet av kandidat 123
	public void skrivKontaktFil() {
		String filnavn = "kontakt.txt";
		
		PrintWriter filen = filbehandling.lagForbindelseSkriving(filnavn);
		
		for(Kundekontakt kontakten: kontaktliste) {
			filen.println(kontakten.getKundenr());
			filen.println(kontakten.getDato());
			filen.println(kontakten.getTekst());
		}
		filen.close();
	}
	
	// Uthenting av arraylistene
	//Skrevet av kandidat 111
	public ArrayList hentFakturaliste() {
		return faktura;
	}
	
	
	public ArrayList hentVareliste() {
		return vare;
	}
	
	//Henting av fakturalinjeliste
	public Faktura finnFaktura(int fakturanr) {
		
		for(Faktura fakturaen : faktura) {
			if(fakturaen.getFakturanr() == fakturanr) {
				return fakturaen;
			}
		}
		return null;
	}
	
	//Henting av fakturalinjeliste
	public ArrayList finnFakturaliste(int fakturanr) {
		// henter rett faktura
		Faktura fakT =finnFaktura(fakturanr);
		// Henter listen med fakturalinjer
		ArrayList<Fakturalinje> linjeTemp = fakT.getFakturalinjeliste();
		
		ArrayList<Fakturalinje> linjen = new ArrayList<Fakturalinje>();
		
		for(Fakturalinje fakturaen : linjeTemp) {
				 linjen.add(fakturaen);
		}
		return linjen;
	}
	
	//Henting av vareliste
	//Skrevet av kandidat 118
	public ArrayList hentVarer() {
		ArrayList<Vare> listen = new ArrayList<Vare>();
		Vare varen;
		for (String nokkel : vareMap.keySet()) {
			varen = (Vare) vareMap.get(nokkel.toString());
			listen.add(varen);
		}
		return listen;
	}
	
	
	// laging av faktura/ fakturalinjer
	//Skrevet av kandidat 128
	public int lagFaktura(int kundenr) {
		
		// Finner fakturanr
		// Sjekker om det er innhold i listen
		int nestenr;
		if(faktura.size() ==0) {
			nestenr =1;
		} else {
			Faktura faknr = faktura.get(faktura.size()-1);
			nestenr = faknr.getFakturanr()+1;
		}	
		
		// Lager en faktura
		// Finne datoen nå
		LocalDate fakturadato = LocalDate.now();
		// finner forfallsdatoen
		LocalDate forfallsdato = fakturadato.plusDays(14);
		// Formatterer datoene
		DateTimeFormatter formattet = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String fakturadatoF = fakturadato.format(formattet);
		String forfallsdatoF = forfallsdato.format(formattet);
		// Lager objektet
		Faktura fakturaenT = new Faktura(nestenr, kundenr,fakturadatoF,forfallsdatoF);
		// Legger objektet til i listen
		faktura.add(fakturaenT);
		
		// legger faktura listen til i fakturaen
		for(Fakturalinje varen: handleliste) {
			// Parser dataen
			int fakturanr = Integer.parseInt(varen.getFakturanr());
			int rabattInt = Integer.parseInt(varen.getRabatt());
			float prisFloat = Float.parseFloat(varen.getTotalPris());
			int antallInt = Integer.parseInt(varen.getAntall());
			int varenrInt = Integer.parseInt(varen.getVarenr());
			fakturaenT.regFakturalinjeliste(nestenr,varenrInt,varen.getVarenavn(), antallInt, rabattInt, antallInt*prisFloat-rabattInt);
		}
		handleliste.clear();
		return nestenr;
	} 
	
	//Skrevet av kandidat 123
	public void regHandleliste(Vare varen, int antall, int rabatt) {
		int varenr = Integer.parseInt(varen.getVarenr());
		
		handleliste.add(new Fakturalinje(varenr, varen.getVarenavn(), antall, rabatt, varen.getPris()*antall-rabatt));
	}
	
	public ArrayList getHandlelisten() {
		return handleliste;
	}
	
	// Metode for å slette fra handlelisten i kontroll
	public void slettHandleliste(Fakturalinje varen) {
		handleliste.remove(varen);
	}

	// Metode for å skille mellom privat og firma kunde
	//Skrevet i fellesskap av kandidat 123 og 128
	public ArrayList finnPrivatKunder() {
		// Liste med de to typene
		ArrayList<Privatkunde> kundeListe = new ArrayList<Privatkunde>();
		for(int key : kundeMap.keySet()) {
			try {
				Privatkunde faken =(Privatkunde) kundeMap.get(key);
				kundeListe.add(faken);
			} catch(Exception e) {
				Firmakunde faken = (Firmakunde) kundeMap.get(key);
			}
		}
		return kundeListe;
	}
	// Metode for å skille mellom privat og firma kunde
	//Skrevet i felleskap av kandidat 118 og 123
	public ArrayList finnFirmaKunder() {
		// Liste med de to typene
		ArrayList<Firmakunde> firmaListe = new ArrayList<Firmakunde>();
		for(int key : kundeMap.keySet()) {
			try {
				Privatkunde faken =(Privatkunde) kundeMap.get(key);
			} catch(Exception e) {
				Firmakunde faken = (Firmakunde) kundeMap.get(key);
				firmaListe.add(faken);
			}
		}
		return firmaListe;
	}
	
	// legger til kundekontakt
	public void regKundekontakt(int kundenr, String dato, String tekst) {
		kontaktliste.add(new Kundekontakt(kundenr,dato, tekst));
	
	}
	
	// Finn samtaler
	//Skrevet av kandidat 111
	public ArrayList finnSamtaler(int kundenr) {
		ArrayList<Kundekontakt> midler = new ArrayList<Kundekontakt>();
		for(Kundekontakt kontakt: kontaktliste) {
			if(kontakt.getKundenr() == kundenr) {
				midler.add(kontakt);
			}
		}
		return midler;
	}
	
	//Skrevet av kandidat 118
	public ArrayList finnKunder() {
		ArrayList<Kunde> midler = new ArrayList<Kunde>();
		for(int key: kundeMap.keySet()) {
			Kunde kunden = (Kunde) kundeMap.get(key);
			midler.add(kunden);
		}
		return midler;
	}
	
	// Sjekker om kunden er privat eller firmakunde
	public String sjekkKunden(Kunde kunden) {
		try {
			Privatkunde kunde = (Privatkunde) kunden;
			return "Privat";
		}catch(Exception e){
			Firmakunde kunde = (Firmakunde) kunden;
			return "Firma";
		}
	}
	
//Legger til varene i TreeMapen
//Skrevet i fellesskap av kandidat 111 og 118
	public void regVare(String varenavn, float pris) {
		int nestenr = 0;
		String varenr = "0";
		if(vareMap.size() == 0) {
			varenr = "1";
		} else {
			int finn = vareMap.size();
			//for-løkke for å finne neste varenummer
			for(int i = 0; i < vareMap.size()+1; i++) {nestenr++;}
			varenr = Integer.toString(nestenr);
		}
		vareMap.put(varenr, new Vare(varenr, varenavn, pris));
	}
	
	// get fakturalisten
	public ArrayList getFakturaliste() {
		return faktura;
	}
}
