package com.highload.backend.api.web.rest;

import com.highload.backend.api.FriendApi;
import com.highload.backend.service.FriendService;
import com.highload.backend.service.JwtCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;

@RestController
public class FriendApiController implements FriendApi {

    private final HttpServletRequest request;
    private final FriendService friendService;

    @Autowired
    public FriendApiController(HttpServletRequest request, FriendService friendService) {
        this.request = request;
        this.friendService = friendService;
    }

    public ResponseEntity<Void> setFriend(String friendId) {
        String authorizationHeader = request.getHeader("Authorization");
        String userId = JwtCreate.extractUserId(authorizationHeader);
        friendService.add(UUID.fromString(userId), UUID.fromString(friendId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteFriend(String friendId) {
        String authorizationHeader = request.getHeader("Authorization");
        String userId = JwtCreate.extractUserId(authorizationHeader);
        friendService.delete(UUID.fromString(userId), UUID.fromString(friendId));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
