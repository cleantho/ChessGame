package model.pieces;

import model.Board;
import model.Color;
import model.Piece;
import model.Position;

public class Queen extends Piece {

	public Queen(Color color, Board board) {
		super(color, board);
	}

	@Override
	public String toString() {
		return "Q";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] positions = new boolean[board.getRows()][board.getColumns()];
		int row = getPosition().getRow();
		int column = getPosition().getColumn();
		int left = -1, right = 1, above = -1, below = 1;

		// above
		Position p = new Position(row, column);
		while (p.setValues(p.getRow() + above, column) && getBoard().isEmpty(p)) {
			positions[p.getRow()][column] = true;
		}
		if (p.setValues(p.getRow(), column) && isThereOpponentPiece(p)) {
			positions[p.getRow()][column] = true;
		}
		// below
		p.setValues(row, column);
		while (p.setValues(p.getRow() + below, column) && getBoard().isEmpty(p)) {
			positions[p.getRow()][column] = true;
		}
		if (p.setValues(p.getRow(), column) && isThereOpponentPiece(p)) {
			positions[p.getRow()][column] = true;
		}
		// left
		p.setValues(row, column);
		while (p.setValues(row, p.getColumn() + left) && getBoard().isEmpty(p)) {
			positions[row][p.getColumn()] = true;
		}
		if (p.setValues(row, p.getColumn()) && isThereOpponentPiece(p)) {
			positions[row][p.getColumn()] = true;
		}
		// right
		p.setValues(row, column);
		while (p.setValues(row, p.getColumn() + right) && getBoard().isEmpty(p)) {
			positions[row][p.getColumn()] = true;
		}
		if (p.setValues(row, p.getColumn()) && isThereOpponentPiece(p)) {
			positions[row][p.getColumn()] = true;
		}
		// north-west
		p.setValues(row, column);
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
