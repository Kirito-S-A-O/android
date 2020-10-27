package com.edu.dudu;

import android.content.SharedPreferences;

/**
 *  Created by AS on 2020/6/8.
 */
public class Constants {

    // 五子连珠
    public final static int MAX_COUNT_IN_LINE = 5;

    // 棋盘的行数
    final static int MAX_LINE = 15;

    // 检查的方向
    final static int HORIZONTAL = 0;
    final static int VERTICAL = 1;
    final static int LEFT_DIAGONAL = 2;
    final static int RIGHT_DIAGONAL = 3;

    //输赢次数
    final  static  int WHITE_WIN = 0;
    final  static  int BLACK_WIN = 0;
    
    //SharedPreferences轻量型数据  不用
    final static SharedPreferences mSharedpreferences = null;
    final static SharedPreferences.Editor mEditor = null;

    //sqllite字段
    public static final int DATABASE_VERSION = 1;
    public final  static String DATABASE_NAME = "user.db";
    public final  static String TABLE_NAME = "user";

    //确认是否为登陆 登陆用户名
    public static boolean flag = false;
    public static String name = "";

}
