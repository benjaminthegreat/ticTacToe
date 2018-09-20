import java.util.*;

public class TicTacToe {

	/*
	 * main MEthod Header hahahaha
	 * BS and JS code
	 */
	public static void main(String[] args) {
		// hey guys vebsmn
		Scanner scan = new Scanner(System.in);
		// out here asking for input
		System.out.print("Hello my dude want to play some itc tac toe how many dimensions?\n>");
		int dim = scan.nextInt();
		while (!(dim == 2 || dim == 3)) {
			System.out.print("u hav the big stupid give me a good Number\n>");
			dim = scan.nextInt();
		}
		System.out.print("How big do you want your dimensions to be?\n>");
		int size = scan.nextInt();
		while (size <= 0) {
			System.out.print("u hav the big stupid give me a good Number\n>");
			size = scan.nextInt();
		}
		// create array and populate it with empty spaces
		char[][][] board = new char[size][size][size];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				for (int k = 0; k < board[i][j].length; k++) {
					board[i][j][k] = ' ';
				}
			}
		}
		// e
		System.out.print("if you have a friend to play with, input 2. If you want to play alone, input 1\n>");
		int players = scan.nextInt();
		while (players != 1 && players != 2) {
			System.out.print("e\n>");
			players = scan.nextInt();
		}
		// switch over to nextline by trimming the newline character from the input
		// stream
		scan.nextLine();
		boolean hardAI = false;
		// ask about easy/hard AI if this is a singleplayer 2d game
		if (dim == 2 && players == 1) {
			boolean correctAnswer = false;
			while (!correctAnswer) {
				System.out.print("Do you want easy or hard AI?\n>");
				String input = scan.nextLine();
				if (input.equals("hard")) {
					hardAI = true;
					correctAnswer = true;
				} else if (input.equals("easy")) {
					correctAnswer = true;
				}
			}

		}
		printBoard(board, dim);
		// time to input loop, child
		// get the first command
		System.out.print("Take the first move, player X\n>");
		char player = 'X';
		String input = scan.nextLine();
		// do the loop logic
		while (!input.equals("quit") && !isWinner(board)) {
			// this is kinda terrible code
			int[] command = null;
			try {
				command = parseCommand(input, dim);
				if (board[command[0]][command[1]][command[2]] != ' ') {
					command = null;
					throw new Exception();
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("your command is invalid. try again\n>");
			}
			// check if that worked
			if (command != null) {
				try {
					board[command[0]][command[1]][command[2]] = player;
					player = player == 'X' ? 'O' : 'X';
					printBoard(board, dim);
					if (isWinner(board))
						break;
					// do random AI
					if (players == 1) {
						System.out.println("AI turn");
						// loop until the AI picks a valid move
						boolean aiMoved = false;
						while (!aiMoved && !hardAI) {
							int[] move = randomMove(dim, size);
							if (board[move[0]][move[1]][move[2]] == ' ') {
								aiMoved = true;
								board[move[0]][move[1]][move[2]] = 'O';
								player = 'X';
								printBoard(board, dim);
								if (isWinner(board))
									break;
								System.out.print("Take the next move\n>");
							}
						}
						if (hardAI) {
							int[] move = smartMove2d(board[0]);
							board[0][move[1]][move[2]] = 'O';
							player = 'X';
							printBoard(board, dim);
							if (isWinner(board))
								break;
							System.out.print("Take the next move\n>");
						}
					} else {
						System.out.print("Take the next move, player " + player + "\n>");
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("your command is invalid. try again\n>");
				}

			}
			input = scan.nextLine();
		}
		scan.close();
	}

	/* print method
	 * return type: none
	 * parameter: board to print, dimension of board
	 * precondition: board is initialized to have the correct characters in it
	 * postcondition: board is printed
	 * BS code
	 */
	public static void printBoard(char[][][] board, int dim) {
		if (dim == 3) {
			for (int i = 0; i < board.length; i++) {
				// handle the number above the boards
				// pad the number
				System.out.print("  ");
				for (int j = 0; j < board.length - 1; j++) {
					System.out.print(" ");
				}

				// print the number
				System.out.println(i + 1);

				// pad the board
				System.out.println();

				// print the actual board
				print2D(board[i]);
			}
		} else {
			print2D(board[0]);
		}
	}

	/* print2d method
	 * return type: none
	 * parameters: 2 dimensional char array to print
	 * precondition: board is initialized
	 * postcondition: board is printed
	 * BS and JS code
	 */
	public static void print2D(char[][] board) {

		// print the letters for the columns
		System.out.print("  ");
		for (int i = 0; i < board.length; i++) {
			System.out.print(((char) (i + 'A')) + " ");
		}
		System.out.println();
		// this uses nested for loops to handle each layer of the board and properly
		// format it
		for (int i = 0; i < board.length; i++) {
			System.out.print(i + 1 + " ");
			for (int j = 0; j < board[i].length; j++) {
				// this decides whether to use the end-of-line case or not
				if (j == board[i].length - 1) {
					System.out.println(board[i][j]);
				} else {
					System.out.print(board[i][j] + "|");
				}
			}
			// this handles the horizontal lines
			if (i < board[i].length - 1) {
				System.out.print("  ");
				for (int f = 0; f < 2 * (board.length) - 1; f++) {
					System.out.print("-");
				}
				System.out.println();
			}
		}
		System.out.println();
	}

	/* random ai method generates a random move
	 * parameters: dimension and size of board
	 * return: an integer array containing the random move
	 * precondition: none
	 * postcondition: move is within the board
	 */
	public static int[] randomMove(int dim, int size) {
		Random rand = new Random();
		int[] move = new int[3];
		for (int i = 0; i < 3; i++) {
			move[i] = rand.nextInt(size);
		}
		if (dim == 2)
			move[0] = 0;
		return move;
	}

	/* smart ai method generates an optimal move
	 * parameters: board and dimension
	 * return: the optimal move
	 * precondition: none
	 * postcondition: an optimal move is returned
	 */
	public static int[] smartMove2d(char[][] board) {
		// first, check for a winning move
		// every space must be checked for n-1 in a row sections with a space at the end
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				// this runs for each square on the board
				// determine if it's my square
				if (board[i][j] != 'O') {
					continue;
				}
				// now check for the sections
				// horizontal, vertical, and each diagonal need to be checked
				int horizontalInARow = 0;
				int verticalInARow = 0;
				int fortyFiveDegreesInARow = 0;
				int oneHundredAndThirtyFiveDegreesInARow = 0;
				// horizontal, positive
				for (int k = j + 1; k < board.length; k++) {
					if (board[i][k] == board[i][j]) {
						horizontalInARow++;
					}
					if (horizontalInARow == board.length - 1) {
						if (board[i][k] == ' ') {
							int[] move = { 0, i, k };
							return move;
						}
					}
				}
				// horizontal, negative
				for (int k = j - 1; k >= 0; k--) {
					if (board[i][k] == board[i][j]) {
						horizontalInARow++;
					}
					if (horizontalInARow == board.length - 1) {
						if (board[i][k] == ' ') {
							int[] move = { 0, i, k };
							return move;
						}
					}
				}
				// vertical, positive
				for (int k = i + 1; k < board.length; k++) {
					if (board[k][j] == board[i][j]) {
						verticalInARow++;
					}
					if (verticalInARow == board.length - 1) {
						if (board[k][j] == ' ') {
							int[] move = { 0, i, k };
							return move;
						}
					}
				}
				// vertical, negative
				for (int k = i - 1; k >= 0; k--) {
					if (board[k][j] == board[i][j]) {
						verticalInARow++;
					}
					if (verticalInARow == board.length - 1) {
						if (board[k][j] == ' ') {
							int[] move = { 0, i, k };
							return move;
						}
					}
				}
				// diagonal, 45 degrees
				// positive
				for (int k = 0; k < board.length - j && k < board.length - i; k++) {
					if (board[i + k][j + k] == board[i][j]) {
						fortyFiveDegreesInARow++;
					}
					if (fortyFiveDegreesInARow == board.length - 1) {
						if (board[i + k][j + k] == ' ') {
							int[] move = { 0, i + k, j + k };
							return move;
						}
					}
				}
				// negative
				for (int k = 0; k >= j && k >= i; k++) {
					if (board[i - k][j - k] == board[i][j]) {
						fortyFiveDegreesInARow++;
					}
					if (fortyFiveDegreesInARow == board.length - 1) {
						if (board[i - k][j - k] == ' ') {
							int[] move = { 0, i - k, j - k };
							return move;
						}
					}
				}
				// diagonal, 135 degrees
				// positive but this terminology breaks down and is mostly just to help me
				// comprehend what the code I'm writing does
				for (int k = 0; k < board.length - j && k >= i; k++) {
					if (board[i + k][j + k] == board[i][j]) {
						oneHundredAndThirtyFiveDegreesInARow++;
					}
					if (oneHundredAndThirtyFiveDegreesInARow == board.length - 1) {
						if (board[i + k][j - k] == ' ') {
							int[] move = { 0, i + k, j - k };
							return move;
						}
					}
				}
				// diagonal, 135 degrees
				// negative but this terminology breaks down and is mostly just to help me
				// comprehend what the code I'm writing does
				for (int k = 0; k >= j && k < board.length - i; k++) {
					if (board[i + k][j + k] == board[i][j]) {
						oneHundredAndThirtyFiveDegreesInARow++;
					}
					if (oneHundredAndThirtyFiveDegreesInARow == board.length - 1) {
						if (board[i + k][j - k] == ' ') {
							int[] move = { 0, i + k, j - k };
							return move;
						}
					}
				}
			}
		}
		// block check, same as above but I want to block, rather than win
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				// this runs for each square on the board
				// determine if it's my square
				if (board[i][j] != 'X') {
					continue;
				}
				// now check for the sections
				// horizontal, vertical, and each diagonal need to be checked
				int horizontalInARow = 0;
				int verticalInARow = 0;
				int fortyFiveDegreesInARow = 0;
				int oneHundredAndThirtyFiveDegreesInARow = 0;
				// horizontal, positive
				for (int k = j + 1; k < board.length; k++) {
					if (board[i][k] == board[i][j]) {
						horizontalInARow++;
					}
					if (horizontalInARow == board.length - 1) {
						if (board[i][k] == ' ') {
							int[] move = { 0, i, k };
							return move;
						}
					}
				}
				// horizontal, negative
				for (int k = j - 1; k >= 0; k--) {
					if (board[i][k] == board[i][j]) {
						horizontalInARow++;
					}
					if (horizontalInARow == board.length - 1) {
						if (board[i][k] == ' ') {
							int[] move = { 0, i, k };
							return move;
						}
					}
				}
				// vertical, positive
				for (int k = i + 1; k < board.length; k++) {
					if (board[k][j] == board[i][j]) {
						verticalInARow++;
					}
					if (verticalInARow == board.length - 1) {
						if (board[k][j] == ' ') {
							int[] move = { 0, i, k };
							return move;
						}
					}
				}
				// vertical, negative
				for (int k = i - 1; k >= 0; k--) {
					if (board[k][j] == board[i][j]) {
						verticalInARow++;
					}
					if (verticalInARow == board.length - 1) {
						if (board[k][j] == ' ') {
							int[] move = { 0, i, k };
							return move;
						}
					}
				}
				// diagonal, 45 degrees
				// positive
				for (int k = 0; k < board.length - j && k < board.length - i; k++) {
					if (board[i + k][j + k] == board[i][j]) {
						fortyFiveDegreesInARow++;
					}
					if (fortyFiveDegreesInARow == board.length - 1) {
						if (board[i + k][j + k] == ' ') {
							int[] move = { 0, i + k, j + k };
							return move;
						}
					}
				}
				// negative
				for (int k = 0; k <= j && k <= i; k++) {
					if (board[i - k][j - k] == board[i][j]) {
						fortyFiveDegreesInARow++;
					}
					if (fortyFiveDegreesInARow == board.length - 1) {
						if (board[i - k][j - k] == ' ') {
							int[] move = { 0, i - k, j - k };
							return move;
						}
					}
				}
				// diagonal, 135 degrees
				// positive but this terminology breaks down and is mostly just to help me
				// comprehend what the code I'm writing does
				for (int k = 0; k < board.length - j && k <= i; k++) {
					if (board[i - k][j + k] == board[i][j]) {
						oneHundredAndThirtyFiveDegreesInARow++;
					}
					if (oneHundredAndThirtyFiveDegreesInARow == board.length - 1) {
						if (board[i - k][j + k] == ' ') {
							int[] move = { 0, i + k, j - k };
							return move;
						}
					}
				}
				// diagonal, 135 degrees
				// negative but this terminology breaks down and is mostly just to help me
				// comprehend what the code I'm writing does
				for (int k = 0; k <= j && k < board.length - i; k++) {
					if (board[i + k][j - k] == board[i][j]) {
						oneHundredAndThirtyFiveDegreesInARow++;
					}
					if (oneHundredAndThirtyFiveDegreesInARow == board.length - 1) {
						if (board[i + k][j - k] == ' ') {
							int[] move = { 0, i + k, j - k };
							return move;
						}
					}
				}
			}
		}
		// fork detection
		// it only finds the first fork, and technically only finds 3x3-style forks, but
		// it is somewhat useable. I could write better code that looks further, but it
		// becomes increasingly complicated
		// this is a pain to generalize and generalizes poorly
		// run for every space on the board
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] != ' ') {
					continue;
				}
				int[] move = { 0, i, j };
				// check every space around it for an O
				boolean hasO = false;
				if (i - 1 >= 0) {
					if (board[i - 1][j] == 'O') {
						if (hasO) {
							return move;
						}
						hasO = true;
					}
				}
				if (j - 1 >= 0) {
					if (board[i][j - 1] == 'O') {
						if (hasO) {
							return move;
						}
						hasO = true;
					}
				}
				if (j + 1 < board.length) {
					if (board[i][j + 1] == 'O') {
						if (hasO) {
							return move;
						}
						hasO = true;
					}
				}
				if (i < board.length - 1) {
					if (board[i + 1][j] == 'O') {
						if (hasO) {
							return move;
						}
						hasO = true;
					}
				}
				if (j < board.length - 1 && i < board.length - 1) {
					if (board[i + 1][j + 1] == 'O') {
						if (hasO) {
							return move;
						}
						hasO = true;
					}
				}
				if (j > 0 && i < board.length - 1) {
					if (board[i + 1][j - 1] == 'O') {
						if (hasO) {
							return move;
						}
						hasO = true;
					}
				}
				if (j < board.length - 1 && i > 0) {
					if (board[i - 1][j + 1] == 'O') {
						if (hasO) {
							return move;
						}
						hasO = true;
					}
				}
				if (j > 0 && i > 0) {
					if (board[i - 1][j - 1] == 'O') {
						if (hasO) {
							return move;
						}
						hasO = true;
					}
				}
			}
		}
		// blocking fork is the same but with X
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] != ' ') {
					continue;
				}
				int[] move = { 0, i, j };
				// check every space around it for an O
				boolean hasO = false;
				if (i - 1 >= 0) {
					if (board[i - 1][j] == 'X') {
						if (hasO) {
							return move;
						}
						hasO = true;
					}
				}
				if (j - 1 >= 0) {
					if (board[i][j - 1] == 'X') {
						if (hasO) {
							return move;
						}
						hasO = true;
					}
				}
				if (j + 1 < board.length) {
					if (board[i][j + 1] == 'X') {
						if (hasO) {
							return move;
						}
						hasO = true;
					}
				}
				if (i < board.length - 1) {
					if (board[i + 1][j] == 'X') {
						if (hasO) {
							return move;
						}
						hasO = true;
					}
				}
				if (j < board.length - 1 && i < board.length - 1) {
					if (board[i + 1][j + 1] == 'X') {
						if (hasO) {
							return move;
						}
						hasO = true;
					}
				}
				if (j > 0 && i < board.length - 1) {
					if (board[i + 1][j - 1] == 'X') {
						if (hasO) {
							return move;
						}
						hasO = true;
					}
				}
				if (j < board.length - 1 && i > 0) {
					if (board[i - 1][j + 1] == 'X') {
						if (hasO) {
							return move;
						}
						hasO = true;
					}
				}
				if (j > 0 && i > 0) {
					if (board[i - 1][j - 1] == 'X') {
						if (hasO) {
							return move;
						}
						hasO = true;
					}
				}
			}
		}
		// and finally, the simple moves
		// I'm going to simplify a bit here and check the
		// center center
		if (board[board.length / 2][board.length / 2] == ' ') {
			int[] move = { 0, board.length / 2, board.length / 2 };
			return move;
		}
		int b = board.length - 1;
		// opposite corner
		if (board[0][0] == 'X') {
			int[] move = { 0, b, b };
			return move;
		}
		if (board[b][0] == 'X') {
			int[] move = { 0, 0, b };
			return move;
		}
		if (board[0][b] == 'X') {
			int[] move = { 0, b, 0 };
			return move;
		}
		if (board[b][b] == 'X') {
			int[] move = { 0, 0, 0 };
			return move;
		}
		// open corner
		if (board[0][0] == ' ') {
			int[] move = { 0, 0, 0 };
			return move;
		}
		if (board[b][0] == ' ') {
			int[] move = { 0, b, 0 };
			return move;
		}
		if (board[0][b] == ' ') {
			int[] move = { 0, 0, b };
			return move;
		}
		if (board[b][b] == ' ') {
			int[] move = { 0, b, b };
			return move;
		}
		// instead of empty side, I will generalize to random move
		// lel
		// this is probably ok because it just loops until a random move happens to be a
		// valid move
		// it isn't optimal but it probably works
		// optimally I should make this code recursive with each subset of the board and
		// looking for a winning move in each board, then returning the first one found
		// instead of just checking the trivial cases for each subset
		// obviously I'd need to implement blocking and forking somehow but it would
		// work
		// if I hadn't procrastinated this until 11 the night it was due
		while (true) {
			int[] randMove = randomMove(board.length, 2);
			if (board[randMove[1]][randMove[2]] == ' ') {
				return randMove;
			}
		}
	}

	/* parses a move input and returns the intended coordinates
	 * parameters: an input that is a move, dimension is for verification checking purposes
	 * return: the intended coordinates, as an array of integers
	 * precondition: the input is formatted correctly
	 * postcondition: none
	 * BS code
	 */
	public static int[] parseCommand(String input, int dimension) {
		// Make a scanner to get individual tokens
		Scanner tokenizer = new Scanner(input);
		// decide which board to use
		// initialize a variable for it
		int dim = dimension == 2 ? 0 : tokenizer.nextInt() - 1;
		// now handle parsing the next part
		String boardIndex = tokenizer.next();
		// done with the scanner
		tokenizer.close();
		// split the token into two parts and store them as ints
		int column = boardIndex.charAt(0) - 'A';
		int row = Integer.parseInt(boardIndex.charAt(1) + "") - 1;
		// and return the array
		int[] e = { dim, row, column };
		return e;
	}

	/* figures out if someone has won
	 * param: the board
	 * return: boolean if someone has won yet
	 * precon: board is populated
	 * postcon: none
	 * JS Code
	 */
	public static boolean isWinner(char[][][] board) {
		for (char[][] board2D : board) {
			if (is2DWinner(board2D))
				return true;
		}
		for (int i = 0; i < board.length; i++) {
			char[][] board2D = new char[board.length][board.length];
			for (int j = 0; j < board.length; j++) {
				board2D[j] = board[j][i];
			}
			if (is2DWinner(board2D))
				return true;

		}
		for (int i = 0; i < board.length; i++) {
			char[][] board2D = new char[board.length][board.length];
			for (int j = 0; j < board.length; j++) {
				char[] arr = new char[board.length];
				for (int k = 0; k < board.length; k++) {
					arr[k] = board[k][j][i];
				}
				board2D[j] = arr;
			}
			if (is2DWinner(board2D))
				return true;

		}
		// four diagonal ways to win yet unchecked
		char[] dia1 = new char[board.length];
		char[] dia2 = new char[board.length];
		char[] dia3 = new char[board.length];
		char[] dia4 = new char[board.length];
		for (int i = 0; i < board.length; i++) {
			dia1[i] = board[i][i][i];
			dia2[i] = board[i][i][board.length - 1 - i];
			dia3[i] = board[i][board.length - 1 - i][board.length - 1 - i];
			dia4[i] = board[i][board.length - 1 - i][i];
		}
		if (is1DWinner(dia1) || is1DWinner(dia2) || is1DWinner(dia3) || is1DWinner(dia4))
			return true;
		return false;
	}

	/* figures out if someone has won
	 * param: the board
	 * return: boolean if someone has won yet
	 * precon: board is populated
	 * postcon: none
	 * JS Code
	 */
	public static boolean is2DWinner(char[][] board) {
		for (char[] arr : board) {
			if (is1DWinner(arr))
				return true;
		}
		for (int i = 0; i < board.length; i++) {
			char[] arr = new char[board.length];
			for (int j = 0; j < board.length; j++) {
				arr[j] = board[j][i];
			}
			if (is1DWinner(arr))
				return true;
		}
		char[] dia1 = new char[board.length];
		char[] dia2 = new char[board.length];
		for (int i = 0; i < board.length; i++) {
			dia1[i] = board[i][i];
			dia2[i] = board[i][board.length - i - 1];
		}
		if (is1DWinner(dia1) || is1DWinner(dia2))
			return true;

		return false;
	}

	/* figures out if someone has won
	 * param:  a 1D array
	 * return: boolean if someone has won yet
	 * precon: board is populated
	 * postcon: none
	 * JS Code
	 */
	public static boolean is1DWinner(char[] arr) {
		// placeholder
		char first = '#';
		if (arr[0] == 'X' || arr[0] == 'O') {
			first = arr[0];
		}
		for (char c : arr) {
			if (c != first)
				return false;
		}
		System.out.println(first + " WINS!!!");
		return true;

	}

	/*prints a help menu if called by player
	 * param: none
	 * return: none
	 * precon: help is called
	 * postcon: menu was printed
	 * JS Code
	 */
	public static void help() {
		System.out.println(
				"This is tic tac toe. To enter your symbol, input the coordinates you would like to put it in in the form <letter><number>");
		System.out.println("Example: A1");
		System.out.println(
				"If playing in 3D, input the number indicating what level you would like to put your symbol in first, then a space, then the location on that level");
		System.out.println("Example: 1 A1");
		System.out.println("you can type quit to quit at any time");
	}

}
