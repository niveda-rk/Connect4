import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class listens to the keyboard keys that are being pressed and takes actions based on that
 */
public class KeyManager implements KeyListener {
    private GameState state;   // current state of the game
    private int a,d,left,up,right,down,space,enter,shift;

    public KeyManager(GameState state){
        this.state = state;
        a=97;
        d=100;
        left = 37;
        up = 38;
        right = 39;
        down = 40;
        space = 32;
        enter = 10;
        shift = 16;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int key = e.getKeyChar();

        if (state.getStatus() == ' ' && state.choice!=-1) {
            if (key == left || key == a)
                do {
                    if (state.pos != 0)
                        state.pos--;
                    else
                        state.pos = state.dim - 1;
                } while (state.coins[0][state.pos] != ' ');

            if (key == right || key == d)
                do {
                    if (state.pos != (state.dim - 1))
                        state.pos++;
                    else
                        state.pos = 0;
                } while (state.coins[0][state.pos] != ' ');

            if (key == space) {
                if (state.isEmptyBoard() && state.choice == 2) {
                    GameTree tree = new GameTree(state, 0);
                    state.pos = tree.returnNextGoodMove();
                    state.updateCoins();
                    state.swapPlayer();
                    state.aiPos = state.pos;
                    return;
                }
                state.updateCoins();
                state.swapPlayer();
                if (state.getStatus() == ' ' && state.choice != 0) {
                    GameTree tree = new GameTree(state, 0);
                    state.pos = tree.returnNextGoodMove();
                    state.updateCoins();
                    state.swapPlayer();
                }
                state.pos = 0;
                while (state.pos < state.dim && state.coins[0][state.pos] != ' ')
                    state.pos++;
            }
        } else if (key == enter ) {
            state.resetGame();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(state.choice==-1){
            if(key == up){
                if(state.temp == -1 || state.temp == 0)
                    state.temp = 2;
                else state.temp--;
            }
            else if(key == down){
                if(state.temp!=2)
                    state.temp ++;
                else state.temp=0;
            }else if(key == enter){
                state.choice = state.temp;
            }
        }else {
            if (state.getStatus() == ' ') {
                if (key == left)
                    do {
                        if (state.pos != 0)
                            state.pos--;
                        else
                            state.pos = state.dim - 1;
                    } while (state.coins[0][state.pos] != ' ');


                if (key == right)
                    do {
                        if (state.pos != (state.dim - 1))
                            state.pos++;
                        else
                            state.pos = 0;
                    } while (state.coins[0][state.pos] != ' ');

            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }
}
