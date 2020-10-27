package com.edu.dudu;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.edu.dudu.Dialog.CustomDialog;
import com.edu.dudu.sqllitedatabase.DAO;
import com.edu.dudu.sqllitedatabase.DatabaseHelper;

public class Admin extends AppCompatActivity {

    private EditText edt_name,edt_paiming,edt_white_core,edt_black_core;
    private Button btn_update,btn_delete;
    private TextView tv_last,tv_next;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);


        final DatabaseHelper helper = new DatabaseHelper(this);
        helper.getWritableDatabase();

        //找到控件
        edt_name = findViewById(R.id.user_name);
        edt_paiming = findViewById(R.id.user_paiming);
        edt_white_core = findViewById(R.id.white_core);
        edt_black_core = findViewById(R.id.black_core);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);
        tv_last = findViewById(R.id.Tv_last);
        tv_next = findViewById(R.id.Tv_next);

        //查询表中所有数据 以白棋分数倒叙排序
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select * from "+Constants.TABLE_NAME+" order by WhiteWin DESC";
        //返回游标
        final Cursor cursor = db.rawQuery(sql,null);

        //游标指向第一行  以为是以白棋分数排行所以排名从上往下依次1-2-3-4..
        cursor.moveToFirst();
        //游标下移指向_id
        cursor.moveToNext();
            //获取当前游标位置
            int index1 = cursor.getPosition();
            //通过index读取字段信息 从第一列开始读取数据 第一列为_id不需要读取
            String name = cursor.getString(1);
            edt_name.setText(name);
            //设置排行
            edt_paiming.setText(String.valueOf(cursor.getPosition()));
            //下一列 白棋成绩
            edt_white_core.setText(cursor.getString(3));
            //下一列 黑棋成绩
            edt_black_core.setText(cursor.getString(4));


        //下一条
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果没有到底 下一条可用
                if (cursor.moveToNext()) {
                    //查询name字段 返回字段所在的位置  int index = cursor.getPosition();

                    edt_name.setText(cursor.getString(1));
                    Log.d("TAG----name", cursor.getString(1));
                    //设置排行
                    edt_paiming.setText(String.valueOf(cursor.getPosition()));
                    Log.d("TAG----position", String.valueOf(cursor.getPosition()));
                    //下一列 白棋成绩

                    edt_white_core.setText(cursor.getString(3));
                    Log.d("TAG----white", cursor.getString(3));
                    //下一列 黑棋成绩
                    edt_black_core.setText(cursor.getString(4));
                    Log.d("TAG----black", cursor.getString(4));
                }
            }
        });

        //上一条
        tv_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果没有到顶 上一条可用
                if (cursor.moveToPrevious()) {
                    //查询name字段 返回字段所在的位置  int index = cursor.getPosition();

                    edt_name.setText(cursor.getString(1));
                    Log.d("TAG----name", cursor.getString(2));
                    //设置排行
                    edt_paiming.setText(String.valueOf(cursor.getPosition()));
                    Log.d("TAG----position", String.valueOf(cursor.getPosition()));
                    //下一列 白棋成绩
                    edt_white_core.setText(cursor.getString(3));
                    Log.d("TAG----white", cursor.getString(3));
                    //下一列 黑棋成绩
                    edt_black_core.setText(cursor.getString(4));
                    Log.d("TAG----black", cursor.getString(4));



                }
            }
        });


        //修改
        btn_update = findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = helper.getWritableDatabase();
                DAO dao = new DAO(Admin.this);
//                将edt_name、edt_paiming、edt_white_core、edt_black_core的数据填入insert方法中
                   String name = edt_name.getText().toString();
                   int WhiteWin = Integer.parseInt(edt_white_core.getText().toString());
                   int BlackWin = Integer.parseInt(edt_black_core.getText().toString());

                   dao.update(cursor,
                           name,
                           WhiteWin,
                           BlackWin);

                Toast.makeText(Admin.this,"操作成功！",Toast.LENGTH_SHORT).show();
            }
//               //刷新activity
//                Admin.onResume.onCreate();
        });

        //删除
        btn_delete = findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = helper.getWritableDatabase();
                final DAO dao = new DAO(Admin.this);

                CustomDialog customDialog = new CustomDialog(Admin.this, R.style.CustomDialog);
                customDialog.setTitle("提示 kira☆").setMessage("是否删除！")
                        .setCancel("取消", new CustomDialog.IonCancelListener()
                        {
                            @Override
                            public void onCancel(CustomDialog dialog)
                            {
                                Toast.makeText(Admin.this,"取消！",Toast.LENGTH_SHORT).show();
                            }
                        }).setConfirm("确认", new CustomDialog.IonConfirmListener() {
                    @Override
                    public void onConfirm(CustomDialog dialog)
                    {
                        View tv1 = findViewById(R.id.tv_login);
                        dao.delete(cursor);
                        Toast.makeText(Admin.this,"删除成功！",Toast.LENGTH_SHORT).show();
                    }
                }).show();
                //刷新界面

            }
        });


    }


}
