package com.qjy.easynews.model;

import java.util.List;

/**
 * Created by qjy on 15-4-21.
 */
public class News {
    private int id;
    private String title;
    private String content;
    private String create_time;
    private String cover_pic;
    private String descript;
    private List<String> pic_list;
    private int comment_total;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCover_pic() {
        return cover_pic;
    }

    public void setCover_pic(String cover_pic) {
        this.cover_pic = cover_pic;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public int getComment_total() {
        return comment_total;
    }

    public void setComment_total(int comment_total) {
        this.comment_total = comment_total;
    }


    public List<String> getPic_list() {
        return pic_list;
    }

    public void setPic_list(List<String> pic_list) {
        this.pic_list = pic_list;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", create_time='" + create_time + '\'' +
                ", cover_pic='" + cover_pic + '\'' +
                ", descript='" + descript + '\'' +
                ", comment_total=" + comment_total +
                '}';
    }
}
