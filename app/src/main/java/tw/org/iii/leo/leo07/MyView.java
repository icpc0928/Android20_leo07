package tw.org.iii.leo.leo07;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class MyView extends View {

    private Bitmap ballBmp ;
//    private Bitmap bgBmp;
//    private Context context;
    private MainActivity activity;
    private Resources resources;
    private Paint paint;
    private int viewW , viewH ;
    private float ballW,ballH ,ballX,ballY, dx,dy;
    private boolean isInit;
    private Timer timer;
    private GestureDetector  gd; //手勢偵測


    public MyView(Context context) {
        super(context);

        setBackgroundColor(Color.GREEN);
        setBackgroundResource(R.drawable.bg);
        //原圖自動縮放

//        this.context = context;
//        this.context.getResources();

        activity = (MainActivity)context;
        resources = activity.getResources();

        timer = new Timer();

        gd = new GestureDetector(new MyGDListener());


//        bgBmp = BitmapFactory.decodeResource(resources,R.drawable.bg);

//        Log.v("leo"," ==>"+(context instanceof MainActivity));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {  //手勢的源頭在這邊 一定要有他

        float ex = event.getX() , ey = event.getY();
        if (ex >= 1537 && ex <= 2085 && ey >=550 && ey <= 940 ){
            if(event.getAction() == MotionEvent.ACTION_DOWN){

            }else if (event.getAction() == MotionEvent.ACTION_MOVE){

            }else if (event.getAction() == MotionEvent.ACTION_UP){

            }
            Log.v("leo",event.getX() + " : " + event.getY());
        }
        return true ; // gd.onTouchEvent(event);
    }

    //手勢
    private class MyGDListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.v("leo",velocityX+" x "+velocityY);

            if(Math.abs(velocityX)>Math.abs(velocityY)){
                //左右
                if(velocityX > 100){
                    //right
                }
                if(velocityX< -100){
                    //left
                }
            }
            if(Math.abs(velocityX)<Math.abs(velocityY)){
                //上下
                if(velocityY< -100){
                    //up
                }
                if(velocityY > 100){
                    //down
                }

            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onDown(MotionEvent e) {
//            Log.v("leo","onDown");

            return true; // super.onDown(e);
        }
    }


    //撰寫一個方法init  如果還沒初始化 那就去初始化 如果初始化哪就不用做惹
 private void init(){
        isInit = true;

     //把跟畫圖有關的就可以放來這
     paint = new Paint();
     paint.setAlpha(127);

     ballBmp = BitmapFactory.decodeResource(resources,R.drawable.ball);

        viewW = getWidth(); viewH = getHeight();   //抓一次寬高就好了 不用每次進到onDraw都抓一次很煩
        ballW = viewW /12f; ballH = ballW;

        Matrix matrix = new Matrix();
        matrix.postScale(ballW/ballBmp.getWidth(),ballH/ballBmp.getHeight());
        ballBmp = Bitmap.createBitmap(ballBmp,0,0,ballBmp.getWidth(),ballBmp.getHeight(),matrix,false);

        ballX = ballY = 100;
        //同樣時間增加距離 ＝速度
        dx=dy=16;
        timer.schedule(new RefreshView(),0,17);   //畫面更新速度
        timer.schedule(new BallTask(),1*1000,30);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!isInit) init();
        canvas.drawBitmap(ballBmp,ballX,ballY,null);
        //最後那個位置是透明度
//        canvas.drawBitmap(bgBmp,0,0,null); //原圖不會做縮放

    }


    //畫面更新
    private  class RefreshView extends TimerTask{
        @Override
        public void run() {
            postInvalidate();
        }
    }

    private class BallTask extends TimerTask {
        @Override
        public void run() {
            if(ballX < 0 || ballX+ballW > viewW){
                dx *= -1;
            }
            if(ballY < 0 || ballY+ballH > viewH){
                dy *= -1;
            }
            ballX += dx;
            ballY += dy;
            //用Invalidated可能會看不到 要用post 執行緒
            postInvalidate();
        }
    }


}
