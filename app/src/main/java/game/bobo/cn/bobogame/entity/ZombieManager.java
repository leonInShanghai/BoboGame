package game.bobo.cn.bobogame.entity;

import android.graphics.Canvas;
import android.graphics.Paint;

import game.bobo.cn.bobogame.model.BaseModel;
import game.bobo.cn.bobogame.view.GameView;

/**
 * Created by Leon on 2018/5/12.
 * Functions:
 */

public class ZombieManager extends BaseModel{
    private boolean isAlive;
    private long lastBirthTime = 0;
    private int deadTime = 15000;



    public ZombieManager (){
        this.isAlive = true;
    }

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        if (System.currentTimeMillis() - lastBirthTime > deadTime){
            lastBirthTime = System.currentTimeMillis();
            giveBirth2Zombie();
        }
    }

    private void giveBirth2Zombie(){
        //和游戏大管家gameview请求加入僵尸
        GameView.getInstance().apply4AddZombie();
    }

    //控制僵尸多与少的关键变量
    public void setDeadTime(int deadTime) {
        this.deadTime = deadTime;
    }
}
