package com.highload.backend.api;

import java.math.BigDecimal;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

@Validated
public interface PostApi {

    @Operation(summary = "", description = "", security = {
        @SecurityRequirement(name = "bearerAuth")}, tags = {})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешно создан пост", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

        @ApiResponse(responseCode = "400", description = "Невалидные данные ввода"),

        @ApiResponse(responseCode = "401", description = "Неавторизованный доступ"),

        @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class))),

        @ApiResponse(responseCode = "503", description = "Ошибка сервера", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class)))})
    @RequestMapping(value = "/post/create",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<String> postCreatePost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody PostCreateBody body
    );

    @Operation(summary = "", description = "", security = {
        @SecurityRequirement(name = "bearerAuth")}, tags = {})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешно удален пост"),

        @ApiResponse(responseCode = "400", description = "Невалидные данные ввода"),

        @ApiResponse(responseCode = "401", description = "Неавторизованный доступ"),

        @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class))),

        @ApiResponse(responseCode = "503", description = "Ошибка сервера", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class)))})
    @RequestMapping(value = "/post/delete/{id}",
        produces = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<Void> postDeleteIdPut(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("id") String id
    );

    @Operation(summary = "", description = "", security = {
        @SecurityRequirement(name = "bearerAuth")}, tags = {})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешно получены посты друзей", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Post.class)))),

        @ApiResponse(responseCode = "400", description = "Невалидные данные ввода"),

        @ApiResponse(responseCode = "401", description = "Неавторизованный доступ"),

        @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class))),

        @ApiResponse(responseCode = "503", description = "Ошибка сервера", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class)))})
    @RequestMapping(value = "/post/feed",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<Post>> postFeedGet(@DecimalMin("0") @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema(defaultValue = "0")) @Valid @RequestParam(value = "offset", required = false, defaultValue = "0") BigDecimal offset
        , @DecimalMin("1") @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema(defaultValue = "10")) @Valid @RequestParam(value = "limit", required = false, defaultValue = "10") BigDecimal limit
    );

    @Operation(summary = "", description = "", tags = {})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешно получен пост", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))),

        @ApiResponse(responseCode = "400", description = "Невалидные данные ввода"),

        @ApiResponse(responseCode = "401", description = "Неавторизованный доступ"),

        @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class))),

        @ApiResponse(responseCode = "503", description = "Ошибка сервера", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class)))})
    @RequestMapping(value = "/post/get/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<Post> postGetIdGet(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("id") String id
    );

    @Operation(summary = "", description = "", security = {
        @SecurityRequirement(name = "bearerAuth")}, tags = {})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешно изменен пост"),

        @ApiResponse(responseCode = "400", description = "Невалидные данные ввода"),

        @ApiResponse(responseCode = "401", description = "Неавторизованный доступ"),

        @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class))),

        @ApiResponse(responseCode = "503", description = "Ошибка сервера", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse500.class)))})
    @RequestMapping(value = "/post/update",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<Void> postUpdatePut(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody PostUpdateBody body
    );
}

