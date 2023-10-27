package com.korit.board.controller;

import com.korit.board.aop.annotation.ArgsAop;
import com.korit.board.aop.annotation.ValidAop;
import com.korit.board.dto.RegisterBoardReqDto;
import com.korit.board.dto.SearchBoardListReqDto;
import com.korit.board.dto.UpdateBoardReqDto;
import com.korit.board.dto.WriteBoardReqDto;
import com.korit.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    @GetMapping("/board/categories")
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok(boardService.getBoardCategoriesAll());
    }

    @ArgsAop
    @ValidAop
    @PostMapping("/board/content")
    public ResponseEntity writeBoard(@Valid @RequestBody WriteBoardReqDto writeBoardReqDto, BindingResult bindingResult) {
        return ResponseEntity.ok(boardService.writeBoardContent(writeBoardReqDto));
    }

    @ArgsAop
    @GetMapping("/boards/{categoryName}/{page}")
    public ResponseEntity<?> getBoardList(
            @PathVariable String categoryName,
            @PathVariable int page,
            SearchBoardListReqDto searchBoardListReqDto) {
        return ResponseEntity.ok(boardService.getBoardList(categoryName, page, searchBoardListReqDto));
    }

    @GetMapping("/boards/{categoryName}/count")
    public ResponseEntity<?> getBoardCount(@PathVariable String categoryName, SearchBoardListReqDto searchBoardListReqDto) {
        return ResponseEntity.ok(boardService.getBoardCount(categoryName, searchBoardListReqDto));
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<?> getBoardDetail(@PathVariable int boardId) {
        return ResponseEntity.ok(boardService.getBoardDetail(boardId));
    }

    @GetMapping("/board/like/{boardId}")
    public ResponseEntity<?> getLikeState(@PathVariable int boardId) {
        return ResponseEntity.ok(boardService.getLikeState(boardId));
    }

    @PostMapping("/board/like/{boardId}")
    public ResponseEntity<?> setLike(@PathVariable int boardId) {
        return ResponseEntity.ok(boardService.setLike(boardId));
    }

    @DeleteMapping("/board/like/{boardId}")
    public ResponseEntity<?> cancelLike(@PathVariable int boardId) {
        return ResponseEntity.ok(boardService.cancelLike(boardId));
    }

    @DeleteMapping("/board/delete/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable int boardId) {
        return ResponseEntity.ok(boardService.deleteBoard(boardId));
    }

    @GetMapping("/board/update/{boardId}")
    public ResponseEntity<?> getUpdateBoard(@PathVariable int boardId) {
        return ResponseEntity.ok(boardService.getBoardUpdate(boardId));
    }

//    @GetMapping("/board/categories")
//    public ResponseEntity<?> getCategoriesUpdate() {
//        return ResponseEntity.ok(boardService.getBoardCategoriesAll());
//    }

    @PutMapping("/board/update/{boardId}")
    public ResponseEntity<?> updateBoard(@PathVariable int boardId, @RequestBody UpdateBoardReqDto updateBoardReqDto) {
        return ResponseEntity.ok(boardService.updateBoard(boardId, updateBoardReqDto));
    }
}
