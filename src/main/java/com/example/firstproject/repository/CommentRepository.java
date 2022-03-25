package com.example.firstproject.repository;

import com.example.firstproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository <Comment, Long> {//관리할 대상, 대상의 id 타입
    //특정 게시글의 모든 댓글 조회
    @Query(value = "SELECT * FROM comment WHERE article_id = :articleId", nativeQuery = true)
    /*위의 쿼리문을 보기 좋게 작성하기
	->이렇게 작성하니 자꾸 commentWHERE테이블로 인식해서 테이블을 못 찾는 에러 발생
	->다시 위의 쿼리문으로 바꿔줌
	@Query(value =
           "SELECT * " +
           "FROM comment" +
           "WHERE article_id = :articleId"
           , nativeQuery = true)*/

    List<Comment> findByArticleId(Long articleId);

    //특정 닉네임의 모든 댓글 조회
    List<Comment> findByNickname(String nickname);
}
