package com.design.teaching_assistant.student;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.design.teaching_assistant.R;
import com.design.teaching_assistant.relevantClass.OnlineClassTest;
import com.design.teaching_assistant.relevantClass.Question;
import com.design.teaching_assistant.relevantClass.QuestionOption;
import com.design.teaching_assistant.relevantClass.TestRecord;
import com.design.teaching_assistant.utils.CCallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class StudentNewClassTestActivity extends AppCompatActivity {
    String answer = "您提交的结果中正确答案有:";
    Button submitBtn;
    RadioGroup radioGroup1;
    RadioButton radio11,radio12,radio13;
    RadioGroup radioGroup2;
    RadioButton radio21,radio22,radio23;
    RadioGroup radioGroup3;
    RadioButton radio31,radio32,radio33;
    RadioGroup radioGroup4;
    RadioButton radio41,radio42,radio43;
    RadioGroup radioGroup5;
    RadioButton radio51,radio52,radio53;
    TextView text1,text2,text3,text4,text5;
    TextView text11,text12,text13,text14,text21,text22,text23,text24,
             text31,text32,text33,text34,text41,text42,text43,text44,
             text51,text52,text53,text54;
    private List<OnlineClassTest> newClassTests = new ArrayList<>();
    private OnlineClassTest newestTest;
    private List<Question> questions;
    Float score ;
    Integer order11,order12,order13,order14,
            order21,order22,order23,order24,
            order31,order32,order33,order34,
            order41,order42,order43,order44,
            order51,order52,order53,order54;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_new_class_test);
        Intent intent= getIntent();
        //获取传递的值
        String str2 = intent.getStringExtra("data3");   //学生学号
        String str = intent.getStringExtra("data");    //课程名
        String str1 = intent.getStringExtra("data2");//授课老师
        initView();
        queryNewTestFromOnlineClassTest(str);
    }
    private void initView(){

        text1 = (TextView)  findViewById(R.id.text1);

        text11 = (TextView) findViewById(R.id.text11);

        text12 = (TextView) findViewById(R.id.text12);

        text13 = (TextView) findViewById(R.id.text13);

        text14 = (TextView) findViewById(R.id.text14);

        text2 = (TextView) findViewById(R.id.text2);

        text21 = (TextView) findViewById(R.id.text21);

        text22 = (TextView) findViewById(R.id.text22);

        text23 = (TextView) findViewById(R.id.text23);

        text24 = (TextView) findViewById(R.id.text24);

        text3 = (TextView) findViewById(R.id.text3);

        text31 = (TextView) findViewById(R.id.text31);

        text32 = (TextView) findViewById(R.id.text32);

        text33 = (TextView) findViewById(R.id.text33);

        text34 = (TextView) findViewById(R.id.text34);

        text4 = (TextView) findViewById(R.id.text4);

        text41 = (TextView) findViewById(R.id.text41);

        text42 = (TextView) findViewById(R.id.text42);

        text43 = (TextView) findViewById(R.id.text43);

        text44 = (TextView) findViewById(R.id.text44);

        text5 = (TextView) findViewById(R.id.text5);

        text51 = (TextView) findViewById(R.id.text51);

        text52 = (TextView) findViewById(R.id.text52);

        text53 = (TextView) findViewById(R.id.text53);

        text54 = (TextView) findViewById(R.id.text54);

        submitBtn=findViewById(R.id.submitResult);    //提交结果按钮
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    List<OnlineClassTest> list = (List<OnlineClassTest>) msg.obj;
                    if (list.size()==0){
                        Toast.makeText(StudentNewClassTestActivity.this,"未查询到测试！",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(StudentNewClassTestActivity.this, "查询到这门课的测试！", Toast.LENGTH_SHORT).show();
                        newestTest = list.get(list.size() - 1);

                        questions = newestTest.getQuestions();
                        text1.setText(questions.get(0).getContent());
                        text2.setText(questions.get(1).getContent());
                        text3.setText(questions.get(2).getContent());
                        text4.setText(questions.get(3).getContent());
                        text5.setText(questions.get(4).getContent());


                        for (int i = 0; i < questions.size(); i++) {
                            Question question = questions.get(i);
                            List<QuestionOption> questionOptions = new ArrayList<>(question.getOptions());
                            switch (i) {
                                case 0:
                                    //为TextView设置
                                    text11.setText(questionOptions.get(0).getContent());
                                    text12.setText(questionOptions.get(1).getContent());
                                    text13.setText(questionOptions.get(2).getContent());
                                    text14.setText(questionOptions.get(3).getContent());
                                    break;
                                case 1:
                                    text21.setText(questionOptions.get(0).getContent());
                                    text22.setText(questionOptions.get(1).getContent());
                                    text23.setText(questionOptions.get(2).getContent());
                                    text24.setText(questionOptions.get(3).getContent());
                                    break;
                                case 2:
                                    text31.setText(questionOptions.get(0).getContent());
                                    text32.setText(questionOptions.get(1).getContent());
                                    text33.setText(questionOptions.get(2).getContent());
                                    text34.setText(questionOptions.get(3).getContent());
                                    break;
                                case 3:
                                    text41.setText(questionOptions.get(0).getContent());
                                    text42.setText(questionOptions.get(1).getContent());
                                    text43.setText(questionOptions.get(2).getContent());
                                    text44.setText(questionOptions.get(3).getContent());
                                    break;
                                case 4:
                                    text51.setText(questionOptions.get(0).getContent());
                                    text52.setText(questionOptions.get(1).getContent());
                                    text53.setText(questionOptions.get(2).getContent());
                                    text54.setText(questionOptions.get(3).getContent());
                                    break;
                            }

                        }
                        score =0F ; //给分数一个初值0
                        submitBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent= getIntent();
                                //获取传递的值
                                String str = intent.getStringExtra("data");    //课程名
                                String str2 = intent.getStringExtra("data3");   //学生学号
                                for (Question q : questions) {
                                    Log.e("answer order:",String.valueOf(q.getAnswerOrder()));
                                }
                                Question question = questions.get(0);
                                List<QuestionOption> questionOptions1 = new ArrayList<>(question.getOptions());
                                order11 = questionOptions1.get(0).getOptionOrder();
                                order12 = questionOptions1.get(1).getOptionOrder();
                                order13 = questionOptions1.get(2).getOptionOrder();
                                order14 = questionOptions1.get(3).getOptionOrder();
                                radioGroup1=(RadioGroup)findViewById(R.id.radioGroup1);

                                if(radioGroup1.getCheckedRadioButtonId()==R.id.radio11&&questions.get(0).getAnswerOrder()==order11){
                                    score+=20;
                                    answer+="1.A"+",";
                                }
                                if(radioGroup1.getCheckedRadioButtonId()==R.id.radio12&&questions.get(0).getAnswerOrder()==order12){
                                    score+=20;
                                    answer+="1.B"+",";
                                }
                                if(radioGroup1.getCheckedRadioButtonId()==R.id.radio13&&questions.get(0).getAnswerOrder()==order13){
                                    score+=20;
                                    answer+="1.C"+",";
                                }
                                if(radioGroup1.getCheckedRadioButtonId()==R.id.radio14&&questions.get(0).getAnswerOrder()==order14){
                                    score+=20;
                                    answer+="1.D"+",";
                                }
                                Question question1 = questions.get(1);
                                List<QuestionOption> questionOptions2 = new ArrayList<>(question1.getOptions());
                                order21 = questionOptions2.get(0).getOptionOrder();
                                order22 = questionOptions2.get(1).getOptionOrder();
                                order23 = questionOptions2.get(2).getOptionOrder();
                                order24 = questionOptions2.get(3).getOptionOrder();

                                radioGroup2=(RadioGroup)findViewById(R.id.radioGroup2);
                                if(radioGroup2.getCheckedRadioButtonId()==R.id.radio21&&questions.get(1).getAnswerOrder()==order21){
                                    score+=20;
                                    answer+="2.A"+",";
                                }
                                if(radioGroup2.getCheckedRadioButtonId()==R.id.radio22&&questions.get(1).getAnswerOrder()==order22){
                                    score+=20;
                                    answer+="2.B"+",";
                                }
                                if(radioGroup2.getCheckedRadioButtonId()==R.id.radio23&&questions.get(1).getAnswerOrder()==order23){
                                    score+=20;
                                    answer+="2.C"+",";
                                }
                                if(radioGroup2.getCheckedRadioButtonId()==R.id.radio24&&questions.get(1).getAnswerOrder()==order24){
                                    score+=20;
                                    answer+="2.D"+",";
                                }
                                Question question2 = questions.get(2);
                                List<QuestionOption> questionOptions3 = new ArrayList<>(question2.getOptions());
                                 order31 = questionOptions3.get(0).getOptionOrder();
                                 order32 = questionOptions3.get(1).getOptionOrder();
                                 order33 = questionOptions3.get(2).getOptionOrder();
                                 order34 = questionOptions3.get(3).getOptionOrder();

                                radioGroup3=(RadioGroup)findViewById(R.id.radioGroup3);
                                if(radioGroup3.getCheckedRadioButtonId()==R.id.radio31&&questions.get(2).getAnswerOrder()==order31){
                                    score+=20;
                                    answer+="3.A"+",";
                                }
                                if(radioGroup3.getCheckedRadioButtonId()==R.id.radio32&&questions.get(2).getAnswerOrder()==order32){
                                    score+=20;
                                    answer+="3.B"+",";
                                }
                                if(radioGroup3.getCheckedRadioButtonId()==R.id.radio33&&questions.get(2).getAnswerOrder()==order33){
                                    score+=20;
                                    answer+="3.C"+",";
                                }
                                if(radioGroup3.getCheckedRadioButtonId()==R.id.radio34&&questions.get(2).getAnswerOrder()==order34){
                                    score+=20;
                                    answer+="3.D"+",";
                                }
                                Question question3 = questions.get(3);
                                List<QuestionOption> questionOptions4 = new ArrayList<>(question3.getOptions());
                                order41 = questionOptions4.get(0).getOptionOrder();
                                order42 = questionOptions4.get(1).getOptionOrder();
                                order43 = questionOptions4.get(2).getOptionOrder();
                                order44 = questionOptions4.get(3).getOptionOrder();

                                radioGroup4=(RadioGroup)findViewById(R.id.radioGroup4);
                                if(radioGroup4.getCheckedRadioButtonId()==R.id.radio41&&questions.get(3).getAnswerOrder()==order41){
                                    score+=20;
                                    answer+="4.A"+",";
                                }
                                if(radioGroup4.getCheckedRadioButtonId()==R.id.radio42&&questions.get(3).getAnswerOrder()==order42){
                                    score+=20;
                                    answer+="4.B"+",";
                                }
                                if(radioGroup4.getCheckedRadioButtonId()==R.id.radio43&&questions.get(3).getAnswerOrder()==order43){
                                    score+=20;
                                    answer+="4.C"+",";
                                }
                                if(radioGroup4.getCheckedRadioButtonId()==R.id.radio44&&questions.get(3).getAnswerOrder()==order44){
                                    score+=20;
                                    answer+="4.D"+",";
                                }
                                Question question4 = questions.get(4);
                                List<QuestionOption> questionOptions5 = new ArrayList<>(question4.getOptions());
                                order51 = questionOptions5.get(0).getOptionOrder();
                                order52 = questionOptions5.get(1).getOptionOrder();
                                order53 = questionOptions5.get(2).getOptionOrder();
                                order54 = questionOptions5.get(3).getOptionOrder();

                                radioGroup5=(RadioGroup)findViewById(R.id.radioGroup5);
                                if(radioGroup5.getCheckedRadioButtonId()==R.id.radio51&&questions.get(4).getAnswerOrder()==order51){
                                    score+=20;
                                    answer+="5.A";
                                }
                                if(radioGroup5.getCheckedRadioButtonId()==R.id.radio52&&questions.get(4).getAnswerOrder()==order52){
                                    score+=20;
                                    answer+="5.B";
                                }
                                if(radioGroup5.getCheckedRadioButtonId()==R.id.radio53&&questions.get(4).getAnswerOrder()==order53){
                                    score+=20;
                                    answer+="5.C";
                                }
                                if(radioGroup5.getCheckedRadioButtonId()==R.id.radio54&&questions.get(4).getAnswerOrder()==order54){
                                    score+=20;
                                    answer+="5.D";
                                }
                                Toast.makeText(StudentNewClassTestActivity.this,"您测试的得分为:"+score+"分(每题20分)！",Toast.LENGTH_SHORT).show();
                                TestRecord.getTestRecord(str, str2,score, answer, newestTest, new CCallBack() {
                                    @Override
                                    public void onSuccess(Object o) {

                                        Toast.makeText(StudentNewClassTestActivity.this, "测试结果提交成功,并且已生成记录！", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(int code, String msg) {
                                        Toast.makeText(StudentNewClassTestActivity.this,msg,Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });

                    }
                    break;
            }

        }
    };

    /*public void submitTestResult(View v){
        Intent intent= getIntent();
        //获取传递的值
        String str = intent.getStringExtra("data");    //课程名
        String str2 = intent.getStringExtra("data3");//学生学号

        for (Question q : questions) {
            Log.e("answer order:",String.valueOf(q.getAnswerOrder()));
        }
        radioGroup1=(RadioGroup)findViewById(R.id.radioGroup1);
        Log.e("checked id 1", String.valueOf(radioGroup1.getCheckedRadioButtonId()));
        Log.e("checked 【-id ", String.valueOf(R.id.radio11));
        if(radioGroup1.getCheckedRadioButtonId()==R.id.radio11&&questions.get(0).getAnswerOrder()==1){
            score+=20;
            answer+="A"+",";
        }
        if(radioGroup1.getCheckedRadioButtonId()==R.id.radio12&&questions.get(0).getAnswerOrder()==2){
            score+=20;
            answer+="B"+",";
        }
        if(radioGroup1.getCheckedRadioButtonId()==R.id.radio13&&questions.get(0).getAnswerOrder()==3){
            score+=20;
            answer+="C"+",";
        }
        if(radioGroup1.getCheckedRadioButtonId()==R.id.radio14&&questions.get(0).getAnswerOrder()==4){
            score+=20;
            answer+="D"+",";
        }

        radioGroup2=(RadioGroup)findViewById(R.id.radioGroup2);
        if(radioGroup2.getCheckedRadioButtonId()==R.id.radio21&&questions.get(1).getAnswerOrder()==1){
            score+=20;
            answer+="A"+",";
        }
        if(radioGroup2.getCheckedRadioButtonId()==R.id.radio22&&questions.get(1).getAnswerOrder()==2){
            score+=20;
            answer+="B"+",";
        }
        if(radioGroup2.getCheckedRadioButtonId()==R.id.radio23&&questions.get(1).getAnswerOrder()==3){
            score+=20;
            answer+="C"+",";
        }
        if(radioGroup2.getCheckedRadioButtonId()==R.id.radio24&&questions.get(1).getAnswerOrder()==4){
            score+=20;
            answer+="D"+",";
        }

        radioGroup3=(RadioGroup)findViewById(R.id.radioGroup3);
        if(radioGroup3.getCheckedRadioButtonId()==R.id.radio31&&questions.get(2).getAnswerOrder()==1){
            score+=20;
            answer+="A"+",";
        }
        if(radioGroup3.getCheckedRadioButtonId()==R.id.radio32&&questions.get(2).getAnswerOrder()==2){
            score+=20;
            answer+="B"+",";
        }
        if(radioGroup3.getCheckedRadioButtonId()==R.id.radio33&&questions.get(2).getAnswerOrder()==3){
            score+=20;
            answer+="C"+",";
        }
        if(radioGroup3.getCheckedRadioButtonId()==R.id.radio34&&questions.get(2).getAnswerOrder()==4){
            score+=20;
            answer+="D"+",";
        }

        radioGroup4=(RadioGroup)findViewById(R.id.radioGroup4);
        if(radioGroup4.getCheckedRadioButtonId()==R.id.radio41&&questions.get(3).getAnswerOrder()==1){
            score+=20;
            answer+="A"+",";
        }
        if(radioGroup4.getCheckedRadioButtonId()==R.id.radio42&&questions.get(3).getAnswerOrder()==2){
            score+=20;
            answer+="B"+",";
        }
        if(radioGroup4.getCheckedRadioButtonId()==R.id.radio43&&questions.get(3).getAnswerOrder()==3){
            score+=20;
            answer+="C"+",";
        }
        if(radioGroup4.getCheckedRadioButtonId()==R.id.radio44&&questions.get(3).getAnswerOrder()==4){
            score+=20;
            answer+="D"+",";
        }

        radioGroup5=(RadioGroup)findViewById(R.id.radioGroup5);
        if(radioGroup5.getCheckedRadioButtonId()==R.id.radio51&&questions.get(4).getAnswerOrder()==1){
            score+=20;
            answer+="A";
        }
        if(radioGroup5.getCheckedRadioButtonId()==R.id.radio52&&questions.get(4).getAnswerOrder()==2){
            score+=20;
            answer+="B";
        }
        if(radioGroup5.getCheckedRadioButtonId()==R.id.radio53&&questions.get(4).getAnswerOrder()==3){
            score+=20;
            answer+="C";
        }
        if(radioGroup5.getCheckedRadioButtonId()==R.id.radio54&&questions.get(4).getAnswerOrder()==4){
            score+=20;
            answer+="D";
        }
        //TestRecord testRecord = new TestRecord(str,score,answer,newestTest);
        TestRecord.getTestRecord(str, str2， score, answer, newestTest, new CCallBack() {
            @Override
            public void onSuccess(Object o) {

                Toast.makeText(StudentNewClassTestActivity.this, "测试结果提交成功,并且已生成记录！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(StudentNewClassTestActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        });

    }*/
    private void  queryNewTestFromOnlineClassTest(String course4Name){
        BmobQuery<OnlineClassTest> bmobQuery = new BmobQuery<OnlineClassTest>();
        if (course4Name != null) {
            bmobQuery.addWhereEqualTo("course3Name", course4Name);
        }
        bmobQuery.findObjects(new FindListener<OnlineClassTest>() {
            @Override
            public void done(final List<OnlineClassTest> list, BmobException e) {

                if (e == null) {
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
                } else {
                    Toast.makeText(StudentNewClassTestActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void back(View v){
        finish();
    }
}




























































