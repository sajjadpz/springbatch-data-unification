package com.example.springbatchdataunification;

import org.junit.runner.RunWith;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

/**
 * @author sajjadpervaiz
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class DataUnificationJobTest {


    @Autowired
    private JdbcOperations jdbcTemplate;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

}
