package com.example.springbatchdataunification.domain.internal;

import com.example.springbatchdataunification.domain.Movie;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class MovieFieldSetMapper implements FieldSetMapper<Movie> {
    @Override
    public Movie mapFieldSet(FieldSet fieldSet) throws BindException {
        Movie movie = new Movie();
        movie.setMovieId(fieldSet.readLong("movieId"));
        movie.setGenre(fieldSet.readString("genre"));
        movie.setMovieTitle(fieldSet.readString("movieTitle"));
        return movie;
    }
}
