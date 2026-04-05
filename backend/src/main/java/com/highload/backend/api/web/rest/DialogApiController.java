package com.highload.backend.api.web.rest;

import com.highload.backend.api.DialogApi;
import com.highload.backend.model.DialogMessage;
import com.highload.backend.model.UserIdSendBody;
import com.highload.backend.service.DialogService;
import com.highload.backend.service.JwtCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;

@RestController
public class DialogApiController implements DialogApi {

    private static final Logger log = LoggerFactory.getLogger(DialogApiController.class);

    private final HttpServletRequest request;

    private final DialogService dialogService;

    @Autowired
    public DialogApiController(HttpServletRequest request, DialogService dialogService) {
        this.request = request;
        this.dialogService = dialogService;
    }

    public ResponseEntity<List<DialogMessage>> dialogList(UUID withUserId) {
        var authorizationHeader = request.getHeader("Authorization");
        var userId = UUID.fromString(JwtCreate.extractUserId(authorizationHeader));
        log.info("dialogList");
        var dialogs = dialogService.getListBy(userId, withUserId);
        return new ResponseEntity<>(dialogs, HttpStatus.OK);
    }

    public ResponseEntity<Void> dialogSend(UUID toUserId, UserIdSendBody body) {
        var authorizationHeader = request.getHeader("Authorization");
        var fromUserId = UUID.fromString(JwtCreate.extractUserId(authorizationHeader));
        log.info("dialogSend");
        dialogService.send(fromUserId, toUserId, body);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
