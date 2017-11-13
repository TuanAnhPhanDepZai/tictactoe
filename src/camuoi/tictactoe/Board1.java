package camuoi.tictactoe;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.ValidationEvent;

public class Board1 {
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
		if (board[point.x][point.y] != NO_PLAYER) {
			return false;
		} else {
			board[point.x][point.y] = player;
		}
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

	// them that toan tim kiem xem doi thu có sap thang hay khong de ngan chan
	public Point sapthuaOrsapthang() {
		Point point = null;
		List<Point> OccpiedCell = getOccupiedCell();
		for (int i = 0; i < OccpiedCell.size(); i++) {
			Point point1 = OccpiedCell.get(i);
			for (int j = 0; j < OccpiedCell.size(); j++) {

				Point point2 = OccpiedCell.get(i);
				if (board[point1.x][point1.y] == board[point2.x][point2.y]) {
					if (point1.x == point2.x && Math.abs(point1.y - point2.y) == 2)
						point = new Point(point1.x, (point1.y + point2.y) / 2);
					else if (point1.y == point2.y && Math.abs(point1.x - point2.x) == 2)
						point = new Point((point1.x + point2.x) / 2, point1.y);
					else if (Math.abs(point1.x - point2.x) == 2 && Math.abs(point1.x - point2.x) == 2)
						point = new Point((point1.x + point2.x) / 2, (point1.y + point2.y) / 2);
					else if (point1.x == point2.x && Math.abs(point1.y - point2.y) == 1) {

					}
				}
			}
		}
		return point;
	}

	// phai co 1 mang cac phan tu da co nguoi choi
	public Point sapthuaOrsapthang1() {
		Point point = null;
		List<Point> OccpiedCell = getAvaiableCell();
		for (int i = 0; i < OccpiedCell.size(); i++) {
			Point point1 = OccpiedCell.get(i);
			if ((placeMove(point1, PLAYER_O) && hasPlayerWon(PLAYER_O))
					|| (placeMove(point1, PLAYER_X) && hasPlayerWon(PLAYER_O))) {
				point = point1;
			}
			board[point1.x][point1.y] = NO_PLAYER;
		}
		return point;
	}

	public List<Point> getOccupiedCell() {
		List<Point> OccupiedCell = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] != NO_PLAYER) {
					OccupiedCell.add(new Point(i, j));
				}
			}
		}
		return OccupiedCell;
	}

	public int miniMax(int depth, int turn) {
		if (hasPlayerWon(PLAYER_X))
			return 1;
		if (hasPlayerWon(PLAYER_O))
			return -1;

		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;

		if (turn == PLAYER_X) {
			List<Point> avaiableCell = getAvaiableCell();
			if (avaiableCell.isEmpty())
				return 0;
			Point locationO = null;
			for (int i = 0; i < avaiableCell.size(); i++) {
				Point point = avaiableCell.get(i);
				if (placeMove(point, PLAYER_X) && hasPlayerWon(PLAYER_X)) {
					computerMove = point;
					board[point.x][point.y] = NO_PLAYER;
					return turn == PLAYER_X ? max : min;
				}
				board[point.x][point.y] = NO_PLAYER;
				if (placeMove(point, PLAYER_O) && hasPlayerWon(PLAYER_O)) {
					computerMove = point;
					board[point.x][point.y] = NO_PLAYER;
					return turn == PLAYER_X ? max : min;
				}
				board[point.x][point.y] = NO_PLAYER;
				placeMove(point, PLAYER_X);
				int scorePlayerX = miniMax(depth + 1, PLAYER_O);
				if (max < scorePlayerX) {
					max = scorePlayerX;
					locationO = point;
				}
				if (depth == 0 && hasPlayerWon(PLAYER_X)) {
					computerMove = point;
					break;
				}
				computerMove = point;
				board[point.x][point.y] = NO_PLAYER;
			}

		} else if (turn == PLAYER_O) {
			List<Point> avaiableCell = getAvaiableCell();
			for (int i = 0; i < avaiableCell.size(); i++) {
				Point point = avaiableCell.get(i);
				int scorePlayerO = miniMax(depth + 1, PLAYER_X);
				if (min > scorePlayerO) {
					min = scorePlayerO;
				}
				if (min == -1) {
					board[point.x][point.y] = NO_PLAYER;
					break;
				}
				board[point.x][point.y] = NO_PLAYER;
			}
		}

		return turn == PLAYER_X ? max : min;
	}
}
