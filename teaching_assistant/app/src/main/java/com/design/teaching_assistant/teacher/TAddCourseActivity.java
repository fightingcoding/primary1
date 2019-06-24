package com.design.teaching_assistant.teacher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.design.teaching_assistant.R;
import com.design.teaching_assistant.relevantClass.Course;
import com.design.teaching_assistant.utils.CCallBack;

public class TAddCourseActivity extends AppCompatActivity {
    private EditText t2;
    private EditText t3;
    private EditText t4;
    private EditText t5;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tadd_course);
        initView();
    }
    private void initView(){

        t2=findViewById(R.id.cTeach);
        t3=findViewById(R.id.cScore);
        t4=findViewById(R.id.cName);
        t5=findViewById(R.id.cNum);
        confirm=findViewById(R.id.confirm);
    }
    public void addConfirm(View v){
        String cTeacherName=t2.getText().toString().trim();
        String credit=t3.getText().toString().trim();
        String cName=t4.getText().toString().trim();
        String num=t5.getText().toString().trim();
        Course.addCourse(num,cName,credit,cTeacherName,new CCallBack(){
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(TAddCourseActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(TAddCourseActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
