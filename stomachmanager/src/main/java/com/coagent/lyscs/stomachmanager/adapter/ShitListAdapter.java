package com.coagent.lyscs.stomachmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.coagent.lyscs.stomachmanager.bean.ShitBean;

import java.util.List;

/**
 * Created by lyscs on 2017/6/10.
 */
public class ShitListAdapter extends BaseAdapter {

    private List<ShitBean> mList;
    private Context mContext;
    private LayoutInflater mInflater;

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
        return null;
    }
}
