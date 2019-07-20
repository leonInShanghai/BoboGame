package game.bobo.cn.bobogame.global;

import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.Point;

public class Config {
	public static float scaleWidth;
	public static float scaleHeight;
	
	public static int deviceWidth;
	public static int deviceHeight;
	
	public static Bitmap gameBK;
	public static Bitmap seedBank;
	public static Bitmap seedFlower;
	//种子银行向日葵的选中状态
	public static Bitmap SEEDFLOWER2;
	public static Bitmap seedPea;
	//种子银行的豌豆的选中状态
	public static Bitmap SEEDPEA2;
	public static Bitmap sun;
	public static Bitmap bullet;

	//玩家的分数
	public static long SCORE;


	public static Bitmap[] flowerFrames = new Bitmap[8];
	public static Bitmap[] peaFrames = new Bitmap[8];
	public static Bitmap[] zombieFrames = new Bitmap[7];
	
    /*plantPoints  里面存放了所有合适的植物安放点的集合**/
	public static HashMap<Integer, Point> plantPoints = new HashMap<Integer, Point>();
	public static int[] raceWayYpoints = new int[5];
	
	public static int sunDeadLocationX;
	
    //僵尸和植物图片的高度差
	public static int heightYDistance;

}
