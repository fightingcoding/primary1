package com.design.teaching_assistant.student;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.design.teaching_assistant.R;
import com.design.teaching_assistant.relevantClass.SignRecord;

import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class StudentAttendanceActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);
        textView = findViewById(R.id.signText);
        Intent intent= getIntent();
        //获取传递的值
        String str2=intent.getStringExtra("data3");    //学生学号
        String str = intent.getStringExtra("data");    //课程名
        String str1 = intent.getStringExtra("data2");//授课老师
    }
    public void beginSign(View v){     //学生进行一键签到
        Intent intent= getIntent();
        //获取传递的值
        String str2=intent.getStringExtra("data3");    //学生学号
        String str = intent.getStringExtra("data");    //课程名
        String str1 = intent.getStringExtra("data2");//授课老师
        queryAndAgainAlterDataFromSignRecord(str,str2,str1);
    }
    //查询签到记录表里的数据，并在教师提示可以进行签到时对签到状态进行修改
    private void queryAndAgainAlterDataFromSignRecord(String course11Name,String student11Num,String teacher11Name ){
        //查询SignRecord表里课程名、老师姓名、学生学号分别为course11Name、teacher11Name、student11Num的数据
        BmobQuery<SignRecord> bmobQuery = new BmobQuery<SignRecord>();
        if (course11Name != null&&teacher11Name != null&&student11Num != null) {
            bmobQuery.addWhereEqualTo("course9Name",course11Name);
            bmobQuery.addWhereEqualTo("teacher9Name",teacher11Name);
            bmobQuery.addWhereEqualTo("student9Num",student11Num);
        }
        bmobQuery.findObjects(new FindListener<SignRecord>() {
            @Override
            public void done(final List<SignRecord> list, BmobException e) {

                if (e == null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if(list.size()==0){
                                Toast.makeText(StudentAttendanceActivity.this,"当前没有签到！",Toast.LENGTH_SHORT).show();
                            }else{
                                String idStr = list.get(0).getObjectId(); //获取objectId以便于更新数据
                                //更新SignRecord表里面id为idStr的数据，将isSign内容更新为False
                                list.get(0).setIsSign(Boolean.FALSE);
                                list.get(0).setSignTime(new Date(System.currentTimeMillis()));
                                list.get(0).update(new UpdateListener(){

                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            textView.setText("签到成功！");

                                        }else{
                                            Toast.makeText(StudentAttendanceActivity.this,"签到失败："+e.getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    }).start();

                    Toast.makeText(StudentAttendanceActivity.this,"查询到要使用的记录并更改状态成功！",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(StudentAttendanceActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void back(View v){finish();}
}
