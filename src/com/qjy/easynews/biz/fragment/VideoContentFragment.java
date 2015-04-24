package com.qjy.easynews.biz.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.qjy.easynews.R;
import com.qjy.easynews.biz.Adapter.VideoAdapter;
import com.qjy.easynews.biz.widget.PullToRefreshBase;
import com.qjy.easynews.biz.widget.PullToRefreshListView;
import com.qjy.easynews.model.Video;
import com.qjy.easynews.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qjy on 15-4-24.
 */
public class VideoContentFragment extends Fragment {

    private PullToRefreshListView refreshListView_content;
    private LinearLayout linearLayout_empty;
    private Context context;
    private ListView listView;
    private VideoAdapter adapter;
    private String cate_id;
    private int currentPage = 1;
    private List<Video> totalList;
    private boolean isLoading;
    private LinearLayout linearLayout_empty2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        context = getActivity();

        initView(view);
        initData();
        addListener();

        return view;
    }

    public void initView(View view) {

        refreshListView_content = (PullToRefreshListView) view.findViewById(R.id.content_list);

        linearLayout_empty = (LinearLayout) view.findViewById(R.id.linearLayout_contentfragment_emptyinfo);

        linearLayout_empty2 = (LinearLayout) view.findViewById(R.id.linearLayout_contentfragment_emptyinfo2);

        listView = refreshListView_content.getRefreshableView();

        refreshListView_content.setEmptyView(linearLayout_empty);

    }

    public void initData() {

        Bundle bundle = getArguments();
        cate_id = bundle.getString("cate_id");

        totalList = new ArrayList<>();
        adapter = new VideoAdapter(totalList, context);
        listView.setAdapter(adapter);

        loadData();
    }

    public void addListener() {
        refreshListView_content.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                loadData();
            }
        });

        refreshListView_content.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                if (!isLoading) {
                    isLoading = true;
                    Toast.makeText(context, "正在加载", Toast.LENGTH_SHORT).show();
                    currentPage++;
                    loadData();
                }
            }
        });
    }

    private void loadData() {
        HttpUtils.getVideoList(context, cate_id, currentPage, new HttpUtils.OnObjSuccessListener() {
            @Override
            public void loadUI(Object obj) {
                if (obj instanceof List) {
                    List<Video> list = (List<Video>) obj;
                    notifyAdapter(list);
                }
            }

            @Override
            public void errorUI() {
                linearLayout_empty.setVisibility(View.GONE);
//                refreshListView_content.removeView(linearLayout_empty);

                refreshListView_content.setEmptyView(linearLayout_empty2);
            }
        });
    }

    private void notifyAdapter(List<Video> list) {
        if (currentPage == 1) {
            refreshListView_content.onRefreshComplete();
            totalList.clear();

        }
        totalList.addAll(list);
        adapter.notifyDataSetChanged();
        isLoading = false;
    }
}