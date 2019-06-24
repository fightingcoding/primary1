package com.design.teaching_assistant.utils;

/**
 * Created by htz on 2019/5/19.
 */
public abstract class CCallBack<T> {

    public abstract void onSuccess(T t);

    public abstract void onFailure(int code, String msg);
}
