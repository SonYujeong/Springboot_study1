package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j //로깅을 위한 어노테이션
public class ArticleController {

    @Autowired //스프링 부트가 미리 생성해놓은 객체를 가져다가 자동 연결해준다.
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        log.info(form.toString());
        //System.out.println(form.toString()); ->로깅 기능으로 대체

        //1. DTO를 Entity로 변환하기
        Article article = form.toEntity(); //Article타입의 Entity를 반환하도록 한다.
        log.info(article.toString());
        //System.out.println(article.toString()); DTO가 Entity로 잘 변환됐는지 확인

        //2. Repository에게 Entity를 DB에 저장하도록 한다.
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        //System.out.println(saved.toString()); article이 잘 저장됐는지 확인
        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id = " +id); //잘 받아졌는지 확인

        //1. id로 데이터를 가져온다.
        Article articleEntity = articleRepository.findById(id).orElse(null);

        //2. 가져온 데이터를 모델에 등록한다.
        model.addAttribute("article", articleEntity);

        //3. 보여줄 페이지를 설정한다.
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){

        //1. 모든 Article을 가져온다.
        List<Article> articleEntityList = articleRepository.findAll();

        //2. 가져온 Article 묶음을 뷰로 전달한다.
        model.addAttribute("articleList", articleEntityList);

        //3. 뷰 페이지를 설정한다.
        return "articles/index"; // articles/index.mustache
    }
}
