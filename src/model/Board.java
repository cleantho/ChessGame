package model;

import model.pieces.King;

public class Board {

	private int rows;
	private int columns;
	private boolean check;
	private Piece[][] pieces;
	private King[] kings = new King[2];

	public Board() {
		rows = columns = 8;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public Piece[][] getPieces() {
		return pieces;
	}

	public Piece getPiece(Position position) {
		if (isEmpty(position)) {
			throw new BoardException("There is not a piece on position " + position + ".");
		}
		return pieces[position.getRow()][position.getColumn()];
	}

	public King getMyKing(Color color) {
		if (color == Color.WHITE) {
			return kings[0];
		} else {
			return kings[1];
		}
	}

	public King getOpponentKing(Color color) {
		if (color == Color.WHITE) {
			return kings[1];
		} else {
			return kings[0];
		}
	}

	public boolean isEmpty(Position position) {
		if (pieces[position.getRow()][position.getColumn()] == null) {
			return true;
		}
		return false;
	}

	public void addPiece(Piece piece, Position position) {
		if (!isEmpty(position))
			throw new BoardException("There is already a piece on position " + position + ".");
		piece.setPosition(position);
		pieces[position.getRow()][position.getColumn()] = piece;
		if (piece instanceof King) {
			if (piece.getColor() == Color.WHITE) {
				kings[0] = (King) piece;
			} else {
				kings[1] = (King) piece;
			}
		}
	}

	public Piece removePiece(Position position) {
		if (isEmpty(position)) {
			throw new BoardException("There is not a piece on position " + position + ".");
		}
		Piece pieceRemoved = getPiece(position);
		pieceRemoved.setPosition(null);
		pieces[position.getRow()][position.getColumn()] = null;
		return pieceRemoved;
	}

}
