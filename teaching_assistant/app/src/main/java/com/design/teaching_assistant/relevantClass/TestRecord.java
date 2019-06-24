package com.design.teaching_assistant.relevantClass;

import android.os.AsyncTask;

import com.design.teaching_assistant.utils.CCallBack;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;

public class TestRecord extends BmobObject {
    private String student5Num;
    private String course5Name;
    private Float score;
    private String answer;
    private OnlineClassTest classTest;

    public TestRecord(String course5Name,String  student5Num,Float score ,String answer,OnlineClassTest classTest) {

        this.course5Name = course5Name;
        this.student5Num = student5Num;     //学生学号
        this.score = score;
        this.answer = answer;
        this.classTest = classTest;
    }


    public String getCourse5Name() {
        return this.course5Name;
    }

    public void setCourse5Name(String course5Name) {
        this.course5Name = course5Name;
    }

    public String getStudent5Num() {
        return student5Num;
    }

    public void setStudent5Num(String student5Num) {
        this.student5Num = student5Num;
    }

    public OnlineClassTest getClassTest() {
        return this.classTest;
    }

    public void setClassTest(OnlineClassTest classTest) {
        this.classTest = classTest;
    }

    public Float getScore() {
        return this.score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    final static int NOTEXIST = -4;
    final static int EXIST = -1;
    final static int ERROR = -2;
    final static int OK = -3;

    public static void getTestRecord(final String course5Name, final String student5Num,final Float score , final String answer, final OnlineClassTest classTest, final CCallBack cCallBack) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                //判断是否有重复的测试结果
                BmobQuery<TestRecord> bmobQuery = new BmobQuery<>();
                bmobQuery.addWhereEqualTo("course5Name", course5Name);
                bmobQuery.addWhereEqualTo("classTest",classTest);
                List<TestRecord> testRecordList = null;
                try {
                    testRecordList = bmobQuery.findObjectsSync(TestRecord.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (testRecordList != null && testRecordList.size() > 0) {
                    return EXIST;
                }

                TestRecord bmobTestRecord = new TestRecord(course5Name,student5Num,score,answer,classTest);
                try {
                    bmobTestRecord.saveSync();
                } catch (Exception e) {
                    e.printStackTrace();
                    return ERROR;
                }
                return OK;
            }
            @Override
            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
                switch (result) {
                    case OK:
                        cCallBack.onSuccess(OK);
                        break;
                    case EXIST:
                        cCallBack.onFailure(EXIST, "测试结果已经提交,无需再进行测试！");
                        break;
                    default:
                        cCallBack.onFailure(ERROR, "测试结果没有成功生成！");
                        break;
                }
            }
        }.execute();
    }
}
