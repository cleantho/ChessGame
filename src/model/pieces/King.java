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
		int row = position.getRow();
		int column = position.getColumn();
		int[] r = { -1, 1, 0, 0, -1, -1, 1, 1 };
		int[] c = { 0, 0, -1, 1, -1, 1, -1, 1 };
		Position p = new Position(row, column);
		for (int i = 0; i < r.length; i++) {
			if (p.setValues(row + r[i], column + c[i]) && (getBoard().isEmpty(p) || isThereOpponentPiece(p))) {
				if (isMyKingInCheck(p)) {
					positions[p.getRow()][p.getColumn()] = false;
				} else {
					positions[p.getRow()][p.getColumn()] = true;
				}
			}
		}
		// treatment castling
		if (getMoveCount() == 0 && !board.isCheck()) {
			int[] col = { -1, 1 };
			p = new Position(row, column);
			for (int i = 0; i < col.length; i++) {
				while (p.setValues(row, p.getColumn() + col[i]) && getBoard().isEmpty(p)) {
				}
				if (p.setValues(row, p.getColumn()) && !getBoard().isEmpty(p)
						&& (getBoard().getPiece(p) instanceof Rook) && (getBoard().getPiece(p).getMoveCount() == 0)) {
					if (isMyKingInCheck(new Position(row, column + 2 * col[i]))) {
						positions[row][column + 2 * col[i]] = false;
					} else {
						positions[row][column + 2 * col[i]] = true;
					}
				}
				p.setValues(row, column);
			}
		}
		return positions;
	}

	public boolean isInCheck() {
		int row = position.getRow();
		int column = position.getColumn();
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

	public boolean isInCheckmate() {
		Piece[][] pieces = board.getPieces();
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				if (pieces[i][j] != null && pieces[i][j].getColor() == getColor()) {
					boolean[][] possible = pieces[i][j].possibleMoves();
					for (int x = 0; x < possible.length; x++) {
						for (int y = 0; y < possible.length; y++) {
							if (possible[x][y])
								return false;
						}
					}
				}
			}
		}
		return true;
	}
}
