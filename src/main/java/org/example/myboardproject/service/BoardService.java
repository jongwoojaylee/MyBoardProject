package org.example.myboardproject.service;

import lombok.RequiredArgsConstructor;
import org.example.myboardproject.domain.Board;
import org.example.myboardproject.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;


    //board 표시, paging 처리
    public Page<Board> getAllBoards(Pageable pageable) {
        Pageable sortedByDescId = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC,"id"));
        return boardRepository.findAll(sortedByDescId);
    }
    //보드 작성
    public Board saveBoard(Board board) {
        return boardRepository.save(board);
    }
    //보드 1개만 표시
    public Board getBoardById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }
    //보드 삭제
    public void deleteBoardById(Long id) {
        boardRepository.deleteById(id);
    }
    //보드 수정
    public Board updateBoard(Board board) {
        return boardRepository.save(board);
    }
    public boolean verifyPassword(Long id, String password) {
        Board board = getBoardById(id);
        if (board.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }

    }

}
