package com.highload.backend.api;

import java.math.BigDecimal;

import com.highload.backend.model.Post;
import com.highload.backend.model.PostCreateBody;
import com.highload.backend.model.PostUpdateBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;

@RestController
public class PostApiController implements PostApi {

    private static final Logger log = LoggerFactory.getLogger(PostApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    public PostApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<String> postCreatePost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody PostCreateBody body
    ) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<String>(objectMapper.readValue("\"1d535fd6-7521-4cb1-aa6d-031be7123c4d\"", String.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<String>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> postDeleteIdPut(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("id") String id
    ) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Post>> postFeedGet(@DecimalMin("0") @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema(defaultValue = "0")) @Valid @RequestParam(value = "offset", required = false, defaultValue = "0") BigDecimal offset
        , @DecimalMin("1") @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema(defaultValue = "10")) @Valid @RequestParam(value = "limit", required = false, defaultValue = "10") BigDecimal limit
    ) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Post>>(objectMapper.readValue("[ {\n  \"author_user_id\" : \"author_user_id\",\n  \"id\" : \"1d535fd6-7521-4cb1-aa6d-031be7123c4d\",\n  \"text\" : \"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lectus mauris ultrices eros in cursus turpis massa.\"\n}, {\n  \"author_user_id\" : \"author_user_id\",\n  \"id\" : \"1d535fd6-7521-4cb1-aa6d-031be7123c4d\",\n  \"text\" : \"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lectus mauris ultrices eros in cursus turpis massa.\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Post>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Post>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Post> postGetIdGet(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("id") String id
    ) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Post>(objectMapper.readValue("{\n  \"author_user_id\" : \"author_user_id\",\n  \"id\" : \"1d535fd6-7521-4cb1-aa6d-031be7123c4d\",\n  \"text\" : \"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lectus mauris ultrices eros in cursus turpis massa.\"\n}", Post.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Post>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Post>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> postUpdatePut(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody PostUpdateBody body
    ) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }
}
