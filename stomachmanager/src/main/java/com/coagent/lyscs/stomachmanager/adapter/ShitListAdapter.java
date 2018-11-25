package com.coagent.lyscs.stomachmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.coagent.lyscs.stomachmanager.R;
import com.coagent.lyscs.stomachmanager.bean.ShitBean;

import java.util.List;

/**
 * Created by lyscs on 2017/6/10.
 */
public class ShitListAdapter extends BaseAdapter {

    private List<ShitBean> mList;
    private Context mContext;
    private LayoutInflater mInflater;

    public ShitListAdapter(Context context, List<ShitBean> list) {
        this.mList = list;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        ShitBean bean;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.home_list_item, null);
            viewHolder.mTvShitDate = (TextView)convertView.findViewById(R.id.tv_date);
            viewHolder.mTvShitKg = (TextView)convertView.findViewById(R.id.tv_kg);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        bean = mList.get(position);
        String date = bean.getShitYear()+""+bean.getShitMonth()+""+bean.getShitDay();
        viewHolder.mTvShitDate.setText(date);
        viewHolder.mTvShitKg.setText(bean.getShitKg());
        return convertView;
    }

    private static class ViewHolder{
        public TextView mTvShitDate;
        public TextView mTvShitKg;
    }
}
