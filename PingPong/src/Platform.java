//import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Vasilis on 14/4/2017.
 */
public class Platform {

    public static final int WIDTH = 8;
    public static final int HEIGHT = 66;

    private int x;
    private int y;
    private boolean isEnemy;
        
    private Ball ball;

    private static int speed = 5;
    private int platformSpeed;

    private Board board;


    //Constructor
    public Platform(Board board, int x, int y, boolean isEnemy, Ball ball){
        this.board = board;

        this.x = x;
        this.y = y;
        
        this.isEnemy = isEnemy;
        this.ball = ball;

        this.platformSpeed = 0;
    }

    //Methods
    public int getX(){
        return x;
    }
    
    public void setX(int x){
        this.x = x;
    }

    public int getY(){
        return y;
    }
    
    public void setY(int y){
        this.y = y;
    }
    
    public void setCoordinates(int x, int y){
        this.setX(x);
        this.setY(y);
    }

    public int getSpeed(){
        return speed;
    }
    
    public void setSpeed(int speed){
        this.speed = speed;
    }

    public void moveUp(){ 
        int totalBallSpeed = (int)Math.sqrt(Math.pow(ball.getSpeedX(), 2) + Math.pow(ball.getSpeedY(), 2));
        
        if(isEnemy){
            if(totalBallSpeed < speed && Math.abs(ball.getX() - this.getX()) < Platform.HEIGHT/2) speed = (int)Math.ceil(ball.getSpeedY());
            else speed = 4;
        }
     
        if(this.y > 0) {
            this.platformSpeed = -speed;
            this.y -= speed;
        }            
    }

    public void moveDown(){
        int totalBallSpeed = (int)Math.sqrt(Math.pow(ball.getSpeedX(), 2) + Math.pow(ball.getSpeedY(), 2));
        
        if(isEnemy){
            if(totalBallSpeed < speed && Math.abs(ball.getX() - this.getX()) < Platform.HEIGHT/2) speed = (int)Math.ceil(ball.getSpeedY());
            else speed = 4;
        }
        
        if(this.y < (Board.HEIGHT - HEIGHT-HEIGHT/4 + 2)){            
            this.platformSpeed = speed;
            this.y += speed;
        }
    }

    public void move(){
        if(platformSpeed > 0) moveDown();
        if(platformSpeed < 0) moveUp();
    }
    
    public void moveTo(int yPos){
        if(y > yPos) moveUp();   
        else moveDown();
    }
    
    public int calculateTotalPositionY(Ball ball){
        
        int currentBallX, currentBallY;
        int currentSpeedX, currentSpeedY;
                
        currentBallX = ball.getX();
        currentBallY = ball.getY();
        
        currentSpeedX = ball.getSpeedX();
        currentSpeedY = ball.getSpeedY();
        
        while(currentBallX != Board.WIDTH){
            
            currentBallX += ball.getSpeedX();
            currentBallY += ball.getSpeedY();
            
            if(currentBallY == 0 || currentBallY == (Board.HEIGHT - 4 - 2 * ball.getRadius())){
                currentSpeedY = -currentSpeedY; 
            }
        }
        return currentBallY;
    }

    public void keyReleased(KeyEvent event){
        platformSpeed = 0;
    }

    public void keyPressed(KeyEvent event){
        if(event.getKeyCode() == KeyEvent.VK_UP){
            platformSpeed = -speed;  
        }
        if(event.getKeyCode() == KeyEvent.VK_DOWN){
            platformSpeed = speed;
        }
    }

    public void paint(Graphics2D g){
        g.fillRect(x, y, Platform.WIDTH, Platform.HEIGHT);
    }
}
