package edu.damago.java.objectbased;

/**
 * Enum instances representing chess pieces.
 * @author Sascha Baumeister
 */
public enum ChessPiece {
	BLACK_PAWN(true, '\u265F', 'p'),
	BLACK_BISHOP(true, '\u265D', 'b'),
	BLACK_KNIGHT(true, '\u265E', 'n'),
	BLACK_ROOK(true, '\u265C', 'r'),
	BLACK_QUEEN(true, '\u265B', 'q'),
	BLACK_KING(true, '\u265A', 'k'),
	WHITE_PAWN(false, '\u2659', 'P'),
	WHITE_BISHOP(false, '\u2657', 'B'),
	WHITE_KNIGHT(false, '\u2658', 'N'),
	WHITE_ROOK(false, '\u2656', 'R'),
	WHITE_QUEEN(false, '\u2655', 'Q'),
	WHITE_KING(false, '\u2654', 'K');

	private final boolean black;
	private final char graphicSymbol;
	private final char textSymbol;

	private ChessPiece (final boolean black, final char graphicSymbol, final char textSymbol) {
		this.black = black;
		this.graphicSymbol = graphicSymbol;
		this.textSymbol = textSymbol;
	}


	public boolean isBlack () {
		return this.black;
	}


	public boolean isWhite () {
		return !this.black;
	}


	public char graphicSymbol () {
		return this.graphicSymbol;
	}


	public char textSymbol () {
		return this.textSymbol;
	}
}