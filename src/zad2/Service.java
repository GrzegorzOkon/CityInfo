package zad2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import javafx.collections.FXCollections;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Service {
	
	String kraj = null;
	
	Map<String, String> waluty = new HashMap<String, String>() {	
		{
			put("Poland", "PLN");
			put("Spain", "EUR");
			put("USA", "USD");
		}
	};
	
	Map<String, String> miasta = new HashMap<String, String>() {	
		{
			put("Barcelona", "Barcelona");
			put("Chicago", "Chicago");
			put("Cracow", "Krak�w");
			put("Madrid", "Madryt");
			put("New York", "Nowy_Jork");
			put("Pozna�", "Pozna�");
			put("Seville", "Sewilla");
			put("Warsaw", "Warszawa");
			put("Washington", "Waszyngton");
		}
	};
	
	public Service() {}
	
	public Service(String kraj) {
		this.kraj = kraj;
	}
	
	/*
	 * Zwraca informacj� o pogodzie w podanym mie�cie danego kraju w formacie JSON 
	 * 
	 * @param miasto jest nazw� miasta dla kt�rego chcemy sprawdzi� aktualn� pogod�
	 * 
	 * @return otrzymany obiekt JSON zapisany w formie tekstowej lub null w przypadku wyst�pienia b��du IOException lub JSONException
	 */
	public String getWeather(String miasto) {
		
		OpenWeatherMap owm = new OpenWeatherMap(OpenWeatherMap.Units.METRIC, "e1062b0ac1617d837933b2d9d1801f80");
		String JSON = null;
		
		try {
			CurrentWeather aktualnaPogoda = owm.currentWeatherByCityName(miasto);         
			
			if (aktualnaPogoda.hasBaseStation()) {	//czy znalaz� pogod� dla podanej lokacji
				JSON = aktualnaPogoda.getRawResponse();
			}
		} catch (IOException | JSONException e) {
			
		    e.printStackTrace();
		}
		    
		return JSON;
	}
	
	/*
	 * Zwraca informacj� o kursie wymiany waluty kraju wobec podanej przez u�ytkownika waluty
	 * (serwis fixer.io w darmowej wersji udost�pnia jedynie kurs euro)
	 * 
	 * @param kod_waluty jest kodem waluty dla kt�rej pobierzemy kurs
	 * 
	 * @return otrzymana aktualna warto�� kursu wybranej waluty w stosunku do euro
	 */
	Double getRateFor(String kod_waluty) {
		
		String BASE_URL = "http://data.fixer.io/api/";
		String ENDPOINT = "latest";
		String ACCESS_KEY = "ff25386cdc086b897f6078b87df4c109";	

		HttpGet get = new HttpGet(BASE_URL + ENDPOINT + "?access_key=" + ACCESS_KEY + "&base=" + "EUR" + "&symbols=" + kod_waluty);
		
		Double kursWaluty = null;
		
        try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response =  httpClient.execute(get);) {

            HttpEntity entity = response.getEntity();
            JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity));

            kursWaluty =  exchangeRates.getJSONObject("rates").getDouble(kod_waluty);
        } catch (IOException | JSONException e) {
        	
            e.printStackTrace();
        } 

		return kursWaluty;
	}
	
	/*
	 * Zwraca informacj� o kursie NBP z�otego wobec waluty podanego kraju
	 * 
	 * @return otrzymana aktualna warto�� NBP z�otego wobec waluty podanego kraju
	 */
	Double getNBPRate() {
		
		String[] BASE_URL = {"http://www.nbp.pl/kursy/kursya.html", "http://www.nbp.pl/kursy/kursyb.html"};
		Double kursWaluty = null;
		
		for (int i = 0; i < 2 && kursWaluty == null; i++) {
			
			HttpGet get = new HttpGet(BASE_URL[i]);
		
			try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response =  httpClient.execute(get);) {

				HttpEntity entity = response.getEntity();
				String exchangeRates = EntityUtils.toString(entity);

				int indeksKoduWaluty = exchangeRates.indexOf(waluty.get(kraj));
				
				if (indeksKoduWaluty != -1) {
            	
					int indeksKursu = exchangeRates.indexOf(">", indeksKoduWaluty + 10) + 1;
					kursWaluty = Double.valueOf(exchangeRates.substring(indeksKursu, indeksKursu + 6).replace(',', '.'));
				} 
			} catch (IOException e) {
        	
				e.printStackTrace();
			} 
		}
		
		return kursWaluty;
	}
	
	public String getWikiDescription(String miasto) {
		
		String miastoPoPolsku = miasta.get(miasto);
		
		if (miastoPoPolsku != null) {
			return "https://pl.wikipedia.org/wiki/" + miastoPoPolsku;
		}
		
		return miastoPoPolsku;
	}
}