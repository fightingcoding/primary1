package com.design.teaching_assistant.relevantClass;


import java.util.Set;

import cn.bmob.v3.BmobObject;

public class Question  extends BmobObject {
    private Integer answerOrder;
    private String  content;
    private Set<QuestionOption> options;
    private Set<OnlineClassTest> tests;

    public Question() {
    }

    public Integer getAnswerOrder() {
        return this.answerOrder;
    }

    public void setAnswerOrder(Integer answerOrder) {
        this.answerOrder = answerOrder;
    }

    public Set<OnlineClassTest> getTests() {
        return this.tests;
    }

    public void setTests(Set<OnlineClassTest> tests) {
        this.tests = tests;
    }


    public Set<QuestionOption> getOptions() {
        return this.options;
    }

    public void setOptions(Set<QuestionOption> options) {
        this.options = options;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
