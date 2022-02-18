
public class Problem4 {
	// This program solves sudoku puzzle using recursion, backtracking, and
	// constraint satisfication.
	private int[][] board; // sudoku board
	private int boardSize;

	// Constructor of Sudoku class
	public Problem4(int[][] board, int boardSize) {
		this.board = board; // set initial board
		this.boardSize = boardSize;
	}

	// Method solves a given puzzle
	public void solve() { // fill the board starting
		if (fill(0)) // at the beginning
			display(); // if success display board
		else
			System.out.println("No solution"); // otherwise failure
	}

	// Method fills a board using recursion/backtracking.
	// It fills the board starting at a given location
	private boolean fill(int location) {
		int x = location / boardSize; // find x,y coordinates of
		int y = location % boardSize; // current location
		int value;
		int temp = 0;

		if (location > boardSize * boardSize - 1) // if location exceeds board
			return true; // whole board is filled

		else if (board[x][y] != -4 && board[x][y] != -2 && board[x][y] != -3) // if location already has value
			return fill(location + 1); // fill the rest of borad

		else // otherwise
		{
			temp = board[x][y];
			for (value = 1; value <= boardSize; value++) {
				board[x][y] = value;

				if (check(x, y) && notLetter(board[x][y]) && fill(location + 1))
					return true; // if number causes no conflicts and the rest
				//board[x][y] = temp;
			} // of board can be filled then done

			board[x][y] = temp; // if none of numbers 1-9 work then
			return false; // empty the location and back track
		}
	}

	// Method checks whether a value at a given location causes any conflicts
	private boolean check(int x, int y) {
		int region = (int) Math.sqrt(boardSize);
		int a, b, i, j;

		for (j = 0; j < boardSize; j++) // check value causes conflict in row
			if (j != y && board[x][j] == board[x][y]) {
				return false;
			}
		for (i = 0; i < boardSize; i++) // check value causes conflict in column
			if (i != x && board[i][y] == board[x][y])
				return false;

		a = (x / region) * region;
		b = (y / region) * region; // check value causes conflict in
		for (i = 0; i < region; i++) // 3x3 region
			for (j = 0; j < region; j++)
				if ((a + i != x) && (b + j != y) && board[a + i][b + j] == board[x][y])
					return false;

		return true;
	}

	private boolean notLetter(int x) {
		boolean retVal = true;
		switch (x) {
		case -1:
			retVal = false;
			break;
		case -2:
			retVal = false;
			break;
		case -3:
			retVal = false;
			break;
		case -4:
			retVal = false;
			break;
		default:
			break;
		}
		return retVal;
	}

	// Method displays a board
	public void display() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++)
				System.out.print(board[i][j] + " ");
			System.out.println();
		}
	}

}
