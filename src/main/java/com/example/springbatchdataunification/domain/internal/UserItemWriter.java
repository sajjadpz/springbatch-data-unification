package com.example.springbatchdataunification.domain.internal;


import com.example.springbatchdataunification.domain.User;
import com.example.springbatchdataunification.domain.UserDao;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class UserItemWriter implements ItemWriter<User> {

    private UserDao userDao;

    @Override
    public void write(List<? extends User> users) throws Exception {
        for (User user : users) {
            userDao.saveUser(user);
        }
    }

    public UserItemWriter setUserDao(UserDao userDao) {
        this.userDao = userDao;
        return this;
    }
}
