package com.example.springbatchdataunification.processor;

import com.example.springbatchdataunification.domain.Movie;
import org.springframework.batch.item.ItemProcessor;


/**
 *
 *
 * @author sajjadpervaiz
 */
public class MovieItemProcessor implements ItemProcessor<Movie, Movie> {
    @Override
    public Movie process(Movie movie) throws Exception {
        return null;
    }
}
