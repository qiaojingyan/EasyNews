package com.qjy.easynews.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.qjy.easynews.app.AppCtx;
import com.qjy.easynews.configuration.Constants;
import com.qjy.easynews.model.News;
import com.qjy.easynews.model.Title;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by qjy on 15-4-21.
 */
public class HttpUtils {

    private static RequestQueue mRequestQueue;


    public static void getNews(final Context context,String cate_id,int p, final OnSuccessListener listener){
        if (context == null){
            return;
        }
        if(mRequestQueue == null){
            mRequestQueue = AppCtx.getInstance().getRequestQueue();
        }
        StringRequest request = new StringRequest(Constants.getTextURL(cate_id, p), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<News> topList = JsonUtils.jsonToTopNewsList(response);
                List<News> list = JsonUtils.jsonToNewsList(response);
                if(topList != null){
                    listener.loadNewsUI(topList,list);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"网络异常",Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(request);
    }


    public static void getTitles(final Context context,String type, final OnSuccessListener listener){
        if(context == null){
            return;
        }
        if(mRequestQueue == null){
            mRequestQueue = AppCtx.getInstance().getRequestQueue();
        }
        StringRequest request = new StringRequest(Constants.getTitleUrl(type), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<Title> titles = JsonUtils.jsonToTitle(response);
                if(titles != null){
                    listener.loadUI(titles);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"网络异常",Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(request);
    }

    public interface OnSuccessListener{
        void loadUI(List<Title> list);
        void loadNewsUI(List<News> topList,List<News> list);
    }


}
