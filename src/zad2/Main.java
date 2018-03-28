package zad2;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Main extends Application { 
	
    private Scene scena; 
    
    private BorderPane kontenerGlowny, konternerDolny;
    private GridPane kontenerSiatki;
    private HBox kontenerPrzyciskow;
    
    private ObservableList<String> kraje, polskieMiasta, hiszpañskieMiasta, amerykañskieMiasta;
    private ComboBox<String> poleKrajów, poleMiast, poleWalut;
    private TextArea poleWyszukiwania;
    private Button pobierzPogodê, pobierzKursWaluty, pobierzKursNBP, pobierzOpis;

    
    // =============================================================================
    
    private void prepareScene(Stage primaryStage) {    
    	
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
        
        konternerDolny.setRight(kontenerPrzyciskow);
        kontenerGlowny.setBottom(konternerDolny);
        
        scena = new Scene(kontenerGlowny, 800, 600);
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