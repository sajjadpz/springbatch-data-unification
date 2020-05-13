package com.example.springbatchdataunification.configuration;

import com.example.springbatchdataunification.domain.Movie;
import com.example.springbatchdataunification.domain.Rating;
import com.example.springbatchdataunification.domain.User;
import com.example.springbatchdataunification.domain.internal.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

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
//                .next(ratingLoad())
//                .next(ratingLoad())
                .build();
    }

    @Bean
    public FlatFileItemReader<User> readUser() {
        FlatFileItemReader<User> reader = new FlatFileItemReader<User>();
        reader.setResource(new ClassPathResource("data/movieratingtweets/users.dat"));
        reader.setLineMapper(new DefaultLineMapper<User>() {{
            setLineTokenizer(new DelimitedLineTokenizer("::") {{
                setNames("userId", "twitterId");
            }});
            setFieldSetMapper(new UserFieldSetMapper());
        }});
        return reader;
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
        FlatFileItemReader<Rating> reader = new FlatFileItemReader<Rating>();
        reader.setResource(new ClassPathResource("data/movieratingtweets/ratings.dat"));
        reader.setLineMapper(new DefaultLineMapper<Rating>() {{
            setLineTokenizer(new DelimitedLineTokenizer("::") {{
                setNames("userId", "movieId", "rating", "ratingTimestamp");
            }});
            setFieldSetMapper(new RatingSetFileMapper());
        }});
        return reader;
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
        FlatFileItemReader<Movie> reader = new FlatFileItemReader<Movie>();
        reader.setResource(new ClassPathResource("data/movieratingtweets/movies.dat"));
        reader.setLineMapper(new DefaultLineMapper<Movie>() {{
            setLineTokenizer(new DelimitedLineTokenizer("::") {{
                setNames("userId", "movieId", "rating", "ratingTimestamp");
            }});
            setFieldSetMapper(new MovieFieldSetMapper());
        }});
        return reader;
    }

    @Bean
    public ItemWriter<Movie> writeMovie() {
        JdbcMovieDao jdbcMovieDao = new JdbcMovieDao();
        jdbcMovieDao.setDataSource(dataSource);
        return new MovieItemWriter()
                .setMovieDao(jdbcMovieDao);
    }

    @Bean
    protected Step userLoad() {
        return stepBuilderFactory.get("userLoad")
                .<User, User>chunk(100)
                .reader(readUser())
                .writer(writerUser())
                .build();
    }

    @Bean
    protected Step ratingLoad() {
        return stepBuilderFactory.get("ratingLoad")
                .<Rating, Rating>chunk(100)
                .reader(readRating())
                .writer(writeRating())
                .build();
    }

    @Bean
    protected Step movieLoad() {
        return stepBuilderFactory.get("movieLoad")
                .<Movie, Movie>chunk(100)
                .reader(readMovie())
                .writer(writeMovie())
                .build();
    }


}
