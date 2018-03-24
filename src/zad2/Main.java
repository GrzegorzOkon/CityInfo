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
	    // cz�� uruchamiaj�ca GUI

	}

}*/

public class Main extends Application { 
    private Scene scena; // scena
    
    private BorderPane kontenerGlowny, konternerDolny;
    private GridPane kontenerSiatki;
    private HBox kontenerPrzyciskow;
    
    private ObservableList<String> countries, polishCities, indiaCities, israelCities;
    private ComboBox<String> poleKraj�w, poleMiast, poleWalut;
    private TextArea poleWyszukiwania;
    private Button pobierzPogod�, pobierzKursWaluty, pobierzKursNBP, pobierzOpis;

    
    // =============================================================================
    
    private void prepareScene(Stage primaryStage) {    	
        kontenerGlowny = new BorderPane();
        kontenerGlowny.setPadding(new Insets(15, 15, 15, 15));  //tworzy odst�p wok� konteneru
        
        countries = FXCollections.observableArrayList(
        		"Poland",
        		"India",
        		"Israel");

        polishCities = FXCollections.observableArrayList(
                "Cracow",
                "Pozna�",
                "Warsaw");

        indiaCities = FXCollections.observableArrayList(
                "StatesGermany1",
                "StatesGermany2",
                "StatesGermany3");

        israelCities = FXCollections.observableArrayList(
                "StatesIsrael1",
                "StatesIsrael2",
                "StatesIsrael3");
        
        poleKraj�w = new ComboBox(countries);
        
        poleMiast = new ComboBox();
        poleKraj�w.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
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
        kontenerSiatki.add(poleKraj�w, 0, 1);
        kontenerSiatki.add(new Label("City: "), 1, 0);
        kontenerSiatki.add(poleMiast, 1, 1);
        kontenerSiatki.add(new Label("Currency: "), 4, 0);
        kontenerSiatki.add(poleWalut, 4, 1); 
        
        kontenerSiatki.setPadding(new Insets(0, 0, 10, 0));  // tworzy odst�p pod kontenerem
        
        kontenerGlowny.setTop(kontenerSiatki);
        
        poleWyszukiwania = new TextArea();
        kontenerGlowny.setCenter(poleWyszukiwania);
        
        konternerDolny = new BorderPane();
        konternerDolny.setPadding(new Insets(10, 0, 0, 0));  // tworzy odst�p nad kontenerem
        
        kontenerPrzyciskow = new HBox(16);
        
        pobierzPogod� = new Button("Get weather");
        pobierzKursWaluty = new Button("Get rate for");
        pobierzKursNBP = new Button("Get NBP rate");
        pobierzOpis = new Button("Get description");
        
        kontenerPrzyciskow.getChildren().add(pobierzPogod�);
        kontenerPrzyciskow.getChildren().add(pobierzKursWaluty);
        kontenerPrzyciskow.getChildren().add(pobierzKursNBP);
        kontenerPrzyciskow.getChildren().add(pobierzOpis);
        
		// Przypisanie dzia�ania do przycisku sprawdzenia w centrali
        /*SprawdzWCentrali.setOnAction((event) -> {		    
            if (opcjaNumerW�asny.isSelected() == true) {
            	kontroler.wyszukajWCentraliNrAkt(walidujDane());
            } else if (opcjaNumerSystemowy.isSelected() == true) {
            	kontroler.wyszukajWCentraliIdDok(walidujDane());
            } else {
            	kontroler.wyszukajWCentraliSymDok(walidujDane());
            }         	
		});*/
        
		// Przypisanie dzia�ania do przycisku sprawdzenia we wszystkich izbach
        /*SprawdzLokalnie.setOnAction((event) -> {		    
            if (opcjaNumerW�asny.isSelected() == true) {
            	kontroler.wyszukajLokalnieNrAkt(walidujDane());
            } else if (opcjaNumerSystemowy.isSelected() == true) {
            } else {	
            	kontroler.wyszukajLokalnieSymDok(walidujDane());
            }         	
		});*/
        
		// Przypisuje czyszczenie p�l do przycisku Wyczy��
        /*Wyczysc.setOnAction((event) -> {		    
        	poleWyszukiwania.clear();
        	poleRaportuDlaHelpDesku.clear();
        	poleRaportuDlaAdministratora.clear();
        	poleDziennikaZdarze�.clear();
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