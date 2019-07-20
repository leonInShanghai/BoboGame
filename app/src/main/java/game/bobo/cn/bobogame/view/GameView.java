package game.bobo.cn.bobogame.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import game.bobo.cn.bobogame.dialog.LeAlertDialog;
import game.bobo.cn.bobogame.entity.Bullet;
import game.bobo.cn.bobogame.entity.EmplaceFlower;
import game.bobo.cn.bobogame.entity.EmplacePea;
import game.bobo.cn.bobogame.entity.Flower;
import game.bobo.cn.bobogame.entity.Pea;
import game.bobo.cn.bobogame.entity.SeedFlower;
import game.bobo.cn.bobogame.entity.SeedPea;
import game.bobo.cn.bobogame.entity.Sun;
import game.bobo.cn.bobogame.entity.Zombie;
import game.bobo.cn.bobogame.entity.ZombieManager;
import game.bobo.cn.bobogame.global.Config;
import game.bobo.cn.bobogame.model.BaseModel;
import game.bobo.cn.bobogame.model.Plant;
import game.bobo.cn.bobogame.model.TouchAble;
import game.bobo.cn.bobogame.tools.ShowToast;

/**
 * Created by Leon on 2018/4/21.
 * Functions: 大管家所有对象都在这里处理
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback,Runnable{

    private Canvas canvas;
    private Paint paint;
    private SurfaceHolder surfaceHolder;
    private boolean gameRunFlag = false;
    private Context context;
    //豌豆是否出山 玩家达到一定分数豌豆才能出现
    private boolean isappear = true;

    //上次点击导航栏向日葵的时间
    private long lastSeedFlowerTouchTime = 0;

    //是否种子向日葵
    private boolean isSeedFlower = false;

    //是否种子向豌豆
    private boolean isSeedPea = false;

    //严重警告
    private boolean isWaring = true;

    private static GameView gameView;

    private ArrayList<BaseModel> deadList;
    private ArrayList<BaseModel> gameLayout2;
    private ArrayList<BaseModel> gameLayout1;

    private ArrayList<BaseModel> gameLayoutSun0;
    private ArrayList<BaseModel> gameLayoutBullet0;

    private ArrayList<BaseModel> gamelayout4plant0;
    private ArrayList<BaseModel> gamelayout4plant1;
    private ArrayList<BaseModel> gamelayout4plant2;
    private ArrayList<BaseModel> gamelayout4plant3;
    private ArrayList<BaseModel> gamelayout4plant4;

    private ArrayList<BaseModel> gamelayout4zombie0;
    private ArrayList<BaseModel> gamelayout4zombie1;
    private ArrayList<BaseModel> gamelayout4zombie2;
    private ArrayList<BaseModel> gamelayout4zombie3;
    private ArrayList<BaseModel> gamelayout4zombie4;

    private ZombieManager zombieManager;

    public GameView(Context context){
        super(context);
        this.context = context;
        this.paint = new Paint();
        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);
        this.gameRunFlag = true;
        gameView = this;
    }



    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        createElement();
        new Thread(this).start();
    }

    private void createElement() {

        Config.heightYDistance = Config.zombieFrames[0].getHeight() - Config.flowerFrames[0].getHeight();
        //阳光搜集终点X坐标-Leon没有用到
        Config.sunDeadLocationX = (Config.deviceWidth - Config.seedBank.getWidth()) / 2;

        gameLayoutSun0 = new ArrayList<BaseModel>();
        gameLayoutBullet0 = new ArrayList<BaseModel>();

        gameLayout2 = new ArrayList<BaseModel>();
        gameLayout1 = new ArrayList<BaseModel>();
                                                    //Config.deviceWidth - Config.seedBank.getWidth()) / 2 + Config.seedBank.getWidth() / 7 * 2
        SeedFlower seedFlower = new SeedFlower((Config.deviceWidth - Config.seedBank.getWidth()) / 2 + Config.seedBank.getWidth() / 7,0);
        gameLayout2.add(seedFlower);



        deadList = new ArrayList<BaseModel>();

        gamelayout4plant0 = new ArrayList<BaseModel>();
        gamelayout4plant1 = new ArrayList<BaseModel>();
        gamelayout4plant2 = new ArrayList<BaseModel>();
        gamelayout4plant3 = new ArrayList<BaseModel>();
        gamelayout4plant4 = new ArrayList<BaseModel>();

        gamelayout4zombie0 = new ArrayList<BaseModel>();
        gamelayout4zombie1 = new ArrayList<BaseModel>();
        gamelayout4zombie2 = new ArrayList<BaseModel>();
        gamelayout4zombie3 = new ArrayList<BaseModel>();
        gamelayout4zombie4 = new ArrayList<BaseModel>();

        zombieManager = new ZombieManager();

        for (int i = 0;i < 5;i++){
            for (int j = 0;j < 9;j++){
                Config.plantPoints.put(i * 10 + j,new Point((j + 2)
                        * Config.deviceWidth / 11
                        - Config.deviceWidth / 11 / 2,(i + 1)
                        * Config.deviceHeight / 6));
                //计算跑到Y坐标，并将Y坐标存放至raceWayYpoints[i]
                if (j == 0){
                     Config.raceWayYpoints[i] = (i + 1) * Config.deviceHeight / 6;
                }

            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void run() {
        while (gameRunFlag){
            //加锁避免多个线程同事画一块画布surfaceHolder-锁的对象
            synchronized (surfaceHolder){
                try {
                    canvas = surfaceHolder.lockCanvas();
                    //画游戏的背景
                    canvas.drawBitmap(Config.gameBK,0,0,paint);
                    //画seedbank（导航栏）
                    canvas.drawBitmap(Config.seedBank,Config.sunDeadLocationX,0,paint);
                    updateData();
                    ondraw(canvas);
                }catch (Exception e){
                    Log.e("GameView-run()方法中报错",e.toString());
                }finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //数据的更新-移除dead的对象
    private void updateData(){
        deadList.clear();
        for (BaseModel model : gameLayout1){
            if (!model.isAlive()){
                deadList.add(model);
            }
        }
        for (BaseModel model : gameLayout2){
            if (!model.isAlive()){
                deadList.add(model);
            }
        }

        //添加过期的阳光到deadList
        for (BaseModel model : gameLayoutSun0){
            if (!model.isAlive()){
                deadList.add(model);
            }
        }

        //添加过期的子弹到deadList
        for (BaseModel model : gameLayoutBullet0){
            if (!model.isAlive()){
                deadList.add(model);
            }
        }

        for (BaseModel model : gamelayout4zombie0){
            if (!model.isAlive()){
                deadList.add(model);
            }
        }

        for (BaseModel model : gamelayout4zombie1){
            if (!model.isAlive()){
                deadList.add(model);
            }
        }

        for (BaseModel model : gamelayout4zombie2){
            if (!model.isAlive()){
                deadList.add(model);
            }
        }

        for (BaseModel model : gamelayout4zombie3){
            if (!model.isAlive()){
                deadList.add(model);
            }
        }

        for (BaseModel model : gamelayout4zombie4){
            if (!model.isAlive()){
                deadList.add(model);
            }
        }

        for (BaseModel model : gamelayout4plant0){
            if (!model.isAlive()){
                deadList.add(model);
            }
        }

        for (BaseModel model : gamelayout4plant1){
            if (!model.isAlive()){
                deadList.add(model);
            }
        }

        for (BaseModel model : gamelayout4plant2){
            if (!model.isAlive()){
                deadList.add(model);
            }
        }

        for (BaseModel model : gamelayout4plant3){
            if (!model.isAlive()){
                deadList.add(model);
            }
        }

        for (BaseModel model : gamelayout4plant4){
            if (!model.isAlive()){
                deadList.add(model);
            }
        }

        for (BaseModel model : deadList){
            gameLayout1.remove(model);
            gameLayout2.remove(model);
            gameLayoutSun0.remove(model);
            gameLayoutBullet0.remove(model);
            gamelayout4zombie0.remove(model);
            gamelayout4zombie1.remove(model);
            gamelayout4zombie2.remove(model);
            gamelayout4zombie3.remove(model);
            gamelayout4zombie4.remove(model);
            //移除过期的阳光
            gamelayout4plant0.remove(model);
            gamelayout4plant1.remove(model);
            gamelayout4plant2.remove(model);
            gamelayout4plant3.remove(model);
            gamelayout4plant4.remove(model);
        }
    }

    //把所有的元素画到game view上
    protected void ondraw(Canvas canvas){

        //用来显示分数用的
       paint.setTextSize(50);
       paint.setColor(Color.GREEN);

       if (Config.SCORE > 100 ){
           canvas.drawText( String.valueOf(Config.SCORE),50,50,paint);
       } else if (Config.SCORE > 0 && Config.SCORE <= 100){
           canvas.drawText( "SCORE: "+Config.SCORE,50,50,paint);
       }else {
           canvas.drawText( "快种植物",50,50,paint);
       }

        zombieManager.drawSelf(canvas,paint);

        for (BaseModel model : gamelayout4plant0){
            model.drawSelf(canvas,paint);
        }
        for (BaseModel model : gamelayout4plant1){
            model.drawSelf(canvas,paint);
        }
        for (BaseModel model : gamelayout4plant2){
            model.drawSelf(canvas,paint);
        }
        for (BaseModel model : gamelayout4plant3){
            model.drawSelf(canvas,paint);
        }
        for (BaseModel model : gamelayout4plant4){
            model.drawSelf(canvas,paint);
        }
        //僵尸
        for (BaseModel model : gamelayout4zombie0){
            model.drawSelf(canvas,paint);
        }
        for (BaseModel model : gamelayout4zombie1){
            model.drawSelf(canvas,paint);
        }
        for (BaseModel model : gamelayout4zombie2){
            model.drawSelf(canvas,paint);
        }
        for (BaseModel model : gamelayout4zombie3){
            model.drawSelf(canvas,paint);
        }
        for (BaseModel model : gamelayout4zombie4){
            model.drawSelf(canvas,paint);
        }
        //画第三层阳光
        for (BaseModel model : gameLayoutSun0){
            model.drawSelf(canvas,paint);
        }
        //画子弹
        for (BaseModel model : gameLayoutBullet0){
            model.drawSelf(canvas,paint);
        }
        for (BaseModel model : gameLayout2){
            model.drawSelf(canvas,paint);
        }
        for (BaseModel model : gameLayout1){
            model.drawSelf(canvas,paint);
        }
    }

    //响应点击事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();


        //避免空指针
        if (gameLayout2 == null){ return false; };

        //用户点击了种子银行的豌豆
        for (BaseModel model : gameLayout2){
            // Log.e("gameLayout1","进入for循环");
            if (model instanceof SeedPea){
                if (SeedPea.getInstance().isTouchArea(x,y) && System.currentTimeMillis() - lastSeedFlowerTouchTime > 500) {
                    lastSeedFlowerTouchTime =  System.currentTimeMillis();
                    //用户选中了豌豆向日葵自然取消选择
                    if (SeedFlower.getInstance().isSelected()){
                        SeedFlower.getInstance().setSelected(false);
                        isSeedFlower = false;
                    }
                    //如果种子银行的豌豆为false
                    if (SeedPea.getInstance().isSelected() == false ) {
                        //并且生产一个EmplaceFlower
                        gameLayout1.clear();
                        if (gameLayout1.size() < 1) {//gameLayout1中最多只能放一个植物
                            isSeedPea = true;
                            //(Config.deviceWidth - Config.seedBank.getWidth()) / 2 + Config.seedBank.getWidth() / 7 * 2,0
                            gameLayout1.add(new EmplacePea((Config.deviceWidth - Config.seedBank.getWidth()) / 2 + Config.seedBank.getWidth() / 7 * 2 - 15, 0));
                        }
                        //最后让它变为true
                        SeedPea.getInstance().setSelected(true);
                    } else {
                        gameLayout1.clear();
                        isSeedPea = false;
                        //变为false一定要放在后面
                        SeedPea.getInstance().setSelected(false);
                    }
                    return true;

                }
            }
        }


        //用户点击了种子银行的向日葵
        for (BaseModel model : gameLayout2){
           // Log.e("gameLayout1","进入for循环");
            if (model instanceof SeedFlower){
                if (SeedFlower.getInstance().isTouchArea(x,y) && System.currentTimeMillis() - lastSeedFlowerTouchTime > 500) {
                    lastSeedFlowerTouchTime =  System.currentTimeMillis();
                    //用户选择了向日葵豌豆自然取消选中
                    if (SeedPea.getInstance() != null && SeedPea.getInstance().isSelected()){
                        SeedPea.getInstance().setSelected(false);
                        isSeedPea = false;
                    }
                    //如果种子银行的向日葵为false
                    if (SeedFlower.getInstance().isSelected() == false ) {
                        //并且生产一个EmplaceFlower
                        gameLayout1.clear();
                        if (gameLayout1.size() < 1) {//gameLayout1中最多只能放一个植物
                            isSeedFlower = true;
                            gameLayout1.add(new EmplaceFlower((Config.deviceWidth - Config.seedBank.getWidth()) / 2 + Config.seedBank.getWidth() / 8, 0));
                        }
                        //最后让它变为true
                        SeedFlower.getInstance().setSelected(true);
                    } else {
                        gameLayout1.clear();
                        isSeedFlower = false;
                        //变为false一定要放在后面
                        SeedFlower.getInstance().setSelected(false);
                    }
                    return true;

                }
            }
        }

        //避免空指针
        if (gameLayout1 == null){ return false; };

        //&& isappear == false
        if (isSeedPea == true && gameLayout1.size() == 1 && System.currentTimeMillis() - lastSeedFlowerTouchTime > 500){
            lastSeedFlowerTouchTime =  System.currentTimeMillis();
            applay4Plant(x, y, EmplacePea.getInstance());
            gameLayout1.clear();
            SeedPea.getInstance().setSelected(false);
            isSeedPea = false;
            return true;
        }


        if (isSeedFlower == true && gameLayout1.size() == 1 && System.currentTimeMillis() - lastSeedFlowerTouchTime > 500){
            lastSeedFlowerTouchTime =  System.currentTimeMillis();
            applay4Plant(x, y, EmplaceFlower.getInstance());
            gameLayout1.clear();
            SeedFlower.getInstance().setSelected(false);
            isSeedFlower = false;
            return true;
        }

        return false;

    }

    //静态方法方便其他类获取GameView
    public static GameView getInstance(){
        return gameView;
    }

    //添加emplace state植物
    public void applay4EmplacePlant(int locationX,int locationY,BaseModel model) {
        synchronized (surfaceHolder){
            //gameLayout1放的是正在安放状态的植物
            if (gameLayout1.size() < 1) {//gameLayout1中最多只能放一个植物
                if (model instanceof SeedPea){//EmplacePea
                    gameLayout1.add(new EmplacePea(locationX, locationY));
                }else if (model instanceof SeedFlower){//EmplaceFlower
                    gameLayout1.add(new EmplaceFlower(locationX, locationY));
                }
            }
        }
    }

    //种植植物
    public void applay4Plant(int locationX, int locationY, BaseModel baseModel) {

        //Log.e("种植",String.valueOf(locationX));
        Point point;
        for (Integer key : Config.plantPoints.keySet()){
            point = Config.plantPoints.get(key);
            //判断合适植物安放的目标点
            if ((Math.abs(locationX - point.x) < Config.deviceWidth / 11 / 2) &&
                    (Math.abs(locationY - point.y) < Config.deviceHeight / 6 / 2)){
                //用来确定植物的跑道
                int raceWayIndex = 6;
                for (int i = 0;i < Config.raceWayYpoints.length;i++){
                    if (point.y == Config.raceWayYpoints[i]){
                        raceWayIndex = i;
                    }
                }

                if (isPlantExist(key,raceWayIndex)){
                    switch (raceWayIndex){
                        case 0:
                            if (baseModel instanceof EmplacePea){
                                gamelayout4plant0.add(new Pea(point.x,point.y,key,raceWayIndex));
                            }else if (baseModel instanceof EmplaceFlower){
                                gamelayout4plant0.add(new Flower(point.x,point.y,key,raceWayIndex));
                            }
                            break;
                        case 1:
                            if (baseModel instanceof EmplacePea){
                                gamelayout4plant1.add(new Pea(point.x,point.y,key,raceWayIndex));
                            }else if (baseModel instanceof EmplaceFlower){
                                gamelayout4plant1.add(new Flower(point.x,point.y,key,raceWayIndex));
                            }
                            break;
                        case 2:
                            if (baseModel instanceof EmplacePea){
                                gamelayout4plant2.add(new Pea(point.x,point.y,key,raceWayIndex));
                            }else if (baseModel instanceof EmplaceFlower){
                                gamelayout4plant2.add(new Flower(point.x,point.y,key,raceWayIndex));
                            }
                            break;
                        case 3:
                            if (baseModel instanceof EmplacePea){
                                gamelayout4plant3.add(new Pea(point.x,point.y,key,raceWayIndex));
                            }else if (baseModel instanceof EmplaceFlower){
                                gamelayout4plant3.add(new Flower(point.x,point.y,key,raceWayIndex));
                            }
                            break;
                        case 4:
                            if (baseModel instanceof EmplacePea){
                                gamelayout4plant4.add(new Pea(point.x,point.y,key,raceWayIndex));
                            }else if (baseModel instanceof EmplaceFlower){
                                gamelayout4plant4.add(new Flower(point.x,point.y,key,raceWayIndex));
                            }
                            break;
                    }
                }
            }
        }
    }

    //判断已有位置上是否被占用
    private boolean isPlantExist(int key,int raceWayIndex){
        switch (raceWayIndex){
            case 0:
                for (BaseModel model : gamelayout4plant0){
                    if (model instanceof Plant){
                        if (key == ((Plant) model).getmapIndex()){
                            return false;
                        }
                    }
                }
                break;
            case 1:
                for (BaseModel model : gamelayout4plant1){
                    if (model instanceof Plant){
                        if (key == ((Plant) model).getmapIndex()){
                            return false;
                        }
                    }
                }
                break;
            case 2:
                for (BaseModel model : gamelayout4plant2){
                    if (model instanceof Plant){
                        if (key == ((Plant) model).getmapIndex()){
                            return false;
                        }
                    }
                }
                break;
            case 3:
                for (BaseModel model : gamelayout4plant3){
                    if (model instanceof Plant){
                        if (key == ((Plant) model).getmapIndex()){
                            return false;
                        }
                    }
                }
                break;
            case 4:
                for (BaseModel model : gamelayout4plant4){
                    if (model instanceof Plant){
                        if (key == ((Plant) model).getmapIndex()){
                            return false;
                        }
                    }
                }
                break;
        }
        return true;
    }

    //被Flower请求，用于产生阳光
    public void giveBirth2Sun(int locationX, int locationY,int raceWayIndex) {

        synchronized (surfaceHolder){
            switch (raceWayIndex){
                case 0:
                    // gameLayout3. gamelayout4plant0
                    gameLayoutSun0.add(new Sun(locationX,locationY,raceWayIndex));
                    break;
                case 1:
                    gameLayoutSun0.add(new Sun(locationX,locationY,raceWayIndex));
                    break;
                case 2:
                    gameLayoutSun0.add(new Sun(locationX,locationY,raceWayIndex));
                    break;
                case 3:
                    gameLayoutSun0.add(new Sun(locationX,locationY,raceWayIndex));
                    break;
                case 4:
                    gameLayoutSun0.add(new Sun(locationX,locationY,raceWayIndex));
                    break;
            }

        }
    }

    //ZombieManager请求，用于画僵尸
    public void apply4AddZombie() {
        synchronized (surfaceHolder){
            //产生随机数让僵尸出现在随机的跑道上
            int raceWay = (int) (Math.random() * 5);
            switch (raceWay){
                case 0:
                    //Config.deviceWidth + 20：为了让僵尸出现不突兀
                    gamelayout4zombie0.add(new Zombie(Config.deviceWidth + 20,Config.raceWayYpoints[raceWay] - Config.heightYDistance,raceWay));
                    break;
                case 1:
                    gamelayout4zombie1.add(new Zombie(Config.deviceWidth + 20,Config.raceWayYpoints[raceWay] - Config.heightYDistance,raceWay));
                    break;
                case 2:
                    gamelayout4zombie2.add(new Zombie(Config.deviceWidth + 20,Config.raceWayYpoints[raceWay] - Config.heightYDistance,raceWay));
                    break;
                case 3:
                    gamelayout4zombie3.add(new Zombie(Config.deviceWidth + 20,Config.raceWayYpoints[raceWay] - Config.heightYDistance,raceWay));
                    break;
                case 4:
                    gamelayout4zombie4.add(new Zombie(Config.deviceWidth + 20,Config.raceWayYpoints[raceWay] - Config.heightYDistance,raceWay));
                    break;
            }
        }
    }

    /**处理碰撞检测,碰撞检测的发起者是僵尸*/
    public void checkCollision(Zombie zombie,int raceWay) {
           synchronized (surfaceHolder){
                switch (raceWay){
                    case 0:
                        for (BaseModel model : gamelayout4plant0){
                            if (Math.abs((model.getLocationX() + model.getModelWidth() / 2) -
                                    (zombie.getLocationX() + zombie.getModelWidth() / 2))
                                    < Math.abs((model.getModelWidth() + zombie.getModelWidth()) / 2 )){
                                 if (model instanceof Plant){
                                     model.setAlive(false);
                                 }else {
                                     zombie.setAlive(false);
                                 }
                            }
                        }
                        break;
                    case 1:
                        for (BaseModel model : gamelayout4plant1){
                            if (Math.abs((model.getLocationX() + model.getModelWidth() / 2) -
                                    (zombie.getLocationX() + zombie.getModelWidth() / 2))
                                    < Math.abs((model.getModelWidth() + zombie.getModelWidth()) / 2 )){
                                if (model instanceof Plant){
                                    model.setAlive(false);
                                }else {
                                    zombie.setAlive(false);
                                }
                            }
                        }
                         break;
                    case 2:
                        for (BaseModel model : gamelayout4plant2){
                            if (Math.abs((model.getLocationX() + model.getModelWidth() / 2) -
                                    (zombie.getLocationX() + zombie.getModelWidth() / 2))
                                    < Math.abs((model.getModelWidth() + zombie.getModelWidth()) / 2 )){
                                if (model instanceof Plant){
                                    model.setAlive(false);
                                }else {
                                    zombie.setAlive(false);
                                }
                            }
                        }
                        break;
                    case 3:
                        for (BaseModel model : gamelayout4plant3){
                            if (Math.abs((model.getLocationX() + model.getModelWidth() / 2) -
                                    (zombie.getLocationX() + zombie.getModelWidth() / 2))
                                    < Math.abs((model.getModelWidth() + zombie.getModelWidth()) / 2 )){
                                if (model instanceof Plant){
                                    model.setAlive(false);
                                }else {
                                    zombie.setAlive(false);
                                }
                            }
                        }
                        break;
                    case 4:
                        for (BaseModel model : gamelayout4plant4){
                            if (Math.abs((model.getLocationX() + model.getModelWidth() / 2) -
                                    (zombie.getLocationX() + zombie.getModelWidth() / 2))
                                    < Math.abs((model.getModelWidth() + zombie.getModelWidth()) / 2 )){
                                if (model instanceof Plant){
                                    model.setAlive(false);
                                }else {
                                    zombie.setAlive(false);
                                }
                            }
                        }
                        break;
                }
           }
    }

    /**处理碰撞检测,碰撞检测的发起者是太阳*/
    public void sunCheckCollision(Sun sun,int raceWay) {
        synchronized (surfaceHolder){
            switch (raceWay){
                case 0:
                    for (BaseModel model : gamelayout4zombie0){
                        if (Math.abs((model.getLocationX() + model.getModelWidth() / 2) -
                                (sun.getLocationX() + sun.getModelWidth() / 2))
                                < Math.abs((model.getModelWidth() + sun.getModelWidth()) / 2 )){
                                model.setAlive(false);
                                sun.setAlive(false);
                                Config.SCORE += 4;
                                difficultiesAreAdjusted();
                        }
                    }
                    break;
                case 1:
                    for (BaseModel model :  gamelayout4zombie1){
                        if (Math.abs((model.getLocationX() + model.getModelWidth() / 2) -
                                (sun.getLocationX() + sun.getModelWidth() / 2))
                                < Math.abs((model.getModelWidth() + sun.getModelWidth()) / 2 )){
                                model.setAlive(false);
                                sun.setAlive(false);
                                Config.SCORE += 4;
                                difficultiesAreAdjusted();
                        }
                    }
                    break;
                case 2:
                    for (BaseModel model : gamelayout4zombie2){
                        if (Math.abs((model.getLocationX() + model.getModelWidth() / 2) -
                                (sun.getLocationX() + sun.getModelWidth() / 2))
                                < Math.abs((model.getModelWidth() + sun.getModelWidth()) / 2 )){
                                model.setAlive(false);
                                sun.setAlive(false);
                                Config.SCORE += 4;
                                difficultiesAreAdjusted();
                        }
                    }
                    break;
                case 3:
                    for (BaseModel model : gamelayout4zombie3){
                        if (Math.abs((model.getLocationX() + model.getModelWidth() / 2) -
                                (sun.getLocationX() + sun.getModelWidth() / 2))
                                < Math.abs((model.getModelWidth() + sun.getModelWidth()) / 2 )){
                                 model.setAlive(false);
                                 sun.setAlive(false);
                                 Config.SCORE += 4;
                                 difficultiesAreAdjusted();
                        }
                    }
                    break;
                case 4:
                    for (BaseModel model : gamelayout4zombie4){
                        if (Math.abs((model.getLocationX() + model.getModelWidth() / 2) -
                                (sun.getLocationX() + sun.getModelWidth() / 2))
                                < Math.abs((model.getModelWidth() + sun.getModelWidth()) / 2 )){
                                model.setAlive(false);
                                sun.setAlive(false);
                                Config.SCORE += 4;
                                difficultiesAreAdjusted();
                        }
                    }
                    break;
            }
        }
    }

    //被Pea请求，用于产生子弹
    public void giveBirth2Bullet(int locationX, int locationY, int raceWayIndex) {
        synchronized (surfaceHolder){
            gameLayoutBullet0.add(new Bullet(locationX,locationY,raceWayIndex));
        }
    }

    //处理碰撞检测 发起者是子弹
    public void bulletCheckCollision(Bullet bullet, int raceWay) {
        synchronized (surfaceHolder){
            switch (raceWay){
                case 0:
                    for (BaseModel model : gamelayout4zombie0){
                        if (Math.abs((model.getLocationX() + model.getModelWidth() / 2) -
                                (bullet.getLocationX() + bullet.getModelWidth() / 2))
                                < Math.abs((model.getModelWidth() + bullet.getModelWidth()) / 2 )){
                            model.setAlive(false);
                            bullet.setAlive(false);
                            difficultiesAreAdjusted();
                        }
                    }
                    break;
                case 1:
                    for (BaseModel model :  gamelayout4zombie1){
                        if (Math.abs((model.getLocationX() + model.getModelWidth() / 2) -
                                (bullet.getLocationX() + bullet.getModelWidth() / 2))
                                < Math.abs((model.getModelWidth() + bullet.getModelWidth()) / 2 )){
                            model.setAlive(false);
                            bullet.setAlive(false);
                            difficultiesAreAdjusted();
                        }
                    }
                    break;
                case 2:
                    for (BaseModel model :  gamelayout4zombie2){
                        if (Math.abs((model.getLocationX() + model.getModelWidth() / 2) -
                                (bullet.getLocationX() + bullet.getModelWidth() / 2))
                                < Math.abs((model.getModelWidth() + bullet.getModelWidth()) / 2 )){
                            model.setAlive(false);
                            bullet.setAlive(false);
                            difficultiesAreAdjusted();
                        }
                    }
                    break;
                case 3:
                    for (BaseModel model :  gamelayout4zombie3){
                        if (Math.abs((model.getLocationX() + model.getModelWidth() / 2) -
                                (bullet.getLocationX() + bullet.getModelWidth() / 2))
                                < Math.abs((model.getModelWidth() + bullet.getModelWidth()) / 2 )){
                            model.setAlive(false);
                            bullet.setAlive(false);
                            difficultiesAreAdjusted();
                        }
                    }
                    break;
                case 4:
                    for (BaseModel model :  gamelayout4zombie4){
                        if (Math.abs((model.getLocationX() + model.getModelWidth() / 2) -
                                (bullet.getLocationX() + bullet.getModelWidth() / 2))
                                < Math.abs((model.getModelWidth() + bullet.getModelWidth()) / 2 )){
                            model.setAlive(false);
                            bullet.setAlive(false);
                            difficultiesAreAdjusted();
                        }
                    }
                    break;
            }
        }
    }


    private void difficultiesAreAdjusted(){
        Config.SCORE += 1;

        if (Config.SCORE > 1120){
            zombieManager.setDeadTime(1);
            Zombie.getInstance().setxPeed(30);//僵尸前进速度加快
        }else if (Config.SCORE > 1110){
            if(isWaring) {
                isWaring = false;
                ShowToast.showUiToast(context, this, "僵尸即将进行报复！");
            }
        }else if (Config.SCORE > 900 ){
            zombieManager.setDeadTime(500);
        }else if (Config.SCORE > 280){
            zombieManager.setDeadTime(300);
        }else if (Config.SCORE > 260){
            //只出场一次以后不再出场
            isappear = false;
            ShowToast.showUiToast(context,this,"使用豌豆消灭更多僵尸");
            SeedPea seedPea = new SeedPea((Config.deviceWidth - Config.seedBank.getWidth()) / 2 + Config.seedBank.getWidth() / 7 * 2,0);
            gameLayout2.add(seedPea);
            zombieManager.setDeadTime(1000);
         }else if (Config.SCORE > 200) {
            zombieManager.setDeadTime(2000);
         } else if (Config.SCORE > 150) {
             zombieManager.setDeadTime(800);
         }else if (Config.SCORE > 145) {
            ShowToast.showUiToast(context,this,"一大波僵尸即将来袭");
        }else if (Config.SCORE > 100){
             zombieManager.setDeadTime(2000);
        } else if (Config.SCORE > 60) {
             zombieManager.setDeadTime(800);
        }else if (Config.SCORE > 55) {
            ShowToast.showUiToast(context,this,"一大波僵尸即将来袭");
            //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
        } else if (Config.SCORE > 5){
             //控制僵尸慢慢变多增加游戏难度
             zombieManager.setDeadTime(3000);
             //这里注释掉了 豌豆在用户积累一定分数后出场
             //SeedPea seedPea = new SeedPea((Config.deviceWidth - Config.seedBank.getWidth()) / 2 + Config.seedBank.getWidth() / 7 * 2,0);
             //gameLayout2.add(seedPea);
        }
    }

    //游戏结束弹出提并且退出游戏
    public void gameOver() {
        // 无论是是在子线程，还是主线程都处理
        ShowToast.showUiAlert(context,gameView,Config.SCORE);
        //停止游戏循环工作 - 清空数据
        gameRunFlag = false;
        deadList.clear();
        gameLayout2.clear();
        gameLayout1.clear();
        gameLayoutSun0.clear();
        gameLayoutBullet0.clear();
        gamelayout4plant0.clear();
        gamelayout4plant1.clear();
        gamelayout4plant2.clear();
        gamelayout4plant3.clear();
        gamelayout4plant4.clear();
        gamelayout4zombie0.clear();
        gamelayout4zombie1.clear();
        gamelayout4zombie2.clear();
        gamelayout4zombie3.clear();
        gamelayout4zombie4.clear();
        zombieManager = null;
        Config.SCORE = 0;
    }

}
