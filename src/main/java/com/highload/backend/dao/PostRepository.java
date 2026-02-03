package com.highload.backend.dao;

import com.highload.backend.model.PostUpdateBody;
import com.highload.backend.model.generated.tables.Friend;
import com.highload.backend.model.generated.tables.Post;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Repository
public class PostRepository {

    private final DSLContext context;

    public PostRepository(DSLContext context) {
        this.context = context;
    }

    public UUID add(UUID userId, String text) {
        var result = context.insertInto(Post.POST)
            .columns(
                Post.POST.USER_ID,
                Post.POST.TEXT)
            .values(
                userId,
                text)
            .returningResult(Post.POST.ID)
            .fetchOne();
        assert result != null;
        return result.getValue(Post.POST.ID);
    }

    public com.highload.backend.model.Post getBy(UUID id) {
        var result = context.fetchOne(Post.POST,
            Post.POST.ID.eq(id)
                .and(Post.POST.IS_DELETED.eq(Boolean.FALSE)));
        if (Objects.isNull(result)) {
            return null;
        }
        return result.into(com.highload.backend.model.Post.class);
    }

    public List<com.highload.backend.model.Post> getPostsByFriends(UUID userId,
        Long offset,
        Long limit) {
        return context.select(Post.POST)
            .from(Post.POST)
            .join(Friend.FRIEND)
            .on(Post.POST.USER_ID.eq(Friend.FRIEND.FRIEND_ID))
            .where(Friend.FRIEND.USER_ID.eq(userId))
            .and(Post.POST.IS_DELETED.eq(Boolean.FALSE))
            .orderBy(Post.POST.USER_ID, Post.POST.ID)
            .limit(limit)
            .offset(offset)
            .fetchInto(com.highload.backend.model.Post.class);
    }

    public int setDeleted(UUID id, UUID userId) {
        return context.update(Post.POST)
            .set(
                Post.POST.IS_DELETED,
                Boolean.TRUE
            )
            .where(Post.POST.ID.eq(id)
                .and(Post.POST.USER_ID.eq(userId))
                .and(Post.POST.IS_DELETED.eq(Boolean.FALSE)))
            .execute();
    }

    public int update(UUID userId, PostUpdateBody body) {
        return context.update(Post.POST)
            .set(
                Post.POST.TEXT,
                body.getText()
            )
            .where(Post.POST.ID.eq(UUID.fromString(body.getId()))
                .and(Post.POST.USER_ID.eq(userId))
                .and(Post.POST.IS_DELETED.eq(Boolean.FALSE)))
            .execute();
    }
}
