package model;

import model.pieces.*;

public abstract class Piece {
	protected Position position;
	private Color color;
	private int moveCount;
	protected Board board;

	public Piece(Color color, Board board) {
		this.color = color;
		this.board = board;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Color getColor() {
		return color;
	}

	public int getMoveCount() {
		return moveCount;
	}

	public void increaseMoveCount() {
		moveCount++;
	}

	public Board getBoard() {
		return board;
	}

	public boolean isThereOpponentPiece(Position position) {
		if (board.isEmpty(position)) {
			return false;
		}
		Piece p = board.getPiece(position);
		return color != p.getColor();
	}

	public abstract boolean[][] possibleMoves();

	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves();
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				if (mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isMyKingInCheck(Position target) {
		boolean result = false;
		King king = board.getMyKing(color);
		Piece[][] pieces = board.getPieces();
		
		Position aux = position;
		Piece pieceRemoved = null;
		if (!board.isEmpty(target)) {
			pieceRemoved = board.removePiece(target);
		}
		board.addPiece(board.removePiece(position), target);

		int row = king.position.getRow();
		int column = king.position.getColumn();

		// Knight
		int[] row1 = { -2, -2, 2, 2, -1, 1, -1, 1 };
		int[] col1 = { -1, 1, -1, 1, -2, -2, 2, 2 };
		Position p = new Position(row, column);
		for (int i = 0; i < row1.length; i++) {
			if (p.setValues(row + row1[i], column + col1[i]) && !board.isEmpty(p) && isThereOpponentPiece(p)) {
				if (pieces[p.getRow()][p.getColumn()] instanceof Knight) {
					result = true;
					break;
				}
			}
		}
		// Bishop, Queen, King
		int[] row2 = { -1, -1, 1, 1 };
		int[] col2 = { -1, 1, -1, 1 };
		p = new Position(row, column);
		for (int i = 0; i < row2.length; i++) {
			while (p.setValues(p.getRow() + row2[i], p.getColumn() + col2[i]) && board.isEmpty(p)) {
			}
			if (p.setValues(p.getRow(), p.getColumn()) && isThereOpponentPiece(p)
					&& (pieces[p.getRow()][p.getColumn()] instanceof Queen
							|| pieces[p.getRow()][p.getColumn()] instanceof Bishop)) {
				result = true;
				break;
			}
			p.setValues(row, column);
			if (p.setValues(row + row2[i], column + col2[i]) && !board.isEmpty(p) && isThereOpponentPiece(p)
					&& pieces[p.getRow()][p.getColumn()] instanceof King) {
				result = true;
				break;
			}
			p.setValues(row, column);
		}
		// Rook, Queen, King
		int[] row3 = { -1, 1, 0, 0 };
		int[] col3 = { 0, 0, -1, 1 };
		p = new Position(row, column);
		for (int i = 0; i < row3.length; i++) {
			while (p.setValues(p.getRow() + row3[i], p.getColumn() + col3[i]) && board.isEmpty(p)) {
			}
			if (p.setValues(p.getRow(), p.getColumn()) && isThereOpponentPiece(p)
					&& (pieces[p.getRow()][p.getColumn()] instanceof Queen
							|| pieces[p.getRow()][p.getColumn()] instanceof Rook)) {
				result = true;
				break;
			}
			p.setValues(row, column);
			if (p.setValues(row + row3[i], column + col3[i]) && !board.isEmpty(p) && isThereOpponentPiece(p)
					&& pieces[p.getRow()][p.getColumn()] instanceof King) {
				result = true;
				break;
			}
			p.setValues(row, column);
		}
		// Pawn
		int[] row4 = new int[2];
		int[] col4 = new int[2];
		if (king.getColor() == Color.WHITE) {
			row4[0] = -1;
			row4[1] = -1;
			col4[0] = -1;
			col4[1] = 1;
		} else {
			row4[0] = 1;
			row4[1] = 1;
			col4[0] = -1;
			col4[1] = 1;
		}
		p = new Position(row, column);
		for (int i = 0; i < row4.length; i++) {
			if (p.setValues(row + row4[i], column + col4[i]) && !board.isEmpty(p) && isThereOpponentPiece(p)
					&& pieces[p.getRow()][p.getColumn()] instanceof Pawn) {
				result = true;
				break;
			}
		}

		// undo movement
		board.addPiece(board.removePiece(target), aux);
		if (pieceRemoved != null) {
			board.addPiece(pieceRemoved, new Position(target.getRow(), target.getColumn()));
		}

		return result;
	}

}
