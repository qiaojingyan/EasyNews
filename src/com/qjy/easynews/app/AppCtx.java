package com.qjy.easynews.app;

import android.app.Application;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by qjy on 15-4-21.
 */
public class AppCtx extends Application{

    private static AppCtx mAppCtx;
    private RequestQueue mRequestQueue;


    @Override
    public void onCreate() {
        super.onCreate();

        firstCreateInstance();
    }

    public static AppCtx getInstance(){
        return mAppCtx;
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(this);
        }
        return mRequestQueue;
    }

    private void firstCreateInstance(){
        if(mAppCtx == null){
            mAppCtx = this;
        }
        getRequestQueue();
    }

}
