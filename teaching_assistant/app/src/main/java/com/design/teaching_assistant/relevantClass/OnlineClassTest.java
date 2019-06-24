package com.design.teaching_assistant.relevantClass;

import android.os.AsyncTask;

import com.design.teaching_assistant.utils.CCallBack;

import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;

public class OnlineClassTest extends BmobObject {
    private String course3Name;
    private Date establishedTime;
    private Date deadline;
    private List<Question> questions;

    public OnlineClassTest(String course3Name, Date establishedTime, Date deadline, List<Question> questions) {
        this.course3Name = course3Name;
        this.establishedTime = establishedTime;
        this.deadline = deadline;
        this.questions = questions;
    }


    public List<Question> getQuestions() {
        return this.questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getCourse3Name() {
        return this.course3Name;
    }

    public void setCourse3Name(String course3Name) {
        this.course3Name = course3Name;
    }

    public Date getEstablishedTime() {
        return this.establishedTime;
    }

    public void setEstablishedTime(Date establishedTime) {
        this.establishedTime = establishedTime;
    }

    public Date getDeadline() {
        return this.deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    final static int NOTEXIST = -4;
    final static int EXIST = -1;
    final static int ERROR = -2;
    final static int OK = -3;

    /**
     * 教师发起测试
     */
    public static void createTest(final String course3Name, final List<Question> questions, final Date establishedTime, final Date deadline, final CCallBack cCallBack) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                //判断是否有重复测试
                BmobQuery<OnlineClassTest> bmobQuery = new BmobQuery<>();
                bmobQuery.addWhereEqualTo("establishedTime", establishedTime);
                List<OnlineClassTest> classTestList = null;
                try {
                    classTestList = bmobQuery.findObjectsSync(OnlineClassTest.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (classTestList != null && classTestList.size() > 0) {
                    return EXIST;
                }

                OnlineClassTest bmobTest = new OnlineClassTest(course3Name, establishedTime, deadline, questions);
                try {
                    bmobTest.saveSync();
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
                        cCallBack.onFailure(EXIST, "测试已经存在");
                        break;
                    default:
                        cCallBack.onFailure(ERROR, "测试没有成功！");
                        break;
                }
            }
        }.execute();
    }
}






