import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Timer;

/**
 * Created by Vasilis on 14/4/2017.
 */
public class Ball implements ActionListener{
    
    public static final int UP = -1;
    public static final int DOWN = 1;
        
    public static final int HOR_SPEED = 5;
    
    private static final int DELAY = 0;
    
    private int x;
    private int y;
    private int speedX;
    private int speedY;
    private int radius;
    private int bounces;
    private int direction;
    private boolean playerTouchedBall;
    private boolean canChase;
    private boolean playerScored;
    
    private Timer timer;
    
    //Constructor

    public Ball(int x, int y, int speedX, int speedY, int radius){
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.radius = radius;
        this.direction = 1;

        this.bounces = 0;
        this.playerTouchedBall = true;
        this.canChase = false;
        
        timer = new Timer(DELAY, this);
        timer.setRepeats(false);
                
    }

    //Methods

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getSpeedX(){
        return this.speedX;
    }

    public int getSpeedY(){
        return this.speedY;
    }

    public int getRadius(){
        return this.radius;
    }

    public void setSpeedX(int speedX){
        this.speedX = speedX;
    }

    public void setSpeedY(int speedY){
        this.speedY = speedY;
    }

    public void setRadius(int radius){
        this.radius = radius;
    }
    
    public int getDirection(){
        return this.direction;
    }
    
    public void setDirection(int direction){
        this.direction = direction;
    }

    public void setBounces(int bounces) {
        this.bounces = bounces;
    }

    public int getBounces() {
        return this.bounces;
    }
    
    public void setPlayerTouchedBall(boolean playerTouchedBall){
        this.playerTouchedBall = playerTouchedBall;
    }
    
    public boolean getPlayerTouchedBall(){
        return playerTouchedBall;
    }
    
    public void setCanChase(boolean canChase){
        this.canChase = canChase;
    }
    
    public boolean getCanChase(){
        return canChase;
    }
    
  
    public void move(){
        if((y < 0) || (y > (Board.HEIGHT-4 - 2*radius))){
            speedY = -speedY;
            direction *= (-1);
        }
        x = x + speedX;
        y = y + speedY;
    }

    public void bounceHorizontally(Platform platform) {
        speedX = -speedX;
    }

    public void bounceVertically() {    
        speedY = -speedY;
    }

    public void paint(Graphics2D g, Color color){
        g.setColor(color);
        g.fillOval(x, y, radius, radius);
    }


    public int boundCollision(){
        int scoreSide = 0;

        //scoreSide = 1 : Player scores
        //scoreSide = -1 : Enemy scores

        if(x < -(radius * 10)){

            scoreSide = -1;
            
            playerScored = false;
            
            x = Board.WIDTH / 2 - radius / 2;
            y = Board.HEIGHT / 2 - radius / 2;
            
            speedX = scoreSide * HOR_SPEED;
            speedY = 0;
            
            playerTouchedBall = true;
            
            timer.start();         
            //System.out.println("Enemy scored.");

            return scoreSide;
        }

        if(x > Board.WIDTH  + (radius * 9)){

            scoreSide = 1;
            
            playerScored = true;
                        
            x = Board.WIDTH / 2 - radius / 2;
            y = Board.HEIGHT / 2 - radius / 2;
            
            speedX = scoreSide * HOR_SPEED;
            speedY = 0;
            
            playerTouchedBall = false;
            
            timer.start();
            //System.out.println("Player scored.");

            return scoreSide;
        }
        return scoreSide;
    }


    public void incrementSpeed(int bounces){
        if(speedX > 0) {
            switch (bounces) {
                case 0:
                    speedX = 5;
                    break;
                case 8:
                    speedX = 6;
                    break;
                case 24:
                    speedX = 7;
                    break;
                case 32:
                    speedX = 8;
                    break;
                case 40: 
                    speedX = 9;
                    break;
                case 48:
                    speedX = 10;
                    break;
            }
        }
        else {
            switch(bounces){
                case 0:
                    speedX = -5;
                    break;
                case 8:
                    speedX = -6;
                    break;
                case 24:
                    speedX = -7;
                    break;
                case 32:
                    speedX = -8;
                    break;
                case 40:
                    speedX = -9;
                    break;
                case 48:
                    speedX = -10;
                    break;
            }
        }
    }



    public int collide(Platform platform){
        int platformX = platform.getX();
        Random random = new Random();
        int rand;

        //Left platform (Player)
        if(platformX < Board.WIDTH / 2) {
            if ((x > platform.getX()) && (x < (platform.getX() + platform.WIDTH)) && (y > platform.getY() && y < platform.getY() + platform.HEIGHT)) {

                bounces += 1;
                this.bounceHorizontally(platform);
                
                
                if(this.speedY > 0){

                    rand = -1 + random.nextInt(3);
                    if(rand == 0) rand += direction;
                    if(this.speedY == 0) this.speedY = 2;
                    this.speedY += rand;
                }
                else{

                    rand = -1 + random.nextInt(3);
                    if(rand == 0) rand += direction;
                    if(this.speedY == 0) this.speedY = -2;
                    this.speedY += rand;
                }
                 this.playerTouchedBall = true;
                 this.canChase = true;
                 
                return bounces;
            }
            
            
        }

        //Right platform (Enemy)
        if(platformX > Board.WIDTH / 2){  
            if ((x > platform.getX() - radius) && (x < (platform.getX() + platform.WIDTH - radius)) && (y > platform.getY() && y < platform.getY() + platform.HEIGHT)){

                bounces += 1;
                this.bounceHorizontally(platform);
               
              
                if(this.speedY > 0){

                    rand = -1 + random.nextInt(3);
                    if(rand == 0) rand += direction;
                    if(this.speedY == 0) this.speedY = 2;
                    this.speedY += rand;
                }
                else{

                    rand = -1 + random.nextInt(3);
                    if(rand == 0) rand += direction;
                    if(this.speedY == 0) this.speedY = -2;
                    this.speedY += rand;
                }
                this.playerTouchedBall = false;
                this.canChase = false;
                
                return bounces;
            }
        }
        return bounces;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == timer){
            if(playerScored){
                speedX = HOR_SPEED;
            }
            else {
                speedX = -HOR_SPEED;
            } 
            speedY = 0;
        }
    }
}