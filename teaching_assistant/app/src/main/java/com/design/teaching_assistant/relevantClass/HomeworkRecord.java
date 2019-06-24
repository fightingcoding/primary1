package com.design.teaching_assistant.relevantClass;

import android.os.AsyncTask;

import com.design.teaching_assistant.utils.CCallBack;

import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;

public class HomeworkRecord  extends BmobObject {

    private String homeworkId;     //作业号
    private String course14Name;  //课程名
    private String teacher14Name; //授课老师
    private String student14Num; //学生学号
    private String answer;       //对应作业的回答内容
    private Date submittedTime;  //回答提交的时间

    public HomeworkRecord(String homeworkId,String course14Name,String teacher14Name,String student14Num,String answer,Date submittedTime) {

        this.homeworkId = homeworkId;
        this.course14Name = course14Name;
        this.teacher14Name= teacher14Name;
        this.student14Num = student14Num;
        this.answer = answer;
        this.submittedTime = submittedTime;
    }

    public String  getHomeworkId() {
        return this.homeworkId;
    }

    public void setHomeworkId(String  homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getCourse14Name() {
        return course14Name;
    }

    public void setCourse14Name(String course14Name) {
        this.course14Name = course14Name;
    }

    public String getTeacher14Name() {
        return teacher14Name;
    }

    public void setTeacher14Name(String teacher14Name) {
        this.teacher14Name = teacher14Name;
    }

    public String getStudent14Num() {
        return student14Num;
    }

    public void setStudent14Num(String student14Num) {
        this.student14Num = student14Num;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getSubmittedTime() {
        return this.submittedTime;
    }

    public void setSubmittedTime(Date submittedTime) {
        this.submittedTime = submittedTime;
    }

    final static int EXIST = -1;
    final static int ERROR = -2;
    final static int OK = -3;

    /**
     * 学生上传回答作业
     */
    public static void uploadAnswer(final String course14Name,final String teacher14Name, final String student14Num, final String homeworkId, final String answer, final Date submittedTime , final CCallBack cCallBack) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                //判断是否已经回答过相应问题
                BmobQuery<HomeworkRecord> bmobQuery = new BmobQuery<HomeworkRecord>();
                bmobQuery.addWhereEqualTo("course14Name",course14Name);
                bmobQuery.addWhereEqualTo("student14Num",student14Num);
                bmobQuery.addWhereEqualTo("homeworkId",homeworkId);
                List<HomeworkRecord> homeworkRecordList = null;
                try {
                    homeworkRecordList = bmobQuery.findObjectsSync(HomeworkRecord.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (homeworkRecordList != null && homeworkRecordList.size() >0) {
                    return EXIST;
                }
                else {
                    HomeworkRecord homeworkRecord = new HomeworkRecord(homeworkId,course14Name, teacher14Name, student14Num, answer, submittedTime);
                    try {
                        homeworkRecord.saveSync();
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
                        cCallBack.onFailure(EXIST, "本作业题已经回答过了！");
                        break;
                    default:
                        cCallBack.onFailure(ERROR, "本作业题回答未上传成功！");
                        break;
                }
            }
        }.execute();
    }

}
