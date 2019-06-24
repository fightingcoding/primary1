package com.design.teaching_assistant.student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.design.teaching_assistant.R;
import com.design.teaching_assistant.relevantClass.Homework;
import com.design.teaching_assistant.relevantClass.HomeworkRecord;
import com.design.teaching_assistant.utils.CCallBack;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class StudentHomeworkUploadActivity extends AppCompatActivity {
    private ListView subject;
    private EditText t1;
    private List<Homework>dataList1 = new ArrayList<Homework>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_homework_upload);
        //获取意图对象
        Intent intent1=getIntent();
        //获取传递的值
        String topic = intent1.getStringExtra("topic");           //作业题目
        String homeworkId = intent1.getStringExtra("homeworkId");//作业号
        String str2=intent1.getStringExtra("data3");    //学生学号
        String str = intent1.getStringExtra("data");    //课程名
        String str1 = intent1.getStringExtra("data2");//授课老师
        subject =(ListView) findViewById(R.id.subjectList);
        t1=(EditText)findViewById(R.id.answer);
        subject.setAdapter(baseAdapter);
        query3DataFromHomework(str,str1,homeworkId);

    }
    //主要用于显示作业题目
    private void query3DataFromHomework(String course13Name,String teacher13Name,String homeworkId){
        //查找Homework表里课程名、老师姓名、作业号分别为course13Name、teacher13Name、homeworkId的数据
        BmobQuery<Homework> bmobQuery = new BmobQuery<Homework>();
        if (course13Name != null&&teacher13Name != null&&homeworkId != null) {
            bmobQuery.addWhereEqualTo("teacher13Name", teacher13Name);
            bmobQuery.addWhereEqualTo("course13Name",course13Name);
            bmobQuery.addWhereEqualTo("homeworkId",homeworkId);
        }
        bmobQuery.findObjects(new FindListener<Homework>() {
            @Override
            public void done(final List<Homework> list, BmobException e) {

                if (e == null) {
                    if(list.size()==0){
                        Toast.makeText(StudentHomeworkUploadActivity.this,"没有查询到作业！",Toast.LENGTH_SHORT).show();
                    }else{
                        for (int i = 0; i <list.size(); i++) {
                            dataList1.add(list.get(i));
                        }
                        baseAdapter.notifyDataSetChanged();

                    Toast.makeText(StudentHomeworkUploadActivity.this,"查询到该作业！",Toast.LENGTH_SHORT).show();}
                } else {
                    Toast.makeText(StudentHomeworkUploadActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    baseAdapter.notifyDataSetChanged();
                }
            }
        });

    }
    public void submit(View v){
        //获取意图对象
        Intent intent1=getIntent();
        //获取传递的值
        String homeworkId = intent1.getStringExtra("homeworkId");//作业号
        String str2=intent1.getStringExtra("data3");    //学生学号
        String str = intent1.getStringExtra("data");    //课程名
        String str1 = intent1.getStringExtra("data2");//授课老师
        String answer= t1.getText().toString().trim();//获取回答的内容
        Date currentTime = new Date(System.currentTimeMillis());//获取提交回答的时间
        HomeworkRecord.uploadAnswer(str, str1, str2, homeworkId, answer, currentTime, new CCallBack() {
            @Override
            public void onSuccess(Object o) {
                //如果上传了作业的回答，就跳转回原先界面
                StudentHomeworkUploadActivity.this.setResult(RESULT_OK);
                finish();
            }
            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(StudentHomeworkUploadActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        });

    }
    private BaseAdapter baseAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return dataList1.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList1.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(StudentHomeworkUploadActivity.this).inflate(R.layout.item_list9,null);
            }
            final Homework homework = dataList1.get(position);
            TextView hwSubject = (TextView) convertView.findViewById(R.id.homeworkSubject);
            hwSubject.setText(homework.getContent());
            return convertView;
        }
    };

    public void back(View v){
        finish();
    }
}
