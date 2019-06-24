package com.design.teaching_assistant.student;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.design.teaching_assistant.R;

public class StudentCourseCommentSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course_comment_success);
    }
    public void back(View v){
        finish();
    }
}
