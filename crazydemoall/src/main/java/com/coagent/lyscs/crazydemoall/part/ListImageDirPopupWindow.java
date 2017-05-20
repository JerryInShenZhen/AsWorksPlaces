package com.coagent.lyscs.crazydemoall.part;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.coagent.lyscs.crazydemoall.R;
import com.coagent.lyscs.crazydemoall.bean.FolderBean;
import com.coagent.lyscs.crazydemoall.utils.ImageLoader;

import java.util.List;

/**
 * Created by lyscs on 2017/5/19.
 */
public class ListImageDirPopupWindow extends PopupWindow {
    private int mWidth;
    private int mHeight;
    private View mConvertView;
    private ListView mListView;
    private List<FolderBean> mDatas;

    /**
     * 内部接口，用于回调，监听PopupWindow每一行的点击事件
     * 用于通知调用者点击事件得到FolderBean，知道选择的是哪个文件夹
     */
    public interface OnDirSelectedListener{
        void onSelect(FolderBean bean);
    }
    public OnDirSelectedListener dirSelectedListener;

    /**
     * 设置回调接口对象
     */
    public void setOnDirSelectedListener(OnDirSelectedListener dirSelectedListener) {
        this.dirSelectedListener = dirSelectedListener;
    }

    public ListImageDirPopupWindow(Context context, List<FolderBean> datas) {
        calWidthAndHeight(context);
        mConvertView = LayoutInflater.from(context).
                inflate(R.layout.popup_main, null);
        this.mDatas = datas;

        setContentView(mConvertView);
        setWidth(mWidth);
        setHeight(mHeight);

        setFocusable(true);
        setTouchable(true);     //可触摸
        setOutsideTouchable(true);      //可点击这个view之外区域，然后popupwindow消失
        setBackgroundDrawable(new BitmapDrawable());
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE){ //点到外面
                    dismiss();
                    return true;
                }
                return false;
            }
        });

        initView(context);
        intEvent();
    }

    private void intEvent() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (dirSelectedListener != null){
                    dirSelectedListener.onSelect(mDatas.get(position));
                }
            }
        });
    }

    private void initView(Context context) {
        mListView = (ListView) mConvertView.findViewById(R.id.lv_list_dir);
        mListView.setAdapter(new ListDirAdapter(context, mDatas));
    }

    /**
     * 计算PopupWindow的宽高
     */
    private void calWidthAndHeight(Context context){
        //获取屏幕的宽高
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        mWidth = outMetrics.widthPixels;
        mHeight = (int) (outMetrics.heightPixels * 0.7);


    }

    private class ListDirAdapter extends ArrayAdapter<FolderBean>{
        private LayoutInflater inflater;
        //private List<FolderBean> mDatas;

        public ListDirAdapter(Context context, List<FolderBean> objects) {
            super(context, 0, objects);
            inflater = LayoutInflater.from(context);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null){
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.popup_item, parent, false);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_dir_item_image);
                viewHolder.mDirName = (TextView) convertView.findViewById(R.id.tv_dir_item_name);
                viewHolder.mDirCount = (TextView) convertView.findViewById(R.id.tv_dir_item_count);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            FolderBean bean = getItem(position);
            viewHolder.imageView.setImageResource(R.drawable.pictures_no);
            viewHolder.mDirCount.setText(bean.getCount() + "");
            viewHolder.mDirName.setText(bean.getName());
            ImageLoader.getInstance().loadImage(bean.getFirstImgPath(), viewHolder.imageView);

            return convertView;
        }

        private class ViewHolder{
            public ImageView imageView;
            public TextView mDirName;
            public TextView mDirCount;
        }
    }
}
