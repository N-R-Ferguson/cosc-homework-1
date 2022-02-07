import java.util.LinkedList;
import java.util.Scanner;
public class Problem3{

    private final char EMPTY = ' ';
    private final char COMPUTER = 'X';
    private final char PLAYER = '0';
    private final int MIN = 0;
    private final int MAX = 1;
    private final int LIMIT = 3;
    private int COMPUTER_points = 0;
    private int PLAYER_points = 0;

    private class Board{

        private char [][] array;

        private Board(int size){
            array = new char[size][size];

            for(int i = 0; i < size; i++)
                for(int j = 0; j < size; j++)
                    array[i][j] = EMPTY;



        }
    }

    private class Player{
        private int twoConsecutives;
        private int threeConsecutives;
        private int points;

        private Player(){
            this.twoConsecutives = 0;
            this.threeConsecutives = 0;
            this.points = 0;
        }

        private int calculateScore(){
            //System.out.println(twoConsecutives);
            this.points = 2 * this.twoConsecutives + 3 * this.threeConsecutives;
            return this.points;
        }
    }

    private class Computer{
        private int twoConsecutives;
        private int threeConsecutives;
        private int points;

        private Computer(){
            this.twoConsecutives = 0;
            this.threeConsecutives = 0;
            this.points = 0;
        }

        private int calculateScore(){
            //System.out.println(twoConsecutives);
            this.points = 2 * this.twoConsecutives + 3 * this.threeConsecutives;
            return this.points;
        }
    }

    private Board board;
    private int size;
    private Computer computer;
    private Player player; // human player

    public Problem3(int sz){

        this.size = sz;
        this.board = new Board(size);
        this.computer = new Computer();
        this.player = new Player();
        displayBoard(board);
    }

    public void play(){
        while (true)                               //computer and player take turns
        {
            board = playerMove(board);             //player makes a move

            if (playerWin(board))                  //if player wins then game is over
            {
                System.out.println("Player wins");
                break;
            }

            if (draw(board))                       //if draw then game is over
            {
                System.out.println("Draw");
                break;
            }

            board = computerMove(board);           //computer makes a move

            if (computerWin(board))                //if computer wins then game is over
            {                      
                System.out.println("Computer wins");
                break;
            }

            if (draw(board))                       //if draw then game is over
            {
                System.out.println("Draw");
                break;
            }
        }
    }

    private Board playerMove(Board board){
        System.out.print("Player move: ");         //prompt player
     
        Scanner scanner = new Scanner(System.in);  //read player's move
        int i = scanner.nextInt();
        int j = scanner.nextInt();

        board.array[i][j] = PLAYER;                //place player symbol

        for(int a = 0; a < size; a++){
            checkRow(board, a, 'X');
            checkColumn(board, a, 'X');
            checkRow(board, a, '0');
            checkColumn(board, a, '0');
        }
        System.out.println(player.calculateScore());
        System.out.println(computer.calculateScore());
        displayBoard(board);                       //diplay board

        return board;
    }

    private Board computerMove(Board board){
        LinkedList<Board> children = generate(board, COMPUTER);

        int maxIndex = -1;
        int maxValue = Integer.MIN_VALUE;
                                                   //find the child with
        for (int i = 0; i < children.size(); i++)  //largest minmax value
        {
            int currentValue = minmax(children.get(i), MIN, 1);
            if (currentValue > maxValue)
            {
                maxIndex = i;
                maxValue = currentValue;
            }
        }

        Board result = children.get(maxIndex);     //choose the child as next move
                                              
        for(int i = 0; i < size; i++){
            checkRow(board, i, 'X');
            checkColumn(board, i, 'X');
            checkRow(board, i, '0');
            checkColumn(board, i, '0');
        }
        System.out.println("Computer move:"); 
        System.out.println(player.calculateScore());
        System.out.println(computer.calculateScore());
        displayBoard(result);                      //print next move

        return result;               
    }
    
    private int minmax(Board board, int level, int depth){
        if (computerWin(board) || playerWin(board) || draw(board) || depth >= LIMIT)
            return evaluate(board);                //if board is terminal or depth limit is reached
        else                                       //evaluate board
        {
            if (level == MAX)                      //if board is at max level     
            {
                LinkedList<Board> children = generate(board, COMPUTER);
                                                //generate children of board
                int maxValue = Integer.MIN_VALUE;
                                                //find maximum of minmax value of children
                for (int i = 0; i < children.size(); i++)
                {                
                    int currentValue = minmax(children.get(i), MIN, depth+1);

                    if (currentValue > maxValue)
                        maxValue = currentValue;
                }

                return maxValue;                  //return maximum minmax value             
            }
            else                                   //if board is at min level
            {                     
                LinkedList<Board> children = generate(board, PLAYER);
                                                //generate children of board
                int minValue = Integer.MAX_VALUE;
                                                //find minimum of minmax values of children
                for (int i = 0; i < children.size(); i++)
                {
                    int currentValue = minmax(children.get(i), MAX, depth+1);

                    if (currentValue < minValue)
                        minValue = currentValue;
                }

                return minValue;                  //return minimum minmax value  
            }
        }
    }

