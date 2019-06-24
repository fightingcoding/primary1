package com.design.teaching_assistant.teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.design.teaching_assistant.R;
import com.design.teaching_assistant.relevantClass.SignRecord;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class TeacherCreateAttendanceConfirm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_create_attendance_confirm);
        //获取意图对象
        Intent intent = getIntent();
        //获取传递的值
        String str = intent.getStringExtra("data"); //课程名
        String str1 = intent.getStringExtra("data1");//授课老师
    }
    public void confirm(View v){
        //获取意图对象
        Intent intent = getIntent();
        //获取传递的值
        String str = intent.getStringExtra("data"); //课程名
        String str1 = intent.getStringExtra("data1");//授课老师
        queryAndAlterDataFromSignRecord(str,str1);
        Intent it = new Intent(TeacherCreateAttendanceConfirm.this,TeacherViewingAttendanceActivity.class);
        it.putExtra("data",str);      //课程名
        it.putExtra("data1",str1);    //授课老师
        startActivity(it);



    }

    //查询签到记录表里的数据，并在发起签到时对签到状态进行修改
    private void queryAndAlterDataFromSignRecord(String course10Name,String teacher10Name){
        //查询SignRecord表里课程名、老师姓名分别为course10Name、teacher10Name的数据
        BmobQuery<SignRecord> bmobQuery = new BmobQuery<SignRecord>();
        if (course10Name != null&&teacher10Name != null) {
            bmobQuery.addWhereEqualTo("course9Name",course10Name);
            bmobQuery.addWhereEqualTo("teacher9Name",teacher10Name);
        }
        bmobQuery.findObjects(new FindListener<SignRecord>() {
            @Override
            public void done(final List<SignRecord> list, BmobException e) {

                if (e == null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for(int j=0;j<list.size();j++)
                            {
                                String idStr = list.get(j).getObjectId(); //获取objectId以便于更新数据
                                //更新SignRecord表里面id为idStr的数据，isSign内容更新为True
                                list.get(j).setIsSign(Boolean.TRUE);
                                list.get(j).update(new UpdateListener(){

                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                           Toast.makeText(TeacherCreateAttendanceConfirm.this,"可以进行签到了！",Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(TeacherCreateAttendanceConfirm.this,"失败："+e.getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    }).start();

                    Toast.makeText(TeacherCreateAttendanceConfirm.this,"查询到要使用的记录并且操作成功！",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TeacherCreateAttendanceConfirm.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void cancel(View v){
        Toast.makeText(this, "您取消了创建签到的操作！", Toast.LENGTH_SHORT).show();
        finish();
    }
}
