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
		int[] r = { -2, -2, 2, 2, -1, 1, -1, 1 };
		int[] c = { -1, 1, -1, 1, -2, -2, 2, 2 };
		Position p = new Position(row, column);
		for (int i = 0; i < r.length; i++) {
			if (p.setValues(row + r[i], column + c[i]) && (getBoard().isEmpty(p) || isThereOpponentPiece(p))) {
				positions[row + r[i]][column + c[i]] = true;
			}
		}
		return positions;
	}

}
