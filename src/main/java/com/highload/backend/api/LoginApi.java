package com.highload.backend.api;

import com.highload.backend.model.InlineResponse200;
import com.highload.backend.model.InlineResponse500;
import com.highload.backend.model.LoginBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@Validated
public interface LoginApi {

    @Operation(summary = "", description = "Упрощенный процесс аутентификации путем передачи идентификатор пользователя " +
        "и получения токена для дальнейшего прохождения авторизации", tags = {})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешная аутентификация",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse200.class))),
        @ApiResponse(responseCode = "400", description = "Невалидные данные"),
        @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
        @ApiResponse(responseCode = "500", description = "Ошибка сервера",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class))),
        @ApiResponse(responseCode = "503", description = "Ошибка сервера",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class)))})
    ResponseEntity<InlineResponse200> login(@Parameter(in = ParameterIn.DEFAULT, schema = @Schema()) @Valid
        @RequestBody LoginBody body
    );
}

