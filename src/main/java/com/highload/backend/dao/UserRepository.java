package com.highload.backend.dao;

import com.highload.backend.model.UserRegisterBody;
import com.highload.backend.model.generated.tables.Users;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserRepository {

    private final DSLContext context;

    public UserRepository(DSLContext context) {
        this.context = context;
    }

    public UUID add(UserRegisterBody body) {
        var result = context.insertInto(Users.USERS)
            .columns(
                Users.USERS.FIRSTNAME,
                Users.USERS.SECONDNAME,
                Users.USERS.BIRTHDATE,
                Users.USERS.BIOGRAPHY,
                Users.USERS.CITY,
                Users.USERS.PASSWORDHASH)
            .values(
                body.getFirstName(),
                body.getSecondName(),
                body.getBirthdate(),
                body.getBiography(),
                body.getCity(),
                body.getPassword())
            .returningResult(Users.USERS.ID)
            .fetchOne();
        assert result != null;
        return result.getValue((Users.USERS.ID));
    }
}
