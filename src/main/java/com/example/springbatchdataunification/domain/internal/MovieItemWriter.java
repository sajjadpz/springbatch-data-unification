package com.example.springbatchdataunification.domain.internal;

import com.example.springbatchdataunification.domain.Movie;
import com.example.springbatchdataunification.domain.MovieDao;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class MovieItemWriter implements ItemWriter<Movie> {

    private MovieDao movieDao;

    @Override
    public void write(List<? extends Movie> movies) throws Exception {
        for (Movie movie : movies) {
            movieDao.saveMovie(movie);
        }
    }

    public MovieItemWriter setMovieDao(MovieDao movieDao) {
        this.movieDao = movieDao;
        return this;
    }
}
