package game.bobo.cn.bobogame.entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import game.bobo.cn.bobogame.global.Config;
import game.bobo.cn.bobogame.model.BaseModel;
import game.bobo.cn.bobogame.model.TouchAble;
import game.bobo.cn.bobogame.view.GameView;

/**
 * Created by Leon on 2018/4/22.
 * Functions: implements TouchAble
 */

public class EmplaceFlower extends BaseModel  {
    private int locationX;
    private int locationY;
    private boolean isAlive;
    private Rect touchArea;
    //上次点击导航栏向日葵的时间
    private long lastTouchTime = 0;
    public static EmplaceFlower emplaceFlower;


    public EmplaceFlower(int locationX, int locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
        //touchArea 宽高都+ 10 看能解决新手机不好点击的问题不能 结果没有用有去掉了。
        this.touchArea = new Rect(locationX ,locationY,locationX + Config.flowerFrames[0].getWidth() ,locationY + Config.flowerFrames[0].getHeight() );
        this.isAlive = true;
        emplaceFlower = this;
    }

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        if (isAlive){
            canvas.drawBitmap(Config.flowerFrames[0],locationX,locationY,paint);
        }
    }

    public boolean isTouchArea(int x,int y){
        return touchArea.contains(x,y);
    }

    public void resetOffsetTo(){
        Log.e("resetOffsetTo",String.valueOf(locationX)+" y "+String.valueOf(locationY));
        touchArea.offsetTo(locationX,locationY);
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

    public Rect getTouchArea() {
        return touchArea;
    }

    public static EmplaceFlower getInstance(){
        return emplaceFlower;
    }
}
