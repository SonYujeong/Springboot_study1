package com.example.firstproject.entity;

import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity //DB가 해당 객체를 인식 가능하도록 해준다.
@AllArgsConstructor
@ToString
public class Article {

    @Id //대표값 지정(ex.주민등록번호)
    @GeneratedValue //자동 생성 어노테이션(ex.1, 2, 3, ...)
    private Long id;

    @Column //DB의 테이블과 연결시켜준다.
    private String title;

    @Column
    private String content;
}
