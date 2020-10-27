package com.edu.dudu;

import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

public class CountWin extends AppCompatActivity {

    private SharedPreferences mSharedpreferences;
    private SharedPreferences.Editor mEditor;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //轻量化存储 Sharedpreferences
        mSharedpreferences = getSharedPreferences("date", MODE_PRIVATE);
        mEditor = mSharedpreferences.edit();


    }
}
