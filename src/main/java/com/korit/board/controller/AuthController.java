package com.korit.board.controller;

import com.korit.board.aop.annotation.ArgsAop;
import com.korit.board.aop.annotation.ReturnAop;
import com.korit.board.aop.annotation.TimeAop;
import com.korit.board.aop.annotation.ValidAop;
import com.korit.board.dto.SigninReqDto;
import com.korit.board.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import com.korit.board.dto.SignupReqDto;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @ReturnAop
    @ArgsAop
    @TimeAop
    @ValidAop
    @PostMapping("/auth/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupReqDto signupReqDto,
                                    BindingResult bindingResult) {
        return ResponseEntity.ok(authService.signUp(signupReqDto));
    }

    @ArgsAop
    @PostMapping("auth/signin")
    public ResponseEntity<?> signin(@RequestBody SigninReqDto signinReqDto) {

        return  ResponseEntity.ok(authService.signin(signinReqDto));
    }

    @GetMapping("/auth/token/authenticate")
    public ResponseEntity<?> authenticate(@RequestHeader(value = "Authorization") String token) {
        // 토큰의 유효성 검사
        return ResponseEntity.ok(authService.authenticate(token));
    }



}
