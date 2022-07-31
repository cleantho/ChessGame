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
		int row = position.getRow();
		int column = position.getColumn();
		int[] r = { -1, 1, 0, 0, -1, -1, 1, 1 };
		int[] c = { 0, 0, -1, 1, -1, 1, -1, 1 };
		Position p = new Position(row, column);
		for (int i = 0; i < r.length; i++) {
			while (p.setValues(p.getRow() + r[i], p.getColumn() + c[i]) && getBoard().isEmpty(p)) {
				if (isMyKingInCheck(p)) {
					positions[p.getRow()][p.getColumn()] = false;
				} else {
					positions[p.getRow()][p.getColumn()] = true;
				}
			}
			if (p.setValues(p.getRow(), p.getColumn()) && isThereOpponentPiece(p)) {
				if (isMyKingInCheck(p)) {
					positions[p.getRow()][p.getColumn()] = false;
				} else {
					positions[p.getRow()][p.getColumn()] = true;
				}
			}			
			p.setValues(row, column);
		}
		return positions;

	}

}
