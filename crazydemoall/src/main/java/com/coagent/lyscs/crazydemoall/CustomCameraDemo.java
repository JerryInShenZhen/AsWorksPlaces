package com.coagent.lyscs.crazydemoall;

import android.app.Activity;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import com.coagent.lyscs.crazydemoall.part.ReslutPicture;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by lyscs on 2017/5/21.
 */
public class CustomCameraDemo extends Activity implements SurfaceHolder.Callback{

    private Camera mCamera;
    private SurfaceView svPreview;
    private SurfaceHolder mHolder;
    private Button btnCapt;
    private String mFilePath;

    //拍摄完成获取的图片数据的回调函数---保存到内存上
    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File photoFile = new File(mFilePath);
            try {
                FileOutputStream fos = new FileOutputStream(photoFile);
                fos.write(data);
                fos.close();
                Intent intent = new Intent(CustomCameraDemo.this, ReslutPicture.class);
                intent.putExtra("picPath", mFilePath);
                startActivity(intent);
                finish();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiity_custom_camera);

        mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFilePath = mFilePath + "/" + "custom_temp.png";

        svPreview = (SurfaceView) findViewById(R.id.sv_preview);
        btnCapt = (Button) findViewById(R.id.btn_capt);

        mHolder = svPreview.getHolder();
        mHolder.addCallback(this);

        btnCapt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //自动对焦完成时拍摄
                mCamera.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        if (success){
                            mCamera.takePicture(null, null, mPictureCallback);  //拍摄
                        }
                    }
                });
            }
        });

        svPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.autoFocus(null);        //点击surface自动对焦
            }
        });
    }

    /**
     * 获取系统硬件 Camera 对象
     */
    private Camera getCamera(){
        Camera hardwareCamera;
        try{
            hardwareCamera = Camera.open();
        }catch (Exception e){
            hardwareCamera = null;
            e.printStackTrace();
        }
        return hardwareCamera;
    }

    /**
     * 设置Camera的参数：jpeg格式、800*400大小、自动对焦
     */
    private void setCameraParameters(){
        if (mCamera != null){
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPictureFormat(ImageFormat.JPEG);
            parameters.setPictureSize(800, 400);
                parameters.setFocusMode(Camera.Parameters.FLASH_MODE_AUTO);
        }
    }

    /**
     * 释放系统硬件 Camera 对象
     */
    private void releaseCamera(){
        if (mCamera != null){
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     * 开始预览相机获取画面并绑定到SurfaceHolder中
     */
    private void setStartPreview(Camera camera, SurfaceHolder holder){
        if (camera != null){
            try {
                camera.setPreviewDisplay(holder);
                camera.setDisplayOrientation(90);   //设置为竖屏预览
                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setStartPreview(mCamera, mHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mCamera.stopPreview();
        setStartPreview(mCamera, mHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera == null){
            mCamera = getCamera();
            setCameraParameters();
            if (mHolder != null){
                setStartPreview(mCamera, mHolder);  //开始绑定预览
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }
}
