package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    @Autowired //스프링 부트가 미리 생성해놓은 객체를 가져다가 자동 연결해준다.
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        System.out.println(form.toString());

        //1. DTO를 Entity로 변환하기
        Article article = form.toEntity(); //Article타입의 Entity를 반환하도록 한다.
        System.out.println(article.toString()); //DTO가 Entity로 잘 변환됐는지 확인

        //2. Repository에게 Entity를 DB에 저장하도록 한다.
        Article saved = articleRepository.save(article);
        System.out.println(saved.toString()); //article이 잘 저장됐는지 확인
        return "";
    }
}
