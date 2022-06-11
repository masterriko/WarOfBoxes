import java.awt.Color;
import java.awt.Graphics2D;

public class Box
{
    StartGame game;
    private double gold;
    private int positionX = 0;
    private int positionY;
    private int damage;
    private int gold_from_kill;
    private int size;
    private int hp;
    private boolean enemy;
    private int velocity;
    private int speed;
    private int x = 0;
    Box(double gold, boolean enemy, StartGame game){
        this.game = game;
        this.enemy = enemy;
        this.gold = gold;
        this.size = (int)(Math.log(gold / 10 + 1) * 100);
        this.hp = size;
        positionY -= size;
        this.gold_from_kill = (int)(gold * 1 / 3);
        this.positionY = 0;
    }
    public void paint(Graphics2D g, StartGame game, Components c, Base b){
        this.game = game;
        if(enemy){
            x = game.getWidth() - b.get_width() - positionX - size - 10;
            g.setColor(Color.RED);
            g.fillRect(x, game.getHeight() - size - c.get_size() - 1, size, size);
            g.setColor(Color.RED);
            g.fillRect(x, game.getHeight() - size - c.get_size() - size / 5, hp, size/10);
        }else{
            x = b.get_positionX() + b.get_width() + positionX;
            g.setColor(Color.BLUE);
            g.fillRect(b.get_positionX() + b.get_width() + positionX, game.getHeight() - size - c.get_size() - 1, size, size);
            g.setColor(Color.RED);
            g.fillRect(b.get_positionX() + b.get_width() + positionX, game.getHeight() - size - c.get_size() - size / 5, hp, size/10);
        }
    }
    public int get_positionX(){
        return x;
    }
    public int get_positionY(){
        return positionY;
    }
    
    public int get_size(){
        //stranica kvadrata
        return size;
    }
    public int get_damage(){
        //damage je ploščina kvadrata 
        return (int)(Math.pow(size, 2));
    }
    public int get_gold_from_kill(){
        //1/3 ploščine kvadrata golda
        return gold_from_kill;
    }
    public int get_hp(){
        //hp boxa 
        return hp;
    }
    public void set_hp(long hp){
        //nov hp
        this.hp -= hp;
    }
    public int get_velocity(){
        return 1;
    }
    public int get_surface(){
        return size * size;
    }
    public void move(){
        positionX += 1;
    }
}
