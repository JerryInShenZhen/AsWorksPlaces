package com.coagent.lyscs.stomachmanager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.coagent.lyscs.stomachmanager.R;

/**
 * on 2017/5/8.
 * 类的描述:
 */

public class HomeFragment extends Fragment {

    private ListView lvHome;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_content, null);
        lvHome = (ListView) view.findViewById(R.id.lv_home);
        return view;
    }
}
