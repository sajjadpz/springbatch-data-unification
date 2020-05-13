package com.example.springbatchdataunification.domain.internal;


import com.example.springbatchdataunification.domain.User;
import com.example.springbatchdataunification.domain.UserDao;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class JdbcUserDao implements UserDao {

    private NamedParameterJdbcOperations namedParameterJdbcTemplate;

    private static final String INSERT_USER = "INSERT INTO USERS (user_id, twitter_id) VALUES (:userId, :twitterId)";

    @Override
    public void saveUser(User user) {
        namedParameterJdbcTemplate.update(INSERT_USER, new BeanPropertySqlParameterSource(user));
    }

    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
}
