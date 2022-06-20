import javax.swing.JFrame;
import java.awt.Dimension;
public class Main{
    //Start game
    private static final int HEIGHT = 1024;
    private static final int WIDTH = 756;
    public static void main(String []args)throws InterruptedException {
            JFrame frame = new JFrame("War of boxes");
            StartGame game = new StartGame();
            frame.setPreferredSize(new Dimension(HEIGHT, WIDTH));
            frame.setMinimumSize(new Dimension(HEIGHT, WIDTH));
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(game);
            while (true) {
                game.repaint();
                Thread.sleep(10);
            }
    }
}
