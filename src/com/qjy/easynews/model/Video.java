package com.qjy.easynews.model;

/**
 * Created by qjy on 15-4-24.
 */
public class Video {
    private String title;
    private String video_url;
    private int play_num;
    private String pic;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public int getPlay_num() {
        return play_num;
    }

    public void setPlay_num(int play_num) {
        this.play_num = play_num;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "Vedio{" +
                "title='" + title + '\'' +
                ", video_url='" + video_url + '\'' +
                ", play_num=" + play_num +
                ", pic='" + pic + '\'' +
                '}';
    }
}
