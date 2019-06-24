package com.design.teaching_assistant.student;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.design.teaching_assistant.R;
import com.design.teaching_assistant.relevantClass.Correlation;
import com.design.teaching_assistant.relevantClass.Course;
import com.design.teaching_assistant.relevantClass.SignRecord;
import com.design.teaching_assistant.relevantClass.User;
import com.design.teaching_assistant.teacher.Teacher1MainActivity;
import com.design.teaching_assistant.teacher.TeacherMainActivity;
import com.design.teaching_assistant.utils.CCallBack;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class StudentMainActivity extends AppCompatActivity {
    private ListView listView;
    private List<Correlation>dataList1 = new ArrayList<Correlation>();
    private TextView textView;
    private TextView text;
    private TextView text2;
    private Button joinCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        listView=findViewById(R.id.list);
        listView.setAdapter(baseAdapter);
        textView=findViewById(R.id.textView3);
        //获取意图对象
        Intent intent = getIntent();
        //获取传递的值
        final String str = intent.getStringExtra("data"); //学生学号
        queryNameFromUser(str);
        querydataFromCorrelation(str);
        joinCourse=findViewById(R.id.add);
        joinCourse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent it = new Intent(StudentMainActivity.this,StudentJoinCourseActivity.class);
                it.putExtra("data",str);
                startActivity(it);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Toast.makeText(StudentMainActivity.this, "您点击了第"+(position+1)+"行", Toast.LENGTH_SHORT).show();
                text=view.findViewById(R.id.courseName);      //课程名
                text2=view.findViewById(R.id.instructorName);//授课老师
                final String text1=text.getText().toString().trim();
                final String text3=text2.getText().toString().trim();

                AlertDialog.Builder dialog = new AlertDialog.Builder(StudentMainActivity.this);
                dialog.setTitle("确认对话框");
                dialog.setMessage("确定进入这门课程？");
                dialog.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog, int which) {
                        Date currentDate = new Date(System.currentTimeMillis());//获取当前时间
                        SignRecord.participateInLesson(Boolean.FALSE, currentDate, str, text1,text3, new CCallBack() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast.makeText(StudentMainActivity.this,"已经成功生成用于本门课程签到的记录！",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(int code, String msg) {
                                Toast.makeText(StudentMainActivity.this, msg, Toast.LENGTH_SHORT).show();

                            }
                        });
                        Intent it= new  Intent(StudentMainActivity.this, Student1MainActivity.class);
                        //设置传递键值对
                        it.putExtra("data3",str);        //增加传递学生学号str
                        it.putExtra("data",text1);       //课程名
                        it.putExtra("data2",text3);    //授课老师
                        //激活意图
                        startActivity(it);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(StudentMainActivity.this,"您已经取消了操作！",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
                return true;
            }
        });

    }
     Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    List<User> list= (List<User>) msg.obj;
                    String name=list.get(0).getUserName();
                    //为TextView设置值
                    textView.setText(name);
                    break;
            }
        }
    };
    private void  queryNameFromUser(String userId) {
        //查找User表里userId为账号的数据
        BmobQuery<User> bmobQuery = new BmobQuery<User>();
        if (userId != null) {
            bmobQuery.addWhereEqualTo("userId", userId);
        }
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(final List<User> list, BmobException e) {

                if (e == null) {
                    /*for (int i = 0; i <list.size(); i++) {
                        dataList.add(list.get(i));
                    }*/
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message = handler.obtainMessage();
                            message.what = 0;
                            //以消息为载体
                            message.obj = list;//这里的list就是查询出list
                            //向handler发送消息
                            handler.sendMessage(message);
                        }
                    }).start();
                   /* Message message = handler.obtainMessage();
                    message.what = 0;
                    //以消息为载体
                    message.obj = list;//这里的list就是查询出list
                    //向handler发送消息
                    handler.sendMessage(message);
                 */
                    Toast.makeText(StudentMainActivity.this,"查询学生姓名成功！",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(StudentMainActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private void  querydataFromCorrelation(String cStudentNum) {
        dataList1.clear();
        //查找Correlation表里cStudentId为cStudentNum的数据
        BmobQuery<Correlation> bmobQuery = new BmobQuery<Correlation>();
        if (cStudentNum != null) {
            bmobQuery.addWhereEqualTo("cStudentId",cStudentNum );
        }
        bmobQuery.findObjects(new FindListener<Correlation>() {
            @Override
            public void done(final List<Correlation> list, BmobException e) {

                if (e == null) {
                    for (int i = 0; i <list.size(); i++) {
                        dataList1.add(list.get(i));
                    }
                    baseAdapter.notifyDataSetChanged();
                    Toast.makeText(StudentMainActivity.this,"共查询到"+list.size()+"条数据",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(StudentMainActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
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
                convertView = LayoutInflater.from(StudentMainActivity.this).inflate(R.layout.item_list4,null);
            }
            final Correlation corr = dataList1.get(position);
            TextView cId1 = (TextView) convertView.findViewById(R.id.courseId);
            TextView cName =(TextView) convertView.findViewById(R.id.courseName);
            TextView teachName=(TextView)convertView.findViewById(R.id.instructorName);
            TextView credit=(TextView)convertView.findViewById(R.id.studentNum);

            cId1.setText(corr.getcId());
            cName.setText(corr.getcName());
            teachName.setText(corr.getcTeacher());
            credit.setText(corr.getcStudentId());
            return convertView;
        }
    };
    public void refresh(View v){
        //获取意图对象
        Intent intent = getIntent();
        //获取传递的值
        final String str1 = intent.getStringExtra("data"); //学生学号
        querydataFromCorrelation(str1);
    }
    public void back(View v){
        finish();
    }
}
