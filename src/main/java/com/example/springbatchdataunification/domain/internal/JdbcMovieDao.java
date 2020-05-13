package com.example.springbatchdataunification.domain.internal;

import com.example.springbatchdataunification.domain.Movie;
import com.example.springbatchdataunification.domain.MovieDao;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class JdbcMovieDao implements MovieDao {

    private static final String INSERT_MOVIE = "INSERT INTO MOVIES (movie_id, movie_genre, movie_title, movie_year)" +
            "VALUES (:movieId, :movieGenre, :movieTitle, :movieYear)";

    private NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public void saveMovie(Movie movie) {
        namedParameterJdbcOperations.update(INSERT_MOVIE, new BeanPropertySqlParameterSource(movie));
    }

    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcOperations = new NamedParameterJdbcTemplate(dataSource);
    }
}
