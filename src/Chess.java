import java.util.InputMismatchException;
import java.util.Scanner;

import model.ChessMatch;
import view.Prompt;

public class Chess {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Prompt UI = new Prompt();
		ChessMatch match = new ChessMatch();
		while (true) {
			UI.printBoard(match);
			try {
				System.out.print(" Source: ");
				String source = UI.readPosition(sc);
				// UI.printBoard(match);
				System.out.print(" Target: ");
				String target = UI.readPosition(sc);
				if (!match.movePiece(source, target)) {
					System.out.println("Movement is not allowed.");
				}
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		// sc.close();
	}

}
