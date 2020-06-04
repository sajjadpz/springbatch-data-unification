package com.example.springbatchdataunification.configuration;

import com.example.springbatchdataunification.domain.Movie;
import com.example.springbatchdataunification.domain.Rating;
import com.example.springbatchdataunification.domain.User;
import com.example.springbatchdataunification.processor.MovieItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    private StepConfiguration stepConfiguration;

    public JobConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, DataSource dataSource, StepConfiguration stepConfiguration) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.stepConfiguration = stepConfiguration;
    }

    @Bean
    public Job dataUnificationJob() {
        return jobBuilderFactory.get("dataUnificationJob")
                .start(splitFlow())
                .build()
                .build();
    }

    @Bean
    public Flow splitFlow() {
        return new FlowBuilder<SimpleFlow>("splitFlow")
                .split(new SimpleAsyncTaskExecutor())
                .add(flow1(), flow2())
                .build();
    }

    @Bean
    public Flow flow1() {
        return new FlowBuilder<SimpleFlow>("flow1")
                .start(movieLoad())
                .next(userLoad())
                .build();
    }

    @Bean
    public Flow flow2() {
        return new FlowBuilder<SimpleFlow>("flow2")
                .start(ratingLoad())
                .build();
    }

    @Bean
    public MovieItemProcessor processMovie() {
        return new MovieItemProcessor();
    }

    @Bean
    protected Step userLoad() {
        return stepBuilderFactory.get("userLoad")
                .<User, User>chunk(1000)
                .reader(stepConfiguration.readUser())
                .writer(stepConfiguration.writerUser())
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .throttleLimit(20)
                .build();
    }

    @Bean
    protected Step ratingLoad() {
        return stepBuilderFactory.get("ratingLoad")
                .<Rating, Rating>chunk(1000)
                .reader(stepConfiguration.readRating())
                .writer(stepConfiguration.writeRating())
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .throttleLimit(20)
                .build();
    }

    @Bean
    protected Step movieLoad() {
        return stepBuilderFactory.get("movieLoad")
                .<Movie, Movie>chunk(1000)
                .reader(stepConfiguration.readMovie())
                .processor(processMovie())
                .writer(stepConfiguration.writeMovie())
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .throttleLimit(20)
                .build();
    }
}
