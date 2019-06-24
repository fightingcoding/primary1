package com.design.teaching_assistant.teacher;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.design.teaching_assistant.R;
import com.design.teaching_assistant.relevantClass.CourseComment;
import com.design.teaching_assistant.relevantClass.Notice;
import com.design.teaching_assistant.student.Student1MainActivity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class TeacherCommentShowActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_comment_show);
        textView = findViewById(R.id.score);
        Intent intent =getIntent();
        //获取传递的值
        String str = intent.getStringExtra("data"); //课程名
        String str1 = intent.getStringExtra("data1");//授课老师
        queryDataFromCourseComment(str,str1);
    }
    private void queryDataFromCourseComment(String course7Name,String teacher7Name){
        //查找User表里userId为账号的数据
        BmobQuery<CourseComment> bmobQuery = new BmobQuery<CourseComment>();
        if (course7Name != null&&teacher7Name != null) {
            bmobQuery.addWhereEqualTo("course7Name", course7Name);
            bmobQuery.addWhereEqualTo("teacher7Name",teacher7Name);
        }
        bmobQuery.findObjects(new FindListener<CourseComment>() {
            @Override
            public void done(final List<CourseComment> list, BmobException e) {

                if (e == null) {
                    if(list.size()==0){
                      Toast.makeText(TeacherCommentShowActivity.this,"截止目前还没有人提交评教结果呢！",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(TeacherCommentShowActivity.this,"目前共有"+list.size()+"个人提交评教结果！",Toast.LENGTH_SHORT).show();
                        Float totalScore = 0F;
                        for(int i=0;i<list.size();i++){
                            totalScore +=list.get(i).getCommentScore();
                        }
                        String score1= "您本课程评教平均得分:"+String.valueOf(totalScore/list.size());
                        textView.setText(score1);
                    }
                } else {
                    Toast.makeText(TeacherCommentShowActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void back(View v){
        finish();
    }
}
