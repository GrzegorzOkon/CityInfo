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

/*public class Main {

	public static void main(String[] args) {
		Service s = new Service("Poland");
	    String weatherJson = s.getWeather("Warsaw");
	    Double rate1 = s.getRateFor("USD");
	    Double rate2 = s.getNBPRate();
	    // ...
	    // czêœæ uruchamiaj¹ca GUI

	}

}*/

public class Main extends Application { 
    private Scene scena; // scena
    
    private BorderPane kontenerGlowny, konternerDolny;
    private GridPane kontenerSiatki;
    private HBox kontenerPrzyciskow;
    
    private ObservableList<String> countries, polishCities, indiaCities, israelCities;
    private ComboBox<String> poleKrajów, poleMiast, poleWalut;
    private TextArea poleWyszukiwania;
    private Button pobierzPogodê, pobierzKursWaluty, pobierzKursNBP, pobierzOpis;

    
    // =============================================================================
    
    private void prepareScene(Stage primaryStage) {    	
        kontenerGlowny = new BorderPane();
        kontenerGlowny.setPadding(new Insets(15, 15, 15, 15));  //tworzy odstêp wokó³ konteneru
        
        countries = FXCollections.observableArrayList(
        		"Poland",
        		"India",
        		"Israel");

        polishCities = FXCollections.observableArrayList(
                "Cracow",
                "Poznañ",
                "Warsaw");

        indiaCities = FXCollections.observableArrayList(
                "StatesGermany1",
                "StatesGermany2",
                "StatesGermany3");

        israelCities = FXCollections.observableArrayList(
                "StatesIsrael1",
                "StatesIsrael2",
                "StatesIsrael3");
        
        poleKrajów = new ComboBox(countries);
        
        poleMiast = new ComboBox();
        poleKrajów.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {

                switch (t1.toString()) {
                    case "India":
                    	poleMiast.setItems(indiaCities);
                        break;
                   case "Poland":
                	   poleMiast.setItems(polishCities);
                        break;
                   case "Israel":
                	   poleMiast.setItems(israelCities);
                       break;

                }
            }
        });
        
        poleWalut = new ComboBox();
        poleWalut.getItems().addAll(
                "--",
                "--",
                "--",
                "--",
                "--"  
        	);
        
        kontenerSiatki = new GridPane();
        kontenerSiatki.setVgap(4);
        kontenerSiatki.setHgap(8);
        kontenerSiatki.setPadding(new Insets(5, 5, 5, 5));
        kontenerSiatki.add(new Label("Country: "), 0, 0);
        kontenerSiatki.add(poleKrajów, 0, 1);
        kontenerSiatki.add(new Label("City: "), 1, 0);
        kontenerSiatki.add(poleMiast, 1, 1);
        kontenerSiatki.add(new Label("Currency: "), 4, 0);
        kontenerSiatki.add(poleWalut, 4, 1); 
        
        kontenerSiatki.setPadding(new Insets(0, 0, 10, 0));  // tworzy odstêp pod kontenerem
        
        kontenerGlowny.setTop(kontenerSiatki);
        
        poleWyszukiwania = new TextArea();
        kontenerGlowny.setCenter(poleWyszukiwania);
        
        konternerDolny = new BorderPane();
        konternerDolny.setPadding(new Insets(10, 0, 0, 0));  // tworzy odstêp nad kontenerem
        
        kontenerPrzyciskow = new HBox(16);
        
        pobierzPogodê = new Button("Get weather");
        pobierzKursWaluty = new Button("Get rate for");
        pobierzKursNBP = new Button("Get NBP rate");
        pobierzOpis = new Button("Get description");
        
        kontenerPrzyciskow.getChildren().add(pobierzPogodê);
        kontenerPrzyciskow.getChildren().add(pobierzKursWaluty);
        kontenerPrzyciskow.getChildren().add(pobierzKursNBP);
        kontenerPrzyciskow.getChildren().add(pobierzOpis);
        
		// Przypisanie dzia³ania do przycisku sprawdzenia w centrali
        /*SprawdzWCentrali.setOnAction((event) -> {		    
            if (opcjaNumerW³asny.isSelected() == true) {
            	kontroler.wyszukajWCentraliNrAkt(walidujDane());
            } else if (opcjaNumerSystemowy.isSelected() == true) {
            	kontroler.wyszukajWCentraliIdDok(walidujDane());
            } else {
            	kontroler.wyszukajWCentraliSymDok(walidujDane());
            }         	
		});*/
        
		// Przypisanie dzia³ania do przycisku sprawdzenia we wszystkich izbach
        /*SprawdzLokalnie.setOnAction((event) -> {		    
            if (opcjaNumerW³asny.isSelected() == true) {
            	kontroler.wyszukajLokalnieNrAkt(walidujDane());
            } else if (opcjaNumerSystemowy.isSelected() == true) {
            } else {	
            	kontroler.wyszukajLokalnieSymDok(walidujDane());
            }         	
		});*/
        
		// Przypisuje czyszczenie pól do przycisku Wyczyœæ
        /*Wyczysc.setOnAction((event) -> {		    
        	poleWyszukiwania.clear();
        	poleRaportuDlaHelpDesku.clear();
        	poleRaportuDlaAdministratora.clear();
        	poleDziennikaZdarzeñ.clear();
		});*/
        
        konternerDolny.setRight(kontenerPrzyciskow);
        kontenerGlowny.setBottom(konternerDolny);
        
        /*poleRaportuDlaAdministratora = new TextArea();
        poleRaportuDlaAdministratora.setEditable(false);*/
        
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
    
	public synchronized void displayReport() {

	}
}