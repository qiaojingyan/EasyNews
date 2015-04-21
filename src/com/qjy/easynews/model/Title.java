package com.qjy.easynews.model;

/**
 * Created by qjy on 15-4-21.
 */
public class Title {
    private String mCateId;
    private String mName;

    public String getmCateId() {
        return mCateId;
    }

    public void setmCateId(String mCateId) {
        this.mCateId = mCateId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    @Override
    public String toString() {
        return "Title{" +
                "mCateId='" + mCateId + '\'' +
                ", mName='" + mName + '\'' +
                '}';
    }
}
