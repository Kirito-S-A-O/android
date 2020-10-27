package com.edu.dudu.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.dudu.R;
import com.edu.dudu.sqllitedatabase.DatabaseHelper;




public class LinearAdapter extends RecyclerView.Adapter<LinearAdapter.Linearholder>{

    private static final String TAG = "LinearAdapter";
    private Context mcontext;
    private OnItemClickListener mListener;
    private Cursor cursor;
    private DatabaseHelper helper = new DatabaseHelper(this.mcontext);
    int count;
    int i=0;





    /**
     * @param context 上下文
     * @param listener 监听事件
     * @param cursor   数据库游标
     * @param count     行数
     */
    public LinearAdapter(Context context,OnItemClickListener listener,Cursor cursor,int count){
        this.mcontext = context;
        this.mListener = listener;
        this.cursor = cursor;
        this.count = count-1;
    }

    @Override
    public LinearAdapter.Linearholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater 当XML布局资源被解析并转换成View对象时会用到。
        //2.inflate(intresource, ViewGroup root,booleanattachToRoot)
        //第一个传入的参数resourse是你想要加载的布局资源。第二个传入的参数是指当前载入的视图要将要放入在层级结构中的根视图。
        // 如果传入了第三个参数attachToRoot，那么它会决定视图在载入完成后是否附加到传入的根视图中去。
        //作者：享学课堂
        //链接：https://www.jianshu.com/p/14610c347f09
        //来源：简书
        //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
        View view = LayoutInflater.from(mcontext).inflate(R.layout.recycler_item,parent,false);
        return new Linearholder(view);
    }

//    //查询表中所有数据 以白棋分数倒叙排序


    @Override
    public void onBindViewHolder(@NonNull LinearAdapter.Linearholder holder, final int position) {


        Log.d("Test1", String.valueOf(i++));


        //游标指向第一行  以为是以白棋分数排行所以排名从上往下依次1-2-3-4..
        if (cursor.moveToNext()) {
            //查询name字段 返回字段所在的位置  int index = cursor.getPosition();
            //设置用户名
            holder.Tv_name.setText(cursor.getString(1));
//            Log.d(TAG, cursor.getString(1));
            //设置排行
            holder.Tv_paiming.setText(String.valueOf(cursor.getPosition()));
//            Log.d(TAG, String.valueOf(cursor.getPosition()));
            //白棋成绩
            holder.Tv_core.setText(cursor.getString(3));
//            Log.d(TAG, cursor.getString(3));


            //设置图片
            //holder.img.setImageResource(R.drawable.bg_main);
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(mcontext,"click.."+position,Toast.LENGTH_SHORT).show();
                mListener.onclick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
//        DatabaseHelper helper = new DatabaseHelper(this.mcontext);
//        SQLiteDatabase db = helper.getWritableDatabase();
//
//        String sql = "select count(*) from "+Constants.TABLE_NAME;
//        Cursor cursor = db.rawQuery(sql,null);
//
//        cursor.moveToNext();

       Log.d(TAG+"Recycler行数", String.valueOf(count));
//
//        cursor.close();

        return (int) count;





    }




    static class Linearholder extends RecyclerView.ViewHolder{

            public ImageView img;
            public TextView Tv_paiming,Tv_core,Tv_name;

        public Linearholder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.R_iv);
            Tv_paiming = itemView.findViewById(R.id.tv_paiming);
            Tv_core = itemView.findViewById(R.id.tv_core);
            Tv_name = itemView.findViewById(R.id.tv_name);

        }
    }

    //接口回调
    public interface OnItemClickListener{
        void onclick(int pos);
    }
}
