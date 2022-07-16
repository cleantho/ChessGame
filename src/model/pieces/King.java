package model.pieces;

import model.Board;
import model.Color;
import model.Piece;
import model.Position;

public class King extends Piece {

	public King(Color color, Board board) {
		super(color, board);
	}

	@Override
	public String toString() {
		return "K";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] positions = new boolean[board.getRows()][board.getColumns()];
		int row = getPosition().getRow();
		int column = getPosition().getColumn();
		int left = -1, right = 1, above = -1, below = 1;

		// above
		Position p = new Position(row, column);
		if (p.setValues(row + above, column) && (getBoard().isEmpty(p) || isThereOpponentPiece(p))) {
			positions[row + above][column] = true;
		}
		// below
		if (p.setValues(row + below, column) && (getBoard().isEmpty(p) || isThereOpponentPiece(p))) {
			positions[p.getRow()][column] = true;
		}
		// left
		if (p.setValues(row, column + left) && (getBoard().isEmpty(p) || isThereOpponentPiece(p))) {
			positions[row][column + left] = true;
		}
		// right
		if (p.setValues(row, column + right) && (getBoard().isEmpty(p) || isThereOpponentPiece(p))) {
			positions[row][column + right] = true;
		}
		// north-west
		if (p.setValues(row + above, column + left) && (getBoard().isEmpty(p) || isThereOpponentPiece(p))) {
			positions[row + above][column + left] = true;
		}
		// north-east
		if (p.setValues(row + above, column + right) && (getBoard().isEmpty(p) || isThereOpponentPiece(p))) {
			positions[row + above][column + right] = true;
		}
		// south-west
		if (p.setValues(row + below, column + left) && (getBoard().isEmpty(p) || isThereOpponentPiece(p))) {
			positions[row + below][column + left] = true;
		}
		// south-east
		if (p.setValues(row + below, column + right) && (getBoard().isEmpty(p) || isThereOpponentPiece(p))) {
			positions[row + below][column + right] = true;
		}

		return positions;
	}

}
