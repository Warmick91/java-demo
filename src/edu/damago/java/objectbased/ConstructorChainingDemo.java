package edu.damago.java.objectbased;

/**
 * Demo for enum types.
 */
public class ConstructorChainingDemo {

	/**
	 * Application entry point.
	 * @param args the runtime arguments
	 */
	static public void main (final String[] args) {
		
	}


	static public class Generalization extends Object {
		public Generalization (final String message) {
			super();
			System.out.println(message);
		}
	}


	// static public class Specialization extends Generalization {
		// auto-generated public no-arg constructor generates compile error in this case!
		// public Specialization () {
		//		super();
		// }
	// }
}