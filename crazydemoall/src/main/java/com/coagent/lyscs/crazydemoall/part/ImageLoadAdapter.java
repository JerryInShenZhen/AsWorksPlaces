package com.coagent.lyscs.crazydemoall.part;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.coagent.lyscs.crazydemoall.R;
import com.coagent.lyscs.crazydemoall.utils.ImageLoader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImageLoadAdapter extends BaseAdapter {

    private static Set<String> mSelectImg = new HashSet<String>();
    //private Context context;
    private List<String> mImgPaths;
    private String dirPath;
    private LayoutInflater inflater;

    public ImageLoadAdapter(Context context, List<String> mDatas, String dirPath) {
        //this.context = context;
        this.mImgPaths = mDatas;
        this.dirPath = dirPath;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mImgPaths.size();
    }

    @Override
    public Object getItem(int position) {
        return mImgPaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_gridview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageButton = (ImageButton) convertView.findViewById(R.id.ib_select);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final String filePath = dirPath + "/" + mImgPaths.get(position);
        //先重置状态
        viewHolder.imageView.setImageResource(R.drawable.pictures_no);
        viewHolder.imageButton.setImageResource(R.drawable.picture_unselected);
        viewHolder.imageView.setColorFilter(null);
        ImageLoader.getInstance().loadImage(filePath, viewHolder.imageView);

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectImg.contains(filePath)){ //已经被选择
                    mSelectImg.remove(filePath);
                    viewHolder.imageView.setColorFilter(null);
                    viewHolder.imageButton.setImageResource(R.drawable.picture_unselected);
                }else {                             //未被选择
                    mSelectImg.add(filePath);
                    //透明黑色
                    viewHolder.imageView.setColorFilter(Color.parseColor("#77000000"));
                    viewHolder.imageButton.setImageResource(R.drawable.pictures_selected);
                }
            }
        });

        if (mSelectImg.contains(filePath)){
            viewHolder.imageView.setColorFilter(Color.parseColor("#77000000"));
            viewHolder.imageButton.setImageResource(R.drawable.pictures_selected);
        }
        return convertView;
    }

    private class ViewHolder {
        public ImageView imageView;
        public ImageButton imageButton;
    }
}