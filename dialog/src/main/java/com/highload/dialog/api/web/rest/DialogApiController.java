package com.highload.dialog.api.web.rest;

import com.highload.dialog.api.DialogApi;
import com.highload.dialog.model.DialogMessage;
import com.highload.dialog.model.UserIdSendBody;
import com.highload.dialog.remote.BackendServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class DialogApiController implements DialogApi {

    private static final Logger log = LoggerFactory.getLogger(DialogApiController.class);

    private final BackendServiceClient client;

    @Autowired
    public DialogApiController(BackendServiceClient client) {
        this.client = client;
    }

    public ResponseEntity<List<DialogMessage>> dialogList(UUID withUserId) {
        log.info("dialogList");
        return client.dialogList(withUserId);
    }

    public ResponseEntity<Void> dialogSend(UUID toUserId, UserIdSendBody body) {
        log.info("dialogSend");
        return client.dialogSend(toUserId, body);
    }
}
