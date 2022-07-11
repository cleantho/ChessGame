package model;

import java.util.ArrayList;
import java.util.List;

import model.pieces.Pawn;

public class ChessMatch {

	private int turn;
	private boolean check;
	private boolean checkMate;
	private Board board;

	private List<Piece> capturedPieces = new ArrayList<>();

	public ChessMatch() {
		board = new Board(64);
		turn = 1;
		initialSetup();
	}

	public int getTurn() {
		return turn;
	}

	public Board getBoard() {
		return board;
	}

	public List<Piece> getCapturedPieces() {
		return capturedPieces;
	}

	public boolean movePiece(String source, String target) {
		Position s = new Position(source);
		Position t = new Position(target);
		Piece p = board.getPiece(s); 
		boolean[][] possible = p.possibleMoves();
		for(int i=0; i< board.getRows(); i++) {
			for(int j=0; j< board.getColumns(); j++) {
				if(possible[i][j] && t.getRow() == i && t.getColumn() == j) {
					if(!board.isEmpty(t)) {
						capturedPieces.add(board.removePiece(t));
					}
					board.addPiece(board.removePiece(s), t);
					turn++;
					return true;
				}
			}
		}
		return false;
		
	}

	private void initialSetup() {
		board.addPiece(new Pawn(Color.BLACK, board), new Position("a7"));
		board.addPiece(new Pawn(Color.BLACK, board), new Position("b7"));
		board.addPiece(new Pawn(Color.BLACK, board), new Position("c7"));
		board.addPiece(new Pawn(Color.BLACK, board), new Position("d7"));
		board.addPiece(new Pawn(Color.BLACK, board), new Position("e7"));
		board.addPiece(new Pawn(Color.BLACK, board), new Position("f7"));
		board.addPiece(new Pawn(Color.BLACK, board), new Position("g7"));
		board.addPiece(new Pawn(Color.BLACK, board), new Position("h7"));

		board.addPiece(new Pawn(Color.WHITE, board), new Position("a2"));
		board.addPiece(new Pawn(Color.WHITE, board), new Position("b2"));
		board.addPiece(new Pawn(Color.WHITE, board), new Position("c2"));
		board.addPiece(new Pawn(Color.WHITE, board), new Position("d2"));
		board.addPiece(new Pawn(Color.WHITE, board), new Position("e2"));
		board.addPiece(new Pawn(Color.WHITE, board), new Position("f2"));
		board.addPiece(new Pawn(Color.WHITE, board), new Position("g2"));
		board.addPiece(new Pawn(Color.WHITE, board), new Position("h2"));
	}

}
