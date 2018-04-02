package zad2;


import java.awt.BorderLayout;

import javax.swing.JButton;

import org.json.JSONObject;

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
	
	private static Service s;
	private static String weatherJson;
	private static Double rate1;
	private static Double rate2;
	private static String wikiAddress;
	
    private Scene scena; 
    private TabPane kontenerZakladek; 
    private Tab zakladka0, zakladka1;
    private BorderPane kontenerGlowny, konternerDolny, kontenerPrzegl¹darki;
    private GridPane kontenerSiatki;
    private HBox kontenerPrzyciskow;
    
    //private ObservableList<String> kraje, polskieMiasta, hiszpañskieMiasta, amerykañskieMiasta;
    //private ComboBox<String> poleKrajów, poleMiast, poleWalut;
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
        	if (weatherJson != null) {
        		
        		wyœwietlRaport(sformatujPogodê(weatherJson));
        	}
		});
        
        pobierzKursWaluty.setOnAction((event) -> {
        	if (rate1 != null) {
        		
        		wyœwietlRaport("" + rate1);
        	}
		});
        
        pobierzKursNBP.setOnAction((event) -> {		    
        	if (rate2 != null) {
        		
        		wyœwietlRaport("" + rate2);
        	}
		});
        
        pobierzOpis.setOnAction((event) -> {		    
        	if (wikiAddress != null) {
        		
        		silnikStron.load(wikiAddress);
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
    	
        s = new Service("Poland");
        weatherJson = s.getWeather("Warsaw");
        rate1 = s.getRateFor("USD");
        rate2 = s.getNBPRate();
        wikiAddress = s.getWikiDescription("Cracow");
    	
        launch(args);
    }

    // =============================================================================
    
    public String sformatujPogodê(String tekstNiesformatowany) {
    	
    	String tekstSformatowany = "";
    	
    	try {
    	
    			JSONObject jsonObj = new JSONObject(tekstNiesformatowany);
    			
    			if (!jsonObj.isNull("main")) {
    				
    				tekstSformatowany += "Temperatura: " + jsonObj.getJSONObject("main").get("temp") + "\n";
    				tekstSformatowany += "Temperatura minimalna: " + jsonObj.getJSONObject("main").get("temp_min") + "\n";
    				tekstSformatowany += "Temperatura maksymalna: " + jsonObj.getJSONObject("main").get("temp_max") + "\n";
    				tekstSformatowany += "Wilgotnoœæ: " + jsonObj.getJSONObject("main").get("humidity") + "\n";
    				tekstSformatowany += "Ciœnienie: " + jsonObj.getJSONObject("main").get("pressure");
    			}

    	} catch (Exception ex) {}
    	
    	return tekstSformatowany;
    }
    
	public void wyœwietlRaport(String raport) {
		
		poleWyszukiwania.clear();
		poleWyszukiwania.appendText(raport);
	}
}
