package com.coagent.lyscs.bookpart.part;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by lyscs on 2017/7/23.
 */
public class DummyFragment extends Fragment{
    public static final String ARG_SECTION_NUMBER = "section_number";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setGravity(Gravity.START);
        Bundle args = getArguments();
        textView.setText(args.getInt(ARG_SECTION_NUMBER)+"ddhdgdfdfg");
        textView.setTextSize(30);
        return textView;
    }
}
