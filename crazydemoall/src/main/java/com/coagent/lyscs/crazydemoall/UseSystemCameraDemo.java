package com.coagent.lyscs.crazydemoall;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by lyscs on 2017/5/21.
 */
public class UseSystemCameraDemo extends Activity {

    private static final int REQ_1 = 101;
    private static final int REQ_2 = 102;
    private String mFilePath;

    private Button btnSysCamera1;
    private Button btnSysCamera2;
    private Button btnCustomCamera;
    private ImageView ivCameraImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFilePath = mFilePath + "/" + "temp.png";


        btnSysCamera1 = (Button) findViewById(R.id.btn_camera1);
        btnSysCamera2 = (Button) findViewById(R.id.btn_camera2);
        btnCustomCamera = (Button) findViewById(R.id.btn_custom_camera);
        ivCameraImg = (ImageView) findViewById(R.id.iv_camera_img);

        btnSysCamera1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera1();
            }
        });

        btnSysCamera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera2();
            }
        });

        btnCustomCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UseSystemCameraDemo.this, CustomCameraDemo.class));
            }
        });

    }

    /**
     * 调用系统相机，获得的是缩略图
     */
    private void startCamera1(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);    //启动系统相机的action
        //startActivity(intent);
        startActivityForResult(intent, REQ_1);  //拍照完成要返回所拍照片
    }

    /**
     * 设置了输出Uri，拍照的拍片能存到SD卡路径上
     */
    private void startCamera2(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);    //启动系统相机的action
        Uri photoUri = Uri.fromFile(new File(mFilePath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);     //拍照玩相片存储到指定位置和名字
        startActivityForResult(intent, REQ_2);  //拍照完成要返回所拍照片
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
           if (requestCode == REQ_1){
               Bundle bundle = data.getExtras();
               Bitmap bitmap = (Bitmap) bundle.get("data");    //取出的是缩略图
               ivCameraImg.setImageBitmap(bitmap);
           }else if (requestCode == REQ_2){
               try {
                   FileInputStream inputStream = new FileInputStream(mFilePath);
                   Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                   ivCameraImg.setImageBitmap(bitmap);
                   inputStream.close();
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
        }
    }
}
