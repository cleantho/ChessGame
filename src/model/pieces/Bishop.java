package model.pieces;

import model.Board;
import model.Color;
import model.Piece;
import model.Position;

public class Bishop extends Piece {

	public Bishop(Color color, Board board) {
		super(color, board);
	}

	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] positions = new boolean[board.getRows()][board.getColumns()];
		int row = getPosition().getRow();
		int column = getPosition().getColumn();
		int left = -1, right = 1, above = -1, below = 1;

		// north-west
		Position p = new Position(row, column);
		while (p.setValues(p.getRow() + above, p.getColumn() + left) && getBoard().isEmpty(p)) {
			positions[p.getRow()][p.getColumn()] = true;
		}
		if (p.setValues(p.getRow(), p.getColumn()) && isThereOpponentPiece(p)) {
			positions[p.getRow()][p.getColumn()] = true;
		}
		// north-east
		p.setValues(row, column);
		while (p.setValues(p.getRow() + above, p.getColumn() + right) && getBoard().isEmpty(p)) {
			positions[p.getRow()][p.getColumn()] = true;
		}
		if (p.setValues(p.getRow(), p.getColumn()) && isThereOpponentPiece(p)) {
			positions[p.getRow()][p.getColumn()] = true;
		}
		// south-west
		p.setValues(row, column);
		while (p.setValues(p.getRow() + below, p.getColumn() + left) && getBoard().isEmpty(p)) {
			positions[p.getRow()][p.getColumn()] = true;
		}
		if (p.setValues(p.getRow(), p.getColumn()) && isThereOpponentPiece(p)) {
			positions[p.getRow()][p.getColumn()] = true;
		}
		// south-east
		p.setValues(row, column);
		while (p.setValues(p.getRow() + below, p.getColumn() + right) && getBoard().isEmpty(p)) {
			positions[p.getRow()][p.getColumn()] = true;
		}
		if (p.setValues(p.getRow(), p.getColumn()) && isThereOpponentPiece(p)) {
			positions[p.getRow()][p.getColumn()] = true;
		}

		return positions;
	}

}
