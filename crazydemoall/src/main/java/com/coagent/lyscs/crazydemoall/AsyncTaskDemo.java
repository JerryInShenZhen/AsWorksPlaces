package com.coagent.lyscs.crazydemoall;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by lyscs on 2017/5/14.
 */
public class AsyncTaskDemo extends Activity {

    private static final String imgUri = "https://baike.baidu.com/pic/BAT" +
            "/13973564/0/14ce36d3d539b60030437276e050352ac75cb765?fr=lemma#" +
            "aid=0&pic=14ce36d3d539b60030437276e050352ac75cb765";
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);

        mImageView = (ImageView) findViewById(R.id.iv_load_image);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_bar);

        Log.i("MyAsyncTask", "doInBackground: 异步加载进行时 ");
        new MyAsyncTask().execute(imgUri);
    }

    class MyAsyncTask extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... params) {
            Log.i("MyAsyncTask", "doInBackground: 异步加载进行时 ");
            String url = params[0];
            Bitmap bitmap = null;
            URLConnection connection;
            InputStream is;
            try {
                connection = new URL(url).openConnection();
                is = connection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bitmap = BitmapFactory.decodeStream(bis);
                is.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap   ;
        }

        @Override
        protected void onPreExecute() {
            Log.i("MyAsyncTask", "异步加载初始化 ");
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mProgressBar.setVisibility(View.GONE);
            mImageView.setImageBitmap(bitmap);
            //Toast.makeText(AsyncTaskDemo.this, "异步加载完成", Toast.LENGTH_SHORT).show();;
            Log.i("MyAsyncTask", "onPostExecute: 异步加载完成 ");
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
