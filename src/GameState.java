/**
 * this class represents a particular state of the game
 * it is majorly used for passing around the current state of the game between classes and
       to create a game tree in order to find a good next move
 */
public class GameState {

    public int choice;
    public int temp;

    private char currentPlayer;         //'g' or 'o'
    public final int dim = 7;           // dimension of the board

    public int pos ;                    //for drawing the moving coin on top of the board for the player to make choice
    private int rowOfLastcoin;          // row of the coin added last to the board
    public int aiPos;                   // column of the next good move

    public char[][] coins = new char[dim][dim];  // board matrix, ' ' if no coin, 'g' if green coin, 'o' if orange coin

    private char status;                         //' ' if ongoing game,'d' if draw,'g' if green wins,'o' if orange wins

    private int[][] arr;                // indices of all the coins in the winning move, to highlight the winning move

    // getter function that returns the current player
    public char getCurrentPlayer(){ return currentPlayer;}

    // getter function that returns the current status of the game
    public char getStatus(){ return status;  }

    /**
     * to make a copy of this instance
     * @param state -  this instance is copied into state
     */
    public void clone(GameState state){
        state.currentPlayer=this.currentPlayer;
        state.pos = this.pos;
        state.rowOfLastcoin = this.rowOfLastcoin;

        for(int i=0;i<dim;i++)
            System.arraycopy(this.coins[i], 0, state.coins[i], 0, dim);

        state.status=this.status;

        for(int i=0;i<dim;i++)
            System.arraycopy(this.arr[i], 0, state.arr[i], 0, 2);
    }

    //empties the coins array
    private void emptyBoard(){
        for(int i = 0 ; i < dim ; i++)
            for(int j = 0; j < dim ; j++)
                coins[i][j]=' ';
    }

    // resets array arr
    private void resetArr(){
        for(int i=0;i<dim;i++)
            for(int j=0;j<2;j++)
                arr[i][j]=-1;
    }

    /**
     * constructor of this class
     * initialises all variables in this class
     */
    public GameState(){
        choice = -1;
        temp = -1;
        aiPos = -1;
        //initialise coins to default array of black spaces
        emptyBoard();
        //first player to play is 'g'
        currentPlayer = 'g';
        //initialise pos
        pos = 0;
        status = ' ';
        arr = new int[dim][2];
        resetArr();
    }

    // swaps current player
    public void swapPlayer(){
        currentPlayer = currentPlayer == 'g' ? 'o' : 'g';
    }


    // adds coin in the 'pos' column
    public void updateCoins(){
        for(int i = dim-1 ; i >=0 ; i--)
            if(coins[i][pos]==' ') {
                coins[i][pos] = currentPlayer;
                rowOfLastcoin=i;
                break;
            }
        statusUpdate();
    }

    /**
     * replace coins in positions, stored in arr, with 'r' therefore highlighting the winning move
     * @param len - len of the winning move
     */
    private void highlightWin(int len){
        for(int i=0;i<len;i++)
            coins[arr[i][0]][arr[i][1]] = 'r';
    }

    // returns if the board is empty
    public boolean isEmptyBoard(){
        for(int i=0;i<dim;i++)
            if(coins[dim-1][i]!=' ')
                return false;
        return true;
    }

    // returns if the board, ie the coins arr, is full, therefore returns is its a draw
    public boolean checkIfBoardIsFull(){
        boolean isBoardFull = true;
        for(int i=0;i<dim;i++)
            isBoardFull = isBoardFull && (coins[0][i]!=' ');
        return isBoardFull;
    }

    // check is the move that's being made now is a winning move
    private boolean isWinningMove(){
        int count=0;
        char player = currentPlayer;

        // Horizontal check
        for (int i=0;i<dim;i++) {
            if (coins[rowOfLastcoin][i]==player) {
                arr[count][0]=rowOfLastcoin;
                arr[count][1]=i;
                count++;
            }
            else {
                resetArr();
                count = 0;
            }
            if (count>=4) {
                highlightWin(count);
                return true;
            }
        }
        count = 0;
        //Vertical check
        for (int i=0;i<dim;i++) {
            if (coins[i][pos]==player) {
                arr[count][0]=i;
                arr[count][1]=pos;
                count++;
            }
            else {
                resetArr();
                count = 0;
            }
            if (count>=4) {
                highlightWin(count);
                return true;
            }
        }
        count=0;
        // 4 in a row diagonally
        for(int i=pos,j=rowOfLastcoin;i<dim && j<dim;i++,j++) {
            if(coins[j][i]!=player)
                break;
            arr[count][0]=j;
            arr[count][1]=i;
            count++;
        }
        // 4 in a row diagonally
        for(int i=pos-1,j=rowOfLastcoin-1;i>=0 && j>=0;i--,j--) {
            if(coins[j][i]!=player)
                break;
            arr[count][0]=j;
            arr[count][1]=i;
            count++;
        }
        if (count>=4) {
            highlightWin(count);
            return true;
        }
        resetArr();
        count = 0;
        // 4 in a row diagonally
        for(int i=pos,j=rowOfLastcoin;i<dim && j>=0;i++,j--) {
            if(coins[j][i]!=player)
                break;
            arr[count][0]=j;
            arr[count][1]=i;
            count++;
        }
        // 4 in a row diagonally
        for(int i=pos-1,j=rowOfLastcoin+1;i>=0 && j<dim;i--,j++) {
            if(coins[j][i]!=player)
                break;
            arr[count][0]=j;
            arr[count][1]=i;
            count++;
        }
        if(count>=4)
            highlightWin(count);
        else
            resetArr();
        return count >= 4;
    }

    // returns is g (green) has won the game by doing this move
    private boolean gIsWin(){
        return (currentPlayer=='g' && isWinningMove());
    }

    // returns is o (orange) has won the game by doing this move
    private boolean oIsWin(){
        return (currentPlayer=='o' && isWinningMove());
    }

    // updates status of the game
    private void statusUpdate(){
        if(checkIfBoardIsFull())
            status='d';
        else if(gIsWin())
            status='g';
        else if(oIsWin())
            status='o';
    }

    // resets game
    public void resetGame(){
        pos = 0;
        emptyBoard();
        currentPlayer = 'g';
        status = ' ';
        choice = -1;
        temp = -1;
        aiPos = -1;
    }
}
