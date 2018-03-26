package zad2;

import java.io.IOException;

import org.json.JSONException;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

public class Service {
	/*
	 * Zwraca informacj� o pogodzie w podanym mie�cie danego kraju w formacie JSON 
	 * 
	 * @param miasto jest nazw� miasta dla kt�rego chcemy sprawdzi� aktualn� pogod�
	 * 
	 * @return otrzymany obiekt JSON zapisany w formie tekstowej
	 */
	public String getWeather(String miasto) {
		OpenWeatherMap owm = new OpenWeatherMap(OpenWeatherMap.Units.METRIC, "e1062b0ac1617d837933b2d9d1801f80");
		
		try {
			CurrentWeather aktualnaPogoda = owm.currentWeatherByCityName(miasto);      
		        
		    return aktualnaPogoda.getRawResponse();
		} catch (IOException | JSONException e) {
		    e.printStackTrace();
		}
		    
		return null;
	}
	
	Double getRateFor(String kod_waluty) {
		return null;
	}
	
	Double getNBPRate() {
		return null;
	}
}