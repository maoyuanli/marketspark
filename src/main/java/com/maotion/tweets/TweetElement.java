package com.maotion.tweets;

public class TweetElement {
    private String user;
    private String createdAt;
    private String text;

    public TweetElement(String user, String createdAt, String text) {
        this.user = user;
        this.createdAt = createdAt;
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
