package com.example.springbatchdataunification.configuration;

import com.example.springbatchdataunification.domain.Movie;
import com.example.springbatchdataunification.domain.Rating;
import com.example.springbatchdataunification.domain.User;
import com.example.springbatchdataunification.domain.internal.*;
import com.example.springbatchdataunification.processor.MovieItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import javax.sql.DataSource;

/**
 * @author sajjadpervaiz
 */

@Configuration
@EnableBatchProcessing
public class JobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final DataSource dataSource;

    public JobConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, DataSource dataSource) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.dataSource = dataSource;
    }

    @Bean
    public Job dataUnificationJob() {
        return jobBuilderFactory.get("dataUnificationJob")
                .start(userLoad())
                .next(ratingLoad())
                .next(movieLoad())
                .build();
    }

    @Bean
    public FlatFileItemReader<User> readUser() {
        return new FlatFileItemReaderBuilder<User>()
                .name("readUser")
                .resource(new ClassPathResource("data/movieratingtweets/users.dat"))
                .delimited().delimiter("::")
                .names("userId, twitterId")
                .fieldSetMapper(new UserFieldSetMapper())
                .build();
    }

    @Bean
    public ItemWriter<User> writerUser() {
        JdbcUserDao jdbcUserDao = new JdbcUserDao();
        jdbcUserDao.setDataSource(dataSource);
        return new UserItemWriter()
                .setUserDao(jdbcUserDao);
    }

    @Bean
    public FlatFileItemReader<Rating> readRating() {
        return new FlatFileItemReaderBuilder<Rating>()
                .name("readRating")
                .resource(new ClassPathResource("data/movieratingtweets/ratings.dat"))
                .delimited().delimiter("::")
                .names("userId", "movieId", "rating", "ratingTimestamp")
                .fieldSetMapper(new RatingSetFileMapper())
                .build();
    }

    @Bean
    public ItemWriter<Rating> writeRating() {
        JdbcRatingDao jdbcRatingDao = new JdbcRatingDao();
        jdbcRatingDao.setDataSource(dataSource);
        return new RatingItemWriter()
                .setRatingDao(jdbcRatingDao);
    }

    @Bean
    public FlatFileItemReader<Movie> readMovie() {
        return new FlatFileItemReaderBuilder<Movie>()
                .name("readMovie")
                .resource(new ClassPathResource("data/movieratingtweets/movie.dat"))
                .delimited().delimiter("::")
                .names("movieId", "movieTitle", "genre")
                .fieldSetMapper(new MovieFieldSetMapper())
                .build();
    }

    @Bean
    public ItemWriter<Movie> writeMovie() {
        JdbcMovieDao jdbcMovieDao = new JdbcMovieDao();
        jdbcMovieDao.setDataSource(dataSource);
        return new MovieItemWriter()
                .setMovieDao(jdbcMovieDao);
    }

    @Bean
    public MovieItemProcessor processMovie() {
        return new MovieItemProcessor();
    }

    @Bean
    protected Step userLoad() {
        return stepBuilderFactory.get("userLoad")
                .<User, User>chunk(1000)
                .reader(readUser())
                .writer(writerUser())
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    protected Step ratingLoad() {
        return stepBuilderFactory.get("ratingLoad")
                .<Rating, Rating>chunk(1000)
                .reader(readRating())
                .writer(writeRating())
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    protected Step movieLoad() {
        return stepBuilderFactory.get("movieLoad")
                .<Movie, Movie>chunk(1000)
                .reader(readMovie())
                .processor(processMovie())
                .writer(writeMovie())
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }


}
