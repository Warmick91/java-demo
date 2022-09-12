package edu.damago.java.objectbased;

/**
 * Demo for compiletime and runtime constants.
 */
@SuppressWarnings("unused")
public class ConstantDemo {
	static private final String COMPILETIME_CONSTANT = "ABC" + "XYZ";
	static private final String RUNTIME_CONSTANT = "abc".concat("xyz");


	/**
	 * Application entry point.
	 * @param args the runtime arguments
	 */
	static public void main (final String[] args) {
		final long value = 3 * 11;		// local compiletime constant
		System.out.println("value = " + value);

		switch (args.length == 0 ? "" : args[0]) {
			case "abc":
				System.out.println("abc");
				break;
			case COMPILETIME_CONSTANT:
				System.out.println("ABCXYZ");
				break;
//			does not compile!
//			case RUNTIME_CONSTANT:
//				System.out.println("abcxyz");
//				break;
			default:
				System.out.println("other");
				break;
		}
	}
}