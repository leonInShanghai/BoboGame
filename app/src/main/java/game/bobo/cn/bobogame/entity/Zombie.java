package game.bobo.cn.bobogame.entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import game.bobo.cn.bobogame.global.Config;
import game.bobo.cn.bobogame.model.BaseModel;
import game.bobo.cn.bobogame.view.GameView;

/**
 * Created by Leon on 2018/5/12.
 * Functions:
 */

public class Zombie extends BaseModel{
    private int locationX;
    private int locationY;
    private boolean isAlive;
    private int raceWay;
    private int frameIndex = 0;
    //僵尸前进速度
    private int xPeed = 5;
    private static Zombie zombie;

    //控制僵尸帧动画的速度（减慢速度）
    private boolean isAnimation = false;

    //游戏是否具备结束的条件-结束过了就不能再次结束了
    private boolean isGameOver = true;

    public Zombie(int locationX,int locationY,int raceWay){
        this.locationX = locationX;
        this.locationY = locationY;
        this.raceWay = raceWay;
        this.isAlive = true;
        zombie = this;
    }

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        if (isAlive){
            canvas.drawBitmap(Config.zombieFrames[frameIndex],locationX,locationY,paint);

            //减慢僵尸动画
            if (isAnimation){
                frameIndex = (frameIndex) % 7;//巧妙的解决数组越界的问题
                isAnimation = false;
            }else {
                frameIndex = (++frameIndex) % 7;//巧妙的解决数组越界的问题
                isAnimation = true;
            }
            locationX -= xPeed;

            //判断游戏结束
            if (locationX < 0){

               // Log.e("判断游戏结束","判断游戏结束");

                //移除屏幕外的僵尸对象
                if (locationX < -Config.zombieFrames[0].getWidth() && isGameOver){
                    isGameOver = false;
                    //游戏结束僵尸攻入房子-大管家GameView来处理游戏结束
                    GameView.getInstance().gameOver();
                }

            }

            //僵尸发起碰撞检测
            GameView.getInstance().checkCollision(this,raceWay);
        }
    }

    public void setxPeed(int xPeed) {
        this.xPeed = xPeed;
    }

    @Override
    public int getModelWidth() {
        return Config.zombieFrames[frameIndex].getWidth();
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

    public static Zombie getInstance(){
        return zombie;
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
