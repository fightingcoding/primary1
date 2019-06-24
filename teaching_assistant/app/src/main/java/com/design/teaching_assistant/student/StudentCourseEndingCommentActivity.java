package com.design.teaching_assistant.student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.design.teaching_assistant.R;
import com.design.teaching_assistant.relevantClass.CourseComment;
import com.design.teaching_assistant.utils.CCallBack;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class StudentCourseEndingCommentActivity extends AppCompatActivity {
    private RadioGroup Q1;
    private RadioGroup Q2;
    private RadioGroup Q3;
    private RadioGroup Q4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course_ending_comment);
        Intent intent= getIntent();
        //获取传递的值
        String str2=intent.getStringExtra("data3");    //学生学号
        String str = intent.getStringExtra("data");    //课程名
        String str1 = intent.getStringExtra("data2");//授课老师
        Q1=(RadioGroup)findViewById(R.id.radioGroup1);
        Q2=(RadioGroup)findViewById(R.id.radioGroup2);
        Q3=(RadioGroup)findViewById(R.id.radioGroup3);
        Q4=(RadioGroup)findViewById(R.id.radioGroup4);
    }
    //提交评教结果后的响应
    public void submitCommentResult(View v){
        Intent intent= getIntent();
        //获取传递的值
        String str2=intent.getStringExtra("data3");    //学生学号
        String str = intent.getStringExtra("data");    //课程名
        String str1 = intent.getStringExtra("data2");//授课老师
        Float score = 0F;
        if(Q1.getCheckedRadioButtonId()== R.id.radioButton3)
            score+=25;
        else if(Q1.getCheckedRadioButtonId()==R.id.radioButton6) {
            score+=15;
        }else {
            score+=0;
        }
        if(Q2.getCheckedRadioButtonId()== R.id.radioButton){
            score+=25;
        } else if(Q2.getCheckedRadioButtonId()== R.id.radioButton5){
            score+=15;
        } else {
            score+=0;
        }
        if(Q3.getCheckedRadioButtonId()== R.id.radioButton12){
            score+=25;
        } else if(Q3.getCheckedRadioButtonId()== R.id.radioButton14){
            score+=15;
        } else {
            score+=0;
        }
        if(Q4.getCheckedRadioButtonId()== R.id.radioButton9){
            score+=25;
        } else if(Q4.getCheckedRadioButtonId()== R.id.radioButton11){
            score+=15;
        } else {
            score+=0;
        }
        Toast.makeText(StudentCourseEndingCommentActivity.this, "评教总得分："+score+"(每题最高25分)", Toast.LENGTH_SHORT).show();
        CourseComment.submittingCommentResult(str,str1, str2, score, new CCallBack() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(StudentCourseEndingCommentActivity.this,"评教结果成功提交",Toast.LENGTH_SHORT).show();
                //跳转到评教成功界面
                Intent intent1 = new Intent(StudentCourseEndingCommentActivity.this, StudentCourseCommentSuccessActivity.class);
                startActivity(intent1);
            }
            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(StudentCourseEndingCommentActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }
    //这是另一种存数据到bmob的方法
    /*
    CourseComment commentRecord= new CourseComment(str,str2,score);
        commentRecord.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Toast.makeText(StudentCourseEndingCommentActivity.this,"结果提交成功！",Toast.LENGTH_SHORT).show();
                }else{
                    showToast(e.getMessage());
                }

            }
        });

*/
    public void back(View v){
        finish();
    }
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
