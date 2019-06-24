package com.design.teaching_assistant.student;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.design.teaching_assistant.R;
import com.design.teaching_assistant.relevantClass.Notice;
import com.design.teaching_assistant.teacher.Teacher1MainActivity;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Student1MainActivity extends AppCompatActivity {
    private TextView textView;
    private TextView text1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student1_main);
        textView=findViewById(R.id.textView2);
        text1=findViewById(R.id.textView24);

        Intent intent= getIntent();
        //获取传递的值
        String str = intent.getStringExtra("data");    //课程名
        String str1 = intent.getStringExtra("data2");//授课老师
        //为TextView设置值
        queryDataFromNotice(str1,str);
        textView.setText(str);
    }
    public void classCommunication(View v){
        Intent intent= getIntent();
        //获取传递的值
        String str2=intent.getStringExtra("data3");    //学生学号
        String str = intent.getStringExtra("data");    //课程名
        String str1 = intent.getStringExtra("data2");//授课老师
        Intent it=new Intent(Student1MainActivity.this,StudentClassCommunication.class);
        it.putExtra("data",str);
        it.putExtra("data2",str1);
        it.putExtra("data3",str2);      //增加传递学生学号
        startActivity(it);
    }
    public void courseEndingComment(View v){
        Intent intent= getIntent();
        //获取传递的值
        String str2=intent.getStringExtra("data3");    //学生学号
        String str = intent.getStringExtra("data");    //课程名
        String str1 = intent.getStringExtra("data2");//授课老师
        Intent it=new Intent(Student1MainActivity.this, StudentCourseEndingCommentActivity.class);
        it.putExtra("data3",str2);                  //学生学号
        it.putExtra("data",str);                    //课程名
        it.putExtra("data2",str1);                   //授课老师
        startActivity(it);
    }
    public void viewingHomework(View v){
        Intent intent= getIntent();
        //获取传递的值
        String str2=intent.getStringExtra("data3");    //学生学号
        String str = intent.getStringExtra("data");    //课程名
        String str1 = intent.getStringExtra("data2");//授课老师
        Intent it=new Intent(Student1MainActivity.this,StudentViewingHomeworkActivity.class);
        it.putExtra("data3",str2);                  //学生学号
        it.putExtra("data",str);                    //课程名
        it.putExtra("data2",str1);                  //授课老师
        startActivity(it);
    }

    public void joinAttendance(View v){
        Intent intent= getIntent();
        //获取传递的值
        String str2=intent.getStringExtra("data3");    //学生学号
        String str = intent.getStringExtra("data");    //课程名
        String str1 = intent.getStringExtra("data2");//授课老师
        Intent it =new Intent(Student1MainActivity.this,StudentAttendanceActivity.class);
        it.putExtra("data3",str2);
        it.putExtra("data",str);
        it.putExtra("data2",str1);
        startActivity(it);

    }
    public void moreNotice(View v){
        Intent intent1 = this.getIntent();
        String str = intent1.getStringExtra("data");   //课程名
        String str1 = intent1.getStringExtra("data2");//授课老师
        Intent it=new Intent(Student1MainActivity.this,StudentViewingMoreNoticeActivity.class);
        it.putExtra("data",str);
        it.putExtra("data2",str1);//老师姓名
        startActivity(it);
    }
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    List<Notice> list= (List<Notice>) msg.obj;
                    if(list.size()==0){
                        //为TextView设置值
                        text1.setText(" 暂无公告 ！");
                    }
                    else if(list.size()==1){
                        Date establishedTime=list.get(0).getEstablishedTime();
                        String establishTime=dateToString(establishedTime,"yyyy-MM-dd HH:mm:ss");
                        String content=list.get(0).getContent();
                        //为TextView设置值
                        text1.setText(content+"\n"+"\n"+"                         "+"发布时间:"+establishTime);
                    }else{
                    Date establishedTime=list.get(list.size()-1).getEstablishedTime();
                    String establishTime=dateToString(establishedTime,"yyyy-MM-dd HH:mm:ss");
                    String content=list.get(list.size()-1).getContent();
                    //为TextView设置值
                    text1.setText(content+"\n"+"\n"+"                         "+"发布时间:"+establishTime);
                    }

                    break;
            }

        }
    };
    private void  queryDataFromNotice(String publisher,String course1Name) {
        //查找User表里userId为账号的数据
        BmobQuery<Notice> bmobQuery = new BmobQuery<Notice>();
        if (publisher != null&&course1Name != null) {
            bmobQuery.addWhereEqualTo("publisher", publisher);
            bmobQuery.addWhereEqualTo("course1Name",course1Name);
        }
        bmobQuery.findObjects(new FindListener<Notice>() {
            @Override
            public void done(final List<Notice> list, BmobException e) {

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
                    Toast.makeText(Student1MainActivity.this,"公告显示成功！",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Student1MainActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    public void back(View v){
        finish();
    }
}
