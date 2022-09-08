package edu.damago.java.struct;

/**
 * Demo for math operations.
 */
public class MathDemo {

	/**
	 * Application entry point.
	 * @param args the runtime arguments
	 */
	static public void main (final String[] args) {
		System.out.println("floor(+4.9) = " + Math.floor(+4.9));
		System.out.println("floor(+4.1) = " + Math.floor(+4.1));
		System.out.println("floor(+4.0) = " + Math.floor(+4.0));
		System.out.println("floor(-4.0) = " + Math.floor(-4.0));
		System.out.println("floor(-4.1) = " + Math.floor(-4.1));
		System.out.println("floor(-4.9) = " + Math.floor(-4.9));

		System.out.println("ceil(+4.9) = " + Math.ceil(+4.9));
		System.out.println("ceil(+4.1) = " + Math.ceil(+4.1));
		System.out.println("ceil(+4.0) = " + Math.ceil(+4.0));
		System.out.println("ceil(-4.0) = " + Math.ceil(-4.0));
		System.out.println("ceil(-4.1) = " + Math.ceil(-4.1));
		System.out.println("ceil(-4.9) = " + Math.ceil(-4.9));

		System.out.println("round(+4.9) = " + Math.round(+4.9));
		System.out.println("round(+4.1) = " + Math.round(+4.1));
		System.out.println("round(+4.0) = " + Math.round(+4.0));
		System.out.println("round(-4.0) = " + Math.round(-4.0));
		System.out.println("round(-4.1) = " + Math.round(-4.1));
		System.out.println("round(-4.9) = " + Math.round(-4.9));
}

}
