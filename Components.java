import java.awt.Graphics2D;
import java.awt.Color;
public class Components
{
    private int height;
    private int width;
    private int size = 20;
    Components(){
    }
    
    public void paint_floor(Graphics2D g, StartGame game){
        g.setColor(Color.GREEN);
        g.fillRect(0, game.getHeight() - size, game.getWidth(), size);
    }
    public int get_size(){
        return size;
    }
}
