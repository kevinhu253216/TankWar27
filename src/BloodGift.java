
import java.awt.*;

public class BloodGift {
    int x, y, w, h;
    TankClient tc;
    int step = 0;
    private boolean isBloodGiftLive = true;
    private  int[][]  pos={ {359,300}, {360,300}, {400,200}, {100, 2000}, {100, 95}, {300,300}, {600,450}};

    public BloodGift(){
        x = pos[0][0];
        y = pos[0][1];
        w = 15;
        h = 15;
    }

    public void setIsBloodGiftLive( boolean isBloodGiftLive){
        this.isBloodGiftLive = isBloodGiftLive;
    }

    public boolean getIsBloodGiftLive( ){
        return this.isBloodGiftLive ;
    }
    public void draw(Graphics g){
        if(isBloodGiftLive == false){
            return;
        }
        Color c = g.getColor();
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, w, h);
        g.setColor(c);
        move();
    }

    private void move(){
        step++;
        if(step == pos.length){
            step = 0;
        }
        x = pos[step][0];
        y = pos[step][1];
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, w, h);
    }
}