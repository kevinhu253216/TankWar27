

import java.awt.*;
import java.util.List;

public class Missile {
    int x,y;
    Direction dir;
    private boolean isMissileLive = true;
    private boolean isEnemyMissile = true;

    private static final int XSPEED = 10;
    private static final int YSPEED = 10;

    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;

    TankClient tc;

    public Missile(int x, int y,  Direction dir){
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public Missile(int x, int y,boolean isEnemyMissile,  Direction dir, TankClient tc){
        this(x, y, dir);
        this.isEnemyMissile = isEnemyMissile;
        this.tc = tc;
    }

    public void draw(Graphics g){

        if(isMissileLive == false){
            tc.missileList.remove(this);
        }

        Color c = g.getColor();
        if(isEnemyMissile == false) {
            g.setColor(Color.BLACK);
        }else {
            g.setColor(Color.YELLOW);
        }
        g.fillOval(x,y,WIDTH,HEIGHT);
        g.setColor(c);
        move();
    }

    private void move(){
//System.out.println(dir);
        switch(dir){
            case L:
                x -= XSPEED;
                break;
            case LU:
                x -= XSPEED;
                y -= YSPEED;
                break;
            case U:
                y -= YSPEED;
                break;
            case RU:
                x += XSPEED;
                y -= YSPEED;
                break;
            case R:
                x += XSPEED;
                break;
            case RD:
                x += XSPEED;
                y += YSPEED;
                break;
            case D:
                y += YSPEED;
                break;
            case LD:
                x -= XSPEED;
                y += YSPEED;
                break;
        }
        if( x < 0 || y < 0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_WIDTH){
            setIsMissileLive ( false );
            tc.missileList.remove(this);
        }
    }

    public boolean getIsMissileLive(){
        return isMissileLive;
    }

    public void setIsMissileLive(boolean isMissileLive){
        this.isMissileLive=isMissileLive;
    }

    public Rectangle getRect(){ //碰撞检测类
        return new Rectangle( x, y, WIDTH, HEIGHT );
    }

    public boolean hitTank(Tank tank){
        if(this.getIsMissileLive() && this.getRect().intersects(tank.getRect()) && tank.getTankLive() && this.isEnemyMissile != tank.isIsEnemyTank() ) { //这个子弹与 坦克相交
            if(tank.isIsEnemyTank()==false){
                tank.setTankLife(tank.getTankLife()-20);

                if( tank.getTankLife() <= 0 ){
                    tank.setTankLive(false);
                }
            }else {
                tank.setTankLive(false);
            }

            //tank.setTankLive(false);  //消除坦克
            this.isMissileLive=false;
            Explode e = new Explode(x, y, tc);
            tc.explodeList.add(e);
            return true;
        }
        return false;
    }

    public boolean hitTanks(List<Tank> enemyTankList){
        for( int i=0; i<enemyTankList.size(); i++){
            if(hitTank(enemyTankList.get(i))){
                return true;
            }
        }
        return false;
    }

    public boolean hitWall(Wall wall){
        if( this.getIsMissileLive() && this.getRect().intersects(wall.getRect())  ){
            this.isMissileLive = false;
            return true;
        }
        return false;
    }

}