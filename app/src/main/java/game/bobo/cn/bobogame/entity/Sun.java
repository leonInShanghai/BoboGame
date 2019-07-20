package game.bobo.cn.bobogame.entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import game.bobo.cn.bobogame.global.Config;
import game.bobo.cn.bobogame.model.BaseModel;
import game.bobo.cn.bobogame.model.TouchAble;
import game.bobo.cn.bobogame.view.GameView;

/**
 * Created by Leon on 2018/5/1.
 * Functions:阳光类
 *
 * 颜色：米白色裤子+ 黑色裤子尺码：买裤子  >  32[二尺五]
 *
 */

//implements TouchAble leon修改
public class Sun extends BaseModel {

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
    private int xPeed = 5;

    /**阳光的状态，show：生命事件对其有效 move：什么时间对其无效*/
    public enum SunState{
        SHOW,MOVE
    }

    public Sun(int locationX, int locationY,int raceWayIndex) {
        this.locationX = locationX;
        this.locationY = locationY - (Config.sun.getHeight() - Config.flowerFrames[0].getHeight()) / 2;
       // this.touchArea = new Rect(locationX,locationY,locationX + Config.sun.getWidth(),locationY + Config.sun.getHeight());
        this.isAlive = true;
        this.birthTime = System.currentTimeMillis();
        this.raceWayIndex = raceWayIndex;
        this.state = SunState.SHOW;
    }

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        if (isAlive){
            //对于show状态的阳光的处理
            //if (state == SunState.SHOW){  }
                //让太阳五秒中消失
            if (System.currentTimeMillis() - birthTime > 5000){
                    isAlive = false;
               //if (state == SunState.MOVE)
            }else {
                locationX += xPeed;
                if (locationX >= Config.deviceWidth){
                    isAlive = false;
                }

//                //对于move状态的阳光的处理
//                locationX -= xSpeed;
//                locationY -= ySpeed;
//                if (locationY <= 0){
//                    isAlive = false;
//                }
            }
            canvas.drawBitmap(Config.sun,locationX,locationY,paint);
            //太阳主动发起碰撞检测消灭僵尸
            GameView.getInstance().sunCheckCollision(this,raceWayIndex);

        }
    }

    @Override
    public int getModelWidth() {
        return Config.sun.getWidth();
    }

//    @Override
//    public boolean onTouch(MotionEvent event) {
//        int x = (int) event.getX();
//        int y = (int) event.getY();
//        if (touchArea.contains(x,y)){
//            state = SunState.MOVE;
//            //得到X,Y方向上的移动距离
//            xDirectionDistance = locationX - Config.sunDeadLocationX;
//            yDirectionDistance = locationY - 0;
//            xSpeed = xDirectionDistance / 10f;
//            ySpeed = yDirectionDistance / 10f;
//            return true;
//        }
//        return false;
//    }

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
