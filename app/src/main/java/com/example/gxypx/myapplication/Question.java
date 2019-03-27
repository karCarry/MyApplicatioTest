package com.example.gxypx.myapplication;

public class Question{
    private int mQuestion_id;
    private  boolean mAnswer;

    public Question(int question_id, boolean answer) {
        mQuestion_id = question_id;
        mAnswer = answer;
    }

    public int getQuestion_id() {
        return mQuestion_id;
    }

    public void setQuestion_id(int question_id) {
        mQuestion_id = question_id;
    }

    public boolean isAnswer() {
        return mAnswer;
    }

    public void setAnswer(boolean answer) {
        mAnswer = answer;
    }
}
