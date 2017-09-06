package com.coagent.lyscs.stomachmanager;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coagent.lyscs.stomachmanager.adapter.MyFragmentAdapter;
import com.coagent.lyscs.stomachmanager.fragment.ChartFragment;
import com.coagent.lyscs.stomachmanager.fragment.DiaryFragment;
import com.coagent.lyscs.stomachmanager.fragment.HomeFragment;
import com.coagent.lyscs.stomachmanager.fragment.MineFragment;
import com.coagent.lyscs.stomachmanager.view.MyImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewPager mViewPager;
    //主界面下方四个内容的控件
    private MyImageView ivHome;
    private TextView tvHome;
    private MyImageView ivChart;
    private TextView tvChart;
    private MyImageView ivDiary;
    private TextView tvDiary;
    private MyImageView ivMine;
    private TextView tvMine;
    private LinearLayout llHome;
    private LinearLayout llChart;
    private LinearLayout llDiary;
    private LinearLayout llMine;

    private ArrayList<Fragment> mFragments;
    private ArgbEvaluator mColorEvaluator;  //根据偏移量进行颜色渐变
    private int mTextNormalColor;// 未选中的字体颜色
    private int mTextSelectedColor;// 选中的字体颜色

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        setListener(); // viewpager设置滑动监听
    }

    /**
     * 初始化数据包括：字体颜色、Fragments添加到viewpager及适配器、选中和未选中图片的设置
     */
    private void initData() {
        //下两行取颜色的方法过时了
        //mTextNormalColor = getResources().getColor(R.color.main_bottom_tab_textcolor_normal);
        //mTextSelectedColor = getResources().getColor(R.color.main_bottom_tab_textcolor_selected);
        mTextNormalColor = ContextCompat.getColor(this, R.color.main_bottom_tab_textcolor_normal);
        mTextSelectedColor = ContextCompat.getColor(this, R.color.main_bottom_tab_textcolor_selected);

        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
        mFragments.add(new ChartFragment());
        mFragments.add(new DiaryFragment());
        mFragments.add(new MineFragment());
        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(adapter);

        ivHome.setImages(R.drawable.home_normal, R.drawable.home_selected);
        ivChart.setImages(R.drawable.chart_normal, R.drawable.chart_selected);
        ivDiary.setImages(R.drawable.diary_normal, R.drawable.diary_selected);
        ivMine.setImages(R.drawable.mine_normal, R.drawable.mine_selected);
    }

    //控件绑定id
    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_main);

        llHome = (LinearLayout) findViewById(R.id.ll_home);
        llChart = (LinearLayout) findViewById(R.id.ll_chart);
        llDiary = (LinearLayout) findViewById(R.id.ll_diary);
        llMine = (LinearLayout) findViewById(R.id.ll_mine);

        ivHome = (MyImageView) findViewById(R.id.iv_home);
        ivChart = (MyImageView) findViewById(R.id.iv_chart);
        ivDiary = (MyImageView) findViewById(R.id.iv_diary);
        ivMine = (MyImageView) findViewById(R.id.iv_mine);

        tvHome = (TextView) findViewById(R.id.rb1);
        tvChart = (TextView) findViewById(R.id.rb2);
        tvDiary = (TextView) findViewById(R.id.rb3);
        tvMine = (TextView) findViewById(R.id.rb4);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_home:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.ll_chart:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.ll_diary:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.ll_mine:
                mViewPager.setCurrentItem(3);
                break;
        }
    }

    private void setListener() {
        //设置点击监听
        llHome.setOnClickListener(this);
        llChart.setOnClickListener(this);
        llDiary.setOnClickListener(this);
        llMine.setOnClickListener(this);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setTabTextColorAndImageView(position,positionOffset);// 更改text的颜色还有图片
                //Log.i("MyImageView", "position: "+position+"; positionOffset"+positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * 根据到达的位置和偏移量图片字体完成切换效果
     */
    private void setTabTextColorAndImageView(int position, float positionOffset){
        mColorEvaluator = new ArgbEvaluator();
        //当前tab的颜色值
        int evaluateCurrent = (int) mColorEvaluator.evaluate(positionOffset, mTextSelectedColor, mTextNormalColor);
        //将要到的tab的颜色值
        int evaluateThe = (int) mColorEvaluator.evaluate(positionOffset, mTextNormalColor, mTextSelectedColor);

        //setNormal();
        switch (position){
            case 0:
                //设置状态改变字体和图片的改变效果
                tvHome.setTextColor(evaluateCurrent);
                tvChart.setTextColor(evaluateThe);
                ivHome.tansformPage(positionOffset);
                ivChart.tansformPage(1-positionOffset);
                ivDiary.tansformPage(1);
                ivMine.tansformPage(1);
                break;
            case 1:
                tvChart.setTextColor(evaluateCurrent);
                tvDiary.setTextColor(evaluateThe);
                ivChart.tansformPage(positionOffset);
                ivDiary.tansformPage(1-positionOffset);
                ivHome.tansformPage(1);
                ivMine.tansformPage(1);
                break;
            case 2:
                tvDiary.setTextColor(evaluateCurrent);
                tvMine.setTextColor(evaluateThe);
                ivDiary.tansformPage(positionOffset);
                ivMine.tansformPage(1-positionOffset);
                ivHome.tansformPage(1);
                ivChart.tansformPage(1);
                break;
        }


    }

    private void setNormal() {
        tvHome.setTextColor(mTextNormalColor);
        tvChart.setTextColor(mTextNormalColor);
        tvDiary.setTextColor(mTextNormalColor);
        tvMine.setTextColor(mTextNormalColor);
        ivHome.setImageResource(R.drawable.home_normal);
        ivChart.setImageResource(R.drawable.chart_normal);
        ivDiary.setImageResource(R.drawable.diary_normal);
        ivMine.setImageResource(R.drawable.mine_normal);
    }

}
