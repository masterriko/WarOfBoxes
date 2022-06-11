import java.awt.Graphics2D;
import java.awt.Color;
public class Coin
{
    private int height = 20;
    private int width = 20;
    private int positionX;
    private int positionY;
    private int gold;
    Coin(int min, int max, StartGame game){
        positionX = min + (int)(Math.random() * ((max - min) + 1));
        positionY = game.getHeight() - height - 21;
        gold = random_number();
    }
    static int random_number() { 
        return (int)(Math.floor((1 - Math.sqrt(1 - Math.random())) * 101));
    }
    public int get_positionX_min(){
        return positionX;
    }
    public int get_positionX_max(){
        return positionX + width;
    }
    public void paint(Graphics2D g, StartGame game){
        positionY = game.getHeight() - height - 21;
        g.setColor(Color.YELLOW);
        g.fillRect(positionX, positionY, width, height);
    }
    public int get_gold(){
        return gold;
    }
}
