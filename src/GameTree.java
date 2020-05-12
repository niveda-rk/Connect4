import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class gives a good next move
 * Uses the minimax search algorithm
 * Sees 6 moves ahead
 */
public class GameTree {
    private GameState currentState;          // the current state of the game
    private List<GameTree> nextMoves = new ArrayList<>();    // all possible next moves

    private int layerNo;    // current layer number in the tree made
    private int score;      // score in current node in game tree


    // returns the column number, when the coin is dropped in that returned column, its the best possible move
    public int returnNextGoodMove(){
        List<GameTree> temps = new ArrayList<>();
        for(GameTree nextMove:nextMoves)
            if(score == nextMove.score)
                temps.add(nextMove);
        Random random = new Random();
        GameTree h = temps.get( random.nextInt(temps.size()));
        return h.currentState.pos;
    }

    // finds all possible next moves
    private void createPossibleMoves(){
        GameState temp = new GameState();
        final int dim = 7;
        for(int i = 0; i< dim; i++)
            if(currentState.coins[0][i]==' '){
                currentState.clone(temp);
                temp.pos = i;
                temp.updateCoins();
                temp.swapPlayer();
                nextMoves.add(new GameTree(temp,layerNo+1));
            }
    }

    /**
     * calculates the score for this node when the current player is 'g' (green)
     * its the maximum out of the scores of all the next moves
     */
    private void scoreForThisNodeG(){
        score = -1;
        for(GameTree nextMove:nextMoves)
            if(score<nextMove.score)
                score = nextMove.score;
    }

    /**
     * calculates the score for this node when the current player is 'o' (orange)
     * its the minimum out of the scores of all the next moves
     */
    private void scoreForThisNodeO(){
        score = 1;
        for(GameTree nextMove:nextMoves)
            if(score>nextMove.score)
                score = nextMove.score;
    }

    // assigns the current node its score based on the status of the current game state
    private void updateScore(){
        switch (currentState.getStatus()){
            case 'g': //in case g has won in this node
                score = 1;
                break;
            case 'o':  //in case o has won in this node
                score = -1;
                break;
            case 'd':   // in case its a draw
                score = 0;
                break;
            case ' ':   // in case the game is still in progress
                createPossibleMoves();
                if(currentState.getCurrentPlayer()=='g')
                    scoreForThisNodeG();
                else
                    scoreForThisNodeO();
        }
    }

    /**
     * constructor of this class
     * initialises the variables in this class
     * @param cs - current game state in this node
     * @param layerNo - layer number of the node in the game tree
     */
    public GameTree(GameState cs, int layerNo){
        currentState = new GameState();
        cs.clone(currentState);
        this.layerNo = layerNo;

        final int maxLayerNo = 6;

        if(layerNo < maxLayerNo)
            updateScore();
        else
            score = 0;

    }
}
