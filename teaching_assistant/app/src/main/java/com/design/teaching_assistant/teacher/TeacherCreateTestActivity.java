package com.design.teaching_assistant.teacher;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.design.teaching_assistant.R;
import com.design.teaching_assistant.relevantClass.OnlineClassTest;
import com.design.teaching_assistant.relevantClass.Question;
import com.design.teaching_assistant.relevantClass.QuestionOption;
import com.design.teaching_assistant.relevantClass.User;
import com.design.teaching_assistant.utils.CCallBack;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.bmob.v3.BmobQuery;

public class TeacherCreateTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_create_test);
        //获取意图对象
        Intent intent1=getIntent();
        //获取传递的值
        String str = intent1.getStringExtra("data");
        String str1 = intent1.getStringExtra("data1");//授课老师
    }
    public void publishTest(View v) {
        //获取意图对象
        Intent intent1 = getIntent();
        //获取传递的值
        String str = intent1.getStringExtra("data");   //课程名
        //问题1
        EditText text1 = (EditText) findViewById(R.id.text1);
        EditText radio11 = (EditText) findViewById(R.id.radio11);
        EditText radio12 = (EditText) findViewById(R.id.radio12);
        EditText radio13 = (EditText) findViewById(R.id.radio13);
        EditText radio14 = (EditText) findViewById(R.id.radio14);
        EditText radiogroup1 = (EditText) findViewById(R.id.radiogroup1); //从此输入问题一的正确答案
        QuestionOption questionOption11 = new QuestionOption();
        questionOption11.setOptionOrder(1);
        questionOption11.setContent(radio11.getText().toString().trim());
        QuestionOption questionOption12 = new QuestionOption();
        questionOption12.setOptionOrder(2);
        questionOption12.setContent(radio12.getText().toString().trim());
        QuestionOption questionOption13 = new QuestionOption();
        questionOption13.setOptionOrder(3);
        questionOption13.setContent(radio13.getText().toString().trim());
        QuestionOption questionOption14 = new QuestionOption();
        questionOption14.setOptionOrder(4);
        questionOption14.setContent(radio14.getText().toString().trim());
        String answer1 = radiogroup1.getText().toString();        //问题一的正确答案
        Set<QuestionOption> questionOption1 = new HashSet<QuestionOption>();
        questionOption1.add(questionOption11);
        questionOption1.add(questionOption12);
        questionOption1.add(questionOption13);
        questionOption1.add(questionOption14);
        Question question1 = new Question();
        int answerOrder1 = 0;
        if (answer1.equals("A")) {
            //answerOrder1 = 1;
            question1.setAnswerOrder(1);
        } else if (answer1.equals("B")) {

            question1.setAnswerOrder(2);
            //answerOrder1 = 2;
        } else if (answer1.equals("C")) {

            question1.setAnswerOrder(3);
            //answerOrder1 = 3;
        } else {
            question1.setAnswerOrder(4);
           // answerOrder1 = 4;
        }
       // question1.setAnswerOrder(answerOrder1);       //问题一的正确答案顺序号
        question1.setContent(text1.getText().toString().trim());
        question1.setOptions(questionOption1);

        //问题2
        EditText text2 = (EditText) findViewById(R.id.text2);
        EditText radio21 = (EditText) findViewById(R.id.radio21);
        EditText radio22 = (EditText) findViewById(R.id.radio22);
        EditText radio23 = (EditText) findViewById(R.id.radio23);
        EditText radio24 = (EditText) findViewById(R.id.radio24);
        EditText radiogroup2 = (EditText) findViewById(R.id.radiogroup2);
        QuestionOption questionOption21 = new QuestionOption();
        questionOption21.setOptionOrder(1);
        questionOption21.setContent(radio21.getText().toString().trim());
        QuestionOption questionOption22 = new QuestionOption();
        questionOption22.setOptionOrder(2);
        questionOption22.setContent(radio22.getText().toString().trim());
        QuestionOption questionOption23 = new QuestionOption();
        questionOption23.setOptionOrder(3);
        questionOption23.setContent(radio23.getText().toString().trim());
        QuestionOption questionOption24 = new QuestionOption();
        questionOption24.setOptionOrder(4);
        questionOption24.setContent(radio24.getText().toString().trim());
        String answer2 = radiogroup2.getText().toString();         //问题二的正确答案
        Set<QuestionOption> questionOption2 = new HashSet<QuestionOption>();
        questionOption2.add(questionOption21);
        questionOption2.add(questionOption22);
        questionOption2.add(questionOption23);
        questionOption2.add(questionOption24);
        Question question2 = new Question();
        Integer answerOrder2 = 0;
        if (answer2.equals("A")) {
            //answerOrder2 = 1;
            question2.setAnswerOrder(1);
        } else if (answer2.equals("B")) {
            //answerOrder2 = 2;
            question2.setAnswerOrder(2);
        } else if (answer2.equals("C")) {
            //answerOrder2 = 3;
            question2.setAnswerOrder(3);
        } else {
            //answerOrder2 = 4;
            question2.setAnswerOrder(4);
        }
        //question2.setAnswerOrder(answerOrder2);         //问题二的答案顺序号
        question2.setContent(text2.getText().toString().trim());
        question2.setOptions(questionOption2);


        //问题3
        EditText text3 = (EditText) findViewById(R.id.text3);
        EditText radio31 = (EditText) findViewById(R.id.radio31);
        EditText radio32 = (EditText) findViewById(R.id.radio32);
        EditText radio33 = (EditText) findViewById(R.id.radio33);
        EditText radio34 = (EditText) findViewById(R.id.radio34);
        EditText radiogroup3 = (EditText) findViewById(R.id.radiogroup3);
        QuestionOption questionOption31 = new QuestionOption();
        questionOption31.setOptionOrder(1);
        questionOption31.setContent(radio31.getText().toString().trim());
        QuestionOption questionOption32 = new QuestionOption();
        questionOption32.setOptionOrder(2);
        questionOption32.setContent(radio32.getText().toString().trim());
        QuestionOption questionOption33 = new QuestionOption();
        questionOption33.setOptionOrder(3);
        questionOption33.setContent(radio33.getText().toString().trim());
        QuestionOption questionOption34 = new QuestionOption();
        questionOption34.setOptionOrder(4);
        questionOption34.setContent(radio34.getText().toString().trim());
        String answer3 = radiogroup3.getText().toString();        //问题三的正确答案
        Set<QuestionOption> questionOption3 = new HashSet<QuestionOption>();
        questionOption3.add(questionOption31);
        questionOption3.add(questionOption32);
        questionOption3.add(questionOption33);
        questionOption3.add(questionOption34);
        Question question3 = new Question();
        Integer answerOrder3 = 0;
        if (answer3.equals ("A")) {
            //answerOrder3 = 1;
            question3.setAnswerOrder(1);
        } else if (answer3.equals("B") ) {
            //answerOrder3 = 2;
            question3.setAnswerOrder(2);
        } else if (answer3.equals("C")) {
            question3.setAnswerOrder(3);
        } else {
            //answerOrder3=4;
            question3.setAnswerOrder(4);
        }
       // question3.setAnswerOrder(answerOrder3);     //问题三的正确答案顺序号
        question3.setContent(text3.getText().toString().trim());
        question3.setOptions(questionOption3);

        //问题4
        EditText text4 = (EditText) findViewById(R.id.text4);
        EditText radio41 = (EditText) findViewById(R.id.radio41);
        EditText radio42 = (EditText) findViewById(R.id.radio42);
        EditText radio43 = (EditText) findViewById(R.id.radio43);
        EditText radio44 = (EditText) findViewById(R.id.radio44);
        EditText radiogroup4 = (EditText) findViewById(R.id.radiogroup4);
        QuestionOption questionOption41 = new QuestionOption();
        questionOption41.setOptionOrder(1);
        questionOption41.setContent(radio41.getText().toString().trim());
        QuestionOption questionOption42 = new QuestionOption();
        questionOption42.setOptionOrder(2);
        questionOption42.setContent(radio42.getText().toString().trim());
        QuestionOption questionOption43 = new QuestionOption();
        questionOption43.setOptionOrder(3);
        questionOption43.setContent(radio43.getText().toString().trim());
        QuestionOption questionOption44 = new QuestionOption();
        questionOption44.setOptionOrder(4);
        questionOption44.setContent(radio44.getText().toString().trim().trim());
        String answer4 = radiogroup4.getText().toString().trim();     //问题四的正确答案
        Set<QuestionOption> questionOption4 = new HashSet<QuestionOption>();
        questionOption4.add(questionOption41);
        questionOption4.add(questionOption42);
        questionOption4.add(questionOption43);
        questionOption4.add(questionOption44);
        Question question4 = new Question();
        Integer answerOrder4 = 0;
        if (answer4.equals("A")) {
            answerOrder4 = 1;
        } else if (answer4.equals("B")) {
            answerOrder4 = 2;
        } else if (answer4.equals("C")) {
            answerOrder4 = 3;
        } else {
            answerOrder4 = 4;
        }
        question4.setAnswerOrder(answerOrder4);        //问题四的正确答案顺序号
        question4.setContent(text4.getText().toString().trim());
        question4.setOptions(questionOption4);


        //问题5
        EditText text5 = (EditText) findViewById(R.id.text5);
        EditText radio51 = (EditText) findViewById(R.id.radio51);
        EditText radio52 = (EditText) findViewById(R.id.radio52);
        EditText radio53 = (EditText) findViewById(R.id.radio53);
        EditText radio54 = (EditText) findViewById(R.id.radio54);
        EditText radiogroup5 = (EditText) findViewById(R.id.radiogroup5);
        QuestionOption questionOption51 = new QuestionOption();
        questionOption51.setOptionOrder(1);
        questionOption51.setContent(radio51.getText().toString().trim());
        QuestionOption questionOption52 = new QuestionOption();
        questionOption52.setOptionOrder(2);
        questionOption52.setContent(radio52.getText().toString().trim());
        QuestionOption questionOption53 = new QuestionOption();
        questionOption53.setOptionOrder(3);
        questionOption53.setContent(radio53.getText().toString().trim());
        QuestionOption questionOption54 = new QuestionOption();
        questionOption54.setOptionOrder(4);
        questionOption54.setContent(radio54.getText().toString().trim());
        String answer5 = radiogroup5.getText().toString().trim();  //问题五的正确答案
        Set<QuestionOption> questionOption5 = new HashSet<QuestionOption>();
        questionOption5.add(questionOption51);
        questionOption5.add(questionOption52);
        questionOption5.add(questionOption53);
        questionOption5.add(questionOption54);
        Question question5 = new Question();
        Integer answerOrder5 = 0;
        if (answer5.equals("A")) {
            answerOrder5 = 1;
        } else if (answer5.equals("B")) {
            answerOrder5 = 2;
        } else if (answer5.equals("C")) {
            answerOrder5 = 3;
        } else {
            answerOrder5 = 4;
        }
        question5.setAnswerOrder(answerOrder5);     //问题五的正确答案顺序号
        question5.setContent(text5.getText().toString().trim());
        question5.setOptions(questionOption5);

        List<Question> questionList = new ArrayList<Question>();
        questionList.add(question1);
        questionList.add(question2);
        questionList.add(question3);
        questionList.add(question4);
        questionList.add(question5);
        Date current1Date = new Date(System.currentTimeMillis());//获取当前时间
        long currentTime = System.currentTimeMillis();
        currentTime += 5 * 60 * 1000;
        Date deadline = new Date(currentTime);

        OnlineClassTest.createTest(str, questionList, current1Date, deadline, new CCallBack() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(TeacherCreateTestActivity.this,"测试创建成功！",Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(TeacherCreateTestActivity.this,msg,Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void back(View v){
        finish();
    }
}
