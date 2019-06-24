package com.design.teaching_assistant.student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.design.teaching_assistant.R;

public class StudentClassTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_class_test);
    }
    public void classroomTest(View v){
        Intent intent= getIntent();
        //获取传递的值
        String str2 = intent.getStringExtra("data3");   //学生学号
        String str = intent.getStringExtra("data");    //课程名
        String str1 = intent.getStringExtra("data2");//授课老师
        Intent it = new Intent(StudentClassTestActivity.this, StudentNewClassTestActivity.class);
        it.putExtra("data",str);
        it.putExtra("data2",str1);
        it.putExtra("data3",str2);               //增加传递学生学号
        startActivity(it);
    }
    public void preTest(View v){
        Intent intent1= getIntent();
        //获取传递的值
        String str2 = intent1.getStringExtra("data3");   //学生学号
        String str = intent1.getStringExtra("data");    //课程名
        String str1 = intent1.getStringExtra("data2");//授课老师
        Intent intent = new Intent(StudentClassTestActivity.this,StudentPreviousClassTestActivity.class);
        intent.putExtra("data3",str2);          //学生学号
        intent.putExtra("data",str);            //课程名
        intent.putExtra("data2",str1);          //授课老师
        startActivity(intent);
    }
    public void back(View v){finish();}
}
