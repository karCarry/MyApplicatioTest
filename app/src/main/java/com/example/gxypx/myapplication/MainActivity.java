package com.example.gxypx.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mButtonTrue;//正确按钮
    private Button mButtonFalse;//错误按钮
    private TextView mTextViewQuestion;
    private Button mButtonNext;//下一题
    private Button mButtonAnswer;//查看答案
    private int mQuestionsIndex = 0;
    private Question[] mQuestions = new Question[]{
            new Question(R.string.Q1, false),
            new Question(R.string.Q2, true),
            new Question(R.string.Q3, true),
            new Question(R.string.Q4, true),
            new Question(R.string.Q5, true),
            new Question(R.string.Q6, true),
            new Question(R.string.Q7, true),
            new Question(R.string.Q8, true),

    };
    private static final String TAG="MainActivity";
    private static final String KEY_INDEX="index";//索引健
    private static final int REQUEST_CODE_ANSWER=10;//请求代码（表示发给AnswerActivity的）
    private TranslateAnimation mTranslateAnimation; //平移动画

    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    private void UpdateQuestion()
    {
        int i = mQuestions[mQuestionsIndex].getQuestion_id();//获取题目方法
        mTextViewQuestion.setText(i);//设置到文本
//        mTranslateAnimation = new TranslateAnimation
//        (-20, 20, 50, 50); //这四个参数含义分别是当前View x起点坐标、x终点坐标、y起点坐标、y终点坐标
//        mTranslateAnimation.setDuration(10); //动画持续时间
//        mTranslateAnimation.setRepeatCount(5); //重复次数(不包括第一次)
//        mTranslateAnimation.setRepeatMode(Animation.REVERSE); //动画执行模式，Animation.RESTART:从头开始，Animation.REVERSE:逆序
//        mTextViewQuestion.startAnimation(mTranslateAnimation);//文本组件加载指定的动画
        Animation set=AnimationUtils.loadAnimation(this,R.anim.animation_list);
        mTextViewQuestion.startAnimation(set);
    }
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG,"创建界面");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null){
            mQuestionsIndex=savedInstanceState.getInt(KEY_INDEX);
            Log.d(TAG,"回复状态,当前KEY_INDEX="+savedInstanceState.getInt(KEY_INDEX));
        }
        mButtonTrue = findViewById(R.id.true_Btn_id);
        mButtonFalse = findViewById(R.id.false_Btn_id);
        mTextViewQuestion = findViewById(R.id.question_id);
        mButtonAnswer=findViewById(R.id.answer_Btn_id);
        UpdateQuestion();
        mButtonNext=findViewById(R.id.next_Btn_id);
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestionsIndex=(mQuestionsIndex+1)%mQuestions.length;/*防溢出的递增循环方法*/
                UpdateQuestion();//更新题目
                mButtonNext.setEnabled(false);//新题目开始时保持按钮不可用的状态
                if(mQuestionsIndex==mQuestions.length-1){
                    Toast.makeText(getApplicationContext(),R.string.last,Toast.LENGTH_LONG).show();
                    mButtonNext.setText(R.string.TextView_reset);
                    updateButtonNext(R.drawable.ic_reset);
                }
                if(mQuestionsIndex==0){//判断是否为第一题
                    mButtonNext.setText("下一题");//更换按钮文字
                    updateButtonNext(R.drawable.ic_next);
                }
            }
        });
        mButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//回答正确时
                checkQuestions(true);
            }
        });
        mButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//回答错误时
               checkQuestions(false);
            }
        });
        mButtonAnswer.setOnClickListener(new View.OnClickListener() {//查看答案界面
            @Override
            public void onClick(View v) {
                String temp;
                if(mQuestions[mQuestionsIndex].isAnswer()){//解析答案数据
                    temp="正确";
                } else{
                    temp="错误";
                }
                Intent it=new Intent(MainActivity.this,AnswerActivity.class);//显示调用
                it.putExtra("msg",temp);//将数据附加到it中
                startActivityForResult(it,REQUEST_CODE_ANSWER);//需要返回值的启动activity方法
//                startActivity(it);
//                if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){//检查是否已经获得授权
//                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);//动态申请权限，请求代码1
//                }
//                Intent it=new Intent(Intent.ACTION_CALL, Uri.parse("tel:17607900324"));//自动拨打
//                Intent it=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:17607900324"));//手动拨打
//                it.setData(Uri.parse("tel:17607900324"));//使用setData方式补充号码
//                Intent it=new Intent(Intent.ACTION_SENDTO, Uri.parse("sms_to:17607900324"));//发短信
//                it.putExtra("sms_body","123121312");//填写短信框架
//                startActivity(it);
            }
        });
    }
    private void checkQuestions(boolean userAnswer){
        boolean trueAnswer=mQuestions[mQuestionsIndex].isAnswer();//取得正确答案
        int message;
        if(userAnswer==trueAnswer){
            message=R.string.yes;
            mButtonNext.setEnabled(true);
        }else{
            message=R.string.no;
            mButtonNext.setEnabled(false);
        }
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }
    private void updateButtonNext(int imageID){
        Drawable d= getDrawable(imageID);//获取图片ID
        d.setBounds(0,0,d.getMinimumWidth(),d.getMinimumHeight());
        mButtonNext.setCompoundDrawables(null,null,d,null);
    }
   protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, mQuestionsIndex);//形成键值对
        Log.d(TAG,"onSaveInstanceState()保存状态，当前KEY_INDEX="+outState.getInt(KEY_INDEX));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {//请求代码、返回代码、返回来的intent
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_ANSWER && resultCode==RESULT_OK){
            String result=data.getStringExtra("answer_shown");
            Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
        }
    }
    //请求代码比喻为谁发起的请求
    //返回代码比喻为谁返回了数据
    //Intent对象比喻为数据内容
}
