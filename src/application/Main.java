package application;
	
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import Domeneklasser.Faktura;
import Domeneklasser.Fakturalinje;
import Domeneklasser.Firmakunde;
import Domeneklasser.Kunde;
import Domeneklasser.Kundekontakt;
import Domeneklasser.Privatkunde;
import Domeneklasser.Vare;
import Kontrollklasser.Kontroll;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TabPane.TabClosingPolicy;

public class Main extends Application {
	Kontroll kontroll = new Kontroll();
	//Oppretter globale lister som brukes senere i koden
	//Disse listene og deres bruk er opprettet av kandidat 118, 123 og 128
	private ObservableList<Fakturalinje> observablelist = FXCollections.observableArrayList();
	private ObservableList<Vare> varedata = FXCollections.observableArrayList();
	private ObservableList<Fakturalinje> handlelisten = FXCollections.observableArrayList();
	private ObservableList<Privatkunde> privatliste = FXCollections.observableArrayList();
	private ObservableList<Firmakunde> firmaliste = FXCollections.observableArrayList();
	private ObservableList<Kundekontakt> kundeliste = FXCollections.observableArrayList();
	
	public void start(Stage primaryStage) {
		try {
			//Henter all dataene fra tekstfilene og legger de i lister i begynnelsen av programmet
			lesFiler();
			hentKundelister();
			for(Privatkunde kunden: privatliste) {
			}

			//Oppretter globale Panes som brukes til de forskjellige scenene
			BorderPane vareBP = new BorderPane();
			vareBP.setPadding(new Insets(5, 5, 5, 5));
			BorderPane fakturaBP = new BorderPane();
			GridPane nyfakturaGP = new GridPane();
			GridPane lay_gridpane = new GridPane();
			
			// DESIGN FOR HOVEDMENY
			// Hovedmenyen er skrevet i fellesskap av kandidat 118 og 123
			// Bruker gridpane til hovedmenyen for plassering av knapper/tekst
			Scene hovedmeny = new Scene(lay_gridpane,400,250);
			primaryStage.setTitle("Hovedmeny");
			lay_gridpane.setHgap(10);
			lay_gridpane.setVgap(20);
			Label lblfil = new Label("Fil:");
			Button btnLagre = new Button("Lagre");
			Button btnLese = new Button("Lese");
			//Gir brukeren mulighet til å oppdatere listene uten å starte programmet på nytt igjen
			btnLese.setOnAction(e ->lesFiler());
			//Brukeren kan laste opp sine oppdaterte lister til tekst-filene
			btnLagre.setOnAction(e -> skrivFiler());
			Label lblVare = new Label("Vare: ");
			Button btnVare = new Button("Vare");
			Label lblFaktura = new Label("Faktura: ");
			Button btnFaktura = new Button("Faktura");
			Label lblKunde = new Label("Kunde: ");
			Button btnKunde = new Button("Kunde");
			Button btnKontaktMedKunde = new Button("Kontakt med kunde");
			Button btnAvslutt = new Button("Avslutt");
			Button btnTilbake = new Button("Tilbake til hovedmeny");
			btnAvslutt.setOnAction(e -> primaryStage.close());
			btnTilbake.setOnAction(e -> {primaryStage.setScene(hovedmeny); primaryStage.setTitle("Hovedmeny");});
			//Plassering i vinduet av fil funksjoner
			lay_gridpane.add(lblfil, 0, 0);
			lay_gridpane.add(btnLagre, 1, 0);
			lay_gridpane.add(btnLese, 2, 0);
			//Plassering i vinduet av programmets funksjoner
			lay_gridpane.add(lblVare, 0, 1);
			lay_gridpane.add(btnVare, 1, 1);
			lay_gridpane.add(lblFaktura, 0, 2);
			lay_gridpane.add(btnFaktura, 1, 2);
			lay_gridpane.add(lblKunde, 0, 3);
			lay_gridpane.add(btnKunde, 1, 3);
			lay_gridpane.add(btnKontaktMedKunde, 2, 3);
			lay_gridpane.add(btnAvslutt, 3, 4);
			lay_gridpane.setAlignment(Pos.CENTER);
			primaryStage.setScene(hovedmeny);
			primaryStage.show();
			
			//DESIGN FOR VARER
			//Hele varer scenen er skrevet av kandidat 118 og er kontrollert av 111 og 128
			Scene varemeny = new Scene(vareBP,580,550);
			TableView varetabell = new TableView<>();
			//VBox med 5 i padding for å registrere ny vare:
			VBox vareVBox = new VBox(5);
			Text lblLeggTil = new Text("Registrer en ny vare");
			Label lblVareVarenavn = new Label("Varenavn:");
			TextField txtVareVarenavn = new TextField();
			txtVareVarenavn.setMaxWidth(150);
			Label lblVarePris = new Label("Pris:");
			TextField txtVarePris = new TextField();
			txtVarePris.setMaxWidth(150);
			Button btnVareRegistrer = new Button("Registrer");
			Label lblVareStatus = new Label("Status:");
			TextField txtVareStatus = new TextField();
			txtVareStatus.setMaxWidth(150);
			txtVareStatus.setDisable(true);
			vareVBox.getChildren().addAll(lblLeggTil, lblVareVarenavn, txtVareVarenavn, lblVarePris, txtVarePris, btnVareRegistrer, lblVareStatus, txtVareStatus);
			//Henter data fra tekstfeltene og legger de inn i varelisten ved hjelp av en funksjon i kontroll
			btnVareRegistrer.setOnAction(e -> {
				try {
					String tempVarenavn = txtVareVarenavn.getText();
					float tempPris = Float.parseFloat(txtVarePris.getText());
					kontroll.regVare(tempVarenavn, tempPris);
					hentVarer();
					txtVareStatus.setText("Registrert");
				}catch(Exception e2) {
					txtVareStatus.setText("Kunne ikke registrere");
				}
				txtVareVarenavn.clear();
				txtVarePris.clear();
				varetabell.setItems(varedata);
			});		
			btnVare.setOnAction(e -> {primaryStage.setScene(varemeny); primaryStage.setTitle("Vare"); hentVarer();});
			//Lager kolonnene for tabellen og gir de verdier
			TableColumn varenr = new TableColumn("Varenummer:");
			varenr.setCellValueFactory(new PropertyValueFactory<Vare, String>("varenr"));
			TableColumn varenavn = new TableColumn("Varenavn:");
			varenavn.setCellValueFactory(new PropertyValueFactory<Vare, String>("varenavn"));
			TableColumn pris = new TableColumn("Pris:");
			pris.setCellValueFactory(new PropertyValueFactory<Vare, String>("pris"));
			varetabell.getColumns().addAll(varenr, varenavn, pris);
			Button btnVareTilbake = new Button("Tilbake til hovedmeny");
			btnVareTilbake.setOnAction(e -> {primaryStage.setScene(hovedmeny); primaryStage.setTitle("Hovedmeny");});
			//Setter kolonnene inn i tabellen og alle nodene inn i BorderPane for varescenen
			varetabell.setItems(varedata);
			varetabell.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			vareBP.setCenter(varetabell);
			vareBP.setRight(vareVBox);
			vareBP.setBottom(btnVareTilbake);
			BorderPane.setAlignment(btnVareTilbake, Pos.BOTTOM_RIGHT);
			
			//DESIGN FOR KUNDER
			//Skrevet i fellesskap av kandidat 118 og 123, kontrollert av 111 og 128
			BorderPane kundePane = new BorderPane();
			Scene kundemeny = new Scene(kundePane,500,400);
			btnKunde.setOnAction(e -> {primaryStage.setScene(kundemeny); primaryStage.setTitle("Kunde"); hentKundelister();});
			//Faner for privatkunde og firmakunde
			TabPane kundeTab = new TabPane();
			//Gjør det umulig for brukeren å lukke fanene
			kundeTab.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
			Tab privatKunde = new Tab("Privatkunde");
			Tab firmaKunde = new Tab("Firmakunde");
			//Lager tabeller og kolonner for privatkunde og firmakunde
			TableView privatTabell = new TableView<>();
			TableView firmaTabell = new TableView<>();
			TableColumn privatKundenavn = new TableColumn("Kundenavn:");
			privatKundenavn.setCellValueFactory(new PropertyValueFactory<Privatkunde, String>("kundenavn"));
			TableColumn firmaKundenavn = new TableColumn("Kundenavn:");
			firmaKundenavn.setCellValueFactory(new PropertyValueFactory<Firmakunde, String>("kundenavn"));
			TableColumn butikk = new TableColumn("Butikk:");
			butikk.setCellValueFactory(new PropertyValueFactory<Privatkunde, String>("butikk"));
			TableColumn kredittgrense = new TableColumn("Kredittgrense:");
			kredittgrense.setCellValueFactory(new PropertyValueFactory<Firmakunde, String>("kredittgrense"));
			TableColumn tlf = new TableColumn("Telefonnr:");
			tlf.setCellValueFactory(new PropertyValueFactory<Firmakunde, String>("telefonnummer"));
			privatTabell.getColumns().addAll(privatKundenavn, butikk);
			firmaTabell.getColumns().addAll(firmaKundenavn, kredittgrense, tlf);
			privatTabell.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			privatTabell.setMaxHeight(300);
			firmaTabell.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			firmaTabell.setMaxHeight(300);
			firmaTabell.setMaxWidth(500);
			//Setter kolonnene i tabellene
			privatTabell.setItems(privatliste);
			firmaTabell.setItems(firmaliste);
			kundeTab.getTabs().add(privatKunde);
			kundeTab.getTabs().add(firmaKunde);
			//Legger fanene inn i BorderPane for kundescenen
			kundePane.setTop(kundeTab);
			kundeTab.setMaxHeight(350);
			//VBox for registrering av privatkunde
			VBox privatVBox = new VBox();
			Label lblKundenavn = new Label("Kundenavn:");
			TextField txtKundenavn = new TextField();
			txtKundenavn.setMaxWidth(100);
			Label lblButikk = new  Label("Butikk:");
			TextField txtButikk = new TextField();
			txtButikk.setMaxWidth(100);
			Button btnNy = new Button("Legg til");
			privatVBox.getChildren().addAll(lblKundenavn,txtKundenavn,lblButikk,txtButikk,btnNy);
			//VBox for registrering av firmakunde
			VBox firmaVBox = new VBox();
			Label lblKundenavnF = new Label("Kundenavn:");
			TextField txtKundenavnF = new TextField();
			txtKundenavnF.setMaxWidth(100);
			Label lblKredittgrense = new Label("Kredittgrense: ");
			TextField txtKredittgrense = new TextField();
			txtKredittgrense.setMaxWidth(100);
			Label lbltlfnr = new Label("Telefonnr: ");
			TextField txttlfnr = new TextField();
			txttlfnr.setMaxWidth(100);
			Button btnNyF = new Button("Legg til");
			firmaVBox.getChildren().addAll(lblKundenavnF, txtKundenavnF, lblKredittgrense, txtKredittgrense, lbltlfnr, txttlfnr, btnNyF);
			//Styling av privatkunde tabben:
			GridPane privatkundeGP = new GridPane();
			privatkundeGP.setAlignment(Pos.TOP_CENTER);
			privatkundeGP.add(privatTabell, 0, 0);
			privatkundeGP.add(privatVBox,1,0);
			privatkundeGP.setVgap(10);
			privatkundeGP.setHgap(10);
			privatKunde.setContent(privatkundeGP);
			//Styling av firmakunde tabben:
			GridPane firmakundeGP = new GridPane();
			firmakundeGP.setAlignment(Pos.TOP_CENTER);
			firmakundeGP.add(firmaTabell,0,0);
			firmakundeGP.add(firmaVBox, 1, 0);
			firmakundeGP.setVgap(10);
			firmakundeGP.setHgap(10);
			firmaKunde.setContent(firmakundeGP);
			//Knapp for tilbake til hovedmeny
			Button btnKundeTilbake = new Button("Tilbake til hovedmeny");
			kundePane.setBottom(btnKundeTilbake);
			BorderPane.setAlignment(btnKundeTilbake, Pos.BOTTOM_RIGHT);
			btnKundeTilbake.setOnAction(e -> {primaryStage.setScene(hovedmeny); primaryStage.setTitle("Hovedmeny");});
			
			//DESIGN FOR KONTAKT MED KUNDE
			//Skrevet i fellesskap av 111 og 123
			BorderPane kontaktkundePane = new BorderPane();
			Scene kundekontaktmeny = new Scene(kontaktkundePane,500,400);
			btnKontaktMedKunde.setOnAction(e -> {primaryStage.setScene(kundekontaktmeny); primaryStage.setTitle("Kontakt med kunde");});
			TabPane kundekontaktTab = new TabPane();
			kundekontaktTab.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
			Tab nysamtale = new Tab("Ny samtale");
			Tab finnsamtale = new Tab("Finn samtale");
			kundekontaktTab.getTabs().add(nysamtale);
			kundekontaktTab.getTabs().add(finnsamtale);
			kontaktkundePane.setTop(kundekontaktTab);
			kundeTab.setMaxHeight(350);
			//Design for tabben nysamtale
			GridPane kundekontaktGP = new GridPane();
			Label lblkundekontakt = new Label("Lagre oppsummering av samtalen med kunden");
			Label lblkundenr = new Label("Kundenummer: ");
			TextField txtkundenr = new TextField();
			txtkundenr.setMaxWidth(100);
			Label lbldato = new Label("Dato: ");
			DatePicker dato = new DatePicker();
			Label lblsamtale = new Label("Oppsummering av samtalen: ");
			TextField txtsamtale = new TextField();
			txtsamtale.setMaxHeight(200);
			txtsamtale.setMaxWidth(300);
			Button btnkundekontakt = new Button("Lagre");
			kundekontaktGP.setVgap(10);
			kundekontaktGP.setHgap(15);
			kundekontaktGP.add(lblkundekontakt, 0, 0);
			GridPane.setColumnIndex(lblkundekontakt, 1);
			kundekontaktGP.add(lblkundenr, 0, 1);
			kundekontaktGP.add(txtkundenr, 1, 1);
			kundekontaktGP.add(lbldato, 0, 2);
			kundekontaktGP.add(dato, 1, 2);
			kundekontaktGP.add(lblsamtale, 0, 3);
			kundekontaktGP.add(txtsamtale, 1, 3);
			kundekontaktGP.add(btnkundekontakt, 1, 4);
			nysamtale.setContent(kundekontaktGP);
			//Design for tabben finnsamtale
			GridPane finnsamtaleGP = new GridPane();
			Label lblfinnsamtale = new Label("Skriv inn kundenr for å finne tidligere samtale ");
			Label lblkunde = new Label("Kundenummer: ");
			TextField txtkunde = new TextField();
			txtkunde.setMaxWidth(150);
			Button btnkunde = new Button("Søk");
			TableView samtaleTabell = new TableView<>();
			TableColumn date = new TableColumn("Dato");
			date.setCellValueFactory(new PropertyValueFactory<Privatkunde, String>("dato"));
			TableColumn tekst = new TableColumn("Samtale");
			tekst.setCellValueFactory(new PropertyValueFactory<Privatkunde, String>("tekst"));
			samtaleTabell.getColumns().addAll(date,tekst);
			samtaleTabell.setMaxWidth(400);
			samtaleTabell.setMaxHeight(100);
			dato.prefWidthProperty().bind(samtaleTabell.widthProperty().multiply(0.4));
			tekst.prefWidthProperty().bind(samtaleTabell.widthProperty().multiply(0.8));
			TextArea txttekst = new TextArea();
			txttekst.setMaxHeight(120);
			txttekst.setDisable(true);
			finnsamtaleGP.setVgap(10);
			finnsamtaleGP.setHgap(10);
			finnsamtaleGP.add(lblfinnsamtale, 0, 0,2,1);
			finnsamtaleGP.add(lblkunde, 0, 1);
			finnsamtaleGP.add(txtkunde, 1, 1);
			finnsamtaleGP.add(btnkunde, 2, 1);
			finnsamtaleGP.add(samtaleTabell, 0, 2,3,1);
			finnsamtaleGP.add(txttekst, 0, 3,3,1);
			finnsamtale.setContent(finnsamtaleGP);
			
			Button btnkundekontaktTilbake = new Button("Tilbake til hovedmeny");
			kontaktkundePane.setBottom(btnkundekontaktTilbake);
			BorderPane.setAlignment(btnkundekontaktTilbake, Pos.BOTTOM_RIGHT);
			btnkundekontaktTilbake.setOnAction(e -> {primaryStage.setScene(hovedmeny); primaryStage.setTitle("Hovedmeny");});
			
			
			//DESIGN FOR FAKTURA
			//Skrevet i felleskap av kandidat 111 og 123, kontrollert av 128
			//Design av vinduet Faktura
			Scene fakturameny = new Scene(fakturaBP,580,550);
			btnFaktura.setOnAction(e -> {primaryStage.setScene(fakturameny); primaryStage.setTitle("Faktura"); hentVarer();});
			//Bruker TabPane for å lage to tab'er for å velge mellom ny og søkfaktura
			TabPane fakturaTab = new TabPane();
			fakturaTab.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
			Tab sokFaktura = new Tab("Søk på faktura");
			Tab nyFaktura = new Tab("Ny faktura");
			fakturaTab.getTabs().add(sokFaktura);
			fakturaTab.getTabs().add(nyFaktura);
			fakturaBP.setTop(fakturaTab);
			
			//Styling av søk på faktura tabben, lager først tabellen for å vise en faktura
			GridPane sokfakturaGP = new GridPane();
			Label lblsokfaktura = new Label("Søk på ordrenr/fakturanr for å finne fakturaen");
			TableView fakturaTabell = new TableView();
			TableColumn fakturaVarenr = new TableColumn("Varenr:");
			fakturaVarenr.setCellValueFactory(new PropertyValueFactory<Vare, String>("varenr"));
			TableColumn fakturaVarenavn = new TableColumn("Varenavn:");
			fakturaVarenavn.setCellValueFactory(new PropertyValueFactory<Vare, String>("varenavn"));
			TableColumn antall = new TableColumn("Antall:");
			antall.setCellValueFactory(new PropertyValueFactory<Vare, String>("antall"));
			TableColumn rabatt = new TableColumn("Rabatt:");
			sokFaktura.setContent(fakturaTabell);
			rabatt.setCellValueFactory(new PropertyValueFactory<Vare, String>("rabatt"));
			TableColumn fakturaPris = new TableColumn("Pris:");
			fakturaPris.setCellValueFactory(new PropertyValueFactory<Vare, String>("totalPris"));
			fakturaTabell.getColumns().addAll(fakturaVarenr, fakturaVarenavn, antall, rabatt, fakturaPris);
			FlowPane FPsokFaktura = new FlowPane();
			Label lblOrdre = new Label("Søk på ordrenr:");
			TextField txtOrdre = new TextField();
			Button btnOrdre = new Button("Søk");
			btnOrdre.setOnAction(e -> hentFakturalinje(txtOrdre.getText()));
			FPsokFaktura.getChildren().addAll(lblOrdre, txtOrdre, btnOrdre);
			fakturaTabell.setItems(observablelist);
			sokfakturaGP.setAlignment(Pos.CENTER);
			sokfakturaGP.add(lblsokfaktura, 0, 0);
			sokfakturaGP.add(fakturaTabell, 0, 1);
			sokfakturaGP.add(FPsokFaktura,0,2);
			sokfakturaGP.setVgap(10);
			sokfakturaGP.setHgap(10);
			sokFaktura.setContent(sokfakturaGP);
			
			//Styling av ny faktura tabben:
			Label nyfakturaOverskrift = new Label("Velg en vare for å legge den til i fakturaen");
			TableView nyfakturaTabell = new TableView();
			TableColumn varenavnNyfaktura = new TableColumn("Varenavn: ");
			varenavnNyfaktura.setCellValueFactory(new PropertyValueFactory<Vare, String>("varenavn"));
			TableColumn vareprisNyfaktura = new TableColumn("Pris:");
			vareprisNyfaktura.setCellValueFactory(new PropertyValueFactory<Vare, String>("pris"));
			nyfakturaTabell.setMaxHeight(200);
			nyfakturaTabell.setMaxWidth(300);
			nyfakturaTabell.getColumns().addAll(varenavnNyfaktura, vareprisNyfaktura);
			nyfakturaTabell.setItems(varedata);
			nyfakturaTabell.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			//Styling av valg av antall varer i ny faktura tabben
			
			//Skrevet i felleskap av kandidat 118 og 123
			VBox infopanel = new VBox();
			Label lblVarenavn = new Label("Varenavn:");
			TextField txtVarenavn = new TextField();
			txtVarenavn.setMaxWidth(150);
			txtVarenavn.setDisable(true);
			Label lblAntall = new Label("Antall:");
			TextField txtAntall = new TextField("1");
			txtAntall.setMaxWidth(150);
			Label lblRabatt = new Label("Rabatt:");
			TextField txtRabatt = new TextField("0");
			txtRabatt.setMaxWidth(150);
			Button btnLeggtil = new Button("Legg til");
			infopanel.getChildren().addAll(lblVarenavn, txtVarenavn, lblAntall, txtAntall, lblRabatt, txtRabatt, btnLeggtil);
			//Styling av slett knapp som fjerner varer fra fakturaen
			VBox fjernpanel = new VBox();
			Label lblFjernvare = new Label("Velg varen som skal fjernes");
			Button btnFjernvare = new Button("Fjern");
			fjernpanel.getChildren().addAll(lblFjernvare, btnFjernvare);
			//Lager en liste som viser hvilke varer som blir lagt til i fakturaen
			TableView nyfaktura = new TableView();
			TableColumn nyfakturaVarenr = new TableColumn("Varenr:");
			nyfakturaVarenr.setCellValueFactory(new PropertyValueFactory<Fakturalinje, String>("varenr"));
			TableColumn nyfakturaVarenavn = new TableColumn("Varenavn:");
			nyfakturaVarenavn.setCellValueFactory(new PropertyValueFactory<Fakturalinje, String>("varenavn"));
			TableColumn nyfakturaAntall = new TableColumn("Antall:");
			nyfakturaAntall.setCellValueFactory(new PropertyValueFactory<Fakturalinje, String>("antall"));
			TableColumn nyfakturaRabatt = new TableColumn("Rabatt:");
			nyfakturaRabatt.setCellValueFactory(new PropertyValueFactory<Fakturalinje, String>("rabatt"));
			TableColumn nyfakturaPris = new TableColumn("Totalpris:");
			nyfakturaPris.setCellValueFactory(new PropertyValueFactory<Fakturalinje, String>("totalPris"));
			nyfaktura.getColumns().addAll(nyfakturaVarenr, nyfakturaVarenavn, nyfakturaAntall, nyfakturaRabatt, nyfakturaPris);
			nyfaktura.setMaxHeight(200);
			nyfaktura.setMaxWidth(400);
			nyfaktura.setItems(handlelisten);
			//Inntastingsfelt og knapp for å registrere faktura på kunde
			nyfaktura.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			
			//Skrevet av kandidat 111 og kontrollert av 123
			//Inntastingsfelt og knapp for å registrere faktura på kunde
			FlowPane kjopPanel = new FlowPane();
			Label lblKundenr = new Label("Kundenr:");
			TextField txtKundenr = new TextField();
			txtKundenr.setMaxWidth(150);
			Button btnKjop = new Button("Opprett Faktura");
			kjopPanel.getChildren().addAll(lblKundenr, txtKundenr, btnKjop);
			//Felt som viser status om fakturaen ble registrert
			FlowPane statusPanel = new FlowPane();
			Label lblStatus = new Label("Status:");
			TextField txtStatus = new TextField();
			txtStatus.setMaxWidth(150);
			txtStatus.setDisable(true);
			statusPanel.getChildren().addAll(lblStatus, txtStatus);
			// Eaction
			nyfakturaTabell.setOnMouseClicked(e->{
				Vare vare = (Vare) nyfakturaTabell.getSelectionModel().getSelectedItem();
				txtVarenavn.setText(vare.getVarenavn());
			});
			
			//Skrevet av kandidat 128 og kontrollert av 118
			btnLeggtil.setOnAction(e ->{
				handlelisten.clear();
				Vare vare = (Vare) nyfakturaTabell.getSelectionModel().getSelectedItem();
				kontroll.regHandleliste(vare, Integer.parseInt(txtAntall.getText()), Integer.parseInt(txtRabatt.getText()));
				handlelisten.addAll(kontroll.getHandlelisten());
				nyfaktura.setItems(handlelisten);
				txtAntall.setText("1");
				txtRabatt.setText("0");
			});
			
			btnKjop.setOnAction(e->{
				// Finner kunden
				ArrayList<Kunde> listen =kontroll.finnKunder();
				
				Kunde kode = null;
				int statusnr = 0;
				// finner kunden som e valgt
				for(Kunde kunden: listen) {
					String nr = kunden.getKundenr();
					String innnr = txtKundenr.getText();
					if(nr.equals(innnr)) {
						kode = kunden;
					}
				}
				String svar = kontroll.sjekkKunden(kode);
				
				if(svar.equals("Privat") && kode !=null) {
					// ikke lov med rabatt
					int rabatten =0;
					for(Fakturalinje varen: handlelisten) {
						rabatten += Integer.parseInt(varen.getRabatt());
					}
					// hvis rabatte er mer en 0 gi respons
					if(rabatten>0) {
						txtStatus.setText("Kan ikke opprette faktura");
					} else {
						statusnr =kontroll.lagFaktura(Integer.parseInt(txtKundenr.getText()));
						txtStatus.setText("Fakturanr: "+statusnr+" opprettet");
						handlelisten.clear();
						nyfaktura.setItems(handlelisten);
					}
				} else if(kode == null) {
					txtStatus.setText("Kunde ikke funnet");
				}
				else {
					kontroll.lagFaktura(Integer.parseInt(txtKundenr.getText()));
					txtStatus.setText("Fakturanr: "+statusnr+" opprettet");
					handlelisten.clear();
					nyfaktura.setItems(handlelisten);
				}		
			});
			//Fjerner en vare fra fakturaen
			btnFjernvare.setOnAction(e->{
				Fakturalinje vare =(Fakturalinje) nyfaktura.getSelectionModel().getSelectedItem();
				handlelisten.remove(vare);
				// Sletter fra handlelisten i kontroll
				kontroll.slettHandleliste(vare);
				nyfaktura.setItems(handlelisten);	
			});
			
			//Skrevet i fellesskap av kandidat 118 og 128
			btnNyF.setOnAction(e->{
				String navn = txtKundenavnF.getText();
				int kreditt = Integer.parseInt(txtKredittgrense.getText());
				int tlfen = Integer.parseInt(txttlfnr.getText());
				kontroll.nyFkunde(navn,kreditt,tlfen);
				hentKundelister();
				firmaTabell.setItems(firmaliste);
				txtKundenavnF.clear();
				txtKredittgrense.clear();
				txttlfnr.clear();
			});
			
			btnNy.setOnAction(e->{
				String navn = txtKundenavn.getText();
				String butikken = txtButikk.getText();
				kontroll.nyPkunde(navn,butikken);
				hentKundelister();
				privatTabell.setItems(privatliste);
				txtKundenavn.clear();
				txtButikk.clear();
			});
			
			nyfakturaGP.setAlignment(Pos.CENTER);
			nyfakturaGP.add(nyfakturaOverskrift, 0, 0);
			nyfakturaGP.add(nyfakturaTabell,0,1);
			nyfakturaGP.add(infopanel,1,1);
			nyfakturaGP.add(nyfaktura,0,2);
			nyfakturaGP.add(fjernpanel, 1, 2);
			nyfakturaGP.add(kjopPanel,0,3);
			nyfakturaGP.add(statusPanel, 0, 4);
			nyfakturaGP.setVgap(5);
			nyfakturaGP.setHgap(5);
			nyFaktura.setContent(nyfakturaGP);
			
			//Knapp for tilbake til hovedmeny
			fakturaBP.setBottom(btnTilbake);
			BorderPane.setAlignment(btnTilbake, Pos.BOTTOM_RIGHT);
			btnTilbake.setOnAction(e -> {primaryStage.setScene(hovedmeny); primaryStage.setTitle("Hovedmeny");});
		
			btnkundekontakt.setOnAction(e->{
				int kundenr = Integer.parseInt(txtkundenr.getText());
				LocalDate datoenL = dato.getValue();
				String datoS = datoenL.toString();
				String samtale = txtsamtale.getText();
				
				kontroll.regKundekontakt(kundenr, datoS, samtale);
				
			});
			
			btnkunde.setOnAction(e->{
				kundeliste.clear();
				int kundenr = Integer.parseInt(txtkunde.getText());
				ArrayList<Kundekontakt> listen =kontroll.finnSamtaler(kundenr);
				
				for(Kundekontakt kontakt: listen) {
					kundeliste.add(kontakt);
				}
				samtaleTabell.setItems(kundeliste);
			});
			samtaleTabell.setOnMouseClicked(e->{
				Kundekontakt teksten = (Kundekontakt) samtaleTabell.getSelectionModel().getSelectedItem();
				txttekst.setText(teksten.getTekst());
			});		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	// Metode for å lese inn i alle listene/mapsene
	//Skrevet av kandidat 111 og kontrollert av 128
	public void lesFiler() {
		kontroll.lesFilFaktura("Faktura.txt");
		kontroll.lesFilFakturalinje("Fakturalinjeliste.txt");
		kontroll.lesFilVare("Vare.txt");
		kontroll.lesfilFkunde();
		kontroll.lesfilPkunde();
		kontroll.lesFilKundekontakt();

	}
	
	// Metode for å skrive ut til filene
	public void skrivFiler() {
		kontroll.skrivFil("Faktura.txt", kontroll.hentFakturaliste());
		kontroll.skrivFilVare("Vare.txt");
		kontroll.skrivKontaktFil();
		kontroll.skrivFkunde();
		kontroll.skrivPkundeFil();
		
		// Finner alle fakturaene
		ArrayList<Faktura> fak = kontroll.getFakturaliste();
		ArrayList<Fakturalinje> linje = new ArrayList<Fakturalinje>();
		
		for(Faktura faken: fak) {
			// liste med fakturaline
			ArrayList<Fakturalinje> midler = faken.getFakturalinjeliste();
			for(Fakturalinje feken: midler) {
				linje.add(feken);
			}
		}
		kontroll.skrivFil("Fakturalinjeliste.txt", linje);
		
	}

//Metode for å hente alle fakturaene via kontroll
//Skrevet av kandidat 111
	public void hentFakturalinje(String txtOrdre) {
		try {
		observablelist.clear();
		ArrayList<Fakturalinje> data = kontroll.finnFakturaliste(Integer.parseInt(txtOrdre));
		for (Fakturalinje fakturaen : data) {
			observablelist.add(fakturaen);
		}
	}catch(Exception e) {}
	}
	
//Metode for å hente alle varene via kontroll
//Skrevet av kandidat 118
	public void hentVarer() {
		try {
			varedata.clear();
			ArrayList<Vare> data = kontroll.hentVarer();
			for(Vare vare : data) {
				varedata.add(vare);
			}
		} catch(Exception e) {}
	}
	
	// hente listene med kundeinfo
	//Skrevet av kandidat 128
	public void hentKundelister() {
		firmaliste.clear();
		privatliste.clear();
		
		ArrayList<Privatkunde> privat = kontroll.finnPrivatKunder();
		ArrayList<Firmakunde> firma = kontroll.finnFirmaKunder();
		
		for(Privatkunde kunden: privat) {
			privatliste.add(kunden);
		}
		
		for(Firmakunde kunden: firma) {
			firmaliste.add(kunden);
		}
	}
	
}
