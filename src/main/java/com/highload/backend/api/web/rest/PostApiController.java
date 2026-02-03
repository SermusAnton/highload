package com.highload.backend.api.web.rest;

import java.math.BigDecimal;

import com.highload.backend.api.PostApi;
import com.highload.backend.model.Post;
import com.highload.backend.model.PostCreateBody;
import com.highload.backend.model.PostUpdateBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.highload.backend.service.JwtCreate;
import com.highload.backend.service.PostService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
public class PostApiController implements PostApi {

    private final HttpServletRequest request;

    private final PostService postService;

    @Autowired
    public PostApiController(ObjectMapper objectMapper, HttpServletRequest request, PostService postService) {
        this.request = request;
        this.postService = postService;
    }

    public ResponseEntity<String> createPost(PostCreateBody body) {
        String authorizationHeader = request.getHeader("Authorization");
        String userId = JwtCreate.extractUserId(authorizationHeader);
        var id = postService.add(UUID.fromString(userId), body);
        return new ResponseEntity<>(id.toString(), HttpStatus.CREATED);
    }

    public ResponseEntity<Post> getPost(String id) {
        var result = postService.getBy(UUID.fromString(id));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<Void> deletePost(String id) {
        String authorizationHeader = request.getHeader("Authorization");
        String userId = JwtCreate.extractUserId(authorizationHeader);
        var result = postService.setDeleted(UUID.fromString(id), UUID.fromString(userId));
        if (result == 1) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Void> updatePost(PostUpdateBody body) {
        String authorizationHeader = request.getHeader("Authorization");
        String userId = JwtCreate.extractUserId(authorizationHeader);
        var result = postService.update(UUID.fromString(userId), body);
        if (result == 1) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Post>> feedPosts(Long offset,
        Long limit) {
        String authorizationHeader = request.getHeader("Authorization");
        String userId = JwtCreate.extractUserId(authorizationHeader);
        var result = postService.getPostsByFriends(UUID.fromString(userId), offset, limit);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
