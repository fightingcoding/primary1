package com.design.teaching_assistant.teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.design.teaching_assistant.R;
import com.design.teaching_assistant.relevantClass.Homework;
import com.design.teaching_assistant.utils.CCallBack;

import java.util.Date;

public class TeacherUploadHomeworkActivity extends AppCompatActivity {
    private EditText t1;
    private EditText t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_upload_homework);
        Intent intent =getIntent();
        //获取传递的值
        String str = intent.getStringExtra("data"); //课程名
        String str1 = intent.getStringExtra("data1");//授课老师
        t1=findViewById(R.id.homeworkNumber);
        t2=findViewById(R.id.homeworkDescription);
    }
    //老师上传作业题目响应
    public void submit(View v){
        Intent intent =getIntent();
        //获取传递的值
        String str = intent.getStringExtra("data"); //课程名
        String str1 = intent.getStringExtra("data1");//授课老师
        String homeworkId = t1.getText().toString().trim();      //获得作业号
        String homeworkContent = t2.getText().toString().trim(); //获得作业内容描述
        Date currentTime = new Date(System.currentTimeMillis()); //作业的建立（提交）时间
        Homework.uploadHomework(str, str1, homeworkId, homeworkContent, currentTime, new CCallBack() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(TeacherUploadHomeworkActivity.this,"作业题目上传成功！",Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(TeacherUploadHomeworkActivity.this,msg,Toast.LENGTH_SHORT).show();

            }
        });

    }
    public void back(View v){
        finish();
    }
}
