package com.korit.board.controller;

import com.korit.board.aop.annotation.ValidAop;
import com.korit.board.dto.RegisterBoardReqDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class BoardController {

    @ValidAop
    @PostMapping("/board/{category}")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterBoardReqDto registerBoardReqDto,
                                      BindingResult bindingResult) {
        // AuthController에서 사용 했던 유효성 검사 코드가 100%똑같이 사용됨
        return ResponseEntity.ok(true);
    }
}
