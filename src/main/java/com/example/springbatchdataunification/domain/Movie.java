package com.example.springbatchdataunification.domain;

import java.util.Date;

/**
 * @author sajjadpervaiz
 */
public class Movie {

    private long movieId;

    private String movieTitle;

    private Date movieYear;

    private String genre;

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public Date getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(Date movieYear) {
        this.movieYear = movieYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieID=" + movieId +
                ", movieTitle='" + movieTitle + '\'' +
                ", movieYear=" + movieYear +
                ", genre='" + genre + '\'' +
                '}';
    }
}
