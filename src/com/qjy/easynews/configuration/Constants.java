package com.qjy.easynews.configuration;

/**
 * Created by qjy on 15-4-21.
 */
public class Constants {
    private static final String BASEURL = "http://1000phone.net:8088/qfapp/index.php/juba/news/";
    public static final String NEWS = "text";
    public static final String IMAGE = "pic";
    public static final String VIDEO = "video";

    public static String getTitleUrl(String type) {
        String url = "";
        url = BASEURL + "cate_list?type=" + type;
        return url;
    }

    public static String getTextURL(String cate_id, int page) {
        String url = "";
        url = BASEURL + "get_news_list?cate_id=" + cate_id + "&p=" + page;
        return url;
    }

    public static String getPicURL(String cate_id, int page) {
        String url = "";
        url = BASEURL + "get_pic_news_list?cate_id=" + cate_id + "&p=" + page;
        return url;
    }

    public static String getVideoURL(String cate_id,int p) {
        String url = "";
        url = BASEURL + "get_video_news_list?cate_id=" + cate_id+"&p="+p;
        return url;
    }

    public static String getNewsDetail(String news_id) {
        String url = "";
        url = BASEURL + "news_detail?news_id=" + news_id;
        return url;
    }

    public static String getNewsComments(String news_id,int p) {
        String url = "";
        url = BASEURL + "get_comment_list?item_id=" + news_id+"&p="+p;
        return url;
    }



}
