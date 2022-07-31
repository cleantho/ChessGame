package model.pieces;

import model.Board;
import model.Color;
import model.Piece;
import model.Position;

public class Pawn extends Piece {

	private boolean enPassant;

	public Pawn(Color color, Board board) {
		super(color, board);
	}

	public boolean isEnPassant() {
		return enPassant;
	}

	public void setEnPassant(boolean enPassant) {
		this.enPassant = enPassant;
	}

	@Override
	public String toString() {
		return "P";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] positions = new boolean[board.getRows()][board.getColumns()];
		int row = position.getRow();
		int column = position.getColumn();

		int left, right, above, enPassant;
		if (getColor() == Color.WHITE) {
			above = -1;
			left = -1;
			right = 1;
			enPassant = 3;
		} else {
			above = 1;
			left = 1;
			right = -1;
			enPassant = 4;
		}
		Position p = new Position(0, 0);
		// move one house
		if (p.setValues(row + above, column) && getBoard().isEmpty(p)) {
			if (isMyKingInCheck(p)) {
				positions[row + above][column] = false;
			} else {
				positions[row + above][column] = true;
			}
		}
		Position aux = new Position(0, 0);
		// move two house
		if (getMoveCount() == 0 && p.setValues(row + above + above, column) && aux.setValues(row + above, column)
				&& getBoard().isEmpty(aux) && getBoard().isEmpty(p)) {
			if (isMyKingInCheck(p)) {
				positions[row + above + above][column] = false;
			} else {
				positions[row + above + above][column] = true;
			}
		}
		// left capture
		if (p.setValues(row + above, column + left) && !getBoard().isEmpty(p)
				&& getColor() != getBoard().getPiece(p).getColor()) {
			if (isMyKingInCheck(p)) {
				positions[row + above][column + left] = false;
			} else {
				positions[row + above][column + left] = true;
			}
		}
		// right capture
		if (p.setValues(row + above, column + right) && !getBoard().isEmpty(p)
				&& getColor() != getBoard().getPiece(p).getColor()) {
			if (isMyKingInCheck(p)) {
				positions[row + above][column + right] = false;
			} else {
				positions[row + above][column + right] = true;
			}
		}
		if (row == enPassant) {
			if (p.setValues(row, column + left) && !getBoard().isEmpty(p)
					&& ((Pawn) getBoard().getPiece(p)).isEnPassant()) {
				if (isMyKingInCheck(p)) {
					positions[row + above][column + left] = false;
				} else {
					positions[row + above][column + left] = true;
				}
			}
			if (p.setValues(row, column + right) && !getBoard().isEmpty(p)
					&& ((Pawn) getBoard().getPiece(p)).isEnPassant()) {
				if (isMyKingInCheck(p)) {
					positions[row + above][column + right] = false;
				} else {
					positions[row + above][column + right] = true;
				}
			}
		}
		return positions;
	}

}
