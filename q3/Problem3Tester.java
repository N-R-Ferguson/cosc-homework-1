import java.util.Scanner;
import java.util.function.IntPredicate;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.Buffer;

public class Problem3Tester {
    public static void main(String [] args){
        Scanner input = new Scanner(System.in);
        String outputFilename = "";
        int boardSize = 0;

        System.out.println("Enter the board size: ");
        boardSize = input.nextInt();

        //System.out.println("Enter the name fo the file you would like top print the results to: ");
        //outputFilename = input.nextLine();

        Problem3 p = new Problem3(boardSize);
        p.play();
    }
}
