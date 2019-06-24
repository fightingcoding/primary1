package com.design.teaching_assistant.teacher;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.design.teaching_assistant.R;
import com.design.teaching_assistant.relevantClass.Vote;

import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class TeacherCheckVoteActivity extends AppCompatActivity {
    private TextView text1;
    private TextView text2;
    private TextView text3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_check_vote);
        text1 = findViewById(R.id.textView11); //不懂
        text2 = findViewById(R.id.textView12); //一般
        text3 = findViewById(R.id.textView13); //懂
        //获取意图对象
        Intent intent1 = getIntent();
        //获取传递的值
        String str = intent1.getStringExtra("data"); //课程名
        String str1 = intent1.getStringExtra("data1");//授课老师
        Date current1Date = new Date(System.currentTimeMillis());//获取当前时间
        queryDataFromVote(str,str1);
    }
    private void  queryDataFromVote(String course8Name, String teacher8Name){
        BmobQuery<Vote> bmobQuery = new BmobQuery<Vote>();
        if (course8Name != null&&teacher8Name != null) {
            bmobQuery.addWhereEqualTo("course8Name", course8Name);
            bmobQuery.addWhereEqualTo("teacher8Name",teacher8Name);
    }
        bmobQuery.findObjects(new FindListener<Vote>() {
            @Override
            public void done(final List<Vote> list, BmobException e) {

                if (e == null) {
                    if(list.size()==0){
                        showToast("截止目前还没有人投票呢！");
                    }else{
                        Integer yes,no,inter;
                        yes = 0;    //懂
                        no = 0;    //不懂
                        inter = 0;//一般
                    showToast("共查询到"+list.size()+"条投票信息！");
                    for(int i=0;i<list.size();i++){
                        if((list.get(i).getVoteResult()).equals("懂了")){
                            yes++;
                        }
                        if((list.get(i).getVoteResult()).equals("不懂")){
                            no++;
                        }
                        if((list.get(i).getVoteResult()).equals("一般懂")){
                            inter++;
                        }
                    }
                    String t1,t2,t3;
                    t1 = String.valueOf(no)+"票";    //不懂
                    t2 = String.valueOf(inter)+"票";//一般懂
                    t3 = String.valueOf(yes)+"票"; //懂了
                    text1.setText(t1);
                    text2.setText(t2);
                    text3.setText(t3);
                    }
                } else {
                    Toast.makeText(TeacherCheckVoteActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    public void back(View v){
        finish();
    }
}
