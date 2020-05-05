public class GameLoop implements Runnable{

    // for listening to the keys pressed on the keyboard by the user
    private KeyManager km;
    // to run and display continuously
    private Thread thread;
    // if the program is running or not
    private boolean running ;

    // used to display the connect4 board
    private GameDisplay g;
    // it's the current state of the game
    private GameState state;

    // initialise some local variables in this instance
    public GameLoop(){
        running = false;
        state = new GameState();
        km = new KeyManager(state);
    }

    // start function to display all objects
    public synchronized void start(){
        if(running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    // stop function to stop display
    public synchronized  void stop(){
        if(!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // to make everything ready before we start displaying the board onto the screen
    private void init(){
        g = new GameDisplay("Connect4",600,600);
        g.getFrame().addKeyListener(km);
    }

    // the game loop
    public void run(){
        init();

        while(running)
            // display the board of the current state
            g.display(state);

        stop();
    }
}
