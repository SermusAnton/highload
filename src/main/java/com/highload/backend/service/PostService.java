package com.highload.backend.service;

import com.highload.backend.dao.PostRepository;
import com.highload.backend.model.Post;
import com.highload.backend.model.PostCreateBody;
import com.highload.backend.model.PostUpdateBody;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public PostService(PostRepository postRepository, SimpMessagingTemplate messagingTemplate) {
        this.postRepository = postRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public UUID add(UUID userId, PostCreateBody body) {
        // Формируем ключ на основе данных (например, ID автора или категории)
        String routingKey = "user." + userId + ".posted";

        // Отправляем в конкретный exchange amq.topic с динамическим ключом
        messagingTemplate.convertAndSend("/topic/user." + userId + ".posted", body);

        return postRepository.add(userId, body.getText());
    }

    public Post getBy(UUID id) {
        return postRepository.getBy(id);
    }

    public int setDeleted(UUID id, UUID userId) {
        return postRepository.setDeleted(id, userId);
    }

    public int update(UUID userId, PostUpdateBody body) {
        return postRepository.update(userId, body);
    }

    public List<Post> getPostsByFriends(UUID userId,
        Long offset,
        Long limit) {
        return postRepository.getPostsByFriends(userId, offset, limit);
    }
}
