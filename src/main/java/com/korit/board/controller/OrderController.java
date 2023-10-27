package com.korit.board.controller;

import com.korit.board.dto.OrderReqDto;
import com.korit.board.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    // 상품이 여러개일 경우 테이블을 분리해서 여러개 주문한 경우를 찾을 수 있도록 해야한다?
    @PostMapping("/order")
    public ResponseEntity<?> order(@RequestBody OrderReqDto orderReqDto) {
        return ResponseEntity.ok(orderService.saveOrder(orderReqDto));
    }
}
