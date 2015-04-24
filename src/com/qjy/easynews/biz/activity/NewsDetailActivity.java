package com.qjy.easynews.biz.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.qjy.easynews.R;
import com.qjy.easynews.app.AppCtx;
import com.qjy.easynews.model.Comment;
import com.qjy.easynews.model.News;
import com.qjy.easynews.utils.BitmapCache;
import com.qjy.easynews.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qjy on 15-4-23.
 */
public class NewsDetailActivity extends Activity {

    private String mNewsId;
    private TextView mCreateTimeText;
    private TextView mTitleText;
    private TextView mContentText;
    private LinearLayout mCommentContainer;
    private News news;
    private List<Comment> mComments;
    private ProgressDialog mProgressDialog;
    private NetworkImageView mPicNetWorkImg;
    private RequestQueue mRequestQueue;
    private TextView mNoCommentsText;
    private TextView textView_newsdetail_loadingMoreComments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetail);

        initView();
    }

    public void initView() {

        mCreateTimeText = (TextView) findViewById(R.id.textView_newsdetail_createtime);
        mTitleText = (TextView) findViewById(R.id.textView_newsdetail_title);
        mContentText = (TextView) findViewById(R.id.textView_newsdetail_content);
        mCommentContainer = (LinearLayout) findViewById(R.id.linearLayout_newsdetail_comments);
        mPicNetWorkImg = (NetworkImageView) findViewById(R.id.networkImageView_newsdetail_pic);
        mNoCommentsText = (TextView) findViewById(R.id.textView_newsdetail_nocomments);
        textView_newsdetail_loadingMoreComments = (TextView) findViewById(R.id.textView_newsdetail_loadingMoreComments);


        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("正在加载中");
        mProgressDialog.setCanceledOnTouchOutside(false);

        initData();


    }

    public void initData() {

        //获取RequestQueue
        mRequestQueue = AppCtx.getInstance().getRequestQueue();

        mPicNetWorkImg.setDefaultImageResId(R.drawable.defaultimg);

        //获取数据
        Bundle bundle = getIntent().getExtras();
        mNewsId = bundle.getString("news_id");

        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }

        //进行网络 NewsDetail 操作
        HttpUtils.getNewsDetail(this, mNewsId, new HttpUtils.OnObjSuccessListener() {
            @Override
            public void loadUI(Object obj) {
                if (obj instanceof News) {

                    //获得到 News 对象
                    news = (News) obj;

                    //进行界面 UI 加载
                    loadDetailUIData();

                }
            }

            @Override
            public void errorUI() {

            }
        });


        //进行网络 Comments 操作
        HttpUtils.getComments(this, mNewsId, 1, new HttpUtils.OnObjSuccessListener() {
            @Override
            public void loadUI(Object obj) {
                if (obj instanceof List) {

                    //获得到 News 对象
                    mComments = (List<Comment>) obj;

                }

                initCommentsUIData();
            }

            @Override
            public void errorUI() {

            }
        });


    }

    public void clickButton(View view){
        switch (view.getId()){
            case R.id.btn_newsdetail_back:
                finish();
                break;
            case R.id.btn_newsdetail_favorite:
                break;
            case R.id.btn_newsdetail_share:
                break;
            case R.id.textView_newsdetail_loadingMoreComments:

                Intent intent = new Intent(this,CommentsActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("item_id",mNewsId);

                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(intent);
                break;
        }
    }

    private void loadDetailUIData() {
        if (news != null) {
            List<String> pic_list = news.getPic_list();
            if (pic_list != null) {
                ImageLoader imageLoader = new ImageLoader(mRequestQueue, BitmapCache.getInstance());

                mPicNetWorkImg.setImageUrl(pic_list.get(0), imageLoader);

            }
            mContentText.setText(news.getContent());
            mCreateTimeText.setText(news.getCreate_time());
            mTitleText.setText(news.getTitle());
        }

        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void initCommentsUIData() {


        if (mComments != null && mComments.size() != 0) {

            mNoCommentsText.setVisibility(View.GONE);
            textView_newsdetail_loadingMoreComments.setVisibility(View.VISIBLE);


                View view = LayoutInflater.from(this).inflate(R.layout.item_comments, null);
                TextView userTextView = (TextView) view.findViewById(R.id.textView_item_comments_user);
                TextView contentTextView = (TextView) view.findViewById(R.id.textView_item_comments_content);
                TextView createTimeTextView = (TextView) view.findViewById(R.id.textView_item_comments_createtime);

                userTextView.setText(mComments.get(0).getUser());
                contentTextView.setText(mComments.get(0).getContent());
                createTimeTextView.setText(mComments.get(0).getCreate_time());


                View lineView = new View(this);
                lineView.setBackgroundColor(Color.BLACK);
                lineView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));

                mCommentContainer.addView(lineView);
                mCommentContainer.addView(view);

        } else {
            mNoCommentsText.setVisibility(View.VISIBLE);
            textView_newsdetail_loadingMoreComments.setVisibility(View.GONE);
        }

    }
}