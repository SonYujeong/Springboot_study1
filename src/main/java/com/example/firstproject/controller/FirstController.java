package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hi")
    public String niceToMeetYou(Model model) { //모델 받아오기
        model.addAttribute("username", "라이언"); //변수 등록
        return "greetings"; // templates/greetings.mustache을 찾아서 브라우저로 전송해줌
    }
        
    @GetMapping("/bye")
    public String seeYouNext(Model model){
        model.addAttribute("nickname", "어피치");
        return "goodbye";
    }
}
