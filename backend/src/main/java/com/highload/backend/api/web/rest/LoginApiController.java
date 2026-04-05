package com.highload.backend.api.web.rest;

import com.highload.backend.api.LoginApi;
import com.highload.backend.model.InlineResponse200;
import com.highload.backend.model.LoginBody;
import com.highload.backend.service.LoginService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class LoginApiController implements LoginApi {

    private final LoginService loginService;

    private final HttpServletRequest request;

    @Autowired
    public LoginApiController(LoginService loginService, HttpServletRequest request) {
        this.loginService = loginService;
        this.request = request;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<InlineResponse200> login(@Parameter(in = ParameterIn.DEFAULT, schema = @Schema())
    @Valid @RequestBody LoginBody body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            var token = loginService.login(body);
            if (Strings.isNotEmpty(token)) {
                var result = new InlineResponse200();
                result.setToken(token);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
