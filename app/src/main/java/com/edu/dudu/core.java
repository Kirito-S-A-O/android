package com.edu.dudu;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.dudu.RecyclerView.LinearAdapter;
import com.edu.dudu.sqllitedatabase.DatabaseHelper;

public class core extends AppCompatActivity {

    private RecyclerView rv;

    private DatabaseHelper helper = new DatabaseHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.core);

        SQLiteDatabase db = helper.getWritableDatabase();
         String sql = "select * from "+ Constants.TABLE_NAME+" order by WhiteWin DESC";
        final Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();
        long count = cursor.getCount();


        rv = findViewById(R.id.RV1);
        rv.setLayoutManager(new LinearLayoutManager(core.this));
        rv.setAdapter(new LinearAdapter(core.this, new LinearAdapter.OnItemClickListener() {
            @Override
            public void onclick(int pos) {
                Toast.makeText(core.this,"click.."+pos,Toast.LENGTH_SHORT).show();
            }
        },cursor, (int) count));

        //添加装饰
        rv.addItemDecoration(new MyDecoration());     //**********
        //rv.setAdapter();
    }

    class MyDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0,0,0,getResources().getDimensionPixelOffset(R.dimen.dividerheight));
        }
    }


}