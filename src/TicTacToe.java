import java.util.*;


public class TicTacToe {

	/*
	 * main MEthod Header hahahaha
	 * BS and JS code
	 */
	public static void main(String[] args) {
		//hey guys vebsmn
		Scanner scan = new Scanner(System.in);
		//out here asking for input
		System.out.print("Hello my dude want to play some itc tac toe how many dimensions?\n>");
		int dim = scan.nextInt();
		while(!(dim == 2 || dim == 3)) {
			System.out.print("u hav the big stupid give me a good Number\n>");
			dim = scan.nextInt();
		}
		System.out.print("How big do you want your dimensions to be?\n>");
		int size = scan.nextInt();
		while(size <= 0) {
			System.out.print("u hav the big stupid give me a good Number\n>");
			size = scan.nextInt();
		}
		//create array and populate it with empty spaces
		char[][][] board = new char[size][size][size];
		for (int i = 0; i < board.length; i ++) {
			for (int j = 0; j < board[i].length; j++) {
				for (int k = 0; k < board[i][j].length; k++) {
					board[i][j][k] = ' ';
				}
			}
		}
		//e
		System.out.print("if you have a friend to play with, input 2. If you want to play alone, input 1\n>");
		int players = scan.nextInt();
		while(players != 1 && players != 2) {
			System.out.print("e\n>");
			players = scan.nextInt();
		}
		printBoard(board, dim);
		
		
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
		if(dim == 3) {
			for(int i = 0; i < board.length; i++) {
				//handle the number above the boards
				//pad the number
				System.out.print(" ");
				for (int j = 0; j < board.length - 1; j++) {
					System.out.print(" ");
				}
				
				//print the number
				System.out.println(i + 1);
				
				//pad the board
				System.out.println();
				
				//print the actual board
				print2D(board[i]);
			}
		}
		else {
			print2D(board[0]);
		}
	}

	/* print2d method
	 * return type: none
	 * parameters: 2 dimensional char array to print
	 * precondition: board is initialized
	 * postcondition: borad is printed
	 * BS and JS code
	 */
	public static void print2D(char[][] board) {
		
		//print the letters for the columns
		System.out.print(" ");
		for(int i = 0; i < board.length; i++) {
			System.out.print(((char) (i + 'A'))  + " ");
		}
		System.out.println();
		//this uses nested for loops to handle each layer of the board and properly format it
		for(int i = 0; i < board.length; i++) {
			System.out.print(i + 1);
			for(int j = 0; j < board[i].length; j++) {
				//this decides whether to use the end-of-line case or not
				if(j == board[i].length - 1) {
					System.out.println(board[i][j]);
				} else {
					System.out.print(board[i][j] + "|");
				}
			}
			//this handles the horizontal lines
			if(i < board[i].length - 1) {
				System.out.print(" ");
				for (int f = 0; f < 2*(board.length) - 1; f++) {
					System.out.print("-");
				}
				System.out.println();
			}
		}
		System.out.println();
	}
	
	/* parses a move input and returns the intended coordinates
	 * parameters: an input that is a move
	 * return: the intended coordinates, as an array of integers
	 * precondition: the input is formatted correctly
	 * postcondition: none
	 * BS code
	 */
	public static int[][][] parseInput(String input) {
		//decide whether 
	}
}