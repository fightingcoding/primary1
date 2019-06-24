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
import com.design.teaching_assistant.relevantClass.Notice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class StudentViewingMoreNoticeActivity extends AppCompatActivity {
    private ListView listView;
    private TextView textview;
    private List<Notice> dataList1 = new ArrayList<Notice>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_viewing_more_notice);
        Intent intent= getIntent();
        //获取传递的值
        String str = intent.getStringExtra("data");  //课程名
        String str1 = intent.getStringExtra("data2");//授课老师
        querydataFromNotice(str1,str);
        listView=findViewById(R.id.list);
        listView.setAdapter(baseAdapter);
    }
    private void  querydataFromNotice(String publisher,String course1Name) {
        dataList1.clear();
        //查找Notice表里的数据
        BmobQuery<Notice> bmobQuery = new BmobQuery<Notice>();
        if (publisher!= null&&course1Name!= null) {
            bmobQuery.addWhereEqualTo("publisher",publisher );
            bmobQuery.addWhereEqualTo("course1Name",course1Name);
        }
        bmobQuery.findObjects(new FindListener<Notice>() {
            @Override
            public void done(final List<Notice> list, BmobException e) {

                if (e == null) {
                    for (int i = 0; i <list.size(); i++) {
                        dataList1.add(list.get(i));
                    }
                    baseAdapter.notifyDataSetChanged();
                    Toast.makeText(StudentViewingMoreNoticeActivity.this,"共查询到"+list.size()+"条公告",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(StudentViewingMoreNoticeActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
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
                convertView = LayoutInflater.from(StudentViewingMoreNoticeActivity.this).inflate(R.layout.item_list5,null);
            }
            final Notice notice1 = dataList1.get(position);

            TextView name =(TextView) convertView.findViewById(R.id.course1Name);
            TextView content=(TextView)convertView.findViewById(R.id.noticeContent);
            TextView time=(TextView)convertView.findViewById(R.id.noticeTime);
            //Date类型时间转换为字符串类型
            String establishTime=dateToString(notice1.getEstablishedTime(),"yyyy-MM-dd HH:mm:ss");
            name.setText(notice1.getCourse1Name());
            content.setText(notice1.getContent());
            time.setText(establishTime);
            return convertView;
        }
    };
    public void back(View v){
        finish();
    }
}
