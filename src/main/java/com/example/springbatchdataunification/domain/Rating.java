package com.example.springbatchdataunification.domain;

import java.util.Date;

/**
 * @author sajjadpervaiz
 */
public class Rating {

    private long userId;

    private long movieId;

    private int rating;

    private Date ratingTimestamp;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getRatingTimestamp() {
        return ratingTimestamp;
    }

    public void setRatingTimestamp(Date ratingTimestamp) {
        this.ratingTimestamp = ratingTimestamp;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "userId=" + userId +
                ", movieId=" + movieId +
                ", rating=" + rating +
                ", ratingTimeStamp=" + ratingTimestamp +
                '}';
    }
}
