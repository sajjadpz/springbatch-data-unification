package com.example.springbatchdataunification.processor;

import com.example.springbatchdataunification.domain.Movie;
import org.springframework.batch.item.ItemProcessor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author sajjadpervaiz
 */
public class MovieItemProcessor implements ItemProcessor<Movie, Movie> {
    @Override
    public Movie process(Movie movie) throws Exception {
        String movieTitle = movie.getMovieTitle();
        final String movieYear = findMovieReleaseYear(movieTitle);
        movie.setMovieYear(movieYear);
        return movie;
    }

    private String findMovieReleaseYear(String movieTitle) {
        Pattern p = Pattern.compile("(?<=\\().+?(?=\\))");
        Matcher m = p.matcher(movieTitle);
        return m.find() ? m.group() : null;
    }
}
