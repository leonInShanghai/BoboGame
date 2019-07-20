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
 * Functions: 种子银行中的向日葵  implements TouchAble
 */

public class SeedFlower extends BaseModel {
    private int locationX;
    private int locationY;
    private boolean isAlive;
    private Rect touchArea;
    private static SeedFlower seedFlower;

    /**导航栏上的向日葵，被用户选中*/
    private boolean isSelected;

    public SeedFlower(int locationX,int locationY){
        this.locationX = locationX;
        this.locationY = locationY;
        this.touchArea = new Rect(locationX,locationY,locationX + Config.seedFlower.getWidth(),locationY + Config.seedFlower.getHeight());
        this.isAlive = true;
        seedFlower = this;
    }

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        if (isAlive && isSelected == false) {
           // Log.e("false","false");
            canvas.drawBitmap(Config.seedFlower, locationX, locationY, paint);
        }else if (isAlive && isSelected){
           // Log.e("true","true");
            canvas.drawBitmap(Config.SEEDFLOWER2, locationX, locationY, paint);
        }
    }
    public boolean isTouchArea(int x,int y){
        if (touchArea.contains(x,y)){
            return true;
        }
        return false;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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

    public static SeedFlower getInstance(){
        return seedFlower;
    }

}
