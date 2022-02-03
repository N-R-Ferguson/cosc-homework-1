import java.util.Scanner;
import java.io.BufferedReader;
//import java.io.BufferedWriter;
import java.io.FileReader;
//import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Problem1Tester {
	private static String tempArrayLine = "";
	protected static char[][] initial;/*
										 * = {{'5', '7', '1'}, {'2', '0', '8'}, {'4', '6', '3'}};
										 */
	protected static char[][] goal;/*
									 * = {{'0', '4', '8'}, {'1', '5', '2'}, {'6', '3', '7'}};
									 */
	private static int boardSize;
	public static void main(String[] args) {
		String line, fileInputName = "";
		Scanner input;
		//int boardSize = 0;
		int evaluationOption = 1;
		int hueristicOption = 2;

		// Problem1 p = new Problem1(initial, goal, evaluationOption, hueristicOption,
		// boardSize);
		// p.solve();

		String[] lineSplit = null;

		input = new Scanner(System.in);
		System.out.println("Enter the name of the file you would like to read in (.txt is not needed): ");
		fileInputName = input.nextLine();
		input.close();

		try {

			BufferedReader read = new BufferedReader(new FileReader(fileInputName));
			while ((line = read.readLine()) != null) {
				if (line.length() != 0) {
					lineSplit = line.split(" ");

					if (lineSplit.length == 1) {
						if (boardSize == 0)
							boardSize = Integer.parseInt(String.valueOf(lineSplit[0]));
						else if (evaluationOption == 0)
							evaluationOption = Integer.parseInt(String.valueOf(lineSplit[0]));
						else
							hueristicOption = Integer.parseInt(String.valueOf(lineSplit[0]));
					} else if (lineSplit.length == boardSize) {
						for (int i = 0; i < lineSplit.length; i++) {
							tempArrayLine += lineSplit[i] + " ";
						}
						tempArrayLine += "/ ";
					}
				}
			}

			read.close();

			initial = new char[boardSize][boardSize];
			goal = new char[boardSize][boardSize];
			fillArray();
		} catch (FileNotFoundException e) {
			System.out.println("No File by that name was found.");
		} catch (IOException e) {
			System.out.println("There was an error.");
		}

		Problem1 p = new Problem1(initial, goal, evaluationOption, hueristicOption, boardSize);
		p.solve();
	}

	public static void fillArray() {
		String[] tempArray = tempArrayLine.split(" / ");
		String[] tempMatrixLine = null;
		for (int i = 0; i < tempArray.length; i++) {
			tempMatrixLine = tempArray[i].split(" ");

			for (int j = 0; j < tempMatrixLine.length; j++) {
				if (i < boardSize) {
					initial[i][j] = tempMatrixLine[j].charAt(0);
				} else if (i >= boardSize)
					goal[i - boardSize][j] = tempMatrixLine[j].charAt(0);
				//if (i == boardSize+1)
				//	goal[i - boardSize][j] = tempMatrixLine[j].charAt(0);
				//if (i == boardSize+2)
				//	goal[i - boardSize][j] = tempMatrixLine[j].charAt(0);
			}

		}

		// for(int i=0; i< initial.length; i++){
		// System.out.print("[");
		// for(int j=0; j< initial[i].length; j++)
		// System.out.print(initial[i][j]);
		// System.out.print("]\n");
		// }
		//  System.out.println("=======================");
		// for(int i=0; i < goal.length; i++){
		// System.out.print("[");
		// for(int j=0; j< goal[i].length; j++)
		// System.out.print(goal[i][j]);
		// System.out.print("]\n");
		// }

	}
}