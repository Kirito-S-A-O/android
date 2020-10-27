package com.edu.dudu.sqllitedatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.edu.dudu.Constants;

public class DAO {

   private  static final String TAG = "DAO";
    private final DatabaseHelper helper;

    public DAO(Context context){

        //创建数据库
        helper = new DatabaseHelper(context);
        helper.getWritableDatabase();
    }

    /**
     * @param name      用户名
     * @param password  密码
     */
    //增加數據
    public  void insert(String name,String password){
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql  = "insert into "+ Constants.TABLE_NAME+"(name,password) values(?,?)";
        db.execSQL(sql,new Object[]{name,password});
        db.close();
    }


    //刪除數據
    public  void delete(Cursor cursor){
            //删除
         SQLiteDatabase db = helper.getWritableDatabase();
         String sql = "delete from "+Constants.TABLE_NAME+" where name = '"+cursor.getString(1)+"'";
        //弹出确认界面

         db.execSQL(sql);
        db.close();;
    }

    /**
     * @param cursor  将当前的游标位置传入
     * @param user_name  修改用户名
     * @param WhiteWin   白棋分数
     * @param BlackWin   黑棋分数
     */
    //改寫數據
    public  void update(Cursor cursor, String user_name, int WhiteWin, int BlackWin){
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "update "+Constants.TABLE_NAME+" set name = '"+user_name+"', WhiteWin = "+WhiteWin+", BlackWin = "+BlackWin+" where name = '"+cursor.getString(1)+"'";
        db.execSQL(sql);
        db.close();

    }


    public  void update_core(String user_name, int WhiteWin, int BlackWin){
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "update "+Constants.TABLE_NAME+" set WhiteWin = "+WhiteWin+",BlackWin = "+BlackWin+" where name = "+user_name+"";
        db.execSQL(sql);
        db.close();
    }


    /**
     * @param user_name 查询这个用户名是否在数据库里
     * @param user_password  查询这个密码是否在数据库里
     * @return 返回是否找到数据
     */
    //查詢數據 登陆
    public boolean query(String user_name,String user_password){
        SQLiteDatabase db = helper.getWritableDatabase();
        int flag = 0;
        //需要进行判断  如果username 为字母或数字 应加上引号

        //String sql = "select * from "+Constants.TABLE_NAME+" where name = ? and ?";
//        String sql = "select * from "+Constants.TABLE_NAME+" where name = "+user_name+" and password = "+user_password;

        String  sql = "select * from "+Constants.TABLE_NAME+" where name = '" + user_name + "' and password = '" + user_password + "'";
        //输出打印
        Log.d("DAO-----------",sql.toString().trim());
        //返回游标
        Cursor cursor = db.rawQuery(sql,null);
        //通过游标判断是否查询到数据
        while(cursor.moveToNext()) {
            //查询name字段 返回字段所在的位置
            int index1 = cursor.getColumnIndex("name");
            //通过index读取字段信息
            String name = cursor.getString(index1);

            //查询password字段 返回字段所在的位置
            int index2 = cursor.getColumnIndex("password");
            //通过index读取字段信息
            String password = cursor.getString(index2);

            Log.d(TAG, "---query: name"+name);
            Log.d(TAG, "---user_name"+user_name);

            Log.d(TAG, "---query: password"+password);
            Log.d(TAG, "---user_password"+user_password);

            if(name.equals(user_name))
                if (password.equals(user_password))
                  flag = 1;
        }
        Log.d(TAG, "没有找到数据");

        if (flag == 1)
            {db.close();return true;}
        else
            {db.close();return false;}
    }


}
