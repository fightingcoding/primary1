package com.design.teaching_assistant.application;

import android.app.Application;

public class MyApplication extends Application {
    private String userId;     //用户Id，登录时获取到，用于唯一标识一个用户
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
