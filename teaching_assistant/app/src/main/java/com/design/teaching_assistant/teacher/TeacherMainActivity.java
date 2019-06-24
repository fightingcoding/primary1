package com.design.teaching_assistant.teacher;
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
import com.design.teaching_assistant.relevantClass.Course;
import com.design.teaching_assistant.relevantClass.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class TeacherMainActivity extends AppCompatActivity {
    private Button refresh;
    private ListView listView;
    private List<User> dataList = new ArrayList<User>();
    private List<Course>dataList1 = new ArrayList<Course>();
    private TextView textView;
    private TextView text;
    private TextView text2;
    private TextView text3;
    private Button addCourse;
    private String str1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);
        textView = findViewById(R.id.textView3);
        refresh = findViewById(R.id.refresh);
        //获取意图对象
        Intent intent1 = getIntent();
        //获取传递的值
        String str = intent1.getStringExtra("data");
        queryNameFromUser(str);
        listView=findViewById(R.id.list);
        listView.setAdapter(baseAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(TeacherMainActivity.this, "您点击了第"+(position+1)+"行", Toast.LENGTH_SHORT).show();
                text=view.findViewById(R.id.courseName);
                text2=view.findViewById(R.id.instructorName);//授课老师

                final String text1=text.getText().toString();
                final String text4=text2.getText().toString();

                AlertDialog.Builder dialog = new AlertDialog.Builder(TeacherMainActivity.this);
                dialog.setTitle("确认对话框");
                dialog.setMessage("确定进入这门课程？");
                dialog.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick (DialogInterface dialog, int which) {
                             Intent it= new  Intent(TeacherMainActivity.this,Teacher1MainActivity.class);
                                //设置传递键值对
                                it.putExtra("data",text1);  //课程名
                                it.putExtra("data1",text4);//授课老师
                                //激活意图
                             startActivity(it);
                            }
                        });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(TeacherMainActivity.this,"您已经取消了操作！",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
                return true;
            }
        });
        addCourse=(Button)findViewById(R.id.add);
        addCourse.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent it=new Intent(TeacherMainActivity.this,TeacherAddCourseActivity.class);
                startActivity(it);

            }
        });
    }
    private  Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    List<User> list= (List<User>) msg.obj;
                    String name=list.get(0).getUserName();
                    //为TextView设置值
                    textView.setText(name);
                    final String str1 = textView.getText().toString();
                    querydataFromCourse(str1);
                    refresh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            querydataFromCourse(str1);

                        }
                    });

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
                    Toast.makeText(TeacherMainActivity.this,"查询老师姓名成功！",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TeacherMainActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void  querydataFromCourse(String name1) {
        dataList1.clear();
        //查找Course表里instructorName为name1的数据
        BmobQuery<Course> bmobQuery = new BmobQuery<Course>();
        if (name1 != null) {
            bmobQuery.addWhereEqualTo("instructorName",name1 );
        }
        bmobQuery.findObjects(new FindListener<Course>() {
            @Override
            public void done(final List<Course> list, BmobException e) {

                if (e == null) {
                    for (int i = 0; i <list.size(); i++) {
                        dataList1.add(list.get(i));
                    }
                    baseAdapter.notifyDataSetChanged();
                    Toast.makeText(TeacherMainActivity.this,"共查询到"+list.size()+"条数据",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TeacherMainActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
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
                convertView = LayoutInflater.from(TeacherMainActivity.this).inflate(R.layout.item_list3,null);
            }
            final Course course = dataList1.get(position);
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
