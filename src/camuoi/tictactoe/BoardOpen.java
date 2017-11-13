package camuoi.tictactoe;

import java.util.ArrayList;
import java.util.List;

public class BoardOpen {
	public static final int NO_PLAYER = 0;
	public static final int PLAYER_X = 1; // computer
	public static final int PLAYER_O = 2; // person
	public int[][] board = new int[20][20]; // ban co kich co 20 x 20
	public Point pointCurrent; // luu giu nut danh cua person hien tai
	public Point computerMove;

	public boolean isGameOver() {
		return hasPlayerWon(PLAYER_X) || hasPlayerWon(PLAYER_O) || getAvaiableTotalBoard().isEmpty();
	}

	public boolean checkForWon(Point point, int Player) {
		if (point.x + 4 < 20 && point.y + 4 < 20) {
			int x = point.x;
			int y = point.y;
			for (int i = x; i < x + 5; i++) {
				int t = board[x][y];
				if ((t == board[x][y + 1] && t == board[x][y + 2] && t == board[x][y + 3] && t == board[x][y + 4]
						&& t == Player)
						|| (t == board[x + 1][y] && t == board[x + 2][y] && t == board[x + 3][y] && t == board[x + 4][y]
								&& t == Player)) {
					return true;
				}
			}
			if ((board[x][y] == board[x + 1][y + 1] && board[x][y] == board[x + 2][y + 2]
					&& board[x][y] == board[x + 3][y + 3] && board[x][y] == board[x + 4][y + 4]
					&& board[x][y] == Player)
					|| board[x + 4][y] == board[x + 3][y + 1] && board[x + 4][y] == board[x + 2][y + 2]
							&& board[x + 4][y] == board[x + 1][y + 3] && board[x + 4][y] == board[x][y + 4]
							&& board[x + 4][y] == Player)
				return true;
		}
		return false;
	}

	public List<Point> getAvaiableTotalBoard() {
		List<Point> AvaiableTotalBoard = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				if (board[i][j] == NO_PLAYER) {
					AvaiableTotalBoard.add(new Point(i, j));
				}
			}
		}
		return AvaiableTotalBoard;
	}

	public boolean hasPlayerWon(int player) {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				if (checkForWon(new Point(i, j), player) == true) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean hasPlayerWon1(int player) {
		int x1 = Math.max(pointCurrent.x - 5, 0);
		int x2 = Math.min(pointCurrent.x + 5, 19);
		int y1 = Math.max(pointCurrent.y - 5, 0);
		int y2 = Math.min(pointCurrent.y + 5, 19);
		for (int i = x1; i <= x2; i++) {
			for (int j = y1; j <= y2; j++) {
				if (checkForWon(new Point(i, j), player)) {
					return true;
				}
			}
		}
		return false;
	}

	public List<Point> getAvaiableCell() { // luu y ham nay chi tra lai khong
											// gian dia chi co san da duoc
											// khaoanh vung dua vao nuoc di hien
											// tai cua nguoi choi ma thoi, khogn
											// phai toan bo ban co
		List<Point> avaiableCell = new ArrayList<>();
		int x1 = Math.max(pointCurrent.x - 5, 0);
		int x2 = Math.min(pointCurrent.x + 5, 19);
		int y1 = Math.max(pointCurrent.y - 5, 0);
		int y2 = Math.min(pointCurrent.y + 5, 19);

		for (int i = x1; i <= x2; i++) {
			for (int j = y1; j <= y2; j++) {
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
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
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
		if (hasPlayerWon1(PLAYER_X))
			return 1;
		if (hasPlayerWon1(PLAYER_O))
			return -1;

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
					if (currentScore >= 0) {
					}
					if (depth == 0)
						computerMove = point;
				}
				if (currentScore == 1) {
					board[point.x][point.y] = NO_PLAYER; // khong dc chon
					break;
				}
				if (i == avaiableCell.size() - 1) {
					if (depth == 0)
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
