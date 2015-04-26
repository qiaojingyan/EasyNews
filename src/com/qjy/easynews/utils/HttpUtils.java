package com.qjy.easynews.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.qjy.easynews.app.AppCtx;
import com.qjy.easynews.configuration.Constants;
import com.qjy.easynews.model.*;

import java.util.*;

/**
 * Created by qjy on 15-4-21.
 */
public class HttpUtils {

    private static RequestQueue mRequestQueue;

    /**
     * 进行 PicContent 的网络连接以及 Json 解析
     *
     * @param context
     * @param cate_id
     * @param p
     */
    public static void getPicContent(final Context context, String cate_id, int p, final OnPicSuccessListener listener) {
        if (context == null) {
            return;
        }
        getRequestQueue();
        StringRequest request = new StringRequest(Constants.getPicURL(cate_id, p), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //进行 Json 解析
                List<PicContent> list = JsonUtils.jsonToPicList(response);

                //回调
                listener.loadPicUI(list);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(request);
    }


    /**
     * 新闻详情页
     *
     * @param context
     * @param news_id
     * @param listener
     */
    public static void getNewsDetail(final Context context, String news_id, final OnObjSuccessListener listener) {

        if (context == null) {
            return;
        }
        getRequestQueue();
        StringRequest request = new StringRequest(Constants.getNewsDetail(news_id), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // json 解析回 news
                News news = JsonUtils.jsonToNews(response);
                //回调
                listener.loadUI(news);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });


        mRequestQueue.add(request);
    }

    /**
     * 网络连接获取评论列表
     *
     * @param context
     * @param item_id
     * @param p
     * @param listener
     */
    public static void getComments(final Context context, String item_id, int p, final OnObjSuccessListener listener) {

        if (context == null) {
            return;
        }
        getRequestQueue();

        StringRequest request = new StringRequest(Constants.getNewsComments(item_id, p), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // json 解析回 List<Comment>
                List<Comment> comments = JsonUtils.jsonToCommentList(response);

                listener.loadUI(comments);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });

        mRequestQueue.add(request);


    }

    /**
     * 新闻列表页
     *
     * @param context
     * @param cate_id
     * @param p
     * @param listener
     */
    public static void getNews(final Context context, String cate_id, int p, final OnSuccessListener listener) {
        if (context == null) {
            return;
        }
        getRequestQueue();
        StringRequest request = new StringRequest(Constants.getTextURL(cate_id, p), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<News> topList = JsonUtils.jsonToTopNewsList(response);
                List<News> list = JsonUtils.jsonToNewsList(response);
                if (topList != null) {
                    listener.loadNewsUI(topList, list);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(request);
    }

    /**
     * Video 列表页
     * @param context
     * @param cate_id
     * @param p
     * @param listener
     */
    public static void getVideoList(final Context context, String cate_id, int p, final OnObjSuccessListener listener) {
        if (context == null) {
            return;
        }
        getRequestQueue();
        StringRequest request = new StringRequest(Constants.getVideoURL(cate_id, p), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<Video> list = JsonUtils.jsonToVideoList(response);
                if (list != null) {
                    listener.loadUI(list);
                }else {
                    listener.errorUI();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(request);
    }


    /**
     * Title内容
     *
     * @param context
     * @param type
     * @param listener
     */
    public static void getTitles(final Context context, String type, final OnSuccessListener listener) {
        if (context == null) {
            return;
        }
        getRequestQueue();
        StringRequest request = new StringRequest(Constants.getTitleUrl(type), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<Title> titles = JsonUtils.jsonToTitle(response);
                if (titles != null) {
                    listener.loadUI(titles);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(request);
    }

    public static void register(final User user){
        Log.e("register","user : "+user);
        StringRequest request = new StringRequest(StringRequest.Method.GET, Constants.getRegisterURL(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("HttpUtils","response : "+response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Log.e("HttpUtils" ,"jasndjadlasknda");

                Map<String,String> map = new HashMap<>();
                map.put("username",user.getUsername());
                map.put("password",user.getPassword());
                map.put("sequence",user.getSequence());
                map.put("verify_code",user.getVerify_code());
//                map.put("nickname",user.getNickname());
//                map.put("sex",user.getSex());
                map.put("format","json");

                return map;
            }
        };

        mRequestQueue.add(request);
    }

    private static RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = AppCtx.getInstance().getRequestQueue();
        }
        return mRequestQueue;
    }

    public interface OnSuccessListener {
        void loadUI(List<Title> list);

        void loadNewsUI(List<News> topList, List<News> list);
    }

    public interface OnPicSuccessListener {
        void loadPicUI(List<PicContent> list);
    }

    public interface OnObjSuccessListener {
        void loadUI(Object obj);
        void errorUI();
    }


}
