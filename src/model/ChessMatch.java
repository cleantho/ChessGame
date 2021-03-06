package model;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import model.pieces.Bishop;
import model.pieces.King;
import model.pieces.Knight;
import model.pieces.Pawn;
import model.pieces.Queen;
import model.pieces.Rook;

public class ChessMatch {

	private int turn;
	private boolean check;
	// private boolean checkMate;
	private boolean promoted;
	private Piece enPassant;
	private Board board;

	private List<Piece> capturedPieces = new ArrayList<>();
	private King[] kings = new King[2];

	public ChessMatch() {
		board = new Board();
		turn = 1;
		initialSetup();
	}

	public int getTurn() {
		return turn;
	}

	public boolean isCheck() {
		return check;
	}

	public boolean isPromoted() {
		return promoted;
	}

	public Board getBoard() {
		return board;
	}

	public List<Piece> getCapturedPieces() {
		return capturedPieces;
	}

	public Color getCurrentPlayer() {
		return turn % 2 == 0 ? Color.BLACK : Color.WHITE;
	}

	public boolean isEmpty(String position) {
		if (board.isEmpty(Position.convertPosition(position))) {
			throw new InputMismatchException("There is not a piece on position " + position + ". Try again!");
		}
		return true;
	}

	public boolean validatePosition(String position) {
		Position pos = Position.convertPosition(position);
		if (board.isEmpty(pos)) {
			throw new ChessException("There is not a piece on position " + position + ".");
		}
		if (board.getPiece(pos).getColor() != getCurrentPlayer()) {
			throw new ChessException("The chosen piece is not yours.");
		}
		if (!board.getPiece(pos).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece");
		}
		return true;
	}

	public boolean replacePromotedPiece(String type, String target) {
		Position t = new Position(target);
		Piece p = board.getPiece(t);
		if (type.equals("R"))
			p = new Rook(p.getColor(), board);
		else if (type.equals("B"))
			p = new Bishop(p.getColor(), board);
		else if (type.equals("N"))
			p = new Knight(p.getColor(), board);
		else
			p = new Queen(p.getColor(), board);
		board.removePiece(t);
		board.addPiece(p, t);
		promoted = false;
		return true;
	}

	public boolean movePiece(String source, String target) {
		Position s = new Position(source);
		Position t = new Position(target);
		Piece p = board.getPiece(s);
		boolean[][] possible = p.possibleMoves();
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				if (possible[i][j] && t.getRow() == i && t.getColumn() == j) {

					if (!board.isEmpty(t)) {
						capturedPieces.add(board.removePiece(t));
					} else {
						if (p instanceof Pawn && (s.getColumn() != t.getColumn())) { // treatment for Enpassant
							capturedPieces.add(board.removePiece(new Position(s.getRow(), t.getColumn())));
						}
					}
					// treatment for Enpassant
					if (enPassant != null) {
						((Pawn) enPassant).setEnPassant(false);
						enPassant = null;
					}
					if (p instanceof Pawn && (s.getRow() + 2 == t.getRow() || s.getRow() - 2 == t.getRow())) {
						((Pawn) p).setEnPassant(true);
						enPassant = p;
					}
					// end - treatment for Enpassant
					// treatment for Promotion
					if (p instanceof Pawn && (t.getRow() == 0 || t.getRow() == 7)) {
						promoted = true;
					}
					// end - treatment for Promotion
					board.addPiece(board.removePiece(s), t);
					board.getPiece(t).increaseMoveCount();
					turn++;
					// put opponent king in check
					if(p.getColor() == Color.WHITE) {
						check = kings[0].inCheck();
					}else {
						check = kings[1].inCheck();
					}					
					return true;
				}
			}
		}

		throw new ChessException("The chosen piece can't move to target position.");
	}

	private void initialSetup() {
		board.addPiece(new Rook(Color.BLACK, board), new Position("a8"));
		board.addPiece(new Knight(Color.BLACK, board), new Position("b8"));
		board.addPiece(new Bishop(Color.BLACK, board), new Position("c8"));
		board.addPiece(new Queen(Color.BLACK, board), new Position("d8"));
		board.addPiece(new King(Color.BLACK, board), new Position("e8"));
		board.addPiece(new Bishop(Color.BLACK, board), new Position("f8"));
		board.addPiece(new Knight(Color.BLACK, board), new Position("g8"));
		board.addPiece(new Rook(Color.BLACK, board), new Position("h8"));

		board.addPiece(new Pawn(Color.BLACK, board), new Position("a7"));
		board.addPiece(new Pawn(Color.BLACK, board), new Position("b7"));
		board.addPiece(new Pawn(Color.BLACK, board), new Position("c7"));
		board.addPiece(new Pawn(Color.BLACK, board), new Position("d7"));
		board.addPiece(new Pawn(Color.BLACK, board), new Position("e7"));
		//board.addPiece(new Pawn(Color.BLACK, board), new Position("f7"));
		board.addPiece(new Pawn(Color.BLACK, board), new Position("g7"));
		board.addPiece(new Pawn(Color.BLACK, board), new Position("h7"));

		board.addPiece(new Pawn(Color.WHITE, board), new Position("a2"));
		board.addPiece(new Pawn(Color.WHITE, board), new Position("b2"));
		board.addPiece(new Pawn(Color.WHITE, board), new Position("c2"));
		board.addPiece(new Pawn(Color.WHITE, board), new Position("d2"));
		//board.addPiece(new Pawn(Color.WHITE, board), new Position("e2"));
		board.addPiece(new Pawn(Color.WHITE, board), new Position("f2"));
		board.addPiece(new Pawn(Color.WHITE, board), new Position("g2"));
		board.addPiece(new Pawn(Color.WHITE, board), new Position("h2"));

		board.addPiece(new Rook(Color.WHITE, board), new Position("a1"));
		board.addPiece(new Knight(Color.WHITE, board), new Position("b1"));
		board.addPiece(new Bishop(Color.WHITE, board), new Position("c1"));
		board.addPiece(new Queen(Color.WHITE, board), new Position("d1"));
		board.addPiece(new King(Color.WHITE, board), new Position("e1"));
		board.addPiece(new Bishop(Color.WHITE, board), new Position("f1"));
		board.addPiece(new Knight(Color.WHITE, board), new Position("g1"));
		board.addPiece(new Rook(Color.WHITE, board), new Position("h1"));

		kings[0] = (King) board.getPiece(Position.convertPosition("e8"));
		kings[1] = (King) board.getPiece(Position.convertPosition("e1"));
	}

}
