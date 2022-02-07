import java.util.LinkedList;
import java.util.Arrays;
import java.util.Comparator;
public class Problem2 {
    
    private class Board{
        private char[][] array;
        private int gvalue, hvalue, fvalue;
        private Board parent;

        private Board(char [][]arr, int size){
            this.array = new char[size][size];
  
            for(int i = 0; i < size; i++){
                for(int j = 0; j < size; j++){
                    this.array[i][j] = arr[i][j];
                   
                }
            }
            
            this.hvalue = 0;
            this.gvalue = 0;
            this.fvalue = 0;
            this.parent = null;
        }
    }


    private Board initial;
    private Board goal;
    private int size;
    
    public Problem2(char [][] i, int sz){
        this.size = sz;
        System.out.println(size);
        this.initial = new Board(i, size);
        //for (int a = 0; a < size; a++)
		//		System.out.println(this.initial.array[a]);
        //displayBoard(initial);
        this.goal = createGoal(this.initial);
    }

    private Board createGoal(Board board){
        
        Board goal = new Board(board.array, size); // creates a board goal
                                                // that contains the same values as the initial
                                                //but can be sorted to create the final goal board.
        
        for(int i=0; i< size; i++){
            for(int j = 0; j < size; j++){
                if((int)goal.array[i][j] == 71)
                    goal.array[i][j] = Character.toLowerCase(goal.array[i][j]);
            }
            
        }
        char temp = 0;
        for(int l = 0; l < size; l++){
            for(int k = 0; k < size; k++){

                for(int i = 0; i < size; i++){
                    
                    for(int j = 0; j < size; j++){
                        if((int)goal.array[l][k] < (int)goal.array[i][j]){
                            temp = goal.array[l][k];
                            goal.array[l][k] = goal.array[i][j];
                            goal.array[i][j] = temp;
                        }
                    }
                }
            }
        }

        for(int i=0; i< size; i++){
            for(int j = 0; j < size; j++){
                if((int)goal.array[i][j] == 103)
                    goal.array[i][j] = Character.toUpperCase(goal.array[i][j]);
                
            }
        }

        displayBoard(goal);
        
        return goal;
    }

    public void solve(){
        
        LinkedList<Board> openList = new LinkedList<Board>();
        LinkedList<Board> closedList = new LinkedList<Board>();

        openList.addFirst(initial);

        while(!openList.isEmpty()){

            int best = selectBest(openList);

            Board board = openList.remove(best);

            closedList.addLast(board);

            if(goal(board)){
                displayPath(board);
                return;
            }else{
                LinkedList<Board> children = generate(board);

                for(int i = 0; i < children.size(); i++){
                    Board child = children.get(i);

                    if(!exists(child, closedList)){
                        if(!exists(child, openList)){
                            openList.addLast(child);
                        }else{
                            int index = find(child, openList);
                            if(child.fvalue < openList.get(index).fvalue){
                                openList.remove(index);
                                openList.addLast(child);
                            }
                        }
                    }
                }
            }
        }
    }


    /*
        NEEDS TO BE WRITTEN
    */
    private LinkedList<Board> generate(Board board){
        LinkedList<Board> g = new LinkedList<Board>();
        return g;
    }
    /*
        NEEDS TO BE WRITTEN
    */
    private Board createChild(Board board, int i, int j, char direction){
        Board child = copy(board);

       
        return child;
    }

    private int mismatch(Board board){
        int value = 0;

        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                if(board.array[i][j] != goal.array[i][j]) 
                value += 1;

        return value;
    }

    private int selectBest(LinkedList<Board> list){
        int minValue = list.get(0).fvalue;
        int minIndex = 0;

        for(int i = 0; i < list.size(); i++){
            int value = list.get(i).fvalue;
            if(value < minValue){
                minValue = value;
                minIndex = i;
            }
        }
        return minIndex;
    }

    private Board copy(Board board){
        return new Board(board.array, size);
    }

    private boolean goal(Board board){
        return identical(board, goal);
    }

    private boolean exists(Board board, LinkedList<Board> list){
        for(Board element: list)
            if(identical(board, element)) return true;
        return false;
    }

    private int find(Board board, LinkedList<Board> list) {
		for (int i = 0; i < list.size(); i++) // compare board with each
			if (identical(board, list.get(i))) // element of list
				return i;

		return -1;
	}

    private boolean identical(Board q, Board p){
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                if(p.array[i][j] != q.array[i][j]) 
                return false;

        return true;
    }

    private void displayPath(Board board){
        LinkedList<Board> list = new LinkedList<Board>();

        Board pointer = board;

        while(pointer != null){
            list.addFirst(pointer);

            pointer = pointer.parent;
        }

        for(Board element: list)
            displayBoard(element);
    }

    private void displayBoard(Board board){

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++)
                System.out.print(board.array[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }
}
