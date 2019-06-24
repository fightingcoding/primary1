package com.design.teaching_assistant.relevantClass;

import android.os.AsyncTask;

import com.design.teaching_assistant.utils.CCallBack;

import java.util.Date;

import cn.bmob.v3.BmobObject;

public class Notice extends BmobObject {
    private String content;
    private Date establishedTime;
    private String publisher;   //发布人
    private String course1Name;
    public Notice(String content,Date establishedTime,String course1Name, String publisher) {
        this.content = content;
        this.establishedTime = establishedTime;
        this.course1Name = course1Name;
        this.publisher = publisher;


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

    public String getCourse1Name() {
        return this.course1Name;
    }

    public void setCourse1Name(String course1Name) {
        this.course1Name = course1Name;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    final static int NOTEXIST = -4;
    final static int EXIST = -1;
    final static int ERROR = -2;
    final static int OK = -3;

    /**
     * 教师发布课程
     */
    public static void publishingNotice(final String content, final Date  establishedTime, final String course1Name ,final String publisher, final CCallBack cCallBack) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {

                Notice notice = new Notice(content,establishedTime,course1Name,publisher);
                try {
                    notice.saveSync();
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
                        cCallBack.onFailure(ERROR, "发布公告失败!");
                        break;
                }
            }
        }.execute();
    }

}
