package com.coagent.lyscs.crazydemoall;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.coagent.lyscs.crazydemoall.bean.FolderBean;
import com.coagent.lyscs.crazydemoall.part.ImageLoadAdapter;
import com.coagent.lyscs.crazydemoall.part.ListImageDirPopupWindow;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lyscs on 2017/5/18.
 */
public class LoadImageDemo extends Activity {

    private GridView mGridView;
    private List<String> mImgs;
    private RelativeLayout mBottomLy;
    private TextView mDirName;
    private TextView mDirCount;
    private File mCurrentDir;
    private int mMaxCount;
    private ProgressDialog progressDialog;
    private ImageLoadAdapter adapter;
    private List<FolderBean> mFolderBeans = new ArrayList<FolderBean>();
    private ListImageDirPopupWindow dirPopupWindow;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x120) {
                progressDialog.dismiss();
                data2View();
                initDirPopupWindow();
            }
        }
    };

    /**
     * 初始化PopupWindow的数据和点击事件监听及弹出方式
     */
    private void initDirPopupWindow() {
        dirPopupWindow = new ListImageDirPopupWindow(this, mFolderBeans);
        dirPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //PopupWindow消失的时候使得其它区域由暗变亮
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
        //设置dirPopupWindow选择哪个文件夹的监听器
        dirPopupWindow.setOnDirSelectedListener(new ListImageDirPopupWindow.OnDirSelectedListener() {
            @Override
            public void onSelect(FolderBean bean) {
                mCurrentDir = new File(bean.getDir());
                mImgs = Arrays.asList(mCurrentDir.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String filename) {
                        if (filename.endsWith(".jpg")
                                || filename.endsWith(".jpeg")
                                || filename.endsWith(".png")) {
                            return true;
                        }
                        return false;
                    }
                }));
                //更新adapter来更新GridView显示的内容
                adapter = new ImageLoadAdapter(LoadImageDemo.this, mImgs, mCurrentDir.getAbsolutePath());
                mGridView.setAdapter(adapter);
                mDirCount.setText(mImgs.size() + "");
                mDirName.setText(bean.getName());

                dirPopupWindow.dismiss();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadimage);

        initView();
        initDatas();
        initEvent();
    }

    private void initEvent() {
        mBottomLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dirPopupWindow.setAnimationStyle(R.style.dir_popupwindow_anim);
                dirPopupWindow.showAsDropDown(mBottomLy, 0, 0);

                //PopupWindow出现的时候使得其背景区域由亮变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.3f;
                getWindow().setAttributes(lp);


            }
        });
    }

    /**
     * 利用ContentProvider扫描手机所有图片
     */
    private void initDatas() {
        if (!Environment.getExternalStorageState().
                equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "当前存储卡不可用", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog = ProgressDialog.show(this, null, "正在加载");

        new Thread() {
            @Override
            public void run() {
                Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                String selection = MediaStore.Images.Media.MIME_TYPE + "=? or " +
                        MediaStore.Images.Media.MIME_TYPE + "=?";
                String[] selectionArgs = {"image/jpeg", "image/png"};
                String order = MediaStore.Images.Media.DATE_MODIFIED;  //时间排序

                ContentResolver contentResolver = LoadImageDemo.this.getContentResolver();
                Cursor cursor = contentResolver.query(uri, null, selection, selectionArgs, order);

                Set<String> mDirPaths = new HashSet<String>();
                while (cursor.moveToNext()) {
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null) {
                        continue;
                    }
                    String dirPath = parentFile.getAbsolutePath();
                    FolderBean folderBean = null;

                    if (mDirPaths.contains(dirPath)) {  //一个文件夹下只获取一次文件夹名和路径就好
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                        folderBean = new FolderBean();
                        folderBean.setDir(dirPath);
                        folderBean.setFirstImgPath(path);
                    }

                    if (parentFile.list() == null) {
                        continue;
                    }
                    //文件夹下图片的数量
                    int picSize = parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String filename) {
                            if (filename.endsWith(".jpg")
                                    || filename.endsWith(".jpeg")
                                    || filename.endsWith(".png")) {
                                return true;
                            }
                            return false;
                        }
                    }).length;
                    folderBean.setCount(picSize);
                    mFolderBeans.add(folderBean);
                    if (picSize > mMaxCount) {
                        mMaxCount = picSize;
                        mCurrentDir = parentFile;
                    }
                }
                cursor.close();
                //mDirPaths = null;
                handler.sendEmptyMessage(0x120);
            }
        }.start();

    }

    private void initView() {
        mGridView = (GridView) findViewById(R.id.id_gridview);
        mDirName = (TextView) findViewById(R.id.tv_dir_name);
        mDirCount = (TextView) findViewById(R.id.tv_dir_count);
        mBottomLy = (RelativeLayout) findViewById(R.id.rl_bottom);
        //mGridView.setAdapter(adapter);
    }

    /**
     * 将当前文件夹下的图片显示在GridView中
     */
    private void data2View() {
        if (mCurrentDir == null) {
            Toast.makeText(this, "未扫描到图片", Toast.LENGTH_SHORT).show();
            return;
        }
        mImgs = Arrays.asList(mCurrentDir.list());  //得到当前文件夹下全部文件名list
        adapter = new ImageLoadAdapter(this, mImgs, mCurrentDir.getAbsolutePath());
        mGridView.setAdapter(adapter);
        mDirCount.setText(mMaxCount + "");
        mDirName.setText(mCurrentDir.getName());

    }


}
