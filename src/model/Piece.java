package model;

public abstract class Piece {
	private Position position;
	private Color color;
	private boolean firstMove;
	protected Board board;

	public Piece(Color color, Board board) {
		this.color = color;
		this.board = board;
		firstMove = true;
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

	public boolean isFirstMove() {
		return firstMove;
	}

	public void changeFirstMove() {
		firstMove = false;
	}

	public Board getBoard() {
		return board;
	}

	public abstract boolean[][] possibleMoves();
}
