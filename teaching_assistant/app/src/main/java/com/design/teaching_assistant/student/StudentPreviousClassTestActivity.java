package com.design.teaching_assistant.student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.design.teaching_assistant.R;
import com.design.teaching_assistant.relevantClass.Correlation;
import com.design.teaching_assistant.relevantClass.TestRecord;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class StudentPreviousClassTestActivity extends AppCompatActivity {
    private ListView listView;
    private List<TestRecord>dataList1 = new ArrayList<TestRecord>();
    private TextView textView;
    private TextView text;
    private TextView text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_previous_class_test);
        Intent intent1= getIntent();
        //获取传递的值
        String str2 = intent1.getStringExtra("data3");   //学生学号
        String str = intent1.getStringExtra("data");    //课程名
        String str1 = intent1.getStringExtra("data2");//授课老师
        listView=findViewById(R.id.list);
        listView.setAdapter(baseAdapter);
        queryDataFromTestRecord(str2,str);
    }
    private void  queryDataFromTestRecord(String studentNum,String courseName) {
        dataList1.clear();
        //查找TestRecord表里course5Name、student5Num分别为courseName、studentNum的数据
        BmobQuery<TestRecord> bmobQuery = new BmobQuery<TestRecord>();
        if (studentNum!= null&&courseName!= null) {
            bmobQuery.addWhereEqualTo("course5Name",courseName );
            bmobQuery.addWhereEqualTo("student5Num",studentNum);
        }
        bmobQuery.findObjects(new FindListener<TestRecord>() {
            @Override
            public void done(final List<TestRecord> list, BmobException e) {

                if (e == null) {
                    for (int i = 0; i <list.size(); i++) {
                        dataList1.add(list.get(i));
                    }
                    baseAdapter.notifyDataSetChanged();
                    Toast.makeText(StudentPreviousClassTestActivity.this,"共查询到"+list.size()+"条测试结果",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(StudentPreviousClassTestActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    baseAdapter.notifyDataSetChanged();
                }
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
                convertView = LayoutInflater.from(StudentPreviousClassTestActivity.this).inflate(R.layout.item_list6,null);
            }
            final TestRecord testRecord = dataList1.get(position);
            TextView tCName = (TextView) convertView.findViewById(R.id.course6Name);
            TextView tScore=(TextView)convertView.findViewById(R.id.testScore);
            TextView tTime =(TextView)convertView.findViewById(R.id.testTime);

            tCName.setText(testRecord.getCourse5Name());
            tScore.setText(testRecord.getScore().toString());
            tTime.setText(testRecord.getCreatedAt());   //测试记录生成时间
            return convertView;
        }
    };
    public void back(View v){
        finish();
    }
}
