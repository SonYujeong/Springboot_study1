package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Service //서비스 선언(서비스 객체를 스프링부트에 생성한다.)
public class ArticleService {

    @Autowired //DI
    private ArticleRepository articleRepository;

    public List<Article> index(){ //article 목록 조회
        return articleRepository.findAll();
    }

    public Article show(Long id) { //단일 article 조회
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if(article.getId() != null){
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        //1. 수정용 Entity 생성
        Article article = dto.toEntity();
        log.info("id:{}, article:{}", id, article.toString());

        //2. 대상 Entity 찾기
        Article target = articleRepository.findById(id).orElse(null);

        //3. 잘못된 요청 처리(대상이 없거나 id가 다른 경우)
        if(target == null || id != article.getId()){
            //400, 잘못된 요청 응답
            log.info("잘못된 요청! id:{}, article:{}", id, article.toString());
            return null;
        }

        //4. 업데이트
        target.patch(article); //바꿀 대상(target)에 기존 article을 붙여준다.
        Article updated = articleRepository.save(target); //새롭게 붙여진 target을 저장한다.
        return updated;
    }

    public Article delete(@PathVariable Long id){
        //1. 대상 Entity 찾기
        Article target = articleRepository.findById(id).orElse(null);

        //2. 잘못된 요청 처리(대상이 없는 경우)
        if(target == null){
            return null;
        }

        //3. 대상 삭제하기
        articleRepository.delete(target);
        return target;
    }
}
