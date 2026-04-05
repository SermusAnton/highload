package com.highload.backend.dao.mapper;

import com.highload.backend.model.generated.tables.Post;
import org.jooq.Record;
import org.jooq.RecordMapper;

public class PostMapper implements RecordMapper<Record, com.highload.backend.model.Post> {


    @Override
    public com.highload.backend.model.Post map(Record record) {
        var post = new com.highload.backend.model.Post();
        post.id(record.get(Post.POST.ID).toString());
        post.authorUserId(record.get(Post.POST.USER_ID).toString());
        post.text(record.get(Post.POST.TEXT));
        post.setDateTime(record.get(Post.POST.CREATE_TIME));
        return post;
    }
}
