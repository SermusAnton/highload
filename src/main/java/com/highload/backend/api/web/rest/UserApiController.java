package com.highload.backend.api.web.rest;

import com.highload.backend.api.UserApi;
import com.highload.backend.model.InlineResponse2001;
import com.highload.backend.model.User;
import com.highload.backend.model.UserRegisterBody;
import com.highload.backend.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@Validated
public class UserApiController implements UserApi {
    private final UserService userService;

    private final HttpServletRequest request;

    @Autowired
    public UserApiController(UserService userService, HttpServletRequest request) {
        this.userService = userService;
        this.request = request;
    }

    @GetMapping(value = "/user/get/{id}")
    public ResponseEntity<User> getBy(
        @Parameter(in = ParameterIn.PATH, description = "Идентификатор пользователя", required = true, schema = @Schema())
        @PathVariable("id") UUID id
    ) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            var user = userService.getBy(id);
            if (Objects.isNull(user)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/user/register")
    public ResponseEntity<InlineResponse2001> register(@Parameter(in = ParameterIn.DEFAULT, schema = @Schema()) @Valid
        @RequestBody UserRegisterBody body
    ) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            var userId = userService.register(body);
            var result = new InlineResponse2001();
            result.setUserId(userId.toString());
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/user/search")
    public ResponseEntity<List<User>> search(
        @NotNull @Parameter(in = ParameterIn.QUERY, description = "Условие поиска по имени", required = true, schema = @Schema())
        @Valid @RequestParam(value = "first_name") String firstName,
        @NotNull @Parameter(in = ParameterIn.QUERY, description = "Условие поиска по фамилии", required = true, schema = @Schema())
        @Valid @RequestParam(value = "last_name") String lastName
    ) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            var users = userService.search(firstName, lastName);
            return new ResponseEntity<>(users, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
