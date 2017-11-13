package camuoi.tictactoe;

import java.util.ArrayList;
import java.util.List;

public class Board {
	public static final int NO_PLAYER = 0;
	public static final int PLAYER_X = 1;
	public static final int PLAYER_O = 2;
	public int[][] board = new int[3][3];
	public Point computerMove;

	public boolean isGameOver() {
		return hasPlayerWon(PLAYER_X) || hasPlayerWon(PLAYER_O) || getAvaiableCell().isEmpty();
	}

	public boolean hasPlayerWon(int player) {
		if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == player)
				|| (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == player)) {
			return true;
		}
		for (int i = 0; i < 3; i++) {
			if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == player)
					|| (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == player)) {
				return true;
			}
		}
		return false;
	}

	public List<Point> getAvaiableCell() {
		List<Point> avaiableCell = new ArrayList<>();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == NO_PLAYER) {
					avaiableCell.add(new Point(i, j));
				}
			}
		}
		return avaiableCell;
	}

	public boolean placeMove(Point point, int player) {
		if (board[point.x][point.y] != NO_PLAYER)
			return false;
		board[point.x][point.y] = player;

		return true;
	}

	public void displayBoard() {
		System.out.println();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				String value = "?";
				if (board[i][j] == PLAYER_X) {
					value = "X";
				} else if (board[i][j] == PLAYER_O) {
					value = "0";
				}
				System.out.print(value + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public int miniMax(int depth, int turn) {
		if (hasPlayerWon(PLAYER_X))
			return 50 - depth;
		if (hasPlayerWon(PLAYER_O))
			return -50 + depth;

		List<Point> avaiableCell = getAvaiableCell();

		if (avaiableCell.isEmpty())
			return 0;

		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;

		for (int i = 0; i < avaiableCell.size(); i++) {
			Point point = avaiableCell.get(i);
			if (turn == PLAYER_X) {
				placeMove(point, PLAYER_X);
				int currentScore = miniMax(depth + 1, PLAYER_O);
				max = Math.max(max, currentScore);
				if (depth == 0) {
					System.out.println("Computer score for position " + point + "score " + currentScore);
				}
				if (max == currentScore) {
					computerMove = point;
				}
			} else if (turn == PLAYER_O) {
				placeMove(point, PLAYER_O);
				int currentScore = miniMax(depth + 1, PLAYER_X);
				min = Math.min(min, currentScore);
				if (min == -1) {
					board[point.x][point.y] = NO_PLAYER;
					break;
				}
			}
			board[point.x][point.y] = NO_PLAYER;
		}
		return turn == PLAYER_X ? max : min;
	}
}
