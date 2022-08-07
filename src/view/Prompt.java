package view;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.ChessMatch;
import model.Color;
import model.Piece;
import model.Position;
import model.Status;

public class Prompt implements GameInterface {
	public static final String ANSI_ESC = "\u001B[27m";
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	public static final String ANSI_BRIGHT_WHITE = "\u001B[97m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	public static final String ANSI_BRIGHT_YELLOW_BACK = "\u001B[103m";

	private static void ClearConsole() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

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
		ClearConsole();
		Piece[][] pieces = match.getBoard().getPieces();
		boolean[][] possibleMoves = null;
		if (source != "") {
			possibleMoves = match.getBoard().getPiece(Position.convertPosition(source)).possibleMoves();
		}
		int rc = match.getBoard().getRows();
		String background;
		System.out.println(" " + ANSI_BLUE_BACKGROUND + "                            " + ANSI_RESET);
		for (int i = 0; i < rc; i++) {
			System.out.print(" " + ANSI_BLUE_BACKGROUND + ANSI_GREEN + " " + (rc - i) + ANSI_RESET);
			for (int j = 0; j < rc; j++) {
				if (i % 2 != 0) {
					if (j % 2 == 0) {
						background = ANSI_YELLOW_BACKGROUND;
					} else {
						background = ANSI_BRIGHT_YELLOW_BACK;
					}
				} else {
					if (j % 2 == 0) {
						background = ANSI_BRIGHT_YELLOW_BACK;
					} else {
						background = ANSI_YELLOW_BACKGROUND;
					}
				}
				if (possibleMoves != null && possibleMoves[i][j]) {
					background = ANSI_GREEN_BACKGROUND;
				}
				printPiece(pieces[i][j], background);
			}
			System.out.println(ANSI_BLUE_BACKGROUND + "  " + ANSI_RESET);
		}
		System.out.println(" " + ANSI_BLUE_BACKGROUND + ANSI_GREEN + "   a  b  c  d  e  f  g  h   " + ANSI_RESET);
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
			System.out.print(background + "   " + ANSI_RESET);
		} else {
			if (piece.getColor() == Color.BLACK)
				System.out.print(background + ANSI_BLACK + " " + piece + " " + ANSI_RESET);
			else
				System.out.print(background + ANSI_WHITE + " " + piece + " " + ANSI_RESET);
		}
	}

	private void printCapturePieces(List<Piece> pieces) {
		List<Piece> white = pieces.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
		List<Piece> black = pieces.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
		System.out.println("\n Captured Pieces");
		System.out.print("  White: " + ANSI_BRIGHT_YELLOW_BACK + ANSI_WHITE);
		System.out.println(Arrays.toString(white.toArray()));
		System.out.print(ANSI_RESET + "  Black: " + ANSI_YELLOW_BACKGROUND + ANSI_BLACK);
		System.out.println(Arrays.toString(black.toArray()));
		System.out.println(ANSI_RESET);
	}

	private void printMatch(ChessMatch match) {
		System.out.println(" Turn: " + match.getTurn());
		System.out.println(" Waiting player: " + match.getCurrentPlayer());
		String msn = "";
		switch (match.getStatus()) {
		case RESIGNING:
			msn = "\n " + ANSI_BLUE_BACKGROUND + " RESIGNING!!! " + ANSI_RESET;
			break;
		case STALEMATE:
			msn = "\n " + ANSI_BLUE_BACKGROUND + " STALEMATE!!! " + ANSI_RESET;
			break;
		case FIFTY_MOVES:
			msn = "\n " + ANSI_BLUE_BACKGROUND + " FIFTY-MOVES RULE!!! " + ANSI_RESET;
			break;
		case THREEFOLD_REPETITION:
			msn = "\n " + ANSI_BLUE_BACKGROUND + " THREEFOLD REPETITION!!! " + ANSI_RESET;
			break;
		case CHECKMATE:
			msn = "\n " + ANSI_BLUE_BACKGROUND + " CHECKMATE!!! " + ANSI_RESET;
			break;
		case CHECK:
			msn = "\n " + ANSI_BLUE_BACKGROUND + " CHECK!!! " + ANSI_RESET;
			break;
		case NORMAL:
			break;
		}
		System.out.println(msn);
	}

	private void winner(ChessMatch match) {
		Status st = match.getStatus();
		if (st != Status.NORMAL && st != Status.CHECK) {
			String win = match.getCurrentPlayer() == Color.WHITE ? "  BLACK WINS!  " : "  WHITE WINS!  ";
			if (match.getStatus() == Status.STALEMATE || match.getStatus() == Status.FIFTY_MOVES
					|| match.getStatus() == Status.THREEFOLD_REPETITION) {
				win = "    DRAWN!     ";
			}
			System.out.println("\n\n " + ANSI_BLUE_BACKGROUND + win + ANSI_RESET);
			System.out.println(" " + ANSI_BLUE_BACKGROUND + ANSI_GREEN + "  Game Over!!! " + ANSI_RESET);
		}
	}

	@Override
	public String promotionOption(Scanner sc) {
		String type;
		int count = 3;
		do {
			System.out.print(" Choose a piece to exchange the pawn (Q, B, N, R): ");
			type = sc.nextLine().toUpperCase();
			if (!type.equals("Q") && !type.equals("B") && !type.equals("N") && !type.equals("R")) {
				System.out.println(" " + ANSI_RED + "Invalid option!" + ANSI_RESET);
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
		System.out.print(" " + ANSI_RED + e + ANSI_RESET);
	}

}