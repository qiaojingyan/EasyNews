package com.qjy.easynews.biz.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.List;

/**
 * Created by qjy on 15-4-22.
 */
public class CyclePagerAdapter extends PagerAdapter {

    private List<View> list;

    public CyclePagerAdapter(List<View> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position = position % list.size();
        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        position = position%list.size();
        container.removeView(list.get(position));
    }
}
