package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
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

    public static Comment createComment(CommentDto dto, Article article) { //클래스 메소드
        //예외 발생
        if (dto.getId() != null)
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 합니다.");
        if (dto.getArticleId() != article.getId())
             throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못되었습니다.");


       //엔티티 생성 및 반환
       return new Comment(
               dto.getId(),
               article,
               dto.getNickname(),
               dto.getBody()
       );
    }

    public void patch(CommentDto dto) { //인스턴스 메소드
        //예외 발생
        //URL로 던진 id와 JSON으로 던진 id가 다를 경우
        if(this.id != dto.getId())
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력되었습니다.");

        //객체를 갱신
        if(dto.getNickname() != null)
            this.nickname = dto.getNickname();
        if(dto.getBody() != null)
            this.body = dto.getBody();
    }
}
