
import java.awt.*;

public class Explode {

    int x, y;
    private boolean isExplodeLive = true;
    int[] diameter = {4, 7, 12, 18, 26, 32, 49, 30, 14 ,6, 1};
    int step = 0;

    private TankClient tc;

    public Explode(int x, int y, TankClient tc){
        this.x = x;
        this.y = y;
        this.tc = tc;
    }

    public void draw(Graphics g){
        if(isExplodeLive == false){
            tc.explodeList.remove(this);
            return;
        }
        if(step == diameter.length){ //已经走完了一遍生命历程
            step = 0;
            isExplodeLive = false;
            return;
        }
        Color c = g.getColor();
        g.setColor(Color.ORANGE);
        g.fillOval(x, y, diameter[step], diameter[step] );
        g.setColor(c);
        step++;
    }
}