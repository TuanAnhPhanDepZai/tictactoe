package camuoi.tictactoe;

import java.util.Random;
import java.util.Scanner;

public class TicTacToe {

	public static final Random RANDOM = new Random();

	public static void main(String[] args) {
		Board b = new Board();
		Scanner scanner = new Scanner(System.in);
		b.displayBoard();
		System.out.println(" Select turn\n 2. person(O)  1. Computer(X)");
		int choice = scanner.nextInt();
		if (choice == Board.PLAYER_X) {
			Point p = new Point(RANDOM.nextInt(3), RANDOM.nextInt(3));
			b.placeMove(p, Board.PLAYER_X);
			// b.pointCurrent = p;
			b.displayBoard();

		}
		while (!b.isGameOver()) {
			System.out.println("phan tuan anh");
			boolean MoveOk = true;
			do {
				if (!MoveOk) {
					System.out.println(" Cell is filled ");
				}
				System.out.println(" You move :");
				Point personMove = new Point(scanner.nextInt(), scanner.nextInt());
				MoveOk = b.placeMove(personMove, Board.PLAYER_O);
				// b.pointCurrent = personMove ;
			} while (!MoveOk);
			b.displayBoard();

			if (b.isGameOver())
				break;
			b.miniMax(0, Board.PLAYER_X);

			System.out.println("Computer choose position " + b.computerMove);
			b.placeMove(b.computerMove, Board.PLAYER_X);

			b.displayBoard();
		}

		if (b.hasPlayerWon(Board.PLAYER_X))
			System.out.println(" you lose");
		else if (b.hasPlayerWon(Board.PLAYER_O))
			System.out.println(" you win");
		else
			System.out.println(" draw");

	}

}