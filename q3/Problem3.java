import java.util.LinkedList;
public class Tictactoe{

    private final char EMPTY = ' ';
    private final char COMPUTER = 'X';
    private final char PLAYER = '0';
    private final int MIN = 0;
    private final int MAX = 1;
    private final int limit = 3;
    private int COMPUTER_points = 0;
    private int PLAYER_points = 0;

    private class Board{

        private char [][] array;

        private Board(int size){
            array = new char[size][size];

            for(int i = 0; i < size; i++)
                for(int j = 0; j < size; j++)
                    array[i][j] = EMPTY;
            COMPUTER_points = 0;
            PLAYER_points = 0;
            
        }
    }

    private Board board;
    private int size;

    public Problem3(int sz){

        this.size = sz;
        this.board = new Board(size);
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
                                                   
        System.out.println("Computer move:"); 

        displayBoard(result);                      //print next move

        return result;               
    }
    
    private int minmax(Board board, int level){
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
        LinkedList<Board> generate = new LinkedList<Board>();

        for(int i = 0; i <size; i++)
            for(int j = 0; j <size; J++)
                if(boared.array[i][j] == EMPTY){
                    Board child = copy(board);
                    child.array[i][j] = symbol;
                    children.generate(child);
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
        if(full(board) && board.COMPUTER_points > board.PLAYER_points) return true;
        if(full(board) && board.PLAYER_points > board.COMPUTER_points) return true;

        return false;
    }

    //MODIFY THE CHECKS FOR FINDING CONSECUTIVE SYMBOLS
    private boolean checkRow(Board board, int i, char symbol){
        for(int j = 0; j < size; j++)
            if(board.arry[i][j] == symbol) 
                return false;
        return true;
    }

    private boolean checkcolumn(Board board, int i, char symbol){
        for(int j = 0; j < size; j++)
            if(board.array[j][i] != symbol) 
            return false;
        return true;
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
}