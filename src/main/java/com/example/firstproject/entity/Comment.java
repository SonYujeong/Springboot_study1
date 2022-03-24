package com.example.firstproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne //해당 댓글 엔티티 여러개가 하나의 Article에 연관된다.
    @JoinColumn(name = "article_id") //"article_id"컬럼에 Article의 대표값을 저장한다.
    private Article article; //댓글의 부모 게시글

    @Column
    private String nickname; //댓글 작성자

    @Column
    private String body; //댓글 내용
}
