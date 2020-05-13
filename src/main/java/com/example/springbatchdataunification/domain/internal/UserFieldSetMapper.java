package com.example.springbatchdataunification.domain.internal;

import com.example.springbatchdataunification.domain.User;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class UserFieldSetMapper implements FieldSetMapper<User> {
    @Override
    public User mapFieldSet(FieldSet fieldSet) throws BindException {
        User user = new User();
        user.setUserId(fieldSet.readLong(0));
        user.setTwitterId(fieldSet.readLong(1));
        return user;
    }
}
