package game.bobo.cn.bobogame.entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import game.bobo.cn.bobogame.global.Config;
import game.bobo.cn.bobogame.model.BaseModel;
import game.bobo.cn.bobogame.view.GameView;

/**
 * Created by Leon on 2018/5/1.
 * Functions:豌豆射手的子弹
 *
 * 颜色：米白色裤子+ 黑色裤子尺码：买裤子  >  32[二尺五]
 *
 */

//implements TouchAble leon修改
public class Bullet extends BaseModel {

    private int locationX;
    private int locationY;
    private boolean isAlive;
    private Rect touchArea;
    private long birthTime;
    private SunState state;
    private int xDirectionDistance;
    private int yDirectionDistance;
    private float xSpeed;
    private float ySpeed;
    private int raceWayIndex;

    //阳光前进速度
    private int xPeed = 15;

    /**阳光的状态，show：生命事件对其有效 move：什么时间对其无效*/
    public enum SunState{
        SHOW,MOVE
    }

    public Bullet(int locationX, int locationY, int raceWayIndex) {
        this.locationX = locationX + Config.peaFrames[0].getWidth();
        this.locationY = locationY;
       // this.touchArea = new Rect(locationX,locationY,locationX + Config.sun.getWidth(),locationY + Config.sun.getHeight());
        this.isAlive = true;
        this.birthTime = System.currentTimeMillis();
        this.raceWayIndex = raceWayIndex;
        this.state = SunState.SHOW;
    }

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        if (isAlive){
                //让豌豆五秒中消失
            if (System.currentTimeMillis() - birthTime > 5000){
                    isAlive = false;

            }else {
                locationX += xPeed;
                if (locationX >= Config.deviceWidth){
                    isAlive = false;
                }

            }
            canvas.drawBitmap(Config.bullet,locationX,locationY,paint);
            //子弹主动发起碰撞检测消灭僵尸
            GameView.getInstance().bulletCheckCollision(this,raceWayIndex);

        }
    }

    @Override
    public int getModelWidth() {
        return Config.sun.getWidth();
    }


    @Override
    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    @Override
    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    @Override
    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    @Override
    public int getLocationX() {
        return locationX;
    }

    @Override
    public int getLocationY() {
        return locationY;
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }
}
