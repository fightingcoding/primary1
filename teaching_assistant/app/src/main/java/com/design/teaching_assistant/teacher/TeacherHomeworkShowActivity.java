package com.design.teaching_assistant.teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import com.design.teaching_assistant.R;
import com.design.teaching_assistant.relevantClass.Homework;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class TeacherHomeworkShowActivity extends AppCompatActivity {
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;
    private Button b6;
    private Button b7;
    private Button b8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_homework_show);
        Intent intent =getIntent();
        //获取传递的值
        String str = intent.getStringExtra("data"); //课程名
        String str1 = intent.getStringExtra("data1");//授课老师
        initView();
        queryDataFromHomework(str,str1);
    }

    private void initView(){
        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        b4=(Button)findViewById(R.id.button4);
        b5=(Button)findViewById(R.id.button5);
        b6=(Button)findViewById(R.id.button6);
        b7=(Button)findViewById(R.id.button7);
        b8=(Button)findViewById(R.id.button8);
    }

    private void queryDataFromHomework(String course13Name,String teacher13Name){
        //查找Homework表里课程名、教师姓名分别为course13Name和teacher13Name的数据
        BmobQuery<Homework> bmobQuery = new BmobQuery<Homework>();
        if (course13Name != null&&teacher13Name != null) {
            bmobQuery.addWhereEqualTo("course13Name", course13Name);   //通过课程名和老师姓名来
            bmobQuery.addWhereEqualTo("teacher13Name",teacher13Name);  //来查询相应作业
        }
        bmobQuery.findObjects(new FindListener<Homework>() {
            @Override
            public void done(final List<Homework> list, BmobException e) {

                if (e == null) {
                           if(list.size()==0){
                               Toast.makeText(TeacherHomeworkShowActivity.this,"没有检索到作业，作业还没有布置了！",Toast.LENGTH_SHORT).show();
                           }else{
                               Toast.makeText(TeacherHomeworkShowActivity.this,"前"+list.size()+"次作业已经布置，快点击查看吧！",Toast.LENGTH_SHORT).show();
                               //点击作业监听
                               b1.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       Intent intent =getIntent();
                                       //获取传递的值
                                       String str = intent.getStringExtra("data"); //课程名
                                       String str1 = intent.getStringExtra("data1");//授课老师
                                       Intent it = new Intent(TeacherHomeworkShowActivity.this, TeacherCheckHomeworkConditionActivity.class);
                                       it.putExtra("homeworkId", list.get(0).getHomeworkId());
                                       it.putExtra("data",str);
                                       it.putExtra("data1",str1);
                                       startActivity(it);
                                   }
                               });
                               b2.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       if(list.size()>=2){
                                           Intent intent =getIntent();
                                           //获取传递的值
                                           String str = intent.getStringExtra("data"); //课程名
                                           String str1 = intent.getStringExtra("data1");//授课老师
                                           Intent it = new Intent(TeacherHomeworkShowActivity.this, TeacherCheckHomeworkConditionActivity.class);
                                           it.putExtra("homeworkId", list.get(1).getHomeworkId());
                                           it.putExtra("data",str);
                                           it.putExtra("data1",str1);
                                           startActivity(it);
                                       }else
                                           Toast.makeText(TeacherHomeworkShowActivity.this,"第二次作业还未布置！",Toast.LENGTH_SHORT).show();

                                   }
                               });
                               b3.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       if(list.size()>=3){
                                           Intent intent =getIntent();
                                           //获取传递的值
                                           String str = intent.getStringExtra("data"); //课程名
                                           String str1 = intent.getStringExtra("data1");//授课老师
                                           Intent it = new Intent(TeacherHomeworkShowActivity.this, TeacherCheckHomeworkConditionActivity.class);
                                           it.putExtra("homeworkId", list.get(2).getHomeworkId());
                                           it.putExtra("data",str);
                                           it.putExtra("data1",str1);
                                           startActivity(it);
                                       }else
                                           Toast.makeText(TeacherHomeworkShowActivity.this,"第三次作业还未布置！",Toast.LENGTH_SHORT).show();

                                   }
                               });
                               b4.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       if(list.size()>=4) {
                                           Intent intent =getIntent();
                                           //获取传递的值
                                           String str = intent.getStringExtra("data"); //课程名
                                           String str1 = intent.getStringExtra("data1");//授课老师
                                           Intent it = new Intent(TeacherHomeworkShowActivity.this, TeacherCheckHomeworkConditionActivity.class);
                                           it.putExtra("homeworkId", list.get(3).getHomeworkId());
                                           it.putExtra("data",str);
                                           it.putExtra("data1",str1);
                                           startActivity(it);
                                       }else
                                           Toast.makeText(TeacherHomeworkShowActivity.this,"第四次作业还未布置！",Toast.LENGTH_SHORT).show();
                                   }
                               });
                               b5.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       if(list.size()>=5) {
                                           Intent intent =getIntent();
                                           //获取传递的值
                                           String str = intent.getStringExtra("data"); //课程名
                                           String str1 = intent.getStringExtra("data1");//授课老师
                                           Intent it = new Intent(TeacherHomeworkShowActivity.this, TeacherCheckHomeworkConditionActivity.class);
                                           it.putExtra("homeworkId", list.get(4).getHomeworkId());
                                           it.putExtra("data",str);
                                           it.putExtra("data1",str1);
                                           startActivity(it);
                                       }else
                                           Toast.makeText(TeacherHomeworkShowActivity.this,"第五次作业还未布置！",Toast.LENGTH_SHORT).show();
                                   }
                               });
                               b6.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       if(list.size()>=6) {
                                           Intent intent =getIntent();
                                           //获取传递的值
                                           String str = intent.getStringExtra("data"); //课程名
                                           String str1 = intent.getStringExtra("data1");//授课老师
                                           Intent it = new Intent(TeacherHomeworkShowActivity.this, TeacherCheckHomeworkConditionActivity.class);
                                           it.putExtra("homeworkId", list.get(5).getHomeworkId());
                                           it.putExtra("data",str);
                                           it.putExtra("data1",str1);
                                           startActivity(it);
                                       }else
                                           Toast.makeText(TeacherHomeworkShowActivity.this,"第六次作业还未布置！",Toast.LENGTH_SHORT).show();
                                   }
                               });
                               b7.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       if(list.size()>=7) {
                                           Intent intent =getIntent();
                                           //获取传递的值
                                           String str = intent.getStringExtra("data"); //课程名
                                           String str1 = intent.getStringExtra("data1");//授课老师
                                           Intent it = new Intent(TeacherHomeworkShowActivity.this, TeacherCheckHomeworkConditionActivity.class);
                                           it.putExtra("homeworkId", list.get(6).getHomeworkId());
                                           it.putExtra("data",str);
                                           it.putExtra("data1",str1);
                                           startActivity(it);
                                       }else
                                           Toast.makeText(TeacherHomeworkShowActivity.this,"第七次作业还未布置！",Toast.LENGTH_SHORT).show();
                                   }
                               });
                               b8.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       if(list.size()==8){
                                           Intent intent =getIntent();
                                           //获取传递的值
                                           String str = intent.getStringExtra("data"); //课程名
                                           String str1 = intent.getStringExtra("data1");//授课老师
                                           Intent it = new Intent(TeacherHomeworkShowActivity.this, TeacherCheckHomeworkConditionActivity.class);
                                           it.putExtra("homeworkId", list.get(7).getHomeworkId());
                                           it.putExtra("data",str);
                                           it.putExtra("data1",str1);
                                           startActivity(it);
                                       }else
                                           Toast.makeText(TeacherHomeworkShowActivity.this,"第八次作业还未布置！",Toast.LENGTH_SHORT).show();
                                   }
                               });
                           }

                } else {
                    Toast.makeText(TeacherHomeworkShowActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void submitHomework(View v){
        Intent intent =getIntent();
        //获取传递的值
        String str = intent.getStringExtra("data"); //课程名
        String str1 = intent.getStringExtra("data1");//授课老师
        Intent it=new Intent(TeacherHomeworkShowActivity.this,TeacherUploadHomeworkActivity.class);
        it.putExtra("data",str);                  //课程名
        it.putExtra("data1",str1);               //授课老师
        startActivity(it);

    }
    public void back(View v){
        finish();
    }
}
