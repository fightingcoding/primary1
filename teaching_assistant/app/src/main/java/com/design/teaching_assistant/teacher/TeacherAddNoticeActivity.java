package com.design.teaching_assistant.teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.design.teaching_assistant.R;
import com.design.teaching_assistant.application.MyApplication;
import com.design.teaching_assistant.relevantClass.Notice;
import com.design.teaching_assistant.utils.CCallBack;

import java.util.Date;

public class TeacherAddNoticeActivity extends AppCompatActivity {
    private EditText t1;
    MyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_add_notice);
    }
    public void publishNotice(View v){
        //获取意图对象
        Intent intent = getIntent();
        //获取传递的值
        String str = intent.getStringExtra("data");  //课程号
        String str1 = intent.getStringExtra("data1");//老师姓名
        t1=(EditText) findViewById(R.id.inputNotice);
        String  content1=t1.getText().toString().trim();
        Date currentDate = new Date(System.currentTimeMillis());//获取当前时间
        long currentTime=System.currentTimeMillis();
        Notice.publishingNotice(content1,currentDate,str,str1,new CCallBack() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(TeacherAddNoticeActivity.this, "发布成功!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(TeacherAddNoticeActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void back(View v){
        finish();
    }
}
