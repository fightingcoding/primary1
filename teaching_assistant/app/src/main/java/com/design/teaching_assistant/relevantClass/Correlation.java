package com.design.teaching_assistant.relevantClass;

import android.os.AsyncTask;

import com.design.teaching_assistant.utils.CCallBack;

import cn.bmob.v3.BmobObject;

public class Correlation extends BmobObject {
    private String cId;       //课程号
    private String cName;     //课程名
    private String cTeacher;  //授课老师姓名
    private String cStudentId;  //上课学生账号

    public Correlation(String cId,String cName,String cTeacher,String cStudentId ) {
        this.cId = cId;
        this.cName = cName;
        this.cTeacher = cTeacher;
        this.cStudentId = cStudentId;
    }
    public String getcId() {
        return this.cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }
    public String getcName() {
        return this.cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }
    public String getcTeacher() {
        return this.cTeacher;
    }

    public void setcTeacher(String cTeacher) {
        this.cTeacher = cTeacher;
    }
    public String getcStudentId() {
        return this.cStudentId;
    }

    public void setcStudentId(String cStudentId) {
        this.cStudentId = cStudentId;
    }
    final static int NOTEXIST = -4;
    final static int EXIST = -1;
    final static int ERROR = -2;
    final static int OK = -3;


    /**
     * 学生加入对应课程
     */
    public static void joinCourse(final String cId, final String cName, final String cTeacher ,final String cStudent, final CCallBack cCallBack) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {

                Correlation corr = new Correlation(cId,cName,cTeacher,cStudent);
                try {
                    corr.saveSync();
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
                    default:
                        cCallBack.onFailure(ERROR, "没能成功加入课程！");
                        break;
                }
            }
        }.execute();
    }
}
