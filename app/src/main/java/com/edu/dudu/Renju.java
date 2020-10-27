 package com.edu.dudu;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.edu.dudu.service.MyIntentService;


 public class Renju extends AppCompatActivity {

     public static Chronometer chronometer;
     private ChessBoardView chessBoardView;

     static  ImageView imageView = null;
     static TextView text_black;
     static TextView text_white;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wzq);
        chessBoardView = (ChessBoardView) findViewById(R.id.boardView);
        chronometer = findViewById(R.id.chronometer);
        //计时器 进入游戏开始计时
        chronometer.start();



        // 启动服务播放背景音乐
        Intent intent = new Intent(Renju.this, MyIntentService.class);
        String action = MyIntentService.ACTION_MUSIC;
        // 设置action
        intent.setAction(action);
        startService(intent);
//————————————————
//        版权声明：本文为CSDN博主「邹奇」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/csdnzouqi/java/article/details/90904566
//




        imageView = findViewById(R.id.iv_qizi);
       text_black = findViewById(R.id.black_core);
       text_white = findViewById(R.id.white_core);

//        textView = findViewById(R.id.tv);

        //imageView.setImageResource(R.drawable.stone_b1);

        //
       // ChessBoardView.OnClickListener.WhoIsGameing(imageView);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // 再来一局
        if (id == R.id.action_setting) {
            chessBoardView.start();
            //重置时间
            chronometer.setBase(SystemClock.elapsedRealtime());// 复位


            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}