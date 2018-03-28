package test;

import static org.junit.Assert.*;

import org.junit.Test;

import zad2.Service;

public class ServiceTest {
	 Service service = new Service();
	 
	  @Test
	  public void getWeatherTest() {
		  assertNull(service.getWeather("FAKE_CITY"));
	  }
}
