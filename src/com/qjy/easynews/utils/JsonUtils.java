package com.qjy.easynews.utils;

import com.qjy.easynews.model.*;
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


    /**
     * 解析成News，为了详情页面
     * @param jsonStr
     * @return
     */
    public static News jsonToNews(String jsonStr){
        News news = new News();

        try {
            JSONObject obj = new JSONObject(jsonStr);
            JSONObject data = obj.optJSONObject("data");
            if(data == null){
                return null;
            }
            news.setId(data.optInt("news_id"));
            news.setTitle(data.optString("title"));
            news.setContent(data.optString("content"));
            news.setCreate_time(data.optString("create_time"));
            JSONArray picArr = data.optJSONArray("pic_list");

            if(picArr != null){
                List<String> list = new ArrayList<>();
                for (int i = 0; i < picArr.length(); i++) {
                    String str = picArr.optString(i);
                    list.add(str);
                }
                news.setPic_list(list);
            }
            return news;

        } catch (Exception e) {
            e.printStackTrace();
        }


        return news;
    }

    /**
     * json转成News列表
     * @param jsonStr
     * @return
     */
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


    /**
     * 解析 Video 的 list 页
     * @param jsonStr
     * @return
     */
    public static List<Video> jsonToVideoList(String jsonStr){
        List<Video> list = new ArrayList<>();

        try{
            JSONObject obj = new JSONObject(jsonStr);

            JSONArray dataArr = obj.getJSONArray("data");

            if(dataArr == null){
                return null;
            }
            for (int i = 0; i < dataArr.length(); i++) {
                JSONObject dataObj = dataArr.optJSONObject(i);
                Video video = new Video();
                video.setPic(dataObj.optString("pic"));
                video.setPlay_num(dataObj.optInt("play_num"));
                video.setTitle(dataObj.optString("title"));
                video.setVideo_url(dataObj.optString("video_url"));
                list.add(video);
            }
            return list;

        }catch (Exception e){
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

    public static List<PicContent> jsonToPicList(String jsonStr){
        List<PicContent> list = new ArrayList<PicContent>();
        try {
            JSONObject obj = new JSONObject(jsonStr);
            JSONArray datas = obj.optJSONArray("data");
            if(datas == null){
                return null;
            }

            for (int i = 0; i < datas.length(); i++) {
                JSONObject data = datas.optJSONObject(i);

                PicContent picContent = new PicContent();
                picContent.setId(data.optInt("id"));
                picContent.setTitle(data.optString("title"));
                picContent.setContent(data.optString("content"));
                picContent.setPic_total(data.optInt("pic_total"));
                picContent.setDescript(data.optString("descript"));

                JSONArray pic_list = data.optJSONArray("pic_list");

                if (pic_list != null){
                    List<String> list1 = new ArrayList<>();
                    for (int j = 0; j < pic_list.length(); j++) {
                        list1.add(pic_list.optString(j));
                    }

                    picContent.setPic_list(list1);
                }

                list.add(picContent);

            }
            return list;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析获得的 comments 数据
     * @param response
     * @return
     */
    public static List<Comment> jsonToCommentList(String response) {

        List<Comment> comments = new ArrayList<>();

        try {

            JSONObject obj = new JSONObject(response);

            JSONArray dataArr = obj.getJSONArray("data");

            if(dataArr == null){
                return null;
            }

            for (int i = 0; i < dataArr.length(); i++) {

                Comment comment = new Comment();

                JSONObject dataObj = dataArr.getJSONObject(i);

                comment.setContent(dataObj.optString("content"));

                comment.setCreate_time(dataObj.optString("create_time"));

                comment.setUser(dataObj.optString("user"));

                comments.add(comment);

            }


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return comments;

    }
}
