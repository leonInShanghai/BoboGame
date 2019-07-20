package game.bobo.cn.bobogame.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Bundle;
import android.view.MotionEvent;

import java.util.Timer;
import java.util.TimerTask;

import game.bobo.cn.bobogame.R;
import game.bobo.cn.bobogame.global.Config;
import game.bobo.cn.bobogame.tools.DeviceTools;
import game.bobo.cn.bobogame.view.GameView;

//AppCompatActivity
public class IcastGameActivity extends Activity {

    private GameView gameView;
    private Handler handler=null;
    private static IcastGameActivity icastGameActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建属于主线程的handler
        handler = new Handler();
        setContentView(R.layout.activity_icast_game);
        startGame();
        icastGameActivity = this;
    }

    private void startGame() {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            public void run() {
                handler.post(runnableUi);
                cancel();
            }
        }, 1500,1);
    }

    // 构建Runnable对象，在runnable中更新界面
    Runnable   runnableUi=new  Runnable(){
        @Override
        public void run() {
            //更新界面
            init();
            //gameView = new GameView(IcastGameActivity.this);
            //setContentView(gameView);
        }
    };

    private void init() {
        Config.deviceWidth = DeviceTools.getDeviceInfo(this)[0];
        Config.deviceHeight = DeviceTools.getDeviceInfo(this)[1];

        Config.gameBK = BitmapFactory.decodeResource(getResources(),R.drawable.bk);
        Config.scaleWidth = Config.deviceWidth / (float)Config.gameBK.getWidth();
        Config.scaleHeight = Config.deviceHeight / (float)Config.gameBK.getHeight();

        Config.gameBK = DeviceTools.resizeBitmap(Config.gameBK);
        Config.seedBank = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.seedbank));
        Config.sun = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.sun));
        Config.bullet = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.bullet));

        //图片不能等比缩放
        Config.seedFlower = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.seed_flower),Config.seedBank.getWidth() / 8,Config.seedBank.getHeight());
        Config.SEEDFLOWER2 = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.seed_flower2),Config.seedBank.getWidth() / 8,Config.seedBank.getHeight());
        Config.seedPea = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.seed_pea),Config.seedBank.getWidth() / 8,Config.seedBank.getHeight());
        Config.SEEDPEA2 = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.seed_pea2),Config.seedBank.getWidth() / 8,Config.seedBank.getHeight());

        Config.flowerFrames[0] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.p_1_01));
        Config.flowerFrames[1] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.p_1_02));
        Config.flowerFrames[2] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.p_1_03));
        Config.flowerFrames[3] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.p_1_04));
        Config.flowerFrames[4] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.p_1_05));
        Config.flowerFrames[5] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.p_1_06));
        Config.flowerFrames[6] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.p_1_07));
        Config.flowerFrames[7] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.p_1_08));

        Config.peaFrames[0] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.p_2_01));
        Config.peaFrames[1] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.p_2_02));
        Config.peaFrames[2] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.p_2_03));
        Config.peaFrames[3] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.p_2_04));
        Config.peaFrames[4] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.p_2_05));
        Config.peaFrames[5] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.p_2_06));
        Config.peaFrames[6] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.p_2_07));
        Config.peaFrames[7] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.p_2_08));

        Config.zombieFrames[0] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.z_1_01));
        Config.zombieFrames[1] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.z_1_02));
        Config.zombieFrames[2] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.z_1_03));
        Config.zombieFrames[3] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.z_1_04));
        Config.zombieFrames[4] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.z_1_05));
        Config.zombieFrames[5] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.z_1_06));
        Config.zombieFrames[6] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.z_1_07));

        if (gameView == null) {
            gameView = new GameView(IcastGameActivity.this);
        }
        setContentView(gameView);
    }


    //leon注释掉 touch事件冲突
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //避免空指针异常闪退
        if (gameView != null){
            return gameView.onTouchEvent(event);
        }

        return super.onTouchEvent(event);
    }


    public static IcastGameActivity getInstance(){
        return icastGameActivity;
    }

}
