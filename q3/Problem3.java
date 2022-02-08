import java.util.LinkedList;
import java.util.Scanner;
public class Problem3{

    private final char EMPTY = ' ';
    private final char COMPUTER = 'X';
    private final char PLAYER = '0';
    private final int MIN = 0;
    private final int MAX = 1;
    private final int LIMIT = 5;


    private class Board{

        private char [][] array;
        private int COMPUTER_points = 0;
        private int PLAYER_points = 0;
        private int COMPUTER_consecutives = 0;
        private int PLAYER_consecutives = 0;

        private Board(int size){
            array = new char[size][size];

            for(int i = 0; i < size; i++)
                for(int j = 0; j < size; j++)
                    array[i][j] = EMPTY;

            COMPUTER_points = 0;
            COMPUTER_consecutives = 0;
            PLAYER_points = 0;
            PLAYER_consecutives = 0;

            

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
            this.points = 2 * this.twoConsecutives + 3 * this.threeConsecutives;
            return this.points;
        }
        private int calculateNumberConsecutives(){
            return this.twoConsecutives + this.threeConsecutives;
        }
    }

    private Board board;
    private int size;
    private Player computer;
    private Player player; // human player

    public Problem3(int sz){

        this.size = sz;
        this.board = new Board(size);
        this.computer = new Player();
        this.player = new Player();
        displayBoard(board);
    }

    public void play(){
        while (!full(board))                               //turns are taken until the board is full
        {
            board = playerMove(board);             //player makes a move

            board = computerMove(board);           //computer makes a move
        }
        if (playerWin(board))                  // check to see if player has a higher score
        {
            System.out.println("Player wins");
            
        }
        else if (computerWin(board))                  // check to see if computer has a higher score
        {
            System.out.println("Computer wins");
        }

        else if (draw(board))                       // check to see if the scores are then same
        {
            System.out.println("Draw");
        }
        
    }

    private Board playerMove(Board board){
        System.out.print("Player move: ");         // ask for players more
     
        Scanner scanner = new Scanner(System.in);  
        int i = scanner.nextInt();                 // Player enters row location
        int j = scanner.nextInt();                 // Player enters column location

        board.array[i][j] = PLAYER;                

        displayBoard(board);                       //diplay the current game board

        return board;
    }

    private Board computerMove(Board board){
        LinkedList<Board> children = generate(board, COMPUTER);

        int maxIndex = -1;
        int maxValue = Integer.MIN_VALUE;
                                                   //find the child with
        for (int i = 0; i < children.size(); i++)  //largest minmax value
        {
            int currentValue = minmax(children.get(i), MIN, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (currentValue > maxValue)
            {
                maxIndex = i;
                maxValue = currentValue;
            }
        }

        Board result = children.get(maxIndex);     //choose the child as next move
                                              

        System.out.println("Computer move:"); 

        displayBoard(result);                      //print next move

        return result;               
    }
    
    private int minmax(Board board, int level, int depth, int alpha, int beta){
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
                    int currentValue = minmax(children.get(i), MIN, depth+1, alpha, beta);

                    if (currentValue > maxValue)
                        maxValue = currentValue;

                    if(maxValue >= beta)
                        return maxValue;
                    
                    if(maxValue > alpha)
                        alpha = maxValue;
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
                    int currentValue = minmax(children.get(i), MAX, depth+1, alpha, beta);

                    if (currentValue < minValue)
                        minValue = currentValue;
                    if(minValue <= alpha)
                        return minValue;
                    if(minValue < beta)
                        beta = minValue;
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

                    player.twoConsecutives = 0;
                    player.threeConsecutives = 0;
                    computer.twoConsecutives = 0;
                    computer.threeConsecutives = 0;

                    for(int k = 0; k < size; k++){
                        checkRow(child, k, COMPUTER);
                        checkColumn(child, k, COMPUTER);
                        checkRow(child, k, '0');
                        checkColumn(child, k, '0');
                    }

                    child.COMPUTER_consecutives = computer.calculateNumberConsecutives();
                    child.PLAYER_consecutives = player.calculateNumberConsecutives();
                    child.COMPUTER_points = computer.calculateScore();
                    child.PLAYER_points = player.calculateScore();
                    
                    children.addLast(child);
                }

        return children;
    }

    private boolean computerWin(Board board){
        return full(board) && (board.PLAYER_points < board.COMPUTER_points);
    }

    private boolean playerWin(Board board){
        return full(board) && (board.PLAYER_points > board.COMPUTER_points);
    }

    private boolean draw(Board board){
        return full(board) && !computerWin(board) && !playerWin(board);
    }

    private boolean check(Board board, char symbol){ //checks to see who has more points
        if(full(board) && (board.PLAYER_points > board.COMPUTER_points)){
            
            return true;
        }
        else if(full(board) && (board.PLAYER_points - board.COMPUTER_points < 0)) {
            return true;
        }
        return false;
    }

