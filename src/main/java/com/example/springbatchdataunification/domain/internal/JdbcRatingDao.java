package com.example.springbatchdataunification.domain.internal;

import com.example.springbatchdataunification.domain.Rating;
import com.example.springbatchdataunification.domain.RatingDao;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class JdbcRatingDao implements RatingDao {

    private NamedParameterJdbcOperations namedParameterJdbcTemplate;

    private static final String INSERT_RATING = "INSERT INTO RATINGS (user_id, movie_id, rating, rating_timestamp) " +
            "VALUES (:userId, :movieId, :rating, :ratingTimestamp)";

    @Override
    public void saveRating(Rating rating) {
        namedParameterJdbcTemplate.update(INSERT_RATING, new BeanPropertySqlParameterSource(rating));
    }

    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
}
