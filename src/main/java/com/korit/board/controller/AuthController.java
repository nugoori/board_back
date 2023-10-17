package com.korit.board.controller;

import com.korit.board.aop.annotation.ArgsAop;
import com.korit.board.aop.annotation.ReturnAop;
import com.korit.board.aop.annotation.TimeAop;
import com.korit.board.aop.annotation.ValidAop;
import com.korit.board.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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

        authService.signUp(signupReqDto);

        return ResponseEntity.ok(true);
    }
}
