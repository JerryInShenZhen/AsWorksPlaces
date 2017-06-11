package com.coagent.lyscs.stomachmanager.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.coagent.lyscs.stomachmanager.R;

/**
 * 重定义ImageView，setImages的时候传入两张图片
 * 在 transformPage的时候 就会根据 offset的大小来设置透明度 从而效果为渐变
 * Created by lyscs on 2017/5/28.
 */
public class MyImageView extends ImageView {

    private Paint mPaint;
    private Bitmap mSelectedIcon;
    private Bitmap mNormalIcon;
    private Rect mSelectedRect;
    private Rect mNormalRect;
    private int mSelectedAlpha = 0;

    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public final void setImages(int normal, int selected){
        this.mNormalIcon = createBitmap(normal);    //拿到原图
        this.mSelectedIcon = createBitmap(selected);
        int width = (int) getResources().getDimension(R.dimen.tab_image_weith);
        int height = (int) getResources().getDimension(R.dimen.tab_image_height);
        this.mNormalRect = new Rect(0, 0, width, height);
        this.mSelectedRect = new Rect(0, 0, width, height);
        this.mPaint = new Paint();  //拿到画笔
    }

    /**
     * 当滑动时调用此方法
     */
    public final void tansformPage(float offset){
        this.mSelectedAlpha = (int) (255 * (1-offset));
        invalidate();   //重画画布
    }

    /**
     * 通过resId生成一个Bitmap
     */
    private Bitmap createBitmap(int resId){
        return BitmapFactory.decodeResource(getResources(), resId);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPaint == null){
            return;
        }
        Log.i("MyImageView", "onDraw: "+"我进入了重新画图");
        this.mPaint.setAlpha(255 - this.mSelectedAlpha);
        canvas.drawBitmap(this.mNormalIcon, null, this.mNormalRect, this.mPaint);
        this.mPaint.setAlpha(this.mSelectedAlpha);
        canvas.drawBitmap(this.mSelectedIcon, null, this.mSelectedRect, this.mPaint);
    }
}
