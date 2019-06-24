package com.design.teaching_assistant.relevantClass;

import android.os.AsyncTask;

import com.design.teaching_assistant.utils.CCallBack;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;

public class Vote extends BmobObject {

    private String course8Name;
    private String student8Num;
    private String teacher8Name;//授课老师姓名
    private String voteResult;
    private Date establishedTime;
    private Date deadline;

    public Vote(String course8Name, String student8Num, String teacher8Name, String voteResult, Date establishedTime,Date deadline) {
        this.course8Name = course8Name;
        this.student8Num = student8Num;
        this.teacher8Name = teacher8Name;
        this.voteResult = voteResult;
        this.establishedTime = establishedTime;
        this.deadline = deadline;
    }

    public String getCourse8Name() {
        return course8Name;
    }

    public void setCourse8Name(String course8Name) {
        this.course8Name = course8Name;
    }

    public String getStudent8Num() {
        return student8Num;
    }

    public void setStudent8Num(String student8Num) {
        this.student8Num = student8Num;
    }

    public String getTeacher8Name() {
        return teacher8Name;
    }

    public void setTeacher8Name(String teacher8Name) {
        this.teacher8Name = teacher8Name;
    }

    public String getVoteResult() {
        return this.voteResult;
    }

    public void setVoteResult(String voteResult) {
        this.voteResult = voteResult;
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

    final static int EXIST = -1;
    final static int ERROR = -2;
    final static int OK = -3;

    /**
     * 学生提交评教结果至数据库
     */
    public static void submittingVoteResult (final String course8Name, final String student8Num,final String teacher8Name, final String voteResult, final Date establishedTime , final Date deadline,final CCallBack cCallBack) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                //判断是否有重复的课堂投票评教记录
                BmobQuery<Vote> bmobQuery = new BmobQuery<>();
                bmobQuery.addWhereEqualTo("course8Name", course8Name);  //课程号比较
                bmobQuery.addWhereEqualTo("student8Num",student8Num);   //学号比较
                bmobQuery.addWhereEqualTo("establisedTime",establishedTime);//投票日期比较
                List<Vote> voteResultList = null;
                try {
                    voteResultList = bmobQuery.findObjectsSync(Vote.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (voteResultList != null && voteResultList.size() > 0) {
                    return EXIST;
                }
                Vote bmobVoteRecord = new Vote(course8Name,student8Num,teacher8Name,voteResult,establishedTime,deadline);
                try {
                    bmobVoteRecord.saveSync();
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
                        cCallBack.onFailure(EXIST, "本堂课已经投过票了！");
                        break;
                    default:
                        cCallBack.onFailure(ERROR, "投票提交失败！");
                        break;
                }
            }
        }.execute();
    }

}
