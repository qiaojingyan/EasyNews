package com.qjy.easynews.biz.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.qjy.easynews.R;
import com.qjy.easynews.model.User;
import com.qjy.easynews.utils.HttpUtils;

/**
 * Created by qjy on 15-4-25.
 */
public class ConsummateActivity extends Activity {

    public TextView textView_consummate_nickname,textView_consummate_sex,textView_consummate_tel;
    private View nickNameView;
    private TextView textView_dialog_title;
    private EditText editText;
    private String[] items;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_consummate);

        initView();
        initData();


    }

    public void initView(){
        textView_consummate_nickname = (TextView) findViewById(R.id.textView_consummate_nickname);
        textView_consummate_sex = (TextView) findViewById(R.id.textView_consummate_sex);
        textView_consummate_tel = (TextView) findViewById(R.id.textView_consummate_tel);


    }

    public void initData(){
        Bundle bundle = getIntent().getExtras();
        user = (User) bundle.getSerializable("user");
    }

    public void clickButton(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog = null;

        switch (view.getId()){
            case R.id.btn_consummate_back:
                finish();
                break;
            case R.id.btn_consummate_finish:
                String nickname = textView_consummate_nickname.getText().toString();
                String sex = textView_consummate_sex.getText().toString();
                String tel = textView_consummate_tel.getText().toString();
                if(user != null){
                    user.setNickname(nickname);
                    user.setSex(sex);
                    user.setTel(tel);
                    HttpUtils.register(user);
                }



                break;
            case R.id.linearLayout_consummate_nickname:
                nickNameView = getLayoutInflater().inflate(R.layout.dialog_nickname,null);
                textView_dialog_title = (TextView) nickNameView.findViewById(R.id.textView_dialog_title);
                editText = (EditText) nickNameView.findViewById(R.id.edt_dialog_nickname);
                builder.setTitle("输入昵称");
                textView_dialog_title.setText("昵称：");

                builder.setView(nickNameView);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String nickName = editText.getText().toString();
                        textView_consummate_nickname.setText(nickName);
                    }
                });
                builder.setNegativeButton("取消",null);
                dialog = builder.show();
                if(dialog != null){
                    dialog.setCanceledOnTouchOutside(false);
                }

                break;
            case R.id.linearLayout_consummate_sex:
                builder.setTitle("选择性别");
                items = new String[]{"男", "女", "保密"};
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textView_consummate_sex.setText(items[which]);
                    }
                });
                dialog = builder.show();
                if(dialog != null){
                    dialog.setCanceledOnTouchOutside(false);
                }
                break;
            case R.id.linearLayout_consummate_tel:
                nickNameView = getLayoutInflater().inflate(R.layout.dialog_nickname,null);
                textView_dialog_title = (TextView) nickNameView.findViewById(R.id.textView_dialog_title);
                editText = (EditText) nickNameView.findViewById(R.id.edt_dialog_nickname);
                builder.setTitle("输入手机号码");
                textView_dialog_title.setText("手机号：");

                if(nickNameView != null){
                    builder.setView(nickNameView);

                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String tel = editText.getText().toString();
                            textView_consummate_tel.setText(tel);
                        }
                    });
                    builder.setNegativeButton("取消",null);
                    dialog = builder.show();
                    if(dialog != null){
                        dialog.setCanceledOnTouchOutside(false);
                    }
                }
                break;

        }
    }
}