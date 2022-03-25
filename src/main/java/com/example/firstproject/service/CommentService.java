package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired //article 데이터도 DB에서 가져와서 사용해야한다.
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId) {
        /*
        //조회: 댓글 목록 - 엔티티
        List<Comment> comments = commentRepository.findByArticleId(articleId);

        //변환: 엔티티 -> DTO
        List<CommentDto> dtos = new ArrayList<CommentDto>();
        for (int i = 0; i < comments.size(); i++) {
            Comment c = comments.get(i);
            CommentDto dto = CommentDto.createCommentDto(c);
            dtos.add(dto);
        }
        //반환
        return dtos;
        */

        //반복문을 스트림으로 작성
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());
    }

    @Transactional //데이터 생성은 DB를 변경시키므로 반드시 트랜잭션 처리해줘야한다.
    public CommentDto create(Long articleId, CommentDto dto) {
        //게시글 조회 및 예외 발생
        //해당 article이 없으면 예외가 발생하여 더이상 아래 로직이 수행되지 않는다.
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다."));

        //댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, article);

        //댓글 엔티티를 DB에 저장
        Comment created = commentRepository.save(comment);

        //DTO로 변경하여 반환
        return CommentDto.createCommentDto(created);
    }

    @Transactional //데이터 수정은 DB를 변경시키므로 반드시 트랜잭션 처리해줘야한다.
    public CommentDto update(Long id, CommentDto dto) {
        //댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다."));

        //댓글 수정
        //입력값으로 dto를 주고 수정할 대상인 target을 patch()메소드를 통해 수정한다.
        target.patch(dto);

        //DB 갱신
        //갱신된 target을 저장한다.
        Comment updated = commentRepository.save(target);

        //댓글 엔티티를 DTO로 변환 및 반환
        return CommentDto.createCommentDto(updated);
    }

    @Transactional //데이터 삭제는 DB를 변경시키므로 반드시 트랜잭션 처리해줘야한다.
    public CommentDto delete(Long id) {
        //댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상이 없습니다."));

        //댓글을 DB에서 삭제
        commentRepository.delete(target);

        //삭제 댓글을 DTO로 반환
        return CommentDto.createCommentDto(target);
    }
}

