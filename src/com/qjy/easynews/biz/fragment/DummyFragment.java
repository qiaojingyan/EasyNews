package com.qjy.easynews.biz.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.qjy.easynews.model.News;
import com.qjy.easynews.model.Title;
import com.qjy.easynews.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qjy on 15-4-21.
 */
public class DummyFragment extends Fragment {

    private List<Title> mTitles;
    private TextView[] mTitlesText;
    private HorizontalScrollView mScroll;
    private LinearLayout mTitleLayout;
    private String mType;
    private ViewPager mContentPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        mType = bundle.getString("type");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dummy,container,false);
        initView(view);
        return view;
    }

    public void initView(View view){
        Log.e("DummyFragment", "lkamfamkfa");
        mTitleLayout = (LinearLayout) view.findViewById(R.id.main_title);
        mScroll = (HorizontalScrollView) view.findViewById(R.id.main_scroll);
        mContentPager = (ViewPager) view.findViewById(R.id.fragment_pager);
        initTitle();
    }

    public void initTitle() {

        HttpUtils.getTitles(getActivity(), mType, new HttpUtils.OnSuccessListener() {
            @Override
            public void loadUI(List<Title> list) {
                mTitles = list;
                mTitleLayout.removeAllViews();
                initTitleText(list);
                initViewPager();
            }

            @Override
            public void loadNewsUI(List<News> topList, List<News> list) {

            }

        });
    }

    private void initViewPager(){
        List<ContentFragment> totalList = new ArrayList<ContentFragment>();

        for (int i = 0; i < mTitles.size(); i++) {
            ContentFragment fragment = new ContentFragment();
            Bundle bundle = new Bundle();
            bundle.putString("cate_id",mTitles.get(i).getmCateId());
            fragment.setArguments(bundle);
            totalList.add(fragment);
        }

        ContentAdapter adapter = new ContentAdapter(getActivity().getSupportFragmentManager(),totalList);
        mContentPager.setAdapter(adapter);

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
     * 初始化Title的文字内容
     * @param list
     */
    private void initTitleText(List<Title> list){
        mTitlesText = new TextView[list.size()];
        for (int i = 0; i < list.size(); i++) {
            TextView textView = new TextView(getActivity());
            textView.setText(list.get(i).getmName());
            textView.setTextSize(17);
            textView.setPadding(15, 20, 15, 15);
            addTitleListener(textView, i);
            mTitleLayout.addView(textView);
            mTitlesText[i] = textView;

        }
        selectTitle(0);
    }

    private void addTitleListener(TextView textView, final int position){
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTitle(position);

            }
        });
    }



    private void selectTitle(int position){
        for (int i = 0; i < mTitlesText.length; i++) {
            if(i==position){
                setTitleEnable(i, false);

            }else{
                setTitleEnable(i,true);
            }
        }
        mScroll.scrollTo(60*position,0);
    }

    private void setTitleEnable(int position,boolean enabled){
        setEnable(mTitlesText[position],enabled);
        int color = enabled? Color.BLACK:Color.RED;
        mTitlesText[position].setTextColor(color);
        if(enabled){
            mTitlesText[position].setBackgroundColor(Color.parseColor("#00000000"));
        }else{
            mTitlesText[position].setBackgroundResource(R.drawable.textbg);
        }
    }

    private void setEnable(View v, boolean enabled) {
        v.setEnabled(enabled);
    }
}
