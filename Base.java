import java.awt.Graphics2D;
import java.awt.Color;
public class Base{  
    
    private int hp = 600;
    private int positionX;
    private final int positionY = 1;
    private int height;
    private int width;
    private boolean enemy;    
    Base(boolean enemy, StartGame game){
        if(enemy){
            positionX = game.getWidth() - width - 10;
        }else{
            positionX = 10;
        }
        this.enemy = enemy;
        width =  150;
        height = 200;
        hp = width;
    }
    public int get_hp(){
        return hp;
    }
    public int get_positionX(){
        return positionX;
    }
    public int get_width(){
        return width;
    }
    public int get_positionY(){
        return positionY;
    }
    public void set_hp(int hp){
        this.hp -= hp;
    }
    public void upgrade(int length){
        length += length;
    }
    public int get_surface(){
        return width * height;
    }
    public void paint(Graphics2D g, StartGame game, Components c){
        g.setColor(Color.BLUE);
        if(enemy){
        positionX = game.getWidth() - width - 10;
        g.setColor(Color.RED);
    }   
        g.fillRect(positionX, game.getHeight() - height - c.get_size() - 1, width, height);
        g.setColor(Color.RED);
        g.fillRect(positionX, game.getHeight() - height - c.get_size() - 1 - height / 5, hp, height / 10);
    }
}
