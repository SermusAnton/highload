package com.highload.dialog.remote;

import com.highload.dialog.model.DialogMessage;
import com.highload.dialog.model.UserIdSendBody;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "backend-service", url = "${services.backend.url}")
public interface BackendServiceClient {
    @GetMapping(value = "/dialog/{user_id}/list")
    ResponseEntity<List<DialogMessage>> dialogList(
           @PathVariable("user_id") UUID userId
    );

    @PostMapping(value = "/dialog/{user_id}/send")
    ResponseEntity<Void> dialogSend(
            @PathVariable("user_id") UUID userId,
            @Valid @RequestBody UserIdSendBody body
    );
}
