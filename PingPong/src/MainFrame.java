/**
 * Created by Vasilis on 14/4/2017.
 */
import javax.swing.*;
//import java.awt.*;

public class MainFrame extends JFrame {

    //Fields
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;

    //Contructor
    public MainFrame(){
        this.setVisible(true);
        this.setSize(WIDTH,HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Pong");
        this.setLocationRelativeTo(null);

    }

    //Methods
    public int getWidth(){
        return WIDTH;
    }

    public int getHeight(){
        return HEIGHT;
    }
}
