package org.example.Topic;

import java.util.List;

public class Vote {
    private String description;
    private int count;
    private List<Answer> answer;

    public Vote(String description, int count, List<Answer> answer) {
        this.description = description;
        this.count = count;
        this.answer = answer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Answer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Answer> answer) {
        this.answer = answer;
    }
}
