package model;

import java.util.InputMismatchException;

public class Position {

	private int row;
	private int column;

	public Position(int row, int column) {
		validatePosition(row, column);
		this.row = row;
		this.column = column;
	}

	public Position(String position) {
		validatePosition(position);
		this.row = '8' - position.charAt(1);
		this.column = position.charAt(0) - 'a';
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public void setValues(int row, int column) {
		validatePosition(row, column);
		this.row = row;
		this.column = column;
	}

	@Override
	public String toString() {
		return row + ", " + column;
	}

	public static boolean validatePosition(int row, int column) throws InputMismatchException {
		if (row < 0 || row > 7 || column < 0 || column > 7)
			throw new InputMismatchException("Non-existent position value. Valid values are from \"0\" to \"7\".");
		return true;
	}

	public static boolean validatePosition(String position) throws InputMismatchException {
		if (position.length() > 2) {
			throw new InputMismatchException("Non-existent position value. Valid values are from \"a1\" to \"h8\".");
		}
		char letter = position.charAt(0);
		int digit = position.charAt(1);
		if (letter < 'a' || letter > 'h' || digit < '1' || digit > '8') {
			throw new InputMismatchException("Non-existent position value. Valid values are from \"a1\" to \"h8\".");
		}
		return true;
	}

	public static Position convertPosition(String position) throws InputMismatchException {
		return new Position(position);
	}

	public static String convertString(Position position) {
		int row = 8 - position.getRow();
		return "" + (char) ('a' + position.getColumn()) + row;
	}

}
