package org.example.myboardproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.myboardproject.domain.Board;
import org.example.myboardproject.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public String showList(Model model,
                           @RequestParam(defaultValue = "1")int page,
                           @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page -1, size);
        Page<Board> Boards = boardService.getAllBoards(pageable);
        model.addAttribute("boards", Boards);
        model.addAttribute("currentPage", Boards);
        return "list";
    }
    @GetMapping("/view/{id}")
    public String showList(@PathVariable Long id, Model model){
        Board board = boardService.getBoardById(id);
        model.addAttribute("board", board);
        return "view";
    }
    @GetMapping("/writeform")
    public String showWriteForm(Model model){
        Board board = new Board();
        model.addAttribute("board", board);
        return "/writeForm";
    }
    @PostMapping("/write")
    public String writeBoard(@ModelAttribute("board") Board board){
        board.setCreatedAt(LocalDateTime.now());
        board.setUpdatedAt(LocalDateTime.now());
        boardService.saveBoard(board);
        return "redirect:/list";
    }

    @GetMapping("deleteForm/{id}")
    public String deleteBoard(@PathVariable Long id, Model model){
        model.addAttribute("id", id);
        return "/deleteForm";
    }

    @PostMapping("delete/{id}")
    public String deleteBoard(@PathVariable Long id,
                              @RequestParam("password") String password){
        boolean isCorrectPassword = boardService.verifyPassword(id, password);
        if(isCorrectPassword){
            boardService.deleteBoardById(id);
            return "redirect:/list";
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping("/updateForm/{id}")
    public String showEditForm(@PathVariable Long id, Model model){
        model.addAttribute("id",id);
        return "checkPassword";
    }
    @PostMapping("/updateForm/checkPassword/{id}")
    public String updateBoard(@PathVariable Long id,
                              @RequestParam("password") String password,
                              Model model){
        boolean isCorrectPassword = boardService.verifyPassword(id, password);
        System.out.println(isCorrectPassword);
        Board board = boardService.getBoardById(id);
        model.addAttribute("board", board);
        if(isCorrectPassword){
            return "updateForm";
        }
        return "redirect:/error";
    }

    @PostMapping("/update")
    public String editBoard(@ModelAttribute("board") Board board){
        board.setUpdatedAt(LocalDateTime.now());
        boardService.updateBoard(board);
        return "redirect:/list";
    }

    @GetMapping("/error")
    public String showError(){
        return "error";
    }

}
