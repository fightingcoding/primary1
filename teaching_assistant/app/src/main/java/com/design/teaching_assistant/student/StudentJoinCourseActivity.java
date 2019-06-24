package com.design.teaching_assistant.student;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.design.teaching_assistant.R;
import com.design.teaching_assistant.relevantClass.Correlation;
import com.design.teaching_assistant.relevantClass.Course;
import com.design.teaching_assistant.utils.CCallBack;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class StudentJoinCourseActivity extends AppCompatActivity {
    private ListView listView;
    private List<Course> dataList = new ArrayList<Course>();
    private EditText t1;
    private TextView textView;
    private TextView text;
    private TextView text1;
    private TextView text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_join_course);
        initView();
    }
    private void initView(){
        t1=findViewById(R.id.hunt);
        ListView listView=findViewById(R.id.list);
        listView.setAdapter(baseAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Toast.makeText(StudentJoinCourseActivity.this, "您点击了第"+(position+1)+"行", Toast.LENGTH_SHORT).show();
                text = view.findViewById(R.id.courseId);
                text1 = view.findViewById(R.id.courseName);
                text2 = view.findViewById(R.id.instructorName);
                //获取意图对象
                Intent intent1 = getIntent();
                //获取传递的值
                final String str = intent1.getStringExtra("data");
                final String t2 = text.getText().toString();
                final String t3 = text1.getText().toString();
                final String t4 = text2.getText().toString();
                AlertDialog.Builder dialog = new AlertDialog.Builder(StudentJoinCourseActivity.this);
                dialog.setTitle("确认对话框");
                dialog.setMessage("确定加入这门课程？");
                dialog.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog, int which) {
                        Correlation.joinCourse(t2, t3, t4, str, new CCallBack() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast.makeText(StudentJoinCourseActivity.this,"加入成功！",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(int code, String msg) {
                                Toast.makeText(StudentJoinCourseActivity.this, msg, Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(StudentJoinCourseActivity.this,"您已经取消了操作！",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
                return true;
            }
        });


    }
    private void queryDataFromBmob1(String courseName) {
        dataList.clear();
        BmobQuery<Course> bmobQuery=new BmobQuery<Course>();
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
                    Toast.makeText(StudentJoinCourseActivity.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                    baseAdapter.notifyDataSetChanged();
                }
            }
        });
    }
    public void searchFor(View v){
        String searchContent=t1.getText().toString().trim();
        queryDataFromBmob1(searchContent);
        /*queryDataFromBmob1(null);
        t1.getText().clear();*/

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
                convertView = LayoutInflater.from(StudentJoinCourseActivity.this).inflate(R.layout.item_list2,null);
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

    /*@Override
    protected void onResume() {
        super.onResume();
        queryDataFromBmob1(null);
    }*/
    public void back(View v){
        finish();
    }
}
