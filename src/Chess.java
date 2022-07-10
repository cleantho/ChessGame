import java.util.Scanner;

import model.Position;

public class Chess {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Position p = new Position(7,7);
		System.out.println(p);
		System.out.println(Position.convertString(p));
		
		p = new Position("h1");
		System.out.println(p);
		System.out.println(Position.convertPosition("a1"));
		sc.close();
	}

}
