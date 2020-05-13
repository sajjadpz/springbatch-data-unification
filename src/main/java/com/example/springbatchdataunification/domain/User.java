package com.example.springbatchdataunification.domain;

/**
 * @author sajjadpervaiz
 */
public class User {

    private long userId;

    private long twitterId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(long twitterId) {
        this.twitterId = twitterId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", twitterId=" + twitterId +
                '}';
    }


}
