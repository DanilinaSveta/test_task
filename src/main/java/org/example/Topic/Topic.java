package org.example.Topic;

public class Topic {
    private String name_topic;
    private String name_vote;
    private Vote votes;

    public Topic(String name_topic, String name_vote, Vote votes) {
        this.name_topic = name_topic;
        this.name_vote = name_vote;
        this.votes = votes;
    }

    public String getName_topic() {
        return name_topic;
    }

    public void setName_topic(String name_topic) {
        this.name_topic = name_topic;
    }

    public String getName_vote() {
        return name_vote;
    }

    public void setName_vote(String name_vote) {
        this.name_vote = name_vote;
    }

    public Vote getVotes() {
        return votes;
    }

    public void setVotes(Vote votes) {
        this.votes = votes;
    }
}
