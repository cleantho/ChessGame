import java.util.InputMismatchException;
import java.util.Scanner;

import model.ChessException;
import model.ChessMatch;
import model.Status;
import view.Prompt;

public class Chess {

	public static void main(String[] args) throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		Prompt UI = new Prompt();
		ChessMatch match = new ChessMatch();
		int delay = 1500;
		while (!match.isCheckMate()) {
			try {
				UI.printBoard(match);
				String source = UI.readPosition(sc);
				if (source.equals("x")) {
					match.resigning();
					break;
				}
				match.validatePosition(source);	
				String target = "";
				do {
					try {
						UI.printBoard(match, source);
						target = UI.readPosition(sc);						
						match.movePiece(source, target);
						break;
					} catch (InputMismatchException e) {
						UI.printError(e.getMessage());
						Thread.sleep(delay);						
					} catch (ChessException e) {
						UI.printError(e.getMessage());
						Thread.sleep(delay);						
					}
				} while (true);
				if (match.isPromoted()) {
					String type = UI.promotionOption(sc);
					match.replacePromotedPiece(type, target);
				}
				if(match.getStatus() == Status.FIFTY_MOVES) {
					break;
				}
			} catch (InputMismatchException e) {
				UI.printError(e.getMessage());
				Thread.sleep(delay);				
			} catch (ChessException e) {
				UI.printError(e.getMessage());
				Thread.sleep(delay);				
			}
		}		
		UI.printBoard(match);
		sc.close();
	}

}
