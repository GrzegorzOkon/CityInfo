package zad2;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Service {
	
	/*
	 * Zwraca informacjê o pogodzie w podanym mieœcie danego kraju w formacie JSON 
	 * 
	 * @param miasto jest nazw¹ miasta dla którego chcemy sprawdziæ aktualn¹ pogodê
	 * 
	 * @return otrzymany obiekt JSON zapisany w formie tekstowej lub null w przypadku wyst¹pienia b³êdu IOException lub JSONException
	 */
	String getWeather(String miasto) {
		
		OpenWeatherMap owm = new OpenWeatherMap(OpenWeatherMap.Units.METRIC, "e1062b0ac1617d837933b2d9d1801f80");
		
		try {
			
			CurrentWeather aktualnaPogoda = owm.currentWeatherByCityName(miasto);      
		        
		    return aktualnaPogoda.getRawResponse();
		} catch (IOException | JSONException e) {
			
		    e.printStackTrace();
		}
		    
		return null;
	}
	
	/*
	 * Zwraca informacjê o kursie wymiany waluty kraju wobec podanej przez u¿ytkownika waluty
	 * (serwis fixer.io w darmowej wersji udostêpnia jedynie kurs euro)
	 * 
	 * @param kod_waluty jest nazw¹ waluty dla której pobierzemy kurs
	 * 
	 * @return otrzymana aktualna wartoœæ kursu wybranej waluty w stosunku do euro
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
	
	Double getNBPRate() {
		
		return null;
	}
}