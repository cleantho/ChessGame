import java.util.InputMismatchException;
import java.util.Scanner;

import model.ChessException;
import model.ChessMatch;
import view.Prompt;

public class Chess {

	public static void main(String[] args) throws InterruptedException {
		int delay = 1200;
		Scanner sc = new Scanner(System.in);
		Prompt UI = new Prompt();
		ChessMatch match = new ChessMatch();
		while (true) {
			try {
				UI.printBoard(match);
				String source = UI.readPosition(sc);
				if (source.equals("x")) {
					UI.leave(match);
					break;
				}
				match.validatePosition(source);	
				String target = "";
				boolean wrong = true;
				do {
					try {
						UI.printBoard(match, source);
						target = UI.readPosition(sc);						
						match.movePiece(source, target);
						wrong = false;
					} catch (InputMismatchException e) {
						UI.printError(e.getMessage());
						Thread.sleep(delay);						
					} catch (ChessException e) {
						UI.printError(e.getMessage());
						Thread.sleep(delay);						
					}
				} while (wrong);
				if (match.isPromoted()) {
					String type = UI.promotionOption(sc);
					match.replacePromotedPiece(type, target);
				}
			} catch (InputMismatchException e) {
				UI.printError(e.getMessage());
				Thread.sleep(delay);				
			} catch (ChessException e) {
				UI.printError(e.getMessage());
				Thread.sleep(delay);				
			}
		}
		sc.close();
	}

}
