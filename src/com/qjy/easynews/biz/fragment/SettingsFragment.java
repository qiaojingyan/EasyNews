package com.qjy.easynews.biz.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.qjy.easynews.R;
import com.qjy.easynews.app.AppCtx;
import com.qjy.easynews.biz.activity.ChooseActivity;

/**
 * Created by qjy on 15-4-24.
 */
public class SettingsFragment extends Fragment {

    private Button button_settings_login;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        initView(view);

        if(AppCtx.getInstance().isLogin()){
            initLoginSettings();
        }else{
            initSettings();
        }


        return view;
    }

    public void initView(View view){
        button_settings_login = (Button) view.findViewById(R.id.button_settings_login);
    }

    public void initLoginSettings(){
        button_settings_login.setClickable(false);
    }

    public void initSettings(){
        button_settings_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChooseActivity.class);
                startActivity(intent);
            }
        });
    }
}