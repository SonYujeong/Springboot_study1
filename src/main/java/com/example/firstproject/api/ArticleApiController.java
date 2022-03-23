package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController  //RestAPI용 컨트롤러, 데이터(JSON)를 반환한다.
public class ArticleApiController {

    @Autowired //스프링부트에서 가져온다.(DI)
    private ArticleRepository articleRepository;

    //GET
    @GetMapping("/api/articles") //article 목록 조회
    public List<Article> index(){
        return articleRepository.findAll();
    }
    
    @GetMapping("/api/articles/{id}") //단일 article 조회
    public Article index(@PathVariable Long id){
        return articleRepository.findById(id).orElse(null);
    }

    //POST
    @PostMapping("/api/articles")
    public Article create(@RequestBody ArticleForm dto){ //Request의 body에서 ArticleForm을 받아오라는 의미이다.
        Article article = dto.toEntity();
        return articleRepository.save(article);
    }

    //PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){

        //1. 수정용 Entity 생성
        Article article = dto.toEntity();
        log.info("id:{}, article:{}", id, article.toString()); //id는 첫번째{},

        //2. 대상 Entity 조회
        Article target = articleRepository.findById(id).orElse(null);

        //3. 잘못된 요청 처리(대상이 없거나 id가 다른 경우)
        if(target == null || id != article.getId()){
            //400, 잘못된 요청 응답
            log.info("잘못된 요청! id:{}, article:{}", id, article.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        //4. 업데이트 및 정상 응답(200)
        target.patch(article); //바꿀 대상(target)에 기존 article을 붙여준다.
        Article updated = articleRepository.save(target); //새롭게 붙여진 target을 저장한다.
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    //DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        //대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        //잘못된 요청 처리
        if(target == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        //대상 삭제
        articleRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).build(); //body(null)도 가능
    }
}
