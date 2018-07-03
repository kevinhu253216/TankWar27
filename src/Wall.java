
import java.awt.*;

public class Wall {
    int x, y, wallHeight, wallWidth;
    TankClient tc;

    public Wall(int x, int y, int wallWidth, int wallHeight, TankClient tc){
        this.x = x;
        this.y = y;
        this.wallWidth = wallWidth;
        this.wallHeight = wallHeight;
        this.tc = tc;
    }

    public void draw(Graphics g){
        g.fillRect(x, y, wallWidth, wallHeight);
    }

    public Rectangle getRect(){
        return new Rectangle(x, y, wallWidth, wallHeight);
    }



}