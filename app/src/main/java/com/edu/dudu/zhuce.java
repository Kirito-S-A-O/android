package com.edu.dudu;

//import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.edu.dudu.sqllitedatabase.DAO;
import com.edu.dudu.sqllitedatabase.DatabaseHelper;

public class zhuce extends AppCompatActivity {

   private Button btn1,btn2;
   private TextView tv_name,tv_password;
//   private SharedPreferences mSharedpreferences;
//   private SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhuce);

        btn1 = findViewById(R.id.btn_zhuce);
        btn2 = findViewById(R.id.btn_cancel);
        tv_name = findViewById(R.id.edt_zhuce_name);
        tv_password = findViewById(R.id.edt_zhuce_password);

//        //轻量化存储 Sharedpreferences
//        mSharedpreferences = getSharedPreferences("date",MODE_PRIVATE);
//        mEditor = mSharedpreferences.edit();

        //轻量化存储 sqlite
        final DatabaseHelper helper = new DatabaseHelper(this);
        helper.getWritableDatabase();


        btn1 = findViewById(R.id.btn_zhuce);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mEditor.putString("user_name",tv_name.getText().toString());
//                mEditor.putString("user_password",tv_password.getText().toString());
//                mEditor.apply();

                DAO dao = new DAO(zhuce.this);
                dao.insert(tv_name.getText().toString(),tv_password.getText().toString());

                Toast.makeText(zhuce.this,"注册成功",Toast.LENGTH_SHORT).show();
            }
        });
    }
}