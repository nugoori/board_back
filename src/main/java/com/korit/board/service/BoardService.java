package com.korit.board.service;

import com.korit.board.dto.*;
import com.korit.board.entity.Board;
import com.korit.board.entity.BoardCategory;
import com.korit.board.entity.UpdateBoard;
import com.korit.board.repository.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    public List<BoardCategoryRespDto> getBoardCategoriesAll() {
        List<BoardCategoryRespDto> boardCategoryRespDtos = new ArrayList<>();
        boardMapper.getBoardCategories().forEach(category -> {
            boardCategoryRespDtos.add(category.toCategoryDto());
        });
        return boardCategoryRespDtos;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean writeBoardContent(WriteBoardReqDto writeBoardReqDto) {
        BoardCategory boardCategory = null;
        // category를 새로 만들어서 요청을 보낸 경우
        if(writeBoardReqDto.getCategoryId() == 0) {
            boardCategory = BoardCategory.builder()
                    .boardCategoryName(writeBoardReqDto.getCategoryName())
                    .build();
            boardMapper.saveCategory(boardCategory);
            // new categoryId가 insert된 후 값을 바꿔줌
            writeBoardReqDto.setCategoryId(boardCategory.getBoardCategoryId());
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Board board = writeBoardReqDto.toBoardEntity(email);

        return boardMapper.saveBoardContent(board) > 0;
    }

    public List<BoardListRespDto> getBoardList(String category, int page, SearchBoardListReqDto searchBoardListReqDto) {
        int index = (page - 1) * 10;
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("index", index);
        paramsMap.put("categoryName", category);
        paramsMap.put("optionName", searchBoardListReqDto.getOptionName());
        paramsMap.put("searchValue", searchBoardListReqDto.getSearchValue());

        List<BoardListRespDto> boardListRespDtos = new ArrayList<>();
        boardMapper.getBoardList(paramsMap).forEach(
                board -> {
                    boardListRespDtos.add(board.toBoardListRespDto());
                });
        return boardListRespDtos;
    }

    public int getBoardCount(String category, SearchBoardListReqDto searchBoardListReqDto) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("categoryName", category);
        paramsMap.put("optionName", searchBoardListReqDto.getOptionName());
        paramsMap.put("searchValue", searchBoardListReqDto.getSearchValue());

        return boardMapper.getBoardCount(paramsMap);
    }

    public getBoardRespDto getBoardDetail(int boardId) {
        return boardMapper.getBoardByBoardId(boardId).toBoardDto();
    }

    public boolean getLikeState(int boardId) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("boardId", boardId);
        paramsMap.put("email", SecurityContextHolder.getContext().getAuthentication().getName()); // 없으면 ananimous
        return boardMapper.getLikeState(paramsMap) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean setLike(int boardId) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("boardId", boardId);
        paramsMap.put("email", SecurityContextHolder.getContext().getAuthentication().getName());
        return boardMapper.insertLike(paramsMap) > 0;
    }
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelLike(int boardId) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("boardId", boardId);
        paramsMap.put("email", SecurityContextHolder.getContext().getAuthentication().getName());
        return boardMapper.deleteLike(paramsMap) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBoard(int boardId) {
//        Map<String, Object> paramsMap = new HashMap<>();
//        paramsMap.put("boardId", boardId);
//        paramsMap.put("email", SecurityContextHolder.getContext().getAuthentication().getName());
        return boardMapper.deleteBoard(boardId) > 0;
    }

    public getBoardRespDto getBoardUpdate(int boardId) {
        return boardMapper.getBoardByBoardId(boardId).toBoardDto();
    }

    // email검사를 DB에서 까지는 해 줄 필요 없는 듯?
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBoard(int boardId, UpdateBoardReqDto updateBoardReqDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Board updateBoard = updateBoardReqDto.toBoardEntity(email);
        updateBoard.setBoardId(boardId);
        return boardMapper.updateBoard(updateBoard) > 0;
    }
}
