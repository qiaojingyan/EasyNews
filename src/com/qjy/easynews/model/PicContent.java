package com.qjy.easynews.model;

import java.util.List;

/**
 * Created by qjy on 15-4-22.
 */
public class PicContent {
    private int id;
    private String descript;
    private List<String> pic_list;
    private String content;
    private int pic_total;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public List<String> getPic_list() {
        return pic_list;
    }

    public void setPic_list(List<String> pic_list) {
        this.pic_list = pic_list;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPic_total() {
        return pic_total;
    }

    public void setPic_total(int pic_total) {
        this.pic_total = pic_total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
