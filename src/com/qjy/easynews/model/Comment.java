package com.qjy.easynews.model;

/**
 * Created by qjy on 15-4-23.
 */
public class Comment {
    private String content;
    private String create_time;
    private String user;

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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "content='" + content + '\'' +
                ", create_time='" + create_time + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}
