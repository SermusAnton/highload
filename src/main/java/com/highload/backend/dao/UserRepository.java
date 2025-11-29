package com.highload.backend.dao;

import com.highload.backend.model.User;
import com.highload.backend.model.UserRegisterBody;
import com.highload.backend.model.generated.tables.Users;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Repository
public class UserRepository {

    private final DSLContext context;

    public UserRepository(DSLContext context) {
        this.context = context;
    }

    public User getBy(UUID userId) {
        var result = context.fetchOne(Users.USERS, Users.USERS.ID.eq(userId));
        if (Objects.isNull(result)) {
            return null;
        }
        return result.into(User.class);
    }

    public UUID add(UserRegisterBody body, String hash) {
        var result = context.insertInto(Users.USERS)
            .columns(
                Users.USERS.FIRST_NAME,
                Users.USERS.SECOND_NAME,
                Users.USERS.BIRTHDATE,
                Users.USERS.BIOGRAPHY,
                Users.USERS.CITY,
                Users.USERS.PASSWORD_HASH)
            .values(
                body.getFirstName(),
                body.getSecondName(),
                body.getBirthdate(),
                body.getBiography(),
                body.getCity(),
                hash)
            .returningResult(Users.USERS.ID)
            .fetchOne();
        assert result != null;
        return result.getValue((Users.USERS.ID));
    }

    public String getHashPassword(UUID userId) {
        return context.select(Users.USERS.PASSWORD_HASH)
            .from(Users.USERS)
            .where(Users.USERS.ID.eq(userId))
            .fetchOne(Users.USERS.PASSWORD_HASH, String.class);
    }

    public List<User> find(String firstName, String lastName) {
        return context.select().from(Users.USERS)
            .where(Users.USERS.FIRST_NAME.likeIgnoreCase("%" + firstName + "%"))
            .and(Users.USERS.SECOND_NAME.likeIgnoreCase("%" + lastName + "%"))
            .orderBy(Users.USERS.ID)
            .fetchInto(User.class);
    }
}
