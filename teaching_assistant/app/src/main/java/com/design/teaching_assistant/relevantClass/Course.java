package com.design.teaching_assistant.relevantClass;

import android.os.AsyncTask;

import com.design.teaching_assistant.utils.CCallBack;
import com.google.common.base.MoreObjects;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;

public class Course extends BmobObject {
    private String courseId;
    private String courseName;
    private String credit;
    private String instructorName;
    private List<Course>courseList;

    public Course(String courseId , String courseName,String credit,String instructorName) {
         this.courseId=courseId;
         this.courseName=courseName;
         this.credit=credit;
         this.instructorName=instructorName;
    }



    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }


    public String getCourseId() {
        return this.courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCredit() {
        return this.credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }
    @Override

    public String toString() {
        return MoreObjects.toStringHelper(this).add("courseId", this.courseId).add("courseName", this.courseName).add("credit", this.credit).add("instructorName", this.instructorName).toString();
    }
    final static int NOTEXIST = -4;
    final static int EXIST = -1;
    final static int ERROR = -2;
    final static int OK = -3;

    /**
     * 教师添加课程
     */
    public static void addCourse(final String courseId, final String courseName, final String credit ,final String instructorName, final CCallBack cCallBack) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {

                Course course = new Course(courseId,courseName,credit,instructorName);
                try {
                    course.saveSync();
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
                        cCallBack.onFailure(ERROR, "添加课程失败");
                        break;
                }
            }
        }.execute();
    }

    /**
     * 教师检索课程
     */
    public static void huntCourse(final String courseName,final CCallBack cCallBack) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {

                //判断是否有相应课程
                BmobQuery<Course> bmobQuery = new BmobQuery<>();
                bmobQuery.addWhereEqualTo("courseName", courseName);
                List<Course> courseList = null;
                try {
                    courseList = bmobQuery.findObjectsSync(Course.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (courseList != null && courseList.size() > 0) {
                    return OK;
                }
                return NOTEXIST;
            }

            @Override
            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
                switch (result) {
                    case NOTEXIST:
                        cCallBack.onSuccess(NOTEXIST);
                        break;
                    case OK:
                        cCallBack.onFailure(OK, "检索到课程！");
                        break;

                }
            }
        }.execute();
    }

}

