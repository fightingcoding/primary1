package com.design.teaching_assistant.student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.design.teaching_assistant.R;

public class StudentClassCommunication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_class_communication);
        Intent intent= getIntent();
        //获取传递的值
        String str = intent.getStringExtra("data");    //课程名
        String str1 = intent.getStringExtra("data2");//授课老师
    }

    public void vote(View v){
        Intent intent = getIntent();
        String str2= intent.getStringExtra("data3");    //学生学号
        String str = intent.getStringExtra("data");    //课程名
        String str1 = intent.getStringExtra("data2");//授课老师
        Intent it = new Intent(StudentClassCommunication.this,StudentClassVoteActivity.class);
        it.putExtra("data3",str2);                    //学生学号
        it.putExtra("data",str);                     //课程名
        it.putExtra("data2",str1);                  //授课老师
        startActivity(it);
    }
    public void test(View v){
        Intent intent= getIntent();
        //获取传递的值
        String str2= intent.getStringExtra("data3");    //学生学号
        String str = intent.getStringExtra("data");    //课程名
        String str1 = intent.getStringExtra("data2");//授课老师
        Intent it = new Intent(StudentClassCommunication.this,StudentClassTestActivity.class);
        it.putExtra("data",str);
        it.putExtra("data2",str1);
        it.putExtra("data3",str2);            //增加传递学生学号
        startActivity(it);
    }

    public void back(View v){
        finish();
    }
}
