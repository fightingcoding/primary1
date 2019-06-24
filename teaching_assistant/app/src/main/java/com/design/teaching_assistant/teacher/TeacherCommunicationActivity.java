package com.design.teaching_assistant.teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.design.teaching_assistant.R;

public class TeacherCommunicationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_communication);
        //获取意图对象
        Intent intent1 = getIntent();
        //获取传递的值
        String str = intent1.getStringExtra("data");
        String str1 = intent1.getStringExtra("data1");//授课老师
    }

    public void  viewingAttendance(View v){
        //获取意图对象
        Intent intent1 = getIntent();
        //获取传递的值
        String str = intent1.getStringExtra("data"); //课程名
        String str1 = intent1.getStringExtra("data1");//授课老师
        Intent it = new Intent(TeacherCommunicationActivity.this,TeacherViewingAttendanceActivity.class);
        it.putExtra("data",str);                  //课程名
        it.putExtra("data1",str1);                //授课老师
        startActivity(it);
    }

    public void viewingVoteResult(View v){
        //获取意图对象
        Intent intent1 = getIntent();
        //获取传递的值
        String str = intent1.getStringExtra("data"); //课程名
        String str1 = intent1.getStringExtra("data1");//授课老师
        Intent intent = new Intent(TeacherCommunicationActivity.this, TeacherCheckVoteActivity.class);
        intent.putExtra("data",str);                //课程名
        intent.putExtra("data1",str1);              //授课老师姓名
        startActivity(intent);
    }
    public void createTest(View v){
        //获取意图对象
        Intent intent1=getIntent();
        //获取传递的值
        String str = intent1.getStringExtra("data");
        String str1 = intent1.getStringExtra("data1");//授课老师
        Intent intent = new Intent(TeacherCommunicationActivity.this, TeacherCreateTestActivity.class);
        intent.putExtra("data", str);
        intent.putExtra("data1",str1);
        startActivity(intent);
    }
    public void back(View v){
        finish();
    }
}
