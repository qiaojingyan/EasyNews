package com.qjy.easynews.biz.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.qjy.easynews.R;
import com.qjy.easynews.biz.Adapter.CommentAdapter;
import com.qjy.easynews.biz.widget.PullToRefreshBase;
import com.qjy.easynews.biz.widget.PullToRefreshListView;
import com.qjy.easynews.model.Comment;
import com.qjy.easynews.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qjy on 15-4-23.
 */
public class CommentsActivity extends Activity {

    private int mCurrentPage = 1;
    private PullToRefreshListView mRefreshListView;
    private ListView mListView;
    private String item_id;
    private List<Comment> totalList;
    private CommentAdapter adapter;
    private boolean isLoading;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        initView();

        addListener();


    }

    public void initView(){

        mRefreshListView = (PullToRefreshListView) findViewById(R.id.content_list);

        mListView = mRefreshListView.getRefreshableView();

        View view = getLayoutInflater().inflate(R.layout.page_header,null);
        LinearLayout mLinearEmpty = (LinearLayout) findViewById(R.id.linearLayout_contentfragment_emptyinfo);

        mRefreshListView.setEmptyView(mLinearEmpty);
        mListView.addHeaderView(view);

        initData();

    }

    public void initData(){

        Bundle bundle = getIntent().getExtras();
        item_id = bundle.getString("item_id");

        totalList = new ArrayList<>();
        adapter = new CommentAdapter(this, totalList);
        mListView.setAdapter(adapter);

        loadData();


    }

    public void loadData(){

        HttpUtils.getComments(this, item_id, mCurrentPage, new HttpUtils.OnObjSuccessListener() {
            @Override
            public void loadUI(Object obj) {
                if(obj != null){
                    if(obj instanceof List){
                        notifyAdapter((List<Comment>)obj);
                    }
                }
            }

            @Override
            public void errorUI() {

            }
        });
    }

    public void addListener(){
        mRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentPage = 1;
                loadData();
            }
        });

        mRefreshListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                mCurrentPage++;
                if(!isLoading) {
                    isLoading = true;
                    loadData();
                }
            }
        });
    }

    public void clickButton(View view){
        finish();
    }
    
    private void notifyAdapter(List<Comment> list){
        if(mCurrentPage == 1){
            totalList.clear();
            mRefreshListView.onRefreshComplete();
        }
        totalList.addAll(list);
        
        adapter.notifyDataSetChanged();

        isLoading = false;
        
    }
}