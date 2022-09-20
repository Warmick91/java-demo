package edu.damago.java.objectoriented;

import java.util.HashMap;
import java.util.Map;


/**
 * Demo for map usage.
 * @author Sascha Baumeister
 */
public class MapDemo {

	/**
	 * Application entry point.
	 * @param args the runtime arguments
	 */
	static public void main (final String[] args) {
		// alternative to HashMap with sorted keys: TreeMap
		final Map<String,String> person = new HashMap<>();	
		person.put("email", "sascha.baumeister@gmail.com");
		person.put("surname", "Baumeister");
		person.put("forename", "Sascha");
		System.out.println("email = " + person.get("email"));
		System.out.println("forename = " + person.get("forename"));
		System.out.println("surname = " + person.get("surname"));
		System.out.println(person);
	}
}