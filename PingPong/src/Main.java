/**
 * Created by Vasilis on 14/4/2017.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

       //System.out.println("Game started.");
       MainFrame gameFrame = new MainFrame();
       //gameFrame.requestFocusInWindow();
       Board gameBoard = new Board();
       gameFrame.add(gameBoard);
       //gameBoard.setFocusable(true);
       gameBoard.requestFocusInWindow();

       while(true){
           gameBoard.moveBall();
           gameBoard.checkCollision();
           gameBoard.checkBoundCollision();
           gameBoard.movePlayerPlatform();
           gameBoard.moveEnemyPlatform();
           gameBoard.repaint();
           Thread.sleep(10);
       }
    }
}