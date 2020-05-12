import java.awt.*;
import java.awt.image.BufferStrategy;

public class DisplayBoard {
    private int width;        //width of the canvas/ window
    private int height;       // height of the canvas/window

    private int wOfBoard;     // width of the board
    private int startX = 70;  // starting index of the x coordinate of the board
    private int hOfBoard;     // height of the board
    private int startY = 70;  // starting index of the y coordinate of the board

    private GameState state;  // current state of the game

    private Graphics g;       // to draw various features in the board

    /**
     * constructor of this class
     * initialises all variables in this class for given height and width of canvas
     */
    public DisplayBoard(int width, int height){
        this.width = width;
        this.height = height;
        wOfBoard = width - 2*startX;
        hOfBoard = width - 2*startY;

        state = new GameState();
    }

    // sets colour and message based on the status of the game
    private String retStatusAndSetColor(){
        if(state.getStatus()=='d') {
            g.setColor(Color.blue);
            return "IT'S A DRAW!!! NEW GAME? Press ENTER to continue";
        }
        else if(state.getStatus()=='g') {
            g.setColor(Color.green);
            return "GREEN WINS!!! NEW GAME? Press ENTER to continue";
        }
        else if(state.getStatus()=='o') {
            g.setColor(Color.orange);
            return "ORANGE WINS!!! NEW GAME? Press ENTER to continue";
        }
        else {
            g.setColor( state.getCurrentPlayer() == 'g' ? Color.green : Color.orange);
            String str = ((state.getCurrentPlayer() == 'g' ? "Green" : "Orange") + "'s chance" );
            if(state.choice==2) {
                if (state.isEmptyBoard())
                    str = str + ".Press SPACE for Comp's chance";
                else if (state.getCurrentPlayer() == 'o' && state.aiPos != -1)
                    str = str + ".Coin dropped in column " + (state.aiPos + 1) ;
            }else if (state.getCurrentPlayer() == 'g' && state.aiPos !=  -1)
                str = str + ".Coin dropped in column " + (state.aiPos + 1) ;
            if(!state.isEmptyBoard() || state.getCurrentPlayer()=='g')
                str = str + ".Press SPACE to drop coin";
            return str;
        }
    }

    //Prints which players chance it is
    private void renderStatus(){
        String lineToPrint = retStatusAndSetColor();
        g.setFont( new Font("Monospaced", Font.BOLD , 14));
        g.drawString(lineToPrint, 20, height-20);
    }

    // Prints the moving coins indicating where the coin will go when enter is being pressed
    private void renderFloatingCoin(){
        g.setColor(state.getCurrentPlayer()=='g'? Color.green : Color.orange);
        g.fillOval(startX+state.pos*wOfBoard/state.dim + 5,
                startY-hOfBoard/state.dim + 5,
                wOfBoard/state.dim - 10,
                hOfBoard/state.dim - 10);
    }

    //Colours board in blue,helper function for renderBoard()
    private void fillBoard(){
        g.setColor(Color.blue);
        g.fillRect( startX , startY , wOfBoard , hOfBoard);
    }

    //Decides the Colour for each coin with green,orange or nothing, helper function for drawHolesInBoard()
    private void colorCoin(int i,int j){
        switch(state.coins[i][j]){
            case ' ':   g.setColor(Color.black);
                break;
            case 'g':   g.setColor(Color.green);
                break;
            case 'o':   g.setColor(Color.orange);
                break;
            case 'r':   g.setColor(Color.red);
                break;
        }
    }

    //Draws the holes in the board, helper function for renderBoard()
    private void drawHolesInBoard(){

        int x = startX;
        int y = startY;

        for(int i = 0 ; i < state.dim ; i++ , y += hOfBoard/state.dim , x = startX)
            for(int j = 0 ; j < state.dim ; j++ , x += wOfBoard/state.dim) {
                colorCoin(i,j);
                g.fillOval(x + 5, y + 5, wOfBoard / state.dim - 10, hOfBoard / state.dim - 10);
            }
    }

    //Prints Board for Connect4, helper function for render()
    private void renderBoard(){
        fillBoard();
        drawHolesInBoard();
    }

    /**
     * Prints everything
     * @param canvas - canvas which the board is to be drawn on
     * @param s - current state of the game
     */
    public void render(Canvas canvas,GameState s){
        BufferStrategy bs = canvas.getBufferStrategy();
        state = s;
        if(bs == null){
            canvas.createBufferStrategy(3);
            return;
        }

        g= bs.getDrawGraphics();
        //Clear Screen
        g.clearRect(0,0,width,height);
        //Draw here
        g.setColor(Color.black);
        g.fillRect(0,0,width,height);
        if(state.getStatus()!=' ')
            renderStatus();
        else {
            renderStatus();
            renderFloatingCoin();
        }
        renderBoard();

        //End drawing here
        bs.show();
        g.dispose();
    }
}
