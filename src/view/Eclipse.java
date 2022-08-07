package view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.ChessMatch;
import model.Color;
import model.Piece;
import model.Position;
import model.Status;

public class Eclipse implements GameInterface {

	public static final String KING_WHITE = "\u2654 ";
	public static final String QUEEN_WHITE = "\u2655 ";
	public static final String ROOK_WHITE = "\u2656 ";
	public static final String BISHOP_WHITE = "\u2657 ";
	public static final String KNIGHT_WHITE = "\u2658 ";
	public static final String PAWN_WHITE = "\u2659 ";

	public static final String KING_BLACK = "\u265A ";
	public static final String QUEEN_BLACK = "\u265B ";
	public static final String ROOK_BLACK = "\u265C ";
	public static final String BISHOP_BLACK = "\u265D ";
	public static final String KNIGHT_BLACK = "\u265E ";
	public static final String PAWN_BLACK = "\u2659.";

	public static final String MOVE = "\u2573 ";
	public static final String BLACK = "\u25AE ";
	public static final String WHITE = "\u25AF ";

	public static final String KING = "K";
	public static final String QUEEN = "Q";
	public static final String ROOK = "R";
	public static final String BISHOP = "B";
	public static final String KNIGHT = "N";
	public static final String PAWN = "P";

	@Override
	public String readPosition(Scanner sc) throws InputMismatchException {
		String in = sc.nextLine().toLowerCase();
		if (!in.equals("x")) {
			Position.validatePosition(in);
		}
		return in;
	}

	@Override
	public void printBoard(ChessMatch match) {
		printBoard(match, "");
	}

	@Override
	public void printBoard(ChessMatch match, String source) {
		Piece[][] pieces = match.getBoard().getPieces();
		boolean[][] possibleMoves = null;
		if (source != "") {
			possibleMoves = match.getBoard().getPiece(Position.convertPosition(source)).possibleMoves();
		}
		int rc = match.getBoard().getRows();
		String background;
		System.out.println(" ╔═══════════════════╗");
		for (int i = 0; i < rc; i++) {
			System.out.print(" " + (rc - i) + " ");
			for (int j = 0; j < rc; j++) {
				if (i % 2 != 0) {
					if (j % 2 == 0) {
						background = BLACK;
					} else {
						background = WHITE;
					}
				} else {
					if (j % 2 == 0) {
						background = WHITE;
					} else {
						background = BLACK;
					}
				}
				if (possibleMoves != null && possibleMoves[i][j]) {
					background = MOVE;
				}
				printPiece(pieces[i][j], background);
			}
			System.out.println(rc - i);
		}
		System.out.println(" ╚═══════════════════╝");
		System.out.println("   a  b c d e f g  h");
		printCapturePieces(match.getCapturedPieces());
		printMatch(match);
		if (!match.isCheckMate()) {
			if (source == "") {
				if (match.getStatus() != Status.FIFTY_MOVES && match.getStatus() != Status.THREEFOLD_REPETITION) {
					System.out.println(" To leave the game press \"x\".");
					if (match.getStatus() == Status.RESIGNING) {
						System.out.print(" Source: x");
					} else {
						System.out.print(" Source: ");
					}
				}
			} else {
				System.out.println(" Source: " + source);
				System.out.print(" Target: ");
			}
		}
		winner(match);
	}

	private void printPiece(Piece piece, String background) {
		if (piece == null) {
			System.out.print(background);
		} else {
			if (piece.getColor() == Color.BLACK)
				System.out.print(shape(piece.toString(), Color.BLACK));
			else
				System.out.print(shape(piece.toString(), Color.WHITE));
		}
	}

	private String shape(String type, Color color) {
		if (color == Color.WHITE) {
			switch (type) {
			case KING:
				return KING_WHITE;
			case QUEEN:
				return QUEEN_WHITE;
			case BISHOP:
				return BISHOP_WHITE;
			case KNIGHT:
				return KNIGHT_WHITE;
			case ROOK:
				return ROOK_WHITE;
			default:
				return PAWN_WHITE;
			}
		} else {
			switch (type) {
			case KING:
				return KING_BLACK;
			case QUEEN:
				return QUEEN_BLACK;
			case BISHOP:
				return BISHOP_BLACK;
			case KNIGHT:
				return KNIGHT_BLACK;
			case ROOK:
				return ROOK_BLACK;
			default:
				return PAWN_BLACK;
			}
		}
	}

	private void printCapturePieces(List<Piece> pieces) {
		List<Piece> white = pieces.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
		List<Piece> black = pieces.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
		System.out.println("\n Captured Pieces");
		System.out.print("  White: ");
		for (Piece p : white) {
			System.out.print(shape(p.toString(), Color.WHITE));
		}
		System.out.print("\n  Black: ");
		for (Piece p : black) {
			System.out.print(shape(p.toString(), Color.BLACK));
		}
		System.out.println("\n");
	}

	private void printMatch(ChessMatch match) {
		System.out.println(" Turn: " + match.getTurn());
		System.out.println(" Waiting player: " + match.getCurrentPlayer());
		String msn = "";
		switch (match.getStatus()) {
		case RESIGNING:
			msn = "\n  RESIGNING!!! ";
			break;
		case STALEMATE:
			msn = "\n  STALEMATE!!! ";
			break;
		case FIFTY_MOVES:
			msn = "\n  FIFTY-MOVES RULE!!! ";
			break;
		case THREEFOLD_REPETITION:
			msn = "\n  THREEFOLD REPETITION!!! ";
			break;
		case CHECKMATE:
			msn = "\n  CHECKMATE!!! ";
			break;
		case CHECK:
			msn = "\n  CHECK!!! ";
			break;
		case NORMAL:
			break;
		}
		System.out.println(msn);
	}

	private void winner(ChessMatch match) {
		Status st = match.getStatus();
		if (st != Status.NORMAL && st != Status.CHECK) {
			String win = match.getCurrentPlayer() == Color.WHITE ? "BLACK WINS!" : "WHITE WINS!";
			if (match.getStatus() == Status.STALEMATE || match.getStatus() == Status.FIFTY_MOVES
					|| match.getStatus() == Status.THREEFOLD_REPETITION) {
				win = "DRAWN!";
			}
			System.out.println("\n\n " + win);
			System.out.println(" Game Over!!! ");
		}
	}

	@Override
	public String promotionOption(Scanner sc) {
		String type;
		int count = 3;
		do {
			System.out.print(" Choose a piece to exchange the pawn (Q-♕, B-♗, N-♘, R-♖): ");
			type = sc.nextLine().toUpperCase();
			if (!type.equals("Q") && !type.equals("B") && !type.equals("N") && !type.equals("R")) {
				System.out.println(" Invalid option!");
				if (--count == 0) {
					type = "Q";
					break;
				}
			} else {
				break;
			}
		} while (true);
		return type;
	}

	@Override
	public void printError(String e) {
		System.out.println(" " + e);
	}

}
