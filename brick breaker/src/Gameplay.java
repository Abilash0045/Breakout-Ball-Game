import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    // setting the required properties
    private boolean play = false;
    private int score = 0;

    private int bricks = 21;

    // Setting the speed of the ball

    private Timer timer;
    private int delay = 3;

    private int playerposX = 310;
    // Setting ball positions
    private int ballposX = 120;
    private int ballposY = 350;
    // Setting ball directions
    private int ballXdir = -1;
    private int ballYdir = -2;

    private MapGenerator map;

    // Now we need a constructor to work with
    public Gameplay(){
        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start();
    }
    // Function to draw
    public void paint(Graphics g){
        // creating background
        g.setColor(Color.black);
        g.fillRect(1,1,692,592);

        // Drawing map
        map.draw((Graphics2D)g);

        // creating borders
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(683,0,3,592);

        // Drawing scorecard
        g.setColor(Color.blue);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(""+score,590,30);

        // creating paddle
        g.setColor(Color.green);
        g.fillRect(playerposX,550,100,8);

        // creating the ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX,ballposY,20,20);

        // Condition if the player won the game
        if (bricks <= 0){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Congratulations You Won !",260,300);

            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter To  Play Again",230,350);
        }

        // condition for game over
        if (ballposY > 570){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game Over,Score :"+score,190,300);

            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter To Restart The Game",230,350);

        }
        g.dispose();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        // to move the ball & check border intersection
        if (play){
            if (new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerposX,550,100,8))) {
                ballYdir = - ballYdir;
            }
           A: for (int i=0;i<map.map.length;i++){
                for (int j =0;j<map.map[0].length;j++){
                    if (map.map[i][j] > 0){
                       int brickX = j * map.Brickwidth + 80;
                       int brickY = i * map.Brickheight + 50;
                       int brickwidth = map.Brickwidth;
                       int brickheight = map.Brickheight;
                       Rectangle brickrect = new Rectangle(brickX,brickY,brickwidth,brickheight);
                       Rectangle ballrect = new Rectangle(ballposX,ballposY,20,20);
                       if (ballrect.intersects(brickrect)){
                           map.SetBrickVal(0,i,j);
                           bricks--;
                           score += 5;
                           if (ballposX +19 <= brickrect.x || ballposX+1 >= brickrect.x + brickrect.width)
                               ballXdir = - ballXdir;
                           else
                               ballYdir = - ballYdir;
                           break A;
                       }
                    }
                }
            }
            ballposX += ballXdir;
            ballposY += ballYdir;

            if (ballposX < 0){
                ballXdir = - ballXdir;
            }
            if (ballposY < 0){
                ballYdir = - ballYdir;
            }
            if (ballposX > 670){
                ballXdir = - ballXdir;
            }
        }
        repaint();
    }
    // Moving the pedal while pressing the left or right arrowKeys
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            if (playerposX >= 600)
                playerposX = 600;
            else
                moveright();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT){
            if (playerposX < 10)
                playerposX = 10;
            else
                moveleft();
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            if (!play){
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerposX = 310;
                score = 0;
                bricks = 21;
                map = new MapGenerator(3,7);

                repaint();
            }
        }
    }
    public void moveright(){
        play = true;
        playerposX += 20;
    }
    public void moveleft(){
        play = true;
        playerposX -= 20;
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
