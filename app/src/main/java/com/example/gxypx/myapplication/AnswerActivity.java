package com.example.gxypx.myapplication;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class AnswerActivity extends AppCompatActivity {
    private TextView mTextView_answer;
    private ImageView mImageView;
    private static final String EXTRA_ANSWER_SHOWN="answer_shown";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();//隐藏标题栏
        setContentView(R.layout.activity_answer);
        mTextView_answer=findViewById(R.id.answer_txt_id);
        mImageView=findViewById(R.id.imageView);

        Intent data=getIntent();//获取传过来的Intent对象
        String answer=data.getStringExtra("msg");//取出msg对应的数据
        mTextView_answer.setText(answer);//显示文本
        data.putExtra(EXTRA_ANSWER_SHOWN,"已查看答案");
        setResult(Activity.RESULT_OK,data);//返回代码和数据

        mImageView=findViewById(R.id.imageView);
        Animator anim= AnimatorInflater.loadAnimator(this,R.animator.animation_alpha);//加载本地动画属性
        anim.setTarget(mImageView);
        anim.start();

        anim.addListener(new Animator.AnimatorListener() {//动画监听器
            @Override
            public void onAnimationStart(Animator animation) {
                //动画开始前

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //动画结束时
                ValueAnimator moneyAnimator=ValueAnimator.ofFloat(0f,9999999999.99f);//ofFloat浮点估值器方法（初始值，最终值）  f表示浮点型
                moneyAnimator.setInterpolator(new LinearInterpolator());//差值器
                moneyAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {//moneyAnimator动画执行时发生
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float money= (float) animation.getAnimatedValue();//获取动画数值（因为估值器会帮我们计算浮点数值）
                        mTextView_answer.setText(String.format("%.2f$",money));//格式化字符串，表示小数点后保留两位小数，显示到文本中
                    }
                });
                int startcolor=Color.parseColor("#FF69B4");//起始颜色
                int endcolor=Color.parseColor("#A020F0");//终止颜色
                ValueAnimator colorAnimator=ValueAnimator.ofArgb(startcolor,endcolor);
                colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int color= (int) animation.getAnimatedValue();
                        mTextView_answer.setTextColor(color);
                    }
                });
                AnimatorSet Set=new AnimatorSet();
                Set.playTogether(moneyAnimator,colorAnimator);
                Set.setDuration(3000);
                Set.start();

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                //动画取消时

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                //动画重复时  TODO表示提醒

            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        mImageView.setImageResource(R.drawable.animation_frame);//将 帧动画文件 设置到 图片资源中
//        AnimationDrawable animationDrawable= (AnimationDrawable) mImageView.getDrawable();//将 图片资源 设置到 逐帧动画对象中
//        animationDrawable.start();
    }
}
