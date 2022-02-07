import java.util.Scanner;
import java.util.function.IntPredicate;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.Buffer;

public class TictactoeTester {
    public static void main(String [] args){
        Scanner input = new Scanner(System.in);
        String inputFilename = "", outputFilename = "", line = "", finalString="";
        int boardSize = 0;

        System.out.println("Enter the name of the file you would like to read in: ");
        inputFilename = input.nextLine();

        System.out.println("Enter the name fo the file you would like top print the results to: ");
        outputFilename = input.nextLine();

        try{
            BufferedReader reader = new BufferedReader(new FileReader(inputFilename));

            while((line = reader.readLine()) != null){
                finalString+= line;
            }
            boardSize = Character.getNumericValue(finalString.charAt(finalString.length()-1));
            System.out.println(boardSize);
        }catch(FileNotFoundException e){
            System.out.println("No file by that name was found.");
        }catch(IOException e){
            System.out.println("And error occured" + e);
        }

        Problem3 p = new Problem3(boardSize);
        
    }
}
