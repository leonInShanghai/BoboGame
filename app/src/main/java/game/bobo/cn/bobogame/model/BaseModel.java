package game.bobo.cn.bobogame.model;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Leon on 2018/4/22.
 * Functions:
 */

public class BaseModel {
    private int locationX;
    private int locationY;
    private boolean isAlive;

    public void drawSelf(Canvas canvas, Paint paint){

    }

    public int getModelWidth(){
        return 0;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getLocationY() {
        return locationY;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationX() {
        return locationX;
    }

}
