package com.design.teaching_assistant.relevantClass;

import android.os.AsyncTask;
import com.design.teaching_assistant.utils.CCallBack;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;

public class User extends BmobObject {
    private String userId;
    private String userName;
    private String password;
    private String identity;

    public User(String userId,String userName,String password,String identity) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.identity = identity;
    }
    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setIdentity(String identity){
        this.identity=identity;
    }
    public String getIdentity(){
        return this.identity;
    }
    final static int EXIST = -1;
    final static int ERROR = -2;
    final static int OK = -3;

    /**
     * 用户注册
     */
    public static void register(final String userId, final String userName, final String password,final String identity, final CCallBack cCallBack) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {

                //判断是否有重复用户
                BmobQuery<User> bmobQuery = new BmobQuery<>();
                bmobQuery.addWhereEqualTo("userId", userId);
                List<User> userList = null;
                try {
                    userList = bmobQuery.findObjectsSync(User.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (userList != null && userList.size() > 0) {
                    return EXIST;
                }

                User bmobUser = new User(userId,userName, password,identity);
                try {
                    bmobUser.saveSync();
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
                        cCallBack.onFailure(EXIST, "用户已存在");
                        break;
                    default:
                        cCallBack.onFailure(ERROR, "注册失败");
                        break;
                }
            }
        }.execute();
    }

    /**
     * 用户登录
     */
    public static void login(final String userId, final String password,final String identity, final CCallBack cCallBack) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {

                BmobQuery<User> bmobQuery = new BmobQuery<>();
                bmobQuery.addWhereEqualTo("userId", userId);
                bmobQuery.addWhereEqualTo("password", password);
                bmobQuery.addWhereEqualTo("identity",identity);
                List<User> userList = null;
                try {
                    userList = bmobQuery.findObjectsSync(User.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (userList != null && userList.size() > 0) {
                    return OK;
                }
                return ERROR;
            }

            @Override
            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
                switch (result) {
                    case OK:
                        cCallBack.onSuccess(OK);
                        break;
                    case ERROR:
                        cCallBack.onFailure(EXIST, "用户名、密码错误或者您未选择正确角色！");
                        break;
                    default:
                        break;
                }
            }
        }.execute();
    }

}

