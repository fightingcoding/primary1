package com.design.teaching_assistant.relevantClass;

import cn.bmob.v3.BmobObject;

public class QuestionOption extends BmobObject {
    private Integer optionOrder;
    private String  content;
    private Question question;

    public QuestionOption() {
    }

    public Question getQuestion() {
        return this.question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Integer getOptionOrder() {
        return this.optionOrder;
    }

    public void setOptionOrder(Integer optionOrder) {
        this.optionOrder = optionOrder;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
