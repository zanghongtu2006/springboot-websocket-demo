package com.hongtu.project.im.controller;

import com.hongtu.project.im.commom.BaseResponse;
import com.hongtu.project.im.controller.dto.LoginRequest;
import com.hongtu.project.im.controller.dto.LoginResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "login")
public class LoginController {
    private final AuthenticationManager authenticationManager;

    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(path = "")
    public BaseResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authenticationResponse =
                authenticationManager.authenticate(authenticationRequest);
        // ...
        return new BaseResponse.Builder<LoginResponse>().setData(new LoginResponse()).build();
    }

}
