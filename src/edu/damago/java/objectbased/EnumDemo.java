package edu.damago.java.objectbased;

/**
 * Demo for enum types.
 */
public class EnumDemo {
	static public enum Gender { DIVERSE, FEMALE, MALE }

	/**
	 * Application entry point.
	 * @param args the runtime arguments
	 */
	static public void main (final String[] args) {
		final String textA = args[0].trim();
		final int valueA = Integer.parseInt(args[1].trim());
		System.out.println("textA = " + textA + ", valueA = " + valueA);

		final Gender gender1 = Gender.valueOf(textA);
		final Gender gender2 = Gender.values()[valueA];
		System.out.println("gender1 = " + gender1.name());
		System.out.println("gender2 = " + gender2.name());
		System.out.println("gender1 == gender2 = " + (gender1 == gender2));
		System.out.println();

		final String textB = args[2].trim();
		final int valueB = Integer.parseInt(args[3].trim());
		System.out.println("textB = " + textB + ", valueB = " + valueB);

		final ChessPiece chessPiece1 = ChessPiece.valueOf(textB);
		final ChessPiece chessPiece2 = ChessPiece.values()[valueB];
		System.out.println("chessPiece1 = " + chessPiece1.name());
		System.out.println("chessPiece2 = " + chessPiece2.name());
		System.out.println("chessPiece1 == chessPiece2 = " + (chessPiece1 == chessPiece2));
		System.out.println();

		System.out.println("is the black bishop white? " + ChessPiece.BLACK_BISHOP.isWhite());
		System.out.println("black bishop graphics symbol: " + ChessPiece.BLACK_BISHOP.graphicSymbol());
		System.out.println("black bishop text symbol: " + ChessPiece.BLACK_BISHOP.textSymbol());
	}
}