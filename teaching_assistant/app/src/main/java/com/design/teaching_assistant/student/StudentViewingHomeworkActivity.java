package com.design.teaching_assistant.student;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.design.teaching_assistant.R;
import com.design.teaching_assistant.relevantClass.Homework;
import com.design.teaching_assistant.relevantClass.HomeworkRecord;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class StudentViewingHomeworkActivity extends AppCompatActivity {
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;
    private Button b6;
    private Button b7;
    private Button b8;
    private TextView t1;
    private TextView t2;
    private TextView t3;
    private TextView t4;
    private TextView t5;
    private TextView t6;
    private TextView t7;
    private TextView t8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_viewing_homework);
        Intent intent= getIntent();
        //获取传递的值
        String str2=intent.getStringExtra("data3");    //学生学号
        String str = intent.getStringExtra("data");    //课程名
        String str1 = intent.getStringExtra("data2");//授课老师
        initView();
        query2DataFromHomeworkRecord(str,str1,str2);
        query1DataFromHomework(str,str1);

    }
    private void initView(){
        b1=(Button)findViewById(R.id.button10);  //学生点击提交回答
        b2=(Button)findViewById(R.id.button11);
        b3=(Button)findViewById(R.id.button12);
        b4=(Button)findViewById(R.id.button13);
        b5=(Button)findViewById(R.id.button14);
        b6=(Button)findViewById(R.id.button15);
        b7=(Button)findViewById(R.id.button16);
        b8=(Button)findViewById(R.id.button17);
        t1=(TextView)findViewById(R.id.b1);       //表示学生提交作业回答的相应状态
        t2=(TextView)findViewById(R.id.b2);
        t3=(TextView)findViewById(R.id.b3);
        t4=(TextView)findViewById(R.id.b4);
        t5=(TextView)findViewById(R.id.b5);
        t6=(TextView)findViewById(R.id.b6);
        t7=(TextView)findViewById(R.id.b7);
        t8=(TextView)findViewById(R.id.b8);

    }

    //本函数用于变更作业回答状态
    private void query2DataFromHomeworkRecord(String course14Name,String teacher14Name,String student14Num){
        //查找User表里userId为账号的数据
        BmobQuery<HomeworkRecord> bmobQuery = new BmobQuery<HomeworkRecord>();
        if (course14Name != null&&teacher14Name != null&&student14Num !=null) {
            bmobQuery.addWhereEqualTo("course14Name", course14Name);
            bmobQuery.addWhereEqualTo("teacher14Name",teacher14Name);
            bmobQuery.addWhereEqualTo("student14Num",student14Num);
        }
        bmobQuery.findObjects(new FindListener<HomeworkRecord>() {
            @Override
            public void done(final List<HomeworkRecord> list, BmobException e) {

                if (e == null) {
                    if(list.size()==0){

                    }else{
                        switch (list.size()){
                            case 1:
                                t1.setText("已提交");
                                break;
                            case 2:
                                t1.setText("已提交");
                                t2.setText("已提交");
                                break;
                            case 3:
                                t1.setText("已提交");
                                t2.setText("已提交");
                                t3.setText("已提交");
                                break;
                            case 4:
                                t1.setText("已提交");
                                t2.setText("已提交");
                                t3.setText("已提交");
                                t4.setText("已提交");
                                break;
                            case 5:
                                t1.setText("已提交");
                                t2.setText("已提交");
                                t3.setText("已提交");
                                t4.setText("已提交");
                                t5.setText("已提交");
                                break;
                            case 6:
                                t1.setText("已提交");
                                t2.setText("已提交");
                                t3.setText("已提交");
                                t4.setText("已提交");
                                t5.setText("已提交");
                                t6.setText("已提交");
                                break;
                            case 7:
                                t1.setText("已提交");
                                t2.setText("已提交");
                                t3.setText("已提交");
                                t4.setText("已提交");
                                t5.setText("已提交");
                                t6.setText("已提交");
                                t7.setText("已提交");
                                break;
                            case 8:
                                t1.setText("已提交");
                                t2.setText("已提交");
                                t3.setText("已提交");
                                t4.setText("已提交");
                                t5.setText("已提交");
                                t6.setText("已提交");
                                t7.setText("已提交");
                                t8.setText("已提交");
                                break;
                        }
                    }

                } else {
                    Toast.makeText(StudentViewingHomeworkActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void query1DataFromHomework(String course13Name,String teacher13Name){
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
                    if(list.size()==0) {
                        Toast.makeText(StudentViewingHomeworkActivity.this, "作业还没有布置，请等待！", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(StudentViewingHomeworkActivity.this, "前"+list.size()+"次作业已经布置了！", Toast.LENGTH_SHORT).show();
                        b1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent= getIntent();
                                //获取传递的值
                                String str2=intent.getStringExtra("data3");    //学生学号
                                String str = intent.getStringExtra("data");    //课程名
                                String str1 = intent.getStringExtra("data2");//授课老师
                                String  homeworkId = list.get(0).getHomeworkId(); //作业号
                                String  topic = list.get(0).getContent();       //老师上传的作业题目描述
                                Intent it = new Intent(StudentViewingHomeworkActivity.this, StudentHomeworkUploadActivity.class);
                                it.putExtra("topic ",topic);       //传递作业题目
                                it.putExtra("homeworkId", homeworkId); //传递作业号
                                it.putExtra("data3",str2);
                                it.putExtra("data",str);
                                it.putExtra("data2",str1);
                                startActivityForResult(it, 1);
                            }
                        });
                        b2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(list.size()>=2) {
                                    Intent intent= getIntent();
                                    //获取传递的值
                                    String str2=intent.getStringExtra("data3");    //学生学号
                                    String str = intent.getStringExtra("data");    //课程名
                                    String str1 = intent.getStringExtra("data2");//授课老师
                                    String homeworkId = list.get(1).getHomeworkId();
                                    String  topic = list.get(1).getContent();       //老师上传的作业题目描述
                                    Intent it = new Intent(StudentViewingHomeworkActivity.this, StudentHomeworkUploadActivity.class);
                                    it.putExtra("topic ",topic);       //传递作业题目
                                    it.putExtra("homeworkId", homeworkId);
                                    it.putExtra("data3",str2);
                                    it.putExtra("data",str);
                                    it.putExtra("data2",str1);
                                    startActivityForResult(it, 2);
                                }
                                else
                                    Toast.makeText(StudentViewingHomeworkActivity.this, "还未布置第二次作业", Toast.LENGTH_SHORT).show();
                            }
                        });
                        b3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(list.size()>=3) {
                                    Intent intent= getIntent();
                                    //获取传递的值
                                    String str2=intent.getStringExtra("data3");    //学生学号
                                    String str = intent.getStringExtra("data");    //课程名
                                    String str1 = intent.getStringExtra("data2");//授课老师
                                    String homeworkId = list.get(2).getHomeworkId();
                                    String  topic = list.get(2).getContent();       //老师上传的作业题目描述
                                    Intent it = new Intent(StudentViewingHomeworkActivity.this, StudentHomeworkUploadActivity.class);
                                    it.putExtra("topic ",topic);       //传递作业题目
                                    it.putExtra("homeworkId", homeworkId);
                                    it.putExtra("data3",str2);
                                    it.putExtra("data",str);
                                    it.putExtra("data2",str1);
                                    startActivityForResult(it, 3);
                                }
                                else
                                    Toast.makeText(StudentViewingHomeworkActivity.this, "还未布置第三次作业", Toast.LENGTH_SHORT).show();
                            }
                        });
                        b4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(list.size()>=4) {
                                    Intent intent= getIntent();
                                    //获取传递的值
                                    String str2=intent.getStringExtra("data3");    //学生学号
                                    String str = intent.getStringExtra("data");    //课程名
                                    String str1 = intent.getStringExtra("data2");//授课老师
                                    String homeworkId = list.get(3).getHomeworkId();
                                    String  topic = list.get(3).getContent();       //老师上传的作业题目描述
                                    Intent it = new Intent(StudentViewingHomeworkActivity.this, StudentHomeworkUploadActivity.class);
                                    it.putExtra("topic ",topic);       //传递作业题目
                                    it.putExtra("homeworkId", homeworkId);
                                    it.putExtra("data3",str2);
                                    it.putExtra("data",str);
                                    it.putExtra("data2",str1);
                                    startActivityForResult(it, 4);
                                }
                                else
                                    Toast.makeText(StudentViewingHomeworkActivity.this, "还未布置第四次作业", Toast.LENGTH_SHORT).show();
                            }
                        });

                        b5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(list.size()>=5) {
                                    Intent intent= getIntent();
                                    //获取传递的值
                                    String str2=intent.getStringExtra("data3");    //学生学号
                                    String str = intent.getStringExtra("data");    //课程名
                                    String str1 = intent.getStringExtra("data2");//授课老师
                                    String homeworkId =list.get(4).getHomeworkId();
                                    String  topic = list.get(4).getContent();       //老师上传的作业题目描述
                                    Intent it = new Intent(StudentViewingHomeworkActivity.this, StudentHomeworkUploadActivity.class);
                                    it.putExtra("topic ",topic);       //传递作业题目
                                    it.putExtra("homeworkId", homeworkId);
                                    it.putExtra("data3",str2);
                                    it.putExtra("data",str);
                                    it.putExtra("data2",str1);
                                    startActivityForResult(it, 5);
                                }
                                else
                                    Toast.makeText(StudentViewingHomeworkActivity.this, "还未布置第五次作业", Toast.LENGTH_SHORT).show();
                            }
                        });

                        b6.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(list.size()>=6) {
                                    Intent intent= getIntent();
                                    //获取传递的值
                                    String str2=intent.getStringExtra("data3");    //学生学号
                                    String str = intent.getStringExtra("data");    //课程名
                                    String str1 = intent.getStringExtra("data2");//授课老师
                                    String homeworkId = list.get(5).getHomeworkId();
                                    String  topic = list.get(5).getContent();       //老师上传的作业题目描述
                                    Intent it = new Intent(StudentViewingHomeworkActivity.this, StudentHomeworkUploadActivity.class);
                                    it.putExtra("topic ",topic);       //传递作业题目
                                    it.putExtra("homeworkId", homeworkId);
                                    it.putExtra("data3",str2);
                                    it.putExtra("data",str);
                                    it.putExtra("data2",str1);
                                    startActivityForResult(it, 6);
                                }
                                else
                                    Toast.makeText(StudentViewingHomeworkActivity.this, "还未布置第六次作业", Toast.LENGTH_SHORT).show();
                            }
                        });

                        b7.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(list.size()>=7) {
                                    Intent intent= getIntent();
                                    //获取传递的值
                                    String str2=intent.getStringExtra("data3");    //学生学号
                                    String str = intent.getStringExtra("data");    //课程名
                                    String str1 = intent.getStringExtra("data2");//授课老师
                                    String homeworkId = list.get(6).getHomeworkId();
                                    String  topic = list.get(6).getContent();      //老师上传的作业题目描述
                                    Intent it = new Intent(StudentViewingHomeworkActivity.this, StudentHomeworkUploadActivity.class);
                                    it.putExtra("topic ",topic);       //传递作业题目
                                    it.putExtra("homeworkId", homeworkId);
                                    it.putExtra("data3",str2);
                                    it.putExtra("data",str);
                                    it.putExtra("data2",str1);
                                    startActivityForResult(it, 7);
                                }
                                else
                                    Toast.makeText(StudentViewingHomeworkActivity.this, "还未布置第七次作业", Toast.LENGTH_SHORT).show();
                            }
                        });

                        b8.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(list.size()==8) {
                                    Intent intent= getIntent();
                                    //获取传递的值
                                    String str2=intent.getStringExtra("data3");    //学生学号
                                    String str = intent.getStringExtra("data");    //课程名
                                    String str1 = intent.getStringExtra("data2");//授课老师
                                    String homeworkId = list.get(7).getHomeworkId();
                                    String  topic = list.get(7).getContent();       //老师上传的作业题目描述
                                    Intent it = new Intent(StudentViewingHomeworkActivity.this, StudentHomeworkUploadActivity.class);
                                    it.putExtra("topic ",topic);       //传递作业题目
                                    it.putExtra("homeworkId", homeworkId);
                                    it.putExtra("data3",str2);
                                    it.putExtra("data",str);
                                    it.putExtra("data2",str1);
                                    startActivityForResult(it, 8);
                                }
                                else
                                    Toast.makeText(StudentViewingHomeworkActivity.this, "还未布置第八次作业", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(StudentViewingHomeworkActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode== Activity.RESULT_OK) {
            Toast.makeText(StudentViewingHomeworkActivity.this, "上传成功！", Toast.LENGTH_SHORT).show();
            if (requestCode == 1) {
                t1.setText("已提交");
            }
            if (requestCode == 2) {
                t2.setText("已提交");
            }
            if (requestCode == 3) {
                t3.setText("已提交");
            }
            if (requestCode == 4) {
                t4.setText("已提交");
            }
            if (requestCode == 5) {
                t5.setText("已提交");
            }
            if (requestCode == 6) {
                t6.setText("已提交");
            }
            if (requestCode == 7) {
                t7.setText("已提交");
            }
            if (requestCode == 8) {
                t8.setText("已提交");
            }
        }
    }
    public void back(View v){finish();}
}
