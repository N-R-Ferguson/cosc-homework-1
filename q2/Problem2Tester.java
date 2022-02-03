import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;;
public class Problem2Tester{
    private static String tempArrayLine ="";
    protected static char [][] initial;
    private static int boardSize=0;

    public static void main(String [] args){
        String line, fileInputName = "";
        Scanner input;
        String [] lineSplit = null;
        
        
        input = new Scanner(System.in);
        System.out.println("Enter the name of the file you would like to read in (.txt is not needed): ");
        fileInputName = input.nextLine();
        input.close();

        try{
          
            BufferedReader read = new BufferedReader(new FileReader(fileInputName));
            while((line=read.readLine()) != null){
                if (line.length() != 0){
                    lineSplit = line.split(" ");

                    if(lineSplit.length == 1)
                        boardSize = Integer.parseInt(String.valueOf(lineSplit[0])); 
                    else if (lineSplit.length == boardSize){
                        for(int i=0; i< lineSplit.length; i++){
                            tempArrayLine += lineSplit[i] + " ";
                        }
                        tempArrayLine += "/ ";
                    }
                }
            }
            
             read.close();
           
            initial = new char [boardSize][boardSize];
            
            fillArray();
        }catch(FileNotFoundException e){
            System.out.println("No File by that name was found.");
        }catch(IOException e){
            System.out.println("There was an error.");
        }

        //Problem2 p = new Problem2(initial, boardSize);
        //p.solve();
    }  
    


    public static void fillArray(){
        String [] tempArray = tempArrayLine.split(" / ");
        String [] tempMatrixLine = null;
        for(int i = 0; i< tempArray.length; i++){
            tempMatrixLine = tempArray[i].split(" ");
            
            for(int j=0; j< tempMatrixLine.length; j++){
                    initial[i][j] = tempMatrixLine[j].charAt(0);
            }
            
        }

        for(int i=0; i< initial.length; i++){
            System.out.print("[");
            for(int j=0; j< initial[i].length; j++)
                System.out.print(initial[i][j]);
            System.out.print("]\n");
        }
        System.out.println("=======================");

        
    }
}