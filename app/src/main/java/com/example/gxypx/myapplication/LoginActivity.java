package com.example.gxypx.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gxypx.myapplication.com.example.gxpx.utils.MD5Utils;

public class LoginActivity extends AppCompatActivity {
    private TextView mtv_main_title;//标题
    private TextView mtv_back,mtv_register,mtv_find_psw;//返回按钮
    private Button mButton_login;
    private String userName,password,spPsw;
    private EditText mEditText_et_login_username_id,mEditText_et_login_password_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }
    private void init(){
        mtv_main_title=findViewById(R.id.tv_main_title);
        mtv_main_title.setText("登录");
        mtv_back=findViewById(R.id.tv_back);
        mtv_register=findViewById(R.id.tv_register);
        mtv_find_psw=findViewById(R.id.tv_find_psw);
        mButton_login=findViewById(R.id.btn_login_id);
        mEditText_et_login_username_id=findViewById(R.id.et_login_username_id);
        mEditText_et_login_password_id=findViewById(R.id.et_login_password_id);
        //返回按钮点击事件
        mtv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        });
        //立即注册控件的点击事件
        mtv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,1);
            }
        });
        //找回密码控件点击事件
        mtv_find_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到找回密码界面
            }
        });
        mButton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName=mEditText_et_login_username_id.getText().toString().trim();
                password=mEditText_et_login_password_id.getText().toString().trim();
                String md5Psw=MD5Utils.md5(password);
                spPsw=readPsw(userName);
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(LoginActivity.this, "请输入用户名",
                    Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "请输入密码",
                    Toast.LENGTH_SHORT).show();
                    return;
                }else if(md5Psw.equals(password)){
                    Toast.makeText(LoginActivity.this, "登录成功",
                    Toast.LENGTH_SHORT).show();
                    //保存登录状态和登录的用户名
                    saveLoginStatus(true,userName);
                    //把登录成功的状态传递到MainActivity中
                    Intent data=new Intent();
                    data.putExtra("isLogin",true);
                    setResult(RESULT_OK,data);
                    LoginActivity.this.finish();
                    return;
                }else if(!TextUtils.isEmpty(spPsw)&&!md5Psw.equals(spPsw)){
                    Toast.makeText(LoginActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(LoginActivity.this,"此用户名不存在",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //从SharedPreferences中根据用户名读取密码
    private String readPsw(String userName) {
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        return sp.getString(userName,"");
    }
    //保存登录状态和登录用户名到SharedPreferences中
    private  void  saveLoginStatus(boolean status,String userName){
    //loginInfo表示文件名
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();//获取编辑器
        editor.putBoolean("isLogin",status);
        editor.putString("LoginUserName",userName);//存入登录时的用户名
        editor.commit();//提交修改
    }
    protected  void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(data!=null){
            //从注册界面传递过来的用户名
            String userName=data.getStringExtra("userName");
            if(!TextUtils.isEmpty(userName)){
                mEditText_et_login_username_id.setText("userName");
                //设置光标的位置
                mEditText_et_login_username_id.setSelection(userName.length());
            }
        }
    }


}
