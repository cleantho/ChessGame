package model.pieces;

import model.Board;
import model.Color;
import model.Piece;
import model.Position;

public class Rook extends Piece {

	public Rook(Color color, Board board) {
		super(color, board);
	}

	@Override
	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] positions = new boolean[board.getRows()][board.getColumns()];
		int row = getPosition().getRow();
		int column = getPosition().getColumn();
		int[] r = { -1, 1, 0, 0};
		int[] c = { 0, 0, -1, 1};
		Position p = new Position(row, column);
		for (int i = 0; i < r.length; i++) {
			while (p.setValues(p.getRow() + r[i], p.getColumn() + c[i]) && getBoard().isEmpty(p)) {
				positions[p.getRow()][p.getColumn()] = true;
			}
			if (p.setValues(p.getRow(), p.getColumn()) && isThereOpponentPiece(p)) {
				positions[p.getRow()][p.getColumn()] = true;
			}			
			p.setValues(row, column);
		}
		return positions;
	}

}
