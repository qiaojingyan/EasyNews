package com.qjy.easynews.biz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.qjy.easynews.R;

/**
 * Created by qjy on 15-4-25.
 */
public class ChooseActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);




    }

    public void clickButton(View view){

        switch (view.getId()){
            case R.id.button_choose_login :
                Intent intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.button_choose_register:
                Intent intent1 = new Intent(this,RegisterActivity.class);
                startActivity(intent1);
                break;

        }
    }
}