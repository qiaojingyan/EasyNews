package com.qjy.easynews.biz.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.qjy.easynews.R;
import com.qjy.easynews.biz.fragment.DummyFragment;
import com.qjy.easynews.biz.fragment.SettingsFragment;
import com.qjy.easynews.configuration.Constants;
import com.qjy.easynews.model.Title;
import com.qjy.easynews.utils.DecimUtils;
import com.qjy.easynews.utils.HttpUtils;

import java.util.List;

public class MainActivity extends FragmentActivity {


    private LinearLayout mTabBar;
    private LinearLayout[] mTabBars;
    private FragmentManager manager;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initData();
        initView();
        addListener();
    }

    public void initView() {
        findView();
        initTabBar();
    }

    public void initData() {
        manager = getSupportFragmentManager();
    }

    public void addListener() {
        //给TabBar加监听
        addTabBarListener();
    }

    private void addTabBarListener() {
        for (int i = 0; i < mTabBars.length; i++) {
            final int finali = i;
            mTabBars[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectTabBar(finali);
                }
            });
        }
    }

    /**
     * 初始化各个可获得的控件
     */
    public void findView() {
        mTabBar = (LinearLayout) findViewById(R.id.main_tabbar);

    }


    /**
     * 初始化下面的TabBar
     */
    private void initTabBar() {
        int childCount = mTabBar.getChildCount();
        mTabBars = new LinearLayout[childCount];

        for (int i = 0; i < childCount; i++) {
            //获得TabBar中的内容，便于以下操作
            mTabBars[i] = (LinearLayout) mTabBar.getChildAt(i);
        }

        //将第一栏设置为选中状态
        selectTabBar(0);
    }

    private void selectTabBar(int position){

        if(position == 3){
            SettingsFragment fragment = new SettingsFragment();
            FragmentTransaction transaction = manager.beginTransaction();

            transaction.replace(R.id.main_content,fragment);
            transaction.commit();

        }else{
            loadTabData(position);
        }


        for (int i = 0; i < mTabBars.length; i++) {
            if(i==position){
                setTabBarEnable(i,false);
            }else{
                setTabBarEnable(i,true);
            }
        }
    }

    public void loadTabData(int position){
        DummyFragment fragment = new DummyFragment();

        String type = Constants.NEWS;

        switch (position){
            case 1:
                type = Constants.IMAGE;
                break;
            case 2:
                type = Constants.VIDEO;
                break;

        }


        FragmentTransaction transaction = manager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("type",type);
        fragment.setArguments(bundle);

        transaction.replace(R.id.main_content,fragment);
        transaction.commit();

    }

    /**
     * 设置是否为选中状态
     *
     * @param position
     * @param enabled
     */
    private void setTabBarEnable(int position, boolean enabled) {
        //设置此Layout不能点击
        setEnable(mTabBars[position], enabled);
        for (int i = 0; i < mTabBars[position].getChildCount(); i++) {
            //设置子控件不能点击
            setEnable(mTabBars[position].getChildAt(i), enabled);
            setTextEnable(mTabBars[position].getChildAt(i), enabled);
        }
    }

    private void setTextEnable(View v, boolean enabled) {
        if (v != null) {
            int color = enabled ? Color.BLACK : Color.RED;
            if (v instanceof TextView) {
                ((TextView) v).setTextColor(color);
            }
        }
    }

    private void setEnable(View v, boolean enabled) {
        v.setEnabled(enabled);
    }
}

