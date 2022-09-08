package edu.damago.java.objectoriented;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Demo for list usage.
 */
public class ListDemo {

	/**
	 * Application entry point.
	 * @param args the runtime arguments
	 */
	static public void main (final String[] args) {
		final Map<String,String> sascha = new HashMap<>();	
		sascha.put("email", "sascha.baumeister@gmail.com");
		sascha.put("surname", "Baumeister");
		sascha.put("forename", "Sascha");

		final Map<String,String> ines = new HashMap<>();	
		ines.put("email", "ines.bergmann@gmx.de");
		ines.put("surname", "Musterfrau");
		ines.put("forename", "Ines");

		final List<Map<String,String>> people = new ArrayList<>();
		people.add(ines);
		people.add(sascha);

		System.out.println("people = " + people);
		System.out.println("ines = " + people.get(0));
		System.out.println("sascha = " + people.get(1));
	}
}