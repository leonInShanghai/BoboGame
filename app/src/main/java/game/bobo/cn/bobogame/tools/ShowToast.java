package game.bobo.cn.bobogame.tools;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import game.bobo.cn.bobogame.dialog.LeAlertDialog;
import game.bobo.cn.bobogame.global.Config;
import game.bobo.cn.bobogame.view.GameView;

/**
 * Created by Leon on 2018/5/16.
 * Functions:
 */

public class ShowToast {


    //弹出自定义的弹框提示显示分数
    public static void showUiAlert(final Context context, GameView gameView, final long score){

        // 判断是在子线程，还是主线程String dialog_title,String dialogmsg
        if("main".equals(Thread.currentThread().getName())){
            String gradeString = "";
            String scoreString = "";

            if (score > 1130){
                gradeString = "等级：无敌！";
                scoreString = "敬请期待机枪豌豆！";
            }else  if (score > 900 && score <= 1130 ){
                gradeString = "等级：十钱天师";
                scoreString = "分数："+score;
            }else if (score > 800 && score <= 900){
                gradeString = "等级：九钱天师";
                scoreString = "分数："+score;
            }else if (score > 700 && score <= 800){
                gradeString = "等级：八钱天师";
                scoreString = "分数："+score;
            }else if (score > 600 && score <= 700) {
                gradeString = "等级：七钱天师";
                scoreString = "分数："+score;
            } else if (score > 500 && score <= 600) {
                gradeString = "等级：六钱天师";
                scoreString = "分数："+score;
            }else if (score > 400 && score <= 500) {
                gradeString = "等级：五钱天师";
                scoreString = "分数："+score;
            }else if (score > 300 && score <= 400){
                gradeString = "等级：四钱天师";
                scoreString = "分数："+score;
            } else if (score > 200 && score <= 300) {
                gradeString = "等级：三钱天师";
                scoreString = "分数："+score;
            }else if (score > 100 && score <= 200) {
                gradeString = "等级：二钱天师";
                scoreString = "分数："+score;
            } else if (score > 5 && score <= 100){
                gradeString = "等级：一钱天师";
                scoreString = "分数："+score;
            }else if (score > -1 && score <= 5){
                gradeString = "大佬你在干啥？";
                scoreString = "分数："+score;
            }
            Log.e("gradeString1== ",gradeString);
            new LeAlertDialog(context,gradeString,scoreString);
        }else{
            // 子线程
            gameView.post(new Runnable() {
                @Override
                public void run() {
                    String gradeString = "";
                    String scoreString = "";

                    if (score > 1130){
                        gradeString = "等级：无敌！";
                        scoreString = "敬请期待机枪豌豆！";
                    }else  if (score > 900 && score <= 1130 ){
                        gradeString = "等级：十钱天师";
                        scoreString = "分数:"+score;
                    }else if (score > 800 && score <= 900){
                        gradeString = "等级：九钱天师";
                        scoreString = "分数:"+score;
                    }else if (score > 700 && score <= 800){
                        gradeString = "等级：八钱天师";
                        scoreString = "分数:"+score;
                    }else if (score > 600 && score <= 700) {
                        gradeString = "等级：七钱天师";
                        scoreString = "分数："+score;
                    } else if (score > 500 && score <= 600) {
                        gradeString = "等级：六钱天师";
                        scoreString = "分数:"+score;
                    }else if (score > 400 && score <= 500) {
                        gradeString = "等级：五钱天师";
                        scoreString = "分数:"+score;
                    }else if (score > 300 && score <= 400){
                        gradeString = "等级：四钱天师";
                        scoreString = "分数:"+score;
                    } else if (score > 200 && score <= 300) {
                        gradeString = "等级：三钱天师";
                        scoreString = "分数:"+score;
                    }else if (score > 100 && score <= 200) {
                        gradeString = "等级：二钱天师";
                        scoreString = "分数:"+score;
                    } else if (score > 5 && score <= 100){
                        gradeString = "等级：一钱天师";
                        scoreString = "分数:"+score;
                    }else if (score > -1 && score <= 5){
                        gradeString = "大佬你在干啥？";
                        scoreString = "分数:"+score;
                    }
                    Log.e("gradeString2== ",gradeString);
                    new LeAlertDialog(context,gradeString,scoreString);
                }
            });
        }
    }

    public static void showUiToast(final Context context, GameView gameView, final String msg){
        // 判断是在子线程，还是主线程
        if("main".equals(Thread.currentThread().getName())){
            Toast.makeText(context, msg,Toast.LENGTH_SHORT).show();
        }else{
            // 子线程
            gameView.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
