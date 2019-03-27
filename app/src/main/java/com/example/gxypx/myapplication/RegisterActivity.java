package com.example.gxypx.myapplication;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gxypx.myapplication.com.example.gxpx.utils.MD5Utils;

import java.security.NoSuchAlgorithmException;

public class RegisterActivity extends AppCompatActivity {
    private TextView mtv_main_title;//标题
    private TextView mtv_back;//返回按钮
    private Button mBtn_register;//注册按钮
    private EditText mEditText_username,mEditText_psw,mEditText_again;//用户名、密码、再次输入密码的控件
    private String userName,password,pwsagain;//用户名、密码、再次输入密码的控件的获取值
    private RelativeLayout r1_title_bar;//标题布局
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }
    private void init(){
        //从main_title_bar.xml页面中获得对应的UI的控件
        mtv_main_title=findViewById(R.id.tv_main_title);
        mtv_main_title.setText("注册");
        mtv_back=findViewById(R.id.tv_back);
        r1_title_bar=findViewById(R.id.title_bar);
        r1_title_bar.setBackgroundColor(Color.TRANSPARENT);
        //从activity_register.xml页面布局中获得对应的UI控件
        mBtn_register=findViewById(R.id.btn_register_id);
        mEditText_username=findViewById(R.id.et_username_id);
        mEditText_psw=findViewById(R.id.et_password_id);
        mEditText_again=findViewById(R.id.et_password_again_id);
        mtv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        });
        mBtn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEidtString();
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(RegisterActivity.this, "请输入用户名",
                    Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "请输入密码",
                    Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(pwsagain)){
                    Toast.makeText(RegisterActivity.this, "请再次输入密码",
                    Toast.LENGTH_SHORT).show();
                    return;
                }else if(!password.equals(pwsagain)){
                    Toast.makeText(RegisterActivity.this, "输入两次密码不一致",
                    Toast.LENGTH_SHORT).show();
                    return;
                }else if(isExistUsername(userName)){
                    Toast.makeText(RegisterActivity.this, "此用户已存在",
                    Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(RegisterActivity.this, "注册成功",
                    Toast.LENGTH_SHORT).show();
                    //把用户名密码保存到SharedPreferences中
                }
            }
        });
    }
    //获取对应控件的字符串
    private void getEidtString(){
        userName=mEditText_username.getText().toString().trim();
        password=mEditText_psw.getText().toString().trim();
        pwsagain=mEditText_again.getText().toString().trim();
    }
    //从SharedPreferences中读取输入的用户名，并判断此用户名是否存在
    private boolean isExistUsername(String userName) {
        boolean has_userName=false;
        SharedPreferences sp=getSharedPreferences("loginfile",MODE_PRIVATE);
        String spPsw=sp.getString(userName,"");
        if(!TextUtils.isEmpty(spPsw)){
            has_userName=true;
        }
        return  has_userName;
    }
    //保存用户名和密码到SharedPreferences中
    private void saveRegisterInfo(String userName,String password) {
        try {
            String  md5PassWord = MD5Utils.md5(password);
            SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString(userName,md5PassWord);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
