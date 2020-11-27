import javax.swing.*;
import java.awt.*;

public class GameDisplay {
    private int width;   // width of window/canvas
    private int height;   // height of window/canvas
    private String title ;  // title given to the window/canvas

    private JFrame frame;   // frame of the canvas on which the board is to be draw
    private Canvas canvas; // canvas on which the board is gonna be drawn

    private DisplayBoard disb;  // used to display various features in the connect 4 board
    private DisplayMenu dism;   // used to display the main menu of the game

    /**
     * creates canvas to draw the board on it
     * @param title - title given to the window/canvas
     * @param width - width of window/canvas
     * @param height - height of window/canvas
     */
    public GameDisplay(String title, int width, int height){
        this.width = width;
        this.height = height;
        this.title = title;

        disb = new DisplayBoard(width,height);
        dism = new DisplayMenu(width,height);

        createDisplay();
    }

    /**
     * helper function for constructor
     * creates canvas
     */
    private void createDisplay() {
        frame = new JFrame(title);
        frame.setSize(width,height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width,height));
        canvas.setMaximumSize(new Dimension(width,height));
        canvas.setMinimumSize(new Dimension(width,height));
        canvas.setFocusable(false);

        frame.add(canvas);
        frame.pack();
    }

    /**
     * getter function for frame
     * @return - frame of canvas
     */
    public JFrame getFrame(){
        return frame;
    }

    /**
     * displays the board on given canvas for the given state
     * @param state current state of the game
     */
    public void display(GameState state){
        if(state.choice==-1)
            dism.render(canvas,state.temp);
        else
            disb.render(canvas,state);
    }
}
