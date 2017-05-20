package com.coagent.lyscs.crazydemoall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lyscs on 2017/5/13.
 */
public class MyBaseAdapter extends BaseAdapter {

    private List<BaseAdapterItemBean> itemBeanList;
    private LayoutInflater inflater;

    public MyBaseAdapter(Context context, List<BaseAdapterItemBean> itemBeanList) {
        this.itemBeanList = itemBeanList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itemBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*没有利用adapter的缓存机制
        View view = inflater.inflate(R.layout.baseadapter_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_image);
        TextView title = (TextView) view.findViewById(R.id.tv_title);
        TextView content = (TextView) view.findViewById(R.id.tv_content);

        BaseAdapterItemBean bean = itemBeanList.get(position);
        imageView.setImageResource(bean.itemImageResId);
        title.setText(bean.itemTitle);
        content.setText(bean.itemContent);
        return view;*/

        /* 利用缓存机制减少加载布局文件的时间
        if(convertView == null){
            convertView = inflater.inflate(R.layout.baseadapter_item, null);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_image);
        TextView title = (TextView) convertView.findViewById(R.id.tv_title);
        TextView content = (TextView) convertView.findViewById(R.id.tv_content);

        BaseAdapterItemBean bean = itemBeanList.get(position);
        imageView.setImageResource(bean.itemImageResId);
        title.setText(bean.itemTitle);
        content.setText(bean.itemContent);*/

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.baseadapter_item, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_image);
            viewHolder.title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.content = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BaseAdapterItemBean bean = itemBeanList.get(position);
        viewHolder.imageView.setImageResource(bean.itemImageResId);
        viewHolder.title.setText(bean.itemTitle);
        viewHolder.content.setText(bean.itemContent);
        return convertView;
    }

    class ViewHolder{
        public ImageView imageView;
        public TextView title;
        public TextView content;
    }
}
