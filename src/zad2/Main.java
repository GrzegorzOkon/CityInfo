package zad2;


import java.awt.BorderLayout;

import javax.swing.JButton;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


public class Main extends Application { 
	
    private Scene scena; 
    private TabPane kontenerZakladek; 
    private Tab zakladka0, zakladka1;
    private BorderPane kontenerGlowny, konternerDolny, kontenerPrzegl¹darki;
    private GridPane kontenerSiatki;
    private HBox kontenerPrzyciskow;
    private JFXPanel jfxPanel; 
    
    private ObservableList<String> kraje, polskieMiasta, hiszpañskieMiasta, amerykañskieMiasta;
    private ComboBox<String> poleKrajów, poleMiast, poleWalut;
    private TextArea poleWyszukiwania;
    private Button pobierzPogodê, pobierzKursWaluty, pobierzKursNBP, pobierzOpis;

    private WebView przegl¹darka;  
    private WebEngine silnikStron;   

    // =============================================================================
    
    private void prepareScene(Stage primaryStage) {    
    	
        kontenerZakladek = new TabPane();
        kontenerZakladek.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        zakladka0 = new Tab("Wyszukiwanie");
        zakladka1 = new Tab("Wikipedia");
        
        kontenerZakladek.getTabs().addAll(zakladka0, zakladka1);
    	
        kontenerGlowny = new BorderPane();
        kontenerGlowny.setPadding(new Insets(15, 15, 15, 15));  //tworzy odstêp wokó³ konteneru
        
        kraje = FXCollections.observableArrayList(
        		"Poland",
        		"Spain",
        		"USA");

        polskieMiasta = FXCollections.observableArrayList(
                "Cracow",
                "Poznañ",
                "Warsaw");

        hiszpañskieMiasta = FXCollections.observableArrayList(
        		"Barcelona",
                "Madrid",
                "Seville");

        amerykañskieMiasta = FXCollections.observableArrayList(
                "Chicago",
                "New York",
                "Washington");
        
        poleKrajów = new ComboBox(kraje);
        
        poleMiast = new ComboBox();
        poleKrajów.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {

                switch (t1.toString()) {
                	case "Poland":
                    	poleMiast.setItems(polskieMiasta);
                        break;
                	case "Spain":
                		poleMiast.setItems(hiszpañskieMiasta);
                        break;
                    case "USA":
                	   poleMiast.setItems(amerykañskieMiasta);
                       break;
                }
            }
        });
        
        poleWalut = new ComboBox();
        poleWalut.getItems().addAll(                
                "CHF",
                "CZK",
                "JPY",
                "PLN",
                "USD"     
        	);
        
        kontenerSiatki = new GridPane();
        kontenerSiatki.setVgap(4);
        kontenerSiatki.setHgap(8);
        kontenerSiatki.setPadding(new Insets(5, 5, 5, 5));
        kontenerSiatki.add(new Label("Kraj: "), 0, 0);
        kontenerSiatki.add(poleKrajów, 0, 1);
        kontenerSiatki.add(new Label("Miasto: "), 1, 0);
        kontenerSiatki.add(poleMiast, 1, 1);
        kontenerSiatki.add(new Label("Waluta: "), 4, 0);
        kontenerSiatki.add(poleWalut, 4, 1); 
        
        kontenerSiatki.setPadding(new Insets(0, 0, 10, 0));  // tworzy odstêp pod kontenerem
        
        kontenerGlowny.setTop(kontenerSiatki);
        
        poleWyszukiwania = new TextArea();
        kontenerGlowny.setCenter(poleWyszukiwania);
        
        konternerDolny = new BorderPane();
        konternerDolny.setPadding(new Insets(10, 0, 0, 0));  // tworzy odstêp nad kontenerem
        
        kontenerPrzyciskow = new HBox(16);
        
        pobierzPogodê = new Button("Pobierz pogodê");
        pobierzKursWaluty = new Button("Pobierz kurs waluty");
        pobierzKursNBP = new Button("Pobierz kurs NBP");
        pobierzOpis = new Button("Pobierz opis");
        
        kontenerPrzyciskow.getChildren().add(pobierzPogodê);
        kontenerPrzyciskow.getChildren().add(pobierzKursWaluty);
        kontenerPrzyciskow.getChildren().add(pobierzKursNBP);
        kontenerPrzyciskow.getChildren().add(pobierzOpis);
        
        pobierzPogodê.setOnAction((event) -> {	
        	if (poleMiast.getSelectionModel().isEmpty() == false) {
        		wyœwietlRaport(new Service().getWeather(poleMiast.getSelectionModel().getSelectedItem()));
        	}
		});
        
        pobierzKursWaluty.setOnAction((event) -> {
        	if (poleWalut.getSelectionModel().isEmpty() == false) {
        		Double kursWaluty = new Service().getRateFor(poleWalut.getSelectionModel().getSelectedItem());
        		
        		if (kursWaluty != null) {
        			wyœwietlRaport(kursWaluty.toString());
        		}
        	}
		});
        
        pobierzKursNBP.setOnAction((event) -> {		    
        	if (poleKrajów.getSelectionModel().isEmpty() == false && !poleKrajów.getSelectionModel().getSelectedItem().equals("Poland")) {
        		Double kursNBP = new Service(poleKrajów.getSelectionModel().getSelectedItem()).getNBPRate();
        		
        		if (kursNBP != null) {
        			wyœwietlRaport(kursNBP.toString());
        		}
        	}
		});
        
        pobierzOpis.setOnAction((event) -> {		    
        	if (poleMiast.getSelectionModel().isEmpty() == false) {
        		String adresStrony = new Service().getWikiDescription(poleMiast.getSelectionModel().getSelectedItem());
                
        		if (adresStrony != null) {
        			silnikStron.load(adresStrony);
        		}
        	}
		});
         
        konternerDolny.setRight(kontenerPrzyciskow);
        kontenerGlowny.setBottom(konternerDolny);
        
        zakladka0.setContent(kontenerGlowny); 
        
        kontenerPrzegl¹darki = new BorderPane();

        przegl¹darka = new WebView();
        silnikStron = przegl¹darka.getEngine();
   
        kontenerPrzegl¹darki.setCenter(przegl¹darka);
        zakladka1.setContent(kontenerPrzegl¹darki); 
        
        scena = new Scene(kontenerZakladek, 800, 600);
    }
    
    // =============================================================================
    
    @Override
    public void start(Stage primaryStage) {   
    	
        prepareScene(primaryStage);
        
        primaryStage.setTitle("City Info");
        primaryStage.setScene(scena);
        primaryStage.show();
    }

    // =============================================================================

    public static void main(String[] args) { 
    	
          launch(args);
    }

    // =============================================================================
    
	public void wyœwietlRaport(String raport) {
		
		poleWyszukiwania.clear();
		poleWyszukiwania.appendText(raport);
	}
}
