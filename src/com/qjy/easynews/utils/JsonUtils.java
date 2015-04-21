package com.qjy.easynews.utils;

import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.qjy.easynews.model.News;
import com.qjy.easynews.model.Title;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qjy on 15-4-21.
 */
public class JsonUtils {
    /**
     * Json解析成Title
     *
     * @param jsonStr
     * @return
     */
    public static List<Title> jsonToTitle(String jsonStr) {
        List<Title> titles = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(jsonStr);

            JSONArray dataArr = obj.optJSONArray("data");

            if (dataArr == null) {
                return null;
            }

            for (int i = 0; i < dataArr.length(); i++) {
                JSONObject dataObj = dataArr.optJSONObject(i);
                if (dataObj == null) {
                    continue;
                }
                Title title = new Title();
                title.setmCateId(dataObj.optString("cate_id"));
                title.setmName(dataObj.optString("name"));
                titles.add(title);
            }
            return titles;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<News> jsonToTopNewsList(String jsonStr){
        List<News> list = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(jsonStr);
            JSONObject data = obj.optJSONObject("data");
            if(data == null){
                return null;
            }
            JSONArray topArr = data.getJSONArray("top_news");
            for (int i = 0; i < topArr.length(); i++) {
                JSONObject topObj = topArr.optJSONObject(i);
                News news = new News();
                news.setId(topObj.optInt("id"));
                news.setTitle(topObj.optString("title"));
                news.setCover_pic(topObj.optString("cover_pic"));
                list.add(news);
            }
            return list;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<News> jsonToNewsList(String jsonStr){
        List<News> list = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(jsonStr);
            JSONObject data = obj.optJSONObject("data");
            if(data == null){
                return null;
            }
            JSONArray topArr = data.getJSONArray("down_news");
            for (int i = 0; i < topArr.length(); i++) {
                JSONObject topObj = topArr.optJSONObject(i);
                News news = new News();
                news.setId(topObj.optInt("id"));
                news.setTitle(topObj.optString("title"));
                news.setCover_pic(topObj.optString("cover_pic"));
                news.setContent(topObj.optString("content"));
                news.setComment_total(topObj.optInt("comment_total"));
                news.setCreate_time(topObj.optString("create_time"));
                news.setDescript(topObj.optString("descript"));
                list.add(news);
            }
            return list;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
