package org.example.myboardproject.controller;

import org.example.myboardproject.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleController {
    @Autowired
    private BoardRepository boardRepository;

    @GetMapping("/test")
    public String showTest(){
        return "sampleTemplate";
    }



}
