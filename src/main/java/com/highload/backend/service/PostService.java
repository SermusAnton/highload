package com.highload.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.highload.backend.dao.PostRepository;
import com.highload.backend.model.Post;
import com.highload.backend.model.PostCreateBody;
import com.highload.backend.model.PostUpdateBody;

import java.util.Collections;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final FriendService friendService;
    private final RedisService redisService;

    public final String POSTS_DICTIONARY_NAME = "Posts";

    private final ObjectMapper customObjectMapper;

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    public PostService(PostRepository postRepository,
        FriendService friendService,
        RedisService redisService, ObjectMapper customObjectMapper) {
        this.postRepository = postRepository;
        this.friendService = friendService;
        this.redisService = redisService;
        this.customObjectMapper = customObjectMapper;
    }

    @PostConstruct
    public void init() {
        var posts = postRepository.getAll();
        posts.forEach(post ->
        {
            try {
                redisService.saveInList(POSTS_DICTIONARY_NAME + post.getAuthorUserId(),
                    customObjectMapper.writeValueAsString(post));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public UUID add(UUID userId, PostCreateBody body) {
        return postRepository.add(userId, body.getText());
    }

    public Post getBy(UUID id) {
        return postRepository.getBy(id);
    }

    public int setDeleted(UUID id, UUID userId) {
        redisService.deletePosts(POSTS_DICTIONARY_NAME + userId,
            id.toString(),
            userId.toString());
        return postRepository.setDeleted(id, userId);
    }

    public int update(UUID userId, PostUpdateBody body) {
        try {
            redisService.updatePostById(POSTS_DICTIONARY_NAME + userId,
                body.getId(),
                customObjectMapper.writeValueAsString(body));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return postRepository.update(userId, body);
    }

    public List<Post> getPostsByFriends(UUID userId,
        Long offset,
        Long limit) {
        try {
            // Пытаемся взять из Redis
            return redisService.feedPosts(userId.toString(), offset, limit).stream()
                .map(jsonString -> {
                    try {
                        return customObjectMapper.readValue(jsonString, Post.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        } catch (Exception e) {
            logger.error("Redis error, falling back to Database: {}", e.getMessage());
            var friends = friendService.getFriendsBy(userId);
            if (Objects.nonNull(friends)) {
                return postRepository.getPostsByFriends(userId, offset, limit);
            }
        }
        return Collections.emptyList();
    }
}
