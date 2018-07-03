
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Tank {
    private static final int XSPEED = 5;
    private static final int YSPEED = 5;
    private BloodBar bb = new BloodBar();
    private static final int TANK_WIDTH = 30;
    private static final int TANK_HEIGHT = 30;
    private boolean isTankLive = true;
    TankClient tc;
    private int x, y;
    private int oldX, oldY;
    private static Random r = new Random();
    private boolean isEnemyTank;
    private int tankLife = 100;


    private  boolean bL = false, bU =false, bR = false, bD= false;
    private int step = r.nextInt(12) + 3; //最少移动三步


    private Direction dir = Direction.STOP;
    private Direction ptDir = Direction.D;


    public Tank(int x, int y, boolean isEnemy){
        this.x = x;
        this.oldX= x;
        this.y = y;
        this.oldY = y;
        this.isEnemyTank = isEnemy;
    }

    public Tank(int x, int y, boolean isEnemy, Direction dir, TankClient tc){
        this(x, y, isEnemy);
        this.dir = dir;
        this.tc = tc;
    }

    public void setTankLive(boolean isTankLive) {
        this.isTankLive = isTankLive;
    }

    public boolean getTankLive() {
        return this.isTankLive ;
    }

    public boolean isIsEnemyTank() {
        return isEnemyTank;
    }

    public int getTankLife(){
        return this.tankLife;
    }

    public void setTankLife(int tankLife){
        this.tankLife = tankLife;
    }

    public void draw(Graphics g){
        if(isTankLive == false){
            if(isEnemyTank == false){ //自己
                ;
            }
            if(isEnemyTank == true){
                tc.enemyTankList.remove(this);
            }
            return; //如果没活着，直接不画 返回
        }
        if(isIsEnemyTank() == false) {
            bb.draw(g);
        }
        Color c = g.getColor();
        if(isEnemyTank == false) {  //ME
            g.setColor(Color.RED);
        }else {
            g.setColor(Color.BLUE);
        }
        g.fillOval(x,y,TANK_WIDTH,TANK_HEIGHT);
        g.setColor(c);
        move();

        switch(ptDir){
            case L:
                g.drawLine(x + Tank.TANK_WIDTH/2, y+Tank.TANK_HEIGHT/2, x, y+Tank.TANK_HEIGHT/2);
                break;
            case LU:
                g.drawLine(x + Tank.TANK_WIDTH/2, y+Tank.TANK_HEIGHT/2, x, y);
                break;
            case U:
                g.drawLine(x + Tank.TANK_WIDTH/2, y+Tank.TANK_HEIGHT/2, x + Tank.TANK_WIDTH/2, y );
                break;
            case RU:
                g.drawLine(x + Tank.TANK_WIDTH/2, y+Tank.TANK_HEIGHT/2, x + Tank.TANK_WIDTH, y);
                break;
            case R:
                g.drawLine(x + Tank.TANK_WIDTH/2, y+Tank.TANK_HEIGHT/2, x + Tank.TANK_WIDTH, y + Tank.TANK_HEIGHT/2);
                break;
            case RD:
                g.drawLine(x + Tank.TANK_WIDTH/2, y+Tank.TANK_HEIGHT/2, x + Tank.TANK_WIDTH, y + Tank.TANK_HEIGHT);
                break;
            case D:
                g.drawLine(x + Tank.TANK_WIDTH/2, y+Tank.TANK_HEIGHT/2, x + Tank.TANK_WIDTH/2, y + Tank.TANK_HEIGHT);
                break;
            case LD:
                g.drawLine(x + Tank.TANK_WIDTH/2, y+Tank.TANK_HEIGHT/2, x, y+Tank.TANK_HEIGHT);
                break;
        }
    }

    void move(){
        this.oldX = x;
        this.oldY = y;

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
            case STOP:
                break;
        }
        if(this.dir != Direction.STOP){
            this.ptDir =this.dir;
        }

        if(x < 0) x=0;
        if(y < 30) y=30;
        if( x + Tank.TANK_WIDTH > TankClient.GAME_WIDTH ){
            x = TankClient.GAME_WIDTH - TankClient.WIDTH;
        }
        if( y + Tank.TANK_HEIGHT > TankClient.GAME_HEIGHT ){
            y = TankClient.GAME_HEIGHT - TankClient.HEIGHT ;
        }

        if(isEnemyTank){
            if(step == 0){
                Direction[] dirs = Direction.values();  //把enum转换成数组{0，8}
                step = r.nextInt(12) + 3;
                int randomNumber = r.nextInt(dirs.length); // 随机产生一个数，这个数在范围 dirs内
                dir = dirs[randomNumber];
            }

            step--;
            if(r.nextInt(40) > 38) {
                this.fire();
            }
        }


    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        // System.out.println("ok");
        switch (key){
            case KeyEvent.VK_Q:
                if(this.isTankLive == false){
                    this.isTankLive = true;
                    this.setTankLife(100);
                }
                break;
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
        }
        locateDirection();
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        // System.out.println("ok");
        switch (key){
            case KeyEvent.VK_CONTROL:
                fire();
                break;
            case KeyEvent.VK_A:
                superFire();
                break;
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
        }
        locateDirection();
    }

    void locateDirection() {
        //System.out.println("locateDirection");
        if (bL && !bU && !bR && !bD){
            dir = Direction.L;
        }else if (bL && bU && !bR && !bD){
            dir = Direction.LU;
        }else if(!bL && bU  && !bR && !bD){
            dir = Direction.U;
        }else if(!bL && bU  &&  bR && !bD){
            dir = Direction.RU;
        }else if(!bL && !bU && bR  && !bD){////////////////////////////////
            dir = Direction.R;
            System.out.println("R");
        }else if(!bL && !bU && bR  &&  bD){
            dir = Direction.RD;
        }else if(!bL && !bU && !bR &&  bD){
            dir = Direction.D;
            System.out.println("D");
        }else if(bL  && !bU && !bR &&  bD){
            dir = Direction.LD;
        }else if(!bL  && !bU && !bR &&  !bD){
            dir = Direction.STOP;
        }
    }

    public Missile fire(){
        if(!this.isTankLive) {
            return null;
        }
        int x = this.x + Tank.TANK_WIDTH/2 - Missile.HEIGHT/2;
        int y = this.y + Tank.TANK_WIDTH/2 - Missile.HEIGHT/2;
        Missile m = new Missile( x, y, isEnemyTank,  ptDir, this.tc);
        tc.missileList.add(m);
        return m;
    }

    public Rectangle getRect(){//碰撞检测类
        return new Rectangle( x, y, TANK_WIDTH, TANK_HEIGHT );
    }



    public boolean collideWithWall(Wall wall){
        if(this.isTankLive && this.getRect().intersects(wall.getRect())  ){
            this.stay();
            return true;
        }
        return false;
    }

    private void stay(){ //回到上一步位置
        x = oldX;
        y = oldY;
    }

    public boolean collidesWithTanks(java.util.List<Tank> tankList){
        for(int i=0; i<tankList.size(); i++){
            Tank otherTank = tankList.get(i);
            if(this != otherTank){ //不是当前坦克
                if( this.isTankLive && otherTank.isTankLive && this.getRect().intersects(otherTank.getRect()) ){
                    this.stay();
                    otherTank.stay();
                    return true;
                }
            }
        }
        return false;
    }

    private void superFire(){
        Direction[] dirs = Direction.values();
        for(int i=0;i<8;i++){
            fire( dirs[i] );
        }
    }

    public Missile fire(Direction dir){
        if(!this.isTankLive) {
            return null;
        }
        int x = this.x + Tank.TANK_WIDTH/2 - Missile.HEIGHT/2;
        int y = this.y + Tank.TANK_WIDTH/2 - Missile.HEIGHT/2;
        Missile m = new Missile( x, y, isEnemyTank,  dir, this.tc);
        tc.missileList.add(m);
        return m;
    }

    private class BloodBar{
        public void draw(Graphics g){
            Color c =g.getColor();
            g.setColor(Color.RED);
            g.drawRect(x,y-10,TANK_WIDTH,10);
            int w = TANK_WIDTH * tankLife / 100;

            g.fillRect(x,y-10,w,10);
            g.setColor(Color.black);
        }
    }

    public boolean eat( BloodGift b){
        if( this.getTankLive() &&b.getIsBloodGiftLive() && this.getRect().intersects(b.getRect())  ){
            this.setTankLife(100);
            b.setIsBloodGiftLive( false );
            return true;
        }
        return false;
    }
}