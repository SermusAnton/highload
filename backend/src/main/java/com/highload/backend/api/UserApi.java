package com.highload.backend.api;

import com.highload.backend.model.InlineResponse2001;
import com.highload.backend.model.InlineResponse500;
import com.highload.backend.model.User;
import com.highload.backend.model.UserRegisterBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.UUID;

public interface UserApi {

    @Operation(description = "Получение анкеты пользователя")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешное получение анкеты пользователя",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "400", description = "Невалидные данные"),
        @ApiResponse(responseCode = "404", description = "Анкета не найдена"),
        @ApiResponse(responseCode = "500", description = "Ошибка сервера",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class))),
        @ApiResponse(responseCode = "503", description = "Ошибка сервера",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class)))})
    ResponseEntity<User> getBy(
        @Parameter(in = ParameterIn.PATH, description = "Идентификатор пользователя", required = true, schema = @Schema())
        @PathVariable("id") UUID id
    );

    @Operation(description = "Регистрация нового пользователя")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешная регистрация",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse2001.class))),
        @ApiResponse(responseCode = "400", description = "Невалидные данные"),
        @ApiResponse(responseCode = "500", description = "Ошибка сервера",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class))),
        @ApiResponse(responseCode = "503", description = "Ошибка сервера",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class)))})
    ResponseEntity<InlineResponse2001> register(
        @Parameter(in = ParameterIn.DEFAULT, schema = @Schema()) @Valid @RequestBody UserRegisterBody body
    );

    @Operation(description = "Поиск анкет")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешный поиск пользователя",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))),
        @ApiResponse(responseCode = "400", description = "Невалидные данные"),
        @ApiResponse(responseCode = "500", description = "Ошибка сервера",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class))),
        @ApiResponse(responseCode = "503", description = "Ошибка сервера",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class)))})
    ResponseEntity<List<User>> search(
        @NotNull @Parameter(in = ParameterIn.QUERY, description = "Условие поиска по имени", required = true, schema = @Schema())
        @Valid @RequestParam(value = "first_name") String firstName,
        @NotNull @Parameter(in = ParameterIn.QUERY, description = "Условие поиска по фамилии", required = true, schema = @Schema())
        @Valid @RequestParam(value = "last_name") String lastName
    );
}

