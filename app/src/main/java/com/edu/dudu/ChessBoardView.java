package com.edu.dudu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.edu.dudu.sqllitedatabase.DAO;
import com.edu.dudu.sqllitedatabase.DatabaseHelper;

import java.util.ArrayList;

import static com.edu.dudu.Renju.imageView;
import static com.edu.dudu.Renju.text_black;
import static com.edu.dudu.Renju.text_white;

/**
 * Created by AS on 2020/6/8.
 */

public class ChessBoardView extends View {



    // 棋盘的宽度，也是长度
    private int mViewWidth;
    // 棋盘每格的长度
    private float maxLineHeight;
    private Paint paint = new Paint();
    // 定义黑白棋子的Bitmap
    private Bitmap mwhitePiece, mblackPiece;
    private float ratioPieceOfLineHeight = 3 * 1.0f / 4;

    // 判断当前落下的棋子是否是白色的
    private static  boolean mIsWhite = true;
    // 记录黑白棋子位置的列表
    private ArrayList<Point> mwhiteArray = new ArrayList<>();
    private ArrayList<Point> mblackArray = new ArrayList<>();

    // 游戏是否结束
    private boolean mIsGameOver;
    // 游戏结束，是否是白色方胜利
    private boolean mIsWhiteWinner;
    private Log log;

    private Context mcontext;
    //分数
     private  int blackcore=0;
     private int whitecore=0;


    public ChessBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mcontext = context;
        init();
    }
