package model;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board() {
		this(64);
	}

	public Board(int houses) {
		if (houses != 64) {
			throw new BoardException("Error creating board: there must be 64.");
		}
		rows = columns = 8;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Piece[][] getPieces() {
		return pieces;
	}

	public Piece getPiece(Position position) {
		if (isEmpty(position)) {
			throw new BoardException("There is not a piece on position " + position);
		}
		return pieces[position.getRow()][position.getColumn()];
	}

	public boolean isEmpty(Position position) {
		if (pieces[position.getRow()][position.getColumn()] == null) {
			return true;
		}
		return false;
	}

	public void addPiece(Piece piece, Position position) {
		if (!isEmpty(position))
			throw new BoardException("There is already a piece on position " + position);
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.setPosition(position);
	}

	public Piece removePiece(Position position) {
		if (isEmpty(position)) {
			throw new BoardException("There is not a piece on position " + position);
		}
		Piece pieceRemoved = getPiece(position);
		pieces[position.getRow()][position.getColumn()] = null;
		return pieceRemoved;
	}

}
