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
import com.design.teaching_assistant.relevantClass.SignRecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class TeacherViewingAttendanceActivity extends AppCompatActivity {
    private ListView listView;
    private List<SignRecord>dataList1 = new ArrayList<SignRecord>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_viewing_attendance);
        listView=findViewById(R.id.list);
        //获取意图对象
        Intent intent = getIntent();
        //获取传递的值
        String str = intent.getStringExtra("data"); //课程名
        String str1 = intent.getStringExtra("data1");//授课老师
    }

    public void viewingSignDetails(View v){
        Intent intent = getIntent();
        //获取传递的值
        String str = intent.getStringExtra("data"); //课程名
        String str1 = intent.getStringExtra("data1");//授课老师
        listView.setAdapter(baseAdapter);
        querySignListFromSignRecord(str,str1,Boolean.FALSE);

    }

    public void terminateSign(View v){
        Intent intent = getIntent();
        //获取传递的值
        String str = intent.getStringExtra("data"); //课程名
        String str1 = intent.getStringExtra("data1");//授课老师
        queryAndResetSignStateFromSignRecord(str,str1);
        finish();

    }
    //查询签到记录表里的数据，并在结束签到时对签到状态统一进行修改
    private void queryAndResetSignStateFromSignRecord(String course12Name,String teacher12Name){

        //查询SignRecord表里课程名、老师姓名分别为course12Name、teacher12Name的数据
        BmobQuery<SignRecord> bmobQuery = new BmobQuery<SignRecord>();
        if (course12Name != null&&teacher12Name != null) {
            bmobQuery.addWhereEqualTo("course9Name",course12Name);
            bmobQuery.addWhereEqualTo("teacher9Name",teacher12Name);
        }
        bmobQuery.findObjects(new FindListener<SignRecord>() {
            @Override
            public void done(final List<SignRecord> list, BmobException e) {

                if (e == null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for(int j=0;j<list.size();j++)
                            {
                                String idStr = list.get(j).getObjectId(); //获取objectId以便于更新数据
                                //更新SignRecord表里面id为idStr的数据，isSign内容更新为False
                                list.get(j).setIsSign(Boolean.FALSE);
                                list.get(j).update(new UpdateListener(){

                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            Toast.makeText(TeacherViewingAttendanceActivity.this,"本次签到结束了！",Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(TeacherViewingAttendanceActivity.this,"失败："+e.getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }

                        }
                    }).start();

                    Toast.makeText(TeacherViewingAttendanceActivity.this,"对相应记录的状态更改成功！",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TeacherViewingAttendanceActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void querySignListFromSignRecord(String course11Name,String teacher11Name,Boolean isSign){
        dataList1.clear();
        //查找SignRecord表里teacher9Name、course9Name、isSign分别为teacher11Name、course11Name、isSign的数据
        BmobQuery<SignRecord> bmobQuery = new BmobQuery<SignRecord>();
        if (course11Name != null&&teacher11Name !=null&&isSign != Boolean.TRUE) {
            bmobQuery.addWhereEqualTo("course9Name",course11Name );
            bmobQuery.addWhereEqualTo("teacher9Name",teacher11Name);
            bmobQuery.addWhereEqualTo("isSign",isSign);
        }
        bmobQuery.findObjects(new FindListener<SignRecord>() {
            @Override
            public void done(final List<SignRecord> list, BmobException e) {

                if (e == null) {
                    for (int i = 0; i <list.size(); i++) {
                        dataList1.add(list.get(i));
                    }
                    baseAdapter.notifyDataSetChanged();
                    Toast.makeText(TeacherViewingAttendanceActivity.this,"共查询到"+list.size()+"条签到数据",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TeacherViewingAttendanceActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
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
                convertView = LayoutInflater.from(TeacherViewingAttendanceActivity.this).inflate(R.layout.item_list7,null);
            }
            final SignRecord signRecord = dataList1.get(position);
            TextView studentNum = (TextView) convertView.findViewById(R.id.studentNum);
            TextView signTime =(TextView) convertView.findViewById(R.id.signTime);
            //Date类型时间转换为字符串类型
            String signTime1=dateToString(signRecord.getSignTime(),"yyyy-MM-dd HH:mm:ss");
            TextView signState=(TextView)convertView.findViewById(R.id.signState);

            studentNum.setText(signRecord.getStudent9Num());
            signTime.setText(signTime1);
            return convertView;
        }
    };


    public void back(View v){
        finish();
    }
}
