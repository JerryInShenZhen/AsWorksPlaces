package com.coagent.lyscs.stomachmanager.tool;

import android.content.Context;

/**
 * dp和dx相互转换的工具类
 * Created by lyscs on 2017/5/28.
 */
public class DpAndDx {

    /**
     * 根据手机的分辨率从dp的单位转成像素px
     */
    public static int dip2px(Context context, float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从像素px转成dp的单位
     */
    public static int px2dip(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale + 0.5f);
    }
}
