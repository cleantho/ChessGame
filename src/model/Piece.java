package model;

public abstract class Piece {
	private Position position;
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
		if(getBoard().isEmpty(position)) {
			return false;
		}
		Piece p = getBoard().getPiece(position);
		return color != p.getColor();
	}
	
	public abstract boolean[][] possibleMoves();
	
	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves();
		for(int i = 0; i<mat.length; i++) {
			for(int j = 0; j< mat.length; j++) {
				if(mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}
