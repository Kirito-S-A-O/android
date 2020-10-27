package com.edu.dudu.splashAnimation;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.edu.dudu.R;
import com.edu.dudu.launcher;

public class splash extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置没有标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        RelativeLayout layoutSplash=(RelativeLayout) findViewById(R.id.activity_splash);
        AlphaAnimation alphaAnimation=new AlphaAnimation(0.1f,1.0f);
        alphaAnimation.setDuration(1000);//设置动画播放时长1000毫秒（1秒）
        layoutSplash.startAnimation(alphaAnimation);
        //设置动画监听
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            //动画结束
            @Override
            public void onAnimationEnd(Animation animation) {
                //页面的跳转
                Intent intent=new Intent(splash.this,launcher.class);
                startActivity(intent);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

    }
//————————————————
//    版权声明：本文为CSDN博主「Badgehu」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//    原文链接：https://blog.csdn.net/PlainWaterh/article/details/78184847
}
