package com.design.teaching_assistant.relevantClass;

import android.os.AsyncTask;

import com.design.teaching_assistant.utils.CCallBack;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;

public class CourseComment extends BmobObject {          //类名千万别超过20个字符，否则会报异常(这一点很重要)
    private String course7Name;   //课程名
    private String student7Num;  //学生学号
    private String teacher7Name; //老师姓名
    private Float commentScore;  //本次评教得分

    public CourseComment(String course7Name,String teacher7Name,String student7Num,Float commentScore) {
        this.course7Name = course7Name;
        this.teacher7Name = teacher7Name;
        this.student7Num = student7Num;
        this.commentScore = commentScore;
    }

    public String getCourse7Name() {
        return this.course7Name;
    }

    public void setCourse7Name(String course7Name) {
        this.course7Name = course7Name;
    }

    public String getStudent7Num() {
        return this.student7Num;
    }

    public void setStudent7Num(String student7Num) {
        this.student7Num = student7Num;
    }


    public String getTeacher7Name() {
        return this.teacher7Name;
    }

    public void setTeacher7Name(String teacher7Name) {
        this.teacher7Name = teacher7Name;
    }

    public Float getCommentScore() {
        return this.commentScore;
    }

    public void setCommentScore(Float commentScore) {
        this.commentScore = commentScore;
    }

    final static int EXIST = -1;
    final static int ERROR = -2;
    final static int OK = -3;

    /*
    学生提交评教结果至数据库*/

    public static void submittingCommentResult (final String course7Name,final String teacher7Name, final String student7Num, final Float commentScore , final CCallBack cCallBack) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                //判断是否有重复的课程评教记录
                BmobQuery<CourseComment> bmobQuery = new BmobQuery<CourseComment>();
                bmobQuery.addWhereEqualTo("course7Name", course7Name);  //课程名比较
                bmobQuery.addWhereEqualTo("student7Num",student7Num);   //学号比较
                List<CourseComment> endingCommentList = null;
                try {
                    endingCommentList = bmobQuery.findObjectsSync(CourseComment.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (endingCommentList != null && endingCommentList.size() > 0) {
                    return EXIST;
                }
                CourseComment endingCommentRecord = new CourseComment(course7Name,teacher7Name,student7Num,commentScore);
                try {
                    endingCommentRecord.saveSync();
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
                        cCallBack.onFailure(EXIST, "已经评教过了！");
                        break;
                    default:
                        cCallBack.onFailure(ERROR, "提交评教失败！");
                        break;
                }
            }
        }.execute();
    }

}