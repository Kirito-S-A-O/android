package com.edu.dudu;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.edu.dudu.service.MyIntentService;

public class shezhi extends AppCompatActivity {

    private TextView text_Admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shezhi);


        // 服务停止背景音乐
        Intent intent = new Intent(shezhi.this, MyIntentService.class);
        String action = MyIntentService.ACTION_MUSIC_STOP;
        // 设置action
        intent.setAction(action);
        startService(intent);


        text_Admin  = findViewById(R.id.Tv_Admin);
        text_Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(shezhi.this,Admin.class);
                startActivity(intent);
            }
        });

    }
}