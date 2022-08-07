package view;

import java.util.InputMismatchException;
import java.util.Scanner;

import model.ChessMatch;

public interface GameInterface {

	public String readPosition(Scanner sc) throws InputMismatchException;

	public void printBoard(ChessMatch match);

	public void printBoard(ChessMatch match, String source);

	public String promotionOption(Scanner sc);

	public void printError(String e);

}