    private LinkedList<Board> generate(Board board, char symbol){
        LinkedList<Board> children = new LinkedList<Board>();

        for(int i = 0; i <size; i++)
            for(int j = 0; j <size; j++)
                if(board.array[i][j] == EMPTY){
                    Board child = copy(board);
                    child.array[i][j] = symbol;
                    children.addLast(child);
                }
            

        return children;
    }

    private boolean computerWin(Board board){
        return check(board, COMPUTER);
    }

    private boolean playerWin(Board board){
        return check(board, PLAYER);
    }

    private boolean draw(Board board){
        return full(board) && !computerWin(board) && !playerWin(board);
    }

    private boolean check(Board board, char symbol){ //checks to see who has more points
        player.twoConsecutives = 0;
        player.threeConsecutives = 0;
        computer.twoConsecutives = 0;
        computer.threeConsecutives = 0;
        
        if(full(board) && computer.calculateScore() > player.calculateScore()) return true;
        if(full(board) && player.calculateScore() > computer.calculateScore()) return true;

        return false;
    }

    //MODIFY THE CHECKS FOR FINDING CONSECUTIVE SYMBOLS
    private void checkRow(Board board, int i, char symbol){
        int consecutiveSymbolCount = 0;
        for(int j = 0; j < size; j++){
            if(board.array[i][j] != symbol) 
                break;
            consecutiveSymbolCount++;
           
        }
        if(consecutiveSymbolCount == 2){
            if(symbol == 'X')
                computer.twoConsecutives++;
            else
                player.twoConsecutives++;
        }else if(consecutiveSymbolCount == 3){
            if(symbol == 'X'){
                computer.twoConsecutives += 2;
                computer.threeConsecutives++;
            }
            else{
                player.twoConsecutives += 2;
                player.threeConsecutives++;
            }
        }else if(consecutiveSymbolCount > 3){
            if(symbol == 'X'){
                computer.twoConsecutives = consecutiveSymbolCount - 1;
                computer.threeConsecutives = consecutiveSymbolCount - 2;
            }else{
                player.twoConsecutives = consecutiveSymbolCount - 1;
                player.threeConsecutives = consecutiveSymbolCount - 2;

            }

        }
    }

    private void checkColumn(Board board, int i, char symbol){
        int consecutiveSymbolCount = 0;
        for(int j = 0; j < size; j++){
            if(board.array[j][i] != symbol) 
                break;
            consecutiveSymbolCount++;
            System.out.println(consecutiveSymbolCount);
        }
        if(consecutiveSymbolCount == 2){
            if(symbol == 'X')
                computer.twoConsecutives++;
            else
                player.twoConsecutives++;
        }else if(consecutiveSymbolCount == 3){
            if(symbol == 'X'){
                computer.twoConsecutives += 2;
                computer.threeConsecutives++;
            }
            else{
                player.twoConsecutives += 2;
                player.threeConsecutives++;
            }
        }else if(consecutiveSymbolCount > 3){
            if(symbol == 'X'){
                computer.twoConsecutives = consecutiveSymbolCount - 1;
                computer.threeConsecutives = consecutiveSymbolCount - 2;
            }else{
                player.twoConsecutives = consecutiveSymbolCount - 1;
                player.threeConsecutives = consecutiveSymbolCount - 2;

            }
        }
    }


    private boolean full(Board board){
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                if(board.array[i][j] == EMPTY)return false;
        return true;
    }

    private Board copy(Board Board){
        Board result = new Board(size);

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                result.array[i][j] = board.array[i][j];
            }
        }

        return result;
    }

    private void displayBoard(Board board){
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if (j < size - 1 )
                    System.out.print(" " + board.array[i][j] + " |");

                else
                    System.out.print(" " + board.array[i][j]);
            }
            if(i < size - 1)
                System.out.println("\n --+---+---+--");
        }
        System.out.println();
    }

    private int evaluate(Board board)
    {
        if (computerWin(board))                    //utility is 4 if computer wins
            return 4;
        else if (playerWin(board))                 //utility is 1 if player wins
            return 1;
        else if (draw(board))                      //utility is 3 if draw
            return 3;
        else                                       //utility is 2 if depth limit is reached
            return 2;
    } 
}