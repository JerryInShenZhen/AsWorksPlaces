package com.coagent.lyscs.bookpart;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class BitmapFactoryDemo extends Activity {

    private String[] images = null;
    private AssetManager assetManager = null;
    private int currentImg = 0;
    private ImageView img;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_factory_demo);
        findView();
        try {
            assetManager = getAssets(); //获取/assets/目录下的文件
            images = assetManager.list("");
        }catch (IOException e){
            e.printStackTrace();
        }
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentImg >= images.length){
                    currentImg = 0;
                }
                while (!images[currentImg].endsWith(".png")
                        &&!images[currentImg].endsWith(".jpg")
                        &&!images[currentImg].endsWith(".gif")){
                    currentImg++;
                    if(currentImg >= images.length){
                        currentImg = 0;
                    }
                }

                InputStream assetFile = null;
                try {
                    assetFile = assetManager.open(images[currentImg]);
                }catch (IOException e){
                    e.printStackTrace();
                }

                BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
                if(bitmapDrawable != null
                        && bitmapDrawable.getBitmap().isRecycled()){
                    //如果图片还没回收，则强行回收
                    bitmapDrawable.getBitmap().recycle();
                }

                img.setImageBitmap(BitmapFactory.decodeStream(assetFile));
            }
        });
    }

    private void findView() {
        img = (ImageView) findViewById(R.id.iv_show);
        btnNext = (Button) findViewById(R.id.btn_next);
    }
}
