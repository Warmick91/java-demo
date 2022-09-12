package edu.damago.java.objectoriented;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


/**
 * Demo comparing maps and instances.
 */
public class MapVsObjectDemo {
	static public enum Gender { DIVERSE, FEMALE, MALE }

	/**
	 * Application entry point.
	 * @param args the runtime arguments
	 */
	static public void main (final String[] args) {
		
		// usage of strictly typed model
		final Person ines = new Person();
		ines.setEmail("ines.bergmann@gmx.de");
		ines.setSurname("Musterfrau");
		ines.setForename("Ines");
		ines.setGender(Gender.FEMALE);
		ines.setBirthDay(LocalDate.of(1978, 7, 3));
		System.out.println(ines);
		// System.out.println(ines.toString());
		System.out.println();

		// usage of loosely typed model
		final Map<String,Object> sascha = new HashMap<>();	
		sascha.put("email", "sascha.baumeister@gmail.com");
		sascha.put("surname", "Baumeister");
		sascha.put("forename", "Sascha");
		sascha.put("gender", Gender.MALE);
		sascha.put("birthDay", LocalDate.of(1969, 10, 18));
		System.out.println(sascha);
		System.out.println();
		
		sascha.put("genus", "hippopotamus");
		sascha.remove("surname");
		System.out.println(sascha);
		System.out.println();
	}



	static protected class Person {
		private String email;
		private String surname;
		private String forename;
		private Gender gender;
		private LocalDate birthDay;

		public String getEmail () {
			return this.email;
		}

		public void setEmail (final String email) {
			this.email = email;
		}

		public String getSurname () {
			return this.surname;
		}

		public void setSurname (final String surname) {
			this.surname = surname;
		}

		public String getForename () {
			return this.forename;
		}

		public void setForename (final String forename) {
			this.forename = forename;
		}

		public Gender getGender () {
			return this.gender;
		}

		public void setGender (final Gender gender) {
			this.gender = gender;
		}

		public LocalDate getBirthDay () {
			return this.birthDay;
		}

		public void setBirthDay (final LocalDate birthDay) {
			this.birthDay = birthDay;
		}

		@Override
		public String toString () {
			return String.format("{ 'email': '%s', 'surname': '%s', 'forename': '%s', 'gender': '%s', 'birthDay': '%s'}", this.email, this.surname, this.forename, this.gender, this.birthDay);
		}
	}
}