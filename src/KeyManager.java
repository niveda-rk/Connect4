import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class listens to the keyboard keys that are being pressed and takes actions based on that
 */
public class KeyManager implements KeyListener {
    private GameState state;   // current state of the game
    private int a,d,left,right,space,enter,shift;

    public KeyManager(GameState state){
        this.state = state;
        a=97;
        d=100;
        left = 37;
        right = 39;
        space = 32;
        enter = 10;
        shift = 16;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int key = e.getKeyChar();
        if(state.getStatus()==' ') {
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

            if (key == enter || key == space) {
                state.updateCoins();
                state.swapPlayer();
                state.pos = 0;
                while (state.pos < state.dim && state.coins[0][state.pos] != ' ')
                    state.pos++;
            }
        }
        else if(key == enter || key == space || key == shift){
            state.resetGame();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(state.getStatus()==' ') {
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

            if (key == shift && state.getPosOfNextGoodMove()==-1) {
                GameTree tree = new GameTree(state, 0);
                state.setPosOfNextGoodMove(tree.returnNextGoodMove());
                state.addGoodMove();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }
}
