package com.coagent.lyscs.bookpart.part;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lyscs on 2017/9/13.
 */
public class GeometricView extends View {
    public GeometricView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);  //画布绘制成白色
        Paint paint = new Paint();
        paint.setAntiAlias(true);   //去锯齿
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        int viewWidth = this.getWidth();

        //绘制圆形
        canvas.drawCircle(viewWidth/10+10, viewWidth/10+10, viewWidth/10, paint);

        //绘制正方形
        canvas.drawRect(10, viewWidth/5+20, viewWidth/5+10, viewWidth*2/5+20, paint);

        //绘制矩形
        canvas.drawRect(10, viewWidth*2/5+30, viewWidth/5+10, viewWidth/2+30, paint);

        //绘制圆角矩形
        RectF rel0 = new RectF(10, viewWidth/2+40, viewWidth/5+10, viewWidth*3/5+40);
        canvas.drawRoundRect(rel0, 15, 15, paint);

        //绘制椭圆
        RectF rel1 = new RectF(10, viewWidth*3/5+50, viewWidth/5+10, viewWidth*7/10+50);
        canvas.drawOval(rel1, paint);

        //定义一个path对象，封闭成一个三角形，并绘制
        Path path1 = new Path();
        path1.moveTo(10, viewWidth*9/10+60);
        path1.lineTo(viewWidth/5+10, viewWidth*9/10+60);
        path1.lineTo(viewWidth/10+10, viewWidth*7/10+60);
        path1.close();
        canvas.drawPath(path1, paint);

        //定义一个path对象，封闭成一个五角形，并绘制
        Path path2 = new Path();
        path2.moveTo(viewWidth/15+10, viewWidth*9/10+70);
        path2.lineTo(viewWidth*2/15+10, viewWidth*9/10+70);
        path2.lineTo(viewWidth/5+10, viewWidth+70);
        path2.lineTo(viewWidth/10+10, viewWidth*11/10+70);
        path2.lineTo(+10, viewWidth+70);
        path2.close();
        canvas.drawPath(path2, paint);

        //设置填充风格后绘制，画笔颜色为红色
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);

        //绘制圆形
        canvas.drawCircle(viewWidth*3/10+20, viewWidth/10+10, viewWidth/10, paint);

        //绘制正方形
        canvas.drawRect(viewWidth/5+20, viewWidth/5+20, viewWidth*2/5+20, viewWidth*2/5+20, paint);

        //绘制矩形
        canvas.drawRect(viewWidth/5+20, viewWidth*2/5+30, viewWidth*2/5+20, viewWidth/2+30, paint);

        //绘制圆角矩形
        RectF rel2 = new RectF(viewWidth/5+20, viewWidth/2+40, viewWidth*2/5+20, viewWidth*3/5+40);
        canvas.drawRoundRect(rel2, 15, 15, paint);

        //绘制椭圆
        RectF rel3 = new RectF(viewWidth/5+20, viewWidth*3/5+50, viewWidth*2/5+20, viewWidth*7/10+50);
        canvas.drawOval(rel3, paint);

        //定义一个path对象，封闭成一个三角形，并绘制
        Path path3 = new Path();
        path3.moveTo(viewWidth/5+20, viewWidth*9/10+60);
        path3.lineTo(viewWidth*2/5+20, viewWidth*9/10+60);
        path3.lineTo(viewWidth*3/10+20, viewWidth*7/10+60);
        path3.close();
        canvas.drawPath(path3, paint);

        //定义一个path对象，封闭成一个五角形，并绘制
        Path path4 = new Path();
        path4.moveTo(viewWidth*4/15+20, viewWidth*9/10+70);
        path4.lineTo(viewWidth/3+20, viewWidth*9/10+70);
        path4.lineTo(viewWidth*2/5+20, viewWidth+70);
        path4.lineTo(viewWidth*3/10+20, viewWidth*11/10+70);
        path4.lineTo(viewWidth/5+20, viewWidth+70);
        path4.close();
        canvas.drawPath(path4, paint);

        //设置画笔的渐变器
        Shader mShader = new LinearGradient(0, 0, 40, 60, new int[]{Color.RED,
                Color.GREEN, Color.BLUE, Color.YELLOW}, null, Shader.TileMode.REPEAT);
        paint.setShader(mShader);
        paint.setShadowLayer(25, 20, 20, Color.GRAY);   //设置影音

        //绘制圆形
        canvas.drawCircle(viewWidth/2+30, viewWidth/10+10, viewWidth/10, paint);

        //绘制正方形
        canvas.drawRect(viewWidth*2/5+30, viewWidth/5+20, viewWidth*3/5+30, viewWidth*2/5+20, paint);

        //绘制矩形
        canvas.drawRect(viewWidth*2/5+30, viewWidth*2/5+30, viewWidth*3/5+30, viewWidth/2+30, paint);

        //绘制圆角矩形
        RectF rel4 = new RectF(viewWidth*2/5+30, viewWidth/2+40, viewWidth*3/5+30, viewWidth*3/5+40);
        canvas.drawRoundRect(rel4, 15, 15, paint);

        //绘制椭圆
        RectF rel5 = new RectF(viewWidth*2/5+30, viewWidth*3/5+50, viewWidth*3/5+30, viewWidth*7/10+50);
        canvas.drawOval(rel5, paint);

        //定义一个path对象，封闭成一个三角形，并绘制
        Path path5 = new Path();
        path5.moveTo(viewWidth*2/5+30, viewWidth*9/10+60);
        path5.lineTo(viewWidth*3/5+30, viewWidth*9/10+60);
        path5.lineTo(viewWidth/2+30, viewWidth*7/10+60);
        path5.close();
        canvas.drawPath(path5, paint);

        //定义一个path对象，封闭成一个五角形，并绘制
        Path path6 = new Path();
        path6.moveTo(viewWidth*7/15+30, viewWidth*9/10+70);
        path6.lineTo(viewWidth*8/15+30, viewWidth*9/10+70);
        path6.lineTo(viewWidth*3/5+30, viewWidth+70);
        path6.lineTo(viewWidth/2+30, viewWidth*11/10+70);
        path6.lineTo(viewWidth*2/5+30, viewWidth+70);
        path6.close();
        canvas.drawPath(path6, paint);

        //设置字符大小后绘制
        paint.setTextSize(48);
        paint.setShader(null);
        paint.setColor(Color.BLACK);

        canvas.drawText("圆形", viewWidth*3/5+60, viewWidth/10+10, paint);
        canvas.drawText("正方形", viewWidth*3/5+60, viewWidth*3/10+20, paint);
        canvas.drawText("长方形", viewWidth*3/5+60, viewWidth*1/2+20, paint);
        canvas.drawText("圆角矩形", viewWidth*3/5+60, viewWidth*3/5+30, paint);
        canvas.drawText("椭圆形", viewWidth*3/5+60, viewWidth*7/10+30, paint);
        canvas.drawText("三角形", viewWidth*3/5+60, viewWidth*9/10+30, paint);
        canvas.drawText("五角形", viewWidth*3/5+60, viewWidth*11/10+30, paint);
    }
}
