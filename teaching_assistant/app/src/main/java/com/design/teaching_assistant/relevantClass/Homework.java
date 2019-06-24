package com.design.teaching_assistant.relevantClass;

import android.os.AsyncTask;

import com.design.teaching_assistant.utils.CCallBack;

import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;

public class Homework  extends BmobObject {
    private String homeworkId;        //作业识别号
    private String course13Name;      //课程名
    private String teacher13Name;     //布置作业老师
    private String content;           //教师上传的作业题描述
    private Date establishedTime;

    //构造方法一
    public Homework(){

    }
    //构造方法二

    public Homework(String course13Name,String teacher13Name,String homeworkId,String content ,Date establishedTime) {

        this.course13Name = course13Name;
        this.teacher13Name = teacher13Name;
        this.homeworkId = homeworkId;
        this.content = content;
        this.establishedTime = establishedTime;
    }

    public String getHomeworkId() {
        return this.homeworkId;
    }

    public void setHomeworkId(String  homeworkId) {
        this.homeworkId = homeworkId;
    }


    public String getCourse13Name() {
        return this.course13Name;
    }

    public void setCourse13Name(String course13Name) {
        this.course13Name = course13Name;
    }

    public String getTeacher13Name() {
        return this.teacher13Name;
    }

    public void setTeacher13Name(String teacher13Name) {
        this.teacher13Name = teacher13Name;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getEstablishedTime() {
        return this.establishedTime;
    }

    public void setEstablishedTime(Date establishedTime) {
        this.establishedTime = establishedTime;
    }

    final static int NOTEXIST = -4;
    final static int EXIST = -1;
    final static int ERROR = -2;
    final static int OK = -3;

    /**
     * 教师上传作业
     */
    public static void uploadHomework(final String course13Name, final String teacher13Name, final String homeworkId, final String content, final Date establishedTime , final CCallBack cCallBack) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                //判断是否需要布置作业
                BmobQuery<Homework> bmobQuery = new BmobQuery<Homework>();
                bmobQuery.addWhereEqualTo("course13Name",course13Name);
                bmobQuery.addWhereEqualTo("teacher13Name",teacher13Name);
                List<Homework> homeworkList = null;
                try {
                    homeworkList = bmobQuery.findObjectsSync(Homework.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (homeworkList != null && homeworkList.size() >= 8) {
                    return EXIST;
                }
                else {
                    Homework homework = new Homework(course13Name, teacher13Name, homeworkId, content, establishedTime);
                    try {
                        homework.saveSync();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return ERROR;
                    }
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
                        cCallBack.onFailure(EXIST, "作业题已经全部布置完毕！");
                        break;
                    default:
                        cCallBack.onFailure(ERROR, "作业题没有上传成功！");
                        break;
                }
            }
        }.execute();
    }
}
