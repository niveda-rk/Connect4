import java.awt.*;
import java.awt.image.BufferStrategy;

public class DisplayMenu {
    private int width;
    private int height;
    private Graphics g;
    private int choice;

    public DisplayMenu(int width,int height){
        this.width = width;
        this.height = height;
        choice = -1;
    }

    private void printConnect4(){
        g.setFont( new Font("Monospaced", Font.BOLD , 50));
        String str = "CONNECT4";
        int startx = 160;
        int starty = 50;
        for(int i=0;i<str.length();i++) {
            g.setColor(i%2==0?Color.green:Color.orange);
            g.drawString(""+str.charAt(i), startx, starty);
            startx+=30;
        }
    }

    private void printChoice(){
        g.setColor(Color.white);
        g.setFont( new Font("Monospaced", Font.BOLD , 30));
        if(choice==0)
            g.setColor(Color.blue);
        g.drawString("Two player Game",150,150);
        g.setColor(Color.white);
        if(choice==1)
            g.setColor(Color.blue);
        g.drawString("Single Player (Start as green)",40,200);
        g.setColor(Color.white);
        if(choice==2)
            g.setColor(Color.blue);
        g.drawString("Single Player (Start as orange)",30,250);
    }

    private void renderMessages(){
        printConnect4();
        printChoice();
    }

    private void renderBox(){
        g.setColor(Color.blue);
        switch(choice){
            case 0:

        }
    }

    public void render(Canvas canvas,int choice){
        BufferStrategy bs = canvas.getBufferStrategy();
        if(bs == null){
            canvas.createBufferStrategy(3);
            return;
        }
        this.choice = choice;
        g = bs.getDrawGraphics();
        //clear screen
        g.clearRect(0,0,width,height);
        //Draw here
        g.setColor(Color.black);
        g.fillRect(0,0,width,height);
        renderMessages();
        if(choice!=-1)
            renderBox();

        //End drawing here
        bs.show();
        g.dispose();
    }
}
