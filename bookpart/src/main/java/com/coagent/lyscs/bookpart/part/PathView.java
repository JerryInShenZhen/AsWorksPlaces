package com.coagent.lyscs.bookpart.part;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
import android.view.View;

/**
 * Created by lyscs on 2017/9/15.
 */
public class PathView extends View {

    float phase = 0;
    private PathEffect[] effects = new PathEffect[7];   //路径效果
    private int[] colors;
    private Paint paint;
    private Path path;

    public PathView(Context context) {
        super(context);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);

        path = new Path();
        path.moveTo(0, 0);
        for (int i=0; i<=40; i++){
            //随机生成40个点并将它们连接成一条直线
            path.lineTo(i*20, (float) (Math.random()*60));
        }
        colors = new int[]{Color.BLACK, Color.BLUE, Color.CYAN, Color.GREEN,
                Color.MAGENTA, Color.RED, Color.YELLOW};
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        Path p = new Path();
        p.addRect(0, 0, 8, 8, Path.Direction.CCW);

        effects[0] = null;  //不使用路径效果
        effects[1] = new CornerPathEffect(10); //使用CornerPathEffect拐角路劲效果
        effects[2] = new DiscretePathEffect(3.0f, 5.0f);    //分离式,圆滑转角
        effects[3] = new DashPathEffect(new float[]{20, 10, 5, 10}, phase); //波溅式
        effects[4] = new PathDashPathEffect(p, 12, phase, PathDashPathEffect.Style.ROTATE); //
        effects[5] = new ComposePathEffect(effects[2], effects[4]); //调解式，组成
        effects[6] = new SumPathEffect(effects[4], effects[3]); //相加式

        canvas.translate(8, 8); //移至8,8
        for (int i=0; i<effects.length; i++){
            paint.setPathEffect(effects[i]);
            paint.setColor(colors[i]);
            canvas.drawPath(path, paint);
            canvas.translate(0, 60);
        }

        phase +=1;  //改变phase值，形成动画效果，难道不用赋初值？
        invalidate();
    }
}
