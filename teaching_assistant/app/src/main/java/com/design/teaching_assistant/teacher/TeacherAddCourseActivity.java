package com.design.teaching_assistant.teacher;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.design.teaching_assistant.LoginActivity;
import com.design.teaching_assistant.R;
import com.design.teaching_assistant.RegisterActivity;
import com.design.teaching_assistant.relevantClass.Course;
import com.design.teaching_assistant.utils.CCallBack;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class TeacherAddCourseActivity extends AppCompatActivity {
    private ListView listView;
    private List<Course>dataList = new ArrayList<Course>();
    private EditText t1;
    private Button b1;
    private Button b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_add_course);
        initView();
    }
    private void initView(){
        t1=findViewById(R.id.hunt);
        b1=findViewById(R.id.button21);
        b2=findViewById(R.id.button24);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(baseAdapter);

    }
    private void queryDataFromBmob(String courseName) {
        dataList.clear();
        BmobQuery<Course>bmobQuery=new BmobQuery<Course>();
        if (courseName!=null){
            bmobQuery.addWhereEqualTo("courseName",courseName);
        }
        bmobQuery.findObjects(new FindListener<Course>() {
            @Override
            public void done(final List<Course> list, BmobException e) {

                if (e==null){
                    for (int i = 0; i < list.size(); i++) {
                        dataList.add(list.get(i));
                    }
                    baseAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(TeacherAddCourseActivity.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                    baseAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void huntFor(View v){
        String huntContent=t1.getText().toString().trim();
        queryDataFromBmob(huntContent);

    }
    public void addClass(View v){
        Intent it=new Intent(TeacherAddCourseActivity.this,TAddCourseActivity.class);
        startActivity(it);
    }
    private BaseAdapter baseAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(TeacherAddCourseActivity.this).inflate(R.layout.item_list1,null);
            }
            final Course course = dataList.get(position);
            TextView cId = (TextView) convertView.findViewById(R.id.courseId);
            TextView cName =(TextView) convertView.findViewById(R.id.courseName);
            TextView teachName=(TextView)convertView.findViewById(R.id.instructorName);
            TextView credit=(TextView)convertView.findViewById(R.id.credit);

            cId.setText(course.getCourseId());
            cName.setText(course.getCourseName());
            teachName.setText(course.getInstructorName());
            credit.setText(course.getCredit());
            return convertView;
        }
    };

    public void back(View v){
        finish();
    }
}
