package com.design.teaching_assistant.relevantClass;

import android.os.AsyncTask;

import com.design.teaching_assistant.utils.CCallBack;

import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;

public class SignRecord extends BmobObject {
    private Boolean isSign;         //用于判断学生是否签到
    private Date signTime;
    private String student9Num;   //学生学号
    private String course9Name;
    private String teacher9Name;

    //构造方法1
    public SignRecord(){

    }

    //构造方法2
    public SignRecord(Boolean isSign,Date signTime,String student9Num,String course9Name,String teacher9Name) {
        this.isSign = isSign;
        this.signTime = signTime;
        this.student9Num = student9Num;
        this.course9Name = course9Name;
        this.teacher9Name = teacher9Name;
    }

    public Boolean getIsSign() {
        return this.isSign;
    }

    public void setIsSign(Boolean isSign) {
        this.isSign = isSign;
    }

    public Date getSignTime() {
        return this.signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public String getStudent9Num() {
        return this.student9Num;
    }

    public void setStudent9Num(String student9Num) {
        this.student9Num = student9Num;
    }

    public String getCourse9Name() {
        return this.course9Name;
    }

    public void setCourse9Name(String course9Name) {
        this.course9Name = course9Name;
    }

    public String getTeacher9Name() {
        return this.teacher9Name;
    }

    public void setTeacher9Name(String teacher9Name) {
        this.teacher9Name = teacher9Name;
    }
    final static int EXIST = -1;
    final static int ERROR = -2;
    final static int OK = -3;

    /**
     * 用户加入课堂记录
     */
    public static void participateInLesson(final Boolean isSign, final Date signTime, final String student9Num,final String course9Name,final String teacher9Name, final CCallBack cCallBack) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {

                //判断是否有重复用户
                BmobQuery<SignRecord> bmobQuery = new BmobQuery<>();
                bmobQuery.addWhereEqualTo("course9Name", course9Name);
                bmobQuery.addWhereEqualTo("student9Num",student9Num);
                bmobQuery.addWhereEqualTo("teacher9Name",teacher9Name);
                List<SignRecord> recordList = null;
                try {
                    recordList = bmobQuery.findObjectsSync(SignRecord.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (recordList != null && recordList.size() > 0) {
                    return EXIST;
                }

                SignRecord signRecord = new SignRecord(isSign,signTime,student9Num,course9Name,teacher9Name);
                try {
                    signRecord.saveSync();
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
                        cCallBack.onFailure(EXIST, "用于签到的记录已经生成！");
                        break;
                    default:
                        cCallBack.onFailure(ERROR, "生成用于签到的记录失败！");
                        break;
                }
            }
        }.execute();
    }


}

