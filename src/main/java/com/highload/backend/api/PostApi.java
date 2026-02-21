package com.highload.backend.api;

import com.highload.backend.model.InlineResponse500;
import com.highload.backend.model.Post;
import com.highload.backend.model.PostCreateBody;
import com.highload.backend.model.PostUpdateBody;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

@Validated
public interface PostApi {

    @Operation(description = "Добавление поста", security = {
        @SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Успешно создан пост",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "400", description = "Невалидные данные ввода"),
        @ApiResponse(responseCode = "401", description = "Неавторизованный доступ"),
        @ApiResponse(responseCode = "500", description = "Ошибка сервера",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class))),
        @ApiResponse(responseCode = "503", description = "Ошибка сервера",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class)))})
    @PostMapping(value = "/post/create")
    ResponseEntity<String> createPost(@Parameter(in = ParameterIn.DEFAULT, schema = @Schema()) @Valid @RequestBody PostCreateBody body);

    @Operation(description = "Получение поста по ID", security = {
        @SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешно получен пост",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))),
        @ApiResponse(responseCode = "400", description = "Невалидные данные ввода"),
        @ApiResponse(responseCode = "401", description = "Неавторизованный доступ"),
        @ApiResponse(responseCode = "500", description = "Ошибка сервера",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class))),
        @ApiResponse(responseCode = "503", description = "Ошибка сервера",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class)))})
    @GetMapping(value = "/post/get/{id}")
    ResponseEntity<Post> getPost(@Parameter(in = ParameterIn.PATH, required = true, schema = @Schema()) @PathVariable("id") String id);

    @Operation(description = "Удаление поста по ID", security = {
        @SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешно удален пост"),
        @ApiResponse(responseCode = "400", description = "Невалидные данные ввода"),
        @ApiResponse(responseCode = "401", description = "Неавторизованный доступ"),
        @ApiResponse(responseCode = "500", description = "Ошибка сервера",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class))),
        @ApiResponse(responseCode = "503", description = "Ошибка сервера",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class)))})
    @PutMapping(value = "/post/delete/{id}")
    ResponseEntity<Void> deletePost(@Parameter(in = ParameterIn.PATH, required = true, schema = @Schema()) @PathVariable("id") String id);

    @Operation(description = "Изменение поста", security = {
        @SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешно изменен пост"),
        @ApiResponse(responseCode = "400", description = "Невалидные данные ввода"),
        @ApiResponse(responseCode = "401", description = "Неавторизованный доступ"),
        @ApiResponse(responseCode = "500", description = "Ошибка сервера",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class))),
        @ApiResponse(responseCode = "503", description = "Ошибка сервера",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class)))})
    @PutMapping(value = "/post/update")
    ResponseEntity<Void> updatePost(@Parameter(in = ParameterIn.DEFAULT, schema = @Schema()) @Valid @RequestBody PostUpdateBody body);

    @Operation(description = "Получение постов друзей", security = {
        @SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешно получены посты друзей",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Post.class)))),
        @ApiResponse(responseCode = "400", description = "Невалидные данные ввода"),
        @ApiResponse(responseCode = "401", description = "Неавторизованный доступ"),
        @ApiResponse(responseCode = "500", description = "Ошибка сервера",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class))),
        @ApiResponse(responseCode = "503", description = "Ошибка сервера",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class)))})
    @GetMapping(value = "/post/feed")
    ResponseEntity<List<Post>> feedPosts(
        @DecimalMin("0") @Parameter(in = ParameterIn.QUERY,
            schema = @Schema(defaultValue = "0")) @Valid @RequestParam(value = "offset", required = false, defaultValue = "0") Long offset,
        @DecimalMin("1") @Parameter(in = ParameterIn.QUERY,
            schema = @Schema(defaultValue = "10")) @Valid @RequestParam(value = "limit", required = false, defaultValue = "10") Long limit
    );
}

