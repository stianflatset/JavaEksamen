package Hjelpeklasser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
//Hele klassen er opprettet og skrevet i fellesskap
public class Filbehandling {
	
	// Metode for å lage en skriveforbindelse
	public static PrintWriter lagForbindelseSkriving(String filnavn) {
		try {
			
			FileWriter filForbindelse = new FileWriter(filnavn);
			BufferedWriter skriveBuffer = new BufferedWriter(filForbindelse);
			PrintWriter skriv = new PrintWriter(skriveBuffer);
			return skriv;
		}catch(Exception e){
			System.out.println("Finner ikke fil");
			return null;
		}
	}
	public static BufferedReader lagForbindelseLesing(String filnavn) {
		try {
			FileReader fil = new FileReader(filnavn); 
			BufferedReader les = new BufferedReader(fil);
			return les;
		}catch(Exception e) {
			return null;
		}
	}
}
