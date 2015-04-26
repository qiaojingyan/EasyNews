package com.qjy.easynews.biz.activity;

import android.app.Activity;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;
import com.qjy.easynews.R;

/**
 * Created by qjy on 15-4-24.
 */
public class VideoDetailActivity extends Activity {

    private VideoView videoView;
    private View imageView_play;
    private String videoUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videodetail);

        initView();
        initData();
    }

    public void initView(){
        videoView = (VideoView) findViewById(R.id.videoView_videodetail_container);
        imageView_play = findViewById(R.id.imageView_videodetail_play);

    }

    public void initData(){
        Bundle bundle = getIntent().getExtras();
        videoUrl = bundle.getString("video_url");

        if(!videoUrl.startsWith("http://")){
            videoUrl = "http://211.154.6.210:8085/webblue/uploads/141020233730abc.mp4";
        }

        videoView.setVideoURI(Uri.parse(videoUrl));


    }

    public void clickButton(View view){

        switch (view.getId()){
            case R.id.imageView_videodetail_play :
                videoView.start();
                imageView_play.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        videoView.pause();
    }
}