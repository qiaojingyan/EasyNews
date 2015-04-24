package com.qjy.easynews.biz.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.qjy.easynews.R;
import com.qjy.easynews.biz.Adapter.ContentAdapter;
import com.qjy.easynews.configuration.Constants;
import com.qjy.easynews.model.News;
import com.qjy.easynews.model.PicContent;
import com.qjy.easynews.model.Title;
import com.qjy.easynews.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qjy on 15-4-21.
 */
public class DummyFragment extends Fragment {

    //放Title
    private List<Title> mTitles;

    //放Title的TextView
    private TextView[] mTitlesText;

    //放Title的ScrollView
    private HorizontalScrollView mScroll;

    //ScrollView中的LinearLayout
    private LinearLayout mTitleLayout;

    //MainActivity传过来的决定内容的type
    private String mType;

    //用来放具体内容的ViewPager
    private ViewPager mContentPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        //获得 MainActivity 传过来的 type
        mType = bundle.getString("type");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //将 fragment 泵进去
        View view = inflater.inflate(R.layout.fragment_dummy, container, false);
        initView(view);
        return view;
    }

    public void initView(View view) {

        //获得Title的布局,用于后面的添加标题
        mTitleLayout = (LinearLayout) view.findViewById(R.id.main_title);

        //获得ScrollView的布局，用于后面滚动
        mScroll = (HorizontalScrollView) view.findViewById(R.id.main_scroll);

        //ViewPager布局，用来放当前显示的内容Fragment
        mContentPager = (ViewPager) view.findViewById(R.id.fragment_pager);

        // 初始化标题栏
        initTitle();
    }

    /**
     * 初始化标题栏
     */
    public void initTitle() {

        //根据 type 类型 从网络获取标题内容，显示出来
        HttpUtils.getTitles(getActivity(), mType, new HttpUtils.OnSuccessListener() {
            /**
             * 回调方法，返回来的是 Title 的 List
             * @param list
             */
            @Override
            public void loadUI(List<Title> list) {
                // 将 Title 的 List 放给 mTitles
                mTitles = list;

                //将标题里面原有的东西删掉
                mTitleLayout.removeAllViews();

                //根据 List 初始化标题内容
                initTitleText(list);

                //初始化ViewPager
                initViewPager();
            }

            @Override
            public void loadNewsUI(List<News> topList, List<News> list) {

            }

        });
    }

    /**
     * 初始化Title的文字内容
     *
     * @param list
     */
    private void initTitleText(List<Title> list) {
        //初始化 mTitlesText ,将TextView创建出来
        mTitlesText = new TextView[list.size()];

        for (int i = 0; i < list.size(); i++) {
            TextView textView = new TextView(getActivity());
            textView.setText(list.get(i).getmName());
            textView.setTextSize(17);
            textView.setPadding(15, 20, 15, 15);
            addTitleListener(textView, i);
            // 将 TextView 加进布局
            mTitleLayout.addView(textView);
            mTitlesText[i] = textView;

        }
        //初始化是默认选中第一个
        selectTitle(0);
    }

    private void initViewPager() {

        //根据不同的 type 选择不同的 Fragment
        switch (mType) {
            case Constants.NEWS:
                initFragment(ContentFragment.class);
                break;
            case Constants.IMAGE:
                initFragment(PicContentFragment.class);
                break;
            case Constants.VIDEO:
                initFragment(VideoContentFragment.class);
                break;
        }


        if (mContentPager == null) {
            return;
        }

        mContentPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                selectTitle(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    /**
     * 初始化当前所对应的Fragment（创建不同的Fragment）
     */
    private <T extends Fragment> void initFragment(Class<T> T) {

        List<Fragment> totalList = new ArrayList<Fragment>();

        for (int i = 0; i < mTitles.size(); i++) {
            T fragment = null;
            try {
                fragment = T.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString("cate_id", mTitles.get(i).getmCateId());
                fragment.setArguments(bundle);
                totalList.add(fragment);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        ContentAdapter adapter = new ContentAdapter(getChildFragmentManager(), totalList);

        mContentPager.setAdapter(adapter);
    }


    private void addTitleListener(TextView textView, final int position) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTitle(position);

            }
        });
    }


    private void selectTitle(int position) {
        for (int i = 0; i < mTitlesText.length; i++) {
            if (i == position) {
                setTitleEnable(i, false);

            } else {
                setTitleEnable(i, true);
            }
        }
        mScroll.scrollTo(60 * position, 0);
        mContentPager.setCurrentItem(position);
    }

    private void setTitleEnable(int position, boolean enabled) {
        setEnable(mTitlesText[position], enabled);
        int color = enabled ? Color.BLACK : Color.RED;
        mTitlesText[position].setTextColor(color);
        if (enabled) {
            mTitlesText[position].setBackgroundColor(Color.parseColor("#00000000"));
        } else {
            mTitlesText[position].setBackgroundResource(R.drawable.textbg);
        }
    }

    private void setEnable(View v, boolean enabled) {
        v.setEnabled(enabled);
    }
}