//          弃用方法
//    public static void WhoIsGameing(ImageView imageView) {
//        if(mIsWhite){
//            imageView.setImageResource(R.drawable.stone_w2);
//        }
//        else{
//            imageView.setImageResource(R.drawable.stone_b1);
//        }
//
//    }


    private void init() {
        paint.setColor(0x88000000);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);

        mwhitePiece = BitmapFactory.decodeResource(getResources(), R.mipmap.stone_w2);
        mblackPiece = BitmapFactory.decodeResource(getResources(), R.mipmap.stone_b1);
    }

    @Override
    //onMeasure方法，测量View的大小，使View的长宽一致。
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);

        int width = Math.min(widthSize, heightSize);
        if (widthModel == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
        } else if (heightModel == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
        }
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制棋盘的网格
        drawBoard(canvas);
        // 绘制棋盘的黑白棋子
        drawPieces(canvas);
        // 检查游戏是否结束
        checkGameOver();
    }

    // 检查游戏是否结束
    private void checkGameOver() {
        log.d("Game","-----判断-------");
        CheckWinner checkWinner = new CheckWinner();
        boolean whiteWin = checkWinner.checkFiveInLineWinner(mwhiteArray);
        boolean blackWin = checkWinner.checkFiveInLineWinner(mblackArray);

        log.d("Game", String.valueOf(whiteWin));
        log.d("Game", String.valueOf(blackWin));

        if (whiteWin || blackWin) {
            log.d("Game","-----进入-------");
            mIsGameOver = true;
            mIsWhiteWinner = whiteWin;
            //计时器停止计时
            Renju.chronometer.stop();
            String text = mIsWhiteWinner ? "白棋胜利" : "黑棋胜利";
            Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();

        }
    }

    // 根据黑白棋子的数组绘制棋子
    private void drawPieces(Canvas canvas) {
        for (int i = 0, n = mwhiteArray.size(); i < n; i++) {
            Point whitePoint = mwhiteArray.get(i);
            float left = (whitePoint.x + (1 - ratioPieceOfLineHeight) / 2) * maxLineHeight;
            float top = (whitePoint.y + (1 - ratioPieceOfLineHeight) / 2) * maxLineHeight;

            canvas.drawBitmap(mwhitePiece, left, top, null);
        }

        for (int i = 0, n = mblackArray.size(); i < n; i++) {
            Point blackPoint = mblackArray.get(i);
            float left = (blackPoint.x + (1 - ratioPieceOfLineHeight) / 2) * maxLineHeight;
            float top = (blackPoint.y + (1 - ratioPieceOfLineHeight) / 2) * maxLineHeight;

            canvas.drawBitmap(mblackPiece, left, top, null);
        }
    }

    // 绘制棋盘的网线
    private void drawBoard(Canvas canvas) {
        int w = mViewWidth;
        float lineHeight = maxLineHeight;

        for (int i = 0; i < Constants.MAX_LINE; i++) {
            int startX = (int) (lineHeight / 2);
            int endX = (int) (w - lineHeight / 2);

            int y = (int) ((0.5 + i) * lineHeight);
            canvas.drawLine(startX, y, endX, y, paint);
            canvas.drawLine(y, startX, y, endX, paint);
        }
    }

    @Override
    //onSizeChanged方法在布局的阶段，如果View的大小发生改变，此方法得到调用。
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        maxLineHeight = mViewWidth * 1.0f / Constants.MAX_LINE;

        int pieceWidth = (int) (maxLineHeight * ratioPieceOfLineHeight);
        mwhitePiece = Bitmap.createScaledBitmap(mwhitePiece, pieceWidth, pieceWidth, false);
        mblackPiece = Bitmap.createScaledBitmap(mblackPiece, pieceWidth, pieceWidth, false);
    }




    @Override
    //onTouchEvent方法中处理我们下棋子的位置
    public boolean onTouchEvent(MotionEvent event) {

//        imageView = findViewById(R.id.iv_qizi);
//        textView = findViewById(R.id.tv);

        //连接数据库
        DatabaseHelper helper = new DatabaseHelper(this.mcontext);
        helper.getWritableDatabase();

        DAO dao = new DAO(this.mcontext);

        if (mIsGameOver) {
            return false;
        }
        int action = event.getAction();

        log.d("MouseAction", String.valueOf(action));

        if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            Point point = getValidPoint(x, y);
            if (mwhiteArray.contains(point) || mblackArray.contains(point)) {
                return false;
            }
            if (mIsWhite) {
                mwhiteArray.add(point);
                Log.d("Who is gameing", "白棋落子");
                imageView.setImageDrawable(getResources().getDrawable(R.mipmap.stone_b1));
                //白棋分数
                whitecore+=5;
                text_white.setText(String.valueOf(whitecore));
                dao.update_core(Constants.name,whitecore,blackcore);
                //imageView.setImageResource(R.drawable.stone_w2);
                //textView.setText("白色棋子");
            } else {
                Log.d("Who is gameing", "黑棋落子");
                imageView.setImageDrawable(getResources().getDrawable(R.mipmap.stone_w2));
                //黑棋分数
                blackcore+=5;
                text_black.setText(String.valueOf(blackcore));
                dao.update_core(Constants.name,whitecore,blackcore);
//                imageView.setImageResource(R.drawable.stone_w2);
                //textView.setText("黑色棋子");
                mblackArray.add(point);
            }
            invalidate();
            mIsWhite = !mIsWhite;
        }
        return true;
    }

    private Point getValidPoint(int x, int y) {
        int validX = (int) (x / maxLineHeight);
        int validY = (int) (y / maxLineHeight);

        return new Point(validX, validY);
    }

    private static final String INSTANCE = "instance";
    private static final String INSTANCE_GAME_OVER = "instance_game_over";
    private static final String INSTANCE_WHITE_ARRAY = "instance_white_array";
    private static final String INSTANCE_BLACK_ARRAY = "instance_black_array";

    @Override
    //onTouchEvent方法中处理我们下棋子的位置：
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
        bundle.putBoolean(INSTANCE_GAME_OVER, mIsGameOver);

        bundle.putParcelableArrayList(INSTANCE_BLACK_ARRAY, mblackArray);
        bundle.putParcelableArrayList(INSTANCE_WHITE_ARRAY, mwhiteArray);
        return bundle;
    }

    @Override
    //从bundle中恢复棋局：
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mIsGameOver = bundle.getBoolean(INSTANCE_GAME_OVER);
            mwhiteArray = bundle.getParcelableArrayList(INSTANCE_WHITE_ARRAY);
            mblackArray = bundle.getParcelableArrayList(INSTANCE_BLACK_ARRAY);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    // 再来一局
    public void start() {


        whitecore = 0;
        blackcore = 0;
        text_white.setText(String.valueOf(whitecore));
        text_black.setText(String.valueOf(blackcore));


        if(mIsWhiteWinner){
            //白棋获胜

        }
        else{
            //黑棋获胜

        }



        mwhiteArray.clear();
        mblackArray.clear();
        mIsGameOver = false;
        mIsWhiteWinner = false;
        invalidate();
    }
}
