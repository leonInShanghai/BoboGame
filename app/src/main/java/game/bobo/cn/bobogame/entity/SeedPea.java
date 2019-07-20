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
 * Functions:种子银行上的豌豆  Leon修改不再实现 implements TouchAble
 */

public class SeedPea extends BaseModel {
    private int locationX;
    private int locationY;
    private boolean isAlive;
    private Rect touchArea;
    private static SeedPea seedPea;

    /**导航栏上的豌豆，被用户选中*/
    private boolean isSelected;

    public SeedPea(int locationX, int locationY){
        this.locationX = locationX;
        this.locationY = locationY;
        this.touchArea = new Rect(locationX,locationY,locationX + Config.seedPea.getWidth(),locationY + Config.seedPea.getHeight());
        this.isAlive = true;
        seedPea = this;
    }

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {

        if (isAlive && isSelected == false) {
            canvas.drawBitmap(Config.seedPea, locationX, locationY, paint);
        }else if (isAlive && isSelected){
            canvas.drawBitmap(Config.SEEDPEA2, locationX, locationY, paint);
        }
    }

    public boolean isTouchArea(int x,int y){
        if (touchArea.contains(x,y)){
            return true;
        }
        return false;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {

        return isSelected;
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

    public static SeedPea getInstance(){
        return seedPea;
    }

}
