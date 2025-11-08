package com.highload.backend.api;

import com.highload.backend.model.DialogMessage;
import com.highload.backend.model.InlineResponse500;
import com.highload.backend.model.UserIdSendBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.validation.Valid;

import java.util.List;

@Validated
public interface DialogApi {

    @Operation(summary = "", description = "", security = {
        @SecurityRequirement(name = "bearerAuth")}, tags = {})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Диалог между двумя пользователями", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DialogMessage.class)))),

        @ApiResponse(responseCode = "400", description = "Невалидные данные ввода"),

        @ApiResponse(responseCode = "401", description = "Неавторизованный доступ"),

        @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class))),

        @ApiResponse(responseCode = "503", description = "Ошибка сервера", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class)))})
    @RequestMapping(value = "/dialog/{user_id}/list",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<DialogMessage>> dialogUserIdListGet(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("user_id") String userId
    );

    @Operation(summary = "", description = "", security = {
        @SecurityRequirement(name = "bearerAuth")}, tags = {})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешно отправлено сообщение"),

        @ApiResponse(responseCode = "400", description = "Невалидные данные ввода"),

        @ApiResponse(responseCode = "401", description = "Неавторизованный доступ"),

        @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class))),

        @ApiResponse(responseCode = "503", description = "Ошибка сервера", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class)))})
    @RequestMapping(value = "/dialog/{user_id}/send",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<Void> dialogUserIdSendPost(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("user_id") String userId
        , @Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody UserIdSendBody body
    );
}

