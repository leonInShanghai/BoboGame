package game.bobo.cn.bobogame.entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.StrictMode;
import android.util.Log;
import android.view.MotionEvent;

import game.bobo.cn.bobogame.global.Config;
import game.bobo.cn.bobogame.model.BaseModel;
import game.bobo.cn.bobogame.model.TouchAble;
import game.bobo.cn.bobogame.view.GameView;

/**
 * Created by Leon on 2018/4/22.
 * Functions:
 */

public class EmplacePea extends BaseModel {
    private int locationX;
    private int locationY;
    private boolean isAlive;
    private Rect touchArea;
    //上次点导航栏上豌豆的时间
    private long lastTouchTime = 0;
    private static EmplacePea emplacePea;

    public EmplacePea(int locationX, int locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.touchArea = new Rect(locationX,locationY,locationX + Config.peaFrames[0].getWidth(),locationY + Config.peaFrames[0].getHeight());
        this.isAlive = true;
        emplacePea = this;
    }

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        if (isAlive){
            canvas.drawBitmap(Config.peaFrames[0],locationX,locationY,paint);
        }
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

    public static EmplacePea getInstance(){
        return emplacePea;
    }
}
