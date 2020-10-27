package com.edu.dudu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.edu.dudu.sqllitedatabase.DAO;
import com.edu.dudu.sqllitedatabase.DatabaseHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    private  Button btn_login;
    private  Button btn_cancel;
    private EditText edt_name;
    private EditText edt_passward;

    private SharedPreferences mSharedpreferences;
    private SharedPreferences.Editor mEditor;


    @SuppressLint({"ClickableViewAccessibility", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

//        mSharedpreferences = getSharedPreferences("date",MODE_PRIVATE);
//        mEditor = mSharedpreferences.edit();

        final DatabaseHelper helper = new DatabaseHelper(this);
        helper.getWritableDatabase();

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DAO dao = new DAO(Login.this);


                if(edt_name.getText().toString().isEmpty())
                    Toast.makeText(Login.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                else if(edt_passward.getText().toString().isEmpty())
                    Toast.makeText(Login.this,"请输入密码",Toast.LENGTH_SHORT).show();
                else if(dao.query(edt_name.getText().toString(),edt_passward.getText().toString())){
                    Toast.makeText(Login.this,"登陆成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this,launcher.class);
                    Constants.flag = true;
                    Constants.name = edt_name.getText().toString().trim();
                    startActivity((intent));
                }else
                    Toast.makeText(Login.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();


            }
        });


       //cancel 按钮设置
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,zhuce.class);
                startActivity(intent);
            }
        });


        edt_name = findViewById(R.id.edt_name);
        edt_passward = findViewById(R.id.edt_password);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Log.d("EditText", s.toString());   //Log.d()方法内需要传入两个参数。1.第一个参数时tag，一般传入类名，用于对打印信息进行过滤；2.第二个参数，是一个字符串类型的msg，表示你想要打印的内容。
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        //监听edt_name 控件
        if(watcher != null)
            edt_name.addTextChangedListener(watcher);


        //给edt_password 规则表达式
        edt_passward.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.d()方法内需要传入两个参数。1.第一个参数时tag，一般传入类名，用于对打印信息进行过滤；
//                2.第二个参数，是一个字符串类型的msg，表示你想要打印的内容。
                Log.d("EditText", s.toString());
                String edtpassword = s.toString();
                String regex = "[^a-z0-9A-Z]";       //正则表达式  只能输入数字或字母


//                Pattern类：
//                Pattern的创建：
//                Pattern pattern =Pattern.complie(String regex)
//                参数说明：regex：是一个正则表达式的字符串，（也是需要过滤或寻找字符串的正则表达式）
                Pattern p = Pattern.compile(regex);

//                Matcher类：
//                在创建Matcher的时候，需要先创建Pattern；
//                Pattern pattern =Pattern.complie(String regex)
//                Matcher matcher=pattern.matcher( String imput);
                Matcher m = p.matcher(edtpassword);

//                String over=matcher.replaceAll(String instead)；
//                参数说明：instead：将匹配到所有的字符串换成instead字符串
//                over：替换后的字符串；
                String newedtpassword = m.replaceAll("");

                if(!edtpassword.equals(newedtpassword)){
                    edt_passward.setText(newedtpassword);                   //设置EditText的字符

                    edt_passward.setSelection(newedtpassword.length());     //因为删除了字符，要重写设置新的光标所在位置

                    Toast.makeText(Login.this,"只能输入数字或字母！",Toast.LENGTH_SHORT).show();               }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        edt_passward.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, android.view.MotionEvent event) {
                //getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                Drawable drawable = edt_passward.getCompoundDrawables()[2];
                //如果右边没有图片，不再处理
                if (drawable == null)
                    return false;
                //如果不是按下事件，不再处理
                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;

                if (event.getX() > edt_passward.getWidth()
                        - edt_passward.getPaddingRight()
                        - drawable.getIntrinsicWidth()){
                    edt_passward.setInputType((InputType.TYPE_CLASS_TEXT));

                }
                else {
                    if(edt_passward.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD)
                        edt_passward.setInputType((InputType.TYPE_TEXT_VARIATION_PASSWORD));
                }


                return false;
            }
        });

    }
}
