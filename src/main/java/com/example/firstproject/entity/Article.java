package com.example.firstproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity //DB가 해당 객체를 인식 가능하도록 해준다.(해당 클래스로 테이블을 생성한다.)
@AllArgsConstructor
@NoArgsConstructor //디폴트 생성자 추가
@ToString
@Getter
public class Article {

    @Id //대표값 지정(ex.주민등록번호)
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB가 id를 자동 생성하는 어노테이션(ex.1, 2, 3, ...)
    private Long id;

    @Column //DB의 테이블과 연결시켜준다.
    private String title;

    @Column
    private String content;


    public void patch(Article article) {
        if(article.title != null){
            this.title = article.title;
        }
        if(article.content != null){
            this.content = article.content;
        }
    }
}
