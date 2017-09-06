package com.coagent.lyscs.bookpart;


import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Toast;

import com.coagent.lyscs.bookpart.part.DummyFragment;

public class ActionTabDemo extends Activity implements ActionBar.TabListener{

    private static final String SELECTED_ITEM = "selected_item";
    private ActionBar actionBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_tab);

        final ActionBar actionBar1 = getActionBar();
        actionBar1.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar1.addTab(actionBar1.newTab().setText("第一页").setTabListener(this));
        actionBar1.addTab(actionBar1.newTab().setText("第二页").setTabListener(this));
        actionBar1.addTab(actionBar1.newTab().setText("第三页").setTabListener(this));

        //findView();
    }

    private void findView() {
        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab().setText("第一页").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("第二页").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("第三页").setTabListener(this));

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //选中前面保存对应的Fragment
        if(savedInstanceState.containsKey(SELECTED_ITEM)){
            getActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(SELECTED_ITEM)
            );
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //将当前选中的Fragment保存到Bundle中
        outState.putInt(SELECTED_ITEM, getActionBar().getSelectedNavigationIndex());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        Fragment fragment = new DummyFragment();
        Bundle args = new Bundle();
        args.putInt(DummyFragment.ARG_SECTION_NUMBER, tab.getPosition()+1);
        fragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.container, fragment);
        transaction.commit();
        Toast.makeText(ActionTabDemo.this, "onTabSelected"
                +tab.getPosition(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
