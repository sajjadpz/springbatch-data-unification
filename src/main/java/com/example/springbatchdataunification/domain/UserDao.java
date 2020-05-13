package com.example.springbatchdataunification.domain;

import org.springframework.stereotype.Service;

/**
 * Interface for writing {@link User} object
 */
public interface UserDao {
    void saveUser(User user);
}
