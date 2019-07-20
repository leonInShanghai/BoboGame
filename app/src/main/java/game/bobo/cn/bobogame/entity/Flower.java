package game.bobo.cn.bobogame.entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import game.bobo.cn.bobogame.global.Config;
import game.bobo.cn.bobogame.model.BaseModel;
import game.bobo.cn.bobogame.model.Plant;
import game.bobo.cn.bobogame.view.GameView;

/**
 * Created by Leon on 2018/4/30.
 * Functions:向日葵
 */

public class Flower extends BaseModel implements Plant{
    private int locationX;
    private int locationY;
    private boolean isAlive;
    private int frameIndex = 0;
    private int mapIndex;
    private int raceWayIndex;
    private long lastBirthTime = System.currentTimeMillis() - 6000;

    public Flower(int locationX, int locationY, int mapIndex,int raceWayIndex) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.mapIndex = mapIndex;
        this.raceWayIndex = raceWayIndex;
        isAlive = true;
    }

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        if (isAlive){
            canvas.drawBitmap(Config.flowerFrames[frameIndex],locationX,locationY,paint);
            //下面这个方法巧妙的处理的数组越界的问题
            frameIndex =++frameIndex % 8;

            //每过10秒中产生一个阳光
            if (System.currentTimeMillis() - lastBirthTime > 10000){
                lastBirthTime = System.currentTimeMillis();
                giveBirth2Sun();
            }
        }
    }

    @Override
    public int getModelWidth() {
        return Config.flowerFrames[frameIndex].getWidth();
    }

    //产生阳光
    private void giveBirth2Sun(){
        GameView.getInstance().giveBirth2Sun(locationX,locationY,raceWayIndex);
    }

    @Override
    public int getLocationX() {
        return locationX;
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
    public int getLocationY() {
        return locationY;
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public int getmapIndex() {
        return mapIndex;
    }
}
