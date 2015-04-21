package com.qjy.easynews.biz.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.qjy.easynews.biz.fragment.ContentFragment;

import java.util.List;

/**
 * Created by qjy on 15-4-21.
 */
public class ContentAdapter extends FragmentPagerAdapter{

    private List<ContentFragment> list;

    public ContentAdapter(FragmentManager fm,List<ContentFragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
