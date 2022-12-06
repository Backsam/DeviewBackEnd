package com.InhaTc.Deview.Comment.Service;

import com.InhaTc.Deview.Comment.DTO.CommentRequestDTO;
import com.InhaTc.Deview.Comment.Entity.Comment;
import com.InhaTc.Deview.Comment.Repository.CommentRepository;
import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import com.InhaTc.Deview.Portfolio.Repository.PortfolioRepository;
import com.InhaTc.Deview.User.Entitiy.UserEntity;
import com.InhaTc.Deview.User.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UserRepository userRepository;

    public Comment createComment(CommentRequestDTO commentDTO, String userId, Long pfId){
        Comment comment = commentDTO.toEntity();

       Optional<PortfolioEntity> portfolioOpt = Optional.ofNullable(portfolioRepository.findByPfId(pfId));
       portfolioOpt.ifPresentOrElse(
               portfolio -> {
                   comment.setPortfolio(portfolioRepository.findByPfId(pfId));
                   comment.setUser(userRepository.findByUserId(userId));
               },
               () -> {log.info("null Portfolio to Comment");}
       );
        return commentRepository.save(comment);
    }

    //댓글 리스트
    @Transactional(readOnly = true)
    public Page<Comment> getCommentList(Pageable pageable, long pfId) {
        return commentRepository.getCommentOfPortfolio(pageable, pfId);
    }
    // 댓글 삭제
    @Transactional
    public void deleteComments(long commentId , String userId) {
        commentRepository.findById(commentId).ifPresentOrElse(
                comment -> {
                    validate(comment, userId);
                    commentRepository.delete(comment);
                },
                () ->{
                    log.warn("comment can't be null");
                    throw new RuntimeException("Comment can't be null");
                }
        );
    }

    //댓글 수정
    @Transactional
    public void updateComment(CommentRequestDTO commentDTO, String userId, Long commentId) {
        commentRepository.findById(commentId).ifPresentOrElse(
                origin -> {
                    validate(origin, userId);
                    origin.setContent(commentDTO.getContent());
                    commentRepository.save(origin);
                },
                () ->{
                    log.warn("comment can't be null");
                    throw new RuntimeException("Comment can't be null");
                }
        );
    }

    private void validate(final Comment comment, String userId){

        if(comment.getUser().getUserId() == userId){
            log.warn("Not matching User");
            throw new RuntimeException("Not matching User");
        }
    }

}
