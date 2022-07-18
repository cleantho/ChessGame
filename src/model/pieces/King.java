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
		int[] r = { -1, 1, 0, 0, -1, -1, 1, 1 };
		int[] c = { 0, 0, -1, 1, -1, 1, -1, 1 };
		Position p = new Position(row, column);
		for (int i = 0; i < r.length; i++) {
			if (p.setValues(row + r[i], column + c[i]) && (getBoard().isEmpty(p) || isThereOpponentPiece(p))) {
				positions[row + r[i]][column + c[i]] = true;
			}
		}
		return positions;
	}

	public boolean inCheck() {
		int row = getPosition().getRow();
		int column = getPosition().getColumn();
		Piece[][] pieces = board.getPieces();
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				if (pieces[i][j] != null && pieces[i][j].getColor() != getColor()) {
					boolean[][] possible = pieces[i][j].possibleMoves();
					if (possible[row][column]) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
