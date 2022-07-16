package model.pieces;

import model.Board;
import model.Color;
import model.Piece;
import model.Position;

public class Knight extends Piece {

	public Knight(Color color, Board board) {
		super(color, board);
	}

	@Override
	public String toString() {
		return "N";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] positions = new boolean[board.getRows()][board.getColumns()];
		int row = getPosition().getRow();
		int column = getPosition().getColumn();
		int left = -1, right = 1, above = -1, below = 1;

		// above
		Position p = new Position(row, column);
		if (p.setValues(row + above + above, column + left) && (getBoard().isEmpty(p) || isThereOpponentPiece(p))) {
			positions[row + above + above][column + left] = true;
		}
		if (p.setValues(row + above + above, column + right) && (getBoard().isEmpty(p) || isThereOpponentPiece(p))) {
			positions[row + above + above][column + right] = true;
		}
		// below
		if (p.setValues(row + below + below, column + left) && (getBoard().isEmpty(p) || isThereOpponentPiece(p))) {
			positions[row + below + below][column + left] = true;
		}
		if (p.setValues(row + below + below, column + right) && (getBoard().isEmpty(p) || isThereOpponentPiece(p))) {
			positions[row + below + below][column + right] = true;
		}
		// left
		if (p.setValues(row + above, column + left + left) && (getBoard().isEmpty(p) || isThereOpponentPiece(p))) {
			positions[row + above][column + left + left] = true;
		}
		if (p.setValues(row + below, column + left + left) && (getBoard().isEmpty(p) || isThereOpponentPiece(p))) {
			positions[row + below][column + left + left] = true;
		}
		// right
		if (p.setValues(row + above, column + right + right) && (getBoard().isEmpty(p) || isThereOpponentPiece(p))) {
			positions[row + above][column + right + right] = true;
		}
		if (p.setValues(row + below, column + right + right) && (getBoard().isEmpty(p) || isThereOpponentPiece(p))) {
			positions[row + below][column + right + right] = true;
		}
		return positions;
	}

}
