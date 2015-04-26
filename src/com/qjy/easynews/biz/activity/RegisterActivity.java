package com.qjy.easynews.biz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.qjy.easynews.R;
import com.qjy.easynews.app.AppCtx;
import com.qjy.easynews.configuration.Constants;
import com.qjy.easynews.model.User;
import com.qjy.easynews.utils.BitmapCache;
import com.qjy.easynews.utils.HttpUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by qjy on 15-4-25.
 */
public class RegisterActivity extends Activity {

    private NetworkImageView networkImageView_register_code;
    private EditText edt_register_username,edt_register_psw,edt_register_code;
    private RequestQueue requestQueue;
    private String codeURL;
    private ImageLoader imageLoader;
    private String sequence;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);

        initView();
        initData();
        addListener();

    }

    public void initView(){
        networkImageView_register_code = (NetworkImageView) findViewById(R.id.networkImageView_register_code);
        edt_register_username = (EditText) findViewById(R.id.edt_register_username);
        edt_register_psw = (EditText) findViewById(R.id.edt_register_psw);
        edt_register_code = (EditText) findViewById(R.id.edt_register_code);



    }

    public void initData(){
        requestQueue = AppCtx.getInstance().getRequestQueue();
//        sequence = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        sequence = getSerialNumber();
        codeURL = Constants.getCodeURL(sequence);


        imageLoader = new ImageLoader(requestQueue, BitmapCache.getInstance());
        networkImageView_register_code.setErrorImageResId(R.drawable.broken);
        networkImageView_register_code.setImageUrl(codeURL, imageLoader);


    }

    public void addListener(){
        edt_register_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){

                }
            }
        });
    }

    public void clickButton(View view){
        switch (view.getId()){
            case R.id.btn_regist_back:
                finish();
                break;
            case R.id.btn_regist_next:
                String username = edt_register_username.getText().toString();
                String password = edt_register_psw.getText().toString();
                String code = edt_register_code.getText().toString();

                if(("").equals(username) || ("").equals(password) || ("").equals(code)){
                    Toast.makeText(this,"请将信息填写完整",Toast.LENGTH_SHORT).show();
                    break;
                }

                User user = new User();
                user.setUsername(username);
                user.setSequence(sequence);
                user.setPassword(password);
                user.setVerify_code(code);

                Log.e("RegisterActivity","出日志 ");

                HttpUtils.register(user);

//                Intent intent = new Intent(this,ConsummateActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("user",user);
//                intent.putExtras(bundle);
//                startActivity(intent);

                break;

        }
    }

    public static String getSerialNumber(){
        String serialNumber = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serialNumber = (String) get.invoke(c, "ro.serialno");

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return serialNumber;
    }



}