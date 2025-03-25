package org.example.Topic;

public class Answer {
    private String answer;
    private int count_user;

    public Answer(String answer, int count_user) {
        this.answer = answer;
        this.count_user = count_user;
    }

    public int getCount_user() {
        return count_user;
    }

    public void setCount_user(int count_user) {
        this.count_user = count_user;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
