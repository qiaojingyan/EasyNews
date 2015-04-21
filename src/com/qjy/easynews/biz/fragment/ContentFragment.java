package com.qjy.easynews.biz.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;
import com.qjy.easynews.R;
import com.qjy.easynews.model.News;
import com.qjy.easynews.model.Title;
import com.qjy.easynews.utils.HttpUtils;

import java.util.List;

/**
 * Created by qjy on 15-4-21.
 */
public class ContentFragment extends Fragment {

    private ListView mListView;
    private int mCurrentPage;
    private String mCateId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        mCateId = bundle.getString("cate_id");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        initView(view);

        initData();


        return view;
    }

    private void initView(View view){
        mListView = (ListView) view.findViewById(R.id.content_list);

    }

    private void initData(){
        HttpUtils.getNews(getActivity(), mCateId, mCurrentPage, new HttpUtils.OnSuccessListener() {
            @Override
            public void loadUI(List<Title> list) {

            }

            @Override
            public void loadNewsUI(List<News> topList,List<News> list) {

            }
        });
    }
}