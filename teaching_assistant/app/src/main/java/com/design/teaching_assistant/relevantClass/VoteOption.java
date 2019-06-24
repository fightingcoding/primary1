package com.design.teaching_assistant.relevantClass;

import cn.bmob.v3.BmobObject;

public class VoteOption extends BmobObject {
    private Integer voteOptionId;
    private String optionContent;
    private Integer optionCount;
    private Vote vote;

    public VoteOption() {
    }

    public Vote getVote() {
        return this.vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public Integer getVoteOptionId() {
        return this.voteOptionId;
    }

    public void setVoteOptionId(Integer voteOptionId) {
        this.voteOptionId = voteOptionId;
    }

    public String getOptionContent() {
        return this.optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    public Integer getOptionCount() {
        return this.optionCount;
    }

    public void setOptionCount(Integer optionCount) {
        this.optionCount = optionCount;
    }
}
