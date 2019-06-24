package com.design.teaching_assistant.student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.design.teaching_assistant.R;
import com.design.teaching_assistant.relevantClass.Vote;
import com.design.teaching_assistant.utils.CCallBack;

import java.util.Date;

public class StudentClassVoteActivity extends AppCompatActivity {
    RadioGroup radiogroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_class_vote);
        Intent intent = getIntent();
        String str2= intent.getStringExtra("data3");    //学生学号
        String str = intent.getStringExtra("data");    //课程名
        String str1 = intent.getStringExtra("data2");//授课老师
    }

    //onClick事件对应的方法必须是public
    public void submitResult(View v){
        Intent intent = getIntent();
        String str2= intent.getStringExtra("data3");    //学生学号
        String str = intent.getStringExtra("data");    //课程名
        String str1 = intent.getStringExtra("data2");//授课老师
        radiogroup=(RadioGroup)findViewById(R.id.radioGroup);
                String result;
                if(radiogroup.getCheckedRadioButtonId()==R.id.radio_Yes){
                    result = "懂了";
                } else if(radiogroup.getCheckedRadioButtonId()==R.id.radio_Inter){
                    result = "一般懂";
                }else {
                    result = "不懂";
                }
                showToast("您选择的掌握情况是:"+result);
                Date current2Date = new Date(System.currentTimeMillis());//获取当前时间
                long currentTime = System.currentTimeMillis();
                long closeTime = currentTime + 15 * 60 * 1000;         //定时15分钟
                Date deadline = new Date(closeTime);

                Vote.submittingVoteResult(str, str2,str1, result, current2Date, deadline,new CCallBack() {
                    @Override
                    public void onSuccess(Object o) {
                        showToast("投票提交成功！");
                        finish();
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(StudentClassVoteActivity.this, msg, Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    public void back(View v){
        finish();
    }
}