    //MODIFY THE CHECKS FOR FINDING CONSECUTIVE SYMBOLS
    private void checkRow(Board board, int i, char symbol){
        
        int consecutiveSymbolCount = 0;
        for(int j = 0; j < size; j++){
            if(board.array[i][j] != symbol){
                consecutiveSymbolCount = 0;
            }
            else if(board.array[i][j] == symbol){
                consecutiveSymbolCount++;
                
                if(consecutiveSymbolCount == 2){
                    if(symbol == COMPUTER)
                        computer.twoConsecutives += 1;
                    else
                        player.twoConsecutives += 1;
                }else if(consecutiveSymbolCount >= 3){
                    if(symbol == COMPUTER){
                        computer.twoConsecutives += 1;
                        computer.threeConsecutives += 1;
                    }
                    else{
                        player.twoConsecutives += 1;
                        player.threeConsecutives += 1;
                    }
                }
            }
        }
    }

    private void checkColumn(Board board, int i, char symbol){
        int consecutiveSymbolCount = 0;

        for(int j = 0; j < size; j++){
            if(board.array[j][i] != symbol){
                consecutiveSymbolCount = 0;
            }
            else if(board.array[j][i] == symbol){
                consecutiveSymbolCount++;

                if(consecutiveSymbolCount == 2){
                    if(symbol == COMPUTER)
                        computer.twoConsecutives += 1 ;
                    else
                        player.twoConsecutives += 1;
                }else if(consecutiveSymbolCount >= 3){
                    if(symbol == COMPUTER){
                        computer.twoConsecutives += 1;
                        computer.threeConsecutives += 1;
                    }
                    else{
                        player.twoConsecutives += 1;
                        player.threeConsecutives += 1;
                    }
                }
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
        
        // player.twoConsecutives = 0;
        // player.threeConsecutives = 0;
        // computer.twoConsecutives = 0;
        // computer.threeConsecutives = 0;
        // for(int i = 0; i < size; i++){
        //     checkRow(board, i, COMPUTER);
        //     checkColumn(board, i, COMPUTER);
        //     checkRow(board, i, '0');
        //     checkColumn(board, i, '0');
        // }
        
        System.out.println(board.PLAYER_points);
        System.out.println(board.COMPUTER_points);

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if (j < size - 1 )
                    System.out.print(" " + board.array[i][j] + " |");

                else
                    System.out.print(" " + board.array[i][j]);
            }
            if(i < size - 1){
                System.out.print("\n --+");
                for(int k = 0; k< size - 3; k++){
                    System.out.print("---+");
                }
                System.out.print("---+--\n");
            }
        }
        System.out.println();
    }

    private int evaluate(Board board)
    {
        if (computerWin(board) || board.COMPUTER_consecutives > board.PLAYER_consecutives)                    
            return 4 * size;
        else if (playerWin(board) || board.COMPUTER_consecutives < board.PLAYER_consecutives)                 
            return -4 * size;
        else if (draw(board))                     
            return 3 * size;
        else                                       
            return count(board, COMPUTER) - count(board, PLAYER);

    } 

    public int count(Board board, char symbol){
        int answer = 0;

        player.twoConsecutives = 0;
        player.threeConsecutives = 0;
        computer.twoConsecutives = 0;
        computer.threeConsecutives = 0;

        for(int i = 0; i < size; i++){
            
            answer += testRow(board, i, symbol);
        }
        for(int i = 0; i < size; i++){
            
            answer += testColumn(board, i, symbol);
        }

        if(symbol == 'X')
            answer = computer.calculateScore();
        else
            answer = player.calculateScore();

        return answer;
    }

    public int testRow(Board board, int i, char symbol){
        int numberEmpty=0;
        int numberOfSymbolsInRow = 0;
        for(int j = 0; j < size; j++){
            if(board.array[j][i] == symbol)
                numberOfSymbolsInRow++;
            else if(board.array[j][i] == ' ')
                numberEmpty++;
            else{
                numberOfSymbolsInRow = 0;
            }
        }
        int answer  = numberEmpty + numberOfSymbolsInRow;
        if(answer == 2){
            if(symbol == COMPUTER)
                computer.twoConsecutives += 1;
            else
                player.twoConsecutives += 1;
        }else if(answer >= 3){
            if(symbol == COMPUTER){
                computer.twoConsecutives += 1;
                computer.threeConsecutives += 1;
            }
            else{
                player.twoConsecutives += 1;
                player.threeConsecutives += 1;
            }
        }
        return answer;
    }

    public int testColumn(Board board, int i, char symbol){
        int numberEmpty=0;
        int numberOfSymbolsInColumn = 0;
        for(int j = 0; j < size; j++){
            if(board.array[j][i] == symbol)
                numberOfSymbolsInColumn++;
            else if(board.array[j][i] == ' ')
                numberEmpty++;
            else{
                numberOfSymbolsInColumn = 0;
            }
        }
        int answer  = numberEmpty + numberOfSymbolsInColumn;

        if(answer == 2){
            if(symbol == COMPUTER)
                computer.twoConsecutives += 1;
            else
                player.twoConsecutives += 1;
        }else if(answer >= 3){
            if(symbol == COMPUTER){
                computer.twoConsecutives += 1;
                computer.threeConsecutives += 1;
            }
            else{
                player.twoConsecutives += 1;
                player.threeConsecutives += 1;
            }
        }

        return answer;
    }
}