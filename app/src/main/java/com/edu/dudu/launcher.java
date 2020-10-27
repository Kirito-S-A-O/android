package com.edu.dudu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.edu.dudu.Dialog.CustomDialog;


public class launcher extends AppCompatActivity implements View.OnClickListener{

   private Button mbutton1,mbutton2,mbutton3,mbutton4;
   private TextView tv1,tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        Window window;
//        window = launcher.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.dimAmount =0f;
//        window.setAttributes(lp);






        if (!Constants.flag){
//            Toast.makeText(launcher.this,"请登陆！",Toast.LENGTH_SHORT).show();

            CustomDialog customDialog = new CustomDialog(launcher.this, R.style.CustomDialog);
            customDialog.setTitle("提示 kira☆").setMessage("请登陆！")
                    .setCancel("取消", new CustomDialog.IonCancelListener()
                    {
                        @Override
                        public void onCancel(CustomDialog dialog)
                        {
                            Toast.makeText(launcher.this,"不登录无法使用功能！",Toast.LENGTH_SHORT).show();
                        }
                    }).setConfirm("登陆", new CustomDialog.IonConfirmListener() {
                @Override
                public void onConfirm(CustomDialog dialog)
                {
                    tv1 = findViewById(R.id.tv_login);
                   Intent intent = new Intent(launcher.this,Login.class);
                    startActivity(intent);
                }
            }).show();
        }


        //登陆
        tv1 = findViewById(R.id.tv_login);
        tv1.setOnClickListener(launcher.this);

        //注册
        tv2 = findViewById(R.id.tv_sign);
        tv2.setOnClickListener(launcher.this);




        if (IsLogin()) {
            //开始游戏
            mbutton1 = findViewById(R.id.btn1);
            mbutton1.setOnClickListener(launcher.this);

            //设置
            mbutton2 = findViewById(R.id.btn2);
            mbutton2.setOnClickListener(launcher.this);

            //成绩
            mbutton3 = findViewById(R.id.btn3);
            mbutton3.setOnClickListener(launcher.this);

            //关于
            mbutton4 = findViewById(R.id.btn4);
            mbutton4.setOnClickListener(launcher.this);

            //登陆
            tv1.setVisibility(View.INVISIBLE);


            //注册
            tv2.setVisibility(View.INVISIBLE);

        }

    }

    private boolean IsLogin() {

        if (Constants.flag) {
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId())
        {
            //开始游戏
            case R.id.btn1:intent = new Intent(launcher.this, Renju.class);
            break;
            //设置
            case R.id.btn2:intent = new Intent(launcher.this, shezhi.class);
                break;
            //成绩
            case R.id.btn3:intent = new Intent(launcher.this, core.class);
                break;
            //关于
            case R.id.btn4:intent = new Intent(launcher.this,about.class);
                break;
            //登陆
            case R.id.tv_login:intent = new Intent(launcher.this,Login.class);
                break;
            //注册
            case R.id.tv_sign:intent = new Intent(launcher.this,zhuce.class);
                break;

        }
        startActivity(intent);
    }
}