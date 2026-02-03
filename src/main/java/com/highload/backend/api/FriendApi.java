package com.highload.backend.api;

import com.highload.backend.model.InlineResponse500;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Validated
public interface FriendApi {

    @Operation(description = "Добавление друга", security = {
        @SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Пользователь успешно указал своего друга"),
        @ApiResponse(responseCode = "400", description = "Невалидные данные ввода"),
        @ApiResponse(responseCode = "401", description = "Неавторизованный доступ"),
        @ApiResponse(responseCode = "500", description = "Ошибка сервера",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class))),
        @ApiResponse(responseCode = "503", description = "Ошибка сервера",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class)))})
    @PutMapping(value = "/friend/set/{user_id}")
    ResponseEntity<Void> setFriend(@Parameter(in = ParameterIn.PATH, required = true, schema = @Schema()) @PathVariable("user_id") String friendId
    );

    @Operation(description = "Удаление друга", security = {
        @SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Пользователь успешно удалил из друзей пользователя"),
        @ApiResponse(responseCode = "400", description = "Невалидные данные ввода"),
        @ApiResponse(responseCode = "401", description = "Неавторизованный доступ"),
        @ApiResponse(responseCode = "500", description = "Ошибка сервера",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class))),
        @ApiResponse(responseCode = "503", description = "Ошибка сервера",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class)))})
    @PutMapping(value = "/friend/delete/{user_id}")
    ResponseEntity<Void> deleteFriend(@Parameter(in = ParameterIn.PATH, required = true, schema = @Schema()) @PathVariable("user_id") String friendId
    );
}

