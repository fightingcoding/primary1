package com.design.teaching_assistant.teacher;

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
import com.design.teaching_assistant.relevantClass.HomeworkRecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class TeacherCheckHomeworkConditionActivity extends AppCompatActivity {
    private TextView num;
    private ListView listView;
    private List<HomeworkRecord>dataList1 = new ArrayList<HomeworkRecord>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_check_homework_condition);
        num = findViewById(R.id.submittedNum);
        listView=findViewById(R.id.list);
        listView.setAdapter(baseAdapter);
        Intent intent =getIntent();
        //获取传递的值
        String str = intent.getStringExtra("data"); //课程名
        String str1 = intent.getStringExtra("data1");//授课老师
        String str2 = intent.getStringExtra("homeworkId");//作业号
        queryDataFromHomeworkRecord(str,str1,str2);


    }
    //主要用于显示提交作业的人数和名单
    private void queryDataFromHomeworkRecord(String course14Name,String teacher14Name,String homeworkId){
        dataList1.clear();
        //查找HomeworkRecord表里课程名、教师姓名、作业号分别为course14Name、teacher14Name、homeworkId的数据
        BmobQuery<HomeworkRecord> bmobQuery = new BmobQuery<HomeworkRecord>();
        if (course14Name != null&&teacher14Name != null) {
            bmobQuery.addWhereEqualTo("course14Name", course14Name);
            bmobQuery.addWhereEqualTo("teacher14Name",teacher14Name);
            bmobQuery.addWhereEqualTo("homeworkId",homeworkId);
        }
        bmobQuery.findObjects(new FindListener<HomeworkRecord>() {
            @Override
            public void done(final List<HomeworkRecord> list, BmobException e) {
                if (e == null) {
                    if(list.size()==0){
                        Toast.makeText(TeacherCheckHomeworkConditionActivity.this,"截止目前还没人提交作业呢！",Toast.LENGTH_SHORT).show();
                    }else{
                        num.setText(String.valueOf(list.size()));
                        for (int i = 0; i <list.size(); i++) {
                            dataList1.add(list.get(i));
                        }
                        baseAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(TeacherCheckHomeworkConditionActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    baseAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
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
                convertView = LayoutInflater.from(TeacherCheckHomeworkConditionActivity.this).inflate(R.layout.item_list8,null);
            }
            final HomeworkRecord homeworkRecord = dataList1.get(position);
            TextView sNum = (TextView) convertView.findViewById(R.id.student14Num);
            TextView hwAnswer =(TextView) convertView.findViewById(R.id.answer);
            TextView time=(TextView)convertView.findViewById(R.id.submittedTime);
            String  time1=dateToString(homeworkRecord.getSubmittedTime(),"yyyy-MM-dd HH:mm:ss");
            sNum.setText(homeworkRecord.getStudent14Num());
            hwAnswer.setText(homeworkRecord.getAnswer());
            time.setText(time1);
            return convertView;
        }
    };

    public void back(View v){
        finish();
    }
}
