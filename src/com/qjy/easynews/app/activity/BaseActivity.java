package com.qjy.easynews.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

/**
 * Created by qjy on 15-4-21.
 */
public abstract class BaseActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
        addListener();
    }

    public abstract void initView();
    public abstract void initData();
    public abstract void addListener();
}
