import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Vasilis on 14/4/2017.
 */
public class Board extends JPanel implements KeyListener{

    //Fields
    public static final int WIDTH = 640;
    public static final int HEIGHT = 466;

    public static final Color COLOR_BLACK = new Color(0,0,0); // Obstacle color
    public static final Color COLOR_WHITE = new Color(255,255,255); // Closed list nodes color

    private Ball ball;
 
    private Platform playerPlatform;
    private Platform enemyPlatform;

    private int playerScore;
    private int enemyScore;
    private int numOfBounces;

    //Constructor
    public Board(){
        ball = new Ball(Board.WIDTH / 2 - 5, Board.HEIGHT/2, -5, 0, 10);

        playerPlatform = new Platform(this, 0,HEIGHT/2 - Platform.HEIGHT/2, false, ball);
        enemyPlatform = new Platform(this, WIDTH-2*Platform.WIDTH+2,HEIGHT/2 - Platform.HEIGHT/2, true, ball);

        playerScore = 0;
        enemyScore = 0;
        numOfBounces = 0;

        this.addKeyListener(this);
        this.setFocusable(true);
    }

    //Methods
    public void paint(Graphics graphics){
        super.paint(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics2D.setColor(Board.COLOR_BLACK);
        graphics2D.fillRect(0,0, 640,480);

        graphics2D.setColor(Board.COLOR_WHITE);
        
        for(int i=0; i<20; i++) graphics2D.fillRect(this.WIDTH/2-2, i * 36, 1, 20);

        playerPlatform.paint(graphics2D);
        enemyPlatform.paint(graphics2D);
        
        ball.paint(graphics2D, Board.COLOR_WHITE);

        paintScore(graphics2D);

    }

    public void moveBall(){
        ball.move();
        
        if(ball.getCanChase() && ball.getPlayerTouchedBall()){             
            ball.setCanChase(false);
        }
    }
    
    public void checkCollision(){
        ball.collide(playerPlatform);
        ball.collide(enemyPlatform);

        numOfBounces = ball.getBounces();
        //System.out.println("Number of bounces: " + numOfBounces);
        //System.out.println("Speed of ball: " + ball.getSpeedX());
        ball.incrementSpeed(numOfBounces);

    }

    public void movePlayerPlatform(){
        playerPlatform.move();
    }

    public void moveEnemyPlatform(){
        if(ball.getPlayerTouchedBall()){           
            if(ball.getSpeedX() > 0){
                if(ball.getY() >= enemyPlatform.getY() + enemyPlatform.HEIGHT/2){
                    if(ball.getX() <= Board.WIDTH) 
                        enemyPlatform.moveDown();
            
                }
                if(ball.getY() < enemyPlatform.getY() + enemyPlatform.HEIGHT/2){
                    if(ball.getX() <= Board.WIDTH)
                        enemyPlatform.moveUp();
                }
            }
        }    
    }
    
    public void startNewRound(Ball ball, Platform playerPlatform, Platform enemyPlatform, boolean toPlayer){
          ball.setBounces(0);
          playerPlatform.setCoordinates(0,HEIGHT/2 - Platform.HEIGHT/2);
          enemyPlatform.setCoordinates(WIDTH-2*Platform.WIDTH+2,HEIGHT/2 - Platform.HEIGHT/2);
          
          if(toPlayer){
              ball.setSpeedX(5);
              ball.setSpeedY(2);
          }
          else {
              ball.setSpeedX(-5);
              ball.setSpeedY(-2);
          }          
    }

    public void checkBoundCollision(){
      int score = ball.boundCollision();
      
      if(score == -1){
          enemyScore++;
          startNewRound(ball, playerPlatform, enemyPlatform, true);
      }
      if(score == 1){
          playerScore++;
          startNewRound(ball, playerPlatform, enemyPlatform, false);
      }
    }

    public void paintScore(Graphics2D graphics2D){
        graphics2D.setFont(new Font("Steamer", Font.PLAIN, 20));
        graphics2D.drawString("" + playerScore, Board.WIDTH/2 - 64, 20);
        graphics2D.drawString("" + enemyScore, Board.WIDTH/2 + 48, 20); 
    }

    @Override
    public void keyPressed(KeyEvent event) {
        playerPlatform.keyPressed(event);
        //System.out.println("Key pressed");
    }

    @Override
    public void keyReleased(KeyEvent event) {
        playerPlatform.keyReleased(event);
        //System.out.println("Key released.");
    }

    public void keyTyped(KeyEvent event){}
}