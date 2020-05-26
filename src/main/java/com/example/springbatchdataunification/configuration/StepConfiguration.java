package com.example.springbatchdataunification.configuration;

import com.example.springbatchdataunification.domain.Movie;
import com.example.springbatchdataunification.domain.Rating;
import com.example.springbatchdataunification.domain.User;
import com.example.springbatchdataunification.domain.internal.*;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@Configuration
public class StepConfiguration {

    @Value("${batch.input.movies}")
    private Resource inMovies;

    @Value("${batch.input.users}")
    private Resource inUsers;

    @Value("${batch.input.ratings}")
    private Resource inRatings;

    private final DataSource dataSource;

    public StepConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public ItemWriter<User> writerUser() {
        JdbcUserDao jdbcUserDao = new JdbcUserDao();
        jdbcUserDao.setDataSource(dataSource);
        return new UserItemWriter()
                .setUserDao(jdbcUserDao);
    }

    @Bean
    public ItemWriter<Rating> writeRating() {
        JdbcRatingDao jdbcRatingDao = new JdbcRatingDao();
        jdbcRatingDao.setDataSource(dataSource);
        return new RatingItemWriter()
                .setRatingDao(jdbcRatingDao);
    }

    @Bean
    public ItemWriter<Movie> writeMovie() {
        JdbcMovieDao jdbcMovieDao = new JdbcMovieDao();
        jdbcMovieDao.setDataSource(dataSource);
        return new MovieItemWriter()
                .setMovieDao(jdbcMovieDao);
    }

    @Bean
    public FlatFileItemReader<Movie> readMovie() {
        return new FlatFileItemReaderBuilder<Movie>()
                .name("readMovie")
                .resource(inMovies)
                .delimited().delimiter("::")
                .names("movieId", "movieTitle", "genre")
                .fieldSetMapper(new MovieFieldSetMapper())
                .build();
    }

    @Bean
    public FlatFileItemReader<User> readUser() {
        return new FlatFileItemReaderBuilder<User>()
                .name("readUser")
                .resource(inUsers)
                .delimited().delimiter("::")
                .names("userId, twitterId")
                .fieldSetMapper(new UserFieldSetMapper())
                .build();
    }

    @Bean
    public FlatFileItemReader<Rating> readRating() {
        return new FlatFileItemReaderBuilder<Rating>()
                .name("readRating")
                .resource(inRatings)
                .delimited().delimiter("::")
                .names("userId", "movieId", "rating", "ratingTimestamp")
                .fieldSetMapper(new RatingSetFileMapper())
                .build();
    }

}
