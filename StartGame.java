import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class StartGame extends JPanel{
    private static final int HEIGHT = 1024;
    private static final int WIDTH = 756;
    private int gold_blue = 20;
    private int gold_red = 20;
    private final int fontSize = 30;
    private Components components = new Components();
    private List<Box> Boxes_red = new ArrayList<Box>();
    private List<Box> Boxes_blue = new ArrayList<Box>();
    private List<Coin> coins = new ArrayList<Coin>();
    private Dimension size;
    private final long createdMillis = System.currentTimeMillis();
    private long last = 0;
    private Base blue_base;
    private Base red_base;
    private static boolean game_on = true;
    private boolean check_time = true;
    public StartGame() {
        blue_base = new Base(false, this);
        red_base = new Base(true, this);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //if any key is pressed create a box
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    create_box_blue();
                 }
                else if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    create_box_red();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        setFocusable(true);
    }
    
    public void paintComponent(Graphics g){
            //paints on JPanel
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (game_on){
                commands(g2d);
                components.paint_floor(g2d, this);
                blue_base.paint(g2d, this, components);
                red_base.paint(g2d, this, components);
                make_coin(g2d, blue_base, red_base);
                for(Coin coin: coins){
                    coin.paint(g2d, this);
                }
                for (Box box : Boxes_blue) {
                    box.paint(g2d, this, components, blue_base);
                    box.move();
                    check_coin_collision(true, box);
                }
                for (Box box: Boxes_red){
                    box.paint(g2d, this, components, red_base);
                    box.move();
                    check_coin_collision(false, box);
                }
                box_collision();
                game_on = base_collision(red_base, blue_base);
                gold(g2d);
            }else{
                GameOver(g2d);
            }
    }  
    public void commands(Graphics2D g){
        g.setColor(Color.BLACK);
        g.drawString("Press ENTER", this.getWidth() - 100, 100);
        g.drawString("Press SPACE BAR", 30, 100);
    }
    public void box_collision(){
        if (Boxes_red.size() > 0 && Boxes_blue.size() > 0){
            if(Boxes_red.get(0).get_positionX() <= Boxes_blue.get(0).get_positionX() + Boxes_blue.get(0).get_size()){
                if (Boxes_red.get(0).get_surface() == Boxes_blue.get(0).get_surface()){
                    Boxes_red.remove(0);
                    Boxes_blue.remove(0);
                    gold_blue += Boxes_red.get(0).get_gold_from_kill();
                    gold_red += Boxes_blue.get(0).get_gold_from_kill();
                }else if(Boxes_red.get(0).get_surface() < Boxes_blue.get(0).get_surface()){
                    Boxes_blue.get(0).set_hp((int)((double)(Boxes_red.get(0).get_surface()) / (double)(Boxes_blue.get(0).get_surface()) * (double)(Boxes_blue.get(0).get_size())));
                    gold_blue += Boxes_red.get(0).get_gold_from_kill();
                    Boxes_red.remove(0);
                    if(Boxes_blue.get(0).get_hp() <= 0){
                        gold_red += Boxes_blue.get(0).get_gold_from_kill();
                        Boxes_blue.remove(0);
                    }
                }else{
                    Boxes_red.get(0).set_hp((int)((double)(Boxes_blue.get(0).get_surface()) / (double)(Boxes_red.get(0).get_surface()) * (double)(Boxes_red.get(0).get_size())));
                    gold_red += Boxes_blue.get(0).get_gold_from_kill();
                    Boxes_blue.remove(0);
                    if(Boxes_red.get(0).get_hp() <= 0){
                        gold_blue += Boxes_red.get(0).get_gold_from_kill();
                        Boxes_red.remove(0);
                    }
                }
            }
        }
    }
    public boolean base_collision(Base r, Base b){
        if (Boxes_blue.size() > 0){
            if(Boxes_blue.get(0).get_positionX() + Boxes_blue.get(0).get_size() >=  r.get_positionX()){
                r.set_hp((int)((double)(Boxes_blue.get(0).get_surface()) / (double)(r.get_surface()) * (double)(r.get_width())));
                Boxes_blue.remove(0);
                if(r.get_hp() <= 0){
                    r.set_hp(0);
                    return false;
                }
            }
        }
        if (Boxes_red.size() > 0){
            if(Boxes_red.get(0).get_positionX() <=  b.get_positionX() + b.get_width()){
                b.set_hp((int)((double)(Boxes_red.get(0).get_surface()) / (double)(b.get_surface()) * (double)(b.get_width())));
                Boxes_red.remove(0);
                if(b.get_hp() <= 0){
                    b.set_hp(0);
                    return false;
                
                }
            }
        }
        return true;
    }
    public void make_coin(Graphics2D g, Base b, Base r){
        if(get_age_in_seconds(createdMillis) % 3 == 0 && check_time){
            Coin coin = new Coin(b.get_positionX() + b.get_width() + 10, r.get_positionX() - 10, this);
            coins.add(coin);    
            check_time = false;
        }else if(get_age_in_seconds(createdMillis) % 3 != 0){
            check_time = true;
        }
    }
    public void create_box_blue(){
        //Creates blue box
        if (gold_blue > 0){
            Box box = new Box(gold_blue, false, this);
            Boxes_blue.add(box);
            gold_blue = 0;
        }
    }
    public void GameOver(Graphics2D g){
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        g.drawString("GAME OVER" , (int)(this.getWidth() / 2) - 100, (int)(this.getHeight() / 2));
        
    }
    public void create_box_red(){
        //creates red box
        if (gold_red > 0){
            Box box = new Box(gold_red, true, this);
            Boxes_red.add(box);
            gold_red = 0;
        }
    }
    public void gold(Graphics2D g){
        //show how much gold you have
        g.setColor(Color.YELLOW);
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        g.drawString(Integer.toString(gold_blue) + " " + "Gold" , 30, 40);
        g.drawString(Integer.toString(gold_red) + " " + "Gold" , this.getWidth() - 120, 40);
        if (get_age_in_seconds(createdMillis) > last){
            gold_blue += 1;
            gold_red += 1;
            last = get_age_in_seconds(createdMillis);
        }
    }
    public void check_coin_collision(boolean blue, Box box){
        int i = 0;
        for(Coin coin: coins){
            if ((coin.get_positionX_min() <= box.get_positionX() && coin.get_positionX_max() >= box.get_positionX()) || (coin.get_positionX_min() <= box.get_positionX() + box.get_size() && coin.get_positionX_max() >= box.get_positionX() + box.get_size())){
                if(blue){
                    gold_blue += coin.get_gold();
                    coins.remove(i);
                }else{
                    gold_red += coin.get_gold();
                    coins.remove(i);
                }
            }else{
                i++;
            }
        }
    }
    public static int get_age_in_seconds(long createdMillis) {
        //returns time in seconds from start
        long nowMillis = System.currentTimeMillis();
        return (int)((nowMillis - createdMillis) / 1000);
    }
}
