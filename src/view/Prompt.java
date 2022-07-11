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

public class Prompt {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	private static void ClearConsole() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public String readPosition(Scanner sc) throws InputMismatchException {
		String in = sc.nextLine();
		Position.validatePosition(in);
		return in;
	}

	public void printBoard(ChessMatch match) {
		ClearConsole();
		Piece[][] pieces = match.getBoard().getPieces();
		int rc = match.getBoard().getRows();
		System.out.println("     " + ANSI_BLUE_BACKGROUND + "                            " + ANSI_RESET);
		for (int i = 0; i < rc; i++) {
			System.out.print("     " + ANSI_BLUE_BACKGROUND + ANSI_GREEN + " " + (rc - i) + ANSI_RESET);
			for (int j = 0; j < rc; j++) {
				if (i % 2 != 0) {
					if (j % 2 == 0) {
						System.out.print(ANSI_YELLOW_BACKGROUND);
					} else {
						System.out.print(ANSI_CYAN_BACKGROUND);
					}
				} else {
					if (j % 2 == 0) {
						System.out.print(ANSI_CYAN_BACKGROUND);
					} else {
						System.out.print(ANSI_YELLOW_BACKGROUND);
					}
				}
				printPiece(pieces[i][j]);
			}
			System.out.print(ANSI_BLUE_BACKGROUND + "  " + ANSI_RESET);
			System.out.println();
		}
		System.out.println("     " + ANSI_BLUE_BACKGROUND + ANSI_GREEN + "   a  b  c  d  e  f  g  h   " + ANSI_RESET);
		printCapturePieces(match.getCapturedPieces());
		printMatch(match);

	}

	public void printPiece(Piece piece) {
		if (piece == null) {
			System.out.print("   " + ANSI_RESET);
		} else {
			if (piece.getColor() == Color.BLACK)
				System.out.print(ANSI_BLACK + " " + piece + " " + ANSI_RESET);
			else
				System.out.print(ANSI_WHITE + " " + piece + " " + ANSI_RESET);
		}
	}

	public void printCapturePieces(List<Piece> pieces) {
		List<Piece> white = pieces.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
		List<Piece> black = pieces.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
		System.out.println("\n Captured Pieces");
		System.out.print("  White: " + ANSI_CYAN_BACKGROUND + ANSI_BLACK);
		System.out.println(Arrays.toString(white.toArray()));
		System.out.print(ANSI_RESET + "  Black: " + ANSI_YELLOW_BACKGROUND + ANSI_BLACK);
		System.out.println(Arrays.toString(black.toArray()));
		System.out.println(ANSI_RESET);
	}

	public void printMatch(ChessMatch match) {
		System.out.println(" Turn: " + match.getTurn());
		System.out.println(" Waiting player: " + (match.getTurn() % 2 == 0 ? "BLACK\n" : "WHITE\n"));
	}
}