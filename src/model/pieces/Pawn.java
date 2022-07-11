package model.pieces;

import model.Board;
import model.Color;
import model.Piece;
import model.Position;

public class Pawn extends Piece {

	public Pawn(Color color, Board board) {
		super(color, board);
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] positions = new boolean[board.getRows()][board.getColumns()];
		int row = getPosition().getRow();
		int column = getPosition().getColumn();

		int left, right, up;
		if (getColor() == Color.WHITE) {
			up = -1;
			left = -1;
			right = 1;
		} else {
			up = 1;
			left = 1;
			right = -1;
		}
		Position p = new Position(0, 0);
		if (p.setValues(row + up, column + left)) {
			if (!getBoard().isEmpty(p) && getColor() != getBoard().getPiece(p).getColor()) {
				positions[row + up][column + left] = true;
			}
		}
		if (p.setValues(row + up, column + right)) {
			if (!getBoard().isEmpty(p) && getColor() != getBoard().getPiece(p).getColor()) {
				positions[row + up][column + right] = true;
			}
		}
		if (p.setValues(row + up, column)) {
			if (getBoard().isEmpty(p)) {
				positions[row + up][column] = true;
			}
		}
		Position first = new Position(0, 0);
		if (first.setValues(row + up + up, column)) {
			if (getBoard().isEmpty(p) && getBoard().isEmpty(first) && isFirstMove()) {
				positions[row + up + up][column] = true;
			}
		}

		return positions;
	}

	@Override
	public String toString() {
		return "P";
	}

}
