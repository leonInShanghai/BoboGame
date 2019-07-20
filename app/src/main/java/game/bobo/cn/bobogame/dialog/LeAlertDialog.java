package game.bobo.cn.bobogame.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import game.bobo.cn.bobogame.R;
import game.bobo.cn.bobogame.activity.IcastGameActivity;
import game.bobo.cn.bobogame.view.GameView;

/**
 * Created by Leon on 2018/5/19.
 * Functions:
 */

public class LeAlertDialog  {

    public LeAlertDialog(final Context context,final String dialog_title,String dialogmsg) {
        // 1. 布局文件转换为View对象
        LayoutInflater inflater = LayoutInflater.from(context);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.le_alertdialog, null);

        // 2. 新建对话框对象
        final Dialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setContentView(layout);

        //展示用户的等级
        TextView dialogTitle = (TextView) layout.findViewById(R.id.dialog_title);
        dialogTitle.setText(dialog_title);

        //展示用户的分数
        TextView dialog_msg = (TextView) layout.findViewById(R.id.dialog_msg);
        dialog_msg.setText(dialogmsg);

        // 4. 确定按钮
        Button btnOK = (Button) layout.findViewById(R.id.dialog_ok);
        btnOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                IcastGameActivity.getInstance().finish();
            }
        });

    }


}
