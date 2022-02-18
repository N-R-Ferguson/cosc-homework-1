import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Problem4Tester {
	
	private static String tempArrayLine = "";
	protected static int[][] board;
	private static int[][] dummyBoard;
	private static int boardSize;
	public static void main(String[] args) {
		String line, fileInputName = "";
		Scanner input;
		String[] lineSplit = null;

		input = new Scanner(System.in);
		System.out.println("Enter the name of the file you would like to read in (.txt is not needed): ");
		fileInputName = input.nextLine();
		System.out.println("What is the name of the file you would like to write to?");
		String fileName = input.nextLine();
		input.close();

		try {

			BufferedReader read = new BufferedReader(new FileReader(fileInputName));
			while ((line = read.readLine()) != null) {
				if (line.length() != 0) {
					lineSplit = line.split("  ");

					if (lineSplit.length == 1) {
						if (boardSize == 0)
							boardSize = Integer.parseInt(String.valueOf(lineSplit[0]));
							//System.out.println(boardSize);
					} else if (lineSplit.length == boardSize) {
						for (int i = 0; i < lineSplit.length; i++) {
							tempArrayLine += lineSplit[i] + " ";
						}
						tempArrayLine += "/ ";
					}
				}
			}

			read.close();
       
		dummyBoard = new int[boardSize][boardSize];
        board = new int [boardSize][boardSize];
        fillArray();
        
        for (int i = 0; i < dummyBoard.length; i++) {
    		for (int j = 0; j < dummyBoard.length; j++) {
    			System.out.print(dummyBoard[i][j]);
    			} 
    		System.out.println();
    		}
        
    }catch(FileNotFoundException e){
        System.out.println("No File by that name was found.");
    }catch(IOException e){
        System.out.println("There was an error.");
    }
    
        Problem4 s = new Problem4(board,boardSize);
        s.solve();
    }
   

private static int convert(char s)
{
	int num = 0;
		switch (s)
		{
		case 'b': num=-1;
		break;
		
		case 'e': num=-2;
		break; 
		
		case 'o': num=-3;
		break;
		
		case 'w': num=-4;
		break;
		
		default: num=Integer.valueOf(s)-48;
		break;
		}
		
return num;
}

public static void fillArray() {
	  String [] tempArray = tempArrayLine.split(" / ");
      String [] tempMatrixLine = null;
      for(int i = 0; i< tempArray.length; i++){
          tempMatrixLine = tempArray[i].split(" ");
          
          for(int j=0; j< tempMatrixLine.length; j++){
                  board[i][j] = convert(tempMatrixLine[j].charAt(0));
                  
          }
          
      }
}
}
