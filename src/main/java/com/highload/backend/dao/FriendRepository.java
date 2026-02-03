package com.highload.backend.dao;

import com.highload.backend.model.generated.tables.Friend;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class FriendRepository {

    private final DSLContext context;

    public FriendRepository(DSLContext context) {
        this.context = context;
    }

    public void add(UUID userId, UUID friendId) {
        context.insertInto(Friend.FRIEND)
            .columns(
                Friend.FRIEND.USER_ID,
                Friend.FRIEND.FRIEND_ID)
            .values(
                userId,
                friendId)
            .execute();
    }

    public void delete(UUID userId, UUID friendId) {
        context.deleteFrom(Friend.FRIEND)
            .where(Friend.FRIEND.USER_ID.eq(userId)
                .and(Friend.FRIEND.FRIEND_ID.eq(friendId)))
            .execute();
    }
}
